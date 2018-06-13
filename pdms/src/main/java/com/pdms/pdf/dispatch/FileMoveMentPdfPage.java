/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.dispatch;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
public class FileMoveMentPdfPage extends AbstractPDFViewHelper{
    private static final Logger logger = Logger.getLogger(FileMoveMentPdfPage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.BOLD, BaseColor.BLACK);
        Chunk c = new Chunk("REGIONAL PURCHASE UNIT MANUGURU", f);
        c.setBackground(BaseColor.WHITE);        
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(80f);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setIndentationRight(30f);
        document.add(p);
        Chunk cc = new Chunk("ACKNOWLEDGEMENT", f);
        cc.setBackground(BaseColor.WHITE);        
        Paragraph pc = new Paragraph(cc);
        pc.setSpacingBefore(5f);
        pc.setAlignment(Element.ALIGN_CENTER);
        pc.setIndentationRight(30f);
        document.add(pc);
        
        Font f1 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc1 = new Chunk("TO :                                         Dated :   ", f1);
        cc1.setBackground(BaseColor.WHITE);        
        Paragraph pcc = new Paragraph(cc1);
        pcc.setSpacingBefore(20f);
        pcc.setAlignment(Element.ALIGN_LEFT);
        pcc.setIndentationLeft(30f);
        document.add(pcc);
        
        
        
        //********************main table ***************************************
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL);
        
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(90.0f);
        table.setSpacingBefore(20f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {25.0f, 25.0f, 25.0f, 25.0f});
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("Reference"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("File No"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("PO.No"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Type Of Dak"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        document.add(table);
        
    }
    
}
