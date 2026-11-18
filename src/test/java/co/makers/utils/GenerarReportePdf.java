package co.makers.utils;

import org.openqa.selenium.Pdf;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.print.PageMargin;
import org.openqa.selenium.print.PrintOptions;

import java.awt.Desktop;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;

/**
 * Genera el reporte de resultados de la ejecución en HTML y PDF a partir del resumen de Serenity.
 * <p>
 * Se ejecuta automáticamente en {@code mvn verify} (fase post-integration-test, después de que Serenity
 * genera {@code serenity-summary.html}). El PDF se imprime con Chrome headless mediante el comando de
 * impresión estándar de WebDriver.
 * <p>
 * Uso: {@code GenerarReportePdf <serenity-summary.html> <carpeta-destino>}
 */
public final class GenerarReportePdf {

    private static final String NOMBRE_REPORTE = "reporte-ejecucion";

    private GenerarReportePdf() {
    }

    public static void main(String[] args) throws IOException {
        Path resumen = Path.of(args.length > 0 ? args[0] : "target/site/serenity/serenity-summary.html");
        Path destino = Path.of(args.length > 1 ? args[1] : "target/reporte");

        if (!Files.exists(resumen)) {
            System.out.println("[reporte] No se encontró " + resumen + "; no hay resultados para exportar.");
            return;
        }
        Files.createDirectories(destino);

        Path html = destino.resolve(NOMBRE_REPORTE + ".html");
        Files.copy(resumen, html, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("[reporte] HTML: " + html.toAbsolutePath());

        Path pdf = destino.resolve(NOMBRE_REPORTE + ".pdf");
        try {
            Files.write(pdf, imprimirComoPdf(html));
            System.out.println("[reporte] PDF:  " + pdf.toAbsolutePath());
        } catch (RuntimeException e) {
            // Sin Chrome disponible (p. ej. un agente de CI solo para API) no se rompe el build: queda el HTML.
            System.out.println("[reporte] No se pudo generar el PDF (¿Chrome instalado?): " + e.getMessage());
        }

        boolean abrir = args.length <= 2 || Boolean.parseBoolean(args[2]);
        if (abrir && System.getenv("CI") == null) {
            abrirEnElNavegador(html);
        }
    }

    /** Abre el reporte HTML con el navegador predeterminado del sistema. */
    private static void abrirEnElNavegador(Path html) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(html.toUri());
            } else if (System.getProperty("os.name").toLowerCase().contains("win")) {
                new ProcessBuilder("cmd", "/c", "start", "", html.toAbsolutePath().toString()).start();
            } else {
                new ProcessBuilder("xdg-open", html.toAbsolutePath().toString()).start();
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("[reporte] No se pudo abrir el navegador: " + e.getMessage());
        }
    }

    private static byte[] imprimirComoPdf(Path html) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu", "--no-sandbox", "--disable-dev-shm-usage");

        ChromeDriver driver = new ChromeDriver(options);
        try {
            driver.get(html.toUri().toString());

            PrintOptions printOptions = new PrintOptions();
            printOptions.setBackground(true);
            printOptions.setPageMargin(new PageMargin(1.0, 1.0, 1.0, 1.0));
            printOptions.setShrinkToFit(true);

            Pdf pdf = driver.print(printOptions);
            return Base64.getDecoder().decode(pdf.getContent());
        } finally {
            driver.quit();
        }
    }
}
