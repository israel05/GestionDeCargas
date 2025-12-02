package es.israeldelamo.transportes.controladores;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <p>VisorAyudaController class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class VisorAyudaController implements Initializable {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(VisorAyudaController.class);

    @FXML
    private WebView visor;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        WebEngine webEngine = visor.getEngine();
        URL url = getClass().getResource("/es/israeldelamo/transportes/html/ayuda.html"); // Ruta relativa desde resources
        if (url != null) {
            logger.info("Mostrando ayuda HTML");
            webEngine.load(url.toExternalForm());
        } else {
            logger.error("No se pudo cargar el archivo de ayuda HTML");
        }
    }

}
