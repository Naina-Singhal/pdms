/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hpasupuleti
 */
public class IndentFileMappingDTO extends  BaseDTO implements Serializable{

    private static final long serialVersionUID = -8281549977493483173L;
    
    private long indentFileMappingID;
    private long fileGroupID;
    private long indentFormID;
    private String description;
    private String groupName;
    private String fileNo;
    private List<String> mappedIndents=new ArrayList<>();
    private IndentFormDTO indentObj = new IndentFormDTO();
    private GroupDTO groupDTO = new GroupDTO();

    /**
     * @return the indentFileMappingID
     */
    public long getIndentFileMappingID() {
        return indentFileMappingID;
    }

    /**
     * @param indentFileMappingID the indentFileMappingID to set
     */
    public void setIndentFileMappingID(long indentFileMappingID) {
        this.indentFileMappingID = indentFileMappingID;
    }

    /**
     * @return the fileGroupID
     */
    public long getFileGroupID() {
        return fileGroupID;
    }

    /**
     * @param fileGroupID the fileGroupID to set
     */
    public void setFileGroupID(long fileGroupID) {
        this.fileGroupID = fileGroupID;
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

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the fileNo
     */
    public String getFileNo() {
        return fileNo;
    }

    /**
     * @param fileNo the fileNo to set
     */
    public void setFileNo(String fileNo) {
        this.fileNo = fileNo;
    }

    /**
     * @return the mappedIndents
     */
    public List<String> getMappedIndents() {
        return mappedIndents;
    }

    /**
     * @param mappedIndents the mappedIndents to set
     */
    public void setMappedIndents(List<String> mappedIndents) {
        this.mappedIndents = mappedIndents;
    }

    /**
     * @return the indentObj
     */
    public IndentFormDTO getIndentObj() {
        return indentObj;
    }

    /**
     * @param indentObj the indentObj to set
     */
    public void setIndentObj(IndentFormDTO indentObj) {
        this.indentObj = indentObj;
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }
    
    
}
