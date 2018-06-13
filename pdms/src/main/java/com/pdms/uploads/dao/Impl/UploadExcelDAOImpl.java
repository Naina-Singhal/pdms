/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.uploads.dao.Impl;

import com.pdms.dto.CSRVpreparationDTO;
import com.pdms.exception.AppException;
import com.pdms.master.dao.impl.PoEntryDAOImpl;
import com.pdms.uploads.dto.ExcelUploadDTO;
import com.pdms.uploads.service.Impl.UploadExcelServiceImpl;
import com.pdms.utils.DateUtil;
import com.pdms.utils.EncryptDecrypt;
import java.util.ArrayList;
import java.util.List;
import javax.mail.Multipart;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author STEINMETZ
 */
@Repository
public class UploadExcelDAOImpl {
    
    
    private static final Logger logger = Logger.getLogger(UploadExcelDAOImpl.class);

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
    private UploadExcelServiceImpl uploadExcelServiceImpl;
    
    /*
    End : Autowiring the required Class instances
     */
    
    /*
    Default Constructor 
     */

    public UploadExcelDAOImpl(){
        
    }
    
    public int insertUploadExcel(List<ExcelUploadDTO> model, final long sessUserID) throws AppException
    {
        int retVal = 0;
        try
        {
            StringBuilder sb = new StringBuilder();
            sb.append(" INSERT INTO  upload_excel_item_file ");
            sb.append("(id,fileNumber,vendorCode,srNo,itemCategory,itemDescription,qty,unit,customExciseDuty,");
            sb.append("exciseDuty,octroiEntryTax,currencySelected,natureOfPrice,partNoModelNo,basicCost,discount,subTotalBPD,");
            sb.append("packForwaCharges,gst,freightCharges,instalCommiCharges,trainingCharges,inspecTestCharges,gstOnITITCharges,anyOtherCharges,");
            sb.append("evaluatedCost,totEvaluatedCost,totEvaluaCostWords,hSNCode,");            
            sb.append("mRp,session_user_id,ief_date) ");            
            sb.append(" VALUES ");
            sb.append("(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            for(ExcelUploadDTO CSDTO : model) {
                retVal = getJdbcTemplate().update(sb.toString(),
                    new Object[]{CSDTO.getId(),                        
                        CSDTO.getFileNumber().trim(),
                        CSDTO.getVendorCode().trim(),
                        CSDTO.getSrNo().trim(),
                        CSDTO.getItemCategory().trim(),
                        CSDTO.getItemDescription().trim(),
                        CSDTO.getQty().trim(),
                        CSDTO.getUnit().trim(),
                        CSDTO.getCustomExciseDuty().trim(),
                        CSDTO.getExciseDuty().trim(),
                        CSDTO.getOctroiEntryTax().trim(),
                        CSDTO.getCurrencySelected().trim(),
                        CSDTO.getNatureOfPrice().trim(),
                        CSDTO.getPartNoModelNo().trim(),
                        CSDTO.getBasicCost().trim(),
                        CSDTO.getDiscount().trim(),
                        CSDTO.getSubTotalBPD().trim(),
                        CSDTO.getPackForwaCharges().trim(),
                        CSDTO.getGst().trim(),
                        CSDTO.getFreightCharges().trim(),
                        CSDTO.getInstalCommiCharges().trim(),
                        CSDTO.getTrainingCharges().trim(),
                        CSDTO.getInspecTestCharges().trim(),
                        CSDTO.getGstOnITITCharges().trim(),
                        CSDTO.getAnyOtherCharges().trim(),
                        CSDTO.getEvaluatedCost().trim(),
                        CSDTO.getTotEvaluatedCost().trim(),
                        CSDTO.getTotEvaluaCostWords().trim(),
                        CSDTO.getHsnCode().trim(),                         
                        CSDTO.getMrp().trim(),                        
                       // ApplicationConstants.ACTIVE_FLAG_VALUE,
                        sessUserID,
                        dateUtil.generateDBCurrentDateTime()
                    });
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new AppException("Exception Occurred : insertUnitDetail:",e);
        }
        return retVal;
    }
    
    public int getUploadExcelList(MultipartFile file, final long sessUserID) throws AppException
    {
        List<ExcelUploadDTO> lstUser = new ArrayList<>();
        int retValue = 0;
        try {
			
                  DataFormatter formatter = new DataFormatter();
			int i = 1;
			// Creates a workbook object from the uploaded excelfile
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			// Creates a worksheet object representing the first sheet
			HSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the UserInfo Model
				ExcelUploadDTO user = new ExcelUploadDTO();
				// Creates an object representing a single row in excel
				HSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				user.setFileNumber(formatter.formatCellValue(row.getCell(0)));
                                user.setVendorCode(formatter.formatCellValue(row.getCell(1)));
                                user.setSrNo(formatter.formatCellValue(row.getCell(2)));
                                user.setItemCategory(formatter.formatCellValue(row.getCell(3)));
                                user.setItemDescription(formatter.formatCellValue(row.getCell(4)));
                                user.setQty(formatter.formatCellValue(row.getCell(5)));
                                user.setUnit(formatter.formatCellValue(row.getCell(6)));
                                user.setCustomExciseDuty(formatter.formatCellValue(row.getCell(7)));
                                user.setExciseDuty(formatter.formatCellValue(row.getCell(8)));
                                user.setOctroiEntryTax(formatter.formatCellValue(row.getCell(9)));
                                user.setCurrencySelected(formatter.formatCellValue(row.getCell(10)));
                                user.setNatureOfPrice(formatter.formatCellValue(row.getCell(11)));
                                user.setPartNoModelNo(formatter.formatCellValue(row.getCell(12)));
                                user.setBasicCost(formatter.formatCellValue(row.getCell(13)));
                                user.setDiscount(formatter.formatCellValue(row.getCell(14)));
                                user.setSubTotalBPD(formatter.formatCellValue(row.getCell(15)));
                                user.setPackForwaCharges(formatter.formatCellValue(row.getCell(16)));
                                user.setGst(formatter.formatCellValue(row.getCell(17)));
                                user.setFreightCharges(formatter.formatCellValue(row.getCell(18)));
                                user.setInstalCommiCharges(formatter.formatCellValue(row.getCell(19)));
                                user.setTrainingCharges(formatter.formatCellValue(row.getCell(20)));
                                user.setInspecTestCharges(formatter.formatCellValue(row.getCell(21)));
                                user.setGstOnITITCharges(formatter.formatCellValue(row.getCell(22)));
                                user.setAnyOtherCharges(formatter.formatCellValue(row.getCell(23)));
                                user.setEvaluatedCost(formatter.formatCellValue(row.getCell(24)));
                                user.setTotEvaluatedCost(formatter.formatCellValue(row.getCell(25)));
                                user.setTotEvaluaCostWords(formatter.formatCellValue(row.getCell(26)));
                                user.setHsnCode(formatter.formatCellValue(row.getCell(27)));
                                user.setMrp(formatter.formatCellValue(row.getCell(28)));
				// persist data into database in here
				lstUser.add(user);
			}			
			//workbook.close();                        
                   retValue =  uploadExcelServiceImpl.insertUploadExcel(lstUser, sessUserID);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return retValue;
    }
    
    public int getUploadExcelXlsxList(MultipartFile file, final long sessUserID) throws AppException
    {
        List<ExcelUploadDTO> lstUser = new ArrayList<>();
        int retValue = 0;
        try {			
                  DataFormatter formatter = new DataFormatter();
			int i = 1;
			// Creates a workbook object from the uploaded excelfile
			//HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
                        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			// Creates a worksheet object representing the first sheet
			//HSSFSheet worksheet = workbook.getSheetAt(0);
                        XSSFSheet worksheet = workbook.getSheetAt(0);
			// Reads the data in excel file until last row is encountered
			while (i <= worksheet.getLastRowNum()) {
				// Creates an object for the UserInfo Model
				ExcelUploadDTO user = new ExcelUploadDTO();
				// Creates an object representing a single row in excel
				XSSFRow row = worksheet.getRow(i++);
				// Sets the Read data to the model class
				user.setFileNumber(formatter.formatCellValue(row.getCell(0)));
                                user.setVendorCode(formatter.formatCellValue(row.getCell(1)));
                                user.setSrNo(formatter.formatCellValue(row.getCell(2)));
                                user.setItemCategory(formatter.formatCellValue(row.getCell(3)));
                                user.setItemDescription(formatter.formatCellValue(row.getCell(4)));
                                user.setQty(formatter.formatCellValue(row.getCell(5)));
                                user.setUnit(formatter.formatCellValue(row.getCell(6)));
                                user.setCustomExciseDuty(formatter.formatCellValue(row.getCell(7)));
                                user.setExciseDuty(formatter.formatCellValue(row.getCell(8)));
                                user.setOctroiEntryTax(formatter.formatCellValue(row.getCell(9)));
                                user.setCurrencySelected(formatter.formatCellValue(row.getCell(10)));
                                user.setNatureOfPrice(formatter.formatCellValue(row.getCell(11)));
                                user.setPartNoModelNo(formatter.formatCellValue(row.getCell(12)));
                                user.setBasicCost(formatter.formatCellValue(row.getCell(13)));
                                user.setDiscount(formatter.formatCellValue(row.getCell(14)));
                                user.setSubTotalBPD(formatter.formatCellValue(row.getCell(15)));
                                user.setPackForwaCharges(formatter.formatCellValue(row.getCell(16)));
                                user.setGst(formatter.formatCellValue(row.getCell(17)));
                                user.setFreightCharges(formatter.formatCellValue(row.getCell(18)));
                                user.setInstalCommiCharges(formatter.formatCellValue(row.getCell(19)));
                                user.setTrainingCharges(formatter.formatCellValue(row.getCell(20)));
                                user.setInspecTestCharges(formatter.formatCellValue(row.getCell(21)));
                                user.setGstOnITITCharges(formatter.formatCellValue(row.getCell(22)));
                                user.setAnyOtherCharges(formatter.formatCellValue(row.getCell(23)));
                                user.setEvaluatedCost(formatter.formatCellValue(row.getCell(24)));
                                user.setTotEvaluatedCost(formatter.formatCellValue(row.getCell(25)));
                                user.setTotEvaluaCostWords(formatter.formatCellValue(row.getCell(26)));
                                user.setHsnCode(formatter.formatCellValue(row.getCell(27)));
                                user.setMrp(formatter.formatCellValue(row.getCell(28)));
				// persist data into database in here
				lstUser.add(user);
			}			
			//workbook.close();
                   retValue =  uploadExcelServiceImpl.insertUploadExcel(lstUser, sessUserID);
                        
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return retValue;
    }
}
