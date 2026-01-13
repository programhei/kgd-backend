package com.kgd.web.controller.common.vo;

import java.util.Arrays;

public class FileVo {
    // 上传地址
    private String url;
    // 文件名称
    private String fileName;

    // 文件原始名
    private String originalFilename;

    // 文件字节
    private byte[] fileBytes;

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    @Override
    public String toString() {
        return "FileVo{" +
                "url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", originalFilename='" + originalFilename + '\'' +
                ", fileBytes=" + Arrays.toString(fileBytes) +
                '}';
    }
}
