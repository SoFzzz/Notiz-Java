package com.notiz.service;

import com.notiz.dto.report.DistributionDTO;
import com.notiz.dto.report.ReportDTO;
import com.notiz.dto.report.TrendDataDTO;

import java.util.List;

public interface ReportService {
    ReportDTO generateStudentReportCard(String studentId, String periodId, String tenantId);
    List<TrendDataDTO> getSubjectPerformanceTrends(String subjectId, String tenantId);
    DistributionDTO getGradeDistribution(String subjectId, String periodId, String tenantId);
    byte[] generatePdfReport(String studentId, String periodId, String tenantId);
}
