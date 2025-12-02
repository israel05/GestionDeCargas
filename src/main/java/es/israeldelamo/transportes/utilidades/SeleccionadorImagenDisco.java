package es.israeldelamo.transportes.utilidades;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Procedimiento utilidad para lanzar un menu de selección de imagen
 *
 * @author israel
 * @version $Id: $Id
 */
public class SeleccionadorImagenDisco {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(SeleccionadorImagenDisco.class);

    // BLOQUE DE GESTION DE LA CARGA DE IMÁGENES DESDE ARCHIVOS LOCALES
    private InputStream imagen;


    /**
     * Permite elegir un archivo desde el disco duro, puede ser jpg o png y será reescalada
     *
     * @param elImageViewAUsar  el objeto ImageView que hace la llamada
     * @param elBotonQueLoLlama el objeto Button que le llama
     */
    public static void SeleccionadorImagenDisco(ImageView elImageViewAUsar, Button elBotonQueLoLlama) {

        //creamos el filechooser
        FileChooser fileDialog = new FileChooser();
        //le damos propiedades comunes
        fileDialog.setTitle("Elige la imagen a usar");
        //que solo coja png y jpg con un mensajito de que sean pequeñas
        fileDialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("FOTOS PEQUEÑAS DE 200 por 200", "*.png", "*.jpg"));
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        //que cargue desde ese directorio
        fileDialog.setInitialDirectory(new File(currentPath));
        //creamos un file para esa escena
        File file = fileDialog.showOpenDialog(elBotonQueLoLlama.getScene().getWindow());
        if (file == null) {
            Alertas alerta = new Alertas();
            alerta.mostrarAviso("No has elegido imagen alguna");
            logger.info("No has elegido imagen alguna");
        } else {
            try {

                // Cargamos la imagen
                FileInputStream imagen = new FileInputStream(file);
                Image image = new Image(imagen);

                // Redimensionamos la imagen a 250x250
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);


                // Actualizamos el ImageView proporcionado
                elImageViewAUsar.setImage(imageView.getImage());
                elImageViewAUsar.setFitWidth(200);
                elImageViewAUsar.setFitHeight(200);
                elImageViewAUsar.setPreserveRatio(true);


            } catch (Exception exception) {
                Alertas alerta = new Alertas();
                alerta.mostrarError("Atención no se ha podido abrir esa foto");
                logger.error("No se ha podido abrir esa foto");
                exception.printStackTrace();
            }
        }
    }
}
