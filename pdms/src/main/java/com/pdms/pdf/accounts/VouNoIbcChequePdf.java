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
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
public class VouNoIbcChequePdf extends AbstractPDFViewHelper{
    private static final Logger log = Logger.getLogger(VouNoIbcChequePdf.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<VoucherNoDTO> vouIbcObj = (List<VoucherNoDTO>)model.get("vouNoIbcObj");
        
        for(VoucherNoDTO obj: vouIbcObj){
        
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
        
        cell.setPhrase(new Phrase("No.RPUM/ACCTS/DDR/0", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Date :"+obj.getChequeDate(), font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("To ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("The Branch manager, ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("State Bank of India, ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Cherapally Branch, ECIL,  ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("HYDERABAD - 500 062. ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Dear Sir/Madam", font));
        cell.setPaddingTop(30f);
        headerTable.addCell(cell).setPaddingLeft(60);         
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        document.add(headerTable);      
        
        
        Font f = new Font(FontFamily.HELVETICA, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c = new Chunk("Sub:- Retiring of Document against IBC No(s) Mentioned below against each.  ", f);
        c.setBackground(BaseColor.WHITE);
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(30f);
        p.setIndentationLeft(90f);
        document.add(p);
        
        //********************************* TABLE 3 ****************************
        
        Font font3 = FontFactory.getFont(FontFactory.HELVETICA);
        font3.setColor(BaseColor.BLACK);
        font3.setSize(10.0f);
        
        PdfPTable headerTable3 = new PdfPTable(5);
        headerTable3.setWidthPercentage(90.0f);
        headerTable3.setWidths(new float[] {10.0f, 10.0f, 40.0f, 20.0f, 20.0f});
        headerTable3.setSpacingBefore(40);
        headerTable3.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell3 = new PdfPCell();
        cell3.setBackgroundColor(BaseColor.WHITE);        
        cell3.setPadding(1);
       
        
        
        cell3.setPhrase(new Phrase("SL No", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("IBC NO", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("Name Of The Vendor", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase("IBC Amount", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("Amount paid", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell3.setPaddingTop(5f);
        cell3.setPaddingBottom(10f);
        headerTable3.addCell(cell3);        
        
        
        cell3.setPhrase(new Phrase("1 ", font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" "+obj.getIbcNumber(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" "+obj.getSupplierName(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);
        cell3.setPhrase(new Phrase(" "+obj.getChequeAmount(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase(" "+obj.getChequeAmount(), font3));
        cell3.setFixedHeight(70f);
        headerTable3.addCell(cell3);        
        
        
        document.add(headerTable3);
        
        StringBuilder declStr = new StringBuilder();
        declStr.append("Cheque No. "+obj.getChequeNo()+", Dated  "+obj.getChequeDate()+"  for an amount of Rs.  "+obj.getChequeAmount());
        declStr.append(" is enclosed herewith. The receipt of the above cheque may please be acknowledged.");
        
       
        Paragraph declartionPara = new Paragraph(declStr.toString());
        declartionPara.setAlignment(Element.ALIGN_JUSTIFIED);
        declartionPara.setFont(FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL));
        declartionPara.setSpacingBefore(30f);
        declartionPara.setIndentationLeft(30f);
        declartionPara.setIndentationRight(30f);
        document.add(declartionPara);
        
        //*****************************
        
        Font f1 = new Font(FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk en = new Chunk("Encl: a.a.  ", f1);
        en.setBackground(BaseColor.WHITE);
        Paragraph pen = new Paragraph(en);
        pen.setSpacingBefore(20f);
        pen.setIndentationLeft(60f);
        document.add(pen);
        
        Font font4 = FontFactory.getFont(FontFactory.HELVETICA);
        font4.setColor(BaseColor.BLACK);
        font4.setSize(10.0f);
        
        PdfPTable headerTable4 = new PdfPTable(1);
        headerTable4.setWidthPercentage(90.0f);
        headerTable4.setWidths(new float[] {100.0f});
        headerTable4.setSpacingBefore(20);        
        headerTable4.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell4 = new PdfPCell();
        cell4.setBackgroundColor(BaseColor.WHITE);        
        cell4.setPadding(1);
        cell4.setBorder(0);
        
        cell4.setPhrase(new Phrase("Yours Faithfully,", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setPaddingTop(5f);
        cell4.setPaddingBottom(20f);
        cell4.setPaddingRight(30f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("(P. RAMACHANDRA RAJU)", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setPaddingTop(5f);
        cell4.setPaddingBottom(0f);
        cell4.setPaddingRight(0f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Sr. Accounts officer", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);        
        cell4.setPaddingBottom(0f);
        cell4.setPaddingRight(15f);
        headerTable4.addCell(cell4);
        
        document.add(headerTable4);
    }
    }
}
