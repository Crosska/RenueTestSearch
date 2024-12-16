package com.crosska;

public class Report {

    private final String[] parsedReportName;
    private final String originalReportName;
    private Integer queryPrecision;

    public Report(String reportName) {
        this.queryPrecision = 0;
        this.parsedReportName = DataAnalyser.parseString(reportName);
        this.originalReportName = reportName;
    }

    public void increasePrecision() {
        queryPrecision++;
    }

    public void resetPrecision() {
        queryPrecision = 0;
    }

    public String getOriginalReportName() {
        return originalReportName;
    }

    public String[] getParsedReportName() {
        return parsedReportName;
    }

    public int getQueryPrecision() {
        return queryPrecision;
    }

}
