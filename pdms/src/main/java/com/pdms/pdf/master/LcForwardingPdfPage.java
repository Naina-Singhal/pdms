/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.master;

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
import com.pdms.account.dto.VoucherNoDTO;
import com.pdms.dto.LrEntryDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.text.DateFormat;
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
 * @author ramakrishna
 */
@Component
public class LcForwardingPdfPage extends AbstractPDFViewHelper{
    private static final Logger logger = Logger.getLogger(LcForwardingPdfPage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        List<LrEntryDTO> lrrObj = (List<LrEntryDTO>)model.get("lrObj");
        
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();        
        
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(30f);
        document.add(headerPara);
        
        //*********************Main Heder***************************************
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
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("City Office", fontb));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("ECIL ROAD, ECIL Post,", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setPaddingLeft(30);        
        cell.setPhrase(new Phrase("", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("HYDERABAD - 500062", font));
        headerTable.addCell(cell);
        
        for(LrEntryDTO s: lrrObj){
           
        
        cell.setPhrase(new Phrase("Ref.No.:- ", font));
        cell.setPaddingTop(30f);
        headerTable.addCell(cell).setPaddingLeft(30);         
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);        
        cell.setPhrase(new Phrase("Date:   "+dateFormat.format(date), font));
        headerTable.addCell(cell);
        
        document.add(headerTable);  
        
        //****************************SUB**************************************
        Font f = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c = new Chunk("Sub: Forwarding of L.R. against P.O.No. \n"+s.getPoNumber()+" \nDated:", f);
        c.setBackground(BaseColor.WHITE);        
        Paragraph p = new Paragraph(c);
        p.setSpacingBefore(20f);
        p.setAlignment(Element.ALIGN_LEFT);
        p.setIndentationLeft(150f);
        document.add(p);
        
        Font f1 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cc = new Chunk("* * * * * * * * * * ", f1);
        cc.setBackground(BaseColor.WHITE);        
        Paragraph pc = new Paragraph(cc);
        pc.setSpacingBefore(20f);
        pc.setAlignment(Element.ALIGN_CENTER);        
        document.add(pc);
        
        //*********************************Main Pera***************************
        Font f2 = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        String pera = "The consignee copy of L.R.(in original) No. "+s.getLrNumber()+"      Dated:  "+s.getLrDate()
                + ".\nof M/S. "+s.getVenObj().getVendorName()+".     alongwith DC/Invoice No. "+s.getBillNumber()+". Dated: "+s.getBillDate()+".\n"
                +"received  from M/S. "+s.getNameTransporter()+"., \n"
                +"for having despatched the items against subject P.O is/are forwarded herewith for \nclearance "
                +"of consignment at your end please. The receipt of same may please be acknowledged.";
        Chunk cc1 = new Chunk(pera, f2);
        cc1.setBackground(BaseColor.WHITE);        
        Paragraph pc1 = new Paragraph(cc1);
        pc1.setSpacingBefore(20f);
        pc1.setAlignment(Element.ALIGN_LEFT);    
        pc1.setIndentationLeft(30f);
        pc1.setIndentationRight(40f);
        document.add(pc1);
        
        
         //*********************Main Buttom***************************************
        PdfPTable headerTable1 = new PdfPTable(2);
        headerTable1.setWidthPercentage(90.0f);
        headerTable1.setWidths(new float[] {48.0f, 48.0f});
        headerTable1.setSpacingBefore(60);
        
        
        Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
        font1.setColor(BaseColor.BLACK);
        font1.setSize(10.0f);
                
        PdfPCell cell1 = new PdfPCell();
        cell1.setBackgroundColor(BaseColor.WHITE);
        cell1.setBorder(0);
        cell1.setPadding(1);
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase("(G.C.Xxxxxxxx)", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);        
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase("Asst. Purchase Officer", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        cell1.setPhrase(new Phrase("Stores  Officer,", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        cell1.setPhrase(new Phrase("Central  Stores  Unit, ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER); 
        
        cell1.setPhrase(new Phrase("HWP(M),  Manuguru.", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase("  ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase(" ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        cell1.setPhrase(new Phrase("CC to : Shri . P.SATHISH ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase("Process    104", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell1.setPhrase(new Phrase("wrt.Ind.No. IP/XU/2016/1948 ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase("Dated: xxxxx ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);
        
        cell1.setPhrase(new Phrase("IO GROUP : Dy.GM(P) ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_LEFT);        
        cell1.setPhrase(new Phrase("  ", font1));
        headerTable1.addCell(cell1).setHorizontalAlignment(Element.ALIGN_CENTER);
        
        document.add(headerTable1);
        
    }
    }
    
}
