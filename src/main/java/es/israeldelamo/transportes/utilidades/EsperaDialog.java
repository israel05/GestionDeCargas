package es.israeldelamo.transportes.utilidades;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Diálogo sencillo de espera con un ProgressIndicator indeterminado.
 * Útil para mostrar mientras la aplicación está realizando operaciones costosas.
 */
public class EsperaDialog {

    /**
     * Muestra un diálogo modal con un indicador de progreso y devuelve el Stage para poder cerrarlo.
     *
     * @param owner  ventana propietaria; puede ser null
     * @param mensaje texto a mostrar junto al indicador de progreso
     * @return Stage del diálogo mostrado
     */
    public static Stage mostrar(Stage owner, String mensaje) {
        ProgressIndicator indicator = new ProgressIndicator();
        indicator.setPrefSize(40, 40);

        Label lbl = new Label(mensaje == null ? "Cargando..." : mensaje);

        HBox contenido = new HBox(12, indicator, lbl);
        contenido.setAlignment(Pos.CENTER_LEFT);
        contenido.setPadding(new Insets(16));

        VBox root = new VBox(contenido);
        root.setAlignment(Pos.CENTER);

        Stage dialog = new Stage(StageStyle.UTILITY);
        if (owner != null) {
            dialog.initOwner(owner);
        }
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setTitle("Por favor, espere");

        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.sizeToScene();

        dialog.show();
        return dialog;
    }

    /**
     * Cierra el diálogo si no es null y está mostrado.
     */
    public static void cerrar(Stage dialog) {
        if (dialog != null) {
            dialog.close();
        }
    }
}
