/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 *
 * @author hpasupuleti
 */
@JsonIgnoreProperties({"bytes"})
public class IndentFormFileDTO extends BaseDTO implements Serializable{

    private long fileID;
    private String fileName;
    private String fileSize;
    private String fileType;

    private byte[] bytes;
    private String filePath;
    private long indentFormID;
    

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the fileSize
     */
    public String getFileSize() {
        return fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the bytes
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileID
     */
    public long getFileID() {
        return fileID;
    }

    /**
     * @param fileID the fileID to set
     */
    public void setFileID(long fileID) {
        this.fileID = fileID;
    }

    /**
     * @return the indentFormID
     */
    public long getIndentFormID() {
        return indentFormID;
    }

    /**
     * @param indentFormID the indentFormID to set
     */
    public void setIndentFormID(long indentFormID) {
        this.indentFormID = indentFormID;
    }

}
