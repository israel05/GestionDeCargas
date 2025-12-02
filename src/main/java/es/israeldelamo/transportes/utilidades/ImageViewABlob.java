package es.israeldelamo.transportes.utilidades;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

/**
 * Utility class for converting between ImageView and Blob.
 * Provides methods to convert ImageView to Blob and Blob to Image or BufferedImage.
 * This is useful for storing and retrieving images from a database.
 *
 * @version $Id: $Id
 */
public final class ImageViewABlob {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ImageViewABlob.class);

    private ImageViewABlob() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Converts an ImageView to a Blob.
     *
     * @param imgImagenTipoBultoTabTipoBulto the ImageView to be converted
     * @return the resulting Blob, or null if an error occurs
     */
    public static Blob conversorImageViewABlob(ImageView imgImagenTipoBultoTabTipoBulto) {
        try {
            Image image = imgImagenTipoBultoTabTipoBulto.getImage();
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream); // You can change the format as needed
            byte[] imageBytes = outputStream.toByteArray();
            return new javax.sql.rowset.serial.SerialBlob(imageBytes);
        } catch (IOException exIO) {

            logger.error("Error de entrada y salida en la conversión de imágenes");
            return null;
        } catch (SQLException exSQL) {

            logger.error("Error de SQL en la conversión de imágenes");
            return null;
        }
    }

    /**
     * Converts a Blob to an Image.
     * This method is useful for converting blobs read from SQL to Image for use in JasperReports.
     *
     * @param blob the Blob to be converted
     * @return the resulting Image
     * @throws Exception if an error occurs during conversion
     */
    public static Image convertBlobAImagen(Blob blob) throws Exception {
        try (InputStream inputStream = blob.getBinaryStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            return SwingFXUtils.toFXImage(bufferedImage, null);
        }
    }

    /**
     * Converts a Blob to a BufferedImage.
     * This method is useful for converting blobs read from SQL to BufferedImage for use in JasperReports.
     *
     * @param blob the Blob to be converted
     * @return the resulting BufferedImage
     * @throws Exception if an error occurs during conversion
     */
    public static BufferedImage convertBlobToImage(Blob blob) throws Exception {
        try (InputStream inputStream = blob.getBinaryStream()) {
            return ImageIO.read(inputStream);
        }
    }
}
