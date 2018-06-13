/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdms.pdf.reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.pdms.constants.ApplicationConstants;
import com.pdms.dto.IndentFormDTO;
import com.pdms.dto.IndentLogDTO;
import com.pdms.dto.ItemDTO;
import com.pdms.helpers.AbstractPDFViewHelper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

/**
 *
 * @author hpasupuleti
 */
@Component
public class IndentFormPDF extends AbstractPDFViewHelper {

//    @Autowired
//    private DateUtil dateUtil;
//    
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document doc,
            PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        
        IndentFormDTO indentObj = (IndentFormDTO)model.get("indentObj");
        
        Paragraph headerPara_1 = new Paragraph("DEPARTMENT OF ATOMIC ENERGY");
        headerPara_1.setAlignment(Element.ALIGN_CENTER);
        headerPara_1.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        
        Paragraph headerPara_2 = new Paragraph("REGIONAL PURCHASE UNIT (MANUGURU)");
        headerPara_2.setAlignment(Element.ALIGN_CENTER);
        headerPara_2.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        
        Paragraph headerPara_3 = new Paragraph("INDENT FORM");
        headerPara_3.setAlignment(Element.ALIGN_CENTER);
        headerPara_3.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
        
        doc.add(headerPara_1);
        doc.add(headerPara_2);
        doc.add(headerPara_3);
        
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100.0f);
        headerTable.setWidths(new float[] {50.0f, 50.0f});
        headerTable.setSpacingBefore(10);
        
        
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(BaseColor.BLACK);
        font.setSize(10.0f);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.WHITE);
        cell.setBorder(0);
        cell.setPadding(5);
        
        cell.setPhrase(new Phrase("To", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Indent No:"+indentObj.getIndentNumber(), font));
        headerTable.addCell(cell);
        
        
        cell.setPhrase(new Phrase("The Director", font));
        headerTable.addCell(cell);
        
        //cell.setPhrase(new Phrase("Date:"+dateUtil.dateConvertionFromDBToJSP(indentObj.getIndentDate()), font));
        cell.setPhrase(new Phrase("Date:"+indentObj.getIndentDate(), font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Directorate of Purchase & Stores, Central Purchase Unit Mumbai/ Regional Purchase Unit, Hyderabad.", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Section:"+indentObj.getSectionObj().getSectionName(), font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(" ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Desired Delivery Schedule:"+indentObj.getDescriptionDetails(), font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Name & Designation/ grade of The officer indenting the stores ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("["+indentObj.getEmpProfileDTo().getFirstName()+" "+indentObj.getEmpProfileDTo().getLastName()+"] ", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase("Place of Delivery:", font));
        headerTable.addCell(cell);
        
        cell.setPhrase(new Phrase(indentObj.getPodObj().getPlaceOfDelivery(), font));
        headerTable.addCell(cell);
        
        doc.add(headerTable);
        
        Paragraph headerPara_4 = new Paragraph("DECLARATION BY THE APPROVING AUTHORITY");
        headerPara_4.setAlignment(Element.ALIGN_CENTER);
        headerPara_4.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.UNDERLINE));
        
        doc.add(headerPara_4);
        
        doc.add(new Paragraph("  "));
        
        StringBuilder declStr = new StringBuilder();
        declStr.append("I hearby certify that I have been delegated powers to approve indent for the ");
        declStr.append("purchase of stores/ services to be arranged to the extent of the estimated cost");
        declStr.append(" of the item indicated in the indent vide Delegation Order No ...................");
        declStr.append(" dated ......................... issued by CE, HWB, MUMBAI.");
       
        Paragraph declartionPara = new Paragraph(declStr.toString());
        declartionPara.setAlignment(Element.ALIGN_JUSTIFIED);
        declartionPara.setFont(FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLDITALIC));
       
        doc.add(declartionPara);
        
        PdfPTable signTable = new PdfPTable(2);
        signTable.setWidthPercentage(100.0f);
        signTable.setWidths(new float[] {50.0f, 50.0f});
        signTable.setSpacingBefore(10);

        PdfPCell signCell = new PdfPCell();
        signCell.setBackgroundColor(BaseColor.WHITE);
        signCell.setBorder(0);
        signCell.setPadding(5);
        
        signCell.setPhrase(new Phrase("...............................", font));
        signTable.addCell(signCell);
        
        signCell.setPhrase(new Phrase("Signature:",font));
        signTable.addCell(signCell);
        
        signCell.setPhrase(new Phrase("Manager (Purchase)", font));
        signTable.addCell(signCell);
        
        
        
        String apprUserName="";
        if(!indentObj.getIndentLogLi().isEmpty())
        {
            for(IndentLogDTO logObj : indentObj.getIndentLogLi())
            {
                if(logObj.getActionPerformed().equalsIgnoreCase
                            (ApplicationConstants.INDENT_ACTION_PERFORMED_APPROVED))
                {
                    apprUserName = logObj.getUsername();
                }
            }
        }
        signCell.setPhrase(new Phrase("Name & Designation: "+apprUserName ,font));
        signTable.addCell(signCell);
        
        signCell.setPhrase(new Phrase(" ", font));
        signTable.addCell(signCell);
        
        signCell.setPhrase(new Phrase("Grade Of Approving Authority: GENERAL MANAGER",font));
        signTable.addCell(signCell);
        
        doc.add(signTable);
        
        
        PdfPTable itemsTable = new PdfPTable(3);
        itemsTable.setWidthPercentage(100.0f);
        itemsTable.setWidths(new float[] {10.0f,50.0f,40.0f});//,20.0f});
        itemsTable.setSpacingBefore(10);
        
        PdfPCell itemCell = new PdfPCell();
        itemCell.setBackgroundColor(BaseColor.WHITE);
        itemCell.setBorder(1);
        itemCell.setPadding(5);
        
        itemCell.setPhrase(new Phrase("S.No.", font));
        itemsTable.addCell(itemCell);
        
        itemCell.setPhrase(new Phrase("Description of Items with detailed specifications, nature of services to be rendered (repairs, Servicing etc):",font));
        itemsTable.addCell(itemCell);
        
        itemCell.setPhrase(new Phrase("Quantity", font));
        itemsTable.addCell(itemCell);
        
//        itemCell.setPhrase(new Phrase("Estimated Cost",font));
//        itemsTable.addCell(itemCell);
//        
        int loopCount=0;
        Font tabFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        tabFont.setColor(BaseColor.BLACK);
        tabFont.setSize(9.0f);
        for(ItemDTO  itemObj :indentObj.getItemDTOLi())
        {
            loopCount++;
            
            itemCell.setPhrase(new Phrase(loopCount+"", tabFont));
            itemsTable.addCell(itemCell);
            
            itemCell.setPhrase(new Phrase(itemObj.getItemName(), tabFont));
            itemsTable.addCell(itemCell);
            
            itemCell.setPhrase(new Phrase(itemObj.getRequiredStock()+"", tabFont));
            itemsTable.addCell(itemCell);
            
        }
        
//        itemCell.setPhrase(new Phrase(indentObj.getEstimatedCost()+"", tabFont));
//        itemCell.setRowspan(loopCount);
//        itemsTable.addCell(itemCell);
//        
        
        doc.add(itemsTable);
        
        
        PdfPTable estCostITable = new PdfPTable(1);
        estCostITable.setWidthPercentage(100.0f);
        estCostITable.setWidths(new float[] {100.0f});//,20.0f});
        estCostITable.setSpacingBefore(10);
        
        PdfPCell estCostICell = new PdfPCell();
        estCostICell.setBackgroundColor(BaseColor.WHITE);
        estCostICell.setBorder(1);
        estCostICell.setPadding(5);
        
        estCostICell.setPhrase(new Phrase("Total Estimated Cost:"+indentObj.getEstimatedCost(),font));
        estCostICell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        estCostITable.addCell(estCostICell);
        
        doc.add(estCostITable);
        
        PdfPTable clasifTable = new PdfPTable(2);
        clasifTable.setWidthPercentage(100.0f);
        clasifTable.setWidths(new float[] {50.0f,50.0f});
        clasifTable.setSpacingBefore(10);
        
        Font clasiffont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        clasiffont.setColor(BaseColor.BLACK);
        clasiffont.setSize(8.0f);
        
        PdfPCell calsifCell = new PdfPCell();
        calsifCell.setBackgroundColor(BaseColor.WHITE);
        calsifCell.setBorder(0);
        calsifCell.setPadding(5);
        
        calsifCell.setPhrase(new Phrase("Classification of Item:", clasiffont));
        clasifTable.addCell(calsifCell);
        
        calsifCell.setPhrase(new Phrase("*Capital/ Non-Consumable/ consumable",clasiffont));
        clasifTable.addCell(calsifCell);
        
        doc.add(clasifTable);
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        doc.add(new Paragraph("Previous purchase reference if known:",clasiffont));
        
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        doc.add(new Paragraph("Technical reasons/ specification if indent is for proprietary items:",clasiffont));
        
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        PdfPTable estCostTable = new PdfPTable(2);
        estCostTable.setWidthPercentage(100.0f);
        estCostTable.setWidths(new float[]{50.0f, 50.0f});
        estCostTable.setSpacingBefore(5);
        
        PdfPCell estCostCell = new PdfPCell();
        estCostCell.setBackgroundColor(BaseColor.WHITE);
        estCostCell.setBorder(0);
        estCostCell.setPadding(5);
        
        estCostCell.setPhrase(new Phrase("If the estimated cost of proprietary item exceed Rs. 1.00 Crore, details|", clasiffont));
        estCostTable.addCell(estCostCell);
        
        estCostCell.setPhrase(new Phrase("Sanction No.____________________________",clasiffont));
        estCostTable.addCell(estCostCell);
        
        estCostCell.setPhrase(new Phrase("Regarding the specific approval of member for indenting and also the |", clasiffont));
        estCostTable.addCell(estCostCell);
        
        estCostCell.setPhrase(new Phrase("Dated.____________________________",clasiffont));
        estCostTable.addCell(estCostCell);
        
        estCostCell.setPhrase(new Phrase("Authority who conveyed the MF's approval |", clasiffont));
        estCostTable.addCell(estCostCell);
        
        estCostCell.setPhrase(new Phrase(" ",clasiffont));
        estCostTable.addCell(estCostCell);
        
        doc.add(estCostTable);
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        doc.add(new Paragraph("Financial Sanction No. & Date :  ? ",clasiffont));
        
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        doc.add(new Paragraph("Whether budget provision including provision of foreign excahnge during the current financial year exists and if so:",clasiffont));
        
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        doc.add(new Paragraph("The Major and Monor Head Of Account:"+indentObj.getHoaObj().getAccountType()+"     OFFICE EXPENSES",clasiffont));
        
        doc.add(new Paragraph("------------------------------------------------------------------------------------------"));
        
        doc.add(new Paragraph("Amount of budget provision available:",clasiffont));
    }

}
