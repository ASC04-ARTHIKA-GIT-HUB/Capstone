package com.myntra.utilities;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
 
public class ExtentManager {
 
    private static ExtentReports extent;
    static String projectPath = System.getProperty("user.dir");
 
    public static ExtentReports getInstance1() {
        if (extent == null) {
            String reportPath = projectPath + "/reports/MyntraReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Myntra Automation Report");
            spark.config().setDocumentTitle("Myntra Test Execution");
 
            extent = new ExtentReports();
            extent.attachReporter(spark);
 
            extent.setSystemInfo("Tester", "QA Engineer");
            extent.setSystemInfo("Application", "Myntra");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}