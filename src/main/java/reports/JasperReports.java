package reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JREmptyDataSource; // Importação corrigida
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;

@Component
public class JasperReports {

    public byte[] gerarRelatorioPdf() {
        try {
            // Caminho para o seu arquivo XML de relatório
            String path = "src/main/resources/META-INF/relatorio.xml"; 

            // Compila o relatório
            JasperReport jasper = JasperCompileManager.compileReport(path);
            
            // Preenche o relatório (usando um DataSource vazio por enquanto)
            JasperPrint print = JasperFillManager.fillReport(jasper, null, new JREmptyDataSource());

            // Converte para byte array para o navegador baixar
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(print, out);
            
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
