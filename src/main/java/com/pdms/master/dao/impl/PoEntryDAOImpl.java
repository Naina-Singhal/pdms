/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.master.dao.impl;

import com.pdms.account.dto.DdNumberDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.InspectionClearanceDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.dto.MaterialReceiptDTO;
import com.pdms.dto.PlaceOfDeliveryDTO;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.dto.PoDetailsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.dto.UnitDTO;
import com.pdms.dto.VendorDTO;
import com.pdms.exception.AppException;
import com.pdms.itemsDto.receipt.MaterialReceiptItemsDTO;
import com.pdms.purchase.dto.PoTempDTO;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author myassessment
 */
@Repository
public class PoEntryDAOImpl {
    

    private static final Logger logger = Logger.getLogger(PoEntryDAOImpl.class);

    /*
    Start : Autowiring the required Class instances
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    @Autowired
    private EncryptDecrypt encryptDecrypt;
    
    @Autowired
    private DateUtil dateUtil;
    
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */
    public PoEntryDAOImpl(){
        
    }
    
    
    public int insertPoEntryDetail(final List<PoEntryDTO> unitObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO po_entry  ");
            sb.append("(poEntryID,tenderFN,venderName,quotationNo,referenceNo,venDescription,poNumber,");
            sb.append("poDate,price,payment,Inspection,poValue,placeOfDelivery,deliveryPeriod,");
            sb.append("signatory,preparedDate,preparedBy,securityDeposit,pBG,offer,specED,");
            sb.append("addST,freight,others,session_id,po_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(PoEntryDTO PEDTO : unitObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{PEDTO.getPoEntryID(),
                        PEDTO.getTenderFN().trim(),
                        PEDTO.getVenderName().trim(),
                        PEDTO.getQuotationNo().trim(),
                        PEDTO.getReferenceNo().trim(),
                        PEDTO.getVenDescription().trim(),
                        PEDTO.getPoNumber(),
                        PEDTO.getPoDate(),
                        PEDTO.getPrice().trim(),
                        PEDTO.getPayment().trim(),
                        PEDTO.getInspection().trim(),
                        PEDTO.getPoValue().trim(),
                        PEDTO.getPlaceOfDelivery().trim(),                        
                        dateUtil.dateConvertionFromJSPToDB(PEDTO.getDeliveryPeriod().trim()),
                        PEDTO.getSignatory().trim(),                        
                        dateUtil.dateConvertionFromJSPToDB(PEDTO.getPreparedDate().trim()),
                        PEDTO.getPreparedBy().trim(),                        
                        PEDTO.getSecurityDeposit().trim(),
                        PEDTO.getpBG().trim(),                        
                        PEDTO.getOffer().trim(),  
                        PEDTO.getSpecED().trim(),
                        PEDTO.getAddST().trim(),
                        PEDTO.getFreight().trim(),
                        PEDTO.getOthers().trim(),
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPoEntryDetail:",e);
        }
        return retVal;
    }
    
    public List<PoEntryDTO> getPoEntryDetails() throws AppException
    {
        List<PoEntryDTO> PoEntryLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM po_entry ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    PoEntryDTO poEntryDTO = new PoEntryDTO();
                    poEntryDTO.setPoEntryID((Long)(resultMap.get("poEntryID")));
                    poEntryDTO.setTenderFN((String)(resultMap.get("tenderFN")));
                    poEntryDTO.setVenderName((String)(resultMap.get("venderName")));
                    poEntryDTO.setQuotationNo((String)(resultMap.get("quotationNo")));
                    poEntryDTO.setReferenceNo((String)(resultMap.get("referenceNo")));
                    poEntryDTO.setVenDescription((String)(resultMap.get("venDescription")));
                    poEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    poEntryDTO.setPoDate((String)(resultMap.get("poDate")));
                    poEntryDTO.setPrice((String)(resultMap.get("price")));
                    poEntryDTO.setPayment((String)(resultMap.get("payment")));
                    poEntryDTO.setInspection((String)(resultMap.get("inspection")));
                    poEntryDTO.setPoValue((String)(resultMap.get("poValue")));
                    poEntryDTO.setPlaceOfDelivery((String)(resultMap.get("placeOfDelivery")));
                    String delPeriod = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("deliveryPeriod")));
                    poEntryDTO.setDeliveryPeriod((dateUtil.dateConvertionFromDBToJSP(delPeriod))); 
                    poEntryDTO.setSignatory((String)(resultMap.get("signatory")));
                    String preDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("preparedDate")));
                    poEntryDTO.setPreparedDate((dateUtil.dateConvertionFromDBToJSP(preDate))); 
                    poEntryDTO.setPreparedBy((String)(resultMap.get("preparedBy")));                    
                    poEntryDTO.setSecurityDeposit((String)(resultMap.get("securityDeposit")));
                    poEntryDTO.setpBG((String)(resultMap.get("pBG")));                    
                    poEntryDTO.setOffer((String)(resultMap.get("offer")));
                    poEntryDTO.setSpecED((String)(resultMap.get("specED")));                    
                    poEntryDTO.setAddST((String)(resultMap.get("addST")));                   
                    poEntryDTO.setFreight((String)(resultMap.get("freight")));
                    poEntryDTO.setOthers((String)(resultMap.get("others"))); 
                    poEntryDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(poEntryDTO.getPoEntryID()+""));
                    
                    PoEntryLi.add(poEntryDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoEntryDetails:",e);
        }
        return PoEntryLi;
    }
    
    public long getPoNoForIncrement() throws AppException
    {        
        long maxNo = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * FROM po_entry WHERE poNumber=(SELECT MAX(poNumber) FROM po_entry) ");
            //sb.append(" FROM po_entry ");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {                    
                   
                   PoEntryDTO entryDTO = new PoEntryDTO();                    
                    entryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    maxNo = (long)(resultMap.get("poNumber")); 
                }
            }
            maxNo++;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoNoForIncrement:",e);
        }
        return maxNo;
    }
    
    public List<VendorDTO> getVendorDetails(final String vendor_name) throws AppException {
        List<VendorDTO> vendorLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM vendor_details WHERE vendor_code ='"+vendor_name+"' ");
            

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    VendorDTO vendorObj = new VendorDTO();

                    vendorObj.setVendorID((Long) (retMap.get("pk_vendor_id")));
                    vendorObj.setEncVendorID(encryptDecrypt.encrypt(vendorObj.getVendorID() + ""));
                    vendorObj.setVendorCode((String) (retMap.get("vendor_code")));
                    vendorObj.setVendorName((String) (retMap.get("vendor_name")));
                    vendorObj.setVendorAddress((String) (retMap.get("vendor_address")));
                    vendorObj.setVendorCity((String) (retMap.get("vendor_city")));
                    vendorObj.setVendorPin((String) (retMap.get("vendor_pin")));
                    vendorObj.setVendorPhone((String) (retMap.get("vendor_phone")));
                    vendorObj.setVendorFax((String) (retMap.get("vendor_fax")));
                    vendorObj.setVendorEmail((String) (retMap.get("vendor_email")));
                    vendorObj.setRegistrationType((String) (retMap.get("registration_type")));
                    vendorObj.setExpiraryDate((String) (retMap.get("expiry_date")));
                    vendorObj.setVendorPan((String) (retMap.get("vendor_pan")));
                    vendorObj.setVendorRating(((Long) (retMap.get("vendor_rating"))) + "");
                    vendorObj.setSource((String) (retMap.get("source")));
                    vendorObj.setComments((String) (retMap.get("comments")));

                    vendorLi.add(vendorObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getVendorDetails:", e);
        }
        return vendorLi;
    }
    
    public List<PoEntryDTO> getPoEntryDeByFileNo(final int PoNumber) throws AppException
    {
        List<PoEntryDTO> PoEntryLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT * ");
            sb.append(" FROM po_entry WHERE poNumber ='"+PoNumber+"'");
            
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString());
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    PoEntryDTO poEntryDTO = new PoEntryDTO();
                    poEntryDTO.setPoEntryID((Long)(resultMap.get("poEntryID")));
                    poEntryDTO.setTenderFN((String)(resultMap.get("tenderFN")));
                    poEntryDTO.setVenderName((String)(resultMap.get("venderName")));
                    poEntryDTO.setQuotationNo((String)(resultMap.get("quotationNo")));
                    poEntryDTO.setReferenceNo((String)(resultMap.get("referenceNo")));
                    poEntryDTO.setVenDescription((String)(resultMap.get("venDescription")));
                    poEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    poEntryDTO.setPoDate((String)(resultMap.get("poDate")));
                    poEntryDTO.setPrice((String)(resultMap.get("price")));
                    poEntryDTO.setPayment((String)(resultMap.get("payment")));
                    poEntryDTO.setInspection((String)(resultMap.get("inspection")));
                    poEntryDTO.setPoValue((String)(resultMap.get("poValue")));
                    poEntryDTO.setPlaceOfDelivery((String)(resultMap.get("placeOfDelivery")));
                    String delPeriod = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("deliveryPeriod")));
                    poEntryDTO.setDeliveryPeriod((dateUtil.dateConvertionFromDBToJSP(delPeriod)));
                    poEntryDTO.setSignatory((String)(resultMap.get("signatory")));                    
                    String preDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("preparedDate")));
                    poEntryDTO.setPreparedDate((dateUtil.dateConvertionFromDBToJSP(preDate))); 
                    poEntryDTO.setPreparedBy((String)(resultMap.get("preparedBy")));                    
                    poEntryDTO.setSecurityDeposit((String)(resultMap.get("securityDeposit")));
                    poEntryDTO.setpBG((String)(resultMap.get("pBG")));                    
                    poEntryDTO.setOffer((String)(resultMap.get("offer")));
                    poEntryDTO.setSpecED((String)(resultMap.get("specED")));
                    poEntryDTO.setAddST((String)(resultMap.get("addST")));
                    poEntryDTO.setFreight((String)(resultMap.get("freight")));
                    poEntryDTO.setOthers((String)(resultMap.get("others"))); 
                    poEntryDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(poEntryDTO.getPoEntryID()+""));
                    
                    PoEntryLi.add(poEntryDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoEntryDeByFileNo:",e);
        }
        return PoEntryLi;
    }
    
    public List<PublicTenderItemsDTO> getPublicTenderDeFrPo(final long fiNum) throws AppException {
        List<PublicTenderItemsDTO> tenderLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_public_tender_item_id,indentSlNo, ");
            sb.append("fk_item_id,fk_category_id,item_qty,item_units FROM public_tender_item_details WHERE fk_public_tender_id="+fiNum);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());
                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PublicTenderItemsDTO tenObj = new PublicTenderItemsDTO();

                    tenObj.setpTenderItemID((Long) (retMap.get("pk_public_tender_item_id"))); 
                    tenObj.getItemObj().setIndentSlNo((Long)(retMap.get("indentSlNo"))); 
                    tenObj.getItemObj().setItemID((Long)(retMap.get("fk_item_id")));  
                    tenObj.getItemObj().getCategoryDTO().setCategoryID((Long) (retMap.get("fk_category_id")));
                    tenObj.setItemQty((Long) (retMap.get("item_qty"))); 
                    tenObj.setItemUnits((String) (retMap.get("item_units"))); 
                    tenderLi.add(tenObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPublicTenderDeFrPo:", e);
        }
        return tenderLi;
    }
    
    public List<PoEntryDTO> getFileNoFrTenByPoNo(final long poNum) throws AppException {
        List<PoEntryDTO> tenderLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT tenderFN ");
            sb.append(" FROM po_entry WHERE poNumber ="+poNum);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PoEntryDTO tenObj = new PoEntryDTO();
                    tenObj.setTenderFN((String) (retMap.get("tenderFN")));                    
                    tenderLi.add(tenObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getFileNoFrTenByPoNo:", e);
        }
        return tenderLi;
    }
    
    public List<ItemDTO> getItemMaDeByItemNo(final long itemNO) throws AppException {
        List<ItemDTO> itemLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_item_id,fk_unit_id,item_name,item_code,description ");
            sb.append(" FROM item_master WHERE pk_item_id ="+itemNO);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    ItemDTO iObj = new ItemDTO();
                    iObj.setItemID((Long) (retMap.get("pk_item_id")));   
                    iObj.getUnitDTO().setUnitID((Long) (retMap.get("fk_unit_id"))); 
                    iObj.setItemName((String) (retMap.get("item_name"))); 
                    iObj.setItemCode((String) (retMap.get("item_code"))); 
                    iObj.setDescription((String) (retMap.get("description"))); 
                    itemLi.add(iObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getItemMaDeByItemNo:", e);
        }
        return itemLi;
    }
    
    public List<UnitDTO> getUnitDeByUnitNumber(final long unitNO) throws AppException {
        List<UnitDTO> unitLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_unit_id,unit_name,unit_code,description ");
            sb.append(" FROM unit_master WHERE pk_unit_id ="+unitNO);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    UnitDTO uObj = new UnitDTO();
                    uObj.setUnitID((Long) (retMap.get("pk_unit_id")));   
                    uObj.setUnitName((String) (retMap.get("unit_name"))); 
                    uObj.setUnitCode((String) (retMap.get("unit_code"))); 
                    uObj.setUnitDescription((String) (retMap.get("description"))); 
                    unitLi.add(uObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getUnitDeByUnitNumber:", e);
        }
        return unitLi;
    }
    
    public int insertPoEntryDetailOne(final List<PoDetailsDTO> deObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO po_details_one  ");
            sb.append("(poDetailsId,poNumInPo,fileNo,desCode,noteDes,");           
            sb.append("session_id,po_date) ");
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?)");
            
            for(PoDetailsDTO poDto : deObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{poDto.getPoDetailsId(),
                        poDto.getPoNumInPo(),
                        poDto.getFileNo(),
                        poDto.getDesCode().trim(),
                        poDto.getNoteDes().trim(),                        
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertPoEntryDetailOne:",e);
        }
        return retVal;
    }
    
    public int insertPoEntryItemsDe(final PoTempDTO itObj, final long sessUserID) throws AppException {
        try {
            return getTransactionTemplate().execute(new TransactionCallback<Integer>() {
                @Override
                public Integer doInTransaction(TransactionStatus transactionStatus) {

                    int retVal = 0;
                    int retValForm = 0;
                    int retvalItems = 0;
                    try {
                        
                        StringBuilder sb = new StringBuilder();
                        sb.append(" INSERT INTO po_details_one  ");
                        sb.append("(poDetailsId,poNumInPo,fileNo,desCode,noteDes,");
                        sb.append("session_id,po_date) ");
                        sb.append(" VALUES ");
                        sb.append("(?,?,?,?,?,?,?)");

                        List<PoDetailsDTO> poObj = itObj.getPoObj();
                        for (PoDetailsDTO poDto : poObj) {
                            retValForm = getJdbcTemplate().update(sb.toString(),
                                    new Object[]{poDto.getPoDetailsId(),
                                        poDto.getPoNumInPo(),
                                        poDto.getFileNo(),
                                        poDto.getDesCode().trim(),
                                        poDto.getNoteDes().trim(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        StringBuilder sbItem = new StringBuilder();
                        sbItem.append(" INSERT INTO po_entry_items_details  ");
                        sbItem.append("(poDeItemsId,fpoNumber,indentslno,itemCode,quantity,unit,rate,frieght,pfCharges,salesTax,gst,discount,");
                        sbItem.append("total,session_id,cur_date) ");
                        sbItem.append(" VALUES ");
                        sbItem.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        
                        List<PoDeItemsDTO> poIteObj = itObj.getPoItemsObj();
                        for (PoDeItemsDTO poDto : poIteObj) {
                            retvalItems = getJdbcTemplate().update(sbItem.toString(),
                                    new Object[]{poDto.getPoDeItemsId(),
                                        poDto.getPoNum(),
                                        poDto.getIndentslno(),
                                        poDto.getItemCode().trim(),
                                        poDto.getQuantity().trim(),
                                        poDto.getUnit().trim(),
                                        poDto.getRate(),
                                        poDto.getFrieght(),
                                        poDto.getPfCharges(),
                                        poDto.getSalesTax().trim(),
                                        poDto.getGst().trim(),
                                        poDto.getDiscount(),
                                        poDto.getTotal(),
                                        sessUserID,
                                        dateUtil.generateDBCurrentDateTime()
                                    });
                        }

                        

                        if ((retValForm > 0) && (retvalItems > 0)) {
                            retVal = retvalItems;
                        } else {
                            transactionStatus.setRollbackOnly();
                            retVal = -5;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("Exception Occured In:insertPoEntryItemsDe");
                        transactionStatus.setRollbackOnly();
                        return -1;
                    }
                    return retVal;

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occured while Executing the "
                    + "insertPoEntryItemsDe:: " + e.getMessage());
        }
    }
    
    public long getTenderIdByFileNumber(final long fiNO) throws AppException {
        long fileno = 0;
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_public_tender_id ");
            sb.append(" FROM public_tender_details WHERE file_no =").append(fiNO);

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {                   
                    fileno = ((Long)(retMap.get("pk_public_tender_id"))); 
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getTenderIdByFileNumber:", e);
        }
        return fileno;
    }
    
    public List<PublicTenderItemsDTO> getBreifDesFrPubTender() throws AppException {
        List<PublicTenderItemsDTO> pubLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_public_tender_item_id,brief_description ");
            sb.append(" FROM public_tender_item_details ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    PublicTenderItemsDTO uObj = new PublicTenderItemsDTO();  
                    uObj.setpTenderItemID((Long) (retMap.get("pk_public_tender_item_id")));
                    uObj.setBreifDesc((String) (retMap.get("brief_description")));                     
                    pubLi.add(uObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBreifDesFrPubTender:", e);
        }
        return pubLi;
    }
    
     public List<IndentFormDTO> getBreifDesFrIndentForm() throws AppException {
        List<IndentFormDTO> BriLi = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT pk_indent_form_id,description_details,brief_description ");
            sb.append(" FROM indent_form ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString());                    

            if (!resList.isEmpty()) {

                for (Map<String, Object> retMap : resList) {
                    IndentFormDTO uObj = new IndentFormDTO();  
                    uObj.setIndentFormID((Long) (retMap.get("pk_indent_form_id")));
                    uObj.setDescriptionDetails((String) (retMap.get("description_details")));  
                    uObj.setBriefDescription((String) (retMap.get("brief_description"))); 
                    BriLi.add(uObj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getBreifDesFrIndentForm:", e);
        }
        return BriLi;
    }
    
    
    public List<PoEntryDTO> getPoEntryDeByFileNumber(final int fileNumber) throws AppException
    {
        List<PoEntryDTO> PoEntryLi = new LinkedList<>();
        try
        {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT a.poEntryID,a.venderName,a.venDescription,a.poNumber,a.referenceNo,a.poDate,a.placeOfDelivery, ");
            sb.append(" a.preparedDate,a.poValue,a.deliveryPeriod,v.vendor_name,v.contact_person,v.vendor_address, ");
            sb.append(" v.vendor_city,v.vendor_phone FROM po_entry a");
            sb.append(" LEFT JOIN vendor_details v ON a.venderName = v.vendor_code ");
            sb.append(" WHERE tenderFN =? ");
            List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(sb.toString(),
                    new Object[]{fileNumber});
            
            if(!resultList.isEmpty())
            {
                for (Map<String, Object> resultMap : resultList) {
                    
                    PoEntryDTO poEntryDTO = new PoEntryDTO();
                    poEntryDTO.setPoEntryID((Long)(resultMap.get("poEntryID")));
                    poEntryDTO.setVenderName((String)(resultMap.get("venderName")));
                    poEntryDTO.setVenDescription((String)(resultMap.get("venDescription")));
                    poEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    poEntryDTO.setReferenceNo((String)(resultMap.get("referenceNo")));
                    poEntryDTO.setPoDate((String)(resultMap.get("poDate")));
                    poEntryDTO.setPlaceOfDelivery((String)(resultMap.get("placeOfDelivery")));
                    String preDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("preparedDate")));
                    poEntryDTO.setPreparedDate((dateUtil.dateConvertionFromDBToJSP(preDate))); 
                    poEntryDTO.setPoValue((String)(resultMap.get("poValue")));
                    String delPeriod = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("deliveryPeriod")));
                    poEntryDTO.setDeliveryPeriod((dateUtil.dateConvertionFromDBToJSP(delPeriod)));
                    poEntryDTO.getVendorObj().setVendorName((String)(resultMap.get("vendor_name")));
                    poEntryDTO.getVendorObj().setContactPerson((String)(resultMap.get("contact_person")));
                    poEntryDTO.getVendorObj().setVendorAddress((String)(resultMap.get("vendor_address")));
                    poEntryDTO.getVendorObj().setVendorCity((String)(resultMap.get("vendor_city")));
                    poEntryDTO.getVendorObj().setVendorPhone((String)(resultMap.get("vendor_phone")));
                    PoEntryLi.add(poEntryDTO);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoEntryDeByFileNumber:",e);
        }
        return PoEntryLi;
    }
    
    
    public List<PoEntryDTO> getPoEntryReById(final long id) throws AppException {
        List<PoEntryDTO> ddDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM po_entry WHERE poEntryID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    PoEntryDTO poEntryDTO = new PoEntryDTO();
                    poEntryDTO.setPoEntryID((Long)(resultMap.get("poEntryID")));
                    poEntryDTO.setTenderFN((String)(resultMap.get("tenderFN")));
                    poEntryDTO.setVenderName((String)(resultMap.get("venderName")));
                    poEntryDTO.setQuotationNo((String)(resultMap.get("quotationNo")));
                    poEntryDTO.setReferenceNo((String)(resultMap.get("referenceNo")));
                    poEntryDTO.setVenDescription((String)(resultMap.get("venDescription")));
                    poEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    poEntryDTO.setPoDate((String)(resultMap.get("poDate")));
                    poEntryDTO.setPrice((String)(resultMap.get("price")));
                    poEntryDTO.setPayment((String)(resultMap.get("payment")));
                    poEntryDTO.setInspection((String)(resultMap.get("inspection")));
                    poEntryDTO.setPoValue((String)(resultMap.get("poValue")));
                    poEntryDTO.setPlaceOfDelivery((String)(resultMap.get("placeOfDelivery")));
                    String delPeriod = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("deliveryPeriod")));
                    poEntryDTO.setDeliveryPeriod((dateUtil.dateConvertionFromDBToJSP(delPeriod)));
                    poEntryDTO.setSignatory((String)(resultMap.get("signatory")));
                    String preDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("preparedDate")));
                    poEntryDTO.setPreparedDate((dateUtil.dateConvertionFromDBToJSP(preDate))); 
                    poEntryDTO.setPreparedBy((String)(resultMap.get("preparedBy")));                    
                    poEntryDTO.setSecurityDeposit((String)(resultMap.get("securityDeposit")));
                    poEntryDTO.setpBG((String)(resultMap.get("pBG")));                    
                    poEntryDTO.setOffer((String)(resultMap.get("offer")));
                    poEntryDTO.setSpecED((String)(resultMap.get("specED")));                    
                    poEntryDTO.setAddST((String)(resultMap.get("addST")));                   
                    poEntryDTO.setFreight((String)(resultMap.get("freight")));
                    poEntryDTO.setOthers((String)(resultMap.get("others"))); 
                    poEntryDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(poEntryDTO.getPoEntryID()+""));
                    
                    ddDTO.add(poEntryDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoEntryReById:", e);
        }
        return ddDTO;
    }
    
    public int updatePoEntryDetail(final List<PoEntryDTO> unitObj, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" UPDATE po_entry  ");
            sb.append(" SET tenderFN=?,venderName=?,quotationNo=?,referenceNo=?,venDescription=?,poNumber=?,poDate=?,price=?,");  
            sb.append("payment=?,inspection=?,poValue=?,placeOfDelivery=?,deliveryPeriod=?,signatory=?,preparedDate=?,preparedBy=?, ");
            sb.append("securityDeposit=?,pBG=?,offer=?,specED=?,addST=?,freight=?,others=?,session_id=?, ");
            sb.append(" po_date=?  WHERE poEntryID=? ");
            
            for(PoEntryDTO PEDTO : unitObj) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{                       
                        PEDTO.getTenderFN().trim(),
                        PEDTO.getVenderName().trim(),
                        PEDTO.getQuotationNo().trim(),
                        PEDTO.getReferenceNo().trim(),
                        PEDTO.getVenDescription().trim(),
                        PEDTO.getPoNumber(),
                        PEDTO.getPoDate(),
                        PEDTO.getPrice().trim(),
                        PEDTO.getPayment().trim(),
                        PEDTO.getInspection().trim(),
                        PEDTO.getPoValue().trim(),
                        PEDTO.getPlaceOfDelivery().trim(),
                        PEDTO.getDeliveryPeriod().trim(),
                        dateUtil.dateConvertionFromJSPToDB(PEDTO.getDeliveryPeriod().trim()),
                        PEDTO.getSignatory().trim(),
                        dateUtil.dateConvertionFromJSPToDB(PEDTO.getPreparedDate().trim()),
                        PEDTO.getPreparedBy().trim(),                        
                        PEDTO.getSecurityDeposit().trim(),
                        PEDTO.getpBG().trim(),                        
                        PEDTO.getOffer().trim(),  
                        PEDTO.getSpecED().trim(),
                        PEDTO.getAddST().trim(),
                        PEDTO.getFreight().trim(),
                        PEDTO.getOthers().trim(),                       
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime(),
                        PEDTO.getPoEntryID()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : updatePoEntryDetail:",e);
        }
        return retVal;
    }
    
    public List<PoDetailsDTO> getPoNoteDesByPoNo(final long pono) throws AppException {
        List<PoDetailsDTO> poNote = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();
            
            sb.append(" SELECT noteDes ");
            sb.append(" FROM po_details_one ");           
            sb.append(" WHERE poNumInPo =? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{pono});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    PoDetailsDTO poNoteDes = new PoDetailsDTO();           
                    poNoteDes.setNoteDes((String)(resultMap.get("noteDes")));                    
                    poNote.add(poNoteDes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoNoteDesByPoNo:", e);
        }
        return poNote;
    }
    
    public List<PoEntryDTO> getPoEntryReByIdFrPdf(final long id) throws AppException {
        List<PoEntryDTO> ddDTO = new LinkedList<>();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT  a.tenderFN,a.venderName,a.quotationNo,a.referenceNo,a.venDescription,a.poNumber,a.poDate,  ");
            sb.append(" a.price,a.payment,a.inspection,a.poValue,a.placeOfDelivery,a.deliveryPeriod,a.signatory,");
            sb.append(" a.preparedDate,a.preparedBy,a.securityDeposit,a.pBG,a.offer,a.specED,a.addST,a.freight,a.others,");
            sb.append(" v.vendor_name,v.contact_person,v.vendor_address,v.vendor_city,v.vendor_pin,v.vendor_phone,v.vendor_fax,");
            sb.append(" v.vendor_email,v.registration_type,v.expiry_date,v.gstinNumber, ");
            sb.append(" s.signatoryCode,s.signatoryName,s.signatoryDes ");
            sb.append(" FROM po_entry a ");
            sb.append(" JOIN vendor_details v ON a.venderName = v.vendor_code ");
            sb.append(" JOIN signatory_details s ON a.signatory = s.signatoryCode ");
            sb.append(" WHERE a.poEntryID=? ");

            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{id});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    PoEntryDTO poEntryDTO = new PoEntryDTO();                    
                    poEntryDTO.setTenderFN((String)(resultMap.get("tenderFN")));
                    poEntryDTO.setVenderName((String)(resultMap.get("venderName")));
                    poEntryDTO.setQuotationNo((String)(resultMap.get("quotationNo")));
                    poEntryDTO.setReferenceNo((String)(resultMap.get("referenceNo")));
                    poEntryDTO.setVenDescription((String)(resultMap.get("venDescription")));
                    poEntryDTO.setPoNumber((long)(resultMap.get("poNumber")));
                    poEntryDTO.setPoDate((String)(resultMap.get("poDate")));
                    poEntryDTO.setPrice((String)(resultMap.get("price")));
                    poEntryDTO.setPayment((String)(resultMap.get("payment")));
                    poEntryDTO.setInspection((String)(resultMap.get("inspection")));
                    poEntryDTO.setPoValue((String)(resultMap.get("poValue")));
                    poEntryDTO.setPlaceOfDelivery((String)(resultMap.get("placeOfDelivery")));
                    String delPeriod = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("deliveryPeriod")));
                    poEntryDTO.setDeliveryPeriod((dateUtil.dateConvertionFromDBToJSP(delPeriod)));
                    poEntryDTO.setSignatory((String)(resultMap.get("signatory")));
                    String preDate = dateUtil.convertTimestampToString((Timestamp) (resultMap.get("preparedDate")));
                    poEntryDTO.setPreparedDate((dateUtil.dateConvertionFromDBToJSP(preDate))); 
                    poEntryDTO.setPreparedBy((String)(resultMap.get("preparedBy")));                    
                    poEntryDTO.setSecurityDeposit((String)(resultMap.get("securityDeposit")));
                    poEntryDTO.setpBG((String)(resultMap.get("pBG")));                    
                    poEntryDTO.setOffer((String)(resultMap.get("offer")));
                    poEntryDTO.setSpecED((String)(resultMap.get("specED")));                    
                    poEntryDTO.setAddST((String)(resultMap.get("addST")));                   
                    poEntryDTO.setFreight((String)(resultMap.get("freight")));
                    poEntryDTO.setOthers((String)(resultMap.get("others"))); 
                    
                    poEntryDTO.getVendorObj().setVendorName((String)(resultMap.get("vendor_name"))); 
                    poEntryDTO.getVendorObj().setContactPerson((String)(resultMap.get("contact_person")));
                    poEntryDTO.getVendorObj().setVendorAddress((String)(resultMap.get("vendor_address")));
                    poEntryDTO.getVendorObj().setVendorCity((String)(resultMap.get("vendor_city")));
                    poEntryDTO.getVendorObj().setVendorPin((String)(resultMap.get("vendor_pin")));
                    poEntryDTO.getVendorObj().setVendorPhone((String)(resultMap.get("vendor_phone")));
                    poEntryDTO.getVendorObj().setVendorFax((String)(resultMap.get("vendor_fax")));
                    poEntryDTO.getVendorObj().setVendorEmail((String)(resultMap.get("vendor_email")));
                    poEntryDTO.getVendorObj().setRegistrationType((String)(resultMap.get("registration_type")));
                    poEntryDTO.getVendorObj().setExpiraryDate((String)(resultMap.get("expiry_date")));
                    poEntryDTO.getVendorObj().setGstinNumber((String)(resultMap.get("gstinNumber")));
                    
                    poEntryDTO.getSignObj().setSignatoryCode((String)(resultMap.get("signatoryCode")));
                    poEntryDTO.getSignObj().setSignatoryName((String)(resultMap.get("signatoryName")));
                    poEntryDTO.getSignObj().setSignatoryDes((String)(resultMap.get("signatoryDes")));
                     
                    poEntryDTO.setEncFieldValue
                            (encryptDecrypt.encrypt(poEntryDTO.getPoEntryID()+""));
                    
                    ddDTO.add(poEntryDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoEntryReByIdFrPdf:", e);
        }
        return ddDTO;
    }
    
     public List<PoEntryDTO> getPoFifteenDaysRecord(final String datePeriod) throws AppException {
        List<PoEntryDTO> ddDTO = new LinkedList<>();
        try {
            String datePeriodDb = dateUtil.dateConvertionFromJSPToDB(datePeriod);
            StringBuilder sb = new StringBuilder();

            sb.append(" SELECT * FROM po_entry WHERE deliveryPeriod between DATE_SUB( CURDATE() , INTERVAL 15 DAY ) ");
            sb.append(" AND CURDATE() ");
            List<Map<String, Object>> resList = getJdbcTemplate().queryForList(sb.toString(), 
                    new Object[]{datePeriodDb});
            if (!resList.isEmpty()) {

                for (Map<String, Object> resultMap : resList) {
                    PoEntryDTO poEntryDTO = new PoEntryDTO();
                    poEntryDTO.setPoEntryID((Long)(resultMap.get("poEntryID")));
                    poEntryDTO.setTenderFN((String)(resultMap.get("tenderFN")));
                    poEntryDTO.setVenderName((String)(resultMap.get("venderName")));
                    ddDTO.add(poEntryDTO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Exception Occurred : getPoEntryReById:", e);
        }
        return ddDTO;
    }
    
}
