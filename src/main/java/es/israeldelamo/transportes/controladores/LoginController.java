package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.dao.DaoLogin;
import es.israeldelamo.transportes.modelos.ModeloLogin;
import es.israeldelamo.transportes.utilidades.Alertas;
import es.israeldelamo.transportes.utilidades.EsperaDialog;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static es.israeldelamo.transportes.utilidades.Video.reproducirVideo;

/**
 * Una clase que controla la pantalla de Login
 *
 * @author israel
 * @version $Id: $Id
 */
public class LoginController implements Initializable {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    Stage stage;
    /**
     * Botón para salir de la aplicación
     */
    @FXML
    private Button btn_salir;
    /**
     * Campo de contraseña
     */
    @FXML
    private PasswordField fld_passw;
    /**
     * Campo de usuario
     */
    @FXML
    private TextField fld_user;

    /**
     * El bundle de idiomas para este controlador
     */
    private static ResourceBundle bundle;


    /**
     * Carga el bundle de idiomas para que aparezcan las traducciones
     * podriamos haberlo traido de la clase anterior?
     */
    private void cargarBundleIdiomas(){
        // Cargar el idioma seleccionado
        Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
        String idioma = prefs.get("textos", "es"); // Valor por defecto "es"
        Locale locale = new Locale(idioma);
        bundle = ResourceBundle.getBundle("textos", locale);
    }
    /**
     * Abre el mp4 de tutorial de ADMINISTRADOR
     *
     * @param event
     */
    @FXML
    void abrirTutorialAdm(MouseEvent event) {
        reproducirVideo("administrador", stage);
    }

    /**
     * Abre el mp4 de tutorial de CONDUCTOR
     *
     * @param event
     */
    @FXML
    void abrirTutorialCnd(MouseEvent event) {
        reproducirVideo("conductor", stage);
    }

    /**
     * Abre el mp4 de tutorial de GESTOR
     *
     * @param event
     */
    @FXML
    void abrirTutorialGst(MouseEvent event) {
        reproducirVideo("gestor", stage);
    }

    /**
     * Función que añade el doble clic a la tableview Envios
     *
     * @author israel
     */
    @FXML
    private void logeoAdministrador() {
        logger.info("Intentando entrar en administrador");
        if (validarLogin("administrador")) {
            abreLaVentanaDe("gestionAdministrador");
        }
    }

    /**
     * Intenta conectarse como Conductor
     *
     * @author israel
     */
    @FXML
    void logueoConductor() {
        logger.info("Intentando entrar en Coductor");
        if (validarLogin("conductor")) {
            abreLaVentanaDe("gestionVehiculo");
        }
    }

    /**
     * Intenta conectarse como gestor
     *
     * @author israel
     */
    @FXML
    void logueoGestor() {
        logger.info("Intentando entrar en gestor");
        if (validarLogin("gestor")) {
            abreLaVentanaDe("gestionGerente");
        }
    }

    /**
     * Lee la base de datos para ver si existe ese login con ese rol
     *
     * @param tipoDeRol el papel que va a desempeñar el usuario
     * @return true si el login es válido, false si no lo es
     * @author israel
     */
    boolean validarLogin(String tipoDeRol) {
        ModeloLogin milogin = new ModeloLogin(fld_user.getText(), fld_passw.getText(), tipoDeRol);
        try {
            if (DaoLogin.cargarListadoLogins().contains(milogin)) {
                return true;
            } else {
                Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he encontrado esos datos de inicio de sesión");
                logger.error("Error de inicio de sesión");
            }
        } catch (SQLException e) {
            Alertas alertas = new Alertas();
            alertas.mostrarError("Fallo catastrófico de conexión");
        }
        return false;
    }

    /**
     * Abre una ventana en función del nombre de la ventana. Solo puede ser la de admin, conductor o gestor
     *
     * @param elNombreDeLaVentana el nombre de la ventana a abrir
     * @author israel
     */
    private void abreLaVentanaDe(String elNombreDeLaVentana) {
        //preparo los idiomas del bundle
        cargarBundleIdiomas();

        // Guarda la referencia de la ventana principal
        Stage currentStage = (Stage) btn_salir.getScene().getWindow();
        String elUsuario = fld_user.getText();  // Obtiene el usuario desde el campo de texto

        try {
            // Cargar el archivo FXML correspondiente


            // Aquí puedes crear una instancia del controlador manualmente
            if (elNombreDeLaVentana.equals("gestionVehiculo")) {
                // Mostrar diálogo de espera y diferir la carga para permitir que se pinte
                Stage owner = (Stage) btn_salir.getScene().getWindow();
                Stage waitDialog = EsperaDialog.mostrar(owner,
                        bundle != null ? bundle.getString("cargando_datos") : "Cargando datos...");

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/" + elNombreDeLaVentana + ".fxml"), bundle);

                        Parent root = loader.load();
                        GestionVehiculoController controlador = loader.getController();
                        controlador.pasoDeParametros(elUsuario);

                        Scene scene = new Scene(root);
                        Stage newStage = new Stage();
                        newStage.initModality(Modality.APPLICATION_MODAL);
                        newStage.initOwner(owner);

                        Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
                        newStage.getIcons().add(icono);

                        newStage.setScene(scene);
                        newStage.setResizable(false);
                        newStage.setMaximized(true);
                        newStage.setTitle("Gestión Vehículo");

                        GestionVehiculoController controladorVehiculo = loader.getController();
                        controladorVehiculo.initialize(elUsuario);

                        EsperaDialog.cerrar(waitDialog);
                        newStage.showAndWait();
                    } catch (IOException e) {
                        EsperaDialog.cerrar(waitDialog);
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No se pudo abrir la ventana de Vehículos: " + e.getMessage());
                    } finally {
                        EsperaDialog.cerrar(waitDialog);
                    }
                });
            }
            // De forma similar para las otras ventanas
            else if (elNombreDeLaVentana.equals("gestionGerente")) {
                // Mostrar diálogo de espera y diferir la carga para permitir que se pinte
                Stage owner = (Stage) btn_salir.getScene().getWindow();
                Stage waitDialog = EsperaDialog.mostrar(owner,
                        bundle != null ? bundle.getString("cargando_datos") : "Cargando datos...");

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/" + elNombreDeLaVentana + ".fxml"), bundle);

                        Parent root = loader.load();
                        GestionGerenteController controlador = loader.getController();
                        controlador.pasoDeParametros(elUsuario);

                        Scene scene = new Scene(root);
                        Stage newStage = new Stage();
                        newStage.initModality(Modality.APPLICATION_MODAL);
                        newStage.initOwner(owner);

                        Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
                        newStage.getIcons().add(icono);

                        newStage.setScene(scene);
                        newStage.setResizable(false);
                        newStage.setMaximized(true);
                        newStage.setTitle("Gestión Gerente");

                        GestionGerenteController controladorGerente = loader.getController();
                        controladorGerente.initialize(elUsuario);

                        EsperaDialog.cerrar(waitDialog);
                        newStage.showAndWait();
                    } catch (IOException e) {
                        EsperaDialog.cerrar(waitDialog);
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No se pudo abrir la ventana de Gerente: " + e.getMessage());
                    } finally {
                        EsperaDialog.cerrar(waitDialog);
                    }
                });


            } else if (elNombreDeLaVentana.equals("gestionAdministrador")) {
                // Mostrar diálogo de espera mientras se prepara la ventana
                Stage owner = (Stage) btn_salir.getScene().getWindow();
                Stage waitDialog = EsperaDialog.mostrar(owner,
                        bundle != null ? bundle.getString("cargando_datos") : "Cargando datos...");

                // IMPORTANTE: diferimos el trabajo pesado al siguiente ciclo del bucle de eventos
                // para que el diálogo tenga tiempo de renderizarse antes de bloquear con la carga.
                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/" + elNombreDeLaVentana + ".fxml"), bundle);

                        Parent root = loader.load();
                        GestionAdministradorController controlador = loader.getController();
                        controlador.pasoDeParametros(elUsuario);

                        Scene newScene = new Scene(root);
                        newScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

                        Stage newStage = new Stage();
                        newStage.initModality(Modality.APPLICATION_MODAL);
                        newStage.initOwner(owner);
                        newStage.setScene(newScene);
                        newStage.setTitle(elNombreDeLaVentana);
                        Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
                        newStage.getIcons().add(icono);

                        newStage.setResizable(false);
                        newStage.setMaximized(true);

                        GestionAdministradorController controladorAdmin = loader.getController();
                        // Le paso el usuario, en principio un conductor
                        controladorAdmin.initialize(elUsuario);

                        // Cerrar diálogo de espera justo antes de mostrar la ventana
                        EsperaDialog.cerrar(waitDialog);
                        newStage.showAndWait();
                    } catch (IOException e) {
                        // Si algo falla, cerramos el diálogo y mostramos error
                        EsperaDialog.cerrar(waitDialog);
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No se pudo abrir la ventana de Administración: " + e.getMessage());
                    } finally {
                        EsperaDialog.cerrar(waitDialog);
                    }
                });


            } else if (elNombreDeLaVentana.equals("VisorAyuda")) {
                FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/" + elNombreDeLaVentana + ".fxml"),bundle);

                Parent root = loader.load();


                Scene newScene = new Scene(root);
                newScene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

                Stage newStage = new Stage();
                newStage.setScene(newScene);
                newStage.setTitle(elNombreDeLaVentana);
                Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
                newStage.getIcons().add(icono);
                newStage.setResizable(true);
                newStage.setMaximized(true);
                newStage.showAndWait();


            }


        } catch (IOException e) {
            // Mostrar un error si ocurre una excepción al cargar la ventana
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("Fallo al lanzar la ventana solicitada " + elNombreDeLaVentana);
            alertaError.mostrarError(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     *
     * @author israel
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Foco inicial sobre el campo de nombre
        Platform.runLater(() -> fld_user.requestFocus());
        cargarBundleIdiomas();

    }


    /**
     * Muestra la ventana de ayuda
     *
     * @author israel
     */
    @FXML
    void mostrarAyuda() {
        abreLaVentanaDe("VisorAyuda");
    }

    /**
     * Cierra la aplicación
     *
     * @author israel
     */
    @FXML
    void salirDeLaApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de abandonar el programa?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.isPresent() && action.get() == ButtonType.OK) {
            Platform.exit();

        }

    }
}