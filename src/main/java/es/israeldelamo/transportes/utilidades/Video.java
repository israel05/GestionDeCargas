package es.israeldelamo.transportes.utilidades;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Video {

    /**
     * Reproduce un video almacenado en el directorio `resources` del proyecto Maven.
     * El video debe estar en la carpeta `src/main/resources`.
     * @author Junie
     * @param nombreDelVideo El nombre del archivo de video en la carpeta `resources`.
     * @param stage          El escenario donde se va a mostrar el video.
     */
    public static void reproducirVideo(String nombreDelVideo, Stage stage) {
        //todos los videos estan en resources,
        //pero como al empaquetar puede perder la ruta, mejor lo pongo entero
        String videoPath = String.valueOf(Video.class.getResource("/es/israeldelamo/transportes/mp4/" + nombreDelVideo + ".mp4"));

        // Crear un objeto Media a partir de la ruta del video
        Media media = new Media(videoPath);
        MediaPlayer player = new MediaPlayer(media);
        MediaView viewer = new MediaView(player);

        // Ajustar el tamaño del video al tamaño de la escena
        DoubleProperty width = viewer.fitWidthProperty();
        DoubleProperty height = viewer.fitHeightProperty();
        width.bind(Bindings.selectDouble(viewer.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(viewer.sceneProperty(), "height"));
        viewer.setPreserveRatio(true);

        // Crear el contenedor (StackPane) para agregar el video
        StackPane root = new StackPane();
        root.getChildren().add(viewer);

        // Crear la escena y configurarla
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        stage = new Stage();


        Image icono = new Image(String.valueOf(Video.class.getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
        stage.getIcons().add(icono);

        stage.setScene(scene);
        stage.setTitle("Tutorial del " + nombreDelVideo);

        // Agregar el listener para detener el video cuando se cierre la ventana
        stage.setOnCloseRequest(event -> {
            player.stop();  // Detener la reproducción del video
        });

        stage.show();

        player.play();


    }
}
