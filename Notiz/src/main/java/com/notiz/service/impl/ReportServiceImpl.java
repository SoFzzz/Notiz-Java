package com.notiz.service.impl;

import com.notiz.dto.report.DistributionDTO;
import com.notiz.dto.report.ReportDTO;
import com.notiz.dto.report.TrendDataDTO;
import com.notiz.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Override
    public ReportDTO generateStudentReportCard(String studentId, String periodId, String tenantId) {
        return null;
    }

    @Override
    public List<TrendDataDTO> getSubjectPerformanceTrends(String subjectId, String tenantId) {
        return Collections.emptyList();
    }

    @Override
    public DistributionDTO getGradeDistribution(String subjectId, String periodId, String tenantId) {
        return null;
    }

    @Override
    public byte[] generatePdfReport(String studentId, String periodId, String tenantId) {
        return new byte[0];
    }
}
