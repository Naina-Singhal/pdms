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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.dto.IndentFileMappingDTO;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.PoDetailsDTO;
import com.pdms.dto.PoEntryDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import static java.util.Arrays.stream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javafx.scene.transform.Rotate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author myassessment
 */
@Component
public class PurchaseOrPdfPage extends AbstractPDFViewHelper{

    private static final Logger log = Logger.getLogger(PurchaseOrPdfPage.class.getName());
     public static final String FONT = "G:/stainmetz/march/Kruti_Dev_040_Condensed.ttf";
     public static final PdfNumber LANDSCAPE = new PdfNumber(90);
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date(); 
        
        List<PoEntryDTO> poObj = (List<PoEntryDTO>) model.get("poObj");        
         List<IndentFormDTO> indentObj = (List<IndentFormDTO>) model.get("indentForm");
         List<PoDetailsDTO> noteObj = (List<PoDetailsDTO>) model.get("note");
         List<IndentFileMappingDTO> groupObj = (List<IndentFileMappingDTO>) model.get("group");
         String PoNumber = "";
         for(PoEntryDTO p: poObj){
             for(IndentFileMappingDTO g: groupObj){
                 PoNumber = "/ "+g.getGroupDTO().getGroupName()+" / "+p.getTenderFN()+" / "+p.getPoNumber();
             }
         }
        
        Font heading = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
         
        Paragraph headerPara = new Paragraph("  ");
        headerPara.setAlignment(Element.ALIGN_CENTER);
        headerPara.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerPara.setSpacingBefore(0f);
        document.add(headerPara);
        
        Font f9 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
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
        cell.setPhrase(new Phrase("फ़ोन / phone : ", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("क्षेत्रीयक्रयएकक,(मनुगुरु) / Regional Purchase Unit, (MANUGURU)", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("फैक्स / fax : ", font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        
        cell.setPhrase(new Phrase("E - Mail : ", font10));
        cell.setPaddingLeft(0f);
        headerTable.addCell(cell);
        cell.setPhrase(new Phrase("दिनांक /date : "+dateFormat.format(date), font10));
        cell.setPaddingLeft(70f);
        headerTable.addCell(cell);
        
        document.add(headerTable);
        
        for(PoEntryDTO po: poObj){
        
        Font f5 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5 = new Chunk("PURCHASE ORDER            DPS P-23", f5);
        c5.setBackground(BaseColor.WHITE);
        Paragraph p5 = new Paragraph(c5);
        p5.setAlignment(Element.ALIGN_RIGHT);
        p5.setSpacingBefore(0f);       
        p5.setIndentationRight(170f);
        document.add(p5);
        
        Font f6 = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c6 = new Chunk("Purchase Oreder No : DPS / RPUM "+PoNumber+"                                               Date:"+po.getPoDate(), f6);
        c6.setBackground(BaseColor.WHITE);
        Paragraph p6 = new Paragraph(c6);
        p6.setAlignment(Element.ALIGN_LEFT);
        p6.setSpacingBefore(5f); 
        p6.setIndentationLeft(30f);
        document.add(p6);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90.0f);
        table.setSpacingBefore(20f);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell cel = new PdfPCell();
        cel.setBackgroundColor(BaseColor.WHITE);
        cel.setBorder(1);        
        
        Font f7 = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel.setPhrase(new Phrase("M/s. "+po.getVendorObj().getVendorName()+",\n"+po.getVendorObj().getVendorAddress()+",\n"
                + ""+po.getVendorObj().getVendorCity()+",\n"+po.getVendorObj().getVendorPin(), f7));
        cel.setPaddingLeft(5f);
        cel.setFixedHeight(60f);
        //cel.setBorderWidthLeft(1.0f);
        cel.setBorderWidthBottom(0.2f);        
        table.addCell(cel);
        cel.setPhrase(new Phrase("vcode :"+po.getVenderName()+",   Exp.date:"+po.getVendorObj().getExpiraryDate()+"\n"
                +""+po.getReferenceNo()+",\n"
                + ""+po.getVendorObj().getVendorPhone()+",   Fax :"+po.getVendorObj().getVendorFax()+"\n"
                + "Email :"+po.getVendorObj().getVendorEmail(), f7));
        cel.setPaddingLeft(5f);
        cel.setFixedHeight(60f);
        cel.setBorderWidthLeft(0.2f);
        cel.setBorderWidthBottom(0.2f);
        //cel.setBorderWidthRight(0.2f);
        table.addCell(cel);
        
        document.add(table);
        
        Font f8 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c8 = new Chunk("      निम्नलिखितसामानकीआपूर्तिकेलिएआपकीदिनांक 04/01/2018 कीनिविदासंख्या HRPU/NFC/CAP/10634"
                + " मेंदीगईआपकीनिविदाभारतकेराष्ट्रपतिकीओरसेउक्तनिविदा( फार्मसंख्याक्रभनिक्र 101 क) "
                + "केपिछलेपृष्ठपरदीगईशर्तोंकेअनुसारतथानीचेविनिर्दिष्टसामानकीमात्रातथाउसकीकीमतकीसीमातकइसक्रयादेशमेंदीगईशर्तोंकेअनुसारस्वीकारकीजातीहैं.  \n      Yours offer "
                + "contained in the tender no.  Dated to supply undermentioned stores is accepted on behalf of "
                + "the president of India, subject to the terms & conditions of contract in the printed at back side"
                + " of the tender (form no. DPS - 101)and subject to the terms and conditions contained in "
                + "this purchase order and to the extent of quantity and at the price specified.", font10);
        c8.setBackground(BaseColor.WHITE);
        Paragraph p8 = new Paragraph(c8);
        p8.setAlignment(Element.ALIGN_JUSTIFIED);
        p8.setSpacingBefore(5f);       
        p8.setIndentationLeft(30f);
        p8.setIndentationRight(30f);
        document.add(p8);
        
        //*********************************************************************
        PdfPTable table2 = new PdfPTable(6);
        table2.setWidthPercentage(90.0f);
        table2.setSpacingBefore(20f);
        table2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2.setWidths(new float[] {14.0f, 40.0f, 14.0f, 10.0f, 10.0f, 10.0f});
        
        PdfPCell cel2 = new PdfPCell();
        cel2.setBackgroundColor(BaseColor.WHITE);
        cel2.setBorder(1); 
        
        
        
        cel2.setPhrase(new Phrase("Sl NO.: ", f9));
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Description", f9));
        cel2.setPaddingLeft(5f); 
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Qantity", f9));
        cel2.setPaddingLeft(5f);   
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Unit", f9));
        cel2.setPaddingLeft(5f);      
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Rate", f9));
        cel2.setPaddingLeft(5f);    
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Amount", f9));
        cel2.setPaddingLeft(5f);   
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthRight(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("COTTON HOSIERY RAGS.\nDetailed description and terms and conditiond as per annexure.\n"
                + "PAYMENT : 100% payment will be made within 30 days  from the date of receipt of materials at our stores, Heavy"
                + " Water Plant, Manuguru and its final acceptance. Bills in duplicate shall be submitted to Accounts Officer,"
                + "RPUM, Hyderabad. NOTE: Please quote your bank details on your bills to enable ue to make paymentthrough RTGS/NEFT.", f9));
        cel2.setColspan(6);
        cel2.setPaddingLeft(10f);  
        cel2.setPaddingBottom(5f);
        cel2.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Price: F.O.R. MANUGURU. ", f9));
        cel2.setColspan(2);
        cel2.setPaddingLeft(5f);
        cel2.setPaddingBottom(5f);        
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        cel2.setPhrase(new Phrase("Delivery Period :\nON OR BEFORE", f9));
        cel2.setColspan(4);
        cel2.setPaddingLeft(5f);  
        cel2.setPaddingBottom(5f);
        cel2.setBorderWidthLeft(0.1f);
        cel2.setBorderWidthBottom(0.2f);        
        table2.addCell(cel2);
        
        document.add(table2);
        
        //*********************************************************************
        
        Chunk ca = new Chunk("      निविदा फॉर्म नंबर के पीछे मुद्रित नियम और शर्तों पर ध्यान दें। डीपीएस -101 "
                + "ऊपर उल्लिखित है और यह खरीद आदेश अनुबंध का एकमात्र भंडार होगा।  \n      Yours offer "
                + "Please note the terms and conditions printed at the back of the tender "
                + "form no. DPS – 101 referred to above and this purchase order shall be "
                + "the sole repository of the contract.", font10);                
        ca.setBackground(BaseColor.WHITE);
        Paragraph pa = new Paragraph(ca);
        pa.setAlignment(Element.ALIGN_JUSTIFIED);
        pa.setSpacingBefore(5f);       
        pa.setIndentationLeft(30f);
        pa.setIndentationRight(30f);
        document.add(pa);
        
        Font fb = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cb = new Chunk("Yours Faithfully,", fb);                
        cb.setBackground(BaseColor.WHITE);
        Paragraph pb = new Paragraph(cb);
        pb.setAlignment(Element.ALIGN_RIGHT);
        pb.setSpacingBefore(20f);
        pb.setIndentationRight(70f);
        document.add(pb);
        
        
        Chunk cc = new Chunk("(G.C.PAUL)", fb);                
        cc.setBackground(BaseColor.WHITE);
        Paragraph pc = new Paragraph(cc);
        pc.setAlignment(Element.ALIGN_RIGHT);
        pc.setSpacingBefore(20f);
        pc.setIndentationRight(120f);
        document.add(pc);
        
        Chunk cd = new Chunk("Asst.Purchase Officer", fb);                
        cd.setBackground(BaseColor.WHITE);
        Paragraph pd = new Paragraph(cd);
        pd.setAlignment(Element.ALIGN_RIGHT);
        pd.setSpacingBefore(3f);
        pd.setIndentationRight(90f);
        document.add(pd);
        
        Chunk ce = new Chunk("For and on behalf of the President Of India(The Purchaser", fb);                
        ce.setBackground(BaseColor.WHITE);
        Paragraph pe = new Paragraph(ce);
        pe.setAlignment(Element.ALIGN_RIGHT);
        pe.setSpacingBefore(3f);
        pe.setIndentationRight(30f);
        document.add(pe);
        
        //**********************************************************************
        PdfPTable table3 = new PdfPTable(2);
        table3.setWidthPercentage(90.0f);
        table3.setSpacingBefore(20f);
        table3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table3.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell cel3 = new PdfPCell();
        cel3.setBackgroundColor(BaseColor.WHITE);
        cel3.setBorder(1); 
        
        for(IndentFormDTO i: indentObj){
        
        
        cel3.setPhrase(new Phrase("CC to:\n"+i.getEmpProfileDTo().getFirstName()+"  "+i.getEmpProfileDTo().getLastName()
                + "\nIndent No."+i.getIndentNumber()+",   Date:"+i.getIndentDate(), f9));
        cel3.setPaddingLeft(5f);  
        cel3.setPaddingBottom(5f);
        cel3.setBorderWidthLeft(0.2f);
        cel3.setFixedHeight(50f);
        //cel3.setBorderWidthRight(0.2f);
        cel3.setBorderWidthBottom(0.2f);        
        table3.addCell(cel3);
        }
          
        cel3.setPhrase(new Phrase("Certified in Pre-Audit. Expenditure is Debitable to ------\n\n       AA", f9));
        cel3.setPaddingLeft(5f);  
        cel3.setFixedHeight(50f);
        cel3.setPaddingBottom(5f);
        cel3.setBorderWidthLeft(0.2f);
        cel3.setBorderWidthRight(0.2f);
        cel3.setBorderWidthBottom(0.2f);        
        table3.addCell(cel3);
        
        cel3.setPhrase(new Phrase("ACCOUNTS / INDENTER / GROUP: SO / STORES / OFFICE COPY ", f9));
        cel3.setColspan(2);
        cel3.setPaddingLeft(5f);        
        cel3.setPaddingBottom(5f);
        cel3.setBorderWidthLeft(0.2f);
        cel3.setBorderWidthRight(0.2f);
        cel3.setBorderWidthBottom(0.2f);  
        cel3.setFixedHeight(20f);
        cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table3.addCell(cel3);
        
        document.add(table3);
        } // End for loof of po  
        
        document.newPage();
        //**********************NEW PAGE NO 2 *************************************
        
        Paragraph headerParaa = new Paragraph("  ");
        headerParaa.setAlignment(Element.ALIGN_CENTER);
        headerParaa.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerParaa.setSpacingBefore(0f);
        document.add(headerParaa);
        
               
        Image imgheada = Image.getInstance(absoluteDiskPath);        
        imgheada.setAbsolutePosition(280f, 730f);
        imgheada.scaleAbsolute(40, 52);
        imgheada.setDpi(3, 3);
        document.add(imgheada);
        
        
        
        PdfPTable headerTablea = new PdfPTable(2);
        headerTablea.setWidthPercentage(90.0f);
        headerTablea.setWidths(new float[] {50.0f, 50.0f});
        headerTablea.setSpacingBefore(40f);
        
        
        Font fonta = FontFactory.getFont(FontFactory.HELVETICA);
        fonta.setColor(BaseColor.BLACK);
        fonta.setSize(10.0f);
        
        
        
        PdfPCell cella = new PdfPCell();
        cella.setBackgroundColor(BaseColor.WHITE);
        cella.setBorder(0);
        cella.setPadding(1);
        
    
        cella.setPhrase(new Phrase("भारत सरकार / Government of India", font10));
        cella.setPaddingLeft(0f);
        headerTablea.addCell(cella); 
        cella.setPhrase(new Phrase("ECIL Road, ECIL Post, ", font10));
        cella.setPaddingLeft(70f);
        headerTablea.addCell(cella);
        
        cella.setPhrase(new Phrase("परमाणु ऊर्जा विभाग / Department of Atomic Energy", font10));
        cella.setPaddingLeft(0f);
        headerTablea.addCell(cella); 
        cella.setPhrase(new Phrase("हैदराबाद / hyderabad", font10));
        cella.setPaddingLeft(70f);
        headerTablea.addCell(cella);
        
        cella.setPhrase(new Phrase("क्रयएवंभंडारनिदेशालय / Directorate of Purchase & Stores", font10));
        cella.setPaddingLeft(0f);
        headerTablea.addCell(cella);
        cella.setPhrase(new Phrase("फ़ोन / phone : ", font10));
        cella.setPaddingLeft(70f);
        headerTablea.addCell(cella);
        
        cella.setPhrase(new Phrase("क्षेत्रीयक्रयएकक,(मनुगुरु) / Regional Purchase Unit, (MANUGURU)", font10));
        cella.setPaddingLeft(0f);
        headerTablea.addCell(cella);
        cella.setPhrase(new Phrase("फैक्स / fax : ", font10));
        cella.setPaddingLeft(70f);
        headerTablea.addCell(cella);
        
        cella.setPhrase(new Phrase("E - Mail : ", font10));
        cella.setPaddingLeft(0f);
        headerTablea.addCell(cella);
        cella.setPhrase(new Phrase("दिनांक /date : ", font10));
        cella.setPaddingLeft(70f);
        headerTablea.addCell(cella);
        
        document.add(headerTablea);
        
        
        Font f5a = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5a = new Chunk("PURCHASE ORDER            DPS P-23", f5a);
        c5a.setBackground(BaseColor.WHITE);
        Paragraph p5a = new Paragraph(c5a);
        p5a.setAlignment(Element.ALIGN_RIGHT);
        p5a.setSpacingBefore(0f);       
        p5a.setIndentationRight(170f);
        document.add(p5a);
        for(PoEntryDTO p: poObj){
        Font f6a = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c6a = new Chunk("Purchase Oreder No : DPS / RPUM / "+PoNumber+",                                      Date:"+p.getPoDate(), f6a);
        c6a.setBackground(BaseColor.WHITE);
        Paragraph p6a = new Paragraph(c6a);
        p6a.setAlignment(Element.ALIGN_LEFT);
        p6a.setSpacingBefore(5f); 
        p6a.setIndentationLeft(30f);
        document.add(p6a);
        
        PdfPTable tablea = new PdfPTable(2);
        tablea.setWidthPercentage(90.0f);
        tablea.setSpacingBefore(20f);
        tablea.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablea.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell cela = new PdfPCell();
        cela.setBackgroundColor(BaseColor.WHITE);
        cela.setBorder(1);        
        
        Font f7a = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        
        cela.setPhrase(new Phrase("M/s. "+p.getVendorObj().getVendorName()+",\n"+p.getVendorObj().getVendorAddress()+",\n"
                + ""+p.getVendorObj().getVendorCity()+",\n"+p.getVendorObj().getVendorPin(), f7a));
        cela.setPaddingLeft(5f);
        cela.setFixedHeight(60f);
        //cel.setBorderWidthLeft(1.0f);
        cela.setBorderWidthBottom(0.2f);        
        tablea.addCell(cela);
        cela.setPhrase(new Phrase("vcode :"+p.getVenderName()+",   Exp.date:"+p.getVendorObj().getExpiraryDate()+"\n"
                +""+p.getReferenceNo()+",\n"
                + ""+p.getVendorObj().getVendorPhone()+",   Fax :"+p.getVendorObj().getVendorFax()+"\n"
                + "Email :"+p.getVendorObj().getVendorEmail(), f7a));
        cela.setPaddingLeft(5f);
        cela.setFixedHeight(60f);
        cela.setBorderWidthLeft(0.2f);
        cela.setBorderWidthBottom(0.2f);
        //cel.setBorderWidthRight(0.2f);
        tablea.addCell(cela);
        
        document.add(tablea);
        
        Font f8a = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c8a = new Chunk("      निम्नलिखितसामानकीआपूर्तिकेलिएआपकीदिनांक 04/01/2018 कीनिविदासंख्या HRPU/NFC/CAP/10634"
                + " मेंदीगईआपकीनिविदाभारतकेराष्ट्रपतिकीओरसेउक्तनिविदा( फार्मसंख्याक्रभनिक्र 101 क) "
                + "केपिछलेपृष्ठपरदीगईशर्तोंकेअनुसारतथानीचेविनिर्दिष्टसामानकीमात्रातथाउसकीकीमतकीसीमातकइसक्रयादेशमेंदीगईशर्तोंकेअनुसारस्वीकारकीजातीहैं.  \n      Yours offer "
                + "contained in the tender no.  Dated to supply undermentioned stores is accepted on behalf of "
                + "the president of India, subject to the terms & conditions of contract in the printed at back side"
                + " of the tender (form no. DPS - 101)and subject to the terms and conditions contained in "
                + "this purchase order and to the extent of quantity and at the price specified.", font10);
        c8a.setBackground(BaseColor.WHITE);
        Paragraph p8a = new Paragraph(c8a);
        p8a.setAlignment(Element.ALIGN_JUSTIFIED);
        p8a.setSpacingBefore(5f);       
        p8a.setIndentationLeft(30f);
        p8a.setIndentationRight(30f);
        document.add(p8a);
        
        //*********************************************************************
        PdfPTable table2a = new PdfPTable(6);
        table2a.setWidthPercentage(90.0f);
        table2a.setSpacingBefore(20f);
        table2a.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2a.setWidths(new float[] {14.0f, 40.0f, 14.0f, 10.0f, 10.0f, 10.0f});
        
        PdfPCell cel2a = new PdfPCell();
        cel2a.setBackgroundColor(BaseColor.WHITE);
        cel2a.setBorder(1); 
        
        Font f9a = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel2a.setPhrase(new Phrase("Sl NO.: ", f9a));
        cel2a.setPaddingLeft(5f);  
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Description", f9a));
        cel2a.setPaddingLeft(5f); 
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Qantity", f9a));
        cel2a.setPaddingLeft(5f);   
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Unit", f9a));
        cel2a.setPaddingLeft(5f);      
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Rate", f9a));
        cel2a.setPaddingLeft(5f);    
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Amount", f9a));
        cel2a.setPaddingLeft(5f);   
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthRight(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("COTTON HOSIERY RAGS.\nDetailed description and terms and conditiond as per annexure.\n"
                + "PAYMENT : 100% payment will be made within 30 days  from the date of receipt of materials at our stores, Heavy"
                + " Water Plant, Manuguru and its final acceptance. Bills in duplicate shall be submitted to Accounts Officer,"
                + "RPUM, Hyderabad. NOTE: Please quote your bank details on your bills to enable ue to make paymentthrough RTGS/NEFT.", f9a));
        cel2a.setColspan(6);
        cel2a.setPaddingLeft(10f);  
        cel2a.setPaddingBottom(5f);
        cel2a.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Price: F.O.R. MANUGURU. ", f9a));
        cel2a.setColspan(2);
        cel2a.setPaddingLeft(5f);
        cel2a.setPaddingBottom(5f);        
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        cel2a.setPhrase(new Phrase("Delivery Period : "+p.getDeliveryPeriod()+"\nON OR BEFORE", f9a));
        cel2a.setColspan(4);
        cel2a.setPaddingLeft(5f);  
        cel2a.setPaddingBottom(5f);
        cel2a.setBorderWidthLeft(0.1f);
        cel2a.setBorderWidthBottom(0.2f);        
        table2a.addCell(cel2a);
        
        document.add(table2a);
        }   //End loop
        //*********************************************************************
        
        Chunk caa = new Chunk("      निविदा फॉर्म नंबर के पीछे मुद्रित नियम और शर्तों पर ध्यान दें। डीपीएस -101 "
                + "ऊपर उल्लिखित है और यह खरीद आदेश अनुबंध का एकमात्र भंडार होगा।  \n      Yours offer "
                + "Please note the terms and conditions printed at the back of the tender "
                + "form no. DPS – 101 referred to above and this purchase order shall be "
                + "the sole repository of the contract.", font10);                
        caa.setBackground(BaseColor.WHITE);
        Paragraph paa = new Paragraph(caa);
        paa.setAlignment(Element.ALIGN_JUSTIFIED);
        paa.setSpacingBefore(5f);       
        paa.setIndentationLeft(30f);
        paa.setIndentationRight(30f);
        document.add(paa);
        
        Font fba = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cba = new Chunk("Yours Faithfully,", fba);                
        cba.setBackground(BaseColor.WHITE);
        Paragraph pba = new Paragraph(cba);
        pba.setAlignment(Element.ALIGN_RIGHT);
        pba.setSpacingBefore(20f);
        pba.setIndentationRight(70f);
        document.add(pba);
        
        
        Chunk cca = new Chunk("(G.C.PAUL)", fba);                
        cca.setBackground(BaseColor.WHITE);
        Paragraph pca = new Paragraph(cca);
        pca.setAlignment(Element.ALIGN_RIGHT);
        pca.setSpacingBefore(20f);
        pca.setIndentationRight(120f);
        document.add(pca);
        
        Chunk cda = new Chunk("Asst.Purchase Officer", fba);                
        cda.setBackground(BaseColor.WHITE);
        Paragraph pda = new Paragraph(cda);
        pda.setAlignment(Element.ALIGN_RIGHT);
        pda.setSpacingBefore(3f);
        pda.setIndentationRight(90f);
        document.add(pda);
        
        Chunk cea = new Chunk("For and on behalf of the President Of India(The Purchaser", fba);                
        cea.setBackground(BaseColor.WHITE);
        Paragraph pea = new Paragraph(cea);
        pea.setAlignment(Element.ALIGN_RIGHT);
        pea.setSpacingBefore(3f);
        pea.setIndentationRight(30f);
        document.add(pea);
        
        document.newPage();
        //******************* PAGE NO 3 ****************************************
        
        Paragraph headerParab = new Paragraph("  ");
        headerParab.setAlignment(Element.ALIGN_CENTER);
        headerParab.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerParab.setSpacingBefore(0f);
        document.add(headerParab);
        
                
        Image imgheadb = Image.getInstance(absoluteDiskPath);        
        imgheadb.setAbsolutePosition(280f, 730f);
        imgheadb.scaleAbsolute(40, 52);
        imgheadb.setDpi(3, 3);
        document.add(imgheadb);
        
        
        
        PdfPTable headerTableb = new PdfPTable(2);
        headerTableb.setWidthPercentage(90.0f);
        headerTableb.setWidths(new float[] {50.0f, 50.0f});
        headerTableb.setSpacingBefore(40f);
        
        
       
        
        PdfPCell cellb = new PdfPCell();
        cellb.setBackgroundColor(BaseColor.WHITE);
        cellb.setBorder(0);
        cellb.setPadding(1);
        
        
        cellb.setPhrase(new Phrase("भारत सरकार / Government of India", font10));
        cellb.setPaddingLeft(0f);
        headerTableb.addCell(cellb); 
        cellb.setPhrase(new Phrase("ECIL Road, ECIL Post, ", font10));
        cellb.setPaddingLeft(70f);
        headerTableb.addCell(cellb);
        
        cellb.setPhrase(new Phrase("परमाणु ऊर्जा विभाग / Department of Atomic Energy", font10));
        cellb.setPaddingLeft(0f);
        headerTableb.addCell(cellb); 
        cellb.setPhrase(new Phrase("हैदराबाद / hyderabad", font10));
        cellb.setPaddingLeft(70f);
        headerTableb.addCell(cellb);
        
        cellb.setPhrase(new Phrase("क्रयएवंभंडारनिदेशालय / Directorate of Purchase & Stores", font10));
        cellb.setPaddingLeft(0f);
        headerTableb.addCell(cellb);
        cellb.setPhrase(new Phrase("फ़ोन / phone : ", font10));
        cellb.setPaddingLeft(70f);
        headerTableb.addCell(cellb);
        
        cellb.setPhrase(new Phrase("क्षेत्रीयक्रयएकक,(मनुगुरु) / Regional Purchase Unit, (MANUGURU)", font10));
        cellb.setPaddingLeft(0f);
        headerTableb.addCell(cellb);
        cellb.setPhrase(new Phrase("फैक्स / fax : ", font10));
        cellb.setPaddingLeft(70f);
        headerTableb.addCell(cellb);
        
        cellb.setPhrase(new Phrase("E - Mail : ", font10));
        cellb.setPaddingLeft(0f);
        headerTableb.addCell(cellb);
        cellb.setPhrase(new Phrase("दिनांक /date : ", font10));
        cellb.setPaddingLeft(70f);
        headerTableb.addCell(cellb);
        
        document.add(headerTableb);
        
        
        Font f5b = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5b = new Chunk("PURCHASE ORDER            DPS P-45", f5b);
        c5b.setBackground(BaseColor.WHITE);
        Paragraph p5b = new Paragraph(c5b);
        p5b.setAlignment(Element.ALIGN_RIGHT);
        p5b.setSpacingBefore(0f);       
        p5b.setIndentationRight(170f);
        document.add(p5b);
        for(PoEntryDTO po: poObj){
        Font f6b = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c6b = new Chunk("Purchase Oreder No : DPS / RPUM / "+PoNumber+",                                          Date: "+po.getPoDate(), f6b);
        c6b.setBackground(BaseColor.WHITE);
        Paragraph p6b = new Paragraph(c6b);
        p6b.setAlignment(Element.ALIGN_LEFT);
        p6b.setSpacingBefore(5f); 
        p6b.setIndentationLeft(30f);
        document.add(p6b);
        
        PdfPTable tableb = new PdfPTable(2);
        tableb.setWidthPercentage(90.0f);
        tableb.setSpacingBefore(5f);
        tableb.setHorizontalAlignment(Element.ALIGN_CENTER);
        tableb.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell celb = new PdfPCell();
        celb.setBackgroundColor(BaseColor.WHITE);
        celb.setBorder(1);        
        
        Font f7b = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        
        celb.setPhrase(new Phrase("M/s. "+po.getVendorObj().getVendorName()+",\n"+po.getVendorObj().getVendorAddress()+",\n"
                + ""+po.getVendorObj().getVendorCity()+",\n"+po.getVendorObj().getVendorPin(), f7b));
        celb.setPaddingLeft(5f);
        celb.setFixedHeight(60f);
        //cel.setBorderWidthLeft(1.0f);
        celb.setBorderWidthBottom(0.2f);        
        tableb.addCell(celb);
        celb.setPhrase(new Phrase("vcode :"+po.getVenderName()+",   Exp.date:"+po.getVendorObj().getExpiraryDate()+"\n"
                +""+po.getReferenceNo()+",\n"
                + ""+po.getVendorObj().getVendorPhone()+",   Fax :"+po.getVendorObj().getVendorFax()+"\n"
                + "Email :"+po.getVendorObj().getVendorEmail(), f7b));
        celb.setPaddingLeft(5f);
        celb.setFixedHeight(60f);
        celb.setBorderWidthLeft(0.2f);
        celb.setBorderWidthBottom(0.2f);
        //cel.setBorderWidthRight(0.2f);
        tableb.addCell(celb);
        
        document.add(tableb);
        
        
        Font f8b = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c8b = new Chunk("           निम्‍नलिखितसामानकीआपूर्तिकेलिएआपकीदिनांक 21/03/2017 कीनिविदासंख्‍या   HRPU/NFC/CAP/10455 "
                + "मेंप्रस्‍तुतआपकेप्रस्‍तावकोभारतकेराष्‍ट्रपतिकीओरसेफार्मसंख्‍याक्रभनिक्र-102 "
                + "मेंउल्लिखितसभीठेकोंकीसामान्‍यशर्तेंएवंसंयंत्रवमशीनरीकीपूर्तिकोसंचालितकरनेवालीविशेषशर्तेंक्रयएवंभंडारनिदेशालय(भारतसरकार,परमाणुऊर्जाविभागकेअधीन)"
                + "मेंभारतकेराष्‍ट्रपतिद्वारादीगईठेकोंपरलागूहोनेवालीसभीठेकोंकीसामान्‍यशर्तेंवसंयंत्रवमशीनरीकीपूर्तिकोसंचालितकरनेवालीविशेषशर्तेंनामकपुस्तिकामेंसम्मिलितशर्तोंतथाइसक्रयादे"
                + "शमेंअंतर्विष्‍टशर्तोंएवंनिबंधनोंकेअधीनतथानिम्‍नब्‍यौरेवारदीगईदरोंकेअधीनस्‍वीकारकियाजाताहै।  \n     Your offer contained "
                + "in the Tender no."+PoNumber+",  Dated:"+po.getPoDate()+" to supply undermentioned stores is accepted on behalf of the "
                + "President of India, subject to the general conditions of all contracts and special conditions "
                + "of contract governing the supply of plant and machinery in the form no. DPS -102 included in "
                + "pamphletentitled \"general conditions\" applicable to contracts by the president of India in "
                + "the directorate of purchase & stores (under Government of India Department of atomic energy) "
                + "and subject to the terms and conditions contained in this purchase order and to the extent quality "
                + "and at the price specified below:", font10);
        c8b.setBackground(BaseColor.WHITE);
        Paragraph p8b = new Paragraph(c8b);
        p8b.setAlignment(Element.ALIGN_JUSTIFIED);
        p8b.setSpacingBefore(5f);       
        p8b.setIndentationLeft(30f);
        p8b.setIndentationRight(30f);
        document.add(p8b);
        
        
        //*********************************************************************
        PdfPTable table2b = new PdfPTable(6);
        table2b.setWidthPercentage(90.0f);
        table2b.setSpacingBefore(20f);
        table2b.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2b.setWidths(new float[] {14.0f, 40.0f, 14.0f, 10.0f, 10.0f, 10.0f});
        
        PdfPCell cel2b = new PdfPCell();
        cel2b.setBackgroundColor(BaseColor.WHITE);
        cel2b.setBorder(1); 
        
        //Font f9 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel2b.setPhrase(new Phrase("Sl NO.: ", f9));
        cel2b.setPaddingLeft(5f);  
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Description", f9));
        cel2b.setPaddingLeft(5f); 
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Qantity", f9));
        cel2b.setPaddingLeft(5f);   
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Unit", f9));
        cel2b.setPaddingLeft(5f);      
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Rate", f9));
        cel2b.setPaddingLeft(5f);    
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Amount", f9));
        cel2b.setPaddingLeft(5f);   
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthRight(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        for(IndentFormDTO i: indentObj){
        cel2b.setPhrase(new Phrase(po.getVenDescription()+"\nDetailed description and terms and conditiond as per annexure.\n"
                + "PAYMENT : 100% payment will be made within 30 days  from the date of receipt of materials at our stores, Heavy"
                + " Water Plant, Manuguru and its final acceptance. Bills in duplicate shall be submitted to Accounts Officer,"
                + "RPUM, Hyderabad. NOTE: Please quote your bank details on your bills to enable ue to make paymentthrough RTGS/NEFT.\n"
                + "INSPECTION : Pre-despatch inspection will be carried out by: "+i.getEmpProfileDTo().getFirstName()+" "+i.getEmpProfileDTo().getLastName(), f9));
        }
        cel2b.setColspan(6);
        cel2b.setPaddingLeft(10f);  
        cel2b.setPaddingBottom(5f);
        cel2b.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Price: "+po.getPrice(), f9));
        cel2b.setColspan(2);
        cel2b.setPaddingLeft(5f);
        cel2b.setPaddingBottom(5f);        
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        cel2b.setPhrase(new Phrase("Delivery Period : "+po.getDeliveryPeriod()+"\nON OR BEFORE", f9));
        cel2b.setColspan(4);
        cel2b.setPaddingLeft(5f);  
        cel2b.setPaddingBottom(5f);
        cel2b.setBorderWidthLeft(0.1f);
        cel2b.setBorderWidthBottom(0.2f);        
        table2b.addCell(cel2b);
        
        document.add(table2b);
        
        //*********************************************************************
        
         
        Chunk caB = new Chunk("      क्रयऔरभंडारनिदेशालय(भारतसरकार,परमाणुऊर्जाविभागकेअधीन)द्वारादियेजानेवालेठेकोंपरलागूहोनेवालीसभीठेकोंकीसामान्‍य"
                + "शर्तेंएवंसंयंत्रवमशीनरीकीआपूर्तिकोसंचालितकरनेवालीविशेषशर्तेंतथायहक्रयादेशहीइसठेकेकाएकमात्रआधारहोगा।  \n     General conditions of all "
                + "contracts and special conditions of contracts governing the supply of plant and machinery "
                + "applicable to contracts placed bythedirectorate of purchase & stores "
                + "(under government of India, department of atomic energy) and this purchase order "
                + "shall be the sole repository of the contract.", font10);                
        caB.setBackground(BaseColor.WHITE);
        Paragraph paB = new Paragraph(caB);
        paB.setAlignment(Element.ALIGN_JUSTIFIED);
        paB.setSpacingBefore(5f);       
        paB.setIndentationLeft(30f);
        paB.setIndentationRight(30f);
        document.add(paB);
        
        Font fbb = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cbb = new Chunk("Yours Faithfully,", fbb);                
        cbb.setBackground(BaseColor.WHITE);
        Paragraph pbb = new Paragraph(cbb);
        pbb.setAlignment(Element.ALIGN_RIGHT);
        pbb.setSpacingBefore(20f);
        pbb.setIndentationRight(70f);
        document.add(pbb);
        
        
        Chunk ccb = new Chunk("(G.S.S.V.J.SARMA)", fbb);                
        ccb.setBackground(BaseColor.WHITE);
        Paragraph pcb = new Paragraph(ccb);
        pcb.setAlignment(Element.ALIGN_RIGHT);
        pcb.setSpacingBefore(5f);
        pcb.setIndentationRight(90f);
        document.add(pcb);
        
        Chunk cdb = new Chunk("Manager (Purchase)", fbb);                
        cdb.setBackground(BaseColor.WHITE);
        Paragraph pdb = new Paragraph(cdb);
        pdb.setAlignment(Element.ALIGN_RIGHT);
        pdb.setSpacingBefore(3f);
        pdb.setIndentationRight(90f);
        document.add(pdb);
        
        Chunk ceb = new Chunk("For and on behalf of the President Of India(The Purchaser", fbb);                
        ceb.setBackground(BaseColor.WHITE);
        Paragraph peb = new Paragraph(ceb);
        peb.setAlignment(Element.ALIGN_RIGHT);
        peb.setSpacingBefore(3f);
        peb.setIndentationRight(30f);
        document.add(peb);
        
        //**********************************************************************
        PdfPTable table3b = new PdfPTable(2);
        table3b.setWidthPercentage(90.0f);
        table3b.setSpacingBefore(20f);
        table3b.setHorizontalAlignment(Element.ALIGN_CENTER);
        table3b.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell cel3b = new PdfPCell();
        cel3b.setBackgroundColor(BaseColor.WHITE);
        cel3b.setBorder(1); 
        for(IndentFormDTO i: indentObj){
        cel3b.setPhrase(new Phrase("CC to:\n"+i.getEmpProfileDTo().getFirstName()+"  "+i.getEmpProfileDTo().getLastName()
                + "\nIndent No."+i.getIndentNumber()+",   Date:"+i.getIndentDate(), f9));
        cel3b.setPaddingLeft(5f);  
        cel3b.setPaddingBottom(5f);
        cel3b.setBorderWidthLeft(0.2f);
        cel3b.setFixedHeight(50f);
        //cel3.setBorderWidthRight(0.2f);
        cel3b.setBorderWidthBottom(0.2f);        
        table3b.addCell(cel3b);
        
        cel3b.setPhrase(new Phrase("Certified in Pre-Audit. Expenditure is Debitable to xxxx-xxxxx-xxxx\n\n       AA", f9));
        cel3b.setPaddingLeft(5f);  
        cel3b.setFixedHeight(50f);
        cel3b.setPaddingBottom(5f);
        cel3b.setBorderWidthLeft(0.2f);
        cel3b.setBorderWidthRight(0.2f);
        cel3b.setBorderWidthBottom(0.2f);        
        table3b.addCell(cel3b);
        }
        cel3b.setPhrase(new Phrase("ACCOUNTS / INDENTER / GROUP: SO / STORES / OFFICE COPY ", f9));
        cel3b.setColspan(2);
        cel3b.setPaddingLeft(5f);        
        cel3b.setPaddingBottom(5f);
        cel3b.setBorderWidthLeft(0.2f);
        cel3b.setBorderWidthRight(0.2f);
        cel3b.setBorderWidthBottom(0.2f);  
        cel3b.setFixedHeight(20f);
        cel3b.setHorizontalAlignment(Element.ALIGN_CENTER);
        table3b.addCell(cel3b);
        
        document.add(table3b);
        }
        document.newPage();
        //**************** PAGE NO 4 *******************************************
        
        Paragraph headerParabc = new Paragraph("  ");
        headerParabc.setAlignment(Element.ALIGN_CENTER);
        headerParabc.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 11, Font.UNDERLINE));
        headerParabc.setSpacingBefore(0f);
        document.add(headerParabc);
        
                
        Image imgheadbc = Image.getInstance(absoluteDiskPath);        
        imgheadbc.setAbsolutePosition(280f, 730f);
        imgheadbc.scaleAbsolute(40, 52);
        imgheadbc.setDpi(3, 3);
        document.add(imgheadbc);
        
        
        
        PdfPTable headerTablebc = new PdfPTable(2);
        headerTablebc.setWidthPercentage(90.0f);
        headerTablebc.setWidths(new float[] {50.0f, 50.0f});
        headerTablebc.setSpacingBefore(40f);
        
        
       
        
        PdfPCell cellbc = new PdfPCell();
        cellbc.setBackgroundColor(BaseColor.WHITE);
        cellbc.setBorder(0);
        cellbc.setPadding(1);
        
        
        cellbc.setPhrase(new Phrase("भारत सरकार / Government of India", font10));
        cellbc.setPaddingLeft(0f);
        headerTablebc.addCell(cellbc); 
        cellbc.setPhrase(new Phrase("ECIL Road, ECIL Post, ", font10));
        cellbc.setPaddingLeft(70f);
        headerTablebc.addCell(cellbc);
        
        cellbc.setPhrase(new Phrase("परमाणु ऊर्जा विभाग / Department of Atomic Energy", font10));
        cellbc.setPaddingLeft(0f);
        headerTablebc.addCell(cellbc); 
        cellbc.setPhrase(new Phrase("हैदराबाद / hyderabad", font10));
        cellbc.setPaddingLeft(70f);
        headerTablebc.addCell(cellbc);
        
        cellbc.setPhrase(new Phrase("क्रयएवंभंडारनिदेशालय / Directorate of Purchase & Stores", font10));
        cellbc.setPaddingLeft(0f);
        headerTablebc.addCell(cellbc);
        cellbc.setPhrase(new Phrase("फ़ोन / phone : ", font10));
        cellbc.setPaddingLeft(70f);
        headerTablebc.addCell(cellbc);
        
        cellbc.setPhrase(new Phrase("क्षेत्रीयक्रयएकक,(मनुगुरु) / Regional Purchase Unit, (MANUGURU)", font10));
        cellbc.setPaddingLeft(0f);
        headerTablebc.addCell(cellbc);
        cellbc.setPhrase(new Phrase("फैक्स / fax : ", font10));
        cellbc.setPaddingLeft(70f);
        headerTablebc.addCell(cellbc);
        
        cellbc.setPhrase(new Phrase("E - Mail : ", font10));
        cellbc.setPaddingLeft(0f);
        headerTablebc.addCell(cellbc);
        cellbc.setPhrase(new Phrase("दिनांक /date : ", font10));
        cellbc.setPaddingLeft(70f);
        headerTablebc.addCell(cellbc);
        
        document.add(headerTablebc);
        
        
        Font f5bc = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c5bc = new Chunk("PURCHASE ORDER            DPS P-45", f5bc);
        c5bc.setBackground(BaseColor.WHITE);
        Paragraph p5bc = new Paragraph(c5bc);
        p5bc.setAlignment(Element.ALIGN_RIGHT);
        p5bc.setSpacingBefore(0f);       
        p5bc.setIndentationRight(170f);
        document.add(p5bc);
        
        for(PoEntryDTO po: poObj){
        
        Font f6bc = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c6bc = new Chunk("Purchase Oreder No : DPS/RPUM "+PoNumber+"                                                  Date:"+po.getPoDate(), f6bc);
        c6bc.setBackground(BaseColor.WHITE);
        Paragraph p6bc = new Paragraph(c6bc);
        p6bc.setAlignment(Element.ALIGN_LEFT);
        p6bc.setSpacingBefore(5f); 
        p6bc.setIndentationLeft(30f);
        document.add(p6bc);
        
        PdfPTable tablebc = new PdfPTable(2);
        tablebc.setWidthPercentage(90.0f);
        tablebc.setSpacingBefore(5f);
        tablebc.setHorizontalAlignment(Element.ALIGN_CENTER);
        tablebc.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell celbc = new PdfPCell();
        celbc.setBackgroundColor(BaseColor.WHITE);
        celbc.setBorder(1);        
        
        Font f7bc = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.NORMAL, BaseColor.BLACK);
        
        celbc.setPhrase(new Phrase("M/s. "+po.getVendorObj().getVendorName()+",\n"+po.getVendorObj().getVendorAddress()+",\n"
                + ""+po.getVendorObj().getVendorCity()+",\n"+po.getVendorObj().getVendorPin(), f7bc));
        celbc.setPaddingLeft(5f);
        celbc.setFixedHeight(60f);
        //cel.setBorderWidthLeft(1.0f);
        celbc.setBorderWidthBottom(0.2f);        
        tablebc.addCell(celbc);
        celbc.setPhrase(new Phrase("vcode :"+po.getVenderName()+",   Exp.date:"+po.getVendorObj().getExpiraryDate()+"\n"
                +""+po.getReferenceNo()+",\n"
                + ""+po.getVendorObj().getVendorPhone()+",   Fax :"+po.getVendorObj().getVendorFax()+"\n"
                + "Email :"+po.getVendorObj().getVendorEmail(), f7bc));
        celbc.setPaddingLeft(5f);
        celbc.setFixedHeight(60f);
        celbc.setBorderWidthLeft(0.2f);
        celbc.setBorderWidthBottom(0.2f);
        //cel.setBorderWidthRight(0.2f);
        tablebc.addCell(celbc);
        
        document.add(tablebc);
        
        
        Font f8bc = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk c8bc = new Chunk("           निम्‍नलिखितसामानकीआपूर्तिकेलिएआपकीदिनांक 21/03/2017 कीनिविदासंख्‍या   HRPU/NFC/CAP/10455 "
                + "मेंप्रस्‍तुतआपकेप्रस्‍तावकोभारतकेराष्‍ट्रपतिकीओरसेफार्मसंख्‍याक्रभनिक्र-102 "
                + "मेंउल्लिखितसभीठेकोंकीसामान्‍यशर्तेंएवंसंयंत्रवमशीनरीकीपूर्तिकोसंचालितकरनेवालीविशेषशर्तेंक्रयएवंभंडारनिदेशालय(भारतसरकार,परमाणुऊर्जाविभागकेअधीन)"
                + "मेंभारतकेराष्‍ट्रपतिद्वारादीगईठेकोंपरलागूहोनेवालीसभीठेकोंकीसामान्‍यशर्तेंवसंयंत्रवमशीनरीकीपूर्तिकोसंचालितकरनेवालीविशेषशर्तेंनामकपुस्तिकामेंसम्मिलितशर्तोंतथाइसक्रयादे"
                + "शमेंअंतर्विष्‍टशर्तोंएवंनिबंधनोंकेअधीनतथानिम्‍नब्‍यौरेवारदीगईदरोंकेअधीनस्‍वीकारकियाजाताहै।  \n     Your offer contained "
                + "in the Tender no."+PoNumber+",  Dated:"+po.getPoDate()+" to supply undermentioned stores is accepted on behalf of the "
                + "President of India, subject to the general conditions of all contracts and special conditions "
                + "of contract governing the supply of plant and machinery in the form no. DPS -102 included in "
                + "pamphletentitled \"general conditions\" applicable to contracts by the president of India in "
                + "the directorate of purchase & stores (under Government of India Department of atomic energy) "
                + "and subject to the terms and conditions contained in this purchase order and to the extent quality "
                + "and at the price specified below:", font10);
        c8bc.setBackground(BaseColor.WHITE);
        Paragraph p8bc = new Paragraph(c8bc);
        p8bc.setAlignment(Element.ALIGN_JUSTIFIED);
        p8bc.setSpacingBefore(5f);       
        p8bc.setIndentationLeft(30f);
        p8bc.setIndentationRight(30f);
        document.add(p8bc);
        
        
        //*********************************************************************
        PdfPTable table2bc = new PdfPTable(6);
        table2bc.setWidthPercentage(90.0f);
        table2bc.setSpacingBefore(20f);
        table2bc.setHorizontalAlignment(Element.ALIGN_CENTER);
        table2bc.setWidths(new float[] {14.0f, 40.0f, 14.0f, 10.0f, 10.0f, 10.0f});
        
        PdfPCell cel2bc = new PdfPCell();
        cel2bc.setBackgroundColor(BaseColor.WHITE);
        cel2bc.setBorder(1); 
        
        //Font f9 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel2bc.setPhrase(new Phrase("Sl NO.: ", f9));
        cel2bc.setPaddingLeft(5f);  
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Description", f9));
        cel2bc.setPaddingLeft(5f); 
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.2f);
        //cel2.setBorderWidthRight(0.2f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Qantity", f9));
        cel2bc.setPaddingLeft(5f);   
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Unit", f9));
        cel2bc.setPaddingLeft(5f);      
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Rate", f9));
        cel2bc.setPaddingLeft(5f);    
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Amount", f9));
        cel2bc.setPaddingLeft(5f);   
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthRight(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        for(IndentFormDTO i: indentObj){
        cel2bc.setPhrase(new Phrase("   "+po.getVenDescription()+"\nDetailed description and terms and conditiond as per annexure.\n"
                + "PAYMENT : 100% payment will be made within 30 days  from the date of receipt of materials at our stores, Heavy"
                + " Water Plant, Manuguru and its final acceptance. Bills in duplicate shall be submitted to Accounts Officer,"
                + "RPUM, Hyderabad. NOTE: Please quote your bank details on your bills to enable ue to make paymentthrough RTGS/NEFT.\n"
                + "INSPECTION : Pre-despatch inspection will be carried out by: "+i.getEmpProfileDTo().getFirstName()+" "+i.getEmpProfileDTo().getLastName(), f9));
        }
        cel2bc.setColspan(6);
        cel2bc.setPaddingLeft(10f);  
        cel2bc.setPaddingBottom(5f);
        cel2bc.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Price: "+po.getPrice(), f9));
        cel2bc.setColspan(2);
        cel2bc.setPaddingLeft(5f);
        cel2bc.setPaddingBottom(5f);        
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        cel2bc.setPhrase(new Phrase("Delivery Period : "+po.getDeliveryPeriod()+"\nON OR BEFORE", f9));
        cel2bc.setColspan(4);
        cel2bc.setPaddingLeft(5f);  
        cel2bc.setPaddingBottom(5f);
        cel2bc.setBorderWidthLeft(0.1f);
        cel2bc.setBorderWidthBottom(0.2f);        
        table2bc.addCell(cel2bc);
        
        document.add(table2bc);
        
        //*********************************************************************
        
         
        Chunk caBc = new Chunk("      क्रयऔरभंडारनिदेशालय(भारतसरकार,परमाणुऊर्जाविभागकेअधीन)द्वारादियेजानेवालेठेकोंपरलागूहोनेवालीसभीठेकोंकीसामान्‍य"
                + "शर्तेंएवंसंयंत्रवमशीनरीकीआपूर्तिकोसंचालितकरनेवालीविशेषशर्तेंतथायहक्रयादेशहीइसठेकेकाएकमात्रआधारहोगा।  \n     General conditions of all "
                + "contracts and special conditions of contracts governing the supply of plant and machinery "
                + "applicable to contracts placed bythedirectorate of purchase & stores "
                + "(under government of India, department of atomic energy) and this purchase order "
                + "shall be the sole repository of the contract.", font10);                
        caBc.setBackground(BaseColor.WHITE);
        Paragraph paBc = new Paragraph(caBc);
        paBc.setAlignment(Element.ALIGN_JUSTIFIED);
        paBc.setSpacingBefore(5f);       
        paBc.setIndentationLeft(30f);
        paBc.setIndentationRight(30f);
        document.add(paBc);
        
        Font fbbc = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cbbc = new Chunk("Yours Faithfully,", fbbc);                
        cbbc.setBackground(BaseColor.WHITE);
        Paragraph pbbc = new Paragraph(cbbc);
        pbbc.setAlignment(Element.ALIGN_RIGHT);
        pbbc.setSpacingBefore(20f);
        pbbc.setIndentationRight(70f);
        document.add(pbbc);
        
        
        Chunk ccbc = new Chunk(" "+po.getSignObj().getSignatoryName()+"  ("+po.getSignObj().getSignatoryCode()+")", fbbc);                
        ccbc.setBackground(BaseColor.WHITE);
        Paragraph pcbc = new Paragraph(ccbc);
        pcbc.setAlignment(Element.ALIGN_RIGHT);
        pcbc.setSpacingBefore(5f);
        pcbc.setIndentationRight(90f);
        document.add(pcbc);
        
        Chunk cdbc = new Chunk(" "+po.getSignObj().getSignatoryDes(), fbbc);                
        cdbc.setBackground(BaseColor.WHITE);
        Paragraph pdbc = new Paragraph(cdbc);
        pdbc.setAlignment(Element.ALIGN_RIGHT);
        pdbc.setSpacingBefore(3f);
        pdbc.setIndentationRight(60f);
        document.add(pdbc);
        
        Chunk cebc = new Chunk("For and on behalf of the President Of India(The Purchaser", fbbc);                
        cebc.setBackground(BaseColor.WHITE);
        Paragraph pebc = new Paragraph(cebc);
        pebc.setAlignment(Element.ALIGN_RIGHT);
        pebc.setSpacingBefore(3f);
        pebc.setIndentationRight(30f);
        document.add(pebc);
        } //End for loop po
        //***************************END PAGE NO 4 *****************************
       
       for(PoEntryDTO p: poObj){
        document.newPage();    
        
        //***Tax temp;ates display here****************
        Paragraph hea = new Paragraph("       ");
        hea.setAlignment(Element.ALIGN_JUSTIFIED);
        hea.setFont(FontFactory.getFont(FontFactory.COURIER, 9, Font.NORMAL));
        hea.setSpacingBefore(30f);
        document.add(hea);
        
        Font SDD1 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk taxes = new Chunk(""+p.getOffer(), SDD1);                
        taxes.setBackground(BaseColor.WHITE);
        Paragraph taxesa = new Paragraph(taxes);
        taxesa.setAlignment(Element.ALIGN_JUSTIFIED);
        taxesa.setSpacingBefore(10f);
        taxesa.setIndentationLeft(60f);
        taxesa.setIndentationRight(30f);
        document.add(taxesa);
        
        //***END: Tax temp;ates display here ***********
        document.newPage();
        //***********************START NEW PAGE ********************************
        
        Font fbbc1 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        log.info(p.getpBG()+"-----mmmm----"+p.getSecurityDeposit());
        String sd = "a) In accordance with clause no.4 of DPS P-11 the contractor shall furnish a security\n"
                       + "deposite for RS.       (& bgamnt) i.e 10% Basic cost of the equipment towards satisfactory\n"
                       + "performance of the contract. The security deposite may be furnished in the form of bank of India \n"
                       + "or reputed private banks viz.,HDFC/ICICI/AXIS/IDBI strictly as per the format enclosed. The bank\n"
                       + "gaurantee shall remain valid throughout the currency of the contract with two months grace period. \n"
                       + "The contractor should furnish the security deposit within 15days from the date of receipt of this purchase order.\n"
                       + "Please note that the security deposit will summarily be forfeited for non-complaince of the terms and conditions of the order.\n"
                       + "\n"
                       + "b)The bank guarantee shall be sent by the bank directly to the purchaser, thus obviating the necessity for  getting the genuiness of the \n"
                       + "bank guarantee established.\n"
                       + "\n"
                       + "C)Please furnish the contract details of BG issuing bank viz., phone no., faxno. and\n"
                       + "email-id along with bank guarantee to enable us to confirm acceptance of the same.\n"
                       + "\n"
                       + "d)The security deposit shall also be submitted in the form of DEMAND DRAFT drawn in favour of senior Accounts Officer, RPUM,\n"
                       + "Hyderabad payable at Hyderabad.";
        
           String pbg = ""                   
                   + "a) For satisfactory performance of the equipment, the contractor shall furnish a performance bond in the form of a \n"
                   + "Bank Guarantee for an amount of Rs.   (&pbg) towards 10% of contract value as per the format attached to the undersigned\n"
                   + "immediately after final acceptance of the equipment to release the payment. Bank guarantee shall be drawn from State Bank of India\n"
                   + "or any Nationalized Bank of India or reputed private banks viz., HDFC/ICICI/AXIS/IBDI. And shall be valid through out warrantee /\n"
                   + "guarantee period with two months grace period.\n"
                   + "\n"
                   + "b)The validity of performance bond bank guarantee should be reckoned from the date of final acceptance of the equipment only.\n"
                   + "\n"
                   + "c)The bank guarantee shall by the bank directly to the purchaser, thus obviating the necessity for getting the genuineness of the bank \n"
                   + "guarantee established.\n"
                   + "\n"
                   + "d)Please furnish the contact details of BG issuing bank viz., phone no, fax no, and email-id along with bank guarantee to enable us to confirm\n"
                   + "acceptance of the same.\n"
                   + "\n"
                   + "e)The performance bond shall also be submitted in the form of DEMJAND DRAFT drawn in favour of Senior Accounts Officer, RPUM, Hyderabad payable at Hyderabad.";
           if (p.getSecurityDeposit() != " " && p.getpBG().isEmpty()) {
               Chunk cebcH = new Chunk("Security Deposite:    \n", heading);
               cebcH.setBackground(BaseColor.WHITE);
               Paragraph pebcH = new Paragraph(cebcH);
               pebcH.setAlignment(Element.ALIGN_LEFT);
               pebcH.setSpacingBefore(3f);
               pebcH.setIndentationLeft(30f);
               pebcH.setIndentationRight(30f);
               document.add(pebcH);
               
               Chunk cebc1 = new Chunk(sd, fbbc1);
               cebc1.setBackground(BaseColor.WHITE);
               Paragraph pebc1 = new Paragraph(cebc1);
               pebc1.setAlignment(Element.ALIGN_LEFT);
               pebc1.setSpacingBefore(3f);
               pebc1.setIndentationLeft(30f);
               pebc1.setIndentationRight(30f);
               document.add(pebc1);
           } else if (p.getSecurityDeposit().isEmpty() && p.getpBG() != " ") {
               Chunk cebH = new Chunk("Performance Bond:\n", heading);
               cebH.setBackground(BaseColor.WHITE);
               Paragraph pebH = new Paragraph(cebH);
               pebH.setAlignment(Element.ALIGN_LEFT);
               pebH.setSpacingBefore(3f);
               pebH.setIndentationLeft(30f);
               pebH.setIndentationRight(30f);
               document.add(pebH);
               
               Chunk cebc2 = new Chunk(pbg, fbbc1);
               cebc2.setBackground(BaseColor.WHITE);
               Paragraph pebc2 = new Paragraph(cebc2);
               pebc2.setAlignment(Element.ALIGN_LEFT);
               pebc2.setSpacingBefore(3f);
               pebc2.setIndentationLeft(30f);
               pebc2.setIndentationRight(30f);
               document.add(pebc2);
           } else if (p.getSecurityDeposit() != " " && p.getpBG() != " ") {
               Chunk cebcH = new Chunk("Security Deposite:    \n", heading);
               cebcH.setBackground(BaseColor.WHITE);
               Paragraph pebcH = new Paragraph(cebcH);
               pebcH.setAlignment(Element.ALIGN_LEFT);
               pebcH.setSpacingBefore(3f);
               pebcH.setIndentationLeft(30f);
               pebcH.setIndentationRight(30f);
               document.add(pebcH);
               
               Chunk cebc1 = new Chunk(sd, fbbc1);
               cebc1.setBackground(BaseColor.WHITE);
               Paragraph pebc1 = new Paragraph(cebc1);
               pebc1.setAlignment(Element.ALIGN_LEFT);
               pebc1.setSpacingBefore(3f);
               pebc1.setIndentationLeft(30f);
               pebc1.setIndentationRight(30f);
               document.add(pebc1);
               
               Chunk cebH = new Chunk("Performance Bond:\n", heading);
               cebH.setBackground(BaseColor.WHITE);
               Paragraph pebH = new Paragraph(cebH);
               pebH.setAlignment(Element.ALIGN_LEFT);
               pebH.setSpacingBefore(3f);
               pebH.setIndentationLeft(30f);
               pebH.setIndentationRight(30f);
               document.add(pebH);
               
               Chunk cebc2 = new Chunk(pbg, fbbc1);
               cebc2.setBackground(BaseColor.WHITE);
               Paragraph pebc2 = new Paragraph(cebc2);
               pebc2.setAlignment(Element.ALIGN_LEFT);
               pebc2.setSpacingBefore(3f);
               pebc2.setIndentationLeft(30f);
               pebc2.setIndentationRight(30f);
               document.add(pebc2);
           }
        
       } //End for loop
       
       document.newPage();
       //***********************PAGE NO 5 **************************************
       
       //***Note template display here****************
       Font note1 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cebcn1 = new Chunk("PACKING, DESPATCHING, BILLING & OTHER IMPORTANT CONDITIONS:", note1);                
        cebcn1.setBackground(BaseColor.WHITE);
        Paragraph pebcn1 = new Paragraph(cebcn1);
        pebcn1.setAlignment(Element.ALIGN_LEFT);
        pebcn1.setSpacingBefore(3f);
        pebcn1.setIndentationLeft(30f);
        pebcn1.setIndentationRight(30f);
        document.add(pebcn1);
       
       for(PoDetailsDTO p: noteObj){
        Font note = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cebcn = new Chunk("\n"+p.getNoteDes(), note);                
        cebcn.setBackground(BaseColor.WHITE);
        Paragraph pebcn = new Paragraph(cebcn);
        pebcn.setAlignment(Element.ALIGN_JUSTIFIED);
        pebcn.setSpacingBefore(3f);
        pebcn.setIndentationLeft(30f);
        pebcn.setIndentationRight(30f);
        document.add(pebcn);
       }
        //***END: Note template display here ***********
       
       document.newPage();
       //******************PAGE NO 6 *******************************************
       
        Font SD1 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cebsd = new Chunk("BANK GUARANTEE FORMAT FOR SECURITY DEPOSITE", SD1);                
        cebsd.setBackground(BaseColor.WHITE);
        Paragraph cebasd = new Paragraph(cebsd);
        cebasd.setAlignment(Element.ALIGN_CENTER);
        cebasd.setSpacingBefore(10f);
        cebasd.setIndentationLeft(30f);
        cebasd.setIndentationRight(30f);
        document.add(cebasd);
        
        
        PdfPTable sdtable = new PdfPTable(1);
        sdtable.setWidthPercentage(90.0f);
        sdtable.setWidths(new float[] {100.0f});
        sdtable.setSpacingBefore(10f);
        
        
        PdfPCell celsd = new PdfPCell();
        celsd.setBackgroundColor(BaseColor.WHITE);
        celsd.setBorder(0);
        celsd.setPadding(1);
        
        Font SD3 = new Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
        celsd.setPhrase(new Phrase("The President Of India", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("(Acting through the Reginoal", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("Director, Purchase & Stores)", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("Government Of India,", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("Department Of Atomic Energy", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("Regional Purchase Unit (Munuguru)", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("HWGF Bldg., ECIL Road, ECIL Post,", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        celsd.setPhrase(new Phrase("HYDERABAD - 500062.", SD3));
        celsd.setPaddingLeft(0f);
        sdtable.addCell(celsd);
        
        document.add(sdtable);
        
        for(PoEntryDTO p: poObj){    
        StringBuilder declStr = new StringBuilder();
        declStr.append("\nSir,    \n\n1. In consideration of the President of India acting through the Regional Director (P&S), Directorate of ");
        declStr.append("Purchase & Stores, Regional Purchase Unit (Manuguru),HWGF Bldg.,ECIL Road, ECIL Post, Hyderabad -" );
        declStr.append("500 062(hereinafter called The Government)having agreed to exempt "+p.getVendorObj().getVendorName()+", "+p.getVendorObj().getVendorAddress()+", "+p.getVendorObj().getVendorCity()+", "+p.getVendorObj().getVendorPin());
        declStr.append("(Hereinafter called The Contractor(s) from the demand, under the Terms and Condition of");
        declStr.append("an Agreement bearing No. RPUM/HWP/"+PoNumber+", made between"+p.getVendorObj().getVendorName()+", "+p.getVendorObj().getVendorCity());
        declStr.append("and The President of India, acting through Regional Director (P&S), Regional Purchase ");
        declStr.append("Unit(Manuguru), HWGF Bldg., ECIL Road, ECIL Post, Hyderabad - 500 062 for");
        declStr.append("(hereinafter called The Said Agreement) of Security Deposite for the ");
        declStr.append("due fulfillment by the said contractor(s)of the terms and conditained in the said agreement, on");
        declStr.append("production of a Bank Guarantee for Rs "+p.getPoValue()+" We,(hereinafter referred to as the bank) on the request");
        declStr.append("of "+p.getVendorObj().getVendorName()+", contractor(s) do hereby undertake to pay to the");
        declStr.append("Government an amount not exceeding Rs"+p.getPoValue()+" against any loss or damage caused to or suffered or");
        declStr.append("would be caused to or suffered by the Government by reason of any breach by the said Contractor(s)of");
        declStr.append("any of the terms conditions contained in the said agreement.");
        
        //StringBuilder declStr1 = new StringBuilder();
        declStr.append("\n\n2.We(Indicate the Name of the Bank)do hereby undertake to pay the amount due and payable under this");
        declStr.append("Guarantee without any demur, merely on a demand from the Regional Director(P&S)on behalf of");
        declStr.append("Government stating that the amount claimed is due by way of loss or damage caused to or would be caused");
        declStr.append("to or suffered by the Government by reason of breach by the said contractor(s) of any of the terms and");
        declStr.append("conditions contained in the said agreement or by reason of the contractor(s) failure to perform the said");
        declStr.append("agreement. Any such demand made on the Bank shall be conclusive as regards the amount due and");
        declStr.append("payable by the Bank under this Guarantee. However, our liability under this Guarantee shall be restricted to");
        declStr.append("an amount not exceeding");
        
       
        //StringBuilder declStr = new StringBuilder();
        declStr.append("\n\n3.We (Indicate the Name of the Bank)undertake to pay to the Government any money so demanded not");
        declStr.append("withstanding any dispute or disputes raised by the contractor/s in any suit or proceeding before any  ");
        declStr.append("court or tribunal relating thereto our liability under this presnt being absolute and unequivocal. The");
        declStr.append("payment so made by us under this bond shall be a valid discharge of our liability for payment there under");
        declStr.append("and the contractor/s shall have no claim against us for making such payment.");
        
        //StringBuilder declStr = new StringBuilder();
        declStr.append("\n\n4. We (Indicate the Name of Bank)further agree that the Guarantee herein contained shall remain in full");
        declStr.append("force and effect during the period that would be taken for the performance of the said agreement and that it");
        declStr.append("shall continue to be enforceable till all the dues of the Government under or by virtue of the said agreement");
        declStr.append("have been fully paid and its claims satisfied or discharged or till the Regional Director, Purchase & Stores of");
        declStr.append("the Department of Atomic Energy certified that the terms & conditions of the said agreement have been fully");
        declStr.append("and properly carried out by the said contractor(s) and accordingly discharges this Guarantee unless a");
        declStr.append("demand or a claim under this Guarantee is made on us in writing on or before ___________ we shall be");
        declStr.append("discharged from all liability under this Guarantee thereafter.");
        
        
        //StringBuilder declStr = new StringBuilder();
        declStr.append("\n\n5.We (Indicate the Name of Bank)further agree with the Government shall have the");
        declStr.append("fullest liberty without our consent and without affecting in any manner our obligations hereunder to vary any");
        declStr.append("of the terms and conditions of the said Agreement or to extend time of performance by the said contractor(s)");
        declStr.append("from time to time or to postpone for any time or from time to time any of the power exercisable by the");
        declStr.append("Government against the said contractor(s) and to forbear or enforce any of the terms and conditions");
        declStr.append("relating to the said agreement and we shall not be relieved from our liability by reason of any such");
        declStr.append("variation or extension being granted to the said contractor(s) or for any forbearance. act or commission on");
        declStr.append("the part of the Government or any indulgence by the Government to the said contractor(s) or by any such");
        declStr.append("matter or thing whatsoever which under the law relating to sureties would but for this provision, have effect");
        declStr.append("of so relieving us.");
        
        //StringBuilder declStr = new StringBuilder();
        declStr.append("\n\n6.This Guarantee will not be discharged due to the change in the constitution of the bank or the contractor/s.");
        
        //StringBuilder declStr = new StringBuilder();
        declStr.append("\n\n7. We(Indicate the Name of Bank)lastly undertake not to revoke this Guarantee during its currency except");
        declStr.append("with the previous consent of the Government in writing.");
        
        //StringBuilder declStr = new StringBuilder();
        declStr.append("\n\nDated the _____________ day of ____________ 20___ For___________________");
        
                
        Font SD10 = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk INDEM4 = new Chunk(declStr.toString(), SD10);                
        INDEM4.setBackground(BaseColor.WHITE);
        Paragraph inde4 = new Paragraph(INDEM4);
        inde4.setAlignment(Element.ALIGN_JUSTIFIED);
        inde4.setSpacingBefore(40f);
        inde4.setIndentationLeft(30f);
        inde4.setIndentationRight(30f);
        document.add(inde4);
        }
        
        document.newPage();
        //*****************NEW PAGE START***************************************
        
        Font SD4 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk cebpbg = new Chunk("BANK GUARANTEE FORMAT FOR PERFORMANCE BOND\n(For Local Order)", SD4);                
        cebpbg.setBackground(BaseColor.WHITE);
        Paragraph cepbg = new Paragraph(cebpbg);
        cepbg.setAlignment(Element.ALIGN_CENTER);
        cepbg.setSpacingBefore(10f);
        cepbg.setIndentationLeft(30f);
        cepbg.setIndentationRight(30f);
        document.add(cepbg);
        
       document.add(sdtable);
        
       StringBuilder declStrn = new StringBuilder();
       for(PoEntryDTO p: poObj){ 
        declStrn.append("\n1.WHEREAS on or about the __________ day of ______________ "+p.getVendorObj().getVendorName()+".");
        declStrn.append(" a company registered under the companies act 1913/1956 and having its registered office at "+p.getVendorObj().getVendorAddress()+","+p.getVendorObj().getVendorCity()+","+p.getVendorObj().getVendorPin()+".");
        declStrn.append("entered into an agreement bearing No. RPUM/HWP/"+PoNumber+", (hereinafter referred to as ");
        declStrn.append("Contract)with the President of India acting through the Regional Director, Purchase & Stores, Directorate of");
        declStrn.append("Purchase & Stores, Department of Atomic Energy (hereinafter referred to as The Government)for supply of");
        declStrn.append("(hereinafter referred to as The Equipment");
        
            
        
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n2.AND WHEREAS under the terms and condition of the contract an amount of Rs "+p.getPoValue()+", ");
        declStrn.append("representing balance 10 percent payment out of");
        declStrn.append("the total value of the contract of Rs "+p.getPoValue()+", ");
        declStrn.append("is to be paid to the contractor on the final acceptance of the equipment and on the contractor");
        declStrn.append("furnishing a bank guarantee in a manner herein contained duly executed by a Scheduled/Nationalized Bank/");
        declStrn.append("Private Banks indiccated in Po towards satisfactory performance of the equipment during warranty period, viz.");
        declStrn.append("12 months from the date of acceptance/commissioning of the said equipment or 18 months from the date of");
         declStrn.append("dispatch of the last lot of consignmant whichever is earlier.");
       
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n3. NOW WE, The _______________(Bank) in consideration of the promises and the");
        declStrn.append("payment of the said sum of Rs "+p.getPoValue()+", ");
        declStrn.append("by the Government to the Contractor do here by agree and undertake to pay to the Regional Director,");
        declStrn.append("Purchase and Stores, Department of Atomic Energy on behalf of the Government the amount due and payable");
        declStrn.append("under the guarantee without any demur, merely on a demand from the Regional Director, Purchase and");
        declStrn.append("Stores, Department of Atomic Energy stating that the amount claimed is due by way of loss or damage caused");
        declStrn.append("to or suffered by the Government by reason of unsatisfactory performance of the equipment during the");
        declStrn.append("warranty period. Any such demand made on the bank shall be conclusive as regards the amount due and");
        declStrn.append("payable by the bank under this guarantee. However, our liability under this guarantee shall be restricted to an");
        declStrn.append("amount not exceeding Rs "+p.getPoValue());
    }
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n4. We, _____________ (Bank) undertake to pay to the Government any money so demanded");
        declStrn.append("notwithstanding any dispute or disputes raised by the contractor, in any suit or proceedings pending before");
        declStrn.append("any court or tribunal relating thereto, our liability under this present being absolute and unequivocal. The");
        declStrn.append("payment so made by us under this bond shall be valid discharge of our liability for payment there under and");
        declStrn.append("the contractor(s) shall have no claim against us for making such payment.");
        
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n5. We,_________________(Bank), hereby further agree that the decision of the Regional Director,");
        declStrn.append("Purchase and Stores, department of Atomic Energy, as to whether the said equipment is giving satisfactory");
        declStrn.append("performance or not during the warranty period and as to the amount of damages suffered by the Government");
        declStrn.append("on account of unsatisfactory performance of the said equipment shall be final and binding on us.");
                                
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n6. AND WE, The _________________ (Bank),do hereby agree that our liability hereunder shall not be");
        declStrn.append("discharged by virtue of any agreement between the Government and the Contractor whether with or without");
        declStrn.append("our knowledge and/or consent or by reason of the Government showing any indulgence or forbearance to the");
        declStrn.append("contractor whwther as to payment, time for performance, or any other matter whatsoever relating to the");
        declStrn.append("contract which but for this provision would amount to discharge of the surety under the law.");
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n7. This gurantee will not be discharged due to the change in the constitution of the Bank or Contractor(s).");
        
        
        
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\n8. Our Guarantee shall remain in force until ____________ and unless a claim under the guarantee is");
        declStrn.append("lodged with us six months from that date, all right of the Government under the guarantee shall be");
        declStrn.append("forfeited and we shall be relieved and discharged from all our liabilities there under.");
        
        
        //StringBuilder declStr = new StringBuilder();
        declStrn.append("\n\nDated the ____________ day of ______________201_____ For ___________________(Indicate the name of bank)");
               
        
        Font SD9 = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk INDEM3 = new Chunk(declStrn.toString(), SD9);                
        INDEM3.setBackground(BaseColor.WHITE);
        Paragraph inde3 = new Paragraph(INDEM3);
        inde3.setAlignment(Element.ALIGN_JUSTIFIED);
        inde3.setSpacingBefore(40f);
        inde3.setIndentationLeft(30f);
        inde3.setIndentationRight(30f);
        document.add(inde3);
        
        document.newPage();
        //**********************NEW PAGE            ****************************
        
        Font SD5 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk SHIIP = new Chunk("SHIPPING RELEASE", SD5);                
        SHIIP.setBackground(BaseColor.WHITE);
        Paragraph shpping = new Paragraph(SHIIP);
        shpping.setAlignment(Element.ALIGN_CENTER);
        shpping.setSpacingBefore(10f);
        shpping.setIndentationLeft(30f);
        shpping.setIndentationRight(30f);
        document.add(shpping);
        
        PdfPTable ship = new PdfPTable(1);
        ship.setWidthPercentage(90.0f);
        ship.setWidths(new float[] {100.0f});
        ship.setSpacingBefore(10f);
        
        
        PdfPCell cels = new PdfPCell();
        cels.setBackgroundColor(BaseColor.WHITE);
        cels.setBorder(0);
        cels.setPadding(1);
        
        Font SD6 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
        for(PoEntryDTO pr: poObj){         
        celsd.setPhrase(new Phrase("Ref. NO.:"+pr.getReferenceNo()+",                                               Date:"+pr.getPoDate(), SD6));
        celsd.setPaddingLeft(0f);
        ship.addCell(celsd);        
        celsd.setPhrase(new Phrase("Purchase Order No. : DPS/ RPUM / "+PoNumber, SD6));
        celsd.setPaddingLeft(0f);
        ship.addCell(celsd);
        celsd.setPhrase(new Phrase("Supplier : M/s. "+pr.getVendorObj().getVendorName()+".,"
                + "\n"+pr.getVendorObj().getVendorAddress()+","
                + "\n"+pr.getVendorObj().getVendorCity()+","
                + "\n"+pr.getVendorObj().getVendorPin(), SD6));
        celsd.setPaddingLeft(0f);
        ship.addCell(celsd);
        celsd.setPhrase(new Phrase("Shipment Status :", SD6));
        celsd.setPaddingLeft(0f);
        ship.addCell(celsd);
        }
        document.add(ship);
        
        PdfPTable tables = new PdfPTable(8);
        tables.setWidthPercentage(90.0f);
        tables.setSpacingBefore(20f);
        tables.setHorizontalAlignment(Element.ALIGN_CENTER);
        tables.setWidths(new float[] {12.0f, 12.0f, 12.0f, 20.0f, 10.0f, 10.0f, 10.0f, 10.0f});
        
        PdfPCell celh = new PdfPCell();
        celh.setBackgroundColor(BaseColor.WHITE);
        celh.setBorder(1);        
        
        Font f12 = new Font(Font.FontFamily.COURIER, 9.0f, Font.NORMAL, BaseColor.BLACK);
        
        celh.setPhrase(new Phrase("P.O. Item No.", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Quality Ordered", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Quality Earlier Released", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Description", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Quality Submitted", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Quantity Accepted", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Quantity Rejected", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("Remarks,size & Weight of Crates", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(30f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthRight(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        // second row
        celh.setPhrase(new Phrase(" ", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase(" ", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase(" ", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        celh.setPhrase(new Phrase("", f12));
        celh.setPaddingLeft(5f);
        celh.setFixedHeight(400f);
        celh.setBorderWidthLeft(0.1f);
        celh.setBorderWidthRight(0.1f);
        celh.setBorderWidthBottom(0.1f);        
        tables.addCell(celh);
        
        document.add(tables);
       // buttom table
        PdfPTable ship1 = new PdfPTable(1);
        ship1.setWidthPercentage(90.0f);
        ship1.setWidths(new float[] {100.0f});
        ship1.setSpacingBefore(10f);
        
        
        PdfPCell cels1 = new PdfPCell();
        cels1.setBackgroundColor(BaseColor.WHITE);
        cels1.setBorder(0);
        cels1.setPadding(1);        
        
                 
        cels1.setPhrase(new Phrase("The material listed above as accepted are hereby released for shipment.", SD6));
        cels1.setPaddingLeft(0f);
        cels1.setPaddingBottom(5f);
        ship1.addCell(cels1);  
        cels1.setPhrase(new Phrase("Release of the above material does not relieve the supplier of his responsibility to ensure\n"
                + "that the material are in accordance with the terms of specification.", SD6));
        cels1.setPaddingLeft(0f);
        cels1.setPaddingBottom(5f);
        ship1.addCell(cels1);
        cels1.setPhrase(new Phrase("Signature : ___________________________                                      Date :_____________", SD6));
        cels1.setPaddingLeft(0f);
        cels1.setPaddingBottom(5f);
        ship1.addCell(cels1);
        cels1.setPhrase(new Phrase("Name : ________________________________", SD6));
        cels1.setPaddingLeft(0f);
        cels1.setPaddingBottom(5f);
        ship1.addCell(cels1);
        cels1.setPhrase(new Phrase("Designation : _________________________", SD6));
        cels1.setPaddingLeft(0f);
        cels1.setPaddingBottom(5f);
        ship1.addCell(cels1);
        
        document.add(ship1);
        
        document.newPage();
        //************************* NEW PAGE     *******************************
        Font SDD7 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk bgua = new Chunk("BANK GUARANTEE FORMAT TOWARDS ISSUE OF FREE ISSUE MATERIAL\n"
                + "(ON NON-JUDICIAL STAMP PAPER OF APPROPRIATE VALUE)", SDD7);                
        bgua.setBackground(BaseColor.WHITE);
        Paragraph bguaa = new Paragraph(bgua);
        bguaa.setAlignment(Element.ALIGN_CENTER);
        bguaa.setSpacingBefore(30f);
        bguaa.setIndentationLeft(30f);
        bguaa.setIndentationRight(30f);
        document.add(bguaa);
        
        for(PoEntryDTO po: poObj){
        Chunk bgua1 = new Chunk("Ref:Purchase Order No: RPUM / HWP "+PoNumber
                + "                                         Dated: "+po.getPoDate(), SDD7);                
        bgua1.setBackground(BaseColor.WHITE);
        Paragraph bguaa1 = new Paragraph(bgua1);
        bguaa1.setAlignment(Element.ALIGN_LEFT);
        bguaa1.setSpacingBefore(10f);
        bguaa1.setIndentationLeft(30f);        
        document.add(bguaa1);
        }
        
        StringBuilder declStra = new StringBuilder();
        declStra.append("WHERE AS on or about the ________ (Date), thePresident of India, acting through the Regional");
        declStra.append("Director, Purchase & Stores, Directorate of purchase & Stores, Department of Atomic Energy,");
        declStra.append("(Hereinafter referred to as 'the purchaser') has entered into an agreement: Purchase Order No.");
        declStra.append("___________ dated__________ for manufacture, inspection, testing and safe delivery of __________");
        declStra.append("(herein after referred to as 'the Equipment')with M/s.____________________________");
        declStra.append("(hereinafter referred to as 'the Contractor.')\n\n");
        
                   
        
        declStra.append("2.AND whereas in terms of the above said agreement, the Purchaser is required to supply free issue");
        declStra.append("materials costing Rs.__________ [In words] as listed out in the agreement : Purchase Order NO.");
        declStra.append("______________dated___________ for the manufacture of the equipment at the Contractor's site, and that");
        declStra.append("the purchaser has agreed to authorize the Contractor to collect the free issue materials from the");
        declStra.append("Purchaser's site subject the Contractor furnishing a Bank guarantee for Rs. ____________ [In words");
        declStra.append("]in a manner herein specified towards the safeguard of free issue materials.\n\n");
        
        
        
        declStra.append("3. Now, we_____________(Name & Address of the Bank)in consideration of the Purchase having");
        declStra.append("agreed to authorize issue of free issue material for collection by the Contractor, hereby undertake to");
        declStra.append("indemnify the Purchaser and keep the Purchaser indemnified to the extent of the full value of the free");
        declStra.append("issue material till such time the materials are lying under the custody/possession/control of the");
        declStra.append("Contractor and till the equipment along with balance material, if any, are received by the Purchaser");
        declStra.append("after manufacture of the equipment.\n\n");
        
        
        declStra.append("4. WE,___________(Bank) do hereby undertake to pay to the Regional Director, Purchase & Stores,");
        declStra.append("Directorate of Purchase & Stores, Department of Atomic energy, the amount due and payable under");
        declStra.append("this Guarante without any demur, merely on a demand from the Regional Director, purchase &");
        declStra.append("Stores, Directorate of Purchase & Stores, Departmebnt of Atomic Energy, on behalf of the Governmaent");
        declStra.append("starting that the amount claimed is due by way of loss, destruction, deterioration or damage caused to");
        declStra.append("or suffered by the Governmaent to the purchaser's material thereby resulting in aloss to the");
        declStra.append("Government while they are lying under the Contractor's custody, possession or control or on account");
        declStra.append("of the Contractor's failure to fulfill any of the contractual obligations. Any such demand made on the");
        declStra.append("Bank shall be conclusive as regards the amount due and payable by the Bank under this Guarantee.");
        declStra.append("However, our liability under this Guarantee shall be restricted to an amount not exceeding Rs.");
        declStra.append("_____________ [In words].\n\n");
        
        
        declStra.append("5. We, _____________ (Bank) undertake to pay to the Government any money so demanded");
        declStra.append("not with standing any dispute or disputes raised by the Contractor(s) in any suit or proceeding pending");
        declStra.append("before any Court or Tribunal relating thereto our liability under this present guarantee being absolute");
        declStra.append("and unequivocal. The payment so made by us under this Bond shall be a valid discharge of our");
        declStra.append("liability for payment there under and the Contractor(s) shall have no claim against us for making such");
        declStra.append("payments.\n\n");
        
        
        declStra.append("6. WE, ___________ (Bank), also agree that the decision of the Regional Director, Purchase &");
        declStra.append("Stores, Directorate of Purchase & Stores, Department of Atomic Energy, Hyderabad, as to whether");
        declStra.append("the Contractor(s) has caused any loss/destruction or deterioration or damage to the Purchaser's");
        declStra.append("material while these are lying under his custody/possession/control from whatever cause arising as");
        declStra.append("also on the quantum of damage suffered by the Purchase shalll be final and binding on us.\n\n");
        
        
        
        declStra.append("7. We, _____________ (Bank) also agree with the Government that the Government shall have the");
        declStra.append("fullest liberty without our consent and without affecting in any manner our obligations here under to");
        declStra.append("vary any of the terms and conditions of the said Agreement or to extend time for performance by the");
        declStra.append("said Contractors from time or to time or to postpone for any time or from time to time any of the powers");
        declStra.append("exercisable by the Government against the said Contractors and to for bear or enforce any of the");
        declStra.append("terms and conditions relating to the said Agreement and we shall Agreement and we shall not be relieved from our liability by");
        declStra.append("reason of any such variation or extension being granted to the said Contractors or for any");
        declStra.append("for bearance, act or commission on the part of the said Government or any indulgence by the");
        declStra.append("Government to the said Contractors or by any such matter or things whatsoever which under the law");
        declStra.append("relating to sureties would but for this provision, have the effect of so reliving us.\n\n");
        
        
        declStra.append("8. THIS GUARANTEE will not be discharged due to change in the constitution of the Bank or the");
        declStra.append("Contractor(s)\n\n");
        
        
        
        declStra.append("9. OUR GUARANTEE shall remain in full force until ________________");
        declStra.append("__________ and unless a claim under the guarantee is lodged with us on or");
        declStra.append("before the above date, all rights of the Government under the guarantee shall be forfeited and we");
        declStra.append("shall be relieved and discharged from all liabilites there under.\n\n");
        
        
        declStra.append("Dated __________ this _____________ day of ____________20_______.");
        declStra.append("                                              For ______________________ \n\n");
        declStra.append("(PLEASE INDICATE THE NAME OF BANK WITH POSTAL ADDRESS, PHONE NUMBER., FAX");
        declStra.append("NUMBER & EMAIL ADDRESS WITHOUT FAIL)");
        
        Font SD88 = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk INDEM22 = new Chunk(declStra.toString(), SD88);                
        INDEM22.setBackground(BaseColor.WHITE);
        Paragraph inde22 = new Paragraph(INDEM22);
        inde22.setAlignment(Element.ALIGN_JUSTIFIED);
        inde22.setSpacingBefore(40f);
        inde22.setIndentationLeft(30f);
        inde22.setIndentationRight(30f);
        document.add(inde22);
        
        
        //************************* NEW PAGE     *******************************
        document.newPage();
        
        Font fD77 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk inu = new Chunk("Details Of Insurance Policy", fD77);                
        inu.setBackground(BaseColor.WHITE);
        Paragraph inus = new Paragraph(inu);
        inus.setAlignment(Element.ALIGN_CENTER);
        inus.setSpacingBefore(10f);
        inus.setIndentationLeft(30f);
        inus.setIndentationRight(30f);
        document.add(inus);
        
        PdfPTable table8 = new PdfPTable(2);
        table8.setWidthPercentage(90.0f);
        table8.setSpacingBefore(20f);
        table8.setHorizontalAlignment(Element.ALIGN_CENTER);
        table8.setWidths(new float[] {50.0f, 50.0f});
        
        PdfPCell cel8 = new PdfPCell();
        cel8.setBackgroundColor(BaseColor.WHITE);
        cel8.setBorder(1);        
        
        Font f77 = new Font(Font.FontFamily.COURIER, 8.0f, Font.NORMAL, BaseColor.BLACK);
        
        cel8.setPhrase(new Phrase("1)INSURANCE COVERAGE FOR FREE ISSUE MATERIAL :", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase("The Contractor should furnish insurance coverage for the Free Issue Materials in original indicating our complete purchase"
                + "    order reference, beneficiary as given below, FIM details as per Purchase Order. On receipt of insurance policy, purchase "
                + "    will issue Acceptance Letter. On the basis of the same, the contractor shall collect the Free Issue Material from our Stores, "
                + "    HWP[M], Manuguru., with prior intimation. Collection/Transportation of free Issue Material shall be arranged by the supplier "
                + "    at their cost.", f77));
        cel8.setPaddingLeft(5f);     
        cel8.setPaddingTop(8f);
        cel8.setPaddingBottom(8f);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("2) INSURED BY :" +
        "", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase("M/s ", f77));
        cel8.setPaddingLeft(5f);        
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("3) BENEFICIARY : ", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase(" The President of India acting through The Regional Director (P&S) / Asst.Purchase Officer, Department of Atomic Energy, "
                + "                Directorate of Purchase & Stores, Regional Purchase Unit [ Manuguru ], Heavy Water General Facilities Bldg,"
                + "     E.C.I.L. Road, E.C.I.L Post - Hyderabad - 62.", f77));
        cel8.setPaddingLeft(5f);        
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("4) RISK TO BE COVERED :", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase(" Any loss or damage to Purchaser's materials due to fire, Theft, Riot, Burglary, Strike, Civil Commotion, Terrorist Act, Natural" +
"     calamities etc., and any loss or damage arising out of any other causes such as other materials falling on purchaser's materials.", f77));
        cel8.setPaddingLeft(5f);        
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("5) VALUE OF FREE ISSUE MATERIALS TO BE ISSUED FOR:" +
        "", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase("Rs.", f77));
        cel8.setPaddingLeft(5f);        
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("6) VALIDITY :", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase("The policy should be valid for a period of 12 months from the date of Issue or for a period by which the full contracted eqipments"
                + "    /stores are delivered to the purchaser and properly accounted for by the contractor by return of surplus/scrap material out of "
                + "    the free issue material.", f77));
        cel8.setPaddingLeft(5f);        
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("7) DISCHARGE : ", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase("On satisfactory execution of the contract, and the free issue materials have been accounted for by the contractor to the"
                + "       satisfaction of the Purchaser, The Insurance policy will be discharged and returned to the contractor.", f77));
        cel8.setPaddingLeft(5f);   
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("8) CLAM : ", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase("If in the opinion of the purchaser,acting through _ The Regional Director (P&S) / Asst.Purchase Officer, Department of Atomic"
                + "      Energy, Directorate of Purchase & Stores, Regional Purchase Unit [Manuguru], Heavy Water General Facilites Bldg., E.CI.L Road,"
                + "      E.C.I.L. Post - Hyderabad - 62 or any other officer competent to enter in to enter in to contract, damages have occurred on the"
                + "      free issue material, the purchaset acting through the said officer will lodge a claim on the Insurance Company and the Insurance"
                + "      Company shall settle such claims without any demur and merely on demand in writing.", f77));
        cel8.setPaddingLeft(5f);        
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        cel8.setPhrase(new Phrase("9) IMPORTANT NOTE : ", f77));
        cel8.setPaddingLeft(5f);      
        cel8.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.1f);        
        table8.addCell(cel8);
        cel8.setPhrase(new Phrase(" a) Insurance policy should be submitted exactly as per the above format"
                + "       In all respects, covering all risks failing which the policy will no be accepted.\n"
                + "      b) The contractor shall furnish Insurance Policies(Standard Fir & special Perils and Burglary) in Original for acceptance. Xerox"
                + "       Copies/Scanned Copies of Insurance Policies will not be accepted under any circumstances.\n"
                + "      c) The insurance policies should contain all the details viz., Name of the Beneficiary< Complete Purchase Order Reference, Sufficient "
                + "      Validity Period, Value of free Issue Material, and Complete Description of free Issue Material with Quantity.", f77));
        cel8.setPaddingLeft(5f);     
        cel8.setBorderWidthLeft(0.1f);
        cel8.setBorderWidthBottom(0.2f);
        cel8.setBorderWidthRight(0.1f);
        table8.addCell(cel8);
        
        document.add(table8);
        
        
        
        //************************* NEW PAGE     *******************************
        document.newPage();
        Font SD7 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk INDEM = new Chunk("INDEMNITY BOND FORMAT\n(on Non-Judicial Stamp paper of Appropriate Value)", SD7);                
        INDEM.setBackground(BaseColor.WHITE);
        Paragraph inde = new Paragraph(INDEM);
        inde.setAlignment(Element.ALIGN_CENTER);
        inde.setSpacingBefore(10f);
        inde.setIndentationLeft(30f);
        inde.setIndentationRight(30f);
        document.add(inde);
        
        StringBuilder declStri = new StringBuilder();
        declStri.append("We, M/s. ____________________________________________________ indemnify the Purchaser and keep");
        declStri.append("the Purchaser indemnified to the extent of the value of free issue materials to be issued till");
        declStri.append("such time the entire contract is executed and proper account for the free issue materials is");    
        declStri.append("rendered and the left over/surplus and Scrap items are returned to the Purchaser. We shall not");
        declStri.append("utilize the Purchaser's free issue materials for any job other than the one contracted out in this");
        declStri.append("case and also not indulge in any act, commission or negligence which will cause/result in any");
        declStri.append("loss/damage to the Purchaser and in which case we, the Contractor shall be liable to the Purchaser");
        declStri.append("to pay compensation to the full extent of damage/loss and undertake to pay the same. We, the");
        declStri.append("Contractor, shall be responsible for the safety of the free issue materials after these are received");
        declStri.append("by us and all through the period during which the materials remain in our");
        declStri.append("possession/control/custody. The free issue materials on receipt at the Contractor's works shall be");
        declStri.append("inspected by us for ensuring safe and correct receipt of the material. We, the Contractor shall");
        declStri.append("report the discrepancies to the Purchase within 5 days from the date of receipt of the material.");
        declStri.append("We, the Contractor hall take all necessary precautions against any loss, dererioration,damage or");
        declStri.append("deatruction of the FIMs from whatever cause arising whilst the said materials remain in our");
        declStri.append("possession/custody or control. The free issue materials will be inspected periodically at regular");
        declStri.append("intervals by the Contractor for ensuring safe preservation and storage. We, the Contractor, shall");
        declStri.append("also not mix up the materials in question with any other goods and shall render true and proper");
        declStri.append("account of the materials actually used and return balance remaining unused material on hand and");
        declStri.append("scrap along with the final product and if it is not possible, within a period of one month from the");
        declStri.append("date of delivery of the final product covered by this purchse order. We, the Contractor, hereby");
        declStri.append("also indemnify the Purchaser to compensate the difference in cost between the actual cost of the");
        declStri.append("free issue material lost/damaged and the claim settled to the Purchaser by the insurance company.");
        declStri.append("The decision of The Regional Director (P&S) / Asst.Purchase officer, Department of Atomic Energy,");
        declStri.append("Direction of Purchase & Stores, Regional Purchase Unit [Manuguru], Heavy Water General Facilites Bldg,");
        declStri.append("E.C.I.L Road, E.C.I.L. Post - Hyderabad - 62., as to whether the Contractor has caused any loss,,");
        declStri.append("destruction, damage or deterioration of the free issue materials while in his possession, custody or");
        declStri.append("control from whatever cause arising and also on the quantum of damage suffered by the Government,");
        declStri.append("shall be final and binding upon the Contractor.");
        declStri.append("\n");
        declStri.append("\n");
        declStri.append("(Stamp & Signature of the Contractor)");        
        
        
        Font SD8 = new Font(Font.FontFamily.COURIER, 10.0f, Font.NORMAL, BaseColor.BLACK);
        Chunk INDEM2 = new Chunk(declStri.toString(), SD8);                
        INDEM2.setBackground(BaseColor.WHITE);
        Paragraph inde2 = new Paragraph(INDEM2);
        inde2.setAlignment(Element.ALIGN_JUSTIFIED);
        inde2.setSpacingBefore(40f);
        inde2.setIndentationLeft(30f);
        inde2.setIndentationRight(30f);
        document.add(inde2);
        
    }
}
