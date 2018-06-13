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
import com.pdms.account.dto.ChallanEnFrCashDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import com.sun.javafx.scene.layout.region.Margins;
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
public class ChallanCashPdf extends AbstractPDFViewHelper{
    private static final Logger log = Logger.getLogger(ChallanCashPdf.class.getName());
    
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        //List<ChallanEnFrCashDTO> indentObj = (List<ChallanEnFrCashDTO>)model.get("challanObj");
        
        Paragraph headerPara_1 = new Paragraph("  ");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        headerPara_1.setSpacingAfter(-5);
        doc.add(headerPara_1);
        
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
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("B - Sector, DAE Colony,", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("ECIL Post,", font));
        headerTable.addCell(cell);
        
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Hyderabad - 500062", font));
        headerTable.addCell(cell);
        
        
        doc.add(headerTable);
        
       
        
        Font font1 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLD);
        font1.setColor(BaseColor.BLACK);
        font1.setSize(10.0f);
        
        Font font2 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLDITALIC);
        font2.setColor(BaseColor.BLACK);
        font2.setSize(12.0f);
        
        PdfPTable headerTable2 = new PdfPTable(1);
        headerTable2.setWidthPercentage(100.0f);
        headerTable2.setWidths(new float[] {100.0f});
        headerTable2.setSpacingBefore(20);
        
        PdfPCell cell2 = new PdfPCell();
        cell2.setBackgroundColor(BaseColor.WHITE);
        cell2.setBorder(0);
        cell2.setPadding(1);
        
               
        cell2.setPhrase(new Phrase("CHALLAN OF DD/BC PAID INTO STATE BANK OF INDIA, ECIL BRANCH, HYDERABAD", font1));
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_CENTER);        
        
        cell2.setPhrase(new Phrase("CHALLAN NO:- RPUM/HWP/ACCOUNTS/CASH/ 4427 / 2017-2018  DT. 04/11/17", font1));
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        cell2.setPhrase(new Phrase("  ", font1));
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        cell2.setPhrase(new Phrase("BANK ACCOUNT CODE NO:- 33334788633", font2));
        headerTable2.addCell(cell2).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        doc.add(headerTable2);
        //********************************* TABLE 3 ****************************
        
        Font font3 = FontFactory.getFont(FontFactory.HELVETICA);
        font3.setColor(BaseColor.BLACK);
        font3.setSize(10.0f);
        
        PdfPTable headerTable3 = new PdfPTable(7);
        headerTable3.setWidthPercentage(90.0f);
        headerTable3.setWidths(new float[] {12.0f, 12.0f, 16.0f, 10.0f, 16.0f, 12.0f, 10.0f});
        headerTable3.setSpacingBefore(40);
        headerTable3.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell3 = new PdfPCell();
        cell3.setBackgroundColor(BaseColor.WHITE);        
        cell3.setPadding(1);
       
        
        
        cell3.setPhrase(new Phrase("By Whom Tendered", font3));        
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("Name and address of the person on whose behalf money is paid", font3));
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("Full particulars of the remittance and authority", font3));
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase("Amount Rs.", font3));
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("Head of account", font3));
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("AO By whom adjustable", font3));
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase("Order to bank", font3));
        headerTable3.addCell(cell3);
        
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(170f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(170f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(170f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(170f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(170f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" ", font3));
        cell3.setFixedHeight(170f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase(" ", font3));
        headerTable3.addCell(cell3);
        cell3.setFixedHeight(170f);
        
        doc.add(headerTable3);
        
        //******************** table four **************************************
        Font font4 = FontFactory.getFont(FontFactory.HELVETICA);
        font4.setColor(BaseColor.BLACK);
        font4.setSize(10.0f);
        
        PdfPTable headerTable4 = new PdfPTable(2);
        headerTable4.setWidthPercentage(90.0f);
        headerTable4.setWidths(new float[] {30.0f, 70.0f});
        headerTable4.setSpacingBefore(10);
        headerTable4.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell4 = new PdfPCell();
        cell4.setBackgroundColor(BaseColor.WHITE);        
        cell4.setPaddingBottom(0);
        cell4.setBorder(0);
        
        cell4.setPhrase(new Phrase("Rs. 10880", font4)); 
        cell4.setPaddingBottom(20f);
        headerTable4.addCell(cell4);        
        cell4.setPhrase(new Phrase("/(Rupees Ten Thousand eight hundred only )", font4));
        cell4.setPaddingBottom(20f);
        headerTable4.addCell(cell4); 
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setPaddingBottom(0f);
        headerTable4.addCell(cell4);        
        cell4.setPhrase(new Phrase("(P.RAMACHANDRA RAJU)", font4));
        cell4.setPaddingBottom(0f);
        headerTable4.addCell(cell4).setHorizontalAlignment(Element.ALIGN_RIGHT); 
        
        cell4.setPhrase(new Phrase(" ", font4));        
        cell4.setBorderWidthBottom(0.2f);
        cell4.setBorderColorBottom(BaseColor.BLACK);
        cell4.setPaddingBottom(5f);
        headerTable4.addCell(cell4);        
        cell4.setPhrase(new Phrase("Account  Officer", font4));
        cell4.setPaddingRight(23f);
        cell4.setBorderWidthBottom(0.2f);
        cell4.setBorderColorBottom(BaseColor.BLACK);
        cell4.setPaddingBottom(5f);
        headerTable4.addCell(cell4).setHorizontalAlignment(Element.ALIGN_RIGHT); 
        
        cell4.setPhrase(new Phrase("Received payment (Rupees in words)", font4)); 
        cell4.setBorderColorBottom(BaseColor.WHITE);
        headerTable4.addCell(cell4);        
        cell4.setPhrase(new Phrase(" ", font4));
        cell4.setBorderColorBottom(BaseColor.WHITE);
        headerTable4.addCell(cell4).setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        
        doc.add(headerTable4);
        
        //******************** table five **************************************
        Font font5 = FontFactory.getFont(FontFactory.HELVETICA);
        font5.setColor(BaseColor.BLACK);
        font5.setSize(10.0f);
        
        PdfPTable headerTable5 = new PdfPTable(4);
        headerTable5.setWidthPercentage(90.0f);
        headerTable5.setWidths(new float[] {20.0f, 20.0f, 20.0f, 40.0f});
        headerTable5.setSpacingBefore(40);
        headerTable5.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell5 = new PdfPCell();
        cell5.setBackgroundColor(BaseColor.WHITE);        
        cell5.setBorder(0);
        
        cell5.setPhrase(new Phrase("CASHIER", font5)); 
        cell5.setBorderWidthBottom(0.5f);
        cell5.setBorderColorBottom(BaseColor.BLACK);
        cell5.setPaddingBottom(10f);
        headerTable5.addCell(cell5);        
        cell5.setPhrase(new Phrase("HEAD CASHIER", font5));
        cell5.setBorderWidthBottom(0.5f);
        cell5.setBorderColorBottom(BaseColor.BLACK);
        cell5.setPaddingBottom(10f);
        headerTable5.addCell(cell5);
        cell5.setPhrase(new Phrase("DATE", font5)); 
        cell5.setBorderWidthBottom(0.5f);
        cell5.setBorderColorBottom(BaseColor.BLACK);
        cell5.setPaddingBottom(10f);
        headerTable5.addCell(cell5);        
        cell5.setPhrase(new Phrase("Agent/Br.Manager, SBI, ECIL, Hyderabad", font5));
        cell5.setBorderWidthBottom(0.5f);
        cell5.setBorderColorBottom(BaseColor.BLACK);
        cell5.setPaddingBottom(10f);
        headerTable5.addCell(cell5); 
        
        doc.add(headerTable5);
        
    }
    
}
