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
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
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
public class DdForwardingPdfPage extends AbstractPDFViewHelper{

    private static final Logger logger = Logger.getLogger(DdForwardingPdfPage.class.getName());

    
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
      
        String pera = "         We enclose our Demand Draft No: xxxxxxxx  Dated: xxxxxxx . for Rs. 000000."
                +"in settlement of your bill(s) mentioned above.\nRecovories:\nNIl";     
       
        
        Font f = new Font(Font.FontFamily.COURIER, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c = new Chunk(pera, f);
        c.setBackground(BaseColor.WHITE);
        Paragraph p = new Paragraph(c);
        p.setAlignment(Element.ALIGN_JUSTIFIED);
        p.setSpacingBefore(30f);
        p.setIndentationLeft(30f);
        p.setIndentationRight(30f);
        document.add(p);
        
        //**********************************************************************
        
        PdfPTable headerTable1 = new PdfPTable(1);
        headerTable1.setWidthPercentage(90.0f);
        headerTable1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable1.setWidths(new float[] {100.0f});
        headerTable1.setSpacingBefore(40);
        
        
        Font font1 = FontFactory.getFont(FontFactory.COURIER);
        font1.setColor(BaseColor.BLACK);
        font1.setSize(10.0f);
        
        
        PdfPCell cell1 = new PdfPCell();
        cell1.setBackgroundColor(BaseColor.WHITE);
        cell1.setBorder(0);
        cell1.setPadding(1);
        
        cell1.setPhrase(new Phrase("            The receipt for the payment may please be furnished in the acknowledgement", font1));
        headerTable1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase("from sent herewith. If the receipt is made in your form, please send the same alongwith our acknowledgement form.", font1));
        headerTable1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase("Yours faithfully,", font1));  
        cell1.setPaddingTop(10f);
        cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable1.addCell(cell1).setPaddingRight(20f); 
        
        cell1.setPhrase(new Phrase("For Account Officer", font1));
        cell1.setPaddingTop(20f);
        cell1.setBorderWidthBottom(0.3f);
        cell1.setPaddingBottom(5f);
        cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable1.addCell(cell1).setPaddingRight(10f); 
        
        document.add(headerTable1);
        
        
        //**********************************************************************
        
        PdfPTable Table = new PdfPTable(3);
        Table.setWidthPercentage(90.0f);
        Table.setHorizontalAlignment(Element.ALIGN_CENTER);
        Table.setWidths(new float[] {33.0f, 33.0f, 33.0f});
        Table.setSpacingBefore(8);
        
        
        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, Font.UNDERLINE);
        font2.setColor(BaseColor.BLACK);
        font2.setSize(10.0f);
        
        Font font3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, Font.NORMAL);
        font3.setColor(BaseColor.BLACK);
        font3.setSize(10.0f);
        
        
        PdfPCell cel = new PdfPCell();
        cel.setBackgroundColor(BaseColor.WHITE);
        cel.setBorder(0);
        cel.setPadding(1);
        
        cel.setPhrase(new Phrase(" ", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase("ACKNOWLEDGEMENT FORM ", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cel.setPhrase(new Phrase(" ", font3));
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("Accounr Office, ", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase(" ", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cel.setPhrase(new Phrase(" ", font3));
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("DPS, DAE, RPUM, ", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase(" ", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cel.setPhrase(new Phrase("V.No: ", font3));
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("Heavy Water Plant General Facilities,", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase("", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cel.setPhrase(new Phrase("V.Date:  ", font3));
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("OPP-ECIL Officer,s Association Bldg.,", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase(" ", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cel.setPhrase(new Phrase(" ", font3));
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("B - Sector, DAE Colony, ECIL Post,", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase(" ", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cel.setPhrase(new Phrase(" ", font3));
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("HYDERABD - 500062.", font3));
        Table.addCell(cel);        
        cel.setPhrase(new Phrase(" ", font2));
        Table.addCell(cel).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cel.setPhrase(new Phrase(" ", font3));
        Table.addCell(cel);
        
        document.add(Table);
        //*********************************************************************
        String pera1 = "Received Rs.______________(Repees_________________________________________________only)"
                + " from__________________________________________.";
        Font f4 = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c4 = new Chunk(pera1, f4);
        c4.setBackground(BaseColor.WHITE);
        Paragraph p4 = new Paragraph(c4);
        p4.setAlignment(Element.ALIGN_JUSTIFIED);
        p4.setSpacingBefore(30f);
        p4.setIndentationLeft(30f);
        p4.setIndentationRight(30f);
        document.add(p4);
        
        Font f5 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5 = new Chunk("Signature with \nRevenue Stamp", f5);
        c5.setBackground(BaseColor.WHITE);
        Paragraph p5 = new Paragraph(c5);
        p5.setAlignment(Element.ALIGN_RIGHT);
        p5.setSpacingBefore(30f);
        p5.setIndentationLeft(30f);
        p5.setIndentationRight(60f);
        document.add(p5);
    }
    
}
