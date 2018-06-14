/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.purchase;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import com.pdms.purchase.dto.BankGuaranteeDTO;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author naagu
 */
@Component
public class BankGuaranteePdfPage extends AbstractPDFViewHelper{
    private static final Logger log = Logger.getLogger(BankGuaranteePdfPage.class.getName());
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
                
        List<PoEntryDTO> poObj = (List<PoEntryDTO>) model.get("poObj");        
         List<IndentFormDTO> indentObj = (List<IndentFormDTO>) model.get("inObj");         
         List<IndentFileMappingDTO> groupObj = (List<IndentFileMappingDTO>) model.get("gropObj");
         List<BankGuaranteeDTO> baObj = (List<BankGuaranteeDTO>) model.get("baObj");
         String PoNumber = "";
         for(PoEntryDTO p: poObj){
             for(IndentFileMappingDTO g: groupObj){
                 PoNumber = " "+g.getGroupDTO().getGroupName()+" / "+p.getTenderFN()+" / "+p.getPoNumber();
             }
         }
         
         String indFistName = "";
         String indSecName = "";
         for(IndentFormDTO i: indentObj){
             indFistName = i.getEmpProfileDTo().getFirstName();
             indSecName = i.getEmpProfileDTo().getLastName();
         }
        
        Font heading = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
         
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(0f);
        document.add(headerPara);
        
        
        String img = "/WEB-INF/emblem.png";        
        String absoluteDiskPath = getServletContext().getRealPath(img);        
        Image imghead = Image.getInstance(absoluteDiskPath);        
        imghead.setAbsolutePosition(280f, 730f);
        imghead.scaleAbsolute(40, 52);
        imghead.setDpi(3, 3);
        document.add(imghead);
        
        
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(90.0f);
        headerTable.setWidths(new float[] {50.0f, 50.0f});
        headerTable.setSpacingBefore(40f);
        
        
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
        
        String ttfFile = "/WEB-INF/mangal.ttf"; 
        String ttfPath = getServletContext().getRealPath(ttfFile);
        BaseFont unicode = BaseFont.createFont(ttfPath, 
        BaseFont.IDENTITY_H,    BaseFont.EMBEDDED);
        Font font10 = new Font(unicode,9,Font.NORMAL,new BaseColor(0,0,0));
        
        
        cell.setPhrase(new Phrase("भारत सरकार / Government of India", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell); 
        cell.setPhrase(new Phrase("ECIL Road, ECIL Post, ", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("परमाणु ऊर्जा विभाग / Department of Atomic Energy", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell); 
        cell.setPhrase(new Phrase("हैदराबाद / hyderabad", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("क्रयएवंभंडारनिदेशालय / Directorate of Purchase & Stores", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("Mgr. (P) : 040-27181210", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("क्षेत्रीयक्रयएकक,(मनुगुरु) / Regional Purchase Unit, (MANUGURU)", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("फैक्स / fax : 040-27143516", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(" ", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("APO. : 040-27181211", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(" ", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("SAO. : 040-27181215", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        
        cell.setPhrase(new Phrase("E - Mail : apo.rpum-dae@gov.in", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("दिनांक /date : "+dateFormat.format(date), font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        document.add(headerTable);
        
        
        Font f6 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c6 = new Chunk("Purchase Oreder No : DPS / RPUM / "+PoNumber+"                                                                 Date:"+dateFormat.format(date), f6);
        c6.setBackground(BaseColor.WHITE);
        Paragraph p6 = new Paragraph(c6);
        p6.setAlignment(Element.ALIGN_LEFT);
        p6.setSpacingBefore(30f); 
        p6.setIndentationLeft(30f);
        document.add(p6);
        for(BankGuaranteeDTO p: baObj){
        Font f5 = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5 = new Chunk("M/s :"+p.getVenObj().getVendorName()+",\n"
                + ""+p.getVenObj().getVendorAddress()+",\n"
                + ""+p.getVenObj().getVendorCity()+",\n"
                + ""+p.getVenObj().getVendorPin(), f5);
        c5.setBackground(BaseColor.WHITE);
        Paragraph p5 = new Paragraph(c5);
        p5.setAlignment(Element.ALIGN_LEFT);
        p5.setSpacingBefore(10f);       
        p5.setIndentationLeft(30f); 
        document.add(p5);
        }
        
        String bgDet = "";
        for(BankGuaranteeDTO b: baObj){
            bgDet = b.getTempone();
        }
        Font f7 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c7 = new Chunk(" "+bgDet, f7);
        c7.setBackground(BaseColor.WHITE);
        Paragraph p7 = new Paragraph(c7);
        p7.setAlignment(Element.ALIGN_CENTER);
        p7.setSpacingBefore(10f);        
        document.add(p7);
        
        Font f8 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c8 = new Chunk("Dear Sir,", f8);
        c8.setBackground(BaseColor.WHITE);
        Paragraph p8 = new Paragraph(c8);
        p8.setAlignment(Element.ALIGN_LEFT);
        p8.setSpacingBefore(10f);       
        p8.setIndentationLeft(30f);
        document.add(p8);
        
        String poDate = "";
        for(PoEntryDTO p: poObj){
            poDate = p.getPoDate();
        }
        Font f9 = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c9 = new Chunk("Sub : Our P.O.No. RPUM / HWP /"+PoNumber+"\n          Dated : "+poDate+",", f9);
        c9.setBackground(BaseColor.WHITE);
        Paragraph p9 = new Paragraph(c9);
        p9.setAlignment(Element.ALIGN_LEFT);
        p9.setSpacingBefore(10f);       
        p9.setIndentationLeft(90f);
        document.add(p9);        
        
        
        
        
        for(BankGuaranteeDTO b: baObj){
            
        
        
        
        Chunk c10 = new Chunk("Ref : Your Bank Guarantee No. "+b.getBgNumber()+"                       Dated :"+b.getBgDate()+" \n", f9);
        c10.setBackground(BaseColor.WHITE);
        Paragraph pa = new Paragraph(c10);
        pa.setAlignment(Element.ALIGN_LEFT);
        pa.setSpacingBefore(2f);       
        pa.setIndentationLeft(90f);
        document.add(pa);
        
        Chunk c11 = new Chunk("********************", f9);
        c11.setBackground(BaseColor.WHITE);
        Paragraph pa1 = new Paragraph(c11);
        pa1.setAlignment(Element.ALIGN_CENTER);
        pa1.setSpacingBefore(20f);       
        document.add(pa1);
        
        Chunk cb = new Chunk("Yhe Bank Guarantee No. "+b.getBgNumber()+"           Date ."+b.getBgDate()+" for Rs. "+b.getAmount()
                + "Valid upto xxxx  issued by "+b.getBankObj().getBankName()+" - "+b.getBankObj().getBranch()+", and submitted by you against subject order as "
                +b.getTempone()+" \n\n"
                + "The Competent Authority has duly discharged the Bank Guarantee on completion of the contract and the same"
                + "is returned herewith."
                + "\n\n"
                + "This is, however, issued without prejudice to our rights under the terms and conditions of the order. ", f9);
        cb.setBackground(BaseColor.WHITE);
        Paragraph pb = new Paragraph(cb);
        pb.setAlignment(Element.ALIGN_JUSTIFIED);
        pb.setSpacingBefore(30f);       
        pb.setIndentationLeft(60f);
        pb.setIndentationRight(30f);
        document.add(pb);
        
        Chunk cc = new Chunk("Your faithfully,", f9);
        cc.setBackground(BaseColor.WHITE);
        Paragraph pc = new Paragraph(cc);
        pc.setAlignment(Element.ALIGN_RIGHT);
        pc.setSpacingBefore(30f);       
        pc.setIndentationRight(90f);
        document.add(pc);
        
        Font fsi = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cd = new Chunk("(G.CPAUL)", fsi);
        cd.setBackground(BaseColor.WHITE);
        Paragraph pd = new Paragraph(cd);
        pd.setAlignment(Element.ALIGN_RIGHT);
        pd.setSpacingBefore(10f);       
        pd.setIndentationRight(120f);
        document.add(pd);
        
        Chunk ce = new Chunk("Asst Purchase Office", fsi);
        ce.setBackground(BaseColor.WHITE);
        Paragraph pe = new Paragraph(ce);
        pe.setAlignment(Element.ALIGN_RIGHT);
        pe.setSpacingBefore(0f);       
        pe.setIndentationRight(100f);
        document.add(pe);
        
        Chunk cf = new Chunk("for & on behalf of The President Of India", fsi);
        cf.setBackground(BaseColor.WHITE);
        Paragraph pf = new Paragraph(cf);
        pf.setAlignment(Element.ALIGN_RIGHT);
        pf.setSpacingBefore(0f);       
        pf.setIndentationRight(80f);
        document.add(pf);
        
        Chunk cg = new Chunk("(The Purchaser)", fsi);
        cg.setBackground(BaseColor.WHITE);
        Paragraph pg = new Paragraph(cg);
        pg.setAlignment(Element.ALIGN_RIGHT);
        pg.setSpacingBefore(0f);       
        pg.setIndentationRight(110f);
        document.add(pg);
        
        Chunk ch = new Chunk("CC to : 1) Sr.Account Officer, RPUM\n"
                + "             2)Stores Officer, HWPM\n "
                + "3) "+indFistName+"  "+indSecName, f8);
        ch.setBackground(BaseColor.WHITE);
        Paragraph ph = new Paragraph(ch);
        ph.setAlignment(Element.ALIGN_LEFT);
        ph.setSpacingBefore(20f);       
        ph.setIndentationLeft(60f);
        document.add(ph);
        
        document.newPage();
        
        //*********************NEW PAGE 2 **************************************
        
        
        document.add(headerPara);
        document.add(imghead);
        document.add(headerTable);
        
        Chunk cI = new Chunk("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * * * * * * * * * * * * * * * * * * ", fsi);
        cI.setBackground(BaseColor.WHITE);
        Paragraph pi = new Paragraph(cI);
        pi.setAlignment(Element.ALIGN_CENTER);
        pi.setSpacingBefore(0f);       
        document.add(pi);
        
        document.add(p6);
        
        for(BankGuaranteeDTO p: baObj){
        Font f5 = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5 = new Chunk("M/s :"+p.getVenObj().getVendorName()+",\n"
                + ""+p.getVenObj().getVendorAddress()+",\n"
                + ""+p.getVenObj().getVendorCity()+",\n"
                + ""+p.getVenObj().getVendorPin(), f5);
        c5.setBackground(BaseColor.WHITE);
        Paragraph p5 = new Paragraph(c5);
        p5.setAlignment(Element.ALIGN_LEFT);
        p5.setSpacingBefore(10f);       
        p5.setIndentationLeft(30f); 
        document.add(p5);
        }
        
        document.add(p7);
        document.add(p8);
        document.add(p9);  
        document.add(pa);
        
        Chunk cba = new Chunk("We are in receipt of Bank Guarantee No. "+b.getBgNumber()+"           Date ."+b.getBgDate()+" for Rs. "+b.getAmount()
                + "Valid upto "+b.getExpireDate()+"  issued by  "+b.getBankObj().getBankName()+", "+b.getBankObj().getBranch()+", and submitted by you against subject order as "
                + bgDet+" \n\n"
                + "The Competent Authority has Accepted the Bank Guarantee."               
                + "\n\n"
                + "This is, however, issued without prejudice to our rights under the terms and conditions of the order. ", f9);
        cba.setBackground(BaseColor.WHITE);
        Paragraph pba = new Paragraph(cba);
        pba.setAlignment(Element.ALIGN_JUSTIFIED);
        pba.setSpacingBefore(30f);       
        pba.setIndentationLeft(60f);
        pba.setIndentationRight(30f);
        document.add(pba);
        
        document.add(pc);
        document.add(pd);
        document.add(pe);
        document.add(pf);
        document.add(pg);
        
        Chunk cha = new Chunk("CC to : 1) Sr.Account Officer, RPUM\n"
                + "             2)Stores Officer, HWPM \n"
                + "             3) Indentor Name: "+indFistName+" "+indSecName+" \n"
                + "             Sec / Group: 102 Dy.GM(P)", f8);
        cha.setBackground(BaseColor.WHITE);
        Paragraph pha = new Paragraph(cha);
        pha.setAlignment(Element.ALIGN_LEFT);
        pha.setSpacingBefore(20f);       
        pha.setIndentationLeft(60f);
        document.add(pha);
        
        document.newPage();
        
        //************************NEW PAGE NO 3   ******************************
        
        
        Chunk cg1 = new Chunk("SL No : "+b.getSlno(), fsi);
        cg1.setBackground(BaseColor.WHITE);
        Paragraph pg1 = new Paragraph(cg1);
        pg1.setAlignment(Element.ALIGN_LEFT);
        pg1.setSpacingBefore(50f);       
        pg1.setIndentationLeft(30);
        document.add(pg1);
        
        Font fsi3 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk cg2 = new Chunk("BANK GUARANTEE / ACCEPTANCE / DISCHARGE & RETURN / EXTENTION OF\n EXISTING GUARANTEE", fsi3);
        cg2.setBackground(BaseColor.WHITE);
        Paragraph pg2 = new Paragraph(cg2);
        pg2.setAlignment(Element.ALIGN_CENTER);
        pg2.setSpacingBefore(30f);       
        pg2.setIndentationLeft(30);
        document.add(pg2);
        
        PdfPTable Table = new PdfPTable(2);
        Table.setWidthPercentage(90.0f);
        Table.setWidths(new float[] {50.0f, 50.0f});
        Table.setSpacingBefore(20f);
        
        PdfPCell cel = new PdfPCell();
        cel.setBackgroundColor(BaseColor.WHITE);
        cel.setBorder(0);
        cel.setPadding(1);
        
        Font tab = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Font tab1 = new Font(Font.FontFamily.TIMES_ROMAN, 10.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel.setPhrase(new Phrase("(1)   PURCHASE ORDER NO. & DATE", tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": RPUM/HWP/ "+PoNumber+"          Date : "+poDate, tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(2)   NAME OF THE SUPPLIER", tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": M/S. "+b.getVenObj().getVendorName(), tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(3)   BANK GUARANTEE NO. & DATE", tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(":  "+b.getBgNumber()+"       Date:"+b.getBgDate(), tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(4)   NAME OF THE BANK & ADDRESS", tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": "+b.getBankObj().getBankName()+", "+b.getBankObj().getBranch(), tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(5)   AMOUNT", tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": Rs."+b.getAmount(), tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(6)   PURPOSE", tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": "+b.getTempone(), tab));
        cel.setPaddingLeft(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase(" T O W", tab1));
        cel.setPaddingRight(0f);
        cel.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cel.setBorderWidthBottom(0.1f);
        cel.setPaddingBottom(5f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(" A R D", tab1));
        cel.setPaddingLeft(0f);
        cel.setHorizontalAlignment(Element.ALIGN_LEFT);
        cel.setBorderWidthBottom(0.1f);
        cel.setPaddingBottom(5f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("SECURITY     ADVANCE", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase("PROGRESS PAYMENT      PERFORMANCE BOND", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("DEPOSITE     PAYMENT", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase("--------------------", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase(" ", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0.1f);
        cel.setPaddingBottom(5f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase("|     ||      |||", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0.1f);
        cel.setPaddingBottom(5f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase(" ", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0.1f);
        cel.setPaddingBottom(30f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(" ", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0.1f);
        cel.setPaddingBottom(30f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(7)   VALID UPTO\n       EXTENDED UPTO ", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": "+b.getExpireDate(), tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(8)   GRACE PERIOD", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": "+b.getGracePeriod(), tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(9)   ENTRY NO. IN B.G.REGISTER ", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": "+b.getSlno(), tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("(10)  IF DISCHARGING EASON FOR\n"
                + "         DISCHARGING / RETURNING THE BANK\n          GUARANTEE TO SUPPLIER", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase(": "+b.getRemarks(), tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel);
        
        cel.setPhrase(new Phrase("", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel); 
        cel.setPhrase(new Phrase("a) C.S.R.V.________________________at page     /C\n"
                + "b) I/O's Confirmation at page_______________C/N\n"
                + "c0 SO's Confirmation at page________________C/N\n"
                + "d) Fresh Guarantee in lieu of the expired B.C \n"
                + "     towards\n"
                + "     SD/Advance payment/ Progress payment / \n"
                + "     Perfirmance Bond received.", tab));
        cel.setPaddingLeft(0f);
        cel.setBorderWidthBottom(0f);
        cel.setPaddingBottom(3f);
        Table.addCell(cel);
        
        
        
        document.add(Table);
        
        Font fsi4 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cg3 = new Chunk("APO may kindly sign on the pages of BG and on letter to the supplier, accepting / discharging the above Bank "
                + "Guarantee, is put up for signature please.", fsi4);
        cg3.setBackground(BaseColor.WHITE);
        Paragraph pg3 = new Paragraph(cg3);
        pg3.setAlignment(Element.ALIGN_JUSTIFIED);
        pg3.setSpacingBefore(50f);       
        pg3.setIndentationLeft(30f);
        pg3.setIndentationRight(30f);
        document.add(pg3);
        
        
        Chunk cg4 = new Chunk("Manager (Purchase)", fsi4);
        cg4.setBackground(BaseColor.WHITE);
        Paragraph pg4 = new Paragraph(cg4);
        pg4.setAlignment(Element.ALIGN_LEFT);
        pg4.setSpacingBefore(50f);       
        pg4.setIndentationLeft(30f);
        document.add(pg4);
        
        Chunk cg5 = new Chunk("Asst.Purchase Officer", fsi4);
        cg5.setBackground(BaseColor.WHITE);
        Paragraph pg5 = new Paragraph(cg5);
        pg5.setAlignment(Element.ALIGN_RIGHT);
        pg5.setSpacingBefore(10f);       
        pg5.setIndentationRight(60f);
        document.add(pg5);
        
        
        document.newPage();
        
        //***********************NEW PAGE 4  **********************************
        
        Paragraph headerPara8 = new Paragraph("  ");
        headerPara8.setAlignment(Element.ALIGN_CENTER);
        headerPara8.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara8.setSpacingBefore(50f);
        document.add(headerPara8);
        
        Chunk ga = new Chunk("File No. :"+PoNumber, fsi4);
        ga.setBackground(BaseColor.WHITE);
        Paragraph gaa = new Paragraph(ga);
        gaa.setAlignment(Element.ALIGN_LEFT);
        gaa.setSpacingBefore(100f);       
        gaa.setIndentationLeft(60f);
        document.add(gaa);
        
        Chunk ga6 = new Chunk("     Please refer to the subject Purchase order placed on M/s. "+b.getVenObj().getVendorName()
                + ", for the supply of supply of feed fprwarding pumps and sparesO18, vide.P.O.No. "+PoNumber+"   Date: "+poDate
                + "\n\n"
                + "The performance bond Bank Guarantee furnished by the firm is valid upto. "+b.getExpireDate()
                + "\n\n"
                + "You are therefore requested to certify with due approval of competent authority, whether th equipment / Machinery under this contract"
                + ", has performed satisfactory during the Guarantee / Warranty period and three are no complaints noticed and pending.."
                + "\n\n"
                + "On the basis of your certification the "+b.getTempone()+" will be discharged submitted by the firm. Your early action"
                + "is awaited.\n\n"
                + "MPK", fsi4);
        ga6.setBackground(BaseColor.WHITE);
        Paragraph gaa6 = new Paragraph(ga6);
        gaa6.setAlignment(Element.ALIGN_JUSTIFIED);
        gaa6.setSpacingBefore(30f);       
        gaa6.setIndentationLeft(60f);
        gaa6.setIndentationRight(30f);
        document.add(gaa6);
        
        
        Chunk ga7 = new Chunk("Asst.Purchase Officer", fsi4);
        ga7.setBackground(BaseColor.WHITE);
        Paragraph gaa7 = new Paragraph(ga7);
        gaa7.setAlignment(Element.ALIGN_RIGHT);
        gaa7.setSpacingBefore(50f);       
        gaa7.setIndentationRight(60f);
        document.add(gaa7);
        
        
        Chunk ga8 = new Chunk("Manager(P)\n\n\n\nDy.GM(P)", fsi4);
        ga8.setBackground(BaseColor.WHITE);
        Paragraph gaa8 = new Paragraph(ga8);
        gaa8.setAlignment(Element.ALIGN_LEFT);
        gaa8.setSpacingBefore(20f);       
        gaa8.setIndentationLeft(60f);
        document.add(gaa8);
        
        }
        
    }
        
}
