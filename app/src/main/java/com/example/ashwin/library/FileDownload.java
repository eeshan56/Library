package com.example.ashwin.library;

public class FileDownload {

    private String fileName, fileType, fileSize, uploadTime, databaseRef;

    public FileDownload() {

    }

    public FileDownload(String fileName, String fileType, String fileSize, String uploadTime, String databaseRef) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
        this.databaseRef = databaseRef;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getDatabaseRef() {
        return databaseRef;
    }

    public void setDatabaseRef(String databaseRef) {
        this.databaseRef = databaseRef;
    }
}
