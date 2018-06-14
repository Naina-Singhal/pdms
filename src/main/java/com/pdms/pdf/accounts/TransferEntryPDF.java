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
import com.pdms.account.dto.TransferEntryDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class TransferEntryPDF extends AbstractPDFViewHelper{

    private static final Logger LOG = Logger.getLogger(TransferEntryPDF.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<TransferEntryDTO> transObj = (List<TransferEntryDTO>)model.get("traObj");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
        String SysDate= formatter.format(date);  
        
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
        int count = 0;
        for (TransferEntryDTO b : transObj) {
            count++;
            if (count == 1) {
                cell.setPhrase(new Phrase("Tr.O.No. " + b.getTeNumber(), font));
                headerTable.addCell(cell).setPaddingLeft(30);
                cell.setPhrase(new Phrase("", font));
                headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPhrase(new Phrase("Date :" + SysDate, font));
                headerTable.addCell(cell);
            }
        }
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        document.add(headerTable);  
        
        Font f = new Font(Font.FontFamily.HELVETICA, 15.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk c = new Chunk("TRANSFER ENTRY FOR   2017 - 2018 ", f);
        c.setBackground(BaseColor.WHITE);
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(0f);
        p.setIndentationLeft(180f);
        document.add(p);
        
        
        //********************************* TABLE 3 ****************************
        
        Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0f, Font.NORMAL, BaseColor.BLACK);
        Font font5 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        
        PdfPTable headerTable3 = new PdfPTable(4);
        headerTable3.setWidthPercentage(90.0f);
        headerTable3.setWidths(new float[] {40.0f, 30.0f, 15.0f, 15.0f});
        headerTable3.setSpacingBefore(40);
        headerTable3.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell3 = new PdfPCell();
        cell3.setBackgroundColor(BaseColor.WHITE);        
        cell3.setPadding(1);
        cell3.setBorderWidth(0.5f);
        
        cell3.setPhrase(new Phrase("Perticulars of transaction with reasons for propose adjustment", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(10f);
        cell3.setPaddingBottom(10f);
        cell3.setBorderWidthBottom(0.5f);
        headerTable3.addCell(cell3); 
        
        cell3.setPhrase(new Phrase("Head Of Account", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(10f);
        cell3.setPaddingBottom(10f);
        cell3.setBorderWidthBottom(0.5f);
        headerTable3.addCell(cell3);
        
        cell3.setPhrase(new Phrase("Debit", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(10f);
        cell3.setPaddingBottom(10f);
        cell3.setBorderWidthBottom(0.5f);
        headerTable3.addCell(cell3);
        
        cell3.setPhrase(new Phrase("Credit", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(10f);
        cell3.setPaddingBottom(10f);
        cell3.setBorderWidthBottom(0.5f);
        headerTable3.addCell(cell3);
        //********row 1
        int cou = 0;
        for (TransferEntryDTO a : transObj) {
            cou++;
            if(cou == 1){
            cell3.setPhrase(new Phrase("V/CH.NO: "+a.getVouChallanNo()+" V.DT: "+a.getVoucherDate()+"\nCheque NO: "
                    +a.getVouNoObj().getChequeNo()+", Date: "+a.getVouNoObj().getChequeDate()
                    +"\nCancelled due to supplier's INABILITY \nsupply the material.", font5));
            cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell3.setPaddingTop(10f);
            cell3.setPaddingBottom(10f);
            cell3.setBorderWidthBottom(0f);
            headerTable3.addCell(cell3);
            } else {
                cell3.setPhrase(new Phrase(" ", font5));
                cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell3.setPaddingTop(10f);
                cell3.setBorderWidthBottom(0.5f);              
                headerTable3.addCell(cell3);
            }
            cell3.setPhrase(new Phrase(" "+a.getVouNoHoaItems().getHoaname()+" \nMINOR WORKS \nSHORT CODE : "+a.getVouNoHoaItems().getShortcode(), font5));
            cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell3.setPaddingTop(10f);
            cell3.setPaddingBottom(10f);
            cell3.setBorderWidthBottom(0.5f);
            headerTable3.addCell(cell3);

            cell3.setPhrase(new Phrase(" "+a.getVouNoHoaItems().getDebamt(), font5));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setPaddingTop(10f);
            cell3.setPaddingBottom(10f);
            cell3.setBorderWidthBottom(0.5f);
            headerTable3.addCell(cell3);

            cell3.setPhrase(new Phrase(" "+a.getVouNoHoaItems().getCreamt(), font5));
            cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell3.setPaddingTop(10f);
            cell3.setPaddingBottom(10f);
            cell3.setBorderWidthBottom(0.5f);
            headerTable3.addCell(cell3);

        }
        document.add(headerTable3);
        
        
        //*************************bottom table      **************************
        
        Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 11.5f, Font.NORMAL, BaseColor.BLACK);
        
        PdfPTable headerTable4 = new PdfPTable(2);
        headerTable4.setWidthPercentage(90.0f);
        headerTable4.setWidths(new float[] {50.0f, 50.0f});
        headerTable4.setSpacingBefore(40);
        headerTable4.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell4 = new PdfPCell();
        cell4.setBackgroundColor(BaseColor.WHITE);        
        cell4.setBorder(0);
        
        cell4.setPhrase(new Phrase("", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);  
        headerTable4.addCell(cell4); 
        
        cell4.setPhrase(new Phrase("( P.RAMACHANDRA RAJU ) ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        headerTable4.addCell(cell4);         
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("ACCOUNTS OFFICER", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);  
        cell4.setPaddingRight(10f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Pay & Accounts Officer, ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);       
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Heavy Water Plant, ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);       
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("MANUGURU.", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);        
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);       
        headerTable4.addCell(cell4);
        
        document.add(headerTable4);
        
    }
    
}
