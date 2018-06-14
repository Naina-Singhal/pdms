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
public class TenderFormPDF extends AbstractPDFViewHelper {

    private Font getHeadingFont() {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_ROMAN);
        dataFont.setSize(11.0f);

        return dataFont;
    }

    private Font getDataFont() {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_ROMAN);
        dataFont.setSize(9.0f);

        return dataFont;
    }

    private Font getDataFontBold() {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_BOLD);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_BOLD);
        dataFont.setSize(9.0f);

        return dataFont;
    }

    private Font getDataFontSmall() {
        Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        dataFont.setColor(BaseColor.BLACK);
        dataFont.setStyle(FontFactory.TIMES_ROMAN);
        dataFont.setSize(7.0f);

        return dataFont;
    }

    private PdfPCell createImageCell()
            throws DocumentException, IOException {
        Image img = Image.getInstance(POFormPDF.class.getClassLoader().getResource("/embofindia_1.png"));
        img.setAlignment(Element.ALIGN_LEFT);
        PdfPCell tabCell = new PdfPCell(img);
        tabCell.setBorder(0);
        tabCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return tabCell;
    }

    private PdfPTable createLineSeperatorTable()
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
    
    private PdfPTable createFormNumberTable(final PublicTenderDTO tenderObj)
            throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        PdfPCell tabCell = new PdfPCell();
        tabCell.setBorder(1);
        tabCell.setBorderColor(new BaseColor(44, 67, 144));
        tabCell.setBorderWidth(1f);
        tabCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        
        double tenderCost=0.0;
        if(!tenderObj.getTenderCost().isEmpty())
        {
            tenderCost = Double.parseDouble(tenderObj.getTenderCost());
        }
        if(tenderCost > 500000)
        {
            tabCell.setPhrase(new Phrase("FORM No : DPS 44,44A", getHeadingFont()));
        }
        else
        {
            tabCell.setPhrase(new Phrase("FORM No : DPS 22,22A", getHeadingFont()));
        }
        table.addCell(tabCell);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(5);
        table.setSpacingAfter(5);
        return table;
    }

    private PdfPTable createHeaderTable() throws DocumentException, IOException {

        PdfPTable headerTable = new PdfPTable(3);
        headerTable.setWidthPercentage(100.0f);
        headerTable.setWidths(new float[]{50.0f, 20.0f, 30.0f});
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

        cell.setPhrase(new Phrase(header_1_text.toString(), getHeadingFont()));
        headerTable.addCell(cell);

        headerTable.addCell(createImageCell());

        cell.setPhrase(new Phrase(header_3_text.toString(), getHeadingFont()));
        headerTable.addCell(cell);

        return headerTable;
    }

    private PdfPTable createTenderDetailHeaderTable(PublicTenderDTO tenderObj)
            throws DocumentException, IOException {

        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100.0f);
        headerTable.setWidths(new float[]{50.0f, 50.0f});
        headerTable.setSpacingBefore(10);

        StringBuilder header_1_text = new StringBuilder();
        header_1_text.append("Tender No :\n");
        header_1_text.append(tenderObj.getFileNo());

        StringBuilder header_2_text = new StringBuilder();
        header_2_text.append("Dated : ");
        header_2_text.append(tenderObj.getOpeningDate());
        header_2_text.append("\n");
        header_2_text.append("Due Date : ");
        header_2_text.append(tenderObj.getDueDate());
        header_2_text.append("\n");
        header_2_text.append("Date Of Opening : ");
        header_2_text.append(tenderObj.getOpeningDate());
        header_2_text.append("\n");

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(0);
        cell.setPadding(5);

        cell.setPhrase(new Phrase(header_1_text.toString(), getHeadingFont()));
        headerTable.addCell(cell);

        cell.setPhrase(new Phrase(header_2_text.toString(), getHeadingFont()));
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerTable.addCell(cell);

        return headerTable;
    }

    private PdfPTable createTenderDetailBodyTable(PublicTenderDTO tenderObj)
            throws DocumentException, IOException {

        PdfPTable bodyTable = new PdfPTable(1);
        bodyTable.setWidthPercentage(100.0f);
        bodyTable.setWidths(new float[]{100.0f});
        bodyTable.setSpacingBefore(5);

        StringBuilder line_1_text = new StringBuilder();
        line_1_text.append("Dear Sirs ,\n");

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(0);
        cell.setPadding(5);
        cell.setPhrase(new Phrase(line_1_text.toString(), getDataFont()));
        bodyTable.addCell(cell);

        StringBuilder line_2_text = new StringBuilder();
        line_2_text.append("Sub: Our Tender No.");
        line_2_text.append(tenderObj.getFileNo());
        line_2_text.append("Dated :");
        line_2_text.append(tenderObj.getOpeningDate());

        PdfPCell line_2_cell = new PdfPCell();
        line_2_cell.setBackgroundColor(BaseColor.WHITE);
        line_2_cell.setBorder(0);
        line_2_cell.setPadding(5);
        line_2_cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        line_2_cell.setPhrase(new Phrase(line_2_text.toString(), getDataFont()));
        bodyTable.addCell(line_2_cell);

        StringBuilder para_1_text = new StringBuilder();

        para_1_text.append("Please refer to above tender enquiry for which your quotation is solicited on or before the ");
        para_1_text.append("Due Date and time mentioned above. Your offer shall be strictly sent by post/ courier in a ");
        para_1_text.append("sealed cover only duly supersribing the Tender No. and Due Date. Offers sent by email/ fax will ");
        para_1_text.append("not be accepted. \n");
        para_1_text.append("\n ");
        para_1_text.append("For Instructions to Tenders and other terms and conditions of contract shall be governed by our ");
        para_1_text.append("Standard forms which can be downloaded from our website www.dpsdae.gov.in//- form no. as ");
        para_1_text.append("mentioned above. ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("ITEM DETAILS AND DESCRIPTION: ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("DETAILED DESCRIPTION AS PER ENCLOSED ANNEXURE. ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("IMPORTANT NOTES TO TENDERERS: ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        
        para_1_text.append("1. Offers either shall be given on FREE AND SAFE DELIVERY AT HWP(Manuguru) site or indicate exact freight charges. \n ");
        para_1_text.append("In absence of which, freight charges @3% will be taken for comparision purpose. In the event of P.O. placed on you, freight charges at actuals on \n");
        para_1_text.append("production of documentary evidence will be admitted subject to maximum of 3% only. ");
        para_1_text.append("\n ");
        para_1_text.append("2. Please go through the special instructions to the tenderers before submission of quotation. ");
        para_1_text.append("\n ");
        para_1_text.append("3. Quotation shall be kept valid for a minimum period of 90 days from the date of openings of tender. ");
        para_1_text.append("\n ");
        para_1_text.append("4. Form C/D cannot be issued. Full tax applicable may be quoted in your offer.");
        para_1_text.append("\n ");
        para_1_text.append("5. Scanned/ Unsigned offers are not accpetable and such offers shall be rejected without any further");
        para_1_text.append("reference to tenderer. ");
        para_1_text.append("\n ");
        para_1_text.append("6. Payment shall be made as per our Normal terms i.e. full payment after receipt and final acceptance \n");
        para_1_text.append("at our stores no other terms is acceptable.");
        para_1_text.append("\n ");
        para_1_text.append("7. All the suppliers shall clearly indicate in their offer about delivery schedules for supply of materials \n");
        para_1_text.append("and strictly follow the same. In case, the successful contractor fails to deliver the ordered material within the \n ");
        para_1_text.append("scheduled delivery period, liquidated damages @2% shall be leived for each month or part there of for the \n ");
        para_1_text.append("delayed part of the supplies. ");
        
        double tenderCost=0.0;
        if(!tenderObj.getTenderCost().isEmpty())
        {
            tenderCost = Double.parseDouble(tenderObj.getTenderCost());
        }
        if(tenderCost > 500000)
        {
            para_1_text.append("8. Payment shall be made as per our Normal terms i.e. full payment after receipt and final acceptance at \n ");
            para_1_text.append("Place of Delivery.");
        }
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        para_1_text.append("\n ");
        
        PdfPCell para_1_cell = new PdfPCell();
        para_1_cell.setBackgroundColor(BaseColor.WHITE);
        para_1_cell.setBorder(0);
        para_1_cell.setPadding(5);
        para_1_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        para_1_cell.setPhrase(new Phrase(para_1_text.toString(), getDataFont()));
        bodyTable.addCell(para_1_cell);
        
        PdfPTable signatureTable = new PdfPTable(2);
        signatureTable.setWidthPercentage(100.0f);
        signatureTable.setWidths(new float[]{25.0f, 75.0f});
        signatureTable.setSpacingBefore(5);
        
        StringBuilder sign_line_1_text_1 = new StringBuilder();
        sign_line_1_text_1.append("Place Of Delivery");
        
        StringBuilder sign_line_1_text_2 = new StringBuilder();
        sign_line_1_text_2.append("Stores Officer,DAE,DPS,Heavey Water Plant(Manuguru),TELANGANA-507 116. \n\n");
        
        PdfPCell sign_line_1_cell_1 = new PdfPCell();
        sign_line_1_cell_1.setBackgroundColor(BaseColor.WHITE);
        sign_line_1_cell_1.setBorder(0);
        sign_line_1_cell_1.setPadding(5);
        sign_line_1_cell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
        sign_line_1_cell_1.setPhrase(new Phrase(sign_line_1_text_1.toString(), getDataFontSmall()));
        signatureTable.addCell(sign_line_1_cell_1);
        
        PdfPCell sign_line_1_cell_2 = new PdfPCell();
        sign_line_1_cell_2.setBackgroundColor(BaseColor.WHITE);
        sign_line_1_cell_2.setBorder(0);
        sign_line_1_cell_2.setPadding(5);
        sign_line_1_cell_2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        sign_line_1_cell_2.setPhrase(new Phrase(sign_line_1_text_2.toString(), getDataFontSmall()));
        signatureTable.addCell(sign_line_1_cell_2);
        
        PdfPCell sign_line_2_cell_1 = new PdfPCell();
        sign_line_2_cell_1.setBackgroundColor(BaseColor.WHITE);
        sign_line_2_cell_1.setBorder(0);
        sign_line_2_cell_1.setPadding(5);
        sign_line_2_cell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
        sign_line_2_cell_1.setPhrase(new Phrase(" ", getDataFontSmall()));
        signatureTable.addCell(sign_line_2_cell_1);
        
        StringBuilder sign_line_2_text_2 = new StringBuilder();
        if(tenderCost > 500000)
        {
            sign_line_2_text_2.append("(LAKSHMI SUNDER) \n");
            sign_line_2_text_2.append("Manager (Purchase).");
        }
        else
        {
            sign_line_2_text_2.append("(L.DHARMA RAO) \n");
            sign_line_2_text_2.append("Ass. Purchase Officer.");
        }
        
        PdfPCell sign_line_2_cell_2 = new PdfPCell();
        sign_line_2_cell_2.setBackgroundColor(BaseColor.WHITE);
        sign_line_2_cell_2.setBorder(0);
        sign_line_2_cell_2.setPadding(5);
        sign_line_2_cell_2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        sign_line_2_cell_2.setPhrase(new Phrase(sign_line_2_text_2.toString(), getDataFontSmall()));
        signatureTable.addCell(sign_line_2_cell_2);
        
        bodyTable.addCell(signatureTable);
        
        return bodyTable;
    }

    private void createAnexurePageData(PublicTenderDTO tenderObj,
            Document doc) throws DocumentException {

        StringBuilder header_1 = new StringBuilder();
        header_1.append("ANNEXURE-1");
        Paragraph anx_1_head_1 = new Paragraph(new Phrase(header_1.toString(), getHeadingFont()));
        anx_1_head_1.setAlignment(Element.ALIGN_CENTER);

        doc.add(anx_1_head_1);

        doc.add(createLineSeperatorTable());

        PdfPTable header_table = new PdfPTable(2);
        header_table.setWidthPercentage(100.0f);
        header_table.setWidths(new float[]{85.0f, 15.0f});
        header_table.setHorizontalAlignment(Element.ALIGN_LEFT);
        header_table.setSpacingBefore(10);

        PdfPCell header_table_cell = new PdfPCell();
        header_table_cell.setPhrase(new Phrase("Tender No -" + tenderObj.getFileNo()));
        header_table.addCell(header_table_cell);
        header_table_cell.setPhrase(new Phrase("Date -" + tenderObj.getOpeningDate()));
        header_table.addCell(header_table_cell);

        doc.add(header_table);

        StringBuilder anx_para_1 = new StringBuilder();
        anx_para_1.append("\n ");
        anx_para_1.append("\n ");
        anx_para_1.append("\n ");

        PdfPTable tender_info_table = new PdfPTable(2);
        tender_info_table.setWidthPercentage(100.0f);
        tender_info_table.setWidths(new float[]{100.0f});
        tender_info_table.setHorizontalAlignment(Element.ALIGN_LEFT);
        tender_info_table.setSpacingBefore(10);

        PdfPCell tender_info_table_cell = new PdfPCell();
        tender_info_table_cell.setPhrase(new Phrase(anx_para_1.toString()));
        tender_info_table.addCell(tender_info_table_cell);

        doc.add(tender_info_table);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{5.0f, 65.0f, 15.0f, 15.0f});
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(20);

        PdfPCell tabCell = new PdfPCell();
        tabCell.setPhrase(new Phrase("SNo", getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("NAME OF THE ITEM AS PER INDENT", getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("COMP. CODE", getDataFont()));
        table.addCell(tabCell);
        tabCell.setPhrase(new Phrase("REQD QTY.(Nos)", getDataFont()));
        table.addCell(tabCell);

        if (!tenderObj.getTenderItemsLi().isEmpty()) {
            int loopCount = 1;
            for (PublicTenderItemsDTO tenderItemObj : tenderObj.getTenderItemsLi()) {
                PdfPCell i_tabCell = new PdfPCell();
                i_tabCell.setPhrase(new Phrase(loopCount + "", getDataFont()));
                table.addCell(i_tabCell);

                i_tabCell.setPhrase(new Phrase(tenderItemObj.getItemObj().getItemName() + "\n"
                        + tenderItemObj.getBreifDesc(), getDataFont()));
                table.addCell(i_tabCell);

                i_tabCell.setPhrase(new Phrase(tenderItemObj.getItemCode(), getDataFont()));
                table.addCell(i_tabCell);

                i_tabCell.setPhrase(new Phrase(tenderItemObj.getItemQty() + "", getDataFont()));
                table.addCell(i_tabCell);

                loopCount++;
            }
        }

        doc.add(table);

    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        PublicTenderDTO tenderObj = (PublicTenderDTO) model.get("tenderObj");

        doc.add(createFormNumberTable(tenderObj));
        
        doc.add(createHeaderTable());

        doc.add(createLineSeperatorTable());

        doc.add(createTenderDetailHeaderTable(tenderObj));

        doc.add(createTenderDetailBodyTable(tenderObj));
        
        doc.newPage();
        
    }

}
