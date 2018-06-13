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
import com.pdms.account.dto.BillEntryDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author naagu
 */
@Component
public class LrForwaringPdf extends AbstractPDFViewHelper{
    private static final Logger logger = Logger.getLogger(LrForwaringPdf.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<BillEntryDTO> vouIbcObj = (List<BillEntryDTO>)model.get("LrObj");
        
        for(BillEntryDTO obj: vouIbcObj){
        
        Paragraph headerPara_1 = new Paragraph("  ");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        headerPara_1.setSpacingAfter(-5);
        document.add(headerPara_1);
        
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100.0f);
        headerTable.setWidths(new float[] {33.0f, 33.0f, 33.0f});
        headerTable.setSpacingBefore(40);
        
        
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.BLACK);
        font.setSize(10.0f);
        
        Font fontb = FontFactory.getFont(FontFactory.HELVETICA);
        fontb.setColor(BaseColor.BLACK);
        fontb.setSize(13.0f);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(0);
        cell.setPadding(1);
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("Government Of India", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("Department Of atomic Energy", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("Directorate Of Purchase & Stores", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);
        
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("Regional Purchase Unit (Munuguru)", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Heavy Water plant General Facilities,", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Phone No:27181215", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("Account Section", fontb));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("OPP-ECIL officer's Association Bldg.,", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Fax No: 27143516", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("B - Sector, DAE Colony,", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("ECIL Post,", font));
        headerTable.addCell(cell);
        
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Hyderabad - 500062", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("No.RPUM/HRAU/LRF/17-18/", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Date :"+obj.getBillDate(), font));
        headerTable.addCell(cell);
        
        document.add(headerTable);      
        
        
        Font f = new Font(Font.FontFamily.HELVETICA, 11.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk c = new Chunk("Sub:- Forwarding of Lorry/Railway Receipts.", f);
        c.setBackground(BaseColor.WHITE);
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(30f);
        p.setIndentationLeft(150f);
        document.add(p);
        
        StringBuilder declStr = new StringBuilder();
        declStr.append("The following Lorry/Railway Receipts are forwarded herewith for onward transmission ");
        declStr.append(" to Stores Officer, DPS, Heavy Water Plant Manuguru for taking necessary action at their end.");
        
       
        Paragraph declartionPara = new Paragraph(declStr.toString());
        declartionPara.setAlignment(Element.ALIGN_JUSTIFIED);
        declartionPara.setFont(FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL));
        declartionPara.setSpacingBefore(30f);
        declartionPara.setIndentationLeft(30f);
        declartionPara.setIndentationRight(30f);
        document.add(declartionPara);
        
        
        //********************************* TABLE 3 ****************************
        
        Font font3 = FontFactory.getFont(FontFactory.HELVETICA);
        font3.setColor(BaseColor.BLACK);
        font3.setSize(10.0f);
        
        PdfPTable headerTable3 = new PdfPTable(7);
        headerTable3.setWidthPercentage(90.0f);
        headerTable3.setWidths(new float[] {5.0f, 10.0f, 10.0f, 30.0f, 10.0f, 10.0f, 25.0f});
        headerTable3.setSpacingBefore(20);
        headerTable3.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell3 = new PdfPCell();
        cell3.setBackgroundColor(BaseColor.WHITE);        
        cell3.setPadding(1);
       
        
        
        cell3.setPhrase(new Phrase("SL No", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("PO NO", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("File No", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase("Name Of The Vendor", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("INV.NO & DATE", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);   
        cell3.setPhrase(new Phrase("LR.NO & DATE", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase("NAME OF THE TRANSPORTER", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);
        
        
        cell3.setPhrase(new Phrase("1 ", font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" "+obj.getPoNumber(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase(" "+obj.getSupplierName(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(obj.getBillNo()+" \n"+obj.getBillDate(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);   
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);
        
        
        document.add(headerTable3);
        
        
        
        //*****************************
        Font font5 = FontFactory.getFont(FontFactory.HELVETICA, font.BOLD);
        font5.setColor(BaseColor.BLACK);
        font5.setSize(14.0f);
        
        
        Font font4 = FontFactory.getFont(FontFactory.HELVETICA);
        font4.setColor(BaseColor.BLACK);
        font4.setSize(10.0f);
        
        PdfPTable headerTable4 = new PdfPTable(3);
        headerTable4.setWidthPercentage(90.0f);
        headerTable4.setWidths(new float[] {20.0f, 50.0f, 30.0f});
        headerTable4.setSpacingBefore(50);        
        headerTable4.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell4 = new PdfPCell();
        cell4.setBackgroundColor(BaseColor.WHITE);        
        cell4.setPadding(1);
        cell4.setBorder(0);
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase("Sr.ACCOUNTS OFFICER", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Store Officer,,", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Heavy Water Plant,", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase("OFFICE COPY", font5)); 
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Manuguru,", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);
        
        document.add(headerTable4);
        
        Font f6 = new Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk copy = new Chunk("Copy to  : Manager (Purchase), RPUM - for information.", f6);
        copy.setBackground(BaseColor.WHITE);
        Paragraph pco = new Paragraph(copy);
        pco.setSpacingBefore(30f);
        pco.setIndentationLeft(60f);
        document.add(pco);
    }
    }
    
    
}
