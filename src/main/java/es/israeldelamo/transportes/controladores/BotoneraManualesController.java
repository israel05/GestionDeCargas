package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Una pantallita de 3 por 3 con los diferentes informes
 *
 * @author israel
 * @version $Id: $Id
 */
public class BotoneraManualesController {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(BotoneraManualesController.class);

    /**
     * Toma un flujo de entrada, lo guarda en un archivo temporal y trata de abrirlo con la aplicación predeterminada del sistema.
     *
     * @param inputStream   El flujo de entrada que contiene el archivo a abrir.
     * @param fileExtension La extensión del archivo temporal (por ejemplo, ".pdf").
     */

    public static void openFileUsingDefaultApplication(InputStream inputStream, String fileExtension) {
        try {
            // Create a temporary file

            Path tempFile = Files.createTempFile("tempfile", fileExtension);
            File tempFilePath = tempFile.toFile();

            // Copy content from InputStream to the temporary file
            try (FileOutputStream fos = new FileOutputStream(tempFilePath)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }

            // Now, open the file using the default system application
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(tempFilePath);
            } else {
                logger.error("Desktop is not supported on this system.");
                //System.out.println("Desktop is not supported on this system.");
            }

        } catch (IOException e) {
            logger.error("Ha ocurrido un error al cargar el informe al abrir el archivo", e);
            //System.out.println("Error occurred while trying to open the file.");
        }
    }

    /**
     * Método auxiliar para localizar un archivo PDF en los recursos y abrirlo.
     *
     * @param elPdfAAbrir El nombre del archivo PDF (sin la extensión) ubicado en la carpeta de recursos.
     */
    private static void abreUnPDF(String elPdfAAbrir) {
        // Example usage


        try (InputStream inputStream = Iniciografico.class.getResourceAsStream("/es/israeldelamo/transportes/pdf/" + elPdfAAbrir + ".pdf")) {
            if (inputStream != null) {
                openFileUsingDefaultApplication(inputStream, ".pdf");
            } else {
                logger.error("File not found.");

            }


        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Ha ocurrido un error al cargar el informe al abrir el archivo");
            logger.error("Ha ocurrido un error al cargar el informe al abrir el archivo", e);
        }

    }

    /**
     * Lanza un MANUAL DE GUÍA RÁPIDA
     */
    @FXML
    void lanzarManualGuiaRapida() {
        abreUnPDF("GuiaRapida");

    }


    /**
     * Lanza un INFORME DE GUÍA DE REFERENCIA
     */
    @FXML
    void lanzarManualGuiaReferencia() {
        abreUnPDF("GuiaReferencia");
    }

    /**
     * Lanza UNA GUÍA TÉCNICA
     */
    @FXML
    void lanzarManualGuiaTecnica() {
        abreUnPDF("GuiaTecnica");
    }


    /**
     * Lanza un MANUAL DE EXPLOTACIÓN
     */
    @FXML
    void lanzarManualExplotacion() {
        abreUnPDF("ManualExplotacion");
    }

    /**
     * Lanza UN MANUAL DE INSTALACIÓN
     */

    @FXML
    void lanzarManualInstalacion() {
        abreUnPDF("ManualInstalacion");
    }


    /**
     * Lanza un MANUAL DE MANTENIMIENTO
     */
    @FXML
    void lanzarManualMantenimiento() {
        abreUnPDF("ManualMantenimiento");
    }


    /**
     * Lanza un MANUAL DE USUARIO
     */
    @FXML
    void lanzarManualUsuario() {
        abreUnPDF("ManualUsuario");
    }

}
