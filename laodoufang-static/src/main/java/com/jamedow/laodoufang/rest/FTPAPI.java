package com.jamedow.laodoufang.rest;

import com.jamedow.laodoufang.dto.FTPInfo;
import com.jamedow.laodoufang.dto.UserInfo;
import com.jamedow.laodoufang.ftp.MyFtpServer;
import org.apache.ftpserver.ftplet.FtpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/24.
 */
public class FTPAPI {
    @Autowired
    private MyFtpServer myFtpServer;

    @GetMapping("info")
    public FTPInfo info() throws FtpException {
        return myFtpServer.getFTPInfo();
    }

    @PostMapping("setMaxUploadRate")
    public FTPInfo setMaxUploadRate(@RequestBody FTPInfo ftpInfo) throws FtpException, IOException {
        myFtpServer.setMaxUploadRate(ftpInfo.getMaxUploadRate());
        return myFtpServer.getFTPInfo();
    }

    @PostMapping("setMaxDownloadRate")
    public FTPInfo setMaxDownloadRate(@RequestBody FTPInfo ftpInfo) throws FtpException, IOException {
        myFtpServer.setMaxDownloadRate(ftpInfo.getMaxDownloadRate());
        return myFtpServer.getFTPInfo();
    }

    @PostMapping("setHomeDir")
    public FTPInfo setHomeDir(@RequestBody FTPInfo ftpInfo) throws FtpException, IOException {
        myFtpServer.setHomeDir(ftpInfo.getHomeDir());
        return myFtpServer.getFTPInfo();
    }

    @PostMapping("setPassword")
    public FTPInfo setPassword(@RequestBody UserInfo userInfo) throws FtpException {
        myFtpServer.setPassword(userInfo);
        return myFtpServer.getFTPInfo();
    }
}
