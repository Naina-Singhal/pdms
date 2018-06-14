/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.cst;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.helpers.AbstractPDFViewAthree;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author Phanivaranasi
 */
@Component
public class VendorBasedCstPdf extends AbstractPDFViewAthree{
    private static final Logger LOG = Logger.getLogger(VendorBasedCstPdf.class.getName());
    
     @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        //List<VoucherNoDTO> rtgsVouObj = (List<VoucherNoDTO>)model.get("cstObj");
        
        //for(VoucherNoDTO obj: rtgsVouObj){
        
        Paragraph headerPara_1 = new Paragraph(" ");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        headerPara_1.setSpacingAfter(-10);
        document.add(headerPara_1);
        
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(50.0f);
        headerTable.setWidths(new float[] {100.0f});
        headerTable.setSpacingBefore(0);
        headerTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.BLACK);
        font.setSize(7.0f);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(0);
        cell.setPadding(1);
        
        cell.setPhrase(new Phrase("Government Of India", font));
        cell.setPaddingLeft(200.0f);
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell.setPhrase(new Phrase("Department OF Atomic Energy", font));
        cell.setPaddingLeft(200.0f);
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell.setPhrase(new Phrase("Directorate Of Purchase And Stores", font));
        cell.setPaddingLeft(200.0f);
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell.setPhrase(new Phrase("RPUM", font));
        cell.setPaddingLeft(200.0f);
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell.setPhrase(new Phrase("Comparative Statement", font));
        cell.setPaddingLeft(200.0f);
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        document.add(headerTable);
        
        //**********************************************************************
        
        PdfPTable headerTable1 = new PdfPTable(2);
        headerTable1.setWidthPercentage(20.0f);
        headerTable1.setWidths(new float[] {50.0f, 50.0f});
        headerTable1.setSpacingBefore(10f);
        headerTable1.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        
        Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
        font1.setColor(BaseColor.BLACK);
        font1.setSize(7.0f);
        
        PdfPCell cell1 = new PdfPCell();
        cell1.setBackgroundColor(BaseColor.WHITE);
        cell1.setBorderWidthLeft(0.5f);
        cell1.setBorderWidthTop(0.5f);
        cell1.setBorderColorLeft(BaseColor.BLACK);
        cell1.setBorderColorTop(BaseColor.BLACK);
        cell1.setPadding(1);
        
        cell1.setPhrase(new Phrase("Tender No:-", font));
        cell1.setPaddingLeft(20.0f);
        cell1.setBorder(1);
        cell1.setBorderColor(BaseColor.BLACK);
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("20210", font));
        cell1.setPaddingLeft(20.0f);
        cell1.setBorder(1);
        cell1.setBorderColor(BaseColor.BLACK);
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase("Tender No:-", font));
        cell1.setPaddingLeft(20.0f);
        cell1.setBorderWidth(0.2f);
        cell1.setBorderColor(BaseColor.BLACK);
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("20210", font));
        cell1.setBorderWidth(0.2f);
        cell1.setBorderColor(BaseColor.BLACK);
        cell1.setPaddingLeft(20.0f);
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase("Tender No:-", font));
        cell1.setPaddingLeft(20.0f);
        cell1.setBorderWidth(0.2f);
        cell1.setBorderColor(BaseColor.BLACK);
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("20210", font));
        cell1.setPaddingLeft(20.0f);
        cell1.setBorderWidth(0.2f);
        cell1.setBorderColor(BaseColor.BLACK);
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        document.add(headerTable1);
    }
    
}
