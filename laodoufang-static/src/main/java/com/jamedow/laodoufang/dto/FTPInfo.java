package com.jamedow.laodoufang.dto;

/**
 * Description
 * <p>
 * Created by Administrator on 2018/1/24.
 */
public class FTPInfo {
    private String host;
    private int port;
    private String homeDir;
    private int maxDownloadRate; // KB
    private int maxUploadRate; // KB
    private float usedSpace; // G
    private float totalSpace; // G

    public FTPInfo(String host, int port, String homeDir,
                   int maxDownloadRate, int maxUploadRate, long usedSpace, long totalSpace) {
        this.host = host;
        this.port = port;
        this.homeDir = homeDir;
        this.maxDownloadRate = maxDownloadRate;
        this.maxUploadRate = maxUploadRate;
        this.usedSpace = Float.parseFloat(String.format("%.2f", usedSpace * 1.0 / 1024 / 1024 / 1024));
        this.totalSpace = Float.parseFloat(String.format("%.2f", totalSpace * 1.0 / 1024 / 1024 / 1024));
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getHomeDir() {
        return homeDir;
    }

    public int getMaxDownloadRate() {
        return maxDownloadRate;
    }

    public int getMaxUploadRate() {
        return maxUploadRate;
    }

    public float getUsedSpace() {
        return usedSpace;
    }

    public float getTotalSpace() {
        return totalSpace;
    }
}
