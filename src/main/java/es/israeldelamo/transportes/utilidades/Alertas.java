package es.israeldelamo.transportes.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Objects;

/**
 * <p>Alertas class.</p>
 *
 * Establece el icono de las ventanas modales de alerta con el recurso
 * /es/israeldelamo/transportes/imagenes/icono.png
 *
 * @author israel
 * @version $Id: $Id
 */
public class Alertas {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(Alertas.class);

    private static final String ICON_PATH = "/es/israeldelamo/transportes/imagenes/icono.png";
    private static Image CACHED_ICON;

    private static Image getAppIcon() {
        if (CACHED_ICON != null) return CACHED_ICON;
        try (InputStream is = Alertas.class.getResourceAsStream(ICON_PATH)) {
            if (is == null) {
                logger.warn("No se encontró el icono en el classpath: {}", ICON_PATH);
                return null;
            }
            CACHED_ICON = new Image(is);
            return CACHED_ICON;
        } catch (Exception e) {
            logger.error("Error cargando el icono de la aplicación:", e);
            return null;
        }
    }

    private static void aplicarIcono(Alert alert) {
        Image icono = getAppIcon();
        if (icono == null) return;

        // Si aún no está creado el Window, deferimos la asignación hasta que se muestre
        if (alert.getDialogPane().getScene() == null || alert.getDialogPane().getScene().getWindow() == null) {
            alert.setOnShown(event -> {
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                if (stage != null) {
                    stage.getIcons().add(icono);
                }
            });
        } else {
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(icono);
        }
    }

    /**
     * Metodo para que salga la ventana de tipo INFORMATION con el mensaje querido
     *
     * @param mensaje a {@link java.lang.String} object
     */
    public void mostrarInformacion(String mensaje) {
        Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
        alertaInfo.setTitle("Información de la aplicación");
        alertaInfo.setHeaderText(null);
        alertaInfo.setContentText(mensaje);
        alertaInfo.initStyle(StageStyle.UTILITY);
        //quitamos la barra de título de la venta de alerta
        alertaInfo.getDialogPane().getStylesheets().
                add(Objects.requireNonNull(getClass().getResource(
                        "/css/estiloAlertas.css")).toExternalForm());


        aplicarIcono(alertaInfo);
        alertaInfo.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo ERROR con el mensaje querido
     *
     * @param mensaje a {@link java.lang.String} object
     */
    public void mostrarError(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.ERROR);
        alertaError.setTitle("Error en la aplicación");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);

        aplicarIcono(alertaError);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo WARNING con el mensaje querido
     *
     * @param mensaje a {@link java.lang.String} object
     */
    public void mostrarAviso(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.WARNING);
        alertaError.setTitle("Información adicional");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);

        aplicarIcono(alertaError);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo CONFIRMATION con el mensaje querido
     *
     * @param mensaje a {@link java.lang.String} object
     */
    public void mostrarAlertaConfirmacion(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.CONFIRMATION);
        alertaError.setTitle("Confirma esta acción");
        alertaError.setHeaderText(null);
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);

        aplicarIcono(alertaError);
        alertaError.showAndWait();
    }

    /**
     * Metodo para que salga la ventana de tipo INFORMATION con el mensaje querido
     *
     * @param mensaje a {@link java.lang.String} object
     */
    public void mostrarAlertaCabecera(String mensaje) {
        Alert alertaError = new Alert(Alert.AlertType.INFORMATION);
        alertaError.setTitle("ERROR");
        alertaError.setHeaderText("Información importante");
        alertaError.setContentText(mensaje);
        alertaError.initStyle(StageStyle.UTILITY);

        aplicarIcono(alertaError);
        alertaError.showAndWait();
    }
}
