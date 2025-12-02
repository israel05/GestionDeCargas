package es.israeldelamo.transportes.utilidades.filtros;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Optional;

/**
 * Utilidad para mostrar el cuadro de confirmación del filtro antes de lanzar la búsqueda.
 */
public final class ConfirmacionFiltroDialog {

    private ConfirmacionFiltroDialog() {}

    private static Text textoEnNegrita(String texto) {
        Text t = new Text(texto);
        t.setStyle("-fx-font-weight: bold;");
        return t;
    }

    private static Text textoNormal(String texto) {
        return new Text(texto);
    }

    /**
     * Muestra el diálogo de confirmación y devuelve true si el usuario acepta continuar.
     */
    public static boolean mostrar(FiltroEnvioResult filtro) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirma esta selección");
        alert.setHeaderText("Selección de carga");







        Label label = new Label("La selección que has hecho es:");

        TextFlow flow = new TextFlow();
        flow.getChildren().clear();
        flow.getChildren().addAll(
                textoEnNegrita("\nORÍGENES \n"),
                textoNormal(filtro.getOrigenes()),
                textoEnNegrita("\nDESTINOS \n"),
                textoNormal(filtro.getDestinos()),
                textoEnNegrita("\nDISPONIBILIDAD DESDE:\n"),
                textoNormal(filtro.getDisponibilidadDesde()),
                textoEnNegrita("\nDISPONIBILIDAD HASTA:\n"),
                textoNormal(filtro.getDisponibilidadHasta()),
                textoEnNegrita("\nES IDA Y VUELTA:\n"),
                textoNormal(filtro.getIdaYVuelta()),
                textoEnNegrita("\nNECESITA RAMPA:\n"),
                textoNormal(filtro.getConRampa()),
                textoEnNegrita("\nNÚMERO DE CONDUCTORES:\n"),
                textoNormal(filtro.getDobleConductor()),
                textoEnNegrita("\nES ADR:\n"),
                textoNormal(filtro.getEsAdr()),
                textoEnNegrita("\nES ATP:\n"),
                textoNormal(filtro.getEsAtp()),
                textoEnNegrita("\nES DE TIPO:\n"),
                textoNormal(filtro.getEsCompleta()),
                textoEnNegrita("\nTIENE LA NATURALEZA:\n"),
                textoNormal(filtro.getTieneNaturaleza())
        );

        GridPane.setVgrow(flow, Priority.ALWAYS);
        GridPane.setHgrow(flow, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(flow, 0, 1);

        alert.getDialogPane().setContent(expContent);
        alert.getDialogPane().setPrefSize(640, 480);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }
}
