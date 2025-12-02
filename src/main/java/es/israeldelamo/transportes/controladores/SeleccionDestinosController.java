package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.dao.DaoPaisYProvinciaYLocalidadYCodigoPostal;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Esta clase se usa para seleccionar entradas de Pais, provincia y Codigo postal sin importar que existan en envios o en
 * cualquier otro sitio
 * <p>
 * Accede a la tabla pública del padre para rellenar la información de vuelta.
 *
 * @author israel
 * @version $Id: $Id
 */
public class SeleccionDestinosController implements Initializable {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(SeleccionDestinosController.class);

    private ModeloPaisYProvinciaYLocalidadYCodigoPostal paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick;


    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_codigpostal_tv_origenes;
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_pais_tv_origenes;
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_localidad_tv_origenes;
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_provincia_tv_origenes;

    @FXML
    private TableView<ModeloPaisYProvinciaYLocalidadYCodigoPostal> tv_origenes;

    /**
     * Cierra la ventana modal de manera ordenada
     */
    @FXML
    void cerrarVentana() {
        Stage stage = (Stage) tv_origenes.getScene().getWindow();
        stage.close();
    }


    /**
     * Rellena la tabla de paises provincias y codigos postales con el dao de PaisesProvinciasYCodigosPostales
     */
    @FXML
    void mostrarTablaPaisesYProvinciasYCodigosPostales() {
        tc_codigpostal_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        tc_localidad_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tc_provincia_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_pais_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tv_origenes.getItems()
                .clear();
        tv_origenes.getItems()
                .addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostales());
        tv_origenes.refresh();

    }


    /**
     * Función que añade el doble clic a la tableview paises y provincias y codigos postales
     */
    private void funcionDobleClickParaTableViewPaisesYProvinciasYLocalidadYCodigosPostales() {
        tv_origenes.setRowFactory(tv -> {
            TableRow<ModeloPaisYProvinciaYLocalidadYCodigoPostal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick = row.getItem();

                    //ahora hay que pasarla a la tabla GestionVehiculoController
                    GestionVehiculoController.daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados
                            .add(new ModeloPaisYProvinciaYLocalidadYCodigoPostal(
                                    paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getCodigoPostal(),
                                    paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getLocalidad(),
                                    paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getProvincia(),
                                    paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getPais()));

                    cerrarVentana();
                }
            });
            return row;
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mostrarTablaPaisesYProvinciasYCodigosPostales();
        funcionDobleClickParaTableViewPaisesYProvinciasYLocalidadYCodigosPostales();
    }

}
