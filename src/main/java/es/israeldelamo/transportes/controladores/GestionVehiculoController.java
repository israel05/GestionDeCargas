package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.dao.DaoNaturalezaPeligrosa;
import es.israeldelamo.transportes.modelos.ModeloNaturalezasPeligrosas;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static es.israeldelamo.transportes.utilidades.Video.reproducirVideo;

import es.israeldelamo.transportes.utilidades.filtros.ConfirmacionFiltroDialog;
import es.israeldelamo.transportes.utilidades.filtros.FiltroEnvioBuilder;
import es.israeldelamo.transportes.utilidades.filtros.FiltroEnvioResult;
import es.israeldelamo.transportes.utilidades.filtros.TriState;
import es.israeldelamo.transportes.utilidades.navigation.CrearEnvioLauncher;

/**
 * <p>GestionVehiculoController class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class GestionVehiculoController {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(GestionVehiculoController.class);
    /**
     * El bundle de idiomas para este controlador
     */
    private static ResourceBundle bundle;


    /**
     * la lista de los Orígenes seleccionados
     */
    public static ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> daoPaisesYProvinciasYLocalidadesYCodigosPostales_origenes_Seleccionados;
    /**
     *
     */
    public static ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados;
    /**
     * StringBuilder for storing origin data.
     */
    private final StringBuilder cadenaOrigenes = new StringBuilder();
    /**
     * StringBuilder for storing destination data.
     */
    private final StringBuilder cadenaDestinos = new StringBuilder();
    /**
     * StringBuilder for storing load availability data.
     */
    private final StringBuilder disponibilidadDeCargaDe = new StringBuilder();
    /**
     * StringBuilder for storing load availability data.
     */
    private final StringBuilder disponibilidadDeCargaA = new StringBuilder();
    /**
     * StringBuilder for storing round trip data.
     */
    private final StringBuilder idaYVuelta = new StringBuilder();
    /**
     * StringBuilder for storing ramp data.
     */
    private final StringBuilder conRampa = new StringBuilder();
    /**
     * StringBuilder for storing double driver data.
     */
    private final StringBuilder dblConductor = new StringBuilder();
    /**
     * StringBuilder for storing ADR compliance data.
     */
    private final StringBuilder esAdr = new StringBuilder();
    /**
     * StringBuilder for storing ATP compliance data.
     */
    private final StringBuilder esAtp = new StringBuilder();


    ///// TABLA DE ORIGENES
    /**
     * StringBuilder for storing complete load data.
     */
    private final StringBuilder esCompleta = new StringBuilder();
    /**
     * StringBuilder for storing nature data.
     */
    private final StringBuilder tieneNaturaleza = new StringBuilder();
    /**
     * Conjunto escenarios para recordar de donde vienen las pantallas
     */
    private Stage previousStage;
    @FXML
    private MenuItem mi_salir;
    private ModeloPaisYProvinciaYLocalidadYCodigoPostal paisYProvinciaYCodigoPostalSeleccionadoTemporalDesdeDobleClick;

    /// FIN BLOQUE DE SELECCION DE ORIGENES


    /// BLOQUE DE SELECCION DE DESTINOS
    /// BLOQUE DE SELECCION DE ORIGENES
    @FXML
    private Button btn_anyadir_mas_origenes;
    @FXML
    private Button btn_eliminar_origenes_seleccioandos;
    /**
     * Usaremos esta variable para pasarle los datos a Crear envio
     */
    private String elConductor;
    /**
     * Usaremos esta variable para pasarle los datos a Crear envio
     */
    private String cadenaSQLAenviar;
    /**
     * TableColumn for displaying the postal code of the origin.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_codigpostal_tv_origenes;
    /**
     * TableColumn for displaying the locality of the origin.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_localidad_tv_origenes;
    /**
     * TableColumn for displaying the province of the origin.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_provincia_tv_origenes;

    /// FIN BLOQUE DE SELECCION DE DESTINOS


    // SECCIÓN NATURALEZA
    /**
     * TableColumn for displaying the country of the origin.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_pais_tv_origenes;


    /// SECCIÓN DE FECHAS INICIO Y FIN
    /**
     * TableView for displaying origin data.
     */
    @FXML
    private TableView<ModeloPaisYProvinciaYLocalidadYCodigoPostal> tv_origenes;
    /**
     * Button for adding more destinations.
     */
    @FXML
    private Button btn_anyadir_mas_destinos;
    /**
     * Button for deleting selected destinations.
     */
    @FXML
    private Button btn_eliminar_destinos_seleccioandos;
    /**
     * TableColumn for displaying the postal code of the destination.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_codigpostal_tv_destinos;
    /**
     * TableColumn for displaying the locality of the destination.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_localidad_tv_destinos;
    /**
     * TableColumn for displaying the province of the destination.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_provincia_tv_destinos;
    /**
     * TableColumn for displaying the country of the destination.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_pais_tv_destinos;
    /**
     * TableView for displaying destination data.
     */
    @FXML
    private TableView<ModeloPaisYProvinciaYLocalidadYCodigoPostal> tv_destinos;
    /**
     * ComboBox for selecting the nature of the origin.
     */
    @FXML
    private ComboBox<ModeloNaturalezasPeligrosas> cmbbx_naturaleza_tv_origenes;
    /**
     * DatePicker for selecting the start date.
     */
    @FXML
    private DatePicker dp_dia_inicio;
    /**
     * DatePicker for selecting the end date.
     */
    @FXML
    private DatePicker dp_dia_fin;
    /**
     * TextField for inputting the start time.
     */
    @FXML
    private TextField tf_hora_inicio;


    // Variables for storing formatted text for confirmation alerts
    /**
     * TextField for inputting the end time.
     */
    @FXML
    private TextField tf_hora_fin;
    /**
     * Button for closing the window.
     */
    @FXML
    private Button btn_cerrar;
    /**
     * MenuItem for displaying help.
     */
    @FXML
    private MenuItem menuAyuda;
    /**
     * CheckBox for indicating if the vehicle is ADR compliant.
     */
    @FXML
    private CheckBox cbxADR;
    /**
     * CheckBox for indicating if the vehicle is ATP compliant.
     */
    @FXML
    private CheckBox cbxATP;
    /**
     * CheckBox for indicating if the load is complete.
     */
    @FXML
    private CheckBox cbxCargaCompleta;
    /**
     * CheckBox for indicating if there is a double driver.
     */
    @FXML
    private CheckBox cbxDobleConductor;
    /**
     * CheckBox for indicating if the trip is round trip.
     */
    @FXML
    private CheckBox cbxIdaYVuelta;
    /**
     * CheckBox for indicating if the vehicle has a ramp.
     */
    @FXML
    private CheckBox cbxVehiculoConRampa;
    /**
     * La cadena SQL correspondiente el filtro que haya hecho en la anterior pantalla
     */
    //private  ModeloCargaYEntrega modeloCargaYEntregaTemporal=null;
    //private String SentenciaSQLParaEnvioController;
    @FXML
    private VBox cuadranteNaturalezaMercancia; // lo podremos activar y desactivar

    /**
     * Conjunto escenarios para recordar de donde vienen las pantallas
     */
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    /**
     * Volver a la pantalla anterior
     *
     * @param event el evento de acción
     */
    @FXML
    void salir_app(ActionEvent event) {
        Stage currentStage = (Stage) mi_salir.getParentPopup().getOwnerWindow();
        currentStage.close();
        if (previousStage != null) {
            previousStage.show();
        }
    }

    /**
     * El datePicker de JavaFX es bastante simple, pero no tiene un TimePicker. Lo que he encontrado en internet es bastante cutre, así que lo voy a hacer con
     * un TextField que lea los 4 números y los separe por los dos puntos. Va a ser horrible y en futuras versiones habrá que cambiarlo
     */

    boolean verificarHoraReal(String horaAVerificar) {
        String[] partes = horaAVerificar.split(":");
        // Verificar si hay dos partes (horas y minutos)
        if (partes.length != 2) {
            return false;
        }
        try {
            int horas = Integer.parseInt(partes[0]);
            int minutos = Integer.parseInt(partes[1]);
            // Verificar si las horas están en el rango de 0 a 24 y los minutos de 0 a 59
            return horas >= 0 && horas <= 24 && minutos >= 0 && minutos <= 59;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    /**
     * Abre una ventana de ayuda
     *
     * @param event el evento de acción
     */
    @FXML
    void mostrarAyuda(ActionEvent event) {

        Stage currentStage = (Stage) mi_salir.getParentPopup().getOwnerWindow();
        reproducirVideo("conductor", currentStage);
    }

    /**
     * Rellena la tabla de paises provincias y codigos postales con el dao de PaisesProvinciasYCodigosPostales
     */
    @FXML
    void mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostalesOrigen() {
        //inicializo
        daoPaisesYProvinciasYLocalidadesYCodigosPostales_origenes_Seleccionados = FXCollections.observableArrayList(
                new ModeloPaisYProvinciaYLocalidadYCodigoPostal(null, null, null, null));
        //Borro el elemento nulo de inicialización
        daoPaisesYProvinciasYLocalidadesYCodigosPostales_origenes_Seleccionados.remove(0);
        //removeFirst();
        //Defino la factoría
        tc_codigpostal_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        tc_provincia_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_pais_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tc_localidad_tv_origenes.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tv_origenes.getItems().clear();
        tv_origenes.getItems().addAll(daoPaisesYProvinciasYLocalidadesYCodigosPostales_origenes_Seleccionados);
        tv_origenes.refresh();
    }

    /**
     * Rellena la tabla de paises provincias y codigos postales con el dao de PaisesProvinciasYCodigosPostales
     */
    @FXML
    void mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostalesDestino() {
        //inicializo
        daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados = FXCollections.observableArrayList(
                new ModeloPaisYProvinciaYLocalidadYCodigoPostal(null, null, null, null));
        //Borro el elemento nulo de inicialización
        //daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados.removeFirst();
        daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados.remove(0);
        //Defino la factoría
        tc_codigpostal_tv_destinos.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        tc_provincia_tv_destinos.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_localidad_tv_destinos.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tc_pais_tv_destinos.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tv_destinos.getItems().clear();
        tv_destinos.getItems().addAll(daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados);
        tv_destinos.refresh();
    }

    /**
     * Rellenar el combobox con las naturalezas peligrosas
     */
    @FXML
    void mostrarNaturalezasPeligrosasEnComboBox() {
        cmbbx_naturaleza_tv_origenes.getItems()
                .addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
        // hay que formatear el combobox porque si no, nos devuelve el objeto entero
        cmbbx_naturaleza_tv_origenes.setOnAction(e -> {

        });
    }

    /**
     * Si se pulsa en este checkbox el otro checkbox complementario se desactiva y además se desactiva el panel
     *
     * @param event el evento del ratón
     */
    @FXML
    void clickEnATPRaton(MouseEvent event) {
        cbxADR.setSelected(false);
        cuadranteNaturalezaMercancia.setDisable(true);
    }

    /**
     * Si se pulsa en este checkbox el otro checkbox complementario se desactiva y además se desactiva el panel
     *
     * @param event el evento del teclado
     */
    @FXML
    void clickEnATPTeclado(KeyEvent event) {
        cbxADR.setSelected(false);
        cuadranteNaturalezaMercancia.setDisable(true);
    }

    /**
     * Si se pulsa en este checkbox el otro checkbox complementario se desactiva y además se desactiva el panel
     *
     * @param event el evento del ratón
     */
    @FXML
    void clickEnADRRaton(MouseEvent event) {
        cbxATP.setSelected(false);

        cuadranteNaturalezaMercancia.setDisable(false);
    }

    /**
     * Si se pulsa en este checkbox el otro checkbox complementario se desactiva y además se desactiva el panel
     *
     * @param event el evento del teclado
     */
    @FXML
    void clickEnADRTeclado(KeyEvent event) {

        cbxATP.setSelected(false);
        cuadranteNaturalezaMercancia.setDisable(false);
    }


    /**
     * Abre la ventana de añadir origenes
     *
     * @param event el evento de acción
     */
    @FXML
    void anyadir_destinos(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/seleccionDestinos.fxml"),bundle);
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            // nuevo escenario de tipo modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //Asignamos el owner de la nueva ventana
            stage.initOwner(this.tv_destinos.getScene()
                    .getWindow());
            // esto hace que IntelliJ sepa donde está la imagen en función de la raíz del proyecto
            // en otros ides, por ejemplo, code, no va a funcionar por cómo se hace referencia a la raíz de user.dir
            Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
            stage.getIcons().add(icono);


            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Seleccionando destinos");
            stage.showAndWait();
            //refresco cuando vuelva, no seá que se hayan cargado nuevos elementos
            tv_destinos.getItems().clear();
            tv_destinos.getItems().addAll(daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados);
            tv_destinos.refresh();
        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Error al cargar la ventana de SeleccionOrigenes");
            alerta.mostrarError(e.getMessage());
            logger.error("Error al cargar la ventana de SeleccionOrigenes.");
        }
    }

    /**
     * Abre la ventana de añadir origenes
     *
     * @param event el evento de acción
     */
    @FXML
    void anyadir_origenes(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/seleccionOrigenes.fxml"),bundle);
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            // nuevo escenario de tipo modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //Asignamos el owner de la nueva ventana
            stage.initOwner(this.tv_origenes.getScene().getWindow());
            // esto hace que IntelliJ sepa donde está la imagen en función de la raíz del proyecto
            // en otros ides, por ejemplo, visual studio, no va a funcionar por cómo se hace referencia a la raíz de user.dir

            Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
            stage.getIcons().add(icono);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Seleccionando orígenes");
            stage.showAndWait();
            //refresco cuando vuelva, no seá que se hayan cargado nuevos elementos

            tv_origenes.getItems().clear();
            tv_origenes.getItems().addAll(daoPaisesYProvinciasYLocalidadesYCodigosPostales_origenes_Seleccionados);
            tv_origenes.refresh();
        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Error al cargar la ventana de SeleccionOrigenes");
            alerta.mostrarError(e.getMessage());
            logger.error("Error al cargar la ventana de SeleccionOrigenes");
        }
    }


    /**
     * Reinicia todos los controles del filtro a nada
     */

    @FXML
    private void limpiarTodaLaPantalla() {
        //Limpia los tableviews
        tv_origenes.getItems().clear();
        tv_destinos.getItems().clear();
        // fuera textfields
        tf_hora_fin.setText("12:00");
        tf_hora_inicio.setText("12:00");
        // fuera checboxes
        cbxIdaYVuelta.setSelected(false);
        cbxCargaCompleta.setSelected(false);
        cbxADR.setSelected(false);
        cbxATP.setSelected(false);
        cbxVehiculoConRampa.setSelected(false);
        cbxDobleConductor.setSelected(false);
        //fuera comboboxes
        cmbbx_naturaleza_tv_origenes.getItems().clear();
        //fuera dataPickers
        dp_dia_fin.setValue(null);
        dp_dia_inicio.setValue(null);
        cadenaOrigenes.setLength(0); //limpio la cadena de origenes
        cadenaDestinos.setLength(0); // limpio la cadena de destinos


    }


    /**
     * Limpia la tabla de origenes
     *
     * @param event el evento de acción
     */

    @FXML
    void borrar_origenes(ActionEvent event) {
        tv_origenes.getItems().clear();
        GestionVehiculoController.daoPaisesYProvinciasYLocalidadesYCodigosPostales_origenes_Seleccionados.clear();
        // ahora hay que quitar de la cande esa parte


    }

    /**
     * Limpia la tabla de destinos
     *
     * @param event el evento de acción
     */
    @FXML
    void borrar_destinos(ActionEvent event) {
        tv_destinos.getItems().clear();
        GestionVehiculoController.daoPaisesYProvinciasYLocalidadesYCodigosPostales_destinos_Seleccionados.clear();
// ahora hay que quitar de la cande esa parte

    }

    /**
     * Muestra información
     *
     * @param event el evento de acción
     */
    @FXML
    void mostrar_acercade(ActionEvent event) {
        Alertas info_acercade = new Alertas();
        info_acercade.mostrarInformacion("Aplicación de Ciudad Jardín. blas@irakasle.eus");
    }

    /**
     * Muestra todos los elementos de la tabla de cargas sin filtro alguno
     *
     * @param event el evento de acción
     */

    @FXML
    void mostrarTodoSinFiltrar(ActionEvent event) {
        logger.info("Mostrar todo, no aplico ningún filtro");
        //TODO lanzar la siguente pantalla pero sin filtrar nada
    }




    /**
     * Carga el bundle de idiomas para que aparezcan las traducciones
     * podriamos haberlo traido de la clase anterior?
     */
    private void cargarBundleIdiomas(){
        // Cargar el idioma seleccionado
        Preferences prefs = Preferences.userNodeForPackage(GestionAdministradorController.class);
        String idioma = prefs.get("textos", "es"); // Valor por defecto "es"
        Locale locale = new Locale(idioma);
        bundle = ResourceBundle.getBundle("textos", locale);
    }


    /**
     * Se abrira una pantalla nueva donde aparecera la carga filtrada por el conductor, allí se filtrará por el vehículo y se podrá elegir una carga si es que existe
     *
     * @param cadenaSQLADeFiltro la cadena SQL para filtrar
     */
    private void lanzarPantallaCrearNuevoEnvio(String cadenaSQLADeFiltro) {
        Stage owner = null;
        if (mi_salir != null && mi_salir.getParentPopup() != null) {
            owner = (Stage) mi_salir.getParentPopup().getOwnerWindow();
        }
        CrearEnvioLauncher.launch(bundle, owner, elConductor, cadenaSQLADeFiltro);
    }


    /**
     * Construye un gran SQL para la consulta y se lo pasa al DAO que rellenara la tvCargaYEntregas
     * Como solo lo lanza después de confirmación, hay que mostrar al conductor lo que ha elegido.
     * Iremos construyendo el SQL y el mensaje de confirmación a la vez
     */

    @FXML
    private void filtrarPorOpciones() {
        // Mapear checkboxes a TriState donde aplica
        TriState triRampa = cbxVehiculoConRampa.isIndeterminate() ? TriState.INDETERMINATE : (cbxVehiculoConRampa.isSelected() ? TriState.TRUE : TriState.FALSE);
        TriState triDoble = cbxDobleConductor.isIndeterminate() ? TriState.INDETERMINATE : (cbxDobleConductor.isSelected() ? TriState.TRUE : TriState.FALSE);
        TriState triCompleta = cbxCargaCompleta.isIndeterminate() ? TriState.INDETERMINATE : (cbxCargaCompleta.isSelected() ? TriState.TRUE : TriState.FALSE);

        // Construir resultado con SQL y resumen
        FiltroEnvioResult resultado = FiltroEnvioBuilder.build(
                tv_origenes.getItems(),
                tv_destinos.getItems(),
                dp_dia_fin.getValue(),
                tf_hora_fin.getText(),
                dp_dia_inicio.getValue(),
                tf_hora_inicio.getText(),
                cbxIdaYVuelta.isSelected(),
                triRampa,
                triDoble,
                cbxADR.isSelected(),
                cbxATP.isSelected(),
                triCompleta,
                cmbbx_naturaleza_tv_origenes.getValue()
        );

        // Guardar SQL para la siguiente pantalla
        cadenaSQLAenviar = resultado.getSql();

        // Mostrar confirmación y, si acepta, lanzar pantalla
        boolean confirmado = ConfirmacionFiltroDialog.mostrar(resultado);
        if (confirmado) {
            lanzarPantallaCrearNuevoEnvio(cadenaSQLAenviar);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Inicialización de la clase entera
     */
    public void initialize(String elConductor) {
        // Mantener la inicialización ligera para no bloquear la apertura de la ventana.
        // Evitar consultas a BBDD aquí; cargar bajo demanda en acciones del usuario.
        // mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostalesDestino();
        // mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostalesOrigen();
        // mostrarNaturalezasPeligrosasEnComboBox();

        // por cada control hay que ver su estado
        cbxIdaYVuelta.setOnAction(event -> {

        });

        // por cada control hay que ver su estado
        cbxCargaCompleta.setOnAction(event -> {

        });

        // por cada control hay que ver su estado
        cbxADR.setOnAction(event -> {
        });

        // por cada control hay que ver su estado
        cbxATP.setOnAction(event -> {
        });

        // por cada control hay que ver su estado
        cbxDobleConductor.setOnAction(event -> {
        });

        // por cada control hay que ver su estado
        cbxVehiculoConRampa.setOnAction(event -> {
        });


        // verificar que la hora tenga sentido
        tf_hora_inicio.focusedProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (!newVal) { // Si el cursor abandona el TextField
                        String hora = tf_hora_inicio.getText();
                        boolean esHoraValida = verificarHoraReal(hora);
                        if (esHoraValida) {
                            logger.info("Hora válida introducida: {}", hora);
                        } else {
                            Alertas alertas = new Alertas();
                            alertas.mostrarInformacion("Las horas van con números y separadas por : ");
                            logger.warn("Ha metido las horas en el formato incorrecto. Hay que cambiar esto");
                        }
                    }
                });
        tf_hora_fin.focusedProperty()
                .addListener((obs, oldVal, newVal) -> {
                    if (!newVal) { // Si el cursor abandona el TextField
                        String hora = tf_hora_fin.getText();
                        boolean esHoraValida = verificarHoraReal(hora);
                        if (esHoraValida) {
                            logger.info("Hora válida en el campo: {}", hora);
                        } else {
                            Alertas alertas = new Alertas();
                            alertas.mostrarInformacion("Las horas van con números y separadas por : ");
                            logger.warn("Ha metido las horas con el formato incorrecto");
                        }
                    }
                });

    }

    /**
     * Usado para pasar un parámetro desde la ventana que lo ha llamado
     *
     * @param parametro el dato que quiero pasar
     */
    public void pasoDeParametros(String parametro) {
        elConductor = parametro;
    }


}
