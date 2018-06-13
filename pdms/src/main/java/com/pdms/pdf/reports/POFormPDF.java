/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.dto.PublicTenderDTO;
import com.pdms.dto.PublicTenderItemsDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class POFormPDF extends AbstractPDFViewHelper {
    
    
    private static Font getDataFont()
    {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_ROMAN);
        dataFont.setSize(9.0f);
        
        return dataFont;
    }
    
    private static Font getDataFontBold()
    {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_BOLD);
        dataFont.setSize(9.0f);
        
        return dataFont;
    }
    
    private static Font getDataFontSmall()
    {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_ROMAN);
        dataFont.setSize(7.0f);
        
        return dataFont;
    }
    
    private static PdfPCell createImageCell() 
                throws DocumentException, IOException {
        Image img = Image.getInstance(POFormPDF.class.getClassLoader().getResource("/embofindia_1.png"));
        img.setAlignment(Element.ALIGN_LEFT);
        PdfPCell tabCell = new PdfPCell(img);
        tabCell.setBorder(0);
        tabCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tabCell;
    }
    
    private static PdfPTable createLineSeperatorTable() 
                throws DocumentException {
        PdfPTable table = new PdfPTable(1);       
        PdfPCell tabCell = new PdfPCell();
        tabCell.setBorder(1);
        tabCell.setBorderColor(new BaseColor(44, 67, 144));
        tabCell.setBorderWidth(1f);
        tabCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(tabCell);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        return table;
    }
    
    private static PdfPTable createPurchaseOrderNumberTable(PublicTenderDTO tenderObj) 
                throws DocumentException {
        PdfPTable table = new PdfPTable(2);       
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {70.0f, 30.0f});
        PdfPCell tabCell = new PdfPCell();
        tabCell.setPhrase(new Phrase("Purchase Order No :"+tenderObj.getPtVendorObj().getPoNumber(), 
                    getDataFont()));
        table.addCell(tabCell);
        
        tabCell.setPhrase(new Phrase("Dt :"+tenderObj.getPtVendorObj().getPoGenDate(), 
                    getDataFont()));
        table.addCell(tabCell);
        
        
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        return table;
    }
    
    private static PdfPTable createVendorDetailsTable(PublicTenderDTO tenderObj) 
                throws DocumentException {
        PdfPTable table = new PdfPTable(2);       
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {50.0f, 50.0f});
        
        StringBuilder vDtls = new StringBuilder();
        vDtls.append("M/s.");
        vDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorName());
        vDtls.append("\n");
        vDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorAddress());
        vDtls.append("\n");
        vDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorCity());
        vDtls.append("\n");
        vDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorPin());
        
        StringBuilder vAddDtls = new StringBuilder();
        vAddDtls.append("Vendor Code : ");
        vAddDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorCode());
        vAddDtls.append(", Regn : ");
        vAddDtls.append(tenderObj.getPtVendorObj().getVendorObj().getRegistrationType());
        vAddDtls.append(",");
        vAddDtls.append("\n Exp.date: ");
        vAddDtls.append(tenderObj.getPtVendorObj().getVendorObj().getExpiraryDate());
        vAddDtls.append(", Offer Details : ");
        vAddDtls.append(tenderObj.getPtVendorObj().getPoOffer());
        vAddDtls.append("\n Phone : ");
        vAddDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorPhone());
        vAddDtls.append(", Fax : ");
        vAddDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorFax());
        vAddDtls.append("\n Email : ");
        vAddDtls.append(tenderObj.getPtVendorObj().getVendorObj().getVendorEmail());
        
        
        PdfPCell tabCell = new PdfPCell();
        tabCell.setPhrase(new Phrase(vDtls.toString(), getDataFont()));
        table.addCell(tabCell);
        
        tabCell.setPhrase(new Phrase(vAddDtls.toString(), getDataFont()));
        table.addCell(tabCell);
        
        
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        return table;
    }
    
    private static void createItemCategoryTable(PublicTenderDTO tenderObj,
            Document doc) 
                throws DocumentException {
        PdfPTable table = new PdfPTable(6);       
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {5.0f,30.0f,15.0f,15.0f,15.0f,15.0f});
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(30);
        
        PdfPCell tabCell = new PdfPCell();
        tabCell.setPhrase(new Phrase("SNo",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Description",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Quantity",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Unit",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Rates",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Amount",getDataFont()));
        table.addCell(tabCell);
        
        PdfPTable table_1 = new PdfPTable(1);       
        table_1.setWidthPercentage(100.0f);
        table_1.setWidths(new float[] {100.0f});
        
        PdfPCell tabCell_1 = new PdfPCell();
        tabCell_1.setPhrase(new Phrase(tenderObj.getIndentObj().getCategoryObj().getCategoryName(),
                getDataFontBold()));
        table_1.addCell(tabCell_1);
        
        tabCell_1.setPhrase(new Phrase("DETAILED DESCRIPTION AND TERMS AND CONDITIONS AS PER ANNEXURE.",
                getDataFontBold()));
        table_1.addCell(tabCell_1);
        
        StringBuilder inner_tbl_txt = new StringBuilder();
        inner_tbl_txt.append("Payment : 100% payment will be made within 30 days from the date of receipt of ");
        inner_tbl_txt.append(" materials at our store, Heavy Water Plant, Manuguru and its final acceptance.\n");
        inner_tbl_txt.append("Bills in duplicate shall be submitted to Accounts Officer, RPUM, Hyderabad.\n");
        inner_tbl_txt.append("NOTE: Please quote your Bank details on your bills to enable us to make payment through RTGS/ NEFT. ");
        
        tabCell_1.setPhrase(new Phrase(inner_tbl_txt.toString(),
                getDataFontBold()));
        table_1.addCell(tabCell_1);
        
        PdfPTable table_2 = new PdfPTable(2);       
        table_2.setWidthPercentage(100.0f);
        table_2.setWidths(new float[] {50.0f,50.0f});
        table_2.setSpacingAfter(30);
        
        PdfPCell tabCell_2 = new PdfPCell();
        tabCell_2.setPhrase(new Phrase("PRICE : F.O.R SECUNDERABAD.",
                getDataFontBold()));
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("DELIVERY PERIOD ON OR BEFORE : ",
                getDataFontBold()));
        table_2.addCell(tabCell_2);
        
        doc.add(table);
        
        doc.add(table_1);
        
        doc.add(table_2);
    }
    
    public static Paragraph page_1_paragraph_1(PublicTenderDTO tenderObj)
    {
        StringBuilder para = new StringBuilder();
        
        para.append("Yours offer contained in the Tender No.");
        para.append(tenderObj.getFileNo());
        para.append("   dated _______________ to supply under mentioned stores is accepted ");
        para.append(" on behalf of The President of India, subject to the terms and conditions of ");
        para.append(" contract in the form no. DPS-P22A  and subject to the terms and consitions ");
        para.append(" contained in the Purchase order and to the extent of quantity and at the price specified. ");
        para.append("");
        
        Paragraph page_1_p_1 = new Paragraph(new Phrase(para.toString(),getDataFont()));
        page_1_p_1.setAlignment(Element.ALIGN_JUSTIFIED);
        
        return page_1_p_1;
    }
    
    public static Paragraph page_1_paragraph_2()
    {
        StringBuilder para = new StringBuilder();
        
        para.append("Please note that the terms and conditions printed on the tender form no DPS-P22A referred ");
        para.append(" to above and this Purchase Order shall be sole repository of the contract ");
        
        Paragraph page_1_p_2 = new Paragraph(new Phrase(para.toString(),getDataFont()));
        page_1_p_2.setAlignment(Element.ALIGN_JUSTIFIED);
        
        return page_1_p_2;
    }
    
    private static PdfPTable createSignTable() 
                throws DocumentException {
        
        PdfPTable table_2 = new PdfPTable(2);       
        table_2.setWidthPercentage(100.0f);
        table_2.setWidths(new float[] {50.0f,50.0f});
        table_2.setSpacingBefore(30);
        table_2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        
        PdfPCell tabCell_2 = new PdfPCell();
        tabCell_2.setPhrase(new Phrase(" ",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("Yours faithfully,",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        
        
        tabCell_2.setPhrase(new Phrase(" ",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("\n",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        
        
        tabCell_2.setPhrase(new Phrase(" ",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("\n",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        
        
        tabCell_2.setPhrase(new Phrase(" ",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("(L DHARMA RAO),",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        
        
        tabCell_2.setPhrase(new Phrase(" ",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("Asst. Purchase Officer",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        
        tabCell_2.setPhrase(new Phrase(" ",getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase("For and on behalf of The President of India (The Ourchaser)",getDataFontSmall()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_2.setBorder(0);
        table_2.addCell(tabCell_2);
        
        
        return table_2;
    }
    
    
    private static void createCCTable(PublicTenderDTO tenderObj,Document doc) 
                throws DocumentException {
        
        PdfPTable table_2 = new PdfPTable(2);       
        table_2.setWidthPercentage(100.0f);
        table_2.setWidths(new float[] {50.0f,50.0f});
        table_2.setSpacingBefore(60);
        table_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        table_2.getDefaultCell().setBorder(PdfPCell.LEFT | PdfPCell.RIGHT);
        StringBuilder ccSb_1 = new StringBuilder();
        StringBuilder ccSb_2 = new StringBuilder();
        StringBuilder ccSb_3 = new StringBuilder();
        
        ccSb_1.append("CC To: \n");
        ccSb_1.append("Shri. P. Satish, PROCESS, INST \n");
        ccSb_1.append("Ind. No:");
        ccSb_1.append(tenderObj.getIndentObj().getIndentNumber());
        ccSb_1.append(", Dt:");
        ccSb_1.append(tenderObj.getIndentObj().getIndentDate());
        ccSb_1.append("\n");
        ccSb_1.append("Group: ");
        ccSb_1.append(tenderObj.getIndentObj().getMappedGroupName());
        ccSb_1.append("\n");
        ccSb_1.append("\n");
        ccSb_1.append("MPK: ");
        
        ccSb_2.append("Certified in Pre-Audit. Expenditure is Debitable to");
        ccSb_2.append(tenderObj.getIndentObj().getHoaObj().getAccountType());
        ccSb_2.append("\n");
        ccSb_2.append("\n");
        ccSb_2.append("\n");
        ccSb_2.append("\n");
        ccSb_2.append("AA\t \t  Sr. AO/AO, RPUM.");
        
        ccSb_3.append(" ACCOUNTS/ \t\t INDENTOR/ \t\t GROUP:");
        ccSb_3.append(tenderObj.getIndentObj().getMappedGroupName());
        ccSb_3.append("/ \t\t STORES/ \t\t OFFICE COPY");
        
        PdfPCell tabCell_2 = new PdfPCell();
        tabCell_2.setPhrase(new Phrase(ccSb_1.toString(),getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabCell_2.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP);
        table_2.addCell(tabCell_2);
        tabCell_2.setPhrase(new Phrase(ccSb_2.toString(),getDataFont()));
        tabCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
        tabCell_2.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP);
        table_2.addCell(tabCell_2);
        
        PdfPTable table_3 = new PdfPTable(1);       
        table_3.setWidthPercentage(100.0f);
        table_3.setWidths(new float[] {100.0f});
        table_3.setSpacingBefore(0);
        table_3.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell tabCell_3 = new PdfPCell();
        tabCell_3.setPhrase(new Phrase(ccSb_3.toString(),getDataFont()));
        tabCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabCell_3.setBorder(PdfPCell.LEFT | PdfPCell.RIGHT | PdfPCell.TOP | PdfPCell.BOTTOM);
        table_3.addCell(tabCell_3);
        
        doc.add(table_2);
        doc.add(table_3);
    }
    
    
    private static void createAnexureItemsTable(PublicTenderDTO tenderObj,
            Document doc) 
                throws DocumentException {
        
        PdfPTable table = new PdfPTable(6);       
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {5.0f,30.0f,15.0f,15.0f,15.0f,15.0f});
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(20);
        
        PdfPCell tabCell = new PdfPCell();
        tabCell.setPhrase(new Phrase("SNo",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Description",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Quantity",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Unit",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Rates",getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("Amount",getDataFont()));
        table.addCell(tabCell);
        
        if(!tenderObj.getTenderItemsLi().isEmpty())
        {
            int loopCount=1;
            for(PublicTenderItemsDTO tenderItemObj : tenderObj.getTenderItemsLi())
            {
                PdfPCell i_tabCell = new PdfPCell();
                i_tabCell.setPhrase(new Phrase(loopCount+"",getDataFont()));
                table.addCell(i_tabCell);
                
                i_tabCell.setPhrase(new Phrase(tenderItemObj.getItemObj().getItemName()+"\n"+
                        tenderItemObj.getBreifDesc(),getDataFont()));
                table.addCell(i_tabCell);
                
                i_tabCell.setPhrase(new Phrase(tenderItemObj.getItemQty()+"",getDataFont()));
                table.addCell(i_tabCell);
                
                i_tabCell.setPhrase(new Phrase(tenderItemObj.getItemObj().getUnitDTO().getUnitName()+"",getDataFont()));
                table.addCell(i_tabCell);
                
                i_tabCell.setPhrase(new Phrase("__________",getDataFont()));
                table.addCell(i_tabCell);
                
                i_tabCell.setPhrase(new Phrase("__________",getDataFont()));
                table.addCell(i_tabCell);
                
                loopCount++;
            }
        }
        
        PdfPTable table_1 = new PdfPTable(2);       
        table_1.setWidthPercentage(100.0f);
        table_1.setWidths(new float[] {85.0f,15.0f});
        table_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table_1.setSpacingBefore(0);
        
        PdfPCell tabCell_1 = new PdfPCell();
        tabCell_1.setPhrase(new Phrase("Total",getDataFont()));
        tabCell_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table_1.addCell(tabCell_1);
        
        tabCell_1.setPhrase(new Phrase("___________",getDataFont()));
        tabCell_1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table_1.addCell(tabCell_1);
        
        doc.add(table);
        doc.add(table_1);
    }
    
    
    public static void annexure_page_paragraphs(Document doc) throws DocumentException
    {
        StringBuilder para_1 = new StringBuilder();
        StringBuilder para_2 = new StringBuilder();
        StringBuilder para_3 = new StringBuilder();
        
        para_1.append(" VAT will be paid extra @ 5% within the stipulated delivery period. (Our TIN : 36070277612). ");
        
        Paragraph page_1_p_1 = new Paragraph(new Phrase(para_1.toString(),getDataFont()));
        page_1_p_1.setAlignment(Element.ALIGN_JUSTIFIED);
        
        para_2.append("/// FREIGHT CHARGES & TRANSPORTER /// \n");
        para_2.append("Freight charges extra at actuals, subject to maximum of 3% of the basic cost of material, ");
        para_2.append(" will be re-imbursed on production of documentary evidence. Material shall be transported ");
        para_2.append(" through any bank approved transport carriers on door delivery basis, in case, transport ");
        para_2.append(" carrier's branch offices are not located at Manuguru/Aswapuram/Kothagudem/Badrachalam, the ");
        para_2.append(" material shall be booked on FREGIHT PRE-PAID BASIS only & the same shall be claimed in the ");
        para_2.append(" bills for re-imbursement. ");
        
        Paragraph page_1_p_2 = new Paragraph(new Phrase(para_2.toString(),getDataFont()));
        page_1_p_2.setAlignment(Element.ALIGN_JUSTIFIED);
        
        para_3.append("A certificate should be furnished along with the bill regarding the claim of S.T/ T.O.T/ ");
        para_3.append(" E.D. as given in enclosure. \n");
        
        Paragraph page_1_p_3 = new Paragraph(new Phrase(para_3.toString(),getDataFont()));
        page_1_p_3.setAlignment(Element.ALIGN_JUSTIFIED);
        
        doc.add(page_1_p_1);
        doc.add(page_1_p_2);
        doc.add(page_1_p_3);
        
    }
    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        PublicTenderDTO tenderObj = (PublicTenderDTO)model.get("tenderObj");
        
        Font headerFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        headerFont.setColor(BaseColor.BLACK);
        headerFont.setStyle(FontFactory.TIMES_BOLD);
        headerFont.setSize(11.0f);
        
        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100.0f);
        headerTable.setWidths(new float[] {40.0f,30.0f, 30.0f});
        headerTable.setSpacingBefore(10);
        
        StringBuilder header_1_text = new StringBuilder();
        header_1_text.append("GOVERNMENT OF INDIA\n");
        header_1_text.append("DEPARTMENT OF ATOMIC ENERGY\n");
        header_1_text.append("DIRECTORATE OF PURCHASE AND STORES\n");
        header_1_text.append("REGIONAL PURCHASE UNIT (MANUGURU)\n");
        
        StringBuilder header_3_text = new StringBuilder();
        header_3_text.append("ECIL ROAD, ECIL POST,\n");
        header_3_text.append("HYDERABAD - 500 062.\n");
        header_3_text.append("Phones : 040-27181210/11\n");
        header_3_text.append("Fax: 040-27143516\n");
        header_3_text.append("e-mail: apo.rpum-dae@gov.in\n");
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(0);
        cell.setPadding(5);
        
        cell.setPhrase(new Phrase(header_1_text.toString(), headerFont));
        headerTable.addCell(cell);
        
        headerTable.addCell(createImageCell());
        
        cell.setPhrase(new Phrase(header_3_text.toString(), headerFont));
        headerTable.addCell(cell);
        
        
        doc.add(headerTable);
        
        Paragraph ph = new Paragraph("PURCHASE ORDER",headerFont);
        ph.setAlignment(Element.ALIGN_CENTER);
        
        doc.add(ph);
        
        doc.add(createLineSeperatorTable());
        
        doc.add(createPurchaseOrderNumberTable(tenderObj));
        
        doc.add(createVendorDetailsTable(tenderObj));
     
        doc.add(page_1_paragraph_1(tenderObj));
        
        createItemCategoryTable(tenderObj,doc);
        
        doc.add(page_1_paragraph_2());
        
        doc.add(createSignTable());
        
        createCCTable(tenderObj,doc);
        
        doc.newPage();
        
        Paragraph anxpara = new Paragraph("ANNEXURE TO PURCHASE ORDER : "
                +tenderObj.getPtVendorObj().getPoNumber(),
                headerFont);
        anxpara.setAlignment(Element.ALIGN_CENTER);
        
        doc.add(anxpara);
        
        createAnexureItemsTable(tenderObj,doc);
        
        annexure_page_paragraphs(doc);
        
        doc.add(createSignTable());
    }
    
}
