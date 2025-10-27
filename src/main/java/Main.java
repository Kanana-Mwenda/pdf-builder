import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        String htmlFilePath = "target/classes/account-statements.html";
        String outputPdfPath = "src/out/statements.pdf";

        try {
            // Ensure output directory exists
            new File("src/out").mkdirs();

            // Read the HTML
            String html = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);

            try (OutputStream os = new FileOutputStream(outputPdfPath)) {
                PdfRendererBuilder builder = new PdfRendererBuilder();
                builder.useFastMode();

                // Load HTML content directly, setting base URI to the directory containing the HTML file for correct asset resolution
                builder.withHtmlContent(html, new File("target/classes").toURI().toString());
                builder.toStream(os);
                builder.run();
            }

            System.out.println(" PDF created successfully at: " + outputPdfPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" Error generating PDF: " + e.getMessage());
        }
    }
}
