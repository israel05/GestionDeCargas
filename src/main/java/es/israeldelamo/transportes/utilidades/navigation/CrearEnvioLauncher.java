package es.israeldelamo.transportes.utilidades.navigation;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.controladores.CrearEnvioController;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Encapsula la apertura de la ventana "Crear envío" para mantener los controladores más simples.
 */
public final class CrearEnvioLauncher {

    private CrearEnvioLauncher() {}

    /**
     * Abre la ventana modal para crear un nuevo envío.
     * @param bundle bundle de i18n a usar en el FXMLLoader (puede ser null)
     * @param owner ventana propietaria (puede ser null)
     * @param conductor DNI del conductor
     * @param sentenciaSQL filtro SQL construido
     */
    public static void launch(ResourceBundle bundle, Stage owner, String conductor, String sentenciaSQL) {
        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/crearEnvio.fxml"), bundle);
        try {
            Parent root = loader.load();
            CrearEnvioController controlador = loader.getController();
            controlador.cargaElSQLDesdeLaPantallaGestionVehiculo(sentenciaSQL);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            if (owner != null) {
                stage.initOwner(owner);
            }
            Image icono = new Image(String.valueOf(CrearEnvioLauncher.class.getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
            stage.getIcons().add(icono);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Crear un nuevo envio");

            CrearEnvioController crearEnvioController = loader.getController();
            crearEnvioController.inicializarLaEscenaConDatosPasados(conductor, sentenciaSQL);

            stage.showAndWait();
        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Error general del sistema. No puedo hacer eso");
            alerta.mostrarError(e.getMessage());
        }
    }
}
