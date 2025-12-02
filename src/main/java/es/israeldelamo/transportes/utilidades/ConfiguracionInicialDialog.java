package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.Lanzador;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Diálogo para capturar por primera vez la configuración y crear el archivo configuration.properties
 * junto al JAR cuando no existe.
 */
public class ConfiguracionInicialDialog {

    private static final Logger logger = LoggerFactory.getLogger(Lanzador.class);


    private static final String ICON_PATH = "/es/israeldelamo/transportes/imagenes/icono.png";
    private static Image CACHED_ICON;

    private static Image getAppIcon() {
        if (CACHED_ICON != null) return CACHED_ICON;
        try (java.io.InputStream is = ConfiguracionInicialDialog.class.getResourceAsStream(ICON_PATH)) {
            if (is == null) {
                return null;
            }
            CACHED_ICON = new Image(is);
            return CACHED_ICON;
        } catch (Exception e) {
            logger.error("no he podido encontrar el icono de la app");
            return null;
        }
    }

    private static void aplicarIcono(Dialog<?> dialog) {
        Image icono = getAppIcon();
        if (icono == null) return;

        // Si aún no está creado el Window, deferimos la asignación hasta que se muestre
        if (dialog.getDialogPane().getScene() == null
                || dialog.getDialogPane().getScene().getWindow() == null) {
            dialog.setOnShown(event -> {
                Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                if (stage != null) {
                    stage.getIcons().add(icono);
                }
            });
        } else {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            if (stage != null) {
                stage.getIcons().add(icono);
            }
        }
    }



    /**
     * Muestra un diálogo JavaFX para pedir datos de configuración y crea el archivo en la base de la app
     * de este modo la primera vez forzamos al cliente a meter los datos de la base de datos.
     * @param owner ventana propietaria para el diálogo
     * @param destino ruta del archivo configuration.properties a crear
     * @return true si se creó el archivo, false si el usuario canceló
     */
    public static boolean mostrarYCrearSiFalta(Stage owner, Path destino) {
        //Vamos a hacer el cuadro de díalogo en vez de con FXML a mano con los elementos desde aquí

        Dialog<Boolean> dialog = new Dialog<>();
        aplicarIcono(dialog);


        dialog.setTitle("Configuración inicial");
        dialog.setHeaderText("Introduce los datos necesarios para la configurar inicial de la aplicación");
        if (owner != null) {
            dialog.initOwner(owner);
        }

        ButtonType crearButtonType = new ButtonType("Crear", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // Campos a solicitar (orden importante)
        Map<String, TextField> campos = new LinkedHashMap<>();
        TextField tfUser = new TextField();
        tfUser.setPromptText("Usuario de BD");
        campos.put("user", tfUser);

        PasswordField pfPass = new PasswordField();
        pfPass.setPromptText("Contraseña de BD");
        campos.put("password", pfPass);

        TextField tfUrl = new TextField();
        tfUrl.setPromptText("Host o URL BD (p.ej. 212.81.197.58/transportes)");
        campos.put("urlTransportes", tfUrl);

        PasswordField pfPassCorreo = new PasswordField();
        pfPassCorreo.setPromptText("Contraseña servicio de correo de maitrap.io");
        campos.put("passwordCorreo", pfPassCorreo);

        TextField tfDest0 = new TextField();
        tfDest0.setPromptText("Destinatario principal de los correos");
        campos.put("destinatarioCorreo", tfDest0);
        TextField tfDest1 = new TextField();
        tfDest1.setPromptText("Destinatario 1 (opcional)");
        campos.put("destinatarioCorreo1", tfDest1);
        TextField tfDest2 = new TextField();
        tfDest2.setPromptText("Destinatario 1 (opcional)");
        campos.put("destinatarioCorreo2", tfDest2);
        TextField tfDest3 = new TextField();
        tfDest3.setPromptText("Destinatario 1  (opcional)");
        campos.put("destinatarioCorreo3", tfDest3);
        TextField tfDest4 = new TextField();
        tfDest4.setPromptText("Destinatario 1  (opcional)");
        campos.put("destinatarioCorreo4", tfDest4);

        // los campos los metemos uno por uno en el mapa

        int row = 0;
        for (Map.Entry<String, TextField> entry : campos.entrySet()) {
            //añade un label cuyo nombre es el valor del getPrompt del elemento que exista en esa fila del mapa
            // es una solución elegante
            grid.add(new Label(entry.getValue().getPromptText() + ":"), 0, row);
            grid.add(entry.getValue(), 1, row);
            row++;
        }

        Node crearButton = dialog.getDialogPane().lookupButton(crearButtonType);
        crearButton.setDisable(true);

        // Validación cutre: user, password, urlTransportes y destinatarioCorreo no vacíos
        // funcion para validar los datos, si esta en blanco se aguanta que no pasa
        Runnable validar = () -> {
            boolean ok = !tfUser.getText().isBlank()
                    && !pfPass.getText().isBlank()
                    && !tfUrl.getText().isBlank()
                    && !tfDest0.getText().isBlank();
            crearButton.setDisable(!ok);
        };
        tfUser.textProperty().addListener((obs, o, n) -> validar.run());
        pfPass.textProperty().addListener((obs, o, n) -> validar.run());
        tfUrl.textProperty().addListener((obs, o, n) -> validar.run());
        tfDest0.textProperty().addListener((obs, o, n) -> validar.run());

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == crearButtonType) {
                try {
                    Properties p = new Properties();
                    for (Map.Entry<String, TextField> entry : campos.entrySet()) {
                        String key = entry.getKey();
                        String val = entry.getValue().getText() == null ? "" : entry.getValue().getText().trim();
                        p.setProperty(key, val);
                    }
                    try (OutputStream os = Files.newOutputStream(destino)) {
                        p.store(os, "Configuración generada por el usuario");
                    }
                    return true;
                } catch (Exception e) {
                    new Alertas().mostrarError("No se pudo crear el archivo de configuración: " + e.getMessage());
                    return false;
                }
            }
            return false;
        });

        validar.run();
        return Boolean.TRUE.equals(dialog.showAndWait().orElse(false));
    }
}
