package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.dao.*;
import es.israeldelamo.transportes.modelos.*;
import es.israeldelamo.transportes.utilidades.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static es.israeldelamo.transportes.utilidades.RellenaComboLocalizaciones.*;
import static es.israeldelamo.transportes.utilidades.Video.reproducirVideo;

/**
 * Esta clase es el controlador de JavaFX para el panel llamado Gerente
 *
 * @author israel
 * @version $Id: $Id
 */
public class GestionGerenteController {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(GestionGerenteController.class);


    /**
     * el código de gerente que viene de la ventana de login
     */
    private String elGerente;

    /**
     * Conjunto escenarios para recordar de donde vienen las pantallas
     */
    private Stage previousStage;
    @FXML
    private MenuItem mi_salir;
    /**
     * GridPane for creating loads.
     */
    @FXML
    private GridPane gridCreacionCargas;
    /**
     * Label for dangerous nature.
     */
    @FXML
    private Label lblNaturalezaPeligrosa;


    /// BLOQUE CARGA Y ENTREGA
    /**
     * Label para el titulo de la empresa
     */
    @FXML
    private Label lbl_titulo_empresa;
    /**
     * TableView for displaying load and delivery data.
     */
    @FXML
    private TableView<ModeloCargaYEntrega> tv_carga_entrega;
    /**
     * TableColumn for displaying the load code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_cod_carga;
    /**
     * TableColumn for displaying the pickup time slot.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Timestamp> tc_franja_horario_recogida;
    /**
     * TableColumn for displaying the delivery time slot.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Timestamp> tc_franja_horario_entrega;
    /**
     * TableColumn for displaying the delivery time slot.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Timestamp> tc_fecha_creaccion;
    /**
     * TableColumn for displaying the length dimension.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Float> tc_dimension_l;
    /**
     * TableColumn for displaying the width dimension.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Float> tc_dimension_a;
    /**
     * TableColumn for displaying the height dimension.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Float> tc_dimension_h;
    /**
     * TableColumn for displaying the unit weight.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Float> tc_peso_unitario;
    /**
     * TableColumn for displaying the load handling boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_manipulacion_carga_bool;
    /**
     * TableColumn for displaying the stackable boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_remontable;
    /**
     * TableColumn for displaying the dangerous nature boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_naturaleza_peligrosa;
    /**
     * TableColumn for displaying the package type.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_tipo_bulto;
    /**
     * TableColumn for displaying the double driver boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_doble_conductor;
    /**
     * TableColumn for displaying the full load or groupage boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_carga_completa_o_grupaje;
    /**
     * TableColumn for displaying the ramp requirement boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_necesita_rampa;
    /**
     * TableColumn for displaying the dangerous nature code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa;
    /**
     * TableColumn for displaying the origin country.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_pais_origen;
    /**
     * TableColumn for displaying the origin province.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_provincia_origen;
    /**
     * TableColumn for displaying the origin locality.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_localidad_origen;
    /**
     * TableColumn for displaying the origin postal code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_cp_origen;
    /**
     * TableColumn for displaying the destination country.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_pais_destino;
    /**
     * TableColumn for displaying the destination province.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_provincia_destino;
    /**
     * TableColumn for displaying the destination locality.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_localidad_destino;
    /**
     * TableColumn for displaying the destination postal code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_cp_destino;
    /**
     * TableColumn for displaying the instructions.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_instrucciones;
    /**
     * TableColumn for displaying the reimbursement amount.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_reembolso_cantidad;
    /**
     * TableColumn for displaying the return trip boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_con_vuelta;
    /**
     * TableColumn for displaying the price.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_precio;
    /**
     * TableColumn for displaying the insurance.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_seguro;
    /**
     * TableColumn for displaying the ADR compliance boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_adr;
    /**
     * TableColumn for displaying the ATP compliance boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_atp;
    /**
     * TableColumn for displaying the number of packages.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_cantidad_bultos;
    /**
     * TableColumn for displaying la empresa que creo la carga
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_empresa;
    /**
     * TableColumn for displaying si ya se ha servido o no
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_ya_servida;
    @FXML
    private Button botonRefresacar;
    /**
     * TextFields for inputting load and delivery details.
     */
    @FXML
    private TextField tf_codigo_carga_tab_carga_entrega,
            tf_franja_recogida_tab_carga_entrega,
            tf_franja_entrega_tab_carga_entrega,
            tf_dim_a_tab_carga_entrega,
            tf_dim_h_tab_carga_entrega,
            tf_dim_l_tab_carga_entrega,
            tf_peso_unidad_tab_carga_entrega,
            tf_tipo_bulto_tab_carga_entrega,
            tf_instrucciones_tab_carga_entrega,
            tf_reembolso_tab_carga_entrega,
            tf_precio_tab_carga_entrega,
            tf_seguro_tab_carga_entrega,
            tf_cantidad_bultos_carga_entrega;
    /**
     * DatePicker for selecting the start date of the load and delivery.
     */
    @FXML
    private DatePicker dp_fecha_creaccion;
    /**
     * DatePicker for selecting the start date of the load and delivery.
     */
    @FXML
    private DatePicker dp_dia_inicio_carga_entrega;
    /**
     * DatePicker for selecting the end date of the load and delivery.
     */
    @FXML
    private DatePicker dp_dia_fin_carga_entrega;
    /**
     * TextField for inputting the start time of the load and delivery.
     */
    @FXML
    private TextField tf_hora_inicio_carga_entrega;
    /**
     * TextField for inputting the end time of the load and delivery.
     */
    @FXML
    private TextField tf_hora_fin_carga_entrega;
    /**
     * CheckBoxes for various load and delivery options.
     */
    @FXML
    private CheckBox cxbx_manipulacion_carga_entrega,
            cxbx_grupaje_carga_entrega,
            cxbx_necesita_rampa_carga_entrega,
            cbx_remontable_carga_entrega,
            cxbx_dblconductor_carga_entrega,
            cxbx_adr_carga_entrega,
            cxbx_con_vuelta_carga_entrega,
            cxbx_atp_carga_entrega,
            cxbx_ya_servida_carga_entrega;
    /**
     * ComboBox for selecting the dangerous nature of the load.
     */
    @FXML
    private ComboBox<ModeloNaturalezasPeligrosas> combo_naturaleza_peligrosa_carga_entrega;
    /**
     * ComboBox for selecting the package type.
     */
    @FXML
    private ComboBox<ModeloTipoDeBulto> combo_tipo_bulto_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination countries.
     */
    @FXML
    private ComboBox<ModeloPais> combo_pais_origen_carga_entrega, combo_pais_destino_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination provinces.
     */
    @FXML
    private ComboBox<ModeloPaisYProvincia> combo_provincia_origen_carga_entrega, combo_provincia_destino_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination localities.
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidad> combo_localidad_origen_carga_entrega, combo_localidad_destino_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination postal codes.
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> combo_cp_origen_carga_entrega, combo_cp_destino_carga_entrega;
    /**
     *
     */
    private ModeloCargaYEntrega cargaYEntregaTemporalDesdeDobleClick;
    private ModeloPaisYProvinciaYLocalidadYCodigoPostal ambitoOrigenTemporal;
    private ModeloPaisYProvinciaYLocalidadYCodigoPostal ambitoDestinoTemporal;
    private ModeloEmpresa empresaTemporal;

    /**
     * Conjunto escenarios para recordar de donde vienen las pantallas
     */
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    /**
     * Volver a la pantalla anterior
     */
    @FXML
    void salir_app() {
        Stage currentStage = (Stage) mi_salir.getParentPopup().getOwnerWindow();
        currentStage.close();
        if (previousStage != null) {
            previousStage.show();
        }
    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada, el de provincia
     */
    @FXML
    void comb_pais_origen_cambio() {
        ambitoOrigenTemporal.setPais(combo_pais_origen_carga_entrega.getSelectionModel().getSelectedItem().getPais() == null ? "" : combo_pais_origen_carga_entrega.getSelectionModel().getSelectedItem().getPais());
        rellenaComboBoxProvinciasOrigenes();
    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada, el de provincia
     */
    @FXML
    void comb_pais_destino_cambio() {
        ambitoDestinoTemporal.setPais(combo_pais_destino_carga_entrega.getSelectionModel().getSelectedItem().getPais() == null ? "" : combo_pais_destino_carga_entrega.getSelectionModel().getSelectedItem().getPais());
        rellenaComboBoxProvinciasDestinos();
    }

    /**
     * Detecta el cambio de provincia y la establece en el temporal, refresca la de localidades
     */
    @FXML
    void comb_provincia_origen_cambio() {
        ambitoOrigenTemporal.setProvincia(combo_provincia_origen_carga_entrega.getSelectionModel().getSelectedItem().getProvincia());
        rellenaComboBoxLocalidadesOrigenes();
    }

    /**
     * Detecta el cambio de provincia y la establece en el temporal, refresca la de localidades
     */
    @FXML
    void comb_provincia_destino_cambio() {
        ambitoDestinoTemporal.setProvincia(combo_provincia_destino_carga_entrega.getSelectionModel().getSelectedItem().getProvincia());
        rellenaComboBoxLocalidadesDestinos();
    }

    /**
     * Detecta el cambio de localidad y la establece en el temporal, refresca la de localidades
     */
    @FXML
    void comb_localidad_origen_cambio() {
        ambitoOrigenTemporal.setLocalidad(combo_localidad_origen_carga_entrega.getSelectionModel().getSelectedItem().getLocalidad());
        rellenaComboBoxCPOrigen();
    }

    /**
     * Detecta el cambio de localidad y la establece en el temporal, refresca la de localidades
     */
    @FXML
    void comb_localidad_destino_cambio() {
        ambitoDestinoTemporal.setLocalidad(combo_localidad_destino_carga_entrega.getSelectionModel().getSelectedItem().getLocalidad());
        rellenaComboBoxCPDestino();
    }


    /**
     * Rellena toda la tabla Carga Y entrega
     */
    @FXML
    void mostrarTablaCargaYEntrega() {

/*   enesimo intento de poner colorines a la tabla
        tv_carga_entrega.setRowFactory(tv -> new TableRow<>() {

            protected void updateItem(ModeloCargaYEntrega item, boolean empty) {
                super.updateItem(item, empty);

                // Asegúrate de limpiar el estilo en filas vacías o nulas
                if (item == null || item.getYa_reservado() == null) {
                    setStyle("");
                    System.out.println("sin color");
                } else {
                    // Cambiar el color de fondo dependiendo de si está reservado o no
                    if (item.getYa_reservado()) {
                        setStyle("-fx-background-color: #baffba;");  // Color verde claro
                        System.out.println("verde");
                    } else {
                        setStyle("-fx-background-color: #ffd7d1;");  // Color rojo claro
                        System.out.println("rojo");
                    }
                }
            }
        });

*/


        tc_cod_carga.setCellValueFactory(new PropertyValueFactory<>("cod_carga"));
        tc_franja_horario_recogida.setCellValueFactory(new PropertyValueFactory<>("franja_horario_recogida"));
        tc_franja_horario_entrega.setCellValueFactory(new PropertyValueFactory<>("franja_horario_entrega"));
        tc_fecha_creaccion.setCellValueFactory(new PropertyValueFactory<>("fecha_creaccion"));

        tc_dimension_l.setCellValueFactory(new PropertyValueFactory<>("dimension_l"));
        tc_dimension_a.setCellValueFactory(new PropertyValueFactory<>("dimension_a"));
        tc_dimension_h.setCellValueFactory(new PropertyValueFactory<>("dimension_h"));
        tc_peso_unitario.setCellValueFactory(new PropertyValueFactory<>("peso_unitario"));


        //  tc_manipulacion_carga_bool.setCellValueFactory(new PropertyValueFactory<>("manipulacion_carga_bool"));;
        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_manipulacion_carga_bool, ModeloCargaYEntrega::getManipulacion_carga_bool);


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_remontable, ModeloCargaYEntrega::getRemontable);


        // Otro intento más de poner colorines, ver si servida, rojo sin servir
        //tc_es_naturaleza_peligrosa.setCellValueFactory(new PropertyValueFactory<>("es_naturaleza_peligrosa"));;
        //  tc_es_naturaleza_peligrosa.setCellValueFactory(cellData -> {
        //    ModeloCargaYEntrega p = cellData.getValue();
        //     return new ReadOnlyBooleanWrapper(p.getEs_naturaleza_peligrosa());
        //  });
        //  tc_es_naturaleza_peligrosa.setCellFactory(CheckBoxTableCell.forTableColumn(tc_es_naturaleza_peligrosa));


        tc_tipo_bulto.setCellValueFactory(new PropertyValueFactory<>("tipo_bulto"));


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_doble_conductor, ModeloCargaYEntrega::getDoble_conductor);


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_carga_completa_o_grupaje, ModeloCargaYEntrega::getCarga_completa_o_grupaje);


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_necesita_rampa, ModeloCargaYEntrega::getNecesita_rampa);

//devuelve la segunda columna en vez de la primera
        tc_NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            // Suponiendo que getModelosNaturalezaPeligrosa() es el procedimiento que devuelve la tabla ModelosNaturalezaPeligrosa
            Float codNaturalezaPeligrosa = p.getCod_naturaleza_peligrosa();
            String segundoParametro = DaoNaturalezaPeligrosa.devuelvemeLaNaturalezaPeligrosaPorCodigo(codNaturalezaPeligrosa);
            return new SimpleStringProperty(codNaturalezaPeligrosa.toString() + ", " + segundoParametro);
        });


        tc_pais_origen.setCellValueFactory(new PropertyValueFactory<>("pais_origen"));
        tc_provincia_origen.setCellValueFactory(new PropertyValueFactory<>("provincia_origen"));
        tc_localidad_origen.setCellValueFactory(new PropertyValueFactory<>("localidad_origen"));
        tc_cp_origen.setCellValueFactory(new PropertyValueFactory<>("cp_origen"));
        tc_pais_destino.setCellValueFactory(new PropertyValueFactory<>("pais_destino"));
        tc_provincia_destino.setCellValueFactory(new PropertyValueFactory<>("provincia_destino"));
        tc_localidad_destino.setCellValueFactory(new PropertyValueFactory<>("localidad_destino"));
        tc_cp_destino.setCellValueFactory(new PropertyValueFactory<>("cp_destino"));
        tc_instrucciones.setCellValueFactory(new PropertyValueFactory<>("instrucciones"));
        tc_reembolso_cantidad.setCellValueFactory(new PropertyValueFactory<>("reembolso_cantidad"));


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_con_vuelta, ModeloCargaYEntrega::getCon_vuelta);


        tc_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tc_seguro.setCellValueFactory(new PropertyValueFactory<>("seguro"));


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_es_adr, ModeloCargaYEntrega::getEs_adr);


        CargaEntregaUiConfigurer.configureBooleanCheckBox(tc_es_atp, ModeloCargaYEntrega::getEs_atp);

        tc_cantidad_bultos.setCellValueFactory(new PropertyValueFactory<>("cantidad_bultos"));


        tc_empresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));
/*
        //el fondo de la celda queda de color rojo o verde en función de si esta servido o no
        tc_ya_servida.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            if (p.getYa_reservado()) {
                tc_ya_servida.setStyle("-fx-background-color: #783F2D;");
            } else {
                tc_ya_servida.setStyle("-fx-background-color: #629134;"); // Reset the style
            }
            return new ReadOnlyBooleanWrapper(p.getYa_reservado());
        });
        tc_ya_servida.setCellFactory(CheckBoxTableCell.forTableColumn(tc_ya_servida));
*/
        CargaEntregaUiConfigurer.configureYaServidaColumn(tc_ya_servida);


        tv_carga_entrega.getItems().clear();
        // no necesito cargar nada hasta que no haya dado al botón de filtrar
        tv_carga_entrega.getItems().addAll(DaoCargaYEntrega.cargarListadoEntregaYCargaDeUnaSolaEmpresa(elGerente));


        tv_carga_entrega.setTableMenuButtonVisible(true);


    }

    /**
     * Rellena todos los campos de la pantalla leyendo la tableview y recuperando una línea.
     */

    private void funcionDobleClickTableViewCargaYEntrega() {
        tv_carga_entrega.setRowFactory(tv -> {
            TableRow<ModeloCargaYEntrega> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    cargaYEntregaTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_carga_tab_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getCod_carga()));
                    tf_dim_a_tab_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getDimension_a()));
                    tf_dim_h_tab_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getDimension_h()));
                    tf_dim_l_tab_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getDimension_l()));
                    tf_peso_unidad_tab_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getPeso_unitario()));


                    tf_instrucciones_tab_carga_entrega.setText(cargaYEntregaTemporalDesdeDobleClick.getInstrucciones());
                    tf_reembolso_tab_carga_entrega.setText(cargaYEntregaTemporalDesdeDobleClick.getReembolso_cantidad());
                    tf_precio_tab_carga_entrega.setText(cargaYEntregaTemporalDesdeDobleClick.getPrecio());
                    tf_seguro_tab_carga_entrega.setText(cargaYEntregaTemporalDesdeDobleClick.getSeguro());
                    tf_cantidad_bultos_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getCantidad_bultos()));

                    // Convertir el Timestamp a LocalDateTime
                    LocalDateTime localDateTimeRecogida = cargaYEntregaTemporalDesdeDobleClick.getFranja_horario_recogida().toLocalDateTime();
                    // Extraer la fecha del LocalDateTime
                    LocalDate fechaRecogida = localDateTimeRecogida.toLocalDate();
                    dp_dia_inicio_carga_entrega.setValue(fechaRecogida);
                    tf_hora_inicio_carga_entrega.setText(localDateTimeRecogida.getHour() + ":" + localDateTimeRecogida.getMinute());

                    // Convertir el Timestamp a LocalDateTime
                    LocalDateTime localDateTimeEntrega = cargaYEntregaTemporalDesdeDobleClick.getFranja_horario_entrega().toLocalDateTime();
                    // Extraer la fecha del LocalDateTime
                    LocalDate fechaEntrega = localDateTimeRecogida.toLocalDate();
                    dp_dia_fin_carga_entrega.setValue(fechaEntrega);
                    tf_hora_fin_carga_entrega.setText(localDateTimeEntrega.getHour() + ":" + localDateTimeEntrega.getMinute());

                    LocalDate fechaCreaccion = localDateTimeRecogida.toLocalDate();
                    //   LocalDateTime localDateTimeFechaCreaccion = cargaYEntregaTemporalDesdeDobleClick.getFecha_creaccion().toLocalDateTime();
                    dp_fecha_creaccion.setValue(fechaCreaccion);


                    cxbx_manipulacion_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getManipulacion_carga_bool());
                    cbx_remontable_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getRemontable());
                    cxbx_dblconductor_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getDoble_conductor());
                    // cxbx_peligrosidad_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getEs_naturaleza_peligrosa());
                    cxbx_adr_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getEs_adr());
                    cxbx_atp_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getEs_atp());
                    cxbx_grupaje_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getCarga_completa_o_grupaje());
                    cxbx_necesita_rampa_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getNecesita_rampa());
                    cxbx_con_vuelta_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getCon_vuelta());
                    cxbx_ya_servida_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getYa_reservado());

                    relleneComboBoxTipoDeBulto();
                    combo_tipo_bulto_carga_entrega.getSelectionModel().select(DaoTipoDeBulto.devuelvemeElTipoDeBultoCompletoPorCodigo(cargaYEntregaTemporalDesdeDobleClick.getTipo_bulto()));
                    // como devuelve un objeto, hay que convertirlo para que entre en un combobox
                    ModeloTipoDeBulto modeloTipoDeBultoLeido = DaoTipoDeBulto.devuelvemeElTipoDeBultoCompletoPorCodigo(cargaYEntregaTemporalDesdeDobleClick.getCod_carga());
                    if (modeloTipoDeBultoLeido != null) {
                        combo_tipo_bulto_carga_entrega.getSelectionModel().select(
                                new ModeloTipoDeBultoStringConverter().fromString(
                                        modeloTipoDeBultoLeido.getCod_tipo_bulto() + ", " + modeloTipoDeBultoLeido.getNombre_bulto()));
                    }


                    lbl_titulo_empresa.setText(
                            Objects.requireNonNull(DaoEmpresa.cargarUnaSolaEmpresas(cargaYEntregaTemporalDesdeDobleClick.getEmpresa())).getNombre());


                    // Rellenamos naturalezas peligrosas
                    //  combo_naturaleza_peligrosa_carga_entrega
                    combo_naturaleza_peligrosa_carga_entrega.getItems().clear();
                    ObservableList<ModeloNaturalezasPeligrosas> modeloNaturalezasPeligrosas = FXCollections.observableArrayList();
                    modeloNaturalezasPeligrosas.addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
                    combo_naturaleza_peligrosa_carga_entrega.setConverter(new StringConverter<ModeloNaturalezasPeligrosas>() {
                        @Override
                        public String toString(ModeloNaturalezasPeligrosas object) {
                            return object != null ? object.getCodigo_naturaleza_peligrosa() + ", " + object.getDescripcion_naturaleza_peligrosa() : "";
                        }

                        @Override
                        public ModeloNaturalezasPeligrosas fromString(String cadenaCompuestaDeNaturalezas) {

                            String[] parts = cadenaCompuestaDeNaturalezas.split(", ");
                            String codigo = parts[0].trim();
                            String descripcion = parts[1].trim();
                            return new ModeloNaturalezasPeligrosas(Float.parseFloat(codigo), descripcion);
                        }
                    });
                    combo_naturaleza_peligrosa_carga_entrega.getItems().addAll(modeloNaturalezasPeligrosas);
                    ModeloNaturalezasPeligrosas naturalezaPeligrosaLeida = DaoNaturalezaPeligrosa.devuelvemeLaNaturalezaPeligrosaPCompletaPorCodigo(cargaYEntregaTemporalDesdeDobleClick.getCod_naturaleza_peligrosa());
                    if (naturalezaPeligrosaLeida != null) {
                        combo_naturaleza_peligrosa_carga_entrega.getSelectionModel().select(
                                new NaturalezaPeligrosaStringConverter().fromString(
                                        naturalezaPeligrosaLeida.getCodigo_naturaleza_peligrosa() + ", " + naturalezaPeligrosaLeida.getDescripcion_naturaleza_peligrosa()));
                    }


                    //Rellenamos los paises y elegimos el seleccionado según
                    rellenarComboPaises(combo_pais_origen_carga_entrega, cargaYEntregaTemporalDesdeDobleClick.getPais_origen());
                    rellenarComboPaises(combo_pais_destino_carga_entrega, cargaYEntregaTemporalDesdeDobleClick.getPais_destino());


                    //Rellenamos los paises y provincias y elegimos el seleccionado según
                    rellenarComboProvincias(combo_provincia_origen_carga_entrega, cargaYEntregaTemporalDesdeDobleClick.getProvincia_origen(), cargaYEntregaTemporalDesdeDobleClick.getPais_origen());
                    rellenarComboProvincias(combo_provincia_destino_carga_entrega, cargaYEntregaTemporalDesdeDobleClick.getProvincia_destino(), cargaYEntregaTemporalDesdeDobleClick.getPais_destino());

                    //Rellenamos los paises, provincias y localidades. El seleccionado según
                    rellenarComboLocalidades(combo_localidad_origen_carga_entrega, cargaYEntregaTemporalDesdeDobleClick.getLocalidad_origen(), cargaYEntregaTemporalDesdeDobleClick.getProvincia_origen(), cargaYEntregaTemporalDesdeDobleClick.getPais_origen());
                    rellenarComboLocalidades(combo_localidad_destino_carga_entrega, cargaYEntregaTemporalDesdeDobleClick.getLocalidad_destino(), cargaYEntregaTemporalDesdeDobleClick.getProvincia_destino(), cargaYEntregaTemporalDesdeDobleClick.getPais_destino());


                    //Rellenamos los paises y localidades y provincias y elegimos el seleccionado según
                    combo_cp_origen_carga_entrega.getItems().clear();
                    ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> mcp = FXCollections.observableArrayList();
                    //que me devuelva solo las provincias del país, no todos los paises y provincias
                    mcp.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                            cargaYEntregaTemporalDesdeDobleClick.getPais_origen(),
                            cargaYEntregaTemporalDesdeDobleClick.getProvincia_origen(),
                            cargaYEntregaTemporalDesdeDobleClick.getLocalidad_origen()));
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    combo_cp_origen_carga_entrega.setConverter(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter());
                    combo_cp_origen_carga_entrega.getItems().addAll(mcp); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO LOCALIDAD, mientras que modeloConductor.getPais_conductor devuelve un string
                    //componemos una cadena con los datos del objeto para pasárselo a getSelectionModel
                    // la plantilla de la cadena es "Vitoria, (Álava) España"
                    String cpFormateadoOrigen =
                            cargaYEntregaTemporalDesdeDobleClick.getLocalidad_origen() + ", " +
                                    cargaYEntregaTemporalDesdeDobleClick.getProvincia_origen() + ", " +
                                    cargaYEntregaTemporalDesdeDobleClick.getPais_origen() + ", " +
                                    cargaYEntregaTemporalDesdeDobleClick.getCp_origen();
                    combo_cp_origen_carga_entrega.getSelectionModel().select(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter().fromString(cpFormateadoOrigen));


                    //Rellenamos los paises y localidades y provincias y elegimos el seleccionado según
                    combo_cp_destino_carga_entrega.getItems().clear();
                    ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> mcpDestino = FXCollections.observableArrayList();
                    //que me devuelva solo las provincias del país, no todos los paises y provincias
                    mcpDestino.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                            cargaYEntregaTemporalDesdeDobleClick.getPais_destino(),
                            cargaYEntregaTemporalDesdeDobleClick.getProvincia_destino(),
                            cargaYEntregaTemporalDesdeDobleClick.getLocalidad_destino()));
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    combo_cp_destino_carga_entrega.setConverter(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter());
                    combo_cp_destino_carga_entrega.getItems().addAll(mcp); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO LOCALIDAD, mientras que modeloConductor.getPais_conductor devuelve un string
                    //componemos una cadena con los datos del objeto para pasárselo a getSelectionModel
                    // la plantilla de la cadena es "Vitoria, (Álava) España"
                    String cpFormateadoDestino =
                            cargaYEntregaTemporalDesdeDobleClick.getLocalidad_destino() + ", " +
                                    cargaYEntregaTemporalDesdeDobleClick.getProvincia_destino() + ", " +
                                    cargaYEntregaTemporalDesdeDobleClick.getPais_destino() + ", " +
                                    cargaYEntregaTemporalDesdeDobleClick.getCp_destino();

                    combo_cp_destino_carga_entrega.getSelectionModel().select(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter().fromString(cpFormateadoDestino));

                }
            });
            return row;
        });
    }


    /**
     * Rellena los comboboxes de combo box una y otra vez, es un bloque refactorizado
     */
    private void relleneComboBoxTipoDeBulto() {
        combo_tipo_bulto_carga_entrega.getItems().clear();
        ObservableList<ModeloTipoDeBulto> modeloTipoDeBultos = FXCollections.observableArrayList();


        combo_tipo_bulto_carga_entrega.setCellFactory(param -> new ListCell<ModeloTipoDeBulto>() {
            private ImageView imageView = new ImageView();

            @Override
            protected void updateItem(ModeloTipoDeBulto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Blob blob = item.getImagen_bulto();
                    byte[] bytes = null;
                    try {
                        int blobLength = (int) blob.length();
                        bytes = blob.getBytes(1, blobLength);
                    } catch (SQLException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No he podido cargar imagen alguna para ese registro desde Base De Datos");
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos");
                    }

                    try {
                        Image imageDesdeBlob = new Image(new ByteArrayInputStream(Objects.requireNonNull(bytes)));
                        imageView = new ImageView(imageDesdeBlob);
                        imageView.setImage(imageDesdeBlob);
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        // alertaError.mostrarError(e.getMessage());
                        Image imagenPorDefecto = new Image(Objects.requireNonNull(Iniciografico.class.getResourceAsStream("imagenes/icono.png")));
                        imageView.setImage(imagenPorDefecto);
                    }

                    imageView.setFitHeight(50);  // Ajustar el tamaño de la imagen
                    imageView.setFitWidth(50);
                    setText(item.getCod_tipo_bulto() + " (" + item.getNombre_bulto() + ")");
                    //y añadimos la imagen leida del blob
                    setGraphic(imageView);
                }
            }
        });
        // También configurar el botón de selección del ComboBox
        combo_tipo_bulto_carga_entrega.setButtonCell(new ListCell<ModeloTipoDeBulto>() {
            @Override
            protected void updateItem(ModeloTipoDeBulto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getCod_tipo_bulto() + " (" + item.getNombre_bulto() + ")");
                }
            }
        });


        modeloTipoDeBultos.addAll(DaoTipoDeBulto.cargarListadTiposDeBultos());
        combo_tipo_bulto_carga_entrega.getItems().addAll(modeloTipoDeBultos);

    }

    /**
     * Rellena los comboboxes de combo box una y otra vez, es un bloque refactorizado
     */
    private void relleneComboBoxNaturalezaPeligrosa() {
        combo_naturaleza_peligrosa_carga_entrega.getItems().clear();
        ObservableList<ModeloNaturalezasPeligrosas> modeloNaturalezasPeligrosas = FXCollections.observableArrayList();
        modeloNaturalezasPeligrosas.addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
        combo_naturaleza_peligrosa_carga_entrega.getItems().addAll(modeloNaturalezasPeligrosas);
    }


    /**
     * Rellena los comboboxes de paises una y otra vez, es un bloque refactorizado
     */
    private void rellenaComboBoxesPaises() {

        combo_pais_origen_carga_entrega.getItems().clear();
        combo_pais_destino_carga_entrega.getItems().clear();
        ObservableList<ModeloPais> modelosDePaises = FXCollections.observableArrayList();
        modelosDePaises.addAll(DaoPais.cargarListadoPaises());
        combo_pais_destino_carga_entrega.setConverter(new PaisStringConverter());
        combo_pais_destino_carga_entrega.getItems().addAll(modelosDePaises);
        combo_pais_origen_carga_entrega.setConverter(new PaisStringConverter());
        combo_pais_origen_carga_entrega.getItems().addAll(modelosDePaises);

    }


    /**
     * En la inicialización, rellena los combo boxes con los datos de ejemplo
     */
    private void rellenaComboBoxProvinciasOrigenes() {
        combo_provincia_origen_carga_entrega.getItems().clear();
        ObservableList<ModeloPaisYProvincia> mppOrigen = FXCollections.observableArrayList();
        mppOrigen.addAll(DaoPaisYProvincia.cargarListadoProvincias(ambitoOrigenTemporal.getPais()));
        combo_provincia_origen_carga_entrega.setConverter(new PaisYProvinciaStringConverter());
        combo_provincia_origen_carga_entrega.getItems().addAll(mppOrigen);
    }

    /**
     * En la inicialización, rellena los combo boxes con los datos de ejemplo
     */
    private void rellenaComboBoxProvinciasDestinos() {
        combo_provincia_destino_carga_entrega.getItems().clear();
        ObservableList<ModeloPaisYProvincia> mppDestino = FXCollections.observableArrayList();
        mppDestino.addAll(DaoPaisYProvincia.cargarListadoProvincias(ambitoDestinoTemporal.getPais()));
        combo_provincia_destino_carga_entrega.setConverter(new PaisYProvinciaStringConverter());
        combo_provincia_destino_carga_entrega.getItems().addAll(mppDestino);
    }


    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxLocalidadesOrigenes() {
        combo_localidad_origen_carga_entrega.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidad> mlocalidadDestino = FXCollections.observableArrayList();
        mlocalidadDestino.addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades(ambitoOrigenTemporal.getPais(), ambitoOrigenTemporal.getProvincia()));
        combo_localidad_origen_carga_entrega.setConverter(new PaisYProvinciaLocalidadStringConverter());
        combo_localidad_origen_carga_entrega.getItems().addAll(mlocalidadDestino);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxLocalidadesDestinos() {
        combo_localidad_destino_carga_entrega.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidad> mlocalidadDestino = FXCollections.observableArrayList();
        mlocalidadDestino.addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades(ambitoDestinoTemporal.getPais(), ambitoDestinoTemporal.getProvincia()));
        combo_localidad_destino_carga_entrega.setConverter(new PaisYProvinciaLocalidadStringConverter());
        combo_localidad_destino_carga_entrega.getItems().addAll(mlocalidadDestino);
    }

    /**
     * Rellena cuando están vacías.
     */

    private void rellenaComboBoxCPDestino() {
        combo_cp_destino_carga_entrega.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> mcp = FXCollections.observableArrayList();
        mcp.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                ambitoDestinoTemporal.getPais(),
                ambitoDestinoTemporal.getProvincia(),
                ambitoDestinoTemporal.getLocalidad()
        ));
        combo_cp_destino_carga_entrega.setConverter(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter());
        combo_cp_destino_carga_entrega.getItems().addAll(mcp);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxCPOrigen() {
        combo_cp_origen_carga_entrega.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> mcp = FXCollections.observableArrayList();
        mcp.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                ambitoOrigenTemporal.getPais(),
                ambitoOrigenTemporal.getProvincia(),
                ambitoOrigenTemporal.getLocalidad()
        ));

        combo_cp_origen_carga_entrega.setConverter(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter());
        combo_cp_origen_carga_entrega.getItems().addAll(mcp);

    }


    /**
     * Este procedimiento es que activa o desactiva el bloque de naturalezas peligrosas en función ADR y AtP
     */

    @FXML
    private void clickEnAdr() {
        cxbx_adr_carga_entrega.setSelected(true);
        cxbx_atp_carga_entrega.setSelected(false);
        combo_naturaleza_peligrosa_carga_entrega.setDisable(false);
        lblNaturalezaPeligrosa.setDisable(false);
    }

    /**
     * Este procedimiento es que activa o desactiva el bloque de naturalezas peligrosas en función ADR y ATP
     */
    @FXML
    private void clickEnAtp() {
        cxbx_atp_carga_entrega.setSelected(true);
        cxbx_adr_carga_entrega.setSelected(false);
        combo_naturaleza_peligrosa_carga_entrega.setDisable(true);
        lblNaturalezaPeligrosa.setDisable(true);
    }


    /// /////////////////////////////////////////////////////////////////////////
    /// //////////////// ZONA DE ON BOTON  ///////////////////
    /// /////////////////////////////////////////////////////////////////////////

    @FXML
    private void onBotonSalir(ActionEvent event) {
        // Obtén el Stage actual desde el botón que disparó el evento.
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        // Verifica que previousStage no sea null antes de intentar mostrarlo.
        if (previousStage != null) {
            previousStage.show();
        }
    }


    /**
     * Elimina una línea de Carga y Entrega
     */
    @FXML
    private void onBotonBorrarCargaEntrega() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación de carga");
        alert.setHeaderText("Estás a punto de eliminar la carga seleccionada");
        alert.setContentText("¿Estás seguro de que deseas eliminar esa carga?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Si se pulsa el botón "Eliminar" (que es ButtonType.OK)
            if (tf_codigo_carga_tab_carga_entrega.getText() != null && DaoCargaYEntrega.eliminarModeloCargaYEntrega(tf_codigo_carga_tab_carga_entrega.getText())) {
                limpiarGridPane(gridCreacionCargas);
                Alertas informar = new Alertas();
                informar.mostrarInformacion("Carga borrada");
                logger.info("Carga eliminada");

            } else {
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("No hay nada que borrar, elige un registro a modificar pulsando doble click sobre él");
                logger.error("No se ha podido eliminar la carga seleccionada");
            }
        }
        //Foco inicial sobre el campo de nombre
        Platform.runLater(() -> tf_cantidad_bultos_carga_entrega.requestFocus());
        mostrarTablaCargaYEntrega();
    }


    /**
     * Con los datos que hay en la pantalla lanza una modificación de un dato
     */
    @FXML
    private void onBotonModificarCargaEntrega() {
        tv_carga_entrega.setVisible(true); //que se vea el panel
        ModeloCargaYEntrega cargaYEntrega = new ModeloCargaYEntrega(
                Integer.parseInt(tf_codigo_carga_tab_carga_entrega.getText()),

                ConversorDataPickerTimeStamp.DeStringATimeStamp(dp_dia_inicio_carga_entrega.getValue().toString() + " " + tf_hora_inicio_carga_entrega.getText()),
                ConversorDataPickerTimeStamp.DeStringATimeStamp(dp_dia_fin_carga_entrega.getValue().toString() + " " + tf_hora_fin_carga_entrega.getText()),

                Float.valueOf(tf_dim_l_tab_carga_entrega.getText()),
                Float.valueOf(tf_dim_a_tab_carga_entrega.getText()),
                Float.valueOf(tf_dim_h_tab_carga_entrega.getText()),
                Float.valueOf(tf_peso_unidad_tab_carga_entrega.getText()),

                cxbx_manipulacion_carga_entrega.isSelected(),
                cbx_remontable_carga_entrega.isSelected(),
                //  cxbx_peligrosidad_carga_entrega.isSelected(),

                combo_tipo_bulto_carga_entrega.getValue().getCod_tipo_bulto(),
                //   Integer.valueOf(tf_cantidad_bultos_carga_entrega.getText()),

                cxbx_dblconductor_carga_entrega.isSelected(),
                cxbx_grupaje_carga_entrega.isSelected(),
                cxbx_necesita_rampa_carga_entrega.isSelected(),

                combo_naturaleza_peligrosa_carga_entrega.getValue() == null ? 0 : combo_naturaleza_peligrosa_carga_entrega.getValue().getCodigo_naturaleza_peligrosa(),
                combo_localidad_origen_carga_entrega.getValue().getLocalidad(),
                combo_provincia_origen_carga_entrega.getValue().getProvincia(),
                combo_pais_origen_carga_entrega.getValue().getPais(),
                combo_cp_origen_carga_entrega.getValue().getCodigoPostal(),

                combo_localidad_destino_carga_entrega.getValue().getLocalidad(),
                combo_provincia_destino_carga_entrega.getValue().getProvincia(),
                combo_pais_destino_carga_entrega.getValue().getPais(),
                combo_cp_destino_carga_entrega.getValue().getCodigoPostal(),

                tf_instrucciones_tab_carga_entrega.getText(),
                tf_reembolso_tab_carga_entrega.getText(),
                cxbx_con_vuelta_carga_entrega.isSelected(),
                tf_precio_tab_carga_entrega.getText(),
                tf_seguro_tab_carga_entrega.getText(),
                cxbx_adr_carga_entrega.isSelected(),
                cxbx_atp_carga_entrega.isSelected(),
                null, // en esta pantalla no se indica si esta reservado o no
                Integer.valueOf(tf_cantidad_bultos_carga_entrega.getText()),
                ConversorDataPickerTimeStamp.DeStringATimeStamp(String.valueOf(dp_fecha_creaccion.getValue())),
                elGerente //para la ventana actual, sus operaciones son siempre de "elGerente"
        );


        if (DaoCargaYEntrega.modificarModeloCargaYEntrega(Integer.valueOf(tf_codigo_carga_tab_carga_entrega.getText()), cargaYEntrega)) {
            limpiarGridPane(gridCreacionCargas);
            logger.info("Modificación de carga");
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("Carga modificada con éxito");
            mostrarTablaCargaYEntrega();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige una Carga y Entrega a modificar pulsando doble click sobre él");
            logger.info("No se ha podido modificar la carga seleccionada");
        }
        mostrarTablaCargaYEntrega();
        tv_carga_entrega.setVisible(true);
    }

    /**
     * Mira todos los campos obligatorios o marcadas como asterisco uno por uno, si no tienen un valor válido devuelve false
     *
     * @return false si no tiene los campos ogligatorios rellenos
     */

    private boolean veririficarCamposObligatorios() {
        // Delegamos la validación al validador externo para reducir tamaño de esta clase
        return es.israeldelamo.transportes.utilidades.CargaEntregaValidator.validarCamposObligatorios(
                combo_tipo_bulto_carga_entrega,
                dp_dia_inicio_carga_entrega,
                combo_pais_destino_carga_entrega,
                combo_provincia_origen_carga_entrega,
                combo_pais_destino_carga_entrega,
                combo_provincia_destino_carga_entrega,
                combo_localidad_destino_carga_entrega,
                combo_cp_destino_carga_entrega,
                tf_cantidad_bultos_carga_entrega,
                tf_precio_tab_carga_entrega,
                tf_dim_a_tab_carga_entrega,
                tf_dim_h_tab_carga_entrega,
                tf_dim_l_tab_carga_entrega,
                tf_peso_unidad_tab_carga_entrega
        );
    }


    /**
     * Nuevo Carga y entrega para añadir carga y entrega, en la pantalla de ha renombrado como "DAR DE ALTA NUEVA CARGA"
     * Primero llama a verificarCamposObligatorios, luego crea un objeto y lo da de alta en la bbdd.
     */
    @FXML
    private void onBotonNuevaCargaEntrega() {
        if (veririficarCamposObligatorios()) {

            if (dp_dia_fin_carga_entrega.getValue() == null) {
                dp_dia_fin_carga_entrega.setValue(LocalDate.now());
            }


            //Creamos un objeto desde vacío

            ModeloCargaYEntrega modeloCargaYEntregaNuevo = new ModeloCargaYEntrega(
                    Integer.valueOf(tf_codigo_carga_tab_carga_entrega.getText()),
                    //hay que convertir las cadenas en timestamps,
                    ConversorDataPickerTimeStamp.DeStringATimeStamp(dp_dia_inicio_carga_entrega.getValue() == null ? "" : dp_dia_inicio_carga_entrega.getValue().toString() + " " + tf_hora_inicio_carga_entrega.getText()),
                    ConversorDataPickerTimeStamp.DeStringATimeStamp(dp_dia_fin_carga_entrega.getValue() == null ? "" : dp_dia_fin_carga_entrega.getValue().toString() + " " + tf_hora_fin_carga_entrega.getText()),

                    Float.valueOf(tf_dim_l_tab_carga_entrega.getText()), //imposible que este vacio
                    Float.valueOf(tf_dim_a_tab_carga_entrega.getText()), //imposible que este vacio
                    Float.valueOf(tf_dim_h_tab_carga_entrega.getText()), //imposible que este vacio
                    Float.valueOf(tf_peso_unidad_tab_carga_entrega.getText()), //imposible que este vacio

                    //hay campos que podrían ir vacios, pondremos un valor predeterminado
                    cxbx_manipulacion_carga_entrega.isSelected(),
                    cbx_remontable_carga_entrega.isSelected(),

                    combo_tipo_bulto_carga_entrega.getValue().getCod_tipo_bulto(),


                    cxbx_dblconductor_carga_entrega.isSelected(),

                    cxbx_grupaje_carga_entrega.isSelected(),
                    cxbx_necesita_rampa_carga_entrega.isSelected(),
                    combo_naturaleza_peligrosa_carga_entrega.getValue() == null ? 0 : combo_naturaleza_peligrosa_carga_entrega.getValue().getCodigo_naturaleza_peligrosa(),


                    // combo_localidad_origen_carga_entrega.getValue().getLocalidad(),  puede ser que no haya elegido nada, asi que no le ponemos localidad
                    combo_localidad_origen_carga_entrega.getValue() == null ? "LOCALIDADINDETERMINADA" : combo_localidad_origen_carga_entrega.getValue().getLocalidad(),


                    combo_provincia_origen_carga_entrega.getValue().getProvincia(),
                    combo_pais_origen_carga_entrega.getValue().getPais(),
                    // este campo puede no estar relleno, pues no es obligatorio
                    // combo_naturaleza_peligrosa_carga_entrega.getValue() == null ? 0 : combo_naturaleza_peligrosa_carga_entrega.getValue().getCodigo_naturaleza_peligrosa(),
                    combo_cp_origen_carga_entrega.getValue() == null ? 0 : combo_cp_origen_carga_entrega.getValue().getCodigoPostal(),

                    combo_localidad_destino_carga_entrega.getValue().getLocalidad(),
                    combo_provincia_destino_carga_entrega.getValue().getProvincia(),
                    combo_pais_destino_carga_entrega.getValue().getPais(),
                    combo_cp_destino_carga_entrega.getValue().getCodigoPostal(),
                    tf_instrucciones_tab_carga_entrega.getText(),
                    tf_reembolso_tab_carga_entrega.getText(),
                    cxbx_con_vuelta_carga_entrega.isSelected(),
                    tf_precio_tab_carga_entrega.getText(),
                    tf_seguro_tab_carga_entrega.getText(),
                    cxbx_adr_carga_entrega.isSelected(),
                    cxbx_atp_carga_entrega.isSelected(),
                    cxbx_ya_servida_carga_entrega.isSelected(), // en esta pantalla no se indica si esta reservado o no
                    Integer.valueOf(tf_cantidad_bultos_carga_entrega.getText()),
                    Timestamp.valueOf(LocalDate.now().atStartOfDay()),
                    elGerente
            );
            //Llamamos al DAO y lo metemos
            // DaoCargaYEntrega daoCargaYEntrega = new DaoCargaYEntrega();
            //todo no permitir valores malos en la creación
            if (DaoCargaYEntrega.nuevoModeloCargaYEntrega(modeloCargaYEntregaNuevo) && tf_codigo_carga_tab_carga_entrega.getText() != null) {
                // éxito en la actualización, toca refrescar tabla
                onBotonLimpiarCargaEntrega();
                mostrarTablaCargaYEntrega();


            } else {
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando doble click sobre él");
                //TODO asegurarnos que se este modificando algo, y no se haya pulsado sin más al botón modificar
            }
            tv_carga_entrega.setVisible(true); //que se vea el panel
        }//fin de la validaciónde campos obligoatorios
    }

    /**
     * Limpia todos los campos de Carga Y Entrega
     */
    @FXML
    private void onBotonLimpiarCargaEntrega() {
        tv_carga_entrega.setVisible(false);
        limpiarGridPane(gridCreacionCargas);
        //después de limpiar, hay que inicializar los desplegables y dejarlo listo para crear uno nuevo
        //ahora carga el listado de paises en el ComboBox
        rellenaComboBoxesPaises();

        rellenaComboBoxProvinciasOrigenes();
        rellenaComboBoxProvinciasDestinos();

        rellenaComboBoxLocalidadesOrigenes();
        rellenaComboBoxLocalidadesDestinos();

        rellenaComboBoxCPDestino();
        rellenaComboBoxCPOrigen();

        // autocompletar el código de carga y entrega con un número más alto que el más alto de los que existe en la base de datos +1
        tf_codigo_carga_tab_carga_entrega.setText(String.valueOf(1 + DaoCargaYEntrega.buscar_cod_carga_mas_alto()));


    }


    /**
     * A petición del cliente, un botón que refresque la pantalla
     */

    @FXML
    private void onBotonRefrescar() {
        tv_carga_entrega.refresh();
    }


    /**
     * Recorre un GridPane y lo deja limpio
     *
     * @param gridALimpiar el gridPane a limpiar
     */


    private void limpiarGridPane(GridPane gridALimpiar) {
        tv_carga_entrega.setVisible(false);
        for (Node node : gridALimpiar.getChildren()) {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
        }
        for (Node node : gridALimpiar.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
        }
        /*for (Node node : gridALimpiar.getChildren()) {
            if (node instanceof ComboBox<?>) {
                ((ComboBox) node).setValue(null);
            }
        }*/
        dp_dia_inicio_carga_entrega.setValue(LocalDate.now());
        dp_dia_fin_carga_entrega.setValue(LocalDate.now());
        tf_hora_inicio_carga_entrega.setText("12:00");
        tf_hora_fin_carga_entrega.setText("12:00");

    }


    /**
     * {@inheritDoc}
     * <p>
     * Inicialización de clase
     */

    public void initialize(String elUsuario) {


        // Mantener ligera la inicialización para no bloquear la apertura de la ventana.
        // Evitar consultas a BBDD aquí; cargar bajo demanda cuando el usuario lo necesite.
        ambitoOrigenTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();
        ambitoDestinoTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();

        // Deferir una carga inicial para que el usuario vea datos al abrir sin bloquear el primer render.
        // Nota: Esto ejecuta en el hilo JavaFX tras mostrarse la ventana; si estas operaciones tardan, se recomienda
        // migrarlas a un Task en segundo plano. Aquí priorizamos restaurar la visibilidad de datos al abrir.
        Platform.runLater(() -> {
            try {
                relleneComboBoxNaturalezaPeligrosa();
                // pero solo la tuya, la del quien ha iniciado sesión
                lbl_titulo_empresa.setText(Objects.requireNonNull(DaoEmpresa.cargarUnaSolaEmpresas(elGerente)).getNombre());

                rellenaComboBoxesPaises();
                relleneComboBoxTipoDeBulto();
                // mostrar datos principales del Gestor
                mostrarTablaCargaYEntrega();
                tv_carga_entrega.setVisible(true);
                // autocompletar el código de carga y entrega con el más alto + 1
                tf_codigo_carga_tab_carga_entrega.setText(String.valueOf(1 + DaoCargaYEntrega.buscar_cod_carga_mas_alto()));
            } catch (Exception ex) {
                Logger logger = LoggerFactory.getLogger(GestionGerenteController.class);
                logger.error("Error en carga inicial diferida de Gestor", ex);
                Alertas alerta = new Alertas();
                alerta.mostrarError("No se pudieron cargar los datos iniciales del Gestor: " + ex.getMessage());
            }
        });

        // Mantener solo configuración de UI rápida
        funcionDobleClickTableViewCargaYEntrega();

        // Cálculos o lecturas rápidas sin BBDD
        // tf_codigo_carga_tab_carga_entrega.setText(String.valueOf(1 + DaoCargaYEntrega.buscar_cod_carga_mas_alto()));

        botonRefresacar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F5) {
                tv_carga_entrega.refresh();
                logger.info("Petición de refresco de tabla cargas");
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("Tabla actualizada");
            }

        });
    }

    /// /////////////////////////////////////////////////////
    /// //////////////// ZONA DE AYUDA  /////////////////
    /// ///////////////////////////////////////////////////

    @FXML
    private void lanzarVentanaAyuda() {
       /* FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/botoneraManuales.fxml"),bundle);
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            // nuevo escenario de tipo modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //Asignamos el owner de la nueva ventana
            stage.initOwner(this.tv_carga_entrega.getScene().getWindow());
            // esto hace que IntelliJ sepa donde está la imagen en función de la raíz del proyecto
            // en otros ides, por ejemplo, visual studio, no va a funcionar por cómo se hace referencia a la raíz de user.dir

            Image icono = new Image(String.valueOf(getClass().getResource( "/es/israeldelamo/transportes/imagenes/icono.png")));
            stage.getIcons().add(icono);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lanzador de manuales");
            stage.showAndWait();
            //refresco cuando vuelva, no seá que se hayan cargado nuevos elementos

        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Error al cargar la ventana de botonera de manuales");
            alerta.mostrarError(e.getMessage());
            logger.error("Error al cargar la ventana de botonera de manuales");
        }
*/

        //aprovechando un componente de la ventana cualquiera
        //recupero a su padre que es un stage
        Stage currentStage = (Stage) mi_salir.getParentPopup().getOwnerWindow();
        reproducirVideo("gestor", currentStage);

    }


    /**
     * Usado para pasar un parámetro desde la ventana que lo ha llamado
     *
     * @param parametro el dato que quiero pasar
     */
    public void pasoDeParametros(String parametro) {
        elGerente = parametro;
    }

}
