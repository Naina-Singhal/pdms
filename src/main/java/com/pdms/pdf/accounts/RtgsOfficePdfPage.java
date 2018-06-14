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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
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
 * @author myassessment
 */
@Component
public class RtgsOfficePdfPage extends AbstractPDFViewHelper{

    private static final Logger LOG = Logger.getLogger(RtgsOfficePdfPage.class.getName());
    
     @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<VoucherNoDTO> rtObj = (List<VoucherNoDTO>) model.get("rtgObj");
        
        
        //******************** table five **************************************
        Font font5 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLDITALIC);
        font5.setColor(BaseColor.BLACK);
        font5.setSize(11.0f);
        
        Font font6 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLDITALIC);
        font6.setColor(BaseColor.BLACK);
        font6.setSize(12.0f);
        
        PdfPTable headerTable5 = new PdfPTable(1);
        headerTable5.setWidthPercentage(90.0f);
        headerTable5.setWidths(new float[] {100.0f});
        headerTable5.setSpacingBefore(60f);
        headerTable5.setSpacingAfter(10f);
        headerTable5.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell cell5 = new PdfPCell();
        cell5.setBackgroundColor(BaseColor.WHITE);        
        cell5.setBorder(0);
        
             
        cell5.setPhrase(new Phrase("REGIONAL PURCHASE UNIT(MANUGURU)", font5));        
        cell5.setPaddingBottom(5f);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable5.addCell(cell5); 
        
        cell5.setPhrase(new Phrase("ACCOUNTS SECTION - HYDERABAD", font5));        
        cell5.setPaddingBottom(5f);
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerTable5.addCell(cell5); 
        for (VoucherNoDTO obj : rtObj) {
             cell5.setPhrase(new Phrase("Details Of Cheque(s) issued for RTGS/NEFT to SBI, ECIL Branch, Hyderabad on: "+obj.getChequeDate(), font5));
             cell5.setPaddingBottom(5f);
             cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
             headerTable5.addCell(cell5);
         }
        
        
        document.add(headerTable5);
        
        //********************main table ***************************************
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9f, Font.UNDERLINE);
        
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(90.0f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {10.0f, 15.0f, 35.0f, 15.0f, 15.0f, 10.0f});
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("PO No"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Voucher No \nVoucher Date"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Vendor Name And\nBank Details"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("IFSC Code"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("CQE NO./\nCQE Date"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Amount\nRs"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        int count = 0;
        
         for (VoucherNoDTO obj : rtObj) {
             count++;
             table.addCell(new Phrase(" "+obj.getPoNumber(), FontFactory.getFont(FontFactory.HELVETICA, 9f)));
             table.addCell(new Phrase(" "+obj.getVoucherNo()+"\n"+obj.getVoucherDate(), FontFactory.getFont(FontFactory.HELVETICA, 9f)));             
             table.addCell(new Phrase(" "+obj.getVenObj().getVendorName()+"\nBank Name:"+obj.getBankObj().getBankName()+"\nA/c:"+obj.getRtgsObj().getAccountNo(), FontFactory.getFont(FontFactory.HELVETICA, 9f)));
             table.addCell(new Phrase(" "+obj.getRtgsObj().getBranch()+"\n"+obj.getRtgsObj().getIfscCode(), FontFactory.getFont(FontFactory.HELVETICA, 9f)));
             table.addCell(new Phrase(" "+obj.getChequeNo()+"\n"+obj.getChequeDate(), FontFactory.getFont(FontFactory.HELVETICA, 9f)));
             table.addCell(new Phrase(" "+obj.getChequeAmount(), FontFactory.getFont(FontFactory.HELVETICA, 9f)));
         }
        
        
        
        document.add(table);
        
        //*********************bottom table     ********************************
        Font font7 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLDITALIC);
        font7.setColor(BaseColor.BLACK);
        font7.setSize(11.0f);
        
        Font font8 = FontFactory.getFont(FontFactory.HELVETICA, Font.NORMAL);
        font8.setColor(BaseColor.BLACK);
        font8.setSize(10.0f);
        
        PdfPTable table1 = new PdfPTable(3);
        table1.setWidthPercentage(90.0f);
        table1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.setSpacingBefore(20f);
        table1.setWidths(new float[] {30.0f, 40.0f, 30.0f});
        
        PdfPCell cell1 = new PdfPCell();
        cell1.setBackgroundColor(BaseColor.WHITE);        
        cell1.setBorder(0);
        
        cell1.setPhrase(new Phrase("TOTAL CHEQUES", font7));        
        cell1.setPaddingBottom(0f);
        cell1.setPaddingLeft(10f);
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase(" ", font7));        
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase(" ", font7));       
        table1.addCell(cell1); 
        
        cell1.setPhrase(new Phrase(" "+count, font7));       
        cell1.setPaddingLeft(40f);
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase("OFFICE COPY", font7));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase(" ", font7)); 
        table1.addCell(cell1);
        
        cell1.setPhrase(new Phrase(" ", font7));  
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase("Received above all cheques", font8));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase("Sr. Accounts officer ", font7)); 
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(cell1);
        
        cell1.setPhrase(new Phrase(" ", font7));  
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase("BM, SBI, ECIL BRANCH", font7));
        cell1.setPaddingTop(40f);
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(cell1); 
        cell1.setPhrase(new Phrase(" ", font7)); 
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(cell1);
        
        
        
        document.add(table1);
    }
}
