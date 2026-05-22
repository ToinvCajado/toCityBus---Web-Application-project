/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JREmptyDataSource;

public class JasperReports {

    public static void main(String[] args) {
        try {
            JasperReport jasper =
                JasperCompileManager.compileReport(
                    "src/relatorio.jrxml"
                );

            JasperPrint print =
                JasperFillManager.fillReport(
                    jasper,
                    null,
                    new JREmptyDataSource()
                );

            JasperExportManager.exportReportToPdfFile(
                print,
                "relatorio.pdf"
            );

            System.out.println("PDF gerado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
