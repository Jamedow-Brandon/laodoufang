package com.jamedow.laodoufang.service;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.jamedow.utils.UUIDHexGenerator;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private OSSClient client;

    private String bucketName;
    private String key;
    private String localFilePath;
    private String imgServer;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    private List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());


    public String uploadFile(MultipartFile uploadFile, String storgeId) {
        String contentType = uploadFile.getContentType();
        String fileName = uploadFile.getOriginalFilename();
        setKey(storgeId);
        logger.debug("fileName[{}],contentType[{}]", fileName, contentType);
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        ClientConfiguration conf = new ClientConfiguration();
        conf.setIdleConnectionTime(1000);
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);

        try {
            File file = multipartToFile(uploadFile);

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
        return imgServer + File.separator + storgeId;
    }

    private String claimUploadId() {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    private void completeMultipartUpload(String uploadId) {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        logger.debug("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private void listAllParts(String uploadId) {
        logger.debug("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            logger.debug("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
    }

    public File multipartToFile(MultipartFile uploadFile) throws IOException {
        String fileName = uploadFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
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
