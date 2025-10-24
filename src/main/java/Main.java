import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.File;
import java.io.FileOutputStream;


public class Main {
    public static void main(String[] args) {
        String htmlFilePath = "src/main/resources/account-statements.html";
        String outputPdfPath = "src/out/report.pdf";

        try {
            new File("src/out").mkdirs();

            try(FileOutputStream os= new FileOutputStream(outputPdfPath)) {
                PdfRendererBuilder builder = new PdfRendererBuilder(); 
                builder.useFastMode();
                builder.withFile(new File(htmlFilePath));
                builder.toStream(os);
                builder.run();
            }

            System.out.println("PDF created successfully at: " + outputPdfPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error generating PDF: " + e.getMessage());
        }
    }
}
