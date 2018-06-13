/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.accounts;

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
import com.pdms.helpers.AbstractPDFViewHelper;
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
public class ChequeForwdPdfPage extends AbstractPDFViewHelper{

    private static final Logger logger = Logger.getLogger(ChequeForwdPdfPage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //List<VouNoHoaItemsDTO> secObj = (List<VouNoHoaItemsDTO>) model.get("secObj");
        Paragraph headerPara = new Paragraph(" ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
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
        
        cell.setPhrase(new Phrase("Phone No:040-27143597", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("Account Section", fontb));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("OPP-ECIL officer's Association Bldg.,", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Fax No: 040-27143516", font));
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
        
        cell.setPhrase(new Phrase("No.RPUM/ACCTS/RTGS-NEFT", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Date :", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Vendors naem xxxxxxxxx,", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("State bank Of Indiaxxx,", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("E.C.I.L Branchxxx,", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_RIGHT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("HYDERABADxxx - 500 062. TS", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Ref: Bill NO.", font));
        cell.setPaddingTop(20f);
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("Dated:  ", font));
        cell.setPaddingTop(20f);
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Sir/Madam,", font));
        cell.setPaddingTop(20f);
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        document.add(headerTable); 
        //**********************************************************************
      
        //**********************************************************************
        
        PdfPTable headerTable1 = new PdfPTable(1);
        headerTable1.setWidthPercentage(90.0f);
        headerTable1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable1.setWidths(new float[] {100.0f});
        headerTable1.setSpacingBefore(40);
        
        
        Font font1 = FontFactory.getFont(FontFactory.COURIER);
        font1.setColor(BaseColor.BLACK);
        font1.setSize(10.0f);
        
        Font font2 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, Font.UNDERLINE);
        font2.setColor(BaseColor.BLACK);
        font2.setSize(13.0f);
        
        
        PdfPCell cell1 = new PdfPCell();
        cell1.setBackgroundColor(BaseColor.WHITE);
        cell1.setBorder(0);
        cell1.setPadding(1);
        
        cell1.setPhrase(new Phrase("            Payment has been made vide cheque No. xxxxxx, Dated: xxxxxxxx, for ", font1));
        headerTable1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase("Rs. 000000000000, through RTGS as per the bank detailed below.", font1));
        headerTable1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase("Bank        :", font1));
        cell1.setPaddingLeft(40f);
        headerTable1.addCell(cell1); 
        cell1.setPhrase(new Phrase("Branch      :", font1));
        cell1.setPaddingLeft(40f);
        headerTable1.addCell(cell1); 
        cell1.setPhrase(new Phrase("A/C No.     :", font1));
        cell1.setPaddingLeft(40f);
        headerTable1.addCell(cell1); 
        cell1.setPhrase(new Phrase("IFS Code    :", font1));
        cell1.setPaddingLeft(40f);
        headerTable1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase("Recoveries:", font2)); 
        cell1.setPaddingLeft(0f);
        cell1.setPaddingTop(10f);        
        headerTable1.addCell(cell1);
        
        cell1.setPhrase(new Phrase("GST DISALLOWED AS GST DECLARATION CERTIFICATION NOT SUBMITTED", font1)); 
        cell1.setPaddingLeft(0f);
        cell1.setPaddingTop(2f);        
        headerTable1.addCell(cell1);
        
        cell1.setPhrase(new Phrase("Yours faithfully,", font1));  
        cell1.setPaddingTop(30f);
        cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable1.addCell(cell1).setPaddingRight(20f); 
        
        cell1.setPhrase(new Phrase("V.No:  xxxxxxxx, Date:   xxxxxxxxxx,", font1));
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase("(P. RAMACHANDRA RAJU)\nSr. Accounts Officer", font1));
        cell1.setPaddingTop(20f);
        cell1.setBorderWidthBottom(0f);
        cell1.setPaddingBottom(5f);
        cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable1.addCell(cell1).setPaddingRight(10f); 
        
        cell1.setPhrase(new Phrase("Copy to: A.P.O, RPU(M) - for information.", font1));
        cell1.setPaddingTop(120f);
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        headerTable1.addCell(cell1); 
        
        document.add(headerTable1);
        
        
        //**********************************************************************
    
    }
}
