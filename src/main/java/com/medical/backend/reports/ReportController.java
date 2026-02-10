package com.medical.backend.reports;

import com.medical.backend.reports.dto.MonthlyReportResponse;
import com.medical.backend.reports.exporter.ExcelReportGenerator;
import com.medical.backend.reports.exporter.PdfReportGenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReportController {

        private final ReportAggregatorService aggregator;
        private final PdfReportGenerator pdf;
        private final ExcelReportGenerator excel;

        public ReportController(
                        ReportAggregatorService aggregator,
                        PdfReportGenerator pdf,
                        ExcelReportGenerator excel) {
                this.aggregator = aggregator;
                this.pdf = pdf;
                this.excel = excel;
        }

        @GetMapping("/monthly")
        public MonthlyReportResponse monthly(
                        @RequestParam YearMonth month) {
                return aggregator.buildMonthlyReport(month);
        }

        @GetMapping("/monthly/pdf")
        public ResponseEntity<byte[]> pdf(
                        @RequestParam YearMonth month) {
                return ResponseEntity.ok()
                                .header("Content-Disposition",
                                                "attachment; filename=report-" + month + ".pdf")
                                .body(pdf.generate(
                                                aggregator.buildMonthlyReport(month),
                                                month));
        }

        @GetMapping("/monthly/excel")
        public ResponseEntity<byte[]> excel(
                        @RequestParam YearMonth month) {
                return ResponseEntity.ok()
                                .header("Content-Disposition",
                                                "attachment; filename=report-" + month + ".xlsx")
                                .body(excel.generate(
                                                aggregator.buildMonthlyReport(month),
                                                month));
        }
}
