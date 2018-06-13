/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.purchase;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.dto.PoDeItemsDTO;
import com.pdms.helpers.AbstractPdfLandscapFour;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author myassessment
 */
@Component
public class PoItemsPdfPage extends AbstractPdfLandscapFour{

    private static final Logger log = Logger.getLogger(PoItemsPdfPage.class.getName());
    
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<PoDeItemsDTO> itemObj = (List<PoDeItemsDTO>)model.get("itemsObj");
        
        Font fbbc = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk ccbc = new Chunk("ANNEXURE TO P.O.NO.RPUM/HWP/XXXX/XXX", fbbc);                
        ccbc.setBackground(BaseColor.WHITE);
        Paragraph pcbc = new Paragraph(ccbc);
        pcbc.setAlignment(Element.ALIGN_LEFT);
        pcbc.setSpacingBefore(30f);
        pcbc.setIndentationLeft(60f);
        document.add(pcbc);
        
         //*********************************************************************
        PdfPTable table2 = new PdfPTable(12);
        table2.setWidthPercentage(90.0f);
        table2.setSpacingBefore(10f);
        table2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.setWidths(new float[] {5.0f, 5.0f, 30.0f, 5.0f, 5.0f, 8.0f, 7.0f, 7.0f, 7.0f, 7.0f, 6.0f, 8.0f});
        
        PdfPCell cel2 = new PdfPCell();
        cel2.setBackgroundColor(BaseColor.WHITE);
        cel2.setBorder(1); 
        cel2.setBorderColor(BaseColor.GRAY);
        
        Font f9 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel2.setPhrase(new Phrase("Sl No", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Int SlNo", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Item Description", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Qty", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Unit", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Rate", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Fright", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("P&F Charges", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Sales Tax", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("GST", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Discount", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Total", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthRight(0.1f);
        cel2.setBorderWidthBottom(0.1f);        
        table2.addCell(cel2);
        
        Font f8 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        int sl = 1;
        for (PoDeItemsDTO s : itemObj) {
            cel2.setPhrase(new Phrase(""+(sl++), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getIndentslno(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getItemObj().getDescription(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getQuantity(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getUnitObj().getUnitName(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getRate(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getFrieght(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getPfCharges(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getSalesTax(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getGst(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getDiscount(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            //cel2.setBorderWidthRight(0.2f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);

            cel2.setPhrase(new Phrase(""+s.getTotal(), f8));
            cel2.setPaddingLeft(5f);
            cel2.setPaddingBottom(5f);
            cel2.setBorderWidthLeft(0.1f);
            cel2.setBorderWidthRight(0.1f);
            cel2.setBorderWidthBottom(0.1f);
            table2.addCell(cel2);
        }
        
        document.add(table2);
        
        
    }
    
}
