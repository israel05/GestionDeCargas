package es.israeldelamo.transportes;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Punto de entrada en la aplicación, muestra la escena de login
 *
 * @author israel
 * @version $Id: $Id
 */
public class Iniciografico extends Application {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(Iniciografico.class);

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Asegurarse de que existe el archivo configuration.properties junto al JAR
        asegurarArchivoConfiguracion(stage);

        // Cargar el idioma seleccionado
        Preferences prefs = Preferences.userNodeForPackage(Iniciografico.class);
        String idioma = prefs.get("textos", "es"); // Valor por defecto "es"
        Locale locale = new Locale(idioma);
        ResourceBundle bundle = ResourceBundle.getBundle("textos", locale);

        logger.info("Lanzando la ventana de Login");

        FXMLLoader fxmlLoader = new FXMLLoader(Iniciografico.class.getResource("fxml/loginV2.fxml"),bundle);

        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("Transportes");
        stage.setMinHeight(620);
        stage.setMinWidth(700);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        Image icono = new Image(Objects.requireNonNull(getClass().getResourceAsStream("imagenes/icono.png")));
        //ruta de la foto a poner en el logo de la ventanas stage.getIcons().add(imagen);
        stage.getIcons().add(icono);
        stage.show();
    }

    /**
     * Comprueba si existe el archivo "configuration.properties" en el directorio de trabajo (junto al JAR).
     * Si no existe, muestra un diálogo JavaFX solicitando los datos necesarios y lo crea.
     * Si el usuario cancela el diálogo, se cierra la aplicación.
     *
     * Esta lógica garantiza que el archivo no se empaquete dentro del JAR, sino que sea creado por el usuario
     * la primera vez que arranca la aplicación.
     */
    private void asegurarArchivoConfiguracion(Stage owner) {
        Path ruta = Paths.get("configuration.properties");
        if (Files.exists(ruta)) {
            return;
        }

        boolean creado = es.israeldelamo.transportes.utilidades.ConfiguracionInicialDialog
                .mostrarYCrearSiFalta(null, ruta);
        if (!creado) {
            // Usuario canceló: cerrar app limpiamente
            owner.close();
            System.exit(0);
        }
    }
}
