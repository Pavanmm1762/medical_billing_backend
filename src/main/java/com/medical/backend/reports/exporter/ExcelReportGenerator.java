package com.medical.backend.reports.exporter;

import java.io.ByteArrayOutputStream;
import java.time.YearMonth;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.medical.backend.reports.dto.MonthlyReportResponse;

@Component
public class ExcelReportGenerator {

    public byte[] generate(MonthlyReportResponse report, YearMonth month) {

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet summary = wb.createSheet("Summary");
            Row s = summary.createRow(0);
            s.createCell(0).setCellValue("Total Sales");
            s.createCell(1).setCellValue(report.summary().totalSales());

            Sheet daily = wb.createSheet("Daily");
            Row h = daily.createRow(0);
            h.createCell(0).setCellValue("Date");
            h.createCell(1).setCellValue("Sales");
            h.createCell(2).setCellValue("Purchase");
            h.createCell(3).setCellValue("Expense");
            h.createCell(4).setCellValue("Profit");

            int r = 1;
            for (var d : report.daily()) {
                Row row = daily.createRow(r++);
                row.createCell(0).setCellValue(d.date().toString());
                row.createCell(1).setCellValue(d.sales());
                row.createCell(2).setCellValue(d.purchases());
                row.createCell(3).setCellValue(d.otherExpenses());
                row.createCell(4).setCellValue(d.profit());
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
