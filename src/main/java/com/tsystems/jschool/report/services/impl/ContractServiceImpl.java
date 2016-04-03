package com.tsystems.jschool.report.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.tsystems.jschool.report.dao.api.ContractDao;
import com.tsystems.jschool.report.dao.api.TariffDao;
import com.tsystems.jschool.report.entities.Contract;
import com.tsystems.jschool.report.exceptions.ReportServiceException;
import com.tsystems.jschool.report.services.api.ContractService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Stateless
public class ContractServiceImpl implements ContractService {

    @EJB
    private ContractDao contractDao;

    @EJB
    private TariffDao tariffDao;

    public String generateReportByTariffId(int tariffId) {



        String tariffName = tariffDao.getAllTariffs().stream()
                .filter(e -> e.getId() == tariffId).findFirst().get().getName();

        try {

            List<Contract> contracts = contractDao.getUserListByTariffId(tariffId);
            Document document = new Document(PageSize.A4, 50, 30, 30, 30);
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String fileName = tariffName + "_" + dateFormat.format(new Date()) + ".pdf";
            String titleText = "Report on " + (new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss")).format(new Date());

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));

            document.open();
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.NORMAL, new BaseColor(255, 152, 0));
            Font tariffFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, new BaseColor(255, 152, 0));
            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
            Font tableBodyFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);

            Paragraph title = new Paragraph(titleText, titleFont);
            Paragraph tariffTitle = new Paragraph("Tariff: " + tariffName, tariffFont);
            Chapter chapter = new Chapter(title, 1);
            chapter.setNumberDepth(0);
            Section section = chapter.addSection(tariffTitle, 1);
            section.setNumberDepth(0);
            section.add(new Paragraph("_________________"));

            if (contracts != null && !contracts.isEmpty()) {

                PdfPTable contractsTable = new PdfPTable(5);
                contractsTable.setWidthPercentage(100);
                contractsTable.setSpacingBefore(25);
                contractsTable.setSpacingAfter(25);

                String[] titleArray = {"Contract", "Client", "E-mail", "Tariff", "Options"};
                for (int i = 0; i < titleArray.length; i++) {
                    Phrase header = new Phrase(titleArray[i], tableHeaderFont);
                    Float fontSize = header.getFont().getSize();
                    Float capHeight = header.getFont().getBaseFont().getFontDescriptor(BaseFont.CAPHEIGHT, fontSize);
                    Float padding = 5f;
                    PdfPCell cell = new PdfPCell(header);
                    cell.setPadding(padding);
                    cell.setPaddingTop(capHeight - fontSize + padding);
                    cell.setBackgroundColor(new BaseColor(255, 204, 128));
                    cell.setBorderColorLeft(BaseColor.WHITE);
                    cell.setBorderColorRight(BaseColor.WHITE);
                    contractsTable.addCell(cell);
                }

                for (Contract contract : contracts) {
                    contractsTable.addCell(new PdfPCell(new Phrase(contract.getPhoneNumber(), tableBodyFont)));
                    contractsTable.addCell(new PdfPCell(new Phrase(contract.getName() + " " + contract.getSurname(),
                            tableBodyFont)));
                    contractsTable.addCell(new PdfPCell(new Phrase(contract.getEmail(), tableBodyFont)));
                    contractsTable.addCell(new PdfPCell(new Phrase(contract.getTariff(), tableBodyFont)));

                    String options = "";
                    for (int i = 0; i < contract.getContractOptions().size(); i++) {
                        options += contract.getContractOptions().get(i);
                        if (i == contract.getContractOptions().size() - 1) {
                            options += ", ";
                        }

                    }
                    contractsTable.addCell(new PdfPCell(new Phrase(options, tableBodyFont)));
                }

                section.add(contractsTable);
            } else {
                section.add(new Paragraph("No any users with such tariff", tableHeaderFont));
            }

            document.add(chapter);
            document.close();
            return fileName;

        } catch (IOException | DocumentException e) {
            throw new ReportServiceException(e.getMessage());
        }
    }

}
