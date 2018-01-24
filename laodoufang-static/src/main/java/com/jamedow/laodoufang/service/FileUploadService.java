package com.jamedow.laodoufang.service;

import com.jamedow.utils.FtpUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/24.
 */
@Service
@ConfigurationProperties(prefix = "ftp")
public class FileUploadService {
    private String url;

    private String user;

    private String password;

    public void uploadFile(MultipartFile file, String filePath) throws Exception {
        FtpUtil ftpUtil = new FtpUtil(url, 21, user, password);
        ftpUtil.uploadFile(new File(file.getOriginalFilename()), filePath);
    }

    public void deleteFtpFile(String remotePath) throws Exception {
        FtpUtil ftpUtil = new FtpUtil(url, 21, user, password);
        ftpUtil.deleteRemoteFile(remotePath);
    }
}
