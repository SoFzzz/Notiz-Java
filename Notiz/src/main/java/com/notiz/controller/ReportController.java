package com.notiz.controller;

import com.notiz.dto.report.DistributionDTO;
import com.notiz.dto.report.ReportDTO;
import com.notiz.dto.report.TrendDataDTO;
import com.notiz.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/student/{studentId}/period/{periodId}")
    public ResponseEntity<ReportDTO> generateStudentReportCard(@PathVariable String studentId, @PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(reportService.generateStudentReportCard(studentId, periodId, tenantId));
    }

    @GetMapping("/subject/{subjectId}/trends")
    public ResponseEntity<List<TrendDataDTO>> getSubjectPerformanceTrends(@PathVariable String subjectId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(reportService.getSubjectPerformanceTrends(subjectId, tenantId));
    }

    @GetMapping("/subject/{subjectId}/period/{periodId}/distribution")
    public ResponseEntity<DistributionDTO> getGradeDistribution(@PathVariable String subjectId, @PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        return ResponseEntity.ok(reportService.getGradeDistribution(subjectId, periodId, tenantId));
    }

    @GetMapping("/student/{studentId}/period/{periodId}/pdf")
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable String studentId, @PathVariable String periodId, @RequestHeader("X-Tenant-ID") String tenantId) {
        byte[] pdfBytes = reportService.generatePdfReport(studentId, periodId, tenantId);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "report_card_" + studentId + ".pdf");
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
