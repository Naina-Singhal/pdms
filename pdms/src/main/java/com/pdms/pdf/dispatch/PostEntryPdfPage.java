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
import com.pdms.helpers.AbstractPDFViewHelper;
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
public class PostEntryPdfPage extends AbstractPDFViewHelper{
    private static final Logger LOG = Logger.getLogger(PostEntryPdfPage.class.getName());
    
     @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
        
        Paragraph headerPara_1 = new Paragraph("STATEMENT SHOWING DETAILS OF DAILY FRANKING OF RPUM, \n"
                + "     FROM :          TO :        ");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara_1.setSpacingBefore(50f);
        document.add(headerPara_1);
        
        
        //********************main table ***************************************
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9f, Font.UNDERLINE);
        
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(90.0f);
        table.setSpacingBefore(20f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {20.0f, 15.0f, 15.0f, 15.0f, 15.0f, 20.0f});
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("Franking Date"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Opening Amt"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Closing Amt"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Opening Bal"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Closing Bal"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Franked Amt Rupees."));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        document.add(table);
        
    }
}
