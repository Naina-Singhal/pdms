/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hpasupuleti
 */
public class IndentFormDTO extends BaseDTO implements Serializable{

    private static long serialVersionUID = 3256858712895391898L;

    
    private long indentFormID;
    private String indentNumber;
    private String recevidedDate;
    private String estimatedCost;
    private String indentDate;
    private long sectionId;
    private long approveAuthorityId;
    private long itemId;
    private long itemCategoryId;
    private long employeeProfileId;
    private long placeOfDeliveryId;
    private long modeOfPurchaseId;
    private long headOfAccountId;
    private String descriptionDetails;
    private String mta;
    private String briefDescription;
    private String indentTypeCTQ;
    private String indentTypeProp;
    private String otherInformation;
    private long signingAuthorityId;
    private String indentStatus;
    
    private SectionDTO sectionObj = new SectionDTO();
    private DesignationDTO approvingAuthorityObj = new DesignationDTO();
    private ItemDTO itemObj = new ItemDTO();
    private EmployeeProfileDTO empProfileDTo = new EmployeeProfileDTO();
    private PlaceOfDeliveryDTO podObj = new PlaceOfDeliveryDTO();
    private ModeOfPurchaseDTO mopObj = new ModeOfPurchaseDTO();
    private HeadOfAccountDTO hoaObj = new HeadOfAccountDTO();
    private DesignationDTO signingAuthorityObj = new DesignationDTO();
    private CategoryDTO categoryObj = new CategoryDTO();
    private GroupDTO groupObj = new GroupDTO();
    
    private String mappedGroupName;
    private String fileNo;
    
    
    private List<ItemDTO> itemDTOLi = new ArrayList<>();
    private String comments;
    private List<IndentLogDTO> indentLogLi = new ArrayList<>();
    
    private List<IndentFormFileDTO> indentFormFileLi = new ArrayList<>();
    private MultipartFile indentFile;
    private long fileNoFrMapp;
    
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
     * @return the indentNumber
     */
    public String getIndentNumber() {
        return indentNumber;
    }

    /**
     * @param indentNumber the indentNumber to set
     */
    public void setIndentNumber(String indentNumber) {
        this.indentNumber = indentNumber;
    }

    /**
     * @return the recevidedDate
     */
    public String getRecevidedDate() {
        return recevidedDate;
    }

    /**
     * @param recevidedDate the recevidedDate to set
     */
    public void setRecevidedDate(String recevidedDate) {
        this.recevidedDate = recevidedDate;
    }

    /**
     * @return the estimatedCost
     */
    public String getEstimatedCost() {
        return estimatedCost;
    }

    /**
     * @param estimatedCost the estimatedCost to set
     */
    public void setEstimatedCost(String estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    /**
     * @return the indentDate
     */
    public String getIndentDate() {
        return indentDate;
    }

    /**
     * @param indentDate the indentDate to set
     */
    public void setIndentDate(String indentDate) {
        this.indentDate = indentDate;
    }

    /**
     * @return the sectionId
     */
    public long getSectionId() {
        return sectionId;
    }

    /**
     * @param sectionId the sectionId to set
     */
    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * @return the approveAuthorityId
     */
    public long getApproveAuthorityId() {
        return approveAuthorityId;
    }

    /**
     * @param approveAuthorityId the approveAuthorityId to set
     */
    public void setApproveAuthorityId(long approveAuthorityId) {
        this.approveAuthorityId = approveAuthorityId;
    }

    /**
     * @return the itemId
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the employeeProfileId
     */
    public long getEmployeeProfileId() {
        return employeeProfileId;
    }

    /**
     * @param employeeProfileId the employeeProfileId to set
     */
    public void setEmployeeProfileId(long employeeProfileId) {
        this.employeeProfileId = employeeProfileId;
    }

    /**
     * @return the placeOfDeliveryId
     */
    public long getPlaceOfDeliveryId() {
        return placeOfDeliveryId;
    }

    /**
     * @param placeOfDeliveryId the placeOfDeliveryId to set
     */
    public void setPlaceOfDeliveryId(long placeOfDeliveryId) {
        this.placeOfDeliveryId = placeOfDeliveryId;
    }

    /**
     * @return the modeOfPurchaseId
     */
    public long getModeOfPurchaseId() {
        return modeOfPurchaseId;
    }

    /**
     * @param modeOfPurchaseId the modeOfPurchaseId to set
     */
    public void setModeOfPurchaseId(long modeOfPurchaseId) {
        this.modeOfPurchaseId = modeOfPurchaseId;
    }

    /**
     * @return the headOfAccountId
     */
    public long getHeadOfAccountId() {
        return headOfAccountId;
    }

    /**
     * @param headOfAccountId the headOfAccountId to set
     */
    public void setHeadOfAccountId(long headOfAccountId) {
        this.headOfAccountId = headOfAccountId;
    }

    /**
     * @return the descriptionDetails
     */
    public String getDescriptionDetails() {
        return descriptionDetails;
    }

    /**
     * @param descriptionDetails the descriptionDetails to set
     */
    public void setDescriptionDetails(String descriptionDetails) {
        this.descriptionDetails = descriptionDetails;
    }

    /**
     * @return the mta
     */
    public String getMta() {
        return mta;
    }

    /**
     * @param mta the mta to set
     */
    public void setMta(String mta) {
        this.mta = mta;
    }

    /**
     * @return the briefDescription
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     * @param briefDescription the briefDescription to set
     */
    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    
    /**
     * @return the otherInformation
     */
    public String getOtherInformation() {
        return otherInformation;
    }

    /**
     * @param otherInformation the otherInformation to set
     */
    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    /**
     * @return the signingAuthorityId
     */
    public long getSigningAuthorityId() {
        return signingAuthorityId;
    }

    /**
     * @param signingAuthorityId the signingAuthorityId to set
     */
    public void setSigningAuthorityId(long signingAuthorityId) {
        this.signingAuthorityId = signingAuthorityId;
    }

    /**
     * @return the sectionObj
     */
    public SectionDTO getSectionObj() {
        return sectionObj;
    }

    /**
     * @param sectionObj the sectionObj to set
     */
    public void setSectionObj(SectionDTO sectionObj) {
        this.sectionObj = sectionObj;
    }

    /**
     * @return the approvingAuthorityObj
     */
    public DesignationDTO getApprovingAuthorityObj() {
        return approvingAuthorityObj;
    }

    /**
     * @param approvingAuthorityObj the approvingAuthorityObj to set
     */
    public void setApprovingAuthorityObj(DesignationDTO approvingAuthorityObj) {
        this.approvingAuthorityObj = approvingAuthorityObj;
    }

    /**
     * @return the itemObj
     */
    public ItemDTO getItemObj() {
        return itemObj;
    }

    /**
     * @param itemObj the itemObj to set
     */
    public void setItemObj(ItemDTO itemObj) {
        this.itemObj = itemObj;
    }

    /**
     * @return the empProfileDTo
     */
    public EmployeeProfileDTO getEmpProfileDTo() {
        return empProfileDTo;
    }

    /**
     * @param empProfileDTo the empProfileDTo to set
     */
    public void setEmpProfileDTo(EmployeeProfileDTO empProfileDTo) {
        this.empProfileDTo = empProfileDTo;
    }

    /**
     * @return the podObj
     */
    public PlaceOfDeliveryDTO getPodObj() {
        return podObj;
    }

    /**
     * @param podObj the podObj to set
     */
    public void setPodObj(PlaceOfDeliveryDTO podObj) {
        this.podObj = podObj;
    }

    /**
     * @return the mopObj
     */
    public ModeOfPurchaseDTO getMopObj() {
        return mopObj;
    }

    /**
     * @param mopObj the mopObj to set
     */
    public void setMopObj(ModeOfPurchaseDTO mopObj) {
        this.mopObj = mopObj;
    }

    /**
     * @return the hoaObj
     */
    public HeadOfAccountDTO getHoaObj() {
        return hoaObj;
    }

    /**
     * @param hoaObj the hoaObj to set
     */
    public void setHoaObj(HeadOfAccountDTO hoaObj) {
        this.hoaObj = hoaObj;
    }

    /**
     * @return the signingAuthorityObj
     */
    public DesignationDTO getSigningAuthorityObj() {
        return signingAuthorityObj;
    }

    /**
     * @param signingAuthorityObj the signingAuthorityObj to set
     */
    public void setSigningAuthorityObj(DesignationDTO signingAuthorityObj) {
        this.signingAuthorityObj = signingAuthorityObj;
    }

    /**
     * @return the indentStatus
     */
    public String getIndentStatus() {
        return indentStatus;
    }

    /**
     * @param indentStatus the indentStatus to set
     */
    public void setIndentStatus(String indentStatus) {
        this.indentStatus = indentStatus;
    }

    /**
     * @return the mappedGroupName
     */
    public String getMappedGroupName() {
        return mappedGroupName;
    }

    /**
     * @param mappedGroupName the mappedGroupName to set
     */
    public void setMappedGroupName(String mappedGroupName) {
        this.mappedGroupName = mappedGroupName;
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
     * @return the itemCategoryId
     */
    public long getItemCategoryId() {
        return itemCategoryId;
    }

    /**
     * @param itemCategoryId the itemCategoryId to set
     */
    public void setItemCategoryId(long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    /**
     * @return the categoryObj
     */
    public CategoryDTO getCategoryObj() {
        return categoryObj;
    }

    /**
     * @param categoryObj the categoryObj to set
     */
    public void setCategoryObj(CategoryDTO categoryObj) {
        this.categoryObj = categoryObj;
    }

    /**
     * @return the itemDTOLi
     */
    public List<ItemDTO> getItemDTOLi() {
        return itemDTOLi;
    }

    /**
     * @param itemDTOLi the itemDTOLi to set
     */
    public void setItemDTOLi(List<ItemDTO> itemDTOLi) {
        this.itemDTOLi = itemDTOLi;
    }

    /**
     * @return the indentTypeCTQ
     */
    public String getIndentTypeCTQ() {
        return indentTypeCTQ;
    }

    /**
     * @param indentTypeCTQ the indentTypeCTQ to set
     */
    public void setIndentTypeCTQ(String indentTypeCTQ) {
        this.indentTypeCTQ = indentTypeCTQ;
    }

    /**
     * @return the indentTypeProp
     */
    public String getIndentTypeProp() {
        return indentTypeProp;
    }

    /**
     * @param indentTypeProp the indentTypeProp to set
     */
    public void setIndentTypeProp(String indentTypeProp) {
        this.indentTypeProp = indentTypeProp;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the groupObj
     */
    public GroupDTO getGroupObj() {
        return groupObj;
    }

    /**
     * @param groupObj the groupObj to set
     */
    public void setGroupObj(GroupDTO groupObj) {
        this.groupObj = groupObj;
    }

    /**
     * @return the indentLogLi
     */
    public List<IndentLogDTO> getIndentLogLi() {
        return indentLogLi;
    }

    /**
     * @param indentLogLi the indentLogLi to set
     */
    public void setIndentLogLi(List<IndentLogDTO> indentLogLi) {
        this.indentLogLi = indentLogLi;
    }
    
    private int hashCode;
    @Override
    public boolean equals(Object obj) {

        IndentFormDTO indentForm = (IndentFormDTO)obj;

        if(indentFormID == indentForm.indentFormID)
        {
            hashCode = indentForm.hashCode;
            return true;
        }else{
            hashCode = super.hashCode();
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (int) (this.indentFormID ^ (this.indentFormID >>> 32));
        hash = 11 * hash + this.hashCode;
        return hash;
    }

    /**
     * @return the indentFormFileLi
     */
    public List<IndentFormFileDTO> getIndentFormFileLi() {
        return indentFormFileLi;
    }

    /**
     * @param indentFormFileLi the indentFormFileLi to set
     */
    public void setIndentFormFileLi(List<IndentFormFileDTO> indentFormFileLi) {
        this.indentFormFileLi = indentFormFileLi;
    }

    /**
     * @return the indentFile
     */
    public MultipartFile getIndentFile() {
        return indentFile;
    }

    /**
     * @param indentFile the indentFile to set
     */
    public void setIndentFile(MultipartFile indentFile) {
        this.indentFile = indentFile;
    }

    public long getFileNoFrMapp() {
        return fileNoFrMapp;
    }

    public void setFileNoFrMapp(long fileNoFrMapp) {
        this.fileNoFrMapp = fileNoFrMapp;
    }
    
    
    
}
