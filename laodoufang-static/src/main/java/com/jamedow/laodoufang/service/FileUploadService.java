package com.jamedow.laodoufang.service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.jamedow.laodoufang.entity.BaseAttachment;
import com.jamedow.laodoufang.entity.BaseAttachmentExample;
import com.jamedow.laodoufang.mapper.BaseAttachmentMapper;
import com.jamedow.laodoufang.utils.UUIDHexGenerator;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/24.
 */
@Data
@Service
@ConfigurationProperties(prefix = "oss")
public class FileUploadService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    private String key;
    private String localFilePath;
    private String imgServer;

    private OSSClient client;

    @Autowired
    private BaseAttachmentMapper attachmentMapper;

    @Transactional(rollbackFor = Exception.class)
    public BaseAttachment uploadFile(MultipartFile uploadFile) {
        String fileName = uploadFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));  // 获取文件后缀
        String contentType = uploadFile.getContentType();  // 获取文件类型
        long size = uploadFile.getSize();  // 获取文件大小
        String resourceId = UUIDHexGenerator.getUUIDHex();  //生成阿里云资源ID
        String remotePath = imgServer + File.separator + resourceId;
        //设置阿里云文件资源key
        setKey(String.valueOf(resourceId));

        logger.debug("fileName[{}],contentType[{}]", fileName, contentType);

        //创建OSS文件存储服务连接
        createOSSClient();

        try {
            File file = multipartToFile(uploadFile, suffix);

            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
            // 待上传的本地文件
            uploadFileRequest.setUploadFile(file.getAbsolutePath());
            // 设置并发下载数，默认1
            uploadFileRequest.setTaskNum(5);
            // 设置分片大小，默认100KB
            uploadFileRequest.setPartSize(1024 * 1024 * 1);
            // 开启断点续传，默认关闭
            uploadFileRequest.setEnableCheckpoint(true);

            UploadFileResult uploadResult = client.uploadFile(uploadFileRequest);

            CompleteMultipartUploadResult multipartUploadResult =
                    uploadResult.getMultipartUploadResult();
            logger.debug(multipartUploadResult.getETag());
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.error("Error Message: " + oe.getErrorCode());
            logger.error("Error Code:       " + oe.getErrorCode());
            logger.error("Request ID:      " + oe.getRequestId());
            logger.error("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.error("Error Message: " + ce.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }

        //保存附件信息
        BaseAttachment attachment = new BaseAttachment();
        attachment.setName(fileName);
        attachment.setResourceType(contentType);
        attachment.setSuffix(suffix);
        attachment.setSize(size);
        attachment.setResourceId(resourceId);
        attachment.setRemotePath(remotePath);
        attachmentMapper.insertSelective(attachment);

        logger.info("upload image [{}] success", fileName);
        return attachment;
    }


    public List<String> deleteFile(String[] resourceIds) {
        List<String> deletedObjects = new ArrayList<>();
        //创建OSS文件存储服务连接
        createOSSClient();
        try {
            /*
             * Batch put objects into the bucket
             */
            final String content = "Thank you for using Aliyun Object Storage Service";
            List<String> keys = new ArrayList<String>();
            for (String resourceId : resourceIds) {
                InputStream instream = new ByteArrayInputStream(content.getBytes());
                client.putObject(bucketName, resourceId, instream);
                logger.debug("Succeed to put object " + resourceId);
                keys.add(resourceId);

                BaseAttachmentExample attachmentExample = new BaseAttachmentExample();
                attachmentExample.createCriteria().andResourceIdEqualTo(resourceId);
                attachmentMapper.deleteByExample(attachmentExample);
            }

            /*
             * Delete all objects uploaded recently under the bucket
             */
            logger.debug("\nDeleting all objects:");
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(
                    new DeleteObjectsRequest(bucketName).withKeys(keys));
            deletedObjects = deleteObjectsResult.getDeletedObjects();
            for (String object : deletedObjects) {
                logger.debug("\t" + object);
            }

        } catch (OSSException oe) {
            logger.debug("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.debug("Error Message: " + oe.getErrorCode());
            logger.debug("Error Code:       " + oe.getErrorCode());
            logger.debug("Request ID:      " + oe.getRequestId());
            logger.debug("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.debug("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.debug("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
        return deletedObjects;
    }

    /**
     * 创建OSS文件存储服务连接
     */
    private void createOSSClient() {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        ClientConfiguration conf = new ClientConfiguration();
        conf.setIdleConnectionTime(1000);
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
    }

    /**
     * 文件格式转换
     *
     * @param uploadFile multipart格式文件
     * @param prefix     文件后缀
     * @return 返回File格式文件
     * @throws IOException 会抛出的错误类型
     */
    private File multipartToFile(MultipartFile uploadFile, String prefix) throws IOException {
        final File excelFile = File.createTempFile(UUIDHexGenerator.getUUIDHex(), prefix);
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), excelFile);
        return excelFile;

    }
}
