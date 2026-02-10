package com.medical.backend.reports.exporter;

import java.io.ByteArrayOutputStream;
import java.time.YearMonth;

import org.springframework.stereotype.Component;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.medical.backend.reports.dto.MonthlyReportResponse;

@Component
public class PdfReportGenerator {

    public byte[] generate(MonthlyReportResponse report, YearMonth month) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4);
        PdfWriter.getInstance(doc, out);
        doc.open();

        Font title = new Font(Font.HELVETICA, 16, Font.BOLD);
        Font bold = new Font(Font.HELVETICA, 10, Font.BOLD);

        doc.add(new Paragraph("Medical Shop Monthly Report - " + month, title));
        doc.add(Chunk.NEWLINE);

        doc.add(new Paragraph("SUMMARY", bold));
        doc.add(new Paragraph("Sales: ₹ " + report.summary().totalSales()));
        doc.add(new Paragraph("Purchases: ₹ " + report.summary().totalPurchases()));
        doc.add(new Paragraph("Other Expenses: ₹ " + report.summary().otherExpenses()));
        doc.add(new Paragraph("Fixed Expenses: ₹ " + report.summary().fixedExpenses()));
        doc.add(new Paragraph("Net Profit: ₹ " + report.summary().netProfit()));

        doc.add(Chunk.NEWLINE);
        doc.add(new Paragraph("DAY-WISE REPORT", bold));

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100);
        table.addCell("Date");
        table.addCell("Sales");
        table.addCell("Purchase");
        table.addCell("Expense");
        table.addCell("Profit");

        for (var d : report.daily()) {
            table.addCell(d.date().toString());
            table.addCell("₹ " + d.sales());
            table.addCell("₹ " + d.purchases());
            table.addCell("₹ " + d.otherExpenses());
            table.addCell("₹ " + d.profit());
        }

        doc.add(table);
        doc.close();
        return out.toByteArray();
    }
}
