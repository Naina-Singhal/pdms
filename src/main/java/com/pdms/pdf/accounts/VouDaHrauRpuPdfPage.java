/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.accounts;

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
import com.pdms.dto.LrEntryDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
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
public class VouDaHrauRpuPdfPage extends AbstractPDFViewHelper{

    private static final Logger LOG = Logger.getLogger(VouDaHrauRpuPdfPage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        //List<LrEntryDTO> lrrObj = (List<LrEntryDTO>)model.get("lrObj");
        
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
        //***********************Main Heading**************************************
        Font f = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c = new Chunk("HYDERABAD REGIONAL ACCOUNTS UNIT", f);
        c.setBackground(BaseColor.WHITE);        
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(10f);
        p.setAlignment(Element.ALIGN_CENTER);        
        document.add(p);
        
        Font f1 = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc = new Chunk("RPU (M), HYDERABAD", f1);
        cc.setBackground(BaseColor.WHITE);        
        Paragraph pc = new Paragraph(cc);
        pc.setSpacingBefore(0f);
        pc.setAlignment(Element.ALIGN_CENTER);        
        document.add(pc);
        
         //*********************Main ***************************************
        PdfPTable headerTable1 = new PdfPTable(4);
        headerTable1.setWidthPercentage(90.0f);
        headerTable1.setWidths(new float[] {25.0f, 25.0f,25.0f, 25.0f});
        headerTable1.setSpacingBefore(40);
        
        
        Font font1 = FontFactory.getFont(FontFactory.COURIER);
        font1.setColor(BaseColor.BLACK);
        font1.setSize(10.0f);
                
        PdfPCell cell1 = new PdfPCell();
        cell1.setBackgroundColor(BaseColor.WHITE);
        cell1.setBorder(0);
        cell1.setPadding(1);
        
        cell1.setPhrase(new Phrase("V.No. ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("Date :", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell1.setPhrase(new Phrase("Bill.No.", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("Date :", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        
        cell1.setPhrase(new Phrase("P.O.No.", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("Date :", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell1.setPhrase(new Phrase("RV.No.", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("CC No :", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell1.setPhrase(new Phrase("Type Of payment", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setPhrase(new Phrase("Descrption:", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        
        document.add(headerTable1);
        
        //*************************HOA table************************************
        Font font2 = new Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL);
        
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(90.0f);
        table.setSpacingBefore(30f);        
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {20.0f, 13.0f, 13.0f, 13.0f, 20.0f, 20.0f});
        
        PdfPCell cell = new PdfPCell();        
        
        cell = new PdfPCell(new Phrase("Head Of Account."));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Short Code"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("S Code"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Code"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(5f);        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Debit Rs."));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Credit Rs."));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(5f);        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        document.add(table);
        
        //***************8670************************************************
       
        Font f2 = new Font(Font.FontFamily.HELVETICA, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc2 = new Chunk("8670 - C & B.", f2);
        cc2.setBackground(BaseColor.WHITE);        
        Paragraph pc2 = new Paragraph(cc2);
        pc2.setSpacingBefore(40f);
        pc2.setIndentationLeft(30f);
        pc2.setAlignment(Element.ALIGN_LEFT);        
        document.add(pc2);
        
        PdfPTable headerTable2 = new PdfPTable(2);
        headerTable2.setWidthPercentage(90.0f);
        headerTable2.setWidths(new float[] {50.0f, 50.0f});
        headerTable2.setSpacingBefore(5f);
        
        
        Font font3 = FontFactory.getFont(FontFactory.COURIER);
        font3.setColor(BaseColor.BLACK);
        font3.setSize(10.0f);
                
        PdfPCell cell2 = new PdfPCell();
        cell2.setBackgroundColor(BaseColor.WHITE);
        cell2.setBorder(0);
        cell2.setPadding(1);
        
        cell2.setPhrase(new Phrase("Passed for Rupees : ", font1));
        cell2.setBorderWidthTop(1f);
        cell2.setPaddingTop(6f);
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell2.setPhrase(new Phrase(" ", font1));
        cell2.setBorderWidthTop(1f);
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell2.setPhrase(new Phrase("In favour of :", font1));
        cell2.setPaddingTop(6f);
        cell2.setBorderWidthTop(0f);
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell2.setPhrase(new Phrase(" ", font1));
        cell2.setBorderWidthTop(0f);
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell2.setPhrase(new Phrase("By way of :", font1));
        cell2.setPaddingTop(6f);
        cell2.setBorderWidthTop(0f);
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell2.setPhrase(new Phrase(" ", font1));
        cell2.setBorderWidthTop(0f);
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        document.add(headerTable2);
        
        Font f3 = new Font(Font.FontFamily.COURIER, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc3 = new Chunk("____________________________DA___________AA_________________Drawing & Disbursing Officer"
                + "_____Pay_Rs._________________________(Rupees)_____________________________________________"
                + "__________________________________________only.", f3);
        cc3.setBackground(BaseColor.WHITE);        
        Paragraph pc3 = new Paragraph(cc3);
        pc3.setSpacingBefore(30f);
        pc3.setIndentationLeft(30f);
        pc3.setIndentationRight(30f);
        pc3.setAlignment(Element.ALIGN_LEFT);        
        document.add(pc3);
        
        Font f4 = new Font(Font.FontFamily.COURIER, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc4 = new Chunk("________________________________________________________________Sub Pay Officer", f4);
        cc4.setBackground(BaseColor.WHITE);        
        Paragraph pc4 = new Paragraph(cc4);
        pc4.setSpacingBefore(30f);
        pc4.setIndentationLeft(30f);
        pc4.setIndentationRight(30f);
        pc4.setAlignment(Element.ALIGN_CENTER);        
        document.add(pc4);
        
        //**************************************
        PdfPTable headerTable4 = new PdfPTable(2);
        headerTable4.setWidthPercentage(90.0f);
        headerTable4.setWidths(new float[] {50.0f, 50.0f});
        headerTable4.setSpacingBefore(5f);
        
        
        Font font4 = FontFactory.getFont(FontFactory.COURIER);
        font4.setColor(BaseColor.BLACK);
        font4.setSize(10.0f);
                
        PdfPCell cell3 = new PdfPCell();
        cell3.setBackgroundColor(BaseColor.WHITE);
        cell3.setBorder(0);
        cell3.setPadding(1);
        
        cell3.setPhrase(new Phrase("To be sent to the following address : \n\n\n\n", font1));
        cell3.setBorderWidthTop(0f);
        cell3.setPaddingTop(6f);
        headerTable4.addCell(cell3).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell3.setPhrase(new Phrase(" ", font1));
        cell3.setBorderWidthTop(0f);
        headerTable4.addCell(cell3).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        document.add(headerTable4);
        
        
        //***************************CHEQUE DRAWN******************************
        
        Font f5 = new Font(Font.FontFamily.COURIER, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc5 = new Chunk("Cheque No._____________________________________ Date :__________________________"
                + "\nFor Rs.______________________________________________________"
                + "\n\n\n\nDate : xxxxxxx", f5);
        cc5.setBackground(BaseColor.WHITE);        
        Paragraph pc5 = new Paragraph(cc5);
        pc5.setSpacingBefore(30f);
        pc5.setIndentationLeft(30f);
        pc5.setIndentationRight(30f);
        pc5.setAlignment(Element.ALIGN_LEFT);        
        document.add(pc5);
    }
    
}
