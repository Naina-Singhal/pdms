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
public class RtgsLetterPdf extends AbstractPDFViewHelper{
    private static final Logger LOG = Logger.getLogger(RtgsLetterPdf.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<VoucherNoDTO> rtgsVouObj = (List<VoucherNoDTO>)model.get("rtgsObj");
        
        for(VoucherNoDTO obj: rtgsVouObj){
        
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
        
        cell.setPhrase(new Phrase("No.RPUM/ACCTS/RTGS-NEFT/11-12/", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Date :"+obj.getChequeDate(), font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("The Manager(Govt. Accounts),", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("State bank Of India,", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("E.C.I.L Branch,", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("A/c No. 333334547855", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_RIGHT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("HYDERABAD - 500 062. TS", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Sir/Madam,", font));
        cell.setPaddingTop(20f);
        headerTable.addCell(cell).setPaddingLeft(60);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        document.add(headerTable); 
        //**********************************************************************
        
        Font f = new Font(Font.FontFamily.HELVETICA, 11.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk c = new Chunk("Sub:- Remittance of Cheque through NEFT/RTGS - Reg.", f);
        c.setBackground(BaseColor.WHITE);
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(30f);
        p.setIndentationLeft(150f);
        document.add(p);
        
        StringBuilder declStr = new StringBuilder();
        declStr.append("       Please find enclosed cheque bearing No. "+obj.getChequeNo()+"  Dated : "+obj.getChequeDate()+" ");
        declStr.append(" to remit the above sum through NEFT/RTGS as per the details given below;");
        
       
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
        
        PdfPTable headerTable3 = new PdfPTable(2);
        headerTable3.setWidthPercentage(90.0f);
        headerTable3.setWidths(new float[] {50.0f, 50.0f});
        headerTable3.setSpacingBefore(20);
        headerTable3.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell3 = new PdfPCell();
        cell3.setBackgroundColor(BaseColor.WHITE);        
        cell3.setPadding(1);
        cell3.setBorder(0);
        
        cell3.setPhrase(new Phrase("NAME OF THE BENEFICIERY                     :", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.5f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("THE ANDHRA SUGAR LIMITED,", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.5f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3); 
        
        cell3.setPhrase(new Phrase("DESTINATION BANK'S NAME                     :", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f); 
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("ANDHRA BANK,", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3); 
        
         cell3.setPhrase(new Phrase("DESGINATION BANK'S BRANCH                  :", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f); 
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("VENKATAPURAM TANUKU, TANUKU,", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3); 
        
         cell3.setPhrase(new Phrase("IFSC CODE IF BRANCH/BANK                   :", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f); 
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("ANDB0000354,", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3); 
        
         cell3.setPhrase(new Phrase("ACCOUNT NUMBER                             :", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f); 
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("79797979797979,", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3); 
        
         cell3.setPhrase(new Phrase("AMOUNT                                     :", font3)); 
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f); 
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3);        
        cell3.setPhrase(new Phrase("Rs. 45,360   /- ,", font3));
        cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell3.setPaddingTop(2f);
        cell3.setPaddingBottom(5f);
        cell3.setBorderWidthTop(0.0f);
        cell3.setBorderColorTop(BaseColor.BLACK);
        headerTable3.addCell(cell3); 
        
        document.add(headerTable3);
        
        //**********************************************************************
        Font font4 = FontFactory.getFont(FontFactory.HELVETICA);
        font4.setColor(BaseColor.BLACK);
        font4.setSize(8.0f);
        
        Font font5 = FontFactory.getFont(FontFactory.HELVETICA);
        font5.setColor(BaseColor.BLACK);
        font5.setSize(10.0f);
        
        PdfPTable headerTable4 = new PdfPTable(1);
        headerTable4.setWidthPercentage(90.0f);
        headerTable4.setWidths(new float[] {100.0f});
        headerTable4.setSpacingBefore(0f);
        headerTable4.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell4 = new PdfPCell();
        cell4.setBackgroundColor(BaseColor.WHITE);        
        cell4.setPadding(1);
        cell4.setBorder(0);
        
        cell4.setPhrase(new Phrase("RUPEES FOURTY-FIVE THOUSAND THREE HUNDRED SIXTY ONLY:", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.2f);
        cell4.setBorderColorTop(BaseColor.BLACK);
        cell4.setPaddingLeft(150f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Total cheque amount Rs:     45,360.00", font5)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.2f);
        cell4.setBorderColorTop(BaseColor.BLACK);
        cell4.setPaddingLeft(250f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Kindly acknowledge receipt of the above cheque.", font5)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.0f);
        cell4.setBorderColorTop(BaseColor.BLACK);
        cell4.setPaddingLeft(90f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Thanking you,", font5)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.0f);
        cell4.setBorderColorTop(BaseColor.BLACK);
        cell4.setPaddingLeft(150f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("Encl: a.a.", font5)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.0f);
        cell4.setBorderColorTop(BaseColor.BLACK); 
        cell4.setPaddingLeft(0f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase(" ", font4)); 
        cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.0f);
        cell4.setBorderColorTop(BaseColor.BLACK);
        cell4.setPaddingLeft(30f);
        headerTable4.addCell(cell4);
        
        cell4.setPhrase(new Phrase("SR.ACCOUNTS OFFICER ", font5)); 
        cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell4.setPaddingTop(2f);
        cell4.setPaddingBottom(5f);
        cell4.setBorderWidthBottom(0.0f);
        cell4.setBorderColorTop(BaseColor.BLACK);        
        headerTable4.addCell(cell4);
        
        document.add(headerTable4);
        }
    }
    
}
