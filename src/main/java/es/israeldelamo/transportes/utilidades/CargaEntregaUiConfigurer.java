package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloCargaYEntrega;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.util.function.Function;

/**
 * Utilidades para configurar tablas relacionadas con Carga y Entrega.
 */
public final class CargaEntregaUiConfigurer {

    private CargaEntregaUiConfigurer() {}

    /**
     * Configura una columna booleana para mostrarse como CheckBox.
     *
     * @param column columna booleana de la tabla
     * @param getter función que obtiene el valor booleano del item de la fila
     * @param <T> tipo de fila
     */
    public static <T> void configureBooleanCheckBox(TableColumn<T, Boolean> column, Function<T, Boolean> getter) {
        column.setCellValueFactory(cellData -> new ReadOnlyBooleanWrapper(Boolean.TRUE.equals(getter.apply(cellData.getValue()))));
        column.setCellFactory(CheckBoxTableCell.forTableColumn(column));
    }

    /**
     * Configura la columna "ya servida" con color de fondo verde/rojo y un CheckBox indicativo.
     */
    public static void configureYaServidaColumn(TableColumn<ModeloCargaYEntrega, Boolean> column) {
        // ValueFactory
        column.setCellValueFactory(cellData -> new ReadOnlyBooleanWrapper(Boolean.TRUE.equals(cellData.getValue().getYa_reservado())));

        // CellFactory con color de fondo y checkbox
        column.setCellFactory(col -> new TableCell<ModeloCargaYEntrega, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("");
                    setGraphic(null);
                } else {
                    if (item) {
                        setStyle("-fx-background-color: #629134;"); // verde
                    } else {
                        setStyle("-fx-background-color: #783F2D;"); // rojo
                    }
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        });
    }
}
