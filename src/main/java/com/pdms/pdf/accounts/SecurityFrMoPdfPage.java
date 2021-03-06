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
import com.pdms.account.ItemsDto.VouNoHoaItemsDTO;
import com.pdms.despatch.itemsDto.PostageItemsDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.math.BigDecimal;
import java.util.List;
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
public class SecurityFrMoPdfPage extends AbstractPDFViewHelper{
    private static final Logger LOG = Logger.getLogger(SecurityFrMoPdfPage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<VouNoHoaItemsDTO> secObj = (List<VouNoHoaItemsDTO>) model.get("secObj");
        
        
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
        //String poo =  (String) model.get("selectDate");
        
        Paragraph headerPara_1 = new Paragraph("SECURITY DEPOSITE FOR THE MONTH OF : ");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.UNDERLINE));
        headerPara_1.setSpacingBefore(50f);
        document.add(headerPara_1);
        
        //********************main table ***************************************
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL);
        
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(90.0f);
        table.setSpacingBefore(20f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {8.0f, 20.0f, 30.0f, 10.0f, 10.0f, 10.0f, 12.0f});
        PdfPCell cell;
        
        cell = new PdfPCell(new Phrase("Sl.NO."));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Voucher/Challan \nNo/T.E & Date"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Name of The Supplier"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("P.O.No"));
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
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Remarks"));
        cell.setPaddingTop(5f);
        cell.setPaddingBottom(2f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);        
        table.addCell(cell);
        
        int x = 1;
        BigDecimal b = new BigDecimal(0);
        BigDecimal ca = new BigDecimal(0);
        BigDecimal b1 = new BigDecimal(0);
        BigDecimal ca1 = new BigDecimal(0);
        for (VouNoHoaItemsDTO a : secObj) {
            cell.setPhrase(new Phrase(" "+ (x++), font1));
            table.addCell(cell).setPaddingLeft(3);
            cell.setPhrase(new Phrase(" Voucher NO:"+a.getVouObj().getVoucherNo()+"\nDate:"+a.getVouObj().getVoucherDate(), font1));
            table.addCell(cell).setPaddingLeft(3);
            cell.setPhrase(new Phrase(" "+a.getVenObj().getVendorName(), font1));
            table.addCell(cell).setPaddingLeft(3);
            cell.setPhrase(new Phrase(" "+a.getPoNumBer(), font1));
            table.addCell(cell).setPaddingLeft(3);
            cell.setPhrase(new Phrase(" "+a.getDebamt(), font1));
            table.addCell(cell).setPaddingLeft(3);
            cell.setPhrase(new Phrase(" "+a.getCreamt(), font1));
            table.addCell(cell).setPaddingLeft(3);
            cell.setPhrase(new Phrase(" ", font1));
            table.addCell(cell).setPaddingLeft(3);
            b = ca.add(a.getDebamt());
            ca = b;
            b1 = ca1.add(a.getCreamt());
            ca1 = b1;
        }
        
        cell.setPhrase(new Phrase(" ", font1));        
        table.addCell(cell).setPaddingLeft(3);
        cell.setPhrase(new Phrase(" ", font1));        
        table.addCell(cell).setPaddingLeft(3);
        cell.setPhrase(new Phrase(" ", font1));        
        table.addCell(cell).setPaddingLeft(3);
        cell.setPhrase(new Phrase(" Total :", font1));        
        table.addCell(cell).setPaddingLeft(3);
        cell.setPhrase(new Phrase(" "+ca, font1));        
        table.addCell(cell).setPaddingLeft(3);
        cell.setPhrase(new Phrase(""+ca1, font1));        
        table.addCell(cell).setPaddingLeft(3);
        cell.setPhrase(new Phrase(" ", font1));        
        table.addCell(cell).setPaddingLeft(3);
        
        document.add(table);
        
        
        //**********************************************************************
        
      
        
        Font f = new Font(FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c = new Chunk("(P. RAMACHANDRA RAJU) \n SENIOR ACCOUNTS OFFICER", f);
        c.setBackground(BaseColor.WHITE);        
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(80f);
        p.setAlignment(Element.ALIGN_RIGHT);
        p.setIndentationRight(30f);
        document.add(p);
    }
}
