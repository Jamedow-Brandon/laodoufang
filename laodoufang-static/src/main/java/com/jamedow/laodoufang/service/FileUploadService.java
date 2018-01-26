package com.jamedow.laodoufang.service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import com.jamedow.laodoufang.entity.BaseAttachment;
import com.jamedow.laodoufang.mapper.BaseAttachmentMapper;
import com.jamedow.utils.UUIDHexGenerator;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/24.
 */
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
        //设置阿里云资源key
        setKey(String.valueOf(resourceId));

        logger.debug("fileName[{}],contentType[{}]", fileName, contentType);
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        ClientConfiguration conf = new ClientConfiguration();
        conf.setIdleConnectionTime(1000);
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);

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

    private File multipartToFile(MultipartFile uploadFile, String prefix) throws IOException {
        final File excelFile = File.createTempFile(UUIDHexGenerator.getUUIDHex(), prefix);
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), excelFile);
        return excelFile;

    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public String getImgServer() {
        return imgServer;
    }

    public void setImgServer(String imgServer) {
        this.imgServer = imgServer;
    }
}
