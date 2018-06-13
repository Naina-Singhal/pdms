/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.dispatch;

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
import com.pdms.despatch.itemsDto.PostageItemsDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author ramakrishna
 */
@Component
public class postageEntryPdfPage extends AbstractPDFViewHelper{
    private static final Logger logger = Logger.getLogger(postageEntryPdfPage.class.getName());
    
     @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        List<PostageItemsDTO> pooObj = (List<PostageItemsDTO>) model.get("postageObj");
       
        
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
        String poo =  (String) model.get("selectDate");
        
        Paragraph headerPara_1 = new Paragraph("POSTAGE DETAILS OF : "+poo);
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.UNDERLINE));
        headerPara_1.setSpacingBefore(50f);
        document.add(headerPara_1);
        
        //********************main table ***************************************
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL);
        
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(90.0f);
        table.setSpacingBefore(20f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {10.0f, 15.0f, 8.0f, 10.0f, 30.0f, 12.0f, 15.0f});
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("DES.NO."));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("REF"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CQ.NO"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("TYPE"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("NAME  & CITY"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("NATURE"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("AMOUNT"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
         for (PostageItemsDTO obj: pooObj) {
             cell.setPhrase(new Phrase(""+obj.getDispatchno(), font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);
             
             cell.setPhrase(new Phrase("File NO: "+obj.getPostObj().getFileNo(), font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);
             
             cell.setPhrase(new Phrase("", font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);
             
             cell.setPhrase(new Phrase(""+obj.getTods(), font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);
             
             cell.setPhrase(new Phrase("Name:"+obj.getVenObj().getVendorName()+" \nCity:"+obj.getVenObj().getVendorCity(), font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);
             
             cell.setPhrase(new Phrase(""+obj.getNature(), font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);
             
             cell.setPhrase(new Phrase(""+obj.getAmount(), font1));
             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
             table.addCell(cell).setPaddingLeft(3);

         }
        
        document.add(table);
    }
    
}
