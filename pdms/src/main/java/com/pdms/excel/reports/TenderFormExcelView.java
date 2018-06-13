/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.excel.reports;

import com.pdms.dto.PublicTenderDTO;
import com.pdms.exception.AppException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author hpasupuleti
 */
public class TenderFormExcelView extends AbstractExcelView{
    
    
    private static final Logger log = Logger.getLogger(TenderFormExcelView.class);
    
    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, 
            HttpServletRequest request, HttpServletResponse response) throws AppException {
        log.info("Start: TenderFormExcelView : buildExcelDocument() for build excel report based on selected vulnerabilities");
        try {
            
            PublicTenderDTO tenderObj = (PublicTenderDTO) model.get("tenderObj");
            
            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.BLACK.index);
            
            HSSFCellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            headerCellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
            headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            headerCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
            headerCellStyle.setWrapText(true);
            headerCellStyle.setFont(font);
            
            
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
            cellStyle.setWrapText(true);
            cellStyle.setFont(font);
            
            HSSFSheet excelSheet = workbook.createSheet("Tender Details");
            excelSheet.setDefaultColumnWidth(42);
            
            HSSFRow excelHeader = excelSheet.createRow(0);
            excelHeader.createCell(0).setCellValue("Field Name");
            excelHeader.createCell(1).setCellValue("Field Value");
            excelHeader.getCell(0).setCellStyle(cellStyle);
            excelHeader.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_1 = excelSheet.createRow(1);
            tender_excel_row_1.createCell(0).setCellValue("Tender Ref. No.");
            tender_excel_row_1.createCell(1).setCellValue(tenderObj.getFileNo());
            tender_excel_row_1.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_1.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_2 = excelSheet.createRow(2);
            tender_excel_row_2.createCell(0).setCellValue("Tender Details.");
            tender_excel_row_2.createCell(1).setCellValue("");
            tender_excel_row_2.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_2.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_3 = excelSheet.createRow(3);
            tender_excel_row_3.createCell(0).setCellValue("Tender Description.");
            tender_excel_row_3.createCell(1).setCellValue(tenderObj.getDueDate());
            tender_excel_row_3.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_3.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_4 = excelSheet.createRow(4);
            tender_excel_row_4.createCell(0).setCellValue("Tender Envelope Types.");
            tender_excel_row_4.createCell(1).setCellValue("");
            tender_excel_row_4.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_4.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_5 = excelSheet.createRow(5);
            tender_excel_row_5.createCell(0).setCellValue("Tender Type.");
            tender_excel_row_5.createCell(1).setCellValue(tenderObj.getTenderType());
            tender_excel_row_5.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_5.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_6 = excelSheet.createRow(6);
            tender_excel_row_6.createCell(0).setCellValue("Contract Type.");
            tender_excel_row_6.createCell(1).setCellValue("");
            tender_excel_row_6.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_6.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_7 = excelSheet.createRow(7);
            tender_excel_row_7.createCell(0).setCellValue("Bidding Type.");
            tender_excel_row_7.createCell(1).setCellValue("");
            tender_excel_row_7.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_7.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_8 = excelSheet.createRow(8);
            tender_excel_row_8.createCell(0).setCellValue("Mode of Tender Submission");
            tender_excel_row_8.createCell(1).setCellValue("");
            tender_excel_row_8.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_8.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_9 = excelSheet.createRow(9);
            tender_excel_row_9.createCell(0).setCellValue("Estimated Cost");
            tender_excel_row_9.createCell(1).setCellValue(tenderObj.getTenderCost());
            tender_excel_row_9.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_9.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_10 = excelSheet.createRow(10);
            tender_excel_row_10.createCell(0).setCellValue("Bid Validity Period");
            tender_excel_row_10.createCell(1).setCellValue("");
            tender_excel_row_10.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_10.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_11 = excelSheet.createRow(11);
            tender_excel_row_11.createCell(0).setCellValue("Period of Contract (For Rate Contracts only)");
            tender_excel_row_11.createCell(1).setCellValue("");
            tender_excel_row_11.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_11.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_12 = excelSheet.createRow(12);
            tender_excel_row_12.createCell(0).setCellValue("Mode of Tender Fee Payment");
            tender_excel_row_12.createCell(1).setCellValue("");
            tender_excel_row_12.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_12.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_13 = excelSheet.createRow(13);
            tender_excel_row_13.createCell(0).setCellValue("Tender Fees in Rs.");
            tender_excel_row_13.createCell(1).setCellValue("");
            tender_excel_row_13.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_13.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_14 = excelSheet.createRow(14);
            tender_excel_row_14.createCell(0).setCellValue("Payable to & Payable at");
            tender_excel_row_14.createCell(1).setCellValue("");
            tender_excel_row_14.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_14.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_15 = excelSheet.createRow(15);
            tender_excel_row_15.createCell(0).setCellValue("Mode of Tender EMD Fees");
            tender_excel_row_15.createCell(1).setCellValue("");
            tender_excel_row_15.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_15.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_16 = excelSheet.createRow(16);
            tender_excel_row_16.createCell(0).setCellValue("Tender EMD Fees in Rs.");
            tender_excel_row_16.createCell(1).setCellValue("");
            tender_excel_row_16.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_16.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_17 = excelSheet.createRow(17);
            tender_excel_row_17.createCell(0).setCellValue("Place of Delivery/ Project Location");
            tender_excel_row_17.createCell(1).setCellValue("");
            tender_excel_row_17.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_17.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_18 = excelSheet.createRow(18);
            tender_excel_row_18.createCell(0).setCellValue("Qualifying Requirements");
            tender_excel_row_18.createCell(1).setCellValue("");
            tender_excel_row_18.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_18.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_19 = excelSheet.createRow(19);
            tender_excel_row_19.createCell(0).setCellValue("Terms & Conditions");
            tender_excel_row_19.createCell(1).setCellValue("");
            tender_excel_row_19.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_19.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_20 = excelSheet.createRow(20);
            tender_excel_row_20.createCell(0).setCellValue("Mode of Pre-bidding Meeting");
            tender_excel_row_20.createCell(1).setCellValue("");
            tender_excel_row_20.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_20.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_21 = excelSheet.createRow(21);
            tender_excel_row_21.createCell(0).setCellValue("Pre-bid Meeting Venue");
            tender_excel_row_21.createCell(1).setCellValue("");
            tender_excel_row_21.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_21.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_22 = excelSheet.createRow(22);
            tender_excel_row_22.createCell(0).setCellValue("Pre-bid Meeting Start Date & Time");
            tender_excel_row_22.createCell(1).setCellValue("");
            tender_excel_row_22.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_22.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_23 = excelSheet.createRow(23);
            tender_excel_row_23.createCell(0).setCellValue("Pre-bid Meeting End Date & Time");
            tender_excel_row_23.createCell(1).setCellValue("");
            tender_excel_row_23.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_23.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_24 = excelSheet.createRow(24);
            tender_excel_row_24.createCell(0).setCellValue("Document Download Start Date & Time");
            tender_excel_row_24.createCell(1).setCellValue("");
            tender_excel_row_24.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_24.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_25 = excelSheet.createRow(25);
            tender_excel_row_25.createCell(0).setCellValue("Document Download End Date & Time");
            tender_excel_row_25.createCell(1).setCellValue("");
            tender_excel_row_25.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_25.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_26 = excelSheet.createRow(26);
            tender_excel_row_26.createCell(0).setCellValue("Last Date of Submission & Time");
            tender_excel_row_26.createCell(1).setCellValue(tenderObj.getSaleLastDate());
            tender_excel_row_26.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_26.getCell(1).setCellStyle(cellStyle);
            
            HSSFRow tender_excel_row_27 = excelSheet.createRow(27);
            tender_excel_row_27.createCell(0).setCellValue("Opening Start Date & Time");
            tender_excel_row_27.createCell(1).setCellValue(tenderObj.getOpeningDate());
            tender_excel_row_27.getCell(0).setCellStyle(cellStyle);
            tender_excel_row_27.getCell(1).setCellStyle(cellStyle);
            
            
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"Tender Details_"
                    + tenderObj.getFileNo() + ".xls\"");
            
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.debug("Exceptionoccured : TenderFormExcelView: " + e.getMessage());
            throw new AppException("Exception occured while Excecuting the "
                    + "TenderFormExcelView(): " + e.getMessage());
        }
    }
}
