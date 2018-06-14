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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.account.ItemsDto.ItemsInVouDaEnDTO;
import com.pdms.account.dto.VoucherDTO;
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import com.pdms.helpers.AbstractPdfLandscapFour;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class vouDataEnPDFpage extends AbstractPdfLandscapFour{
    private static final Logger LOG = Logger.getLogger(vouDataEnPDFpage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<ItemsInVouDaEnDTO> VouObj = (List<ItemsInVouDaEnDTO>)model.get("vouitObj");
        //boolean setPageSize = document.setPageSize(PageSize.A4.rotate());
        
        
        //******************** table five **************************************
        Font font5 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLDITALIC);
        font5.setColor(BaseColor.BLACK);
        font5.setSize(11.0f);
        
        Font font6 = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLDITALIC);
        font6.setColor(BaseColor.BLACK);
        font6.setSize(12.0f);
        
        PdfPTable headerTable5 = new PdfPTable(2);
        headerTable5.setWidthPercentage(90.0f);
        headerTable5.setWidths(new float[] {6.0f, 94.0f});
        headerTable5.setSpacingBefore(40f);
        headerTable5.setSpacingAfter(10f);
        headerTable5.setHorizontalAlignment(Element.ALIGN_LEFT);
        
        PdfPCell cell5 = new PdfPCell();
        cell5.setBackgroundColor(BaseColor.WHITE);        
        cell5.setBorder(0);
        
        cell5.setPhrase(new Phrase("1", font5));        
        cell5.setPaddingBottom(5f);
         cell5.setPaddingLeft(40f);
        headerTable5.addCell(cell5);       
        cell5.setPhrase(new Phrase("GST NO: 36AAA---JHKHFKH798798", font5));        
        cell5.setPaddingBottom(5f);
        headerTable5.addCell(cell5); 
        
        cell5.setPhrase(new Phrase("2", font5));        
        cell5.setPaddingBottom(5f);
        cell5.setPaddingLeft(40f);
        headerTable5.addCell(cell5); 
        cell5.setPhrase(new Phrase("REGIONAL PURCHASE UNIT MANUGURU (HYDERABAD) \nDEPARTMENT OF ATOMIC ENERGY", font5));        
        cell5.setPaddingBottom(5f);
        headerTable5.addCell(cell5);
        
        cell5.setPhrase(new Phrase(" ", font5));        
        cell5.setPaddingBottom(0f);
        cell5.setPaddingLeft(40f);
        headerTable5.addCell(cell5); 
        cell5.setPhrase(new Phrase("GSTR - 2A \nPART - A", font6));  
        cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell5.setPaddingBottom(0f);
        headerTable5.addCell(cell5);
        
        cell5.setPhrase(new Phrase("3", font5));        
        cell5.setPaddingBottom(5f);
        cell5.setPaddingLeft(40f);
        headerTable5.addCell(cell5); 
        cell5.setPhrase(new Phrase("PERIOD FROM : , TO: ", font5)); 
        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell5.setPaddingBottom(5f);        
        headerTable5.addCell(cell5);
        
        cell5.setPhrase(new Phrase("4", font5));        
        cell5.setPaddingBottom(0f);
        cell5.setPaddingLeft(40f);
        headerTable5.addCell(cell5); 
        cell5.setPhrase(new Phrase("INWARD SUPPLIES RECEIVED FROM REGISTERED TAXABLE PERSONS", font5));
        cell5.setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell5.setPaddingBottom(0f);
        headerTable5.addCell(cell5);
        
        document.add(headerTable5);
        
        //**************************main table**********************************
        Font font1 = new Font(Font.FontFamily.HELVETICA, 9f, Font.UNDERLINE);
        
        Paragraph headerPara_1 = new Paragraph("  ");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        headerPara_1.setSpacingAfter(-5);
        document.add(headerPara_1);
        
        
        PdfPTable table = new PdfPTable(14);
        table.setWidthPercentage(95.0f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {5.0f, 11.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 8.0f, 5.0f, 7.0f, 5.0f, 7.0f, 5.0f, 7.0f});
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("S/N"));
        cell.setPaddingTop(15f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("GSTIN"));
        cell.setPaddingTop(15f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setRowspan(1);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("Invoice"));
        cell.setPaddingTop(15f);
        cell.setPaddingBottom(15f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(6);               
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("IGST"));
        cell.setPaddingTop(15f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("CGST"));
        cell.setPaddingTop(15f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        cell = new PdfPCell(new Phrase("SGST"));
        cell.setPaddingTop(15f);
        cell.setPaddingBottom(5f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setColspan(2);
        table.addCell(cell);
        
        table.addCell(" ");
        table.addCell(" ");
        table.addCell(new Phrase("No", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Date", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Value", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Goods/Ser", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("HSN/SAC", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Taxable Amt", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Rate", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Amt", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Rate", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Amt", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Rate", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        table.addCell(new Phrase("Amt", FontFactory.getFont(FontFactory.HELVETICA, 10)));
        
        int slno = 1;
        
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        
        for (ItemsInVouDaEnDTO Obj : VouObj) {
            table.addCell(new Phrase(" "+ (slno++), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getBillObj().getGstinNo(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getBillObj().getBillNo(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getBillObj().getBillDate(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getBillObj().getBillAmt(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getGbys(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getHsnCode(), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            BigDecimal qty = new BigDecimal(Obj.getQtyReceived());          
            table.addCell(new Phrase(qty.multiply(Obj.getRate())+" ", FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getIgst()+"%", FontFactory.getFont(FontFactory.HELVETICA, 9)));
            BigDecimal hun = new BigDecimal(100.00); 
            BigDecimal qtyAmt = new BigDecimal(0); 
            qtyAmt = qty.multiply(Obj.getRate());
            table.addCell(new Phrase(" "+decimalFormat.format(qtyAmt.multiply(Obj.getIgst()).divide(hun)), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getCgst()+"%", FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+decimalFormat.format(qtyAmt.multiply(Obj.getCgst()).divide(hun)), FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+Obj.getSgst()+"%", FontFactory.getFont(FontFactory.HELVETICA, 9)));
            table.addCell(new Phrase(" "+decimalFormat.format(qtyAmt.multiply(Obj.getSgst()).divide(hun)), FontFactory.getFont(FontFactory.HELVETICA, 9)));
        }
        
        document.add(table);
        
        
    }
    
    
}
