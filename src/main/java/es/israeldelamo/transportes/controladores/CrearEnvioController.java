package es.israeldelamo.transportes.controladores;


import es.israeldelamo.transportes.dao.*;
import es.israeldelamo.transportes.modelos.*;
import es.israeldelamo.transportes.utilidades.Alertas;
import es.israeldelamo.transportes.utilidades.EnvioCorreos;
import es.israeldelamo.transportes.utilidades.VehiculoConverter;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

import static es.israeldelamo.transportes.utilidades.LanzadorDeInformes.lanzadorDeInformes;

/**
 * Clase de gestion del FXML Crear envío
 *
 * @author israel
 * @version $Id: $Id
 */
public class CrearEnvioController {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(CrearEnvioController.class);
    /**
     * El conductor cargado en función del dnipasadoDesdeGestionVehiculo y que usaremos en toda la escena
     */
    private static ModeloConductor elConductorCargado;
    /**
     * Viene desde la selección del combo box de selección vehículo
     */
    private static ModeloVehiculo elVehiculoQueHeLeido;
    /// / TABLA DE CARGAS Y ENTREGAS
    @FXML
    private TableView<ModeloCargaYEntrega> tvCargasYEntregas;
    /**
     * TableColumn for the code of the load.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_cod_carga;
    /**
     * TableColumn for the pickup time slot.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Date> tc_franja_horario_recogida;
    /**
     * TableColumn for the delivery time slot.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Date> tc_franja_horario_entrega;
    /**
     * TableColumn for the length, width, and height dimensions.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_dimension_l, tc_dimension_a, tc_dimension_h;
    /**
     * TableColumn for the unit weight.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_peso_unitario;
    /**
     * TableColumn for the load handling boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_manipulacion_carga_bool;
    /**
     * TableColumn for the stackable boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_remontable;
    /**
     * TableColumn for the dangerous nature boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_naturaleza_peligrosa;
    /**
     * TableColumn for the type of package.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_tipo_bulto;
    /**
     * TableColumn for the double driver boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_doble_conductor;
    /**
     * TableColumn for the full load or groupage boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_carga_completa_o_grupaje;
    /**
     * TableColumn for the ramp requirement boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_necesita_rampa;
    /**
     * TableColumn for the dangerous goods code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa;
    /**
     * TableColumn for the origin country.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_pais_origen;
    /**
     * TableColumn for the origin province.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_provincia_origen;
    /**
     * TableColumn for the origin locality.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_localidad_origen;
    /**
     * TableColumn for the origin postal code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_cp_origen;
    /**
     * TableColumn for the destination country.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_pais_destino;
    /**
     * TableColumn for the destination province.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_provincia_destino;
    /**
     * TableColumn for the destination locality.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_localidad_destino;
    /**
     * TableColumn for the destination postal code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_cp_destino;
    /**
     * TableColumn for the instructions.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_instrucciones;
    /**
     * TableColumn for the reimbursement amount.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_reembolso_cantidad;
    /**
     * TableColumn for the return trip boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_con_vuelta;
    /**
     * TableColumn for the price.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_precio;
    /**
     * TableColumn for the insurance.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_seguro;
    /**
     * TableColumn for the ADR boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_adr;


///// BLOQUE VEHICULO
    /**
     * TableColumn for the ATP boolean.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_atp;
    /**
     * TableColumn for the number of packages.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Integer> tc_cantidad_bultos;
    /**
     * ComboBox for selecting a vehicle.
     */
    @FXML
    private ComboBox<ModeloVehiculo> cbx_elegir_vehiculo;
    /**
     * TextField for the vehicle code.
     */
    @FXML
    private TextField tf_cod_vehiculo_vehiculo;
    /**
     * TextField para las instrucciones especiales del vehículo
     */
    @FXML
    private TextField tf_instrucciones_vehiculos;
    /**
     * TextField for the maximum authorized mass (MMA) of the vehicle.
     */
    @FXML
    private TextField tf_mma_vehiculo;
    /**
     * TextField for the width dimension of the vehicle.
     */
    @FXML
    private TextField tf_dim_a_vehiculo;
    /**
     * TextField for the length dimension of the vehicle.
     */
    @FXML
    private TextField tf_dim_l_vehiculo;
    /**
     * TextField for the height dimension of the vehicle.
     */
    @FXML
    private TextField tf_dim_h_vehiculo;
    /**
     * TextField for the payload capacity of the vehicle.
     */
    @FXML
    private TextField tf_carga_util_vehiculo;
    /**
     * TextField for the stipulation of the vehicle.
     */
    @FXML
    private TextField tf_estipulacion_vehiculo;
    /**
     * CheckBox for indicating if the vehicle has a top load.
     */
    @FXML
    private CheckBox cb_carga_superior_vehiculos;
    /**
     * CheckBox for indicating if the vehicle has a side load.
     */
    @FXML
    private CheckBox cb_carga_lateral_vehiculos;
    /**
     * CheckBox for indicating if the vehicle has a LOLO (Lift On Lift Off) system.
     */
    @FXML
    private CheckBox cb_lolo_vehiculos;
    /**
     * CheckBox for indicating if the vehicle has a RORO system.
     */
    @FXML
    private CheckBox cb_roro_vehiculos;
    /**
     * CheckBox for indicating if the vehicle has a ramp.
     */
    @FXML
    private CheckBox cb_rampa_vehiculos;


///// BLOQUE DEL CONDUCTOR
    /**
     * CheckBox for indicating if the vehicle is ADR compliant.
     */
    @FXML
    private CheckBox cb_adr_vehiculos;
    /**
     * CheckBox for indicating if the vehicle is ATP compliant.
     */
    @FXML
    private CheckBox cb_atp_vehiculos;
    /**
     * TextField for the driver's DNI.
     */
    @FXML
    private TextField tf_dni_conductor;
    /**
     * TextField for the driver's first name.
     */
    @FXML
    private TextField tf_nombre_conductor;
    /**
     * TextField for the driver's last name.
     */
    @FXML
    private TextField tf_apellido_conductor;
    /**
     * TextField for the driver's country.
     */
    @FXML
    private TextField tf_pais_conductor;
    /**
     * TextField for the driver's province.
     */
    @FXML
    private TextField tf_provincia_conductor;
    /**
     * TextField for the driver's locality.
     */
    @FXML
    private TextField tf_localidad_conductor;
    /**
     * TextField for the driver's postal code.
     */
    @FXML
    private TextField tf_cp_conductor;
    /**
     * TextField for the driver's contact information.
     */
    @FXML
    private TextField tf_contacto_conductor;
    /**
     * TextField for the driver's email address.
     */
    @FXML
    private TextField tf_correo_conductor;
    /**
     * TextField for the driver's license type.
     */
    @FXML
    private TextField tf_tipo_carnet;
    /**
     * CheckBox for indicating if the driver is international.
     */
    @FXML
    private CheckBox cb_internacional_conductor;
    /**
     * TextField for the driver's phone number.
     */
    @FXML
    private TextField tf_telefono_conductor;
    /**
     * ImageView for the driver's photo.
     */
    @FXML
    private ImageView img_foto_conductor;
    /**
     * Lo usamos como parámetro del SQL que recibiremos desde la otra clase
     */
    private String elFiltroSQLRecibido;
    /**
     * El dni del conductor que usaremos durante toda la pantalla
     */
    private String dniPasadoDesdeGestionVehiculos;
    /**
     * Los datos en SQL de lo que ha elegido el conductor en su filtro anterior
     */
    private String cadenPasadaDesdeGestionVehiculos;

    /**
     * El conductor elegido viene como parámetro desde la ventana anterior GESTION DE VEHÍCULO. En función de lo que hayamos elegido recuperaremos
     * un dni global u otro
     *
     * @param elConductor el conductor elegido para ser filtrado
     */
    public static void cargarElConductorElegido(String elConductor) {

        elConductorCargado = DaoConductor.cargarUnConductorConcreto(elConductor).get(0);
        //getFirst();
    }


    /**
     * Función de doble clic sobre la table view de Cargas posibles, es la que generara la entrada en la tabla envíos.
     * La idea es generar una entrada de SQL nueva en la tabla ENVIOS, hacer desaparecer la entrada marcada y mostrar un informe PDF con la
     * entrada creada
     */

    private void funcionDobleClickParaTableViewCargaYEntrega() {

        tvCargasYEntregas.setRowFactory(tv -> {
            TableRow<ModeloCargaYEntrega> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {

                    //Ahora que ha seleccionado algo, le cambiaremos el flag de ya_servido
                    // lo dejaremos de mostrar en todas las tablas
                    //Pero antes, habrá que pedir confirmación
                    Alert alertaDeConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);

                    alertaDeConfirmacion.setHeaderText("Confirma la siguiente petición");
                    alertaDeConfirmacion.setTitle("Estos son los filtros que has elegido");
                    //deshacemos el string builder
                    alertaDeConfirmacion.setContentText(row.getItem().toStringParaAlertas());


                    ButtonType buttonTypePatata = new ButtonType("Confirmar esta carga", ButtonBar.ButtonData.OK_DONE);
                    alertaDeConfirmacion.getButtonTypes().setAll(buttonTypePatata, ButtonType.CANCEL);

                    Optional<ButtonType> resultado = alertaDeConfirmacion.showAndWait();

                    if (resultado.isPresent() && resultado.get() == ButtonType.CANCEL) {
                        Alertas alerta = new Alertas();
                        alerta.mostrarAviso("Reserva cancelada");


                    } else {
                        logger.info("Pasamos a la sección de ENVIO");
                        logger.info("EL conductor: {}", elConductorCargado.getDni());
                        logger.info("EL vehiculo: {}", elVehiculoQueHeLeido.getCod_vehiculo());
                        logger.info("la lista leida: {}", row.getItem().getCod_carga());


                        // como vamos a crear un nuevo envío, hay que pasarle sus campos,
                        // el campo tracker es un varchar, podría haber sido un incremental, pero lo vamos a hacer con la marca de tiempo timestamp,
                        // es imposible que exista dos entradas en el mismo momento con precision de segundos, no está pensado para esa carga de usuarios concurrentes
                        Instant instant = Instant.now();
                        long timestampSegundos = instant.getEpochSecond();


                        realizarReserva(elConductorCargado, elVehiculoQueHeLeido, row.getItem(), String.valueOf(timestampSegundos));

                        //llama a la pantalla de generación del PDF ticket de lo comprado
                        logger.info("Mostrando informe con el código :{}", timestampSegundos);
                        lanzarInformePDFConLaReservaHecha(String.valueOf(timestampSegundos));
                        // EnvioCorreos.lanzaUnCorreoDeNoCargas(elConductorCargado.getDni(), "Se he encontrado envios disponibles");
                        // A lo mejor interesa enviar un correo de aviso
                        EnvioCorreos.lanzaUnCorreoDeCargas(elConductorCargado.getDni(), "Se le ha asigando una carga");

                        //actualiza la tabla borrando esa entrada
                        mostrarTablaCargarYEntregas();
                        logger.info("Tabla refrescada");
                    }
                }
            });

            return row;
        });

    }

    /**
     * En función del código de la carga, se lee la tabla de ENVIOS y se muestra una entrada de PDF
     * Para eso usaremos una llamada a JasperReports y le pasaremos ese código de carga
     *
     * @param elCodigoDeLaCargaReservada es el código único del envío recién creado
     */
    private void lanzarInformePDFConLaReservaHecha(String elCodigoDeLaCargaReservada) {
        logger.info("ENTRANDO EN JASPER REPORTS para el codigo de carga: {}", elCodigoDeLaCargaReservada);
        lanzadorDeInformes("EnvioUnico", elCodigoDeLaCargaReservada);
    }


    /**
     * Este procedimiento crea una entrada en la tabla ENVÍO a partir de los parámetros que le pasamos más un timestamp como clave primaria
     *
     * @param elConductor    el conductor elegido
     * @param elVehiculo     el vehículo elegido
     * @param laCarga        la carga elegida
     * @param codigoTracking id único basado en los segundos del sistema
     */
    public void realizarReserva(ModeloConductor elConductor, ModeloVehiculo elVehiculo, ModeloCargaYEntrega laCarga, String codigoTracking) {


        ModeloEnvio modeloEnvio = new ModeloEnvio(codigoTracking, laCarga.getCod_carga(), elConductor.getDni(), elVehiculo.getCod_vehiculo());
        DaoEnvio.nuevoEnvio(modeloEnvio);
        // una vez hecha la inserción en envío, hay que marcar esa carga y envío como ocupada
        DaoCargaYEntrega.marcarComoPedido(String.valueOf(laCarga.getCod_carga()), laCarga.getCp_destino());

        mostrarTablaCargarYEntregas();

    }


    /**
     * Rellena la carga de tablas y entregas. Primero tenemos el dni, luego el vehículo, y ahora hay que rellenarlo.
     */

    void mostrarTablaCargarYEntregas() {
        // DaoCargaYEntrega daoCargaYEntrega = new DaoCargaYEntrega();
        tc_cod_carga.setCellValueFactory(new PropertyValueFactory<>("cod_carga"));
        tc_franja_horario_recogida.setCellValueFactory(new PropertyValueFactory<>("franja_horario_recogida"));
        tc_franja_horario_entrega.setCellValueFactory(new PropertyValueFactory<>("franja_horario_entrega"));
        tc_dimension_l.setCellValueFactory(new PropertyValueFactory<>("dimension_l"));
        tc_dimension_a.setCellValueFactory(new PropertyValueFactory<>("dimension_a"));
        tc_dimension_h.setCellValueFactory(new PropertyValueFactory<>("dimension_h"));
        tc_peso_unitario.setCellValueFactory(new PropertyValueFactory<>("peso_unitario"));


        tc_manipulacion_carga_bool.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getManipulacion_carga_bool());
        });
        tc_manipulacion_carga_bool.setCellFactory(CheckBoxTableCell.forTableColumn(tc_manipulacion_carga_bool));


        tc_remontable.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getRemontable());
        });
        tc_remontable.setCellFactory(CheckBoxTableCell.forTableColumn(tc_remontable));


        tc_tipo_bulto.setCellValueFactory(new PropertyValueFactory<>("tipo_bulto"));


        tc_doble_conductor.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getDoble_conductor());
        });
        tc_doble_conductor.setCellFactory(CheckBoxTableCell.forTableColumn(tc_doble_conductor));


        tc_carga_completa_o_grupaje.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getCarga_completa_o_grupaje());
        });
        tc_carga_completa_o_grupaje.setCellFactory(CheckBoxTableCell.forTableColumn(tc_carga_completa_o_grupaje));


        tc_necesita_rampa.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getNecesita_rampa());
        });
        tc_necesita_rampa.setCellFactory(CheckBoxTableCell.forTableColumn(tc_necesita_rampa));


        tc_NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            // Suponiendo que getModelosNaturalezaPeligrosa() es el procedimiento que devuelve la tabla ModelosNaturalezaPeligrosa
            Float codNaturalezaPeligrosa = p.getCod_naturaleza_peligrosa();
            // DaoNaturalezaPeligrosa daoNaturalezaPeligrosa = new DaoNaturalezaPeligrosa();
            String segundoParametro = DaoNaturalezaPeligrosa.devuelvemeLaNaturalezaPeligrosaPorCodigo(codNaturalezaPeligrosa);
            return new SimpleStringProperty(segundoParametro);
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

        tc_cantidad_bultos.setCellValueFactory(new PropertyValueFactory<>("cantidad_bultos"));


        tc_con_vuelta.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getCon_vuelta());
        });
        tc_con_vuelta.setCellFactory(CheckBoxTableCell.forTableColumn(tc_con_vuelta));

        tc_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tc_seguro.setCellValueFactory(new PropertyValueFactory<>("seguro"));


        tc_es_adr.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getEs_adr());
        });
        tc_es_adr.setCellFactory(CheckBoxTableCell.forTableColumn(tc_es_adr));


        tc_es_atp.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getEs_atp());
        });
        tc_es_atp.setCellFactory(CheckBoxTableCell.forTableColumn(tc_es_atp));
        tvCargasYEntregas.getItems().clear();
        // no cargará los que estén marcados como "ya_reservados"
        tvCargasYEntregas.getItems().addAll(DaoCargaYEntrega.cargarListadoEntregaYCargaSQL(cadenPasadaDesdeGestionVehiculos, false));
        tvCargasYEntregas.refresh();
        tvCargasYEntregas.setTableMenuButtonVisible(true);
        if (tvCargasYEntregas.getItems().isEmpty()) {
            // Por lo visto no hay ni una sola carga con ese criterio. Vamos a alertarle y a enviarle un correo
            Alertas alertas = new Alertas();
            alertas.mostrarInformacion("No hay ningún envio disponible con esos parámetros");
            EnvioCorreos.lanzaUnCorreoDeCargas(elConductorCargado.getDni(), "No he encontrado envíos disponibles");
        }
    }


    /**
     * Como el conductor lo tenemos como parámetros, ya lo podemos rellenar en el formulario, no va a cambiar hasta que salga de la app
     */
    public void rellenarDatosConductor() {
        tf_dni_conductor.setText(elConductorCargado.getDni());
        tf_nombre_conductor.setText(elConductorCargado.getNombre());
        tf_apellido_conductor.setText(elConductorCargado.getApellido());
        tf_pais_conductor.setText(elConductorCargado.getPais());
        tf_provincia_conductor.setText(elConductorCargado.getProvincia());
        tf_localidad_conductor.setText(elConductorCargado.getLocalidad());
        tf_cp_conductor.setText(String.valueOf(elConductorCargado.getCodigo_postal()));
        tf_contacto_conductor.setText(elConductorCargado.getContacto_recogida());
        tf_correo_conductor.setText(elConductorCargado.getCorreo_electronico());
        cb_internacional_conductor.setSelected(elConductorCargado.getEs_internacional());
        tf_telefono_conductor.setText(elConductorCargado.getTelefono());
    }


    /**
     * Los datos del vehículo se rellenan en función de la tabla CONDUCTOR-HAS_VEHICULO, así que primero hay que filtrarla
     * rellenar el combo box y en función de lo elegido rellenar los textfields
     */
    public void rellenarDatosVehiculo() {

        cbx_elegir_vehiculo.getItems().clear();
        // Hay que formatear el combo box porque si no, nos devuelve el objeto entero
        // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
        // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
        cbx_elegir_vehiculo.setConverter(new VehiculoConverter());
        cbx_elegir_vehiculo.getItems().addAll(
                DaoConductorTieneVehiculo.cargarListadoDeVehiculosQueTieneUnConductor(dniPasadoDesdeGestionVehiculos));


//rellenamos los campos con los datos del elemento elegido al cambiar
        cbx_elegir_vehiculo.setOnAction(e -> {

            ModeloVehiculo mv = cbx_elegir_vehiculo.getSelectionModel().getSelectedItem();

            //me quedo con el vehículo para cuando cree en envío
            elVehiculoQueHeLeido = cbx_elegir_vehiculo.getSelectionModel().getSelectedItem();
            tf_cod_vehiculo_vehiculo.setText(mv.getCod_vehiculo());
            tf_mma_vehiculo.setText(String.valueOf(mv.getMMA()));
            tf_dim_h_vehiculo.setText(String.valueOf(mv.getDimension_H()));
            tf_dim_a_vehiculo.setText(String.valueOf(mv.getDimension_A()));
            tf_dim_l_vehiculo.setText(String.valueOf(mv.getDimension_L()));
            tf_carga_util_vehiculo.setText(String.valueOf(mv.getCarga_util()));

            //Estos otros campos lo determina el tipo de vehículo, ergo, hay que consultar en función del tipo de vehículo sus características
            ModeloTipoDeVehiculo mtv = DaoTipoDeVehiculo.recuperaUnTipoDeVehiculo(mv.getCod_tipo_vehiculo());

            cb_rampa_vehiculos.setSelected(mtv.getTiene_rampa());
            cb_adr_vehiculos.setSelected(mtv.getPuede_adr());
            cb_atp_vehiculos.setSelected(mtv.getPuede_atp());
            tf_estipulacion_vehiculo.setText(String.valueOf(mtv.getEstipulacion()));
            tf_instrucciones_vehiculos.setText(String.valueOf(mtv.getInstrucciones()));

            //este último campo pertenece al sistema de carga
            // DaoSistemaCargaVehiculo daoSistemaCargaVehiculo = new DaoSistemaCargaVehiculo();
            ModeloSistemaCargaVehiculo msc = DaoSistemaCargaVehiculo.cargarUnSistemaDeCargaConcreto(mtv.getCod_sistema_carga());
            // rellenamos los checboxes con ese msc
            //como viene de un string, vamos a buscar su cadena por cada tipo de checbkox

            cb_lolo_vehiculos.setSelected(msc.getDescripcion().contains("LOLO"));
            logger.info("he encontrado que es de tipo LOLO? {}", msc.getDescripcion().contains("LOLO"));
            cb_roro_vehiculos.setSelected(msc.getDescripcion().contains("RORO"));
            logger.info("he encontrado que es de tipo RORO? {}", msc.getDescripcion().contains("RORO"));
            cb_carga_lateral_vehiculos.setSelected(msc.getDescripcion().contains("lateral"));
            logger.info("he encontrado que es de tipo lateral? {}", msc.getDescripcion().contains("lateral"));
            cb_carga_superior_vehiculos.setSelected(msc.getDescripcion().contains("superior"));
            logger.info("he encontrado que es de tipo superior? {}", msc.getDescripcion().contains("superior"));

            //Al haber elegido ya su vehículo podemos rellenar la tabla de cargas y envíos filtrada
            mostrarTablaCargarYEntregas();

        });


    }


    /**
     * Carga en función de los parámetros pasados por SQL una serie de cargas y entregas, hay que verificar si con esa combinación de vehículo y petición de
     * carga existe algo para asignar o no y en caso negativo informar
     *
     * @param sentenciaSQLParaEnvioController la SQL a aplicar
     */
    public void cargaElSQLDesdeLaPantallaGestionVehiculo(String sentenciaSQLParaEnvioController) {

        elFiltroSQLRecibido = sentenciaSQLParaEnvioController;
    }


    /**
     * En vez de usar un extend inicial-izable, le pasamos los datos de una clase a otra mediante este procedimiento
     * Como hace las veces de inicializador, también carga todas las tablas
     *
     * @param elConductor el conductor a filtrar
     * @param laSentencia la sentencia SQL que va construyendo
     */
    public void inicializarLaEscenaConDatosPasados(String elConductor, String laSentencia) {
        dniPasadoDesdeGestionVehiculos = elConductor;
        cadenPasadaDesdeGestionVehiculos = laSentencia;
        cargarElConductorElegido(dniPasadoDesdeGestionVehiculos);
        // mostrarTablaCargarYEntregas();
        funcionDobleClickParaTableViewCargaYEntrega();
        rellenarDatosConductor();
        rellenarDatosVehiculo();

    }


    /**
     * Volver atrás en la app
     */
    @FXML
    void salir_app() {
        logger.info("Volver a la pantalla anterior");
        Stage escenaActual = (Stage) tf_cp_conductor.getScene().getWindow(); //ahora tengo la referencia de esta escena
        escenaActual.close(); // la cierro, debería volver atrás
    }


    /**
     * Salir de la app sin más preguntas
     */
    @FXML
    void mi_salir_del_todo() {
        logger.info("Cerrando del toda la aplicación");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de abandonar el programa?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.isPresent() && action.get() == ButtonType.OK) {
            logger.info("Cerrando toda la aplicación");
            Platform.exit();
        }
    }

}
