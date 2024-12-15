package com.crosska;

public class Report {

    private final String reportName;
    private Integer queryPrecision;

    public Report(String reportName) {
        this.queryPrecision = 0;
        this.reportName = reportName;
    }

    public void increasePrecision() {
        queryPrecision++;
    }

    public void resetPrecision() {
        queryPrecision = 0;
    }

    public String getReportName() {
        return reportName;
    }

    public int getQueryPrecision() {
        return queryPrecision;
    }

}
