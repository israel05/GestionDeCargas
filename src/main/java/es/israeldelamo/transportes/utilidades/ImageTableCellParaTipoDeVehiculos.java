package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloTipoDeVehiculo;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Extension de talbe cell para que contenga una imagen.
 *
 * @author chatgpt
 * @version $Id: $Id
 */
public class ImageTableCellParaTipoDeVehiculos extends TableCell<ModeloTipoDeVehiculo, Blob> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ImageTableCellParaTipoDeVehiculos.class);

    private final ImageView imageView = new ImageView();

    /**
     * Procedimiento auxiliar para formatear las celdas como ImageViews
     */
    public ImageTableCellParaTipoDeVehiculos() {
        imageView.setFitWidth(200);  // Set max width to 200
        imageView.setFitHeight(200); // Set max height to 200
        imageView.setPreserveRatio(true); // Preserve aspect ratio
        setGraphic(imageView);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateItem(Blob item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
        } else {
            try {
                // Convertir el BLOB a un array de bytes
                byte[] bytes = item.getBytes(1, (int) item.length());

                // Crear una imagen a partir del array de bytes
                Image image = new Image(new ByteArrayInputStream(bytes));

                // Configurar la imagen en el ImageView
                imageView.setImage(image);
                setGraphic(imageView);
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }
    }
}
