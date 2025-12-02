package es.israeldelamo.transportes.controladores;


import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.dao.*;
import es.israeldelamo.transportes.modelos.*;
import es.israeldelamo.transportes.utilidades.*;
import es.israeldelamo.transportes.utilidades.ComboBoxHelper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static es.israeldelamo.transportes.utilidades.RellenaComboLocalizaciones.*;
import static es.israeldelamo.transportes.utilidades.Video.reproducirVideo;



/**
 * Esta clase gigantesca es la que gestiona la ventana del Administrado donde se cambian todas las entradas de la base de datos.
 * Algún día habrá que hacerla más pequeña
 *
 * @author israel
 * @version $Id: $Id
 */
public class GestionAdministradorController {
    /**
     * El bundle de idiomas para este controlador
     */
    private static ResourceBundle bundle;


    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(GestionAdministradorController.class);
    /**
     * Es el botón de salir programa dentro del menu de esta ventana.
     */
    @FXML
    MenuItem mi_ayuda;
    /// ////////////////////////////////
    /// /////// ZONA DE DETECCIÓN DE CAMBIOS EN LOS COMBOBOXES DEL TAB CARGA Y ENTREGA, EMPRESAS y CONDUCTORES
    /// //////////////////////////
    ModeloPaisYProvinciaYLocalidadYCodigoPostal ambitoOrigenTemporal;

    /// BLOQUE DE ENVIOS
    /// BLOQUE DE ENVIOS
    ModeloPaisYProvinciaYLocalidadYCodigoPostal ambitoDestinoTemporal;
    ModeloPaisYProvinciaYLocalidadYCodigoPostal empresaLocalizacionTemporal;
    ModeloPaisYProvinciaYLocalidadYCodigoPostal conductorLocalizacionTemporal;
    ModeloPaisYProvinciaYLocalidadYCodigoPostal conductorNacionalidadTemporal;
    ModeloTipoDeVehiculo tipoDeVehiculoTemporal;
    /**
     * Es una variable auxiliar para recordar el código de administrador desde loging
     */
    private String elAdministrador;

    /// BLOQUE CARGA Y ENTREGA
    /**
     * TableView for displaying shipment data.
     */
    @FXML
    private TableView<ModeloEnvio> tv_envios;
    /**
     * TableColumn for displaying the tracker of the shipment.
     */
    @FXML
    private TableColumn<ModeloEnvio, String> tc_tracker_tv_envio;
    /**
     * TableColumn for displaying the load and delivery details of the shipment.
     */
    @FXML
    private TableColumn<ModeloEnvio, String> tc_carga_entrega_tv_envio;
    /**
     * TableColumn for displaying the driver of the shipment.
     */
    @FXML
    private TableColumn<ModeloEnvio, String> tc_conductor_tv_envio;
    /**
     * TableColumn for displaying the vehicle of the shipment.
     */
    @FXML
    private TableColumn<ModeloEnvio, String> tc_vehiculo_tv_envio;
    /**
     * TextFields for inputting shipment details.
     */
    @FXML
    private TextField tf_tracker_tv_envio, tf_carga_entrega_tv_envio, tf_conductor_tv_envio, tf_vehiculo_tv_envio;
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
     * TableColumn for displaying si se ha servido esa carga  o not yet
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_ya_servido;
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
     * TableColumn for displaying la fecha de creacción de esta carga.
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
     * TableColumn for displaying if the load requires special handling.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_manipulacion_carga_bool;
    /**
     * TableColumn for displaying if the load is stackable.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_remontable;
    /**
     * TableColumn for displaying the type of package.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_tipo_bulto;
    /**
     * TableColumn for displaying if a double driver is required.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_doble_conductor;
    /**
     * TableColumn for displaying if the load is full or groupage.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_carga_completa_o_grupaje;
    /**
     * TableColumn for displaying if a ramp is needed.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_necesita_rampa;
    /**
     * TableColumn for displaying the hazardous nature code.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa;
    /**
     * TableColumn for displaying the country of origin.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_pais_origen;
    /**
     * TableColumn for displaying the province of origin.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_provincia_origen;
    /**
     * TableColumn for displaying the locality of origin.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_localidad_origen;
    /**
     * TableColumn for displaying the postal code of origin.
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
     * TableColumn for displaying if a return trip is included.
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
     * Muestra en el columna la empresa de esa carga y entrega
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, String> tc_empresa;
    /**
     * TableColumn for displaying if the load is ADR.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_adr;
    /**
     * TableColumn for displaying if the load is ATP.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_es_atp;
    /**
     * TableColumn for displaying the number of packages.
     */
    @FXML
    private TableColumn<ModeloCargaYEntrega, Boolean> tc_cantidad_bultos;
    /**
     * TextFields for inputting load and delivery details.
     */
    @FXML
    private TextField tf_codigo_carga_tab_carga_entrega,

    tf_dim_a_tab_carga_entrega,
            tf_dim_h_tab_carga_entrega,
            tf_dim_l_tab_carga_entrega,
            tf_peso_unidad_tab_carga_entrega,

    tf_instrucciones_tab_carga_entrega,
            tf_reembolso_tab_carga_entrega,
            tf_precio_tab_carga_entrega,
            tf_seguro_tab_carga_entrega,
            tf_cantidad_bultos_carga_entrega;
    /**
     * DatePickers for selecting the start and end date of the load and delivery y la fecha de creacción de esta carga
     */
    @FXML
    private DatePicker dp_dia_inicio_carga_entrega;
    @FXML
    private DatePicker dp_dia_fin_carga_entrega;
    @FXML
    private DatePicker dp_fecha_creaccion;
    /**
     * TextFields for inputting the start and end time of the load and delivery.
     */
    @FXML
    private TextField tf_hora_inicio_carga_entrega;
    @FXML
    private TextField tf_hora_fin_carga_entrega;
    /**
     * CheckBoxes for selecting various load and delivery options.
     */
    @FXML
    private CheckBox cxbx_manipulacion_carga_entrega,
            cxbx_grupaje_carga_entrega,
            cxbx_necesita_rampa_carga_entrega,
            cbx_remontable_carga_entrega,
            cxbx_dblconductor_carga_entrega,
            cxbx_peligrosidad_carga_entrega,
            cxbx_adr_carga_entrega,
            cxbx_con_vuelta_carga_entrega,
            cxbx_ya_servida,
            cxbx_atp_carga_entrega;
    /**
     * ComboBox for selecting the hazardous nature of the load.
     */
    @FXML
    private ComboBox<ModeloNaturalezasPeligrosas> combo_naturaleza_peligrosa_carga_entrega;

    /// BLOQUE DE SISTEMA DE CARGA DE VEHICULO
    /**
     * ComboBox for selecting the hazardous nature of the load.
     */
    @FXML
    private ComboBox<ModeloEmpresa> combo_empresa_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination countries.
     */
    @FXML
    private ComboBox<ModeloPais> combo_pais_origen_carga_entrega, combo_pais_destino_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination provinces.
     */
    @FXML
    private ComboBox<ModeloPaisYProvincia> combo_provincia_origen_carga_entrega,
            combo_provincia_destino_carga_entrega;
    /**
     * ComboBoxes for selecting the origin and destination localities.
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidad> combo_localidad_origen_carga_entrega,
            combo_localidad_destino_carga_entrega;

    /// BLOQUE DEL TAB DOCUMENTOS DE CAPACITACIÓN
    /**
     * ComboBoxes for selecting the origin and destination postal codes.
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> combo_cp_origen_carga_entrega,
            combo_cp_destino_carga_entrega;
    /**
     * ComboBox for selecting the package type.
     */
    @FXML
    private ComboBox<ModeloTipoDeBulto> combo_tipo_bulto_carga_entrega;
    /**
     * TableView for displaying vehicle loading system data.
     */
    @FXML
    private TableView<ModeloSistemaCargaVehiculo> tv_sistema_de_carga_de_vehiculo;
    /**
     * TableColumn for displaying the loading system code.
     */
    @FXML
    private TableColumn<ModeloSistemaCargaVehiculo, Integer> tc_cod_sistema_carga_tv_sistema_carga;
    /**
     * TableColumn for displaying the loading system description.
     */
    @FXML
    private TableColumn<ModeloSistemaCargaVehiculo, String> tc_descripcion_tv_sistema_carga;
    /**
     * TextFields for inputting vehicle loading system details.
     */
    @FXML
    private TextField tf_cod_sistema_carga_tv_sistema_carga, tf_descripcion_tv_sistema_carga;
    /**
     * TableView for displaying training document data.
     */
    @FXML
    private TableView<ModeloDocumentoCapacitacion> tv_documento_capacitacion;
    /**
     * TableColumn for displaying the training document code.
     */
    @FXML
    private TableColumn<ModeloDocumentoCapacitacion, String> tc_codigo_capactiaciontv_documento_capacitacion;


    /// BLOQUE DEL TAB TIPOS DE VEHICULO
    /**
     * TableColumn for displaying the hazard level of the training document.
     */
    @FXML
    private TableColumn<ModeloDocumentoCapacitacion, String> tc_peligrosidadtv_documento_capacitacion;
    /**
     * TableColumn for displaying the photo of the training document.
     */
    @FXML
    private TableColumn<ModeloDocumentoCapacitacion, Blob> tc_fototv_documento_capacitacion;
    /**
     * TextFields for inputting training document details.
     */
    @FXML
    private TextField tf_codigo_capactiacion_tab_documento_capacitacion;
    /**
     * ImageView for displaying the photo of the training document.
     */
    @FXML
    private ImageView img_foto_tv_documento_capacitacion;
    /**
     * Button for uploading the training document image.
     */
    @FXML
    private Button btn_carga_imagen_documento_capacitacion;
    @FXML
    private ComboBox<ModeloNaturalezasPeligrosas> cmbx_peligrosidad_tab_documento_capacitacion;
    /**
     * TableView for displaying vehicle type data.
     */
    @FXML
    private TableView<ModeloTipoDeVehiculo> tv_tipo_de_vehiculo;
    /**
     * TableColumn for displaying the vehicle type code.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, String> tc_codigo_tipo_de_vehiculo_tab_tipo_de_vehiculo;
    /**
     * TableColumn for displaying the vehicle type stipulation.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, String> tc_estipulacion_tab_tipo_de_vehiculo;
    /**
     * TableColumn for displaying the photo of the vehicle type.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, Blob> tc_foto_tab_tipo_de_vehiculo;
    /**
     * TableColumn for displaying the instructions for the vehicle type.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, String> tc_instrucciones_tab_tipo_de_vehiculo;
    /**
     * TableColumn for displaying if the vehicle type is ATP.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, Boolean> tc_atp_tab_tipo_de_vehiculo;
    /**
     * TableColumn for displaying if the vehicle type is ADR.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, Boolean> tc_adr_tab_tipo_de_vehiculo;

    /// BLOQUE DEL TAB EMPRESAS
    /**
     * TableColumn for displaying the loading system of the vehicle type.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, Integer> tc_sistema_de_carga_tab_tipo_de_vehiculo;
    /**
     * TableColumn for displaying if the vehicle type has a ramp.
     */
    @FXML
    private TableColumn<ModeloTipoDeVehiculo, Boolean> tc_rampa_tab_tipo_de_vehiculo;
    /**
     * ImageView for displaying the photo of the vehicle type.
     */
    @FXML
    private ImageView img_foto_tv_tipo_vehiculo;
    /**
     * TextFields for inputting vehicle type details.
     */
    @FXML
    private TextField tf_codigo_vehiculo_tab_tipo_vehiculo,
            tf_estipulacion_tab_tipo_vehiculo,
            tf_instrucciones_tab_tipo_vehiculo;
    /**
     * El combo box que permite elegir un sistema de carga de vehiculos dentro del tipo de vehículo
     */
    @FXML
    private ComboBox<ModeloSistemaCargaVehiculo> cmbx_sistema_carga_tab_tipo_vehiculo;
    /**
     * CheckBoxes for selecting various vehicle type options.
     */
    @FXML
    private CheckBox cxbx_carga_lateral_tab_tipo_vehiculo,
            cxbx_carga_superior_tab_tipo_vehiculo,
            cxbx_es_lolo_roro_tab_tipo_vehiculo,
            cxbx_atp_tab_tipo_vehiculo,
            cxbx_adr_tab_tipo_vehiculo,
            cxbx_rampa_tab_tipo_vehiculo;
    /**
     * TableView for displaying company data.
     */
    @FXML
    private TableView<ModeloEmpresa> tv_empresa;
    /**
     * TableColumn for displaying the company CIF.
     */
    @FXML
    private TableColumn<ModeloEmpresa, String> tc_empresas_cif;
    /**
     * TableColumn for displaying the company name.
     */
    @FXML
    private TableColumn<ModeloEmpresa, String> tc_empresas_nombre;
    /**
     * TableColumn for displaying the company address.
     */
    @FXML
    private TableColumn<ModeloEmpresa, String> tc_empresas_domicilio;
    /**
     * TableColumn for displaying the company locality.
     */
    @FXML
    private TableColumn<ModeloEmpresa, String> tc_empresas_localidad;
    /**
     * TableColumn for displaying the company province.
     */
    @FXML
    private TableColumn<ModeloEmpresa, String> tc_empresas_provincia;
    /**
     * TableColumn for displaying the company country.
     */
    @FXML
    private TableColumn<ModeloEmpresa, String> tc_empresas_pais;
    /**
     * TableColumn for displaying the company postal code.
     */
    @FXML
    private TableColumn<ModeloEmpresa, Integer> tc_empresas_codigo_postal;
    /**
     * TableColumn for displaying the company email.
     */
    @FXML
    private TableColumn<ModeloEmpresa, Integer> tc_empresas_correo;
    /**
     * TableColumn for displaying the company phone number.
     */
    @FXML
    private TableColumn<ModeloEmpresa, Integer> tc_empresas_telefono;

    /// BLOQUE DEL TAB LOGIN
    /**
     * TableColumn for displaying the company contact person.
     */
    @FXML
    private TableColumn<ModeloEmpresa, Integer> tc_empresas_persona_contacto;
    /**
     * TextFields for inputting company details.
     */
    @FXML
    private TextField tf_cif_tab_empresas,
            tf_nombre_tab_empresas,
            tf_domicilio_tab_empresas,
            tf_correo_tab_empresas,
            tf_telefono_tab_empresas,
            tf_persona_contacto_tab_empresas;
    /**
     * ComboBox for selecting the company postal code.
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> cmb_cp_empresas;
    /**
     * ComboBox for selecting the company country.
     */
    @FXML
    private ComboBox<ModeloPais> cmb_pais_empresas;
    /**
     * ComboBox for selecting the company locality.
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidad> cmb_localidad_empresas;

    /// BLOQUE DEL TAB Tipo De Carnet
    /**
     * ComboBox for selecting the company province.
     */
    @FXML
    private ComboBox<ModeloPaisYProvincia> cmb_provincias_empresas;
    /**
     * TextFields for inputting login details.
     */
    @FXML
    private TextField tf_papel_tab_login, tf_contrasenya_tab_login, tf_usuario_tab_login;
    /**
     * TableView for displaying login data.
     */
    @FXML
    private TableView<ModeloLogin> tv_login;

    /// BLOQUE DEL TAB País
    /**
     * TableColumn for displaying the login password.
     */
    @FXML
    private TableColumn<ModeloLogin, String> tc_login_pass;
    /**
     * TableColumn for displaying the login role.
     */
    @FXML
    private TableColumn<ModeloLogin, String> tc_login_rol;
    /**
     * TableColumn for displaying the login username.
     */
    @FXML
    private TableColumn<ModeloLogin, String> tc_login_user;

    /// BLOQUE DEL TAB PAIS PROVINCIA
    /**
     * TextField for inputting the type of license.
     */
    @FXML
    private TextField tf_carnet_tab_tipo_carnet;
    /**
     * TableView for displaying license type data.
     */
    @FXML
    private TableView<ModeloDeCarnet> tv_tipo_carnet;
    /**
     * TableColumn for displaying the license type.
     */
    @FXML
    private TableColumn<ModeloDeCarnet, String> tc_carnet_tv_tipo_carnet;
    /**
     * TableColumn for displaying the country.
     */
    @FXML
    private TableColumn<ModeloPais, String> tc_paises_tv_paises;

    /// BLOQUE DEL TAB PAIS PROVINCIA LOCALIDAD
    /**
     * TableView for displaying country data.
     */
    @FXML
    private TableView<ModeloPais> tv_paises;
    /**
     * TextField for inputting the country.
     */
    @FXML
    private TextField tf_pais_tab_paises;
    /**
     * TableView for displaying province data.
     */
    @FXML
    private TableView<ModeloPaisYProvincia> tv_provincias;
    /**
     * TableColumn for displaying the country.
     */
    @FXML
    private TableColumn<ModeloPaisYProvincia, String> tc_paises_tv_provincias;
    /**
     * TableColumn for displaying the province.
     */
    @FXML
    private TableColumn<ModeloPaisYProvincia, String> tc_provincias_tv_provincias;

    /// BLOQUE DEL TAB PAIS PROVINCIA LOCALIDAD CODIGO POSTAL
    /**
     * TextFields for inputting the country and province.
     */
    @FXML
    private TextField tf_pais_tab_paisesyprovincias, tf_provincia_tab_paisesyprovincias;
    /**
     * TableColumn for displaying the province.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidad, String> tc_provincia_tv_localidades;
    /**
     * TableColumn for displaying the locality.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidad, String> tc_localidad_tv_localidades;
    /**
     * TableColumn for displaying the country.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidad, String> tc_pais_tv_localidades;
    /**
     * TableView for displaying locality data.
     */
    @FXML
    private TableView<ModeloPaisYProvinciaYLocalidad> tv_localidades;
    /**
     * TextFields for inputting the country, province, and locality.
     */
    @FXML
    private TextField tf_pais_tab_localidades, tf_provincia_tab_localidades, tf_localidad_tab_localidades;

    /// BLOQUE DE NATURALEZA PELIGROSA
    /**
     * TableColumn for displaying the postal code.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_codigo_postal_tv_codigo_postal;
    /**
     * TableColumn for displaying the province.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_provincia_tv_codigo_postal;
    /**
     * TableColumn for displaying the locality.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_localidad_tv_codigo_postal;
    /**
     * TableColumn for displaying the country.
     */
    @FXML
    private TableColumn<ModeloPaisYProvinciaYLocalidadYCodigoPostal, String> tc_pais_tv_codigo_postal;

    /// BLOQUE DE VEHICULOS
    /**
     * TableView for displaying postal code data.
     */
    @FXML
    private TableView<ModeloPaisYProvinciaYLocalidadYCodigoPostal> tv_codigo_postal;
    /**
     * TextFields for inputting the country, province, locality, and postal code.
     */
    @FXML
    private TextField tf_pais_tab_codigo_postales,
            tf_provincia_tab_codigo_postales,
            tf_localidad_tab_codigo_postales,
            tf_codigo_postal_tab_codigo_postales;
    /**
     * TableColumn for displaying the hazardous nature code.
     */
    @FXML
    private TableColumn<ModeloNaturalezasPeligrosas, Float> tc_codigo_naturaleza_peligrosa_tv_naturaleza_peligrosa;
    /**
     * TableColumn for displaying the hazardous nature description.
     */
    @FXML
    private TableColumn<ModeloNaturalezasPeligrosas, String> tc_descripcion_naturaleza_peligrosa_tv_naturaleza_peligrosa;
    /**
     * TableView for displaying hazardous nature data.
     */
    @FXML
    private TableView<ModeloNaturalezasPeligrosas> tv_naturaleza_peligrosa;
    /**
     * TextFields for inputting hazardous nature details.
     */
    @FXML
    private TextField tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa,
            tf_descripcion_naturaleza_peligrosa_tab_naturaleza_peligrosa;
    /**
     * TableColumn for displaying the vehicle code.
     */
    @FXML
    private TableColumn<ModeloVehiculo, String> tc_codigo_vehiculo_tv_vehiculos;
    /**
     * TableColumn for displaying the vehicle type code.
     */
    @FXML
    private TableColumn<ModeloVehiculo, String> tc_cod_tipo_vehiculo_tv_vehiculos;
    /**
     * TableColumn for displaying the maximum authorized mass (MMA).
     */
    @FXML
    private TableColumn<ModeloVehiculo, Float> tc_mma_tv_vehiculos;
    /**
     * TableColumn for displaying the width dimension.
     */
    @FXML
    private TableColumn<ModeloVehiculo, Float> tc_dim_a_tv_vehiculos;


    /// BLOQUE DE TIPO DE BULTOS
    /**
     * TableColumn for displaying the length dimension.
     */
    @FXML
    private TableColumn<ModeloVehiculo, Float> tc_dim_l_tv_vehiculos;
    /**
     * TableColumn for displaying the height dimension.
     */
    @FXML
    private TableColumn<ModeloVehiculo, Float> tc_dim_h_tv_vehiculos;
    /**
     * TableColumn for displaying the payload capacity.
     */
    @FXML
    private TableColumn<ModeloVehiculo, Float> tc_carga_util_tv_vehiculos;
    /**
     * TableView for displaying vehicle data.
     */
    @FXML
    private TableView<ModeloVehiculo> tv_vehiculos;
    /**
     * TextFields for inputting vehicle details.
     */
    @FXML
    private TextField tf_codigo_vehiculo_tab_vehiculos,
    // CAMBIADO POR COMBOBOX    tf_codigo_tipo_vehiculo_tab_vehiculos,
    tf_mma_tab_vehiculos,
            tf_dim_a_tab_vehiculos,
            tf_dim_l_tab_vehiculos,
            tf_dim_h_tab_vehiculos,
            tf_carga_util_tab_vehiculos;
    @FXML
    private ComboBox<ModeloTipoDeVehiculo> combo_codigo_tipo_vehiculo_tab_vehiculos;
    /**
     * TableColumn for displaying the image of the package type.
     */
    @FXML
    private TableColumn<ModeloTipoDeBulto, Blob> tc_imageColumn_tv_tipo_bulto = new TableColumn<>("Imagen");
    /**
     * TableView for displaying package type data.
     */
    @FXML
    private TableView<ModeloTipoDeBulto> tv_tipo_bulto;

    /// BLOQUE DE CONDUCTORES TIENEN VEHICULOS
    /**
     * TableColumn for displaying the package type code.
     */
    @FXML
    private TableColumn<ModeloTipoDeBulto, Integer> tc_codigo_bulto_tv_tipo_bulto;
    /**
     * TableColumn for displaying the package type name.
     */
    @FXML
    private TableColumn<ModeloTipoDeBulto, Blob> tc_nombre_bulto_tv_tipo_bulto;
    /**
     * TextField for inputting the package type code.
     */
    @FXML
    private TextField tf_codigo_tipo_bulto_tab_tipo_bulto;
    /**
     * TextField for inputting the package type name.
     */
    @FXML
    private TextField tf_nombre_tipo_bulto_tab_tipo_bulto;
    /**
     * ImageView for displaying the package type image.
     */
    @FXML
    private ImageView img_imagen_tipo_bulto_tab_tipo_bulto;

    /// BLOQUE DE CONDUCTORES
    /**
     * Button for loading the package type image.
     */
    @FXML
    private Button btn_carga_imagen_tipo_bulto;

//            tf_nombre_conductores,
//            tf_apellidos_conductores,
//            tf_contacto_conductores,
//            tf_correo_conductores,
//            tf_telefono_conductores,
//            tf_lista_negra_conductores;
    /**
     * TableColumn for displaying the vehicle associated with the driver.
     */
    @FXML
    private TableColumn<ModeloConductorTieneVehiculo, String> tc_vehiculo_tv_conductor_tiene_vehiculo;
    /**
     * TableColumn for displaying the driver associated with the vehicle.
     */
    @FXML
    private TableColumn<ModeloConductorTieneVehiculo, String> tc_conductor_tv_conductor_tiene_vehiculo;
    /**
     * TableView for displaying the association between drivers and vehicles.
     */
    @FXML
    private TableView<ModeloConductorTieneVehiculo> tv_conductor_tiene_vehiculo;
    /**
     * TextField for inputting the driver code in the driver-vehicle association.
     */
    @FXML
    private TextField tf_codigo_conductor_conductor_tiene_vehiculo;
    /**
     * TextField for inputting the vehicle code in the driver-vehicle association.
     */
    @FXML
    private TextField tf_codigo_vehiculo_conductor_tiene_vehiculo;
    /**
     * Los textfields que perteneces al tab conductores
     */
    @FXML
    private TextField tf_dni_conductores;
    /**
     * Son los dos selectores, uno de nacionalidad y otro de la propia localización del conductor
     */
    @FXML
    private ComboBox<ModeloPais> combo_pais_conductores, combo_nacionalidad_conductores;
    /**
     * Provincia de residencia del conductor
     */
    @FXML
    private ComboBox<ModeloPaisYProvincia> combo_provincia_conductores;
    /**
     * Localidad de residencia del conductor
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidad> combo_localidad_conductores;
    /**
     * El código postal de residencia del conductor
     */
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> combo_cp_conductores;
    /**
     * El tipo de carnet del conductor
     */

    @FXML
    private ComboBox<ModeloDeCarnet> combo_tipo_carnet_conductores;
    /**
     * TableColumn for displaying the driver's DNI.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_dni_tv_conductor;
    /**
     * TableColumn for displaying the driver's name.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_nombre_tv_conductor;
    /**
     * Si tiene capacidad de conducción internacional
     */
    @FXML
    private CheckBox cxbx_es_internacional_conductores;
    /**
     * La foto del conductor
     */
    @FXML
    private ImageView img_foto_conductores;
    /**
     * TableColumn for displaying the driver's surname.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_apellido_tv_conductor;
    /**
     * TableColumn for displaying the driver's contact information for pickup.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_contacto_recogida_tv_conductor;
    /**
     * TableColumn for displaying the driver's email.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_correo_tv_conductor;
    /**
     * TableColumn for displaying the driver's photo.
     */
    @FXML
    private TableColumn<ModeloConductor, Blob> tc_foto_tv_conductor;
    /**
     * TableColumn for displaying if the driver is international.
     */
    @FXML
    private TableColumn<ModeloConductor, Boolean> tc_internacional_tv_conductor;
    /**
     * TableColumn for displaying the driver's postal code.
     */
    @FXML
    private TableColumn<ModeloConductor, Integer> tc_cp_tv_conductor;
    /**
     * TableColumn for displaying the driver's locality.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_localidad_tv_conductor;
    /**
     * TableColumn for displaying the driver's province.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_provincia_tv_conductor;

    ////////////////////////////////////////////////
    /// BLOQUE DE TABs
    ///////////////////////////////////////////////////
    /**
     * TableColumn for displaying the driver's country.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_pais_conductor_tv_conductor;
    /**
     * TableColumn for displaying the driver's license type.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_tipo_carnet_tv_conductor;
    /**
     * TableColumn for displaying the driver's phone number.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_telefono_tv_conductor;
    /**
     * TableColumn for displaying the driver's nationality.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_nacionalidad_tv_conductor;
    /**
     * TableColumn for displaying the reason for the driver's blacklisting.
     */
    @FXML
    private TableColumn<ModeloConductor, String> tc_motivo_lista_negra_tv_conductor;
    /**
     * TableView for displaying driver data.
     */
    @FXML
    private TableView<ModeloConductor> tv_conductores;
    /**
     * Tab for vehicle management.
     */
    @FXML
    private Tab tab_vehiculos;
    /**
     * Tab for login management.
     */
    @FXML
    private Tab tab_login;
    /**
     * Tab for country management.
     */
    @FXML
    private Tab tab_paises;
    /**
     * Tab for province management.
     */
    @FXML
    private Tab tab_provincias;
    /**
     * Tab for locality management.
     */
    @FXML
    private Tab tab_localidades;
    /**
     * Tab for postal code management.
     */
    @FXML
    private Tab tab_codigos_postales;
    /**
     * Tab for driver's license type management.
     */
    @FXML
    private Tab tab_tipo_carnet;
    /**
     * Tab for dangerous nature management.
     */
    @FXML
    private Tab tab_naturalezas_peligrosas;
    /**
     * Tab for package type management.
     */
    @FXML
    private Tab tab_tipo_bulto;
    /**
     * Tab for managing the association between drivers and vehicles.
     */
    @FXML
    private Tab tab_conductorTieneVehiculo;
    /**
     * Tab for company management.
     */
    @FXML
    private Tab tab_empresas;


    ///////////////////////////////////////////////////////
    /// BLOQUE DE OBJETOS SELECCIONADOS POR DOBLE CLICK
    ///////////////////////////////////////////////
    /**
     * Tab for vehicle type management.
     */
    @FXML
    private Tab tab_tipo_de_vehiculo;
    /**
     * Tab for training document management.
     */
    @FXML
    private Tab tab_documento_capacitacion;
    /**
     * Tab for vehicle loading system management.
     */
    @FXML
    private Tab tab_sistema_carga_vehiculo;
    /**
     * Tab for load and delivery management.
     */
    @FXML
    private Tab tab_carga_entrega;
    /**
     * Tab for shipment management.
     */
    @FXML
    private Tab tab_envios;
    /**
     * Tab for driver management.
     */
    @FXML
    private Tab tab_conductores;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloPais paisSeleccionadoTemporalDesdeDobleClick; //
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloPaisYProvincia paisYProvinciaSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloPaisYProvinciaYLocalidad paisYProvinciaYLocalidadSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloPaisYProvinciaYLocalidadYCodigoPostal paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloLogin loginSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloVehiculo vehiculoSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloNaturalezasPeligrosas naturalezaPeligrosaTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloTipoDeBulto tipoDeBultoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloConductor conductorTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloDeCarnet carnetSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloConductorTieneVehiculo conductorTieneVehiculoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloEmpresa empresaSeleccionadoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloTipoDeVehiculo tipoDeVehiculoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloDocumentoCapacitacion documentoCapacitacionTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloSistemaCargaVehiculo sistemaCargaVehiculoTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloCargaYEntrega cargaYEntregaTemporalDesdeDobleClick;
    /**
     * Objeto temporal para usar en doble clic
     */
    private ModeloEnvio envioTemporalDesdeDobleClick;
    /**
     * Conjunto escenarios para recordar de donde vienen las pantallas
     */
    private Stage previousStage;

    /**
     * Conjunto escenarios para recordar de donde vienen las pantallas
     */
    public void setPreviousStage(Stage stage) {
        this.previousStage = stage;
    }

    /// ///////////////////////////////////////////////////////////
    /// ///////////////////  ZONA DE MOSTRAR TABLAS S
    /// //////////////////////////////////////////////////////////

    @FXML
    void mostrarTablaEnvios() {
        tc_tracker_tv_envio.setCellValueFactory(new PropertyValueFactory<>("cod_tracking"));
        tc_carga_entrega_tv_envio.setCellValueFactory(new PropertyValueFactory<>("cod_carga_entrega"));
        tc_conductor_tv_envio.setCellValueFactory(new PropertyValueFactory<>("cod_conductor"));
        tc_vehiculo_tv_envio.setCellValueFactory(new PropertyValueFactory<>("cod_vehiculo"));

        tv_envios.setTableMenuButtonVisible(true);
        tv_envios.getItems().clear();
        // no necesito cargar nada hasta que no haya dado al botón de filtrar
        tv_envios.getItems().addAll(DaoEnvio.cargarListadoEnvios());
        tv_envios.refresh();

    }

    /**
     * Rellena toda la tabla Carga Y entrega
     */
    @FXML
    void mostrarTablaCargaYEntrega() {

        tc_ya_servido.setCellValueFactory(cellData -> {
            // No aplicamos el estilo aquí, solo devolvemos el valor
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getYa_reservado());
        });

        tc_ya_servido.setCellFactory(column -> new TableCell<ModeloCargaYEntrega, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setStyle("");  // Reset the style if the cell is empty
                    setGraphic(null);  // Clear the graphic if empty
                } else {
                    // Aplicar el color de fondo dependiendo del valor de 'item'
                    if (item) {
                        setStyle("-fx-background-color: #629134;");  // verde si está reservado
                    } else {
                        setStyle("-fx-background-color: #783F2D;");  // rojo si no está reservado
                    }

                    // Crear el CheckBox y asociarlo con el valor de la celda
                    CheckBox checkBox = new CheckBox();
                    checkBox.setSelected(item);
                    // checkBox.setDisable(true);  // Si deseas que no se pueda modificar el valor

                    // Asociar el CheckBox a la celda
                    setGraphic(checkBox);
                }
            }
        });


        tc_cod_carga.setCellValueFactory(new PropertyValueFactory<>("cod_carga"));


        tc_franja_horario_recogida.setCellValueFactory(new PropertyValueFactory<>("franja_horario_recogida"));
        tc_franja_horario_entrega.setCellValueFactory(new PropertyValueFactory<>("franja_horario_entrega"));
        tc_fecha_creaccion.setCellValueFactory(new PropertyValueFactory<>("fecha_creaccion"));
        tc_dimension_l.setCellValueFactory(new PropertyValueFactory<>("dimension_l"));
        tc_dimension_a.setCellValueFactory(new PropertyValueFactory<>("dimension_a"));
        tc_dimension_h.setCellValueFactory(new PropertyValueFactory<>("dimension_h"));
        tc_peso_unitario.setCellValueFactory(new PropertyValueFactory<>("peso_unitario"));


        //  tc_manipulacion_carga_bool.setCellValueFactory(new PropertyValueFactory<>("manipulacion_carga_bool"));;
        tc_manipulacion_carga_bool.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getManipulacion_carga_bool());
        });
        tc_manipulacion_carga_bool.setCellFactory(CheckBoxTableCell.forTableColumn(tc_manipulacion_carga_bool));


        //    tc_remontable.setCellValueFactory(new PropertyValueFactory<>("remontable"));;
        tc_remontable.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getRemontable());
        });
        tc_remontable.setCellFactory(CheckBoxTableCell.forTableColumn(tc_remontable));


        tc_tipo_bulto.setCellValueFactory(
                cellData -> {
                    ModeloCargaYEntrega p = cellData.getValue();
                    // Suponiendo que getModelosNaturalezaPeligrosa() es el procedimiento que devuelve la tabla ModelosNaturalezaPeligrosa
                    int codTipoBulto = p.getTipo_bulto();
                    // DaoNaturalezaPeligrosa daoNaturalezaPeligrosa = new DaoNaturalezaPeligrosa();
                    String nombreTipoBulto = Objects.requireNonNull(DaoTipoDeBulto.devuelvemeElTipoDeBultoCompletoPorCodigo(
                            codTipoBulto)).getNombre_bulto();
                    return new SimpleStringProperty(nombreTipoBulto);
                });


        //tc_doble_conductor.setCellValueFactory(new PropertyValueFactory<>("doble_conductor"));;
        tc_doble_conductor.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getDoble_conductor());
        });
        tc_doble_conductor.setCellFactory(CheckBoxTableCell.forTableColumn(tc_doble_conductor));


        //tc_carga_completa_o_grupaje.setCellValueFactory(new PropertyValueFactory<>("carga_completa_o_grupaje"));;
        tc_carga_completa_o_grupaje.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getCarga_completa_o_grupaje());
        });
        tc_carga_completa_o_grupaje.setCellFactory(CheckBoxTableCell.forTableColumn(tc_carga_completa_o_grupaje));


        //tc_necesita_rampa.setCellValueFactory(new PropertyValueFactory<>("necesita_rampa"));;
        tc_necesita_rampa.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getNecesita_rampa());
        });
        tc_necesita_rampa.setCellFactory(CheckBoxTableCell.forTableColumn(tc_necesita_rampa));

//devuelve la segunda columna en vez de la primera
        tc_NATURALEZAS_PELIGROSAS_cod_naturaleza_peligrosa.setCellValueFactory(
                cellData -> {
                    ModeloCargaYEntrega p = cellData.getValue();
                    // Suponiendo que getModelosNaturalezaPeligrosa() es el procedimiento que devuelve la tabla ModelosNaturalezaPeligrosa
                    Float codNaturalezaPeligrosa = p.getCod_naturaleza_peligrosa();
                    // DaoNaturalezaPeligrosa daoNaturalezaPeligrosa = new DaoNaturalezaPeligrosa();
                    String segundoParametro = DaoNaturalezaPeligrosa.devuelvemeLaNaturalezaPeligrosaPorCodigo(
                            codNaturalezaPeligrosa);
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


        //tc_con_vuelta.setCellValueFactory(new PropertyValueFactory<>("con_vuelta"));
        tc_con_vuelta.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getCon_vuelta());
        });
        tc_con_vuelta.setCellFactory(CheckBoxTableCell.forTableColumn(tc_con_vuelta));


        tc_precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        tc_seguro.setCellValueFactory(new PropertyValueFactory<>("seguro"));


        tc_empresa.setCellValueFactory(new PropertyValueFactory<>("empresa"));


        //devuelve la segunda columna en vez de la primera
        tc_empresa.setCellValueFactory(
                cellData -> {
                    ModeloCargaYEntrega p = cellData.getValue();
                    // Suponiendo que ModeloEMpresa() es el procedimiento que devuelve la tabla ModelosEmpresa
                    String codigoEmpresa = p.getEmpresa();
                    // DaoNaturalezaPeligrosa daoNaturalezaPeligrosa = new DaoNaturalezaPeligrosa();
                    String nombreDeEmpresa = Objects.requireNonNull(DaoEmpresa.cargarUnaSolaEmpresas(
                            codigoEmpresa)).getNombre();
                    return new SimpleStringProperty(nombreDeEmpresa);
                });

        //  tc_es_adr.setCellValueFactory(new PropertyValueFactory<>("es_adr"));
        tc_es_adr.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getEs_adr());
        });
        tc_es_adr.setCellFactory(CheckBoxTableCell.forTableColumn(tc_es_adr));


        // tc_es_atp.setCellValueFactory(new PropertyValueFactory<>("es_atp"));
        tc_es_atp.setCellValueFactory(cellData -> {
            ModeloCargaYEntrega p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getEs_atp());
        });
        tc_es_atp.setCellFactory(CheckBoxTableCell.forTableColumn(tc_es_atp));


        tc_cantidad_bultos.setCellValueFactory(new PropertyValueFactory<>("cantidad_bultos"));

        tv_carga_entrega.getItems()
                .clear();
        // no necesito cargar nada hasta que no haya dado al botón de filtrar
        tv_carga_entrega.getItems()
                .addAll(DaoCargaYEntrega.cargarListadoEntregaYCarga());
        tv_carga_entrega.refresh();
        tv_carga_entrega.setTableMenuButtonVisible(true);


    }

    /**
     * Rellena el tableview de la pestaña Empresas
     */
    @FXML
    void mostrarTablaTipoDeVehiculo() {
        tc_codigo_tipo_de_vehiculo_tab_tipo_de_vehiculo.setCellValueFactory(new PropertyValueFactory<>("cod_tipo_vehiculo"));

        tc_estipulacion_tab_tipo_de_vehiculo.setCellValueFactory(new PropertyValueFactory<>("estipulacion"));
        // El tipo de celda se especifica con cellFactory y va a ser ImageTableCell, la clase de celda especial que definimos como utilidad
        tc_foto_tab_tipo_de_vehiculo.setCellFactory(col -> new ImageTableCellParaTipoDeVehiculos());
        // el tipo de contenido es el que lea de la base de datos
        tc_foto_tab_tipo_de_vehiculo.setCellValueFactory(new PropertyValueFactory<>("foto"));
        tc_instrucciones_tab_tipo_de_vehiculo.setCellValueFactory(new PropertyValueFactory<>("instrucciones"));

        // parte de @ainaramu para celbox
        tc_atp_tab_tipo_de_vehiculo.setCellValueFactory(cellData -> {
            ModeloTipoDeVehiculo p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getPuede_atp());
        });
        tc_atp_tab_tipo_de_vehiculo.setCellFactory(CheckBoxTableCell.forTableColumn(tc_atp_tab_tipo_de_vehiculo));
        // finde la parte de ainaramu

        // parte de @ainaramu para celbox
        tc_adr_tab_tipo_de_vehiculo.setCellValueFactory(cellData -> {
            ModeloTipoDeVehiculo p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getPuede_adr());
        });
        tc_adr_tab_tipo_de_vehiculo.setCellFactory(CheckBoxTableCell.forTableColumn(tc_adr_tab_tipo_de_vehiculo));
        // finde la parte de ainaramu

        tc_sistema_de_carga_tab_tipo_de_vehiculo.setCellValueFactory(new PropertyValueFactory<>("cod_sistema_carga"));

        // parte de @ainaramu para celbox
        tc_rampa_tab_tipo_de_vehiculo.setCellValueFactory(cellData -> {
            ModeloTipoDeVehiculo p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getTiene_rampa());
        });
        tc_rampa_tab_tipo_de_vehiculo.setCellFactory(CheckBoxTableCell.forTableColumn(tc_rampa_tab_tipo_de_vehiculo));
        // finde la parte de ainaramu


        tv_tipo_de_vehiculo.setTableMenuButtonVisible(true);
        tv_tipo_de_vehiculo.getItems()
                .clear();
        tv_tipo_de_vehiculo.getItems()
                .addAll(DaoTipoDeVehiculo.cargarListadoVehiculos());
        tv_tipo_de_vehiculo.refresh();
    }

    /**
     * Rellena el tableview de la pestaña Empresas
     */
    @FXML
    void mostrarTablaEmpresas() {
        tc_empresas_cif.setCellValueFactory(new PropertyValueFactory<>("cif"));
        tc_empresas_nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_empresas_domicilio.setCellValueFactory(new PropertyValueFactory<>("domicilio"));
        tc_empresas_pais.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tc_empresas_provincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_empresas_localidad.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tc_empresas_codigo_postal.setCellValueFactory(new PropertyValueFactory<>("cp"));
        tc_empresas_correo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        tc_empresas_telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tc_empresas_persona_contacto.setCellValueFactory(new PropertyValueFactory<>("persona_contacto"));
        tv_empresa.setTableMenuButtonVisible(true);
        tv_empresa.getItems().clear();
        tv_empresa.getItems().addAll(DaoEmpresa.cargarListadoEmpresas());
        tv_empresa.refresh();
    }

    /**
     * Rellena el tableview de la pestaña Paises y provincias con los datos leídos del dao Conductor Tiene vehículo
     */
    @FXML
    void mostrarTablaSistemaCargaVehiculos() {
        tc_cod_sistema_carga_tv_sistema_carga.setCellValueFactory(new PropertyValueFactory<>("cod_sistema_carga"));
        tc_descripcion_tv_sistema_carga.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        tv_sistema_de_carga_de_vehiculo.getItems()
                .clear();
        tv_sistema_de_carga_de_vehiculo.getItems()
                .addAll(DaoSistemaCargaVehiculo.cargarListadoSistemaCargaVehiculo());
        tv_sistema_de_carga_de_vehiculo.refresh();
    }

    /**
     * Rellena el tableview de la pestaña Paises y provincias con los datos leídos del dao Conductor Tiene vehículo
     */
    @FXML
    void mostrarTablaConductorTieneVehiculo() {
        tc_conductor_tv_conductor_tiene_vehiculo.setCellValueFactory(new PropertyValueFactory<>("conductor_dni"));
        tc_vehiculo_tv_conductor_tiene_vehiculo.setCellValueFactory(new PropertyValueFactory<>("vehiculos_cod_vehiculo"));
        tv_conductor_tiene_vehiculo.getItems()
                .clear();
        tv_conductor_tiene_vehiculo.getItems()
                .addAll(DaoConductorTieneVehiculo.cargarListadoConductorTieneVehiculo());
        tv_conductor_tiene_vehiculo.refresh();
    }

    /**
     * Muestra la tabla de conductores que tiene dos columnas especiales, la primera la foto que asumimos que siempre está
     * La segunda el checkbox que tiene un código copiado de @author ainaramu
     */
    @FXML
    void mostrarTablaConductores() {
        tc_dni_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("dni"));
        tc_nombre_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tc_apellido_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        tc_telefono_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        tc_contacto_recogida_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("contacto_recogida"));
        tc_correo_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("correo_electronico"));
        tc_foto_tv_conductor.setCellFactory(col -> new ImageTableCellParaConductores());
        tc_foto_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("foto_humano"));
        // parte de ainaramu
        tc_internacional_tv_conductor.setCellValueFactory(cellData -> {
            ModeloConductor p = cellData.getValue();
            return new ReadOnlyBooleanWrapper(p.getEs_internacional());
        });
        tc_internacional_tv_conductor.setCellFactory(CheckBoxTableCell.forTableColumn(tc_internacional_tv_conductor));
        // finde la parte de ainaramu
        tc_cp_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("codigo_postal"));
        tc_localidad_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tc_provincia_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_pais_conductor_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tc_tipo_carnet_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("tipo_de_carnet"));
        tc_motivo_lista_negra_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("motivo_lista_negra"));
        tc_nacionalidad_tv_conductor.setCellValueFactory(new PropertyValueFactory<>("nacionalidad"));
        tv_conductores.setTableMenuButtonVisible(true);
        tv_conductores.getItems().clear();
        tv_conductores.getItems().addAll(DaoConductor.cargarListadoConductores());
        tv_conductores.refresh();
    }

    /**
     * Rellena el tableview de la pestaña Vehiculos con los datos leídos del Dao Vehiculos
     */
    @FXML
    void mostrarTablaVehiculos() {
        tc_codigo_vehiculo_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("cod_vehiculo"));
        tc_mma_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("MMA"));
        tc_dim_a_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("dimension_A"));
        tc_dim_h_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("dimension_L"));
        tc_dim_l_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("dimension_H"));
        tc_carga_util_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("carga_util"));
        tc_cod_tipo_vehiculo_tv_vehiculos.setCellValueFactory(new PropertyValueFactory<>("cod_tipo_vehiculo"));
        tv_vehiculos.getItems().clear();
        tv_vehiculos.getItems().addAll(DaoVehiculo.cargarListadoVehiculos());


        tv_vehiculos.refresh();
    }

    /**
     * Rellena la tableview del tab Tipo de Bulto con lo que hay en el daoTipoDeBulto
     */
    @FXML
    void mostrarTablaTiposDeBulto() {
        tc_codigo_bulto_tv_tipo_bulto.setCellValueFactory(new PropertyValueFactory<>("cod_tipo_bulto"));
        tc_nombre_bulto_tv_tipo_bulto.setCellValueFactory(new PropertyValueFactory<>("nombre_bulto"));

        // El tipo de celda se especifica con cellFactory y va a ser ImageTableCell, la clase de celda especial que definimos como utilidad
        tc_imageColumn_tv_tipo_bulto.setCellFactory(col -> new ImageTableCellParaTipoBultos());
        // el tipo de contenido es el que lea de la base de datos
        tc_imageColumn_tv_tipo_bulto.setCellValueFactory(new PropertyValueFactory<>("imagen_bulto"));
        tv_tipo_bulto.getItems().clear();
        tv_tipo_bulto.getItems().addAll(DaoTipoDeBulto.cargarListadTiposDeBultos());
        tv_tipo_bulto.refresh();
    }

    /**
     * Rellena la tableview del tab Tipo de Bulto con lo que hay en el daoTipoDeBulto
     */
    @FXML
    void mostrarTablaDocumentosCapacitacion() {
        tc_codigo_capactiaciontv_documento_capacitacion.setCellValueFactory(new PropertyValueFactory<>("cod_capacitacion"));
        tc_peligrosidadtv_documento_capacitacion.setCellValueFactory(new PropertyValueFactory<>("peligrosidad"));

        // El tipo de celda se especifica con cellFactory y va a ser ImageTableCell, la clase de celda especial que definimos como utilidad
        tc_fototv_documento_capacitacion.setCellFactory(col -> new ImageTableCellParaDocumentosCapacitacion());
        // el tipo de contenido es el que lea de la base de datos
        tc_fototv_documento_capacitacion.setCellValueFactory(new PropertyValueFactory<>("foto"));

        tv_documento_capacitacion.getItems().clear();
        tv_documento_capacitacion.getItems().addAll(DaoDocumentoCapacitacion.cargarListadoDocumentosCapacitacion());
        tv_documento_capacitacion.refresh();
    }

    /**
     * Rellena la tabla Naturaleza peligrosa con los datos dl daoNaturalezaPeligrosa
     */
    @FXML
    void mostrarTablaNaturalezaPeligrosa() {
        tc_codigo_naturaleza_peligrosa_tv_naturaleza_peligrosa.setCellValueFactory(new PropertyValueFactory<>("codigo_naturaleza_peligrosa"));
        tc_descripcion_naturaleza_peligrosa_tv_naturaleza_peligrosa.setCellValueFactory(new PropertyValueFactory<>("descripcion_naturaleza_peligrosa"));
        tv_naturaleza_peligrosa.getItems().clear();
        tv_naturaleza_peligrosa.getItems().addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
        tv_naturaleza_peligrosa.refresh();
    }

    /**
     * Rellena el tableview de la pestaña TipoDeCarnet con los datos leídos del Dao Tipo de Carnet
     */
    @FXML
    void mostrarTablaTipoDeCarnet() {
        tc_carnet_tv_tipo_carnet.setCellValueFactory(new PropertyValueFactory<>("TipoDeCarnet"));
        tv_tipo_carnet.getItems().clear();
        tv_tipo_carnet.getItems().addAll(DaoTipoDeCarnet.cargarTiposDeCarnets());
        tv_tipo_carnet.refresh();
    }


/////////////////////////////////////////////////
////////////// ZONA DE DOBLE CLIC ///////////
/////////////////////////////////////////////////

    /**
     * Rellena el tableview de la pestaña Pais con los datos leídos del Dao Paises
     */
    @FXML
    void mostrarTablaPaises() {
        tc_paises_tv_paises.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tv_paises.getItems().clear();
        tv_paises.getItems().addAll(DaoPais.cargarListadoPaises());
        tv_paises.refresh();
    }

    /**
     * Rellena el tableview de la pestaña Paises y provincias con los datos leídos del dao Paises y Provincias
     */
    @FXML
    void mostrarTablaPaisesYProvincias() {
        tc_paises_tv_provincias.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tc_provincias_tv_provincias.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tv_provincias.getItems().clear();
        tv_provincias.getItems().addAll(DaoPaisYProvincia.cargarListadoProvincias());
        tv_provincias.refresh();
    }

    /**
     * Rellena la tabla de paises provincias y codigos postales con el dao de PaisesProvinciasYCodigosPostales
     */
    @FXML
    void mostrarTablaPaisesYProvinciasYLocalidades() {
        tc_provincia_tv_localidades.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_localidad_tv_localidades.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tc_pais_tv_localidades.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tv_localidades.getItems().clear();
        tv_localidades.getItems().addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades());
        tv_localidades.refresh();
    }

    /**
     * Rellena la tabla de paises provincias y codigos postales con el dao de PaisesProvinciasYCodigosPostales
     */
    @FXML
    void mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales() {
        tc_codigo_postal_tv_codigo_postal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        tc_provincia_tv_codigo_postal.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        tc_localidad_tv_codigo_postal.setCellValueFactory(new PropertyValueFactory<>("localidad"));
        tc_pais_tv_codigo_postal.setCellValueFactory(new PropertyValueFactory<>("pais"));
        tv_codigo_postal.getItems().clear();
        tv_codigo_postal.getItems()
                .addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostales());
        tv_codigo_postal.refresh();
    }

    /**
     * Rellena la tabla de Login con los datos de la tabla que controla doaLogin
     */
    @FXML
    void mostrarTablaLogin() {
        tc_login_user.setCellValueFactory(new PropertyValueFactory<>("user"));
        tc_login_pass.setCellValueFactory(new PropertyValueFactory<>("pass"));
        tc_login_rol.setCellValueFactory(new PropertyValueFactory<>("rol"));
        tv_login.getItems().clear();
        try {
            tv_login.getItems().addAll(DaoLogin.cargarListadoLogins());
        } catch (SQLException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError(e.getMessage());
        }
        tv_login.refresh();
    }

    /**
     * Función que añade el doble clic a la tableview Envíos
     */
    private void funcionDobleClickParaTableViewEnvios() {
        tv_envios.setRowFactory(tv -> {
            TableRow<ModeloEnvio> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    envioTemporalDesdeDobleClick = row.getItem();
                    tf_tracker_tv_envio.setText(envioTemporalDesdeDobleClick.getCod_tracking());
                    tf_carga_entrega_tv_envio.setText(String.valueOf(envioTemporalDesdeDobleClick.getCod_carga_entrega()));
                    tf_conductor_tv_envio.setText(envioTemporalDesdeDobleClick.getCod_conductor());
                    tf_vehiculo_tv_envio.setText(envioTemporalDesdeDobleClick.getCod_vehiculo());
                }
            });
            return row;
        });
    }

    /**
     * Función de doble clic sobre la table view del Conductor.
     * Es tal la cantidad de datos del Conductor que la vamos a sacar a un FXML distinto
     */
    private void funcionDobleClickParaTableViewTipoDeVehiculo() {
        tv_tipo_de_vehiculo.setRowFactory(tv -> {
            TableRow<ModeloTipoDeVehiculo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    tipoDeVehiculoTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_vehiculo_tab_tipo_vehiculo.setText(tipoDeVehiculoTemporalDesdeDobleClick.getCod_tipo_vehiculo());
                    tf_estipulacion_tab_tipo_vehiculo.setText(tipoDeVehiculoTemporalDesdeDobleClick.getEstipulacion());
                    tf_instrucciones_tab_tipo_vehiculo.setText(tipoDeVehiculoTemporalDesdeDobleClick.getInstrucciones());

                    //Rellenamos los sistemas de carga del tab vehiculos
                    cmbx_sistema_carga_tab_tipo_vehiculo.getItems().clear();
                    ObservableList<ModeloSistemaCargaVehiculo> modeloSistemaCargaVehiculos = FXCollections.observableArrayList();
                    modeloSistemaCargaVehiculos.addAll(DaoSistemaCargaVehiculo.cargarListadoSistemaCargaVehiculo());
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloSistemaCargaVehiculo. Además, añade un fromString que no es necesario implementar.
                    cmbx_sistema_carga_tab_tipo_vehiculo.setConverter(new SistemaCargaVehiculoStringConverter());
                    cmbx_sistema_carga_tab_tipo_vehiculo.getItems().addAll(modeloSistemaCargaVehiculos);
                    ModeloSistemaCargaVehiculo msc = DaoSistemaCargaVehiculo.cargarUnSistemaDeCargaConcreto(tipoDeVehiculoTemporalDesdeDobleClick.getCod_sistema_carga());
                    if (msc != null) {
                        cmbx_sistema_carga_tab_tipo_vehiculo.getSelectionModel().select(
                                new SistemaCargaVehiculoStringConverter().fromString(
                                        msc.getCod_sistema_carga() + "," + msc.getDescripcion()));
                    }


                    /*
                    // como devuelve un objeto, hay que convertirlo para que entre en un combobox
                    ModeloTipoDeBulto modeloTipoDeBultoLeido = DaoTipoDeBulto.devuelvemeElTipoDeBultoCompletoPorCodigo(cargaYEntregaTemporalDesdeDobleClick.getCod_carga());
                    if (modeloTipoDeBultoLeido != null) {
                        combo_tipo_bulto_carga_entrega.getSelectionModel().select(
                                new ModeloTipoDeBultoStringConverter().fromString(
                                        modeloTipoDeBultoLeido.getCod_tipo_bulto() + ", " + modeloTipoDeBultoLeido.getNombre_bulto()));
                    }
                    */


                    //tf_sistema_carga_tab_tipo_vehiculo.setText(String.valueOf(tipoDeVehiculoTemporalDesdeDobleClick.getCod_sistema_carga()));
                    Blob blob = tipoDeVehiculoTemporalDesdeDobleClick.getFoto();
                    //Si la imagen no existe, que muestre una imagen vacía, si no, que muestre la que existe
                    // Convertir Blob a byte array
                    byte[] bytes = null;
                    try {
                        int blobLength = (int) blob.length();
                        bytes = blob.getBytes(1, blobLength);
                    } catch (SQLException e) {
                        Alertas alertaError = new Alertas();
                        logger.error("SQL No he podido cargar imagen para ese registro desde Base De Datos");
                        alertaError.mostrarError("No he podido cargar imagen alguna para ese registro desde Base De Datos");
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos");
                        logger.error("Null Pointer No hay imagen definida para esa entrada  Base De Datos NULL POINTER");
                    }
                    // Crear una imagen desde el byte array
                    try {
                        // Image imageDesdeBlob = new Image(new ByteArrayInputStream(bytes));
                        // Definir el tamaño máximo de la imagen
                        double maxWidth = 200; // Define el ancho máximo de la imagen
                        double maxHeight = 200; // Define el alto máximo de la imagen
                        assert bytes != null;
                        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(bytes)));
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(maxWidth);
                        imageView.setFitHeight(maxHeight);
                        img_foto_tv_tipo_vehiculo.setImage(new Image(new ByteArrayInputStream(bytes)));
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        logger.error("NULL POINTER sin error No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        Image imagenPorDefecto = new Image(Objects.requireNonNull(Iniciografico.class.getResourceAsStream("imagenes/icono.png")));
                        img_foto_tv_tipo_vehiculo.setImage(imagenPorDefecto);
                    }


                    cxbx_adr_tab_tipo_vehiculo.setSelected(tipoDeVehiculoTemporalDesdeDobleClick.getPuede_adr());
                    cxbx_atp_tab_tipo_vehiculo.setSelected(tipoDeVehiculoTemporalDesdeDobleClick.getPuede_atp());
                    cxbx_rampa_tab_tipo_vehiculo.setSelected(tipoDeVehiculoTemporalDesdeDobleClick.getTiene_rampa());


                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview para Sistema Carga Vehículo
     */
    private void funcionDobleClickParaTableViewSistemaCargaVehiculo() {
        tv_sistema_de_carga_de_vehiculo.setRowFactory(tv -> {
            TableRow<ModeloSistemaCargaVehiculo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    sistemaCargaVehiculoTemporalDesdeDobleClick = row.getItem();
                    tf_cod_sistema_carga_tv_sistema_carga.setText(String.valueOf(sistemaCargaVehiculoTemporalDesdeDobleClick.getCod_sistema_carga()));
                    tf_descripcion_tv_sistema_carga.setText(sistemaCargaVehiculoTemporalDesdeDobleClick.getDescripcion());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview para Conducto Tiene Vehículo
     */
    private void funcionDobleClickParaTableViewConductorTieneVehiculo() {
        tv_conductor_tiene_vehiculo.setRowFactory(tv -> {
            TableRow<ModeloConductorTieneVehiculo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    conductorTieneVehiculoTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_conductor_conductor_tiene_vehiculo.setText(conductorTieneVehiculoTemporalDesdeDobleClick.getConductor_dni());
                    tf_codigo_vehiculo_conductor_tiene_vehiculo.setText(conductorTieneVehiculoTemporalDesdeDobleClick.getVehiculos_cod_vehiculo());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview para Conducto Tiene Vehículo
     */
    private void funcionDobleClickParaTableViewModeloCapacitacion() {
        tv_documento_capacitacion.setRowFactory(tv -> {
            TableRow<ModeloDocumentoCapacitacion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    documentoCapacitacionTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_capactiacion_tab_documento_capacitacion.setText(documentoCapacitacionTemporalDesdeDobleClick.getCod_capacitacion());


                    //Rellenamos los posibles tipos de peligrosidad
                    cmbx_peligrosidad_tab_documento_capacitacion.getItems().clear();
                    ObservableList<ModeloNaturalezasPeligrosas> modelosPeligrosas = FXCollections.observableArrayList();
                    modelosPeligrosas.addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    cmbx_peligrosidad_tab_documento_capacitacion.setConverter(new NaturalezaPeligrosaStringConverter());
                    cmbx_peligrosidad_tab_documento_capacitacion.getItems().addAll(modelosPeligrosas); // Corregido para asignar correctamente los modelos de peligrosidad

                    String peligrosidadFormateada = documentoCapacitacionTemporalDesdeDobleClick.getPeligrosidad()
                            + ", " + DaoNaturalezaPeligrosa.devuelvemeLaNaturalezaPeligrosaPorCodigo
                            (documentoCapacitacionTemporalDesdeDobleClick.getPeligrosidad());
                    cmbx_peligrosidad_tab_documento_capacitacion.getSelectionModel().select(new NaturalezaPeligrosaStringConverter().fromString(
                            peligrosidadFormateada)
                    );


                    Blob blob = documentoCapacitacionTemporalDesdeDobleClick.getFoto();
                    //Si la imagen no existe, que muestre una imagen vacía, si no, que muestre la que existe
                    // Convertir Blob a byte array
                    byte[] bytes = null;
                    try {
                        int blobLength = (int) blob.length();
                        bytes = blob.getBytes(1, blobLength);
                    } catch (SQLException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No he podido cargar imagen alguna para ese registro desde Base De Datos");
                        logger.error("SQL No he podido cargar imagen alguna para ese registro desde Base De Datos");
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos");
                        logger.error("NULL POINTER No hay imagen definida para esa entrada  Base De Datos");
                    }
                    // Crear una imagen desde el byte array
                    try {
                        Image imageDesdeBlob = new Image(new ByteArrayInputStream(Objects.requireNonNull(bytes)));
                        // Definir el tamaño máximo de la imagen
                        double maxWidth = 200; // Define el ancho máximo de la imagen
                        double maxHeight = 200; // Define el alto máximo de la imagen
                        ImageView imageView = new ImageView(imageDesdeBlob);
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(maxWidth);
                        imageView.setFitHeight(maxHeight);
                        img_foto_tv_documento_capacitacion.setImage(imageDesdeBlob);
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        logger.error("NULL POINTER No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        // alertaError.mostrarError(e.getMessage());
                        Image imagenPorDefecto = new Image(Objects.requireNonNull(Iniciografico.class.getResourceAsStream("imagenes/imagenNoDisponible.png")));
                        img_foto_tv_documento_capacitacion.setImage(imagenPorDefecto);
                    }
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview para empresa
     */
    private void funcionDobleClickParaTableViewEmpresas() {
        tv_empresa.setRowFactory(tv -> {
            TableRow<ModeloEmpresa> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    empresaSeleccionadoTemporalDesdeDobleClick = row.getItem();

                    tf_cif_tab_empresas.setText(empresaSeleccionadoTemporalDesdeDobleClick.getCif());
                    tf_nombre_tab_empresas.setText(empresaSeleccionadoTemporalDesdeDobleClick.getNombre());
                    tf_domicilio_tab_empresas.setText(empresaSeleccionadoTemporalDesdeDobleClick.getDomicilio());

                    //Rellenamos los paises y elegimos el seleccionado según
                    cmb_pais_empresas.getItems().clear();
                    ObservableList<ModeloPais> modelosDePais = FXCollections.observableArrayList();
                    modelosDePais.addAll(DaoPais.cargarListadoPaises());
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    cmb_pais_empresas.setConverter(new PaisStringConverter());
                    cmb_pais_empresas.getItems().addAll(modelosDePais); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO PAIS, mientras que modeloConductor.getPais_conductor devuelve un string
                    cmb_pais_empresas.getSelectionModel().select(new PaisStringConverter().fromString(empresaSeleccionadoTemporalDesdeDobleClick.getPais()));


                    //Rellenamos los paises y provincias y elegimos el seleccionado según

                    cmb_provincias_empresas.getItems().clear();
                    ObservableList<ModeloPaisYProvincia> mpp = FXCollections.observableArrayList();
                    //que me devuelva solo las provincias del país, no todos los paises y provincias
                    mpp.addAll(DaoPaisYProvincia.cargarListadoProvincias(empresaSeleccionadoTemporalDesdeDobleClick.getPais()));
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    cmb_provincias_empresas.setConverter(new PaisYProvinciaStringConverter());
                    cmb_provincias_empresas.getItems().addAll(mpp); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO PROVINCIA, mientras que modeloConductor.getPais_conductor devuelve un string
                    //componemos una cadena con los datos del objeto para pasárselo a getSelectionModel
                    // usaremos como plantilla Vitoria, (Álava) España CP-01005
                    //  cmb_provincia_empresas.getSelectionModel().select(new PaisYProvinciaStringConverter().fromString(provinciaFormateada));
                    String provinciaFormateada = empresaSeleccionadoTemporalDesdeDobleClick.getProvincia() + ", " + empresaSeleccionadoTemporalDesdeDobleClick.getPais();
                    cmb_provincias_empresas.getSelectionModel().select(new PaisYProvinciaStringConverter().fromString(provinciaFormateada));


                    //Rellenamos los paises y localidades y provincias y elegimos el seleccionado según
                    cmb_localidad_empresas.getItems().clear();
                    ObservableList<ModeloPaisYProvinciaYLocalidad> mlocalidad = FXCollections.observableArrayList();
                    //que me devuelva solo las provincias del país, no todos los paises y provincias
                    mlocalidad.addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades(empresaSeleccionadoTemporalDesdeDobleClick.getPais(), empresaSeleccionadoTemporalDesdeDobleClick.getProvincia()));
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    cmb_localidad_empresas.setConverter(new PaisYProvinciaLocalidadStringConverter());
                    cmb_localidad_empresas.getItems().addAll(mlocalidad); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO LOCALIDAD, mientras que modeloConductor.getPais_conductor devuelve un string
                    //componemos una cadena con los datos del objeto para pasárselo a getSelectionModel
                    // la plantilla de la cadena es "Vitoria, Álava, España"
                    String localidadFormateada =
                            empresaSeleccionadoTemporalDesdeDobleClick.getLocalidad() + ", " +
                                    empresaSeleccionadoTemporalDesdeDobleClick.getProvincia() + ", " +
                                    empresaSeleccionadoTemporalDesdeDobleClick.getPais();
                    cmb_localidad_empresas.getSelectionModel().select(new PaisYProvinciaLocalidadStringConverter().fromString(localidadFormateada));


                    //Rellenamos los paises y localidades y provincias y elegimos el seleccionado según
                    cmb_cp_empresas.getItems().clear();
                    ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> mcp = FXCollections.observableArrayList();
                    //que me devuelva solo las provincias del país, no todos los paises y provincias
                    mcp.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                            empresaSeleccionadoTemporalDesdeDobleClick.getPais(),
                            empresaSeleccionadoTemporalDesdeDobleClick.getProvincia(),
                            empresaSeleccionadoTemporalDesdeDobleClick.getLocalidad()));
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    cmb_cp_empresas.setConverter(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter());
                    cmb_cp_empresas.getItems().addAll(mcp); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO LOCALIDAD, mientras que modeloConductor.getPais_conductor devuelve un string
                    //componemos una cadena con los datos del objeto para pasárselo a getSelectionModel
                    // la plantilla de la cadena es "Vitoria, (Álava) España"
                    String cpFormateado =
                            empresaSeleccionadoTemporalDesdeDobleClick.getLocalidad() + ", " +
                                    empresaSeleccionadoTemporalDesdeDobleClick.getProvincia() + ", " +
                                    empresaSeleccionadoTemporalDesdeDobleClick.getPais() + ", " +
                                    empresaSeleccionadoTemporalDesdeDobleClick.getCP();
                    cmb_cp_empresas.getSelectionModel().select(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter().fromString(cpFormateado));

                    tf_telefono_tab_empresas.setText(empresaSeleccionadoTemporalDesdeDobleClick.getTelefono());
                    tf_correo_tab_empresas.setText(empresaSeleccionadoTemporalDesdeDobleClick.getCorreo());
                    tf_persona_contacto_tab_empresas.setText(empresaSeleccionadoTemporalDesdeDobleClick.getPersona_contacto());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tabla Naturalezas Peligrosas.
     */
    private void funcionDobleClickParaTableViewNaturalezasPeligrosas() {
        tv_naturaleza_peligrosa.setRowFactory(tv -> {
            TableRow<ModeloNaturalezasPeligrosas> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    naturalezaPeligrosaTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.setText(String.valueOf(naturalezaPeligrosaTemporalDesdeDobleClick.getCodigo_naturaleza_peligrosa()));
                    tf_descripcion_naturaleza_peligrosa_tab_naturaleza_peligrosa.setText(naturalezaPeligrosaTemporalDesdeDobleClick.getDescripcion_naturaleza_peligrosa());
                }
            });
            return row;
        });
    }

    /**
     * Función de doble clic sobre la table view del Conductor.
     * Es tal la cantidad de datos del Conductor que la vamos a sacar a un FXML distinto
     */

    private void funcionDobleClickParaTableViewConductor() {
        tv_conductores.setRowFactory(tv -> {
            TableRow<ModeloConductor> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    conductorTemporalDesdeDobleClick = row.getItem();
                    ///  LANZA LA VENTA CON ESTOS DATOS, CUANDO VUELVA LOS TRAES OTRA VEZ


                    FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/modificarConductor.fxml"),bundle);

                    Parent root;
                    try {
                        root = loader.load();
                        ModificarConductorController controlador = loader.getController();
                        controlador.cargarUnConductorConcreto(conductorTemporalDesdeDobleClick);
                        Scene scene = new Scene(root);

                        // nuevo escenario de tipo modal
                        Stage stage = new Stage();
                        stage.initModality(Modality.APPLICATION_MODAL);
                        //Asignamos el owner de la nueva ventana
                        stage.initOwner(this.tv_conductores.getScene().getWindow());

                        Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
                        stage.getIcons().add(icono);

                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.setTitle("Modificar los parámetros del conducto");
                        //Lanzamos
                        stage.showAndWait();
                        // y referesco la tabla
                        cargarTabConductores();

                    } catch (IOException e) {
                        Alertas alerta = new Alertas();
                        alerta.mostrarError("Error de entrada y salida");
                        logger.error("IO ERROR Error de entrada y salida");
                        alerta.mostrarError(e.getMessage());
                        tv_conductores.refresh();
                    }
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview tipo de bulto
     */
    private void funcionDobleClickParaTableViewTipoDeBulto() {
        tv_tipo_bulto.setRowFactory(tv -> {
            TableRow<ModeloTipoDeBulto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    tipoDeBultoTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_tipo_bulto_tab_tipo_bulto.setText(String.valueOf(tipoDeBultoTemporalDesdeDobleClick.getCod_tipo_bulto()));
                    tf_nombre_tipo_bulto_tab_tipo_bulto.setText(tipoDeBultoTemporalDesdeDobleClick.getNombre_bulto());
                    Blob blob = tipoDeBultoTemporalDesdeDobleClick.getImagen_bulto();
                    //Si la imagen no existe, que muestre una imagen vacía, si no, que muestre la que existe
                    // Convertir Blob a byte array
                    byte[] bytes = null;
                    try {
                        int blobLength = (int) blob.length();
                        bytes = blob.getBytes(1, blobLength);
                    } catch (SQLException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No he podido cargar imagen alguna para ese registro desde Base De Datos");
                        logger.error("No he podido cargar imagen alguna para ese registro desde Base De Datos");
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos");
                        logger.error("No hay imagen definida para esa entrada  Base De Datos");
                    }
                    // Crear una imagen desde el byte array
                    try {
                        assert bytes != null;
                        Image imageDesdeBlob = new Image(new ByteArrayInputStream(bytes));
                        // Definir el tamaño máximo de la imagen
                        double maxWidth = 200; // Define el ancho máximo de la imagen
                        double maxHeight = 200; // Define el alto máximo de la imagen
                        ImageView imageView = new ImageView(imageDesdeBlob);
                        imageView.setPreserveRatio(true);
                        imageView.setFitWidth(maxWidth);
                        imageView.setFitHeight(maxHeight);
                        img_imagen_tipo_bulto_tab_tipo_bulto.setImage(imageDesdeBlob);
                    } catch (NullPointerException e) {
                        Alertas alertaError = new Alertas();
                        alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        logger.error("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        // alertaError.mostrarError(e.getMessage());
                        Image imagenPorDefecto = new Image(Objects.requireNonNull(Iniciografico.class.getResourceAsStream("imagenes/icono.png")));
                        img_imagen_tipo_bulto_tab_tipo_bulto.setImage(imagenPorDefecto);
                    }
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview login
     */
    private void funcionDobleClickParaTableViewLogin() {
        tv_login.setRowFactory(tv -> {
            TableRow<ModeloLogin> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    loginSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_usuario_tab_login.setText(loginSeleccionadoTemporalDesdeDobleClick.getUser());
                    tf_contrasenya_tab_login.setText(loginSeleccionadoTemporalDesdeDobleClick.getPass());
                    tf_papel_tab_login.setText(loginSeleccionadoTemporalDesdeDobleClick.getRol());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview tipo de carnet
     */
    private void funcionDobleClickParaTableViewTipoDeCarnet() {
        tv_tipo_carnet.setRowFactory(tv -> {
            TableRow<ModeloDeCarnet> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    carnetSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_carnet_tab_tipo_carnet.setText(carnetSeleccionadoTemporalDesdeDobleClick.getTipoDeCarnet());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview tipo de paises
     */

    private void funcionDobleClickParaTableViewPaises() {
        tv_paises.setRowFactory(tv -> {
            TableRow<ModeloPais> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    paisSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_pais_tab_paises.setText(paisSeleccionadoTemporalDesdeDobleClick.getPais());
                }
            });
            return row;
        });
    }


    ////////////////////////////////////////////////////////////////////////
    ///////////////   ZONA DE LIMPIEZA DE TEXTFIELDS          //////////////
    ////////////////////////////////////////////////////////////////////////

    /**
     * Función que añade el doble clic a la tableview paises y provincias
     */
    private void funcionDobleClickParaTableViewPaisesYProvincias() {
        tv_provincias.setRowFactory(tv -> {
            TableRow<ModeloPaisYProvincia> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    paisYProvinciaSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_pais_tab_paisesyprovincias.setText(paisYProvinciaSeleccionadoTemporalDesdeDobleClick.getPais());
                    tf_provincia_tab_paisesyprovincias.setText(paisYProvinciaSeleccionadoTemporalDesdeDobleClick.getProvincia());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview paises y provincias
     */
    private void funcionDobleClickParaTableViewPaisesYProvinciasYLocalidades() {
        tv_localidades.setRowFactory(tv -> {
            TableRow<ModeloPaisYProvinciaYLocalidad> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    paisYProvinciaYLocalidadSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_pais_tab_localidades.setText(paisYProvinciaYLocalidadSeleccionadoTemporalDesdeDobleClick.getPais());
                    tf_provincia_tab_localidades.setText(paisYProvinciaYLocalidadSeleccionadoTemporalDesdeDobleClick.getProvincia());
                    tf_localidad_tab_localidades.setText(paisYProvinciaYLocalidadSeleccionadoTemporalDesdeDobleClick.getLocalidad());
                }
            });
            return row;
        });
    }

    /**
     * Función que añade el doble clic a la tableview paises y provincias y codigos postales
     */
    private void funcionDobleClickParaTableViewPaisesYProvinciasYLocalidadesYCodigosPostales() {
        tv_codigo_postal.setRowFactory(tv -> {
            TableRow<ModeloPaisYProvinciaYLocalidadYCodigoPostal> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_pais_tab_codigo_postales.setText(paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getPais());
                    tf_provincia_tab_codigo_postales.setText(paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getProvincia());
                    tf_localidad_tab_codigo_postales.setText(paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getLocalidad());
                    tf_codigo_postal_tab_codigo_postales.setText(String.valueOf(paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick.getCodigoPostal()));
                }
            });
            return row;
        });
    }

    /**
     * Asocial el evento doble clic a la tabla de vehículos y rellena los textos de los textfields de ese tab
     */
    private void funcionDobleClickTableViewVehiculos() {
        tv_vehiculos.setRowFactory(tv -> {
            TableRow<ModeloVehiculo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    vehiculoSeleccionadoTemporalDesdeDobleClick = row.getItem();
                    tf_codigo_vehiculo_tab_vehiculos.setText(String.valueOf(vehiculoSeleccionadoTemporalDesdeDobleClick.getCod_vehiculo()));
                    tf_mma_tab_vehiculos.setText(String.valueOf(vehiculoSeleccionadoTemporalDesdeDobleClick.getMMA()));
                    tf_dim_a_tab_vehiculos.setText(String.valueOf(vehiculoSeleccionadoTemporalDesdeDobleClick.getDimension_A()));
                    tf_dim_l_tab_vehiculos.setText(String.valueOf(vehiculoSeleccionadoTemporalDesdeDobleClick.getDimension_L()));
                    tf_dim_h_tab_vehiculos.setText(String.valueOf(vehiculoSeleccionadoTemporalDesdeDobleClick.getDimension_H()));
                    tf_carga_util_tab_vehiculos.setText(String.valueOf(vehiculoSeleccionadoTemporalDesdeDobleClick.getCarga_util()));

                    // cAMBIADO POR COMBOBOX    tf_codigo_tipo_vehiculo_tab_vehiculos.setText(vehiculoSeleccionadoTemporalDesdeDobleClick.getCod_tipo_vehiculo());

                }
            });
            return row;
        });
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


                    /**
                     *   hourInText.setTextFormatter(new TextFormatter<>(new RangeStringConverter(0, 23), 0));
                     *         minInText.setTextFormatter(new TextFormatter<>(new RangeStringConverter(0, 59), 0));
                     *         secInText.setTextFormatter(new TextFormatter<>(new RangeStringConverter(0, 59), 0));
                     *         alarmInLabel.textProperty().bind(Bindings.format("%s:%s:%s", hourInText.textProperty(), minInText.textProperty(), secInText.textProperty()));

                     private static class RangeStringConverter extends StringConverter<Integer> {

                     private final int min;
                     private final int max;

                     public RangeStringConverter(int min, int max) {
                     this.min = min;
                     this.max = max;
                     }

                     @Override public String toString(Integer object) {
                     return String.format("%02d", object);
                     }

                     @Override public Integer fromString(String string) {
                     int i = Integer.parseInt(string);
                     if (i > max || i < min) {
                     throw new IllegalArgumentException();
                     }
                     return i;
                     }

                     }

                     */


                    // Convertir el Timestamp a LocalDateTime
                    LocalDateTime localDateTimeRecogida = cargaYEntregaTemporalDesdeDobleClick.
                            getFranja_horario_recogida().toLocalDateTime();
                    // Extraer la fecha del LocalDateTime
                    LocalDate fechaRecogida = localDateTimeRecogida.toLocalDate();
                    dp_dia_inicio_carga_entrega.setValue(fechaRecogida);
                    tf_hora_inicio_carga_entrega.setText(String.valueOf(localDateTimeRecogida.getHour()));
                    // como hay que poner un cero por delante, porque si no, al leer va a llamar al 8 como 80. lo hacemos en concatenacion
                    if (localDateTimeRecogida.getMinute() < 9) {
                        tf_hora_inicio_carga_entrega.setText(tf_hora_inicio_carga_entrega.getText() + ":0"
                                + localDateTimeRecogida.getMinute());
                    } else {
                        tf_hora_inicio_carga_entrega.setText(tf_hora_inicio_carga_entrega.getText() +
                                ":" + localDateTimeRecogida.getMinute());
                    }


                    // Convertir el Timestamp a LocalDateTime
                    LocalDateTime localDateTimeEntrega = cargaYEntregaTemporalDesdeDobleClick.
                            getFranja_horario_entrega().toLocalDateTime();
                    // Extraer la fecha del LocalDateTime
                    LocalDate fechaEntrega = localDateTimeEntrega.toLocalDate();
                    dp_dia_fin_carga_entrega.setValue(fechaEntrega);
                    tf_hora_fin_carga_entrega.setText(String.valueOf(localDateTimeEntrega.getHour()));
                    if (localDateTimeEntrega.getMinute() < 9) {
                        tf_hora_fin_carga_entrega.setText(tf_hora_fin_carga_entrega.getText() + ":0"
                                + localDateTimeEntrega.getMinute());
                    } else {
                        tf_hora_fin_carga_entrega.setText(tf_hora_fin_carga_entrega.getText() +
                                ":" + localDateTimeEntrega.getMinute());
                    }


                    // Convertir el Timestamp a LocalDateTime
                    LocalDateTime localDateTimeFechaCreaccion = cargaYEntregaTemporalDesdeDobleClick.
                            getFecha_creaccion().toLocalDateTime();
                    // Extraer la fecha del LocalDateTime
                    LocalDate fechaCreaccion = localDateTimeFechaCreaccion.toLocalDate();
                    dp_fecha_creaccion.setValue(fechaCreaccion);


                    cxbx_manipulacion_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getManipulacion_carga_bool());
                    cbx_remontable_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getRemontable());
                    cxbx_dblconductor_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getDoble_conductor());

                    cxbx_adr_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getEs_adr());
                    cxbx_atp_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getEs_atp());
                    cxbx_ya_servida.setSelected(cargaYEntregaTemporalDesdeDobleClick.getYa_reservado());
                    cxbx_grupaje_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getCarga_completa_o_grupaje());
                    cxbx_necesita_rampa_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getNecesita_rampa());
                    cxbx_con_vuelta_carga_entrega.setSelected(cargaYEntregaTemporalDesdeDobleClick.getCon_vuelta());

                    //Rellenamos los paises y elegimos el seleccionado según
                    combo_pais_origen_carga_entrega.getItems().clear();
                    ObservableList<ModeloPais> modelosDePaisOrigen = FXCollections.observableArrayList();
                    modelosDePaisOrigen.addAll(DaoPais.cargarListadoPaises());
                    // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
                    // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
                    combo_pais_origen_carga_entrega.setConverter(new PaisStringConverter());
                    combo_pais_origen_carga_entrega.getItems().addAll(modelosDePaisOrigen); // Corregido para asignar correctamente los modelos de país
                    // este selecciona un item de tipo MODELO PAIS, mientras que modeloConductor.getPais_conductor devuelve un string
                    combo_pais_origen_carga_entrega.getSelectionModel().select(new PaisStringConverter().fromString(
                            cargaYEntregaTemporalDesdeDobleClick.getPais_origen()));


                    // Con el rollo de los string converters nos vemos obligados a hacer este tipo de cosas
                    //formatear la celda para tipo empresa
                    //carga toda las empresas
                    //pero cuando se rellena el combobox de empresas indicarle cuál se ha rellando
                    // con el codigo no es suficiente, neecsitamos cod+,+nombre
                    //y el nombre lo guarda ModeloEMpresa, porque ModeloCarga no tiene más que el código
                    // recuperaré la empresa entwera a partir del modeloCarga.getEmpresa
                    // usando el DAO emprepsa. cargaEMpresaDesdeCodigo

                    combo_empresa_carga_entrega.getItems().clear();
                    ObservableList<ModeloEmpresa> modeloEmpresas = FXCollections.observableArrayList();
                    modeloEmpresas.addAll(DaoEmpresa.cargarListadoEmpresas());
                    combo_empresa_carga_entrega.setConverter(new EmpresaStringConverter());
                    combo_empresa_carga_entrega.getItems().addAll(modeloEmpresas);
                    String nombreEmpresa =
                            Objects.requireNonNull(DaoEmpresa.cargarUnaSolaEmpresas(
                                    cargaYEntregaTemporalDesdeDobleClick.getEmpresa())).getNombre()
                                    + "," +
                                    Objects.requireNonNull(DaoEmpresa.cargarUnaSolaEmpresas(
                                            cargaYEntregaTemporalDesdeDobleClick.getEmpresa())).getCif();
                    combo_empresa_carga_entrega.getSelectionModel().select(new EmpresaStringConverter().fromString(
                            nombreEmpresa));

                    //lo mismo para cargas peligrosas
                    combo_naturaleza_peligrosa_carga_entrega.getItems().clear();
                    ObservableList<ModeloNaturalezasPeligrosas> modeloNaturalezasPeligrosas = FXCollections.observableArrayList();
                    modeloNaturalezasPeligrosas.addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
                    combo_naturaleza_peligrosa_carga_entrega.setConverter(new NaturalezaPeligrosaStringConverter());
                    combo_naturaleza_peligrosa_carga_entrega.getItems().addAll(modeloNaturalezasPeligrosas);
                    // y una vez cargados, hay que seleccionar justo el que viene de doble click
                    combo_naturaleza_peligrosa_carga_entrega.getSelectionModel().
                            select(DaoNaturalezaPeligrosa.devuelvemeLaNaturalezaPeligrosaPCompletaPorCodigo(
                                    cargaYEntregaTemporalDesdeDobleClick.getCod_naturaleza_peligrosa()));


                    //ahora el tipo de bulto
                    combo_tipo_bulto_carga_entrega.getItems().clear();
                    ObservableList<ModeloTipoDeBulto> modeloTiposDeBultos = FXCollections.observableArrayList();
                    modeloTiposDeBultos.addAll(DaoTipoDeBulto.cargarListadTiposDeBultos());
                    combo_tipo_bulto_carga_entrega.setConverter(new ModeloTipoDeBultoStringConverter());
                    combo_tipo_bulto_carga_entrega.getItems().addAll(modeloTiposDeBultos);
                    // como siempre, ahora con los cargados que elija el que viene del doble click
                    combo_tipo_bulto_carga_entrega.getSelectionModel().select(
                            DaoTipoDeBulto.devuelvemeElTipoDeBultoCompletoPorCodigo(
                                    cargaYEntregaTemporalDesdeDobleClick.getTipo_bulto()));


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

                    tf_cantidad_bultos_carga_entrega.setText(String.valueOf(cargaYEntregaTemporalDesdeDobleClick.getCantidad_bultos()));


                }
            });
            return row;
        });
    }

    /**
     * Limpia el contenido del tab de envios, como tiene muchos componentes lo hacemos en tres pasos
     */
    @FXML
    private void onBotonLimpiarEnvio() {

        limpiarCheckBoxesEnTabView(tab_envios);
        limpiarComboBoxesEnTabView(tab_envios);
        limpiarTextFieldsEnTabView(tab_envios);
    }

    /**
     * Limpia el contenido del tab de Cargas y Entregas
     */
    @FXML
    private void onBotonLimpiarCargaEntrega() {

        limpiarCheckBoxesEnTabView(tab_carga_entrega);
        limpiarComboBoxesEnTabView(tab_carga_entrega);
        limpiarTextFieldsEnTabView(tab_carga_entrega);

        // Después de limpiar, hay que inicializar los desplegables y dejarlo listo para crear uno nuevo
        mostrarTablaCargaYEntrega();

    }

    /**
     * Llamada a limpiar el tab tab_sistema de carga
     */
    @FXML
    private void onBotonLimpiarTabSistemaDeCarga() {
        limpiarTextFieldsEnTabView(tab_sistema_carga_vehiculo);

    }

    /**
     * Llamada a limpiar el  tab_empresas
     */
    @FXML
    private void onBotonLimpiarTabEmpresas() {

        limpiarCheckBoxesEnTabView(tab_empresas);
        limpiarComboBoxesEnTabView(tab_empresas);
        limpiarTextFieldsEnTabView(tab_empresas);
    }

    /**
     * Llamada a limpiar el  tab_login
     */
    @FXML
    private void onBotonLimpiarTabLogin() {
        limpiarTextFieldsEnTabView(tab_login);
    }

    /**
     * Llamada a limpiar el tab tab_tipo_carnet
     */
    @FXML
    private void onBotonLimpiarTabTipoDeCarnet() {
        limpiarTextFieldsEnTabView(tab_tipo_carnet);
    }

    /**
     * Llamada a limpiar el tab tab_paises
     */
    @FXML
    private void onBotonLimpiarTabPaises() {
        limpiarTextFieldsEnTabView(tab_paises);

    }

    /**
     * Llamada a limpiar el tab tab_paises_provincias
     */
    @FXML
    private void onBotonLimpiarTabPaisesYProvincias() {
        limpiarTextFieldsEnTabView(tab_provincias);
    }

    /**
     * Llamada a limpiar el tab tab_codigos_postales
     */
    @FXML
    private void onBotonLimpiarTabPaisesYProvinciasYLocalidades() {
        limpiarTextFieldsEnTabView(tab_localidades);
    }

    /**
     * Llamada a limpiar el tab tab_codigos_postales
     */
    @FXML
    private void onBotonLimpiarTabPaisesYProvinciasYLocalidadesYCodigosPostales() {
        limpiarTextFieldsEnTabView(tab_codigos_postales);
    }

    /**
     * Llamada a limpiar el tab tab_vehiculos
     */
    @FXML
    public void onBotonLimpiarTabVehiculos() {
        limpiarTextFieldsEnTabView(tab_vehiculos);
    }

    /**
     * Llamada a limpiar el tab tab_naturalezas_peligrosas
     */
    @FXML
    public void onBotonLimpiarTabNaturalezaPeligrosa() {
        limpiarTextFieldsEnTabView(tab_naturalezas_peligrosas);
    }

    /**
     * Llamada a limpiar el tab tab_tipo_bulto
     */
    @FXML
    public void onBotonLimpiarTabTipoBultos() {
        limpiarTextFieldsEnTabView(tab_tipo_bulto);
    }

    /**
     * Llamada a limpiar el tab tab_conductorTieneVehiculo
     */
    @FXML
    public void onBotonLimpiarTabcConductorTieneVehiculo() {
        limpiarTextFieldsEnTabView(tab_conductorTieneVehiculo);
    }

////////////////////////////////////////////////////////////////////////////
/////////////////// ZONA DE ELIMINAR  ///////////////////
////////////////////////////////////////////////////////////////////////////

    /**
     * Limpia el tab tipo de vehiculos, tiene muchos checkbox para poder limpiar
     */
    @FXML
    public void onBotonLimpiarTipoDeVehiculoTabTipoDeVehiculos() {

        limpiarCheckBoxesEnTabView(tab_tipo_de_vehiculo);
        limpiarTextFieldsEnTabView(tab_tipo_de_vehiculo);
    }

    /**
     * Limpia los elementos del tab documento capacitación
     */
    @FXML
    public void onBotonLimpiarTabDocumentoCapacitacion() {
        limpiarTextFieldsEnTabView(tab_documento_capacitacion);
    }

    /**
     * Limpia todos los textfields del tab al que pasamos
     *
     * @param tabALimpiar el tab por el que va a iterar
     */

    private void limpiarTextFieldsEnTabView(Tab tabALimpiar) {
        for (Node node : tabALimpiar.getContent()
                .lookupAll(".text-field")) {
            if (node instanceof TextField) {
                ((TextField) node).setText("");
            }
        }
    }

    /**
     * Limpia todos los checkboxes del tab al que pasamos
     *
     * @param tabALimpiar el tab por el que va a iterar
     */

    private void limpiarCheckBoxesEnTabView(Tab tabALimpiar) {
        for (Node node : tabALimpiar.getContent()
                .lookupAll(".check-box")) {
            if (node instanceof CheckBox) {
                ((CheckBox) node).setSelected(false);
            }
        }
    }

    /**
     * Limpia todos los comboboxes del tab que le pasemos
     *
     * @param tabALimpiar el tab por el que va a iterar
     */
    private void limpiarComboBoxesEnTabView(Tab tabALimpiar) {
        for (Node node : tabALimpiar.getContent()
                .lookupAll(".combo-box")) {
            if (node instanceof ComboBox<?>) {
                ((ComboBox) node).setValue(null);
            }
        }
    }

    /**
     * Elimina un envio al ser pulsado el botón de la pantalla "Eliminar envio"
     */

    @FXML
    private void onBotonEliminarEnvio() {
        if (tf_tracker_tv_envio.getText() != null && DaoEnvio.eliminarEnvio(tf_tracker_tv_envio.getText())) {
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");

            mostrarTablaEnvios();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando" +
                    " doble click sobre él");
            logger.info("INFO intenta modificar algo que no existe");
        }
        mostrarTablaEnvios();
    }

    @FXML
    private void onBotonEliminarConductor() {
        if (tf_dni_conductores.getText() != null && DaoConductor.eliminarConductor(tf_dni_conductores.getText())) {
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Conductor eliminado");

            mostrarTablaConductores();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("Si quieres eliminar un conductor, escribe su dni en el campo DNI");
            logger.info("Intento elimianar un conductor sin exito");
        }
        mostrarTablaConductores();
    }

    /**
     * Elimina una línea de Carga y Entrega
     */
    @FXML
    private void onBotonBorrarCargaEntrega() {
        if (tf_codigo_carga_tab_carga_entrega.getText() != null
                && DaoCargaYEntrega.eliminarModeloCargaYEntrega(tf_codigo_carga_tab_carga_entrega.getText())) {

            limpiarComboBoxesEnTabView(tab_carga_entrega);
            limpiarCheckBoxesEnTabView(tab_carga_entrega);
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado carga ");
            mostrarTablaCargaYEntrega();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("INFO No hay nada que modificar, elige un registro a modificar pulsando doble click sobre él");
        }
        mostrarTablaCargaYEntrega();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab Login

     @FXML private void onBotonEliminarRolTabLogin() {
     //le damos el login que seleccióno al hacer doble clic
     ModeloLogin loginDesdeTextfields = new ModeloLogin(
     tf_usuario_tab_login.getText(),
     tf_contrasenya_tab_login.getText(),
     tf_papel_tab_login.getText());
     if (!tf_usuario_tab_login.getText().isEmpty() && DaoLogin.borrarLogin(loginDesdeTextfields)) {
     limpiarTextFieldsEnTabView(tab_login);
     mostrarTablaLogin();
     Alertas notificacion = new Alertas();
     notificacion.mostrarInformacion("Registro eliminado");
     logger.info("Registro eliminado");
     } else {
     Alertas alerta = new Alertas();
     alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
     "pulsando doble click sobre él");
     logger.error("No hay nada que modificar, elige un registro a modificar pulsando doble click sobre él");
     }
     }
     */

    /**
     * Elimina una línea de Sistema de Carga de Vehículo
     */
    @FXML
    private void onBotonEliminarTabSistemaCargaVehiculo() {
        if (tf_cod_sistema_carga_tv_sistema_carga.getText() != null &&
                DaoSistemaCargaVehiculo.eliminarModeloSistemaCargaVehiculo(
                        tf_cod_sistema_carga_tv_sistema_carga.getText())) {

            mostrarTablaSistemaCargaVehiculos();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando doble click sobre él");
        }
        mostrarTablaSistemaCargaVehiculos();
    }

    /**
     * Añade la funcionalidad de eliminar datos al tab empresas
     */
    @FXML
    private void onBotonEliminarDocumentoCapactiacionTabDocumentosCapacitacion() {
        //le damos el login que seleccióno al hacer doble clic
        if (DaoDocumentoCapacitacion.eliminarDocumentoCapacitacion(
                tf_codigo_capactiacion_tab_documento_capacitacion.getText())) {
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Eliminado dentro de las capacitaciones");

            mostrarTablaDocumentosCapacitacion();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe en capacitación");
        }
        mostrarTablaDocumentosCapacitacion();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab empresas
     */
    @FXML
    private void onBotonEliminarTipoDeVehiculoTabTipoDeVehiculo() {
        //le damos el login que seleccióno al hacer doble clic
        if (DaoTipoDeVehiculo.eliminarTipoDeVehiculo(tf_codigo_vehiculo_tab_tipo_vehiculo.getText())) {
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado de tipo de vehículo");

            mostrarTablaTipoDeVehiculo();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar" +
                    " pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe");
        }
        mostrarTablaTipoDeVehiculo();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab empresas
     */
    @FXML
    private void onBotonEliminarEmpresaTabEmpresa() {
        //le damos el login que seleccióno al hacer doble clic
        ModeloEmpresa empresaDesdeTextField = new ModeloEmpresa(
                tf_cif_tab_empresas.getText(),
                tf_nombre_tab_empresas.getText(),
                tf_domicilio_tab_empresas.getText(),
                // los siguientes datos no importan, eliminar es en función del cif
                null,
                null,
                null,
                null,
                null,
                null,
                null

        );
        if (DaoEmpresa.eliminarEmpresa(empresaDesdeTextField)) {

            mostrarTablaLogin();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado del tab empresa");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe en empresas");
        }
        mostrarTablaEmpresas();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab conductor tiene vehículo
     */
    @FXML
    private void onBotonEliminarConductorTieneVehiculoTabConductorTieneVehiculo() {
        //le damos el login que seleccióno al hacer doble clic
        ModeloConductorTieneVehiculo modeloAEliminar = new ModeloConductorTieneVehiculo(
                tf_codigo_conductor_conductor_tiene_vehiculo.getText(),
                tf_codigo_vehiculo_conductor_tiene_vehiculo.getText());
        if (DaoConductorTieneVehiculo.borrarConductoresTienenVehiculo(modeloAEliminar)) {

            mostrarTablaConductorTieneVehiculo();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado del conductor tiene vehículo");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar" +
                    " pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe en conductor tiene vehiculo");
        }
        mostrarTablaConductorTieneVehiculo();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab tipoDeCarnet
     */
    @FXML
    private void onBotonEliminarTipoDeCarnetTabTipoDeCarnet() {
        //le damos el tipo de carnet que seleccióno al hacer doble clic
        ModeloDeCarnet modeloDeCarnetDesdeDobleClick = new ModeloDeCarnet(
                tf_carnet_tab_tipo_carnet.getText());
        if (DaoTipoDeCarnet.borrarTipoDeCarnet(modeloDeCarnetDesdeDobleClick)) {

            mostrarTablaTipoDeCarnet();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado del tipo de carnet");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe en tipo de carnet");
        }
        mostrarTablaTipoDeCarnet();
    }

    /**
     * Elimina un tipo de bulto
     */
    @FXML
    private void onBotonEliminarTipoDeBultoTabTipoDeBulto() {
        //le damos el tipo de bulto que seleccióno al hacer doble clic
        ModeloTipoDeBulto modeloDeTipoDeBultoDesdeDobleClick = new ModeloTipoDeBulto(
                Integer.parseInt(tf_codigo_tipo_bulto_tab_tipo_bulto.getText()));
        if (DaoTipoDeBulto.eliminarTipoDeBulto(modeloDeTipoDeBultoDesdeDobleClick)) {


            mostrarTablaTiposDeBulto();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe en el tipo de bulto");
        }
        mostrarTablaTiposDeBulto();
    }

    /**
     * Elimina un pais desde la tabla tab Pais
     */
    @FXML
    private void onEliminarTabPaises() {
        ModeloPais modeloPais = new ModeloPais(tf_pais_tab_paises.getText());
        if (DaoPais.eliminarPais(modeloPais)) {

            mostrarTablaPaises();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado del tipo de país");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe entre los paises");
        }
        mostrarTablaPaises();
    }

    /**
     * Elimina un pais desde la tabla tab Pais
     */
    @FXML
    private void onEliminarRolTabLogin() {
        ModeloLogin ml = new ModeloLogin(tf_usuario_tab_login.getText(), tf_contrasenya_tab_login.getText(),
                tf_papel_tab_login.getText());
        if (DaoLogin.eliminarLogin(ml)) {


            mostrarTablaLogin();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado del bloque de login");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe entre los logins");
        }
        mostrarTablaLogin();
    }

    /**
     * Elimina una Naturaleza Peligrosa desde su tab
     */
    @FXML
    private void onBotonEliminarNaturalezaPeligrosaTabNaturalezaPeligrosa() {
        if (tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText().isBlank()) {
            //nada que hacer, estaba vacío
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
        } else {
            if (DaoNaturalezaPeligrosa.eliminarNaturalezaPeligrosa(
                    tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText())) {


                mostrarTablaNaturalezaPeligrosa();
                Alertas notificacion = new Alertas();
                notificacion.mostrarInformacion("Registro eliminado");
                logger.info("Registro eliminado de naturalezas peligrosas");
            } else {
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar" +
                        " pulsando doble click sobre él");
                logger.error("Intenta eliminar algo que no existe entre las naturalezas peligrosas");

            }
            mostrarTablaNaturalezaPeligrosa();
        }
    }

    /**
     * Elimina un vehiculo desde Tab Vehiculos
     */
    @FXML
    private void onBotonEliminarVehiculoTabVehiculos() {
        //le damos el tipo de carnet que seleccióno al hacer doble clic
        ModeloVehiculo modeloDeVehiculoDesdeDobleClick = getModeloVehiculo();

        if (DaoVehiculo.elminarVehiculo(modeloDeVehiculoDesdeDobleClick)) {

            mostrarTablaVehiculos();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado de vehículos");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando " +
                    "doble click sobre él");
            logger.error("Intenta eliminar algo que no existe entre los vehículos");
        }
        mostrarTablaVehiculos();
    }

    /**
     * Elimina un objeto de tipo Codigo Postal en función de los datos de los campos TextFields de su tab
     */
    @FXML
    private void onBotonEliminarTabPaisesYProvinciasYLocalidadesYCodigosPostales() {
        //le damos el tipo de bulto que seleccióno al hacer doble clic
        ModeloPaisYProvinciaYLocalidadYCodigoPostal modeloPaisYProvinciaYLocalidadYCodigoPostal =
                new ModeloPaisYProvinciaYLocalidadYCodigoPostal(
                        Integer.valueOf(tf_codigo_postal_tab_codigo_postales.getText()),
                        tf_localidad_tab_codigo_postales.getText(),
                        tf_provincia_tab_codigo_postales.getText(),
                        tf_pais_tab_codigo_postales.getText());

        if (DaoPaisYProvinciaYLocalidadYCodigoPostal.eliminarCodigoPostal(modeloPaisYProvinciaYLocalidadYCodigoPostal)) {

            mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado entre los cp");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe entre los CP");
        }
        mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales();
    }

    /**
     * Elimina una provincia de la base de datos
     */
    @FXML
    private void onEliminarTabProvincias() {
        ModeloPaisYProvincia mp = new ModeloPaisYProvincia(tf_pais_tab_paisesyprovincias.getText(),
                tf_provincia_tab_paisesyprovincias.getText());
        if (DaoPaisYProvincia.eliminarProvincia(mp)) {


            mostrarTablaPaisesYProvincias();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado entre las provincias");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.error("Intenta eliminar algo que no existe entre las provincias");
        }
        mostrarTablaPaisesYProvincias();
    }

    /**
     * Elimina una localidad de la base de datos
     */
    @FXML
    private void onBotonEliminarTabLocalidad() {
        ModeloPaisYProvinciaYLocalidad mp = new ModeloPaisYProvinciaYLocalidad(tf_pais_tab_localidades.getText(),
                tf_provincia_tab_localidades.getText(), tf_localidad_tab_localidades.getText());
        if (DaoPaisYProvinciaYLocalidad.eliminarLocalidad(mp)) {

            mostrarTablaPaisesYProvinciasYLocalidades();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro eliminado");
            logger.info("Registro eliminado de las localidades");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando " +
                    "doble click sobre él");
            logger.error("Intenta eliminar algo que no existe entre las localidades");
        }
        mostrarTablaPaisesYProvinciasYLocalidades();
    }

    /// /////////////////////////////////////////////////////////////////////////
    /// //////////////// ZONA DE MODIFICAR  ///////////////////
    /// /////////////////////////////////////////////////////////////////////////


    @FXML
    private void onBotonModificarEnvio() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        ModeloEnvio modeloEnvioModificado = new ModeloEnvio(
                tf_tracker_tv_envio.getText(),
                Integer.parseInt(tf_carga_entrega_tv_envio.getText()),
                tf_conductor_tv_envio.getText(),
                tf_vehiculo_tv_envio.getText());
        if (DaoEnvio.modificarEnvio(tf_tracker_tv_envio.getText(), modeloEnvioModificado)) {
            // éxito en la actualización, toca refrescar tabla

            mostrarTablaEnvios();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando " +
                    "doble click sobre él");
            logger.info("No puedo modificar el envio");
        }
    }

    /**
     * modifica una provincia
     */
    @FXML
    private void onModificarTabProvincia() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        ModeloPaisYProvincia nuevaProvincia = new ModeloPaisYProvincia(tf_pais_tab_paisesyprovincias.getText(),
                tf_provincia_tab_paisesyprovincias.getText());
        if (DaoPaisYProvincia.modificarProvincia(paisYProvinciaSeleccionadoTemporalDesdeDobleClick, nuevaProvincia)) {

            // éxito en la actualización, toca refrescar tabla


            mostrarTablaPaisesYProvincias();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar" +
                    " pulsando doble click sobre él");
            logger.info("No puedo modificar provincias");

        }
    }

    /**
     * Con los datos que hay en la pantalla lanza una modificación de un dato
     */
    @FXML
    private void onBotonModificarCargaEntrega() {

        ModeloCargaYEntrega cargaYEntrega = new ModeloCargaYEntrega(
                Integer.parseInt(tf_codigo_carga_tab_carga_entrega.getText()),
                //hay que convertir las cadenas en timestamps,

                ConversorDataPickerTimeStamp.DeStringATimeStamp(
                        dp_dia_inicio_carga_entrega.getValue().toString() + " " +
                                tf_hora_inicio_carga_entrega.getText()),


                ConversorDataPickerTimeStamp.DeStringATimeStamp(
                        dp_dia_fin_carga_entrega.getValue().toString() + " " +
                                tf_hora_fin_carga_entrega.getText()),


                Float.valueOf(tf_dim_l_tab_carga_entrega.getText()),
                Float.valueOf(tf_dim_a_tab_carga_entrega.getText()),
                Float.valueOf(tf_dim_h_tab_carga_entrega.getText()),
                Float.valueOf(tf_peso_unidad_tab_carga_entrega.getText()),
                cxbx_manipulacion_carga_entrega.isSelected(),
                cbx_remontable_carga_entrega.isSelected(),

                combo_tipo_bulto_carga_entrega.getValue().getCod_tipo_bulto(),
                cxbx_dblconductor_carga_entrega.isSelected(),

                cxbx_grupaje_carga_entrega.isSelected(),
                cxbx_necesita_rampa_carga_entrega.isSelected(),
                combo_naturaleza_peligrosa_carga_entrega.getValue().getCodigo_naturaleza_peligrosa(),
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
                cxbx_ya_servida.isSelected(), // en esta pantalla no se indica si esta reservado o no
                Integer.valueOf(tf_cantidad_bultos_carga_entrega.getText()),

                ConversorDataPickerTimeStamp.DeStringATimeStamp(dp_fecha_creaccion.getValue().toString()
                        + " " + "00:00"), //el cliente no valora más que el día, la hora nos la inventamos para el parseador
                combo_empresa_carga_entrega.getValue().getCif()


        );

        if (DaoCargaYEntrega.modificarModeloCargaYEntrega(Integer.valueOf(tf_codigo_carga_tab_carga_entrega.getText()),
                cargaYEntrega)) {
            Alertas informa = new Alertas();
            informa.mostrarInformacion("Actualización con éxito");
            limpiarCheckBoxesEnTabView(tab_carga_entrega);
            limpiarComboBoxesEnTabView(tab_carga_entrega);
            limpiarTextFieldsEnTabView(tab_carga_entrega);
            mostrarTablaCargaYEntrega();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige una Carga y Entrega a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No puedo cambiar la carga");

        }


    }

    /**
     * Modifica un documento de capacitación en función de los textfields de la pantalla
     */
    @FXML
    private void onBotonModificarDocumentoCapacitacion() {
        // ImageViewABlob im = new ImageViewABlob();
        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_tv_documento_capacitacion);

        ModeloDocumentoCapacitacion modeloDocumentoCapacitacion = new ModeloDocumentoCapacitacion();
        modeloDocumentoCapacitacion.setCod_capacitacion(tf_codigo_capactiacion_tab_documento_capacitacion.getText());
        modeloDocumentoCapacitacion.setPeligrosidad(cmbx_peligrosidad_tab_documento_capacitacion.
                getSelectionModel().getSelectedItem().getCodigo_naturaleza_peligrosa());
        modeloDocumentoCapacitacion.setFoto(blobDeImagen);

        if (DaoDocumentoCapacitacion.modificarDocumentoCapacitacion(
                documentoCapacitacionTemporalDesdeDobleClick, modeloDocumentoCapacitacion)) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaDocumentosCapacitacion();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, " +
                    "fallo de documento de capacitación");
        }
    }

    /**
     * Modifica un tipo De bulto
     */

    @FXML
    private void onBotonModificarTipoDeBultoTabTipoDeBultos() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir

        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_imagen_tipo_bulto_tab_tipo_bulto);
        ModeloTipoDeBulto modeloTipoDeBultoModificado = new ModeloTipoDeBulto(
                Integer.parseInt(tf_codigo_tipo_bulto_tab_tipo_bulto.getText()),
                tf_nombre_tipo_bulto_tab_tipo_bulto.getText(),
                blobDeImagen);

        if (DaoTipoDeBulto.modificarTipoDeBulto(tipoDeBultoTemporalDesdeDobleClick, modeloTipoDeBultoModificado)) {
            // éxito en la actualización, toca refrescar tabla


            mostrarTablaTiposDeBulto();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar en los tipos de bultos");

        }
    }

    /**
     * Añade la funcionalidad de modificar al TAB Empresa en su botón, Modificar Empresa. Lee el texto
     * previo y se lo envía al Dao modifica
     */
    @FXML
    private void onBotonModificarEmpresaTabEmpresas() {
        //creo una nueva empresa con los datos actuales de los textfields
        ModeloEmpresa modeloEmpresaModificado = getModeloEmpresa();

        modeloEmpresaModificado.setCorreo(tf_correo_tab_empresas.getText());
        modeloEmpresaModificado.setTelefono(tf_telefono_tab_empresas.getText());
        modeloEmpresaModificado.setPersona_contacto(tf_persona_contacto_tab_empresas.getText());

        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        if (DaoEmpresa.modificarEmpresa(empresaSeleccionadoTemporalDesdeDobleClick, modeloEmpresaModificado)) {
            // éxito en la actualización, toca refrescar tabla


            mostrarTablaEmpresas();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, en el bloque de empresas");

        }
    }

    /**
     * Cambia el sistema de carga de vehículo a partir de los datos de los textfields de la pantalla
     */

    @FXML
    private void onBotonModificarTabSistemaCargaVehiculo() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        if (DaoSistemaCargaVehiculo.modificarModeloSistemaCargaVehiculo(sistemaCargaVehiculoTemporalDesdeDobleClick,
                tf_descripcion_tv_sistema_carga.getText())) {
            // éxito en la actualización, toca refrescar tabla


            mostrarTablaSistemaCargaVehiculos();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando " +
                    "doble click sobre él");
            logger.info("No hay nada que modificar, en las sistemas de cargas de vehículos");

        }

    }

    /**
     * Modifica un conductor previamente seleccionado de la tabla de conductores
     * ver funcionDobleClickParaTableViewConductor
     */
    @FXML
    private void onBotonModificarConductor() {
        Alertas alertas = new Alertas();
        alertas.mostrarInformacion("Para modificar un conductor, haz doble click sobre su fila");
        // funcionDobleClickParaTableViewConductor();
    }

    /**
     * Modifica la relación entre un conductor y el coche que tiene asociado a partir del dni de conductor
     * obtenido de los textfields
     */
    @FXML
    private void onBotonModificarConductorTieneVehiculo() {
        if (DaoConductorTieneVehiculo.modificarConductorTieneVehiculo(
                conductorTieneVehiculoTemporalDesdeDobleClick, tf_codigo_vehiculo_conductor_tiene_vehiculo.getText())) {
            mostrarTablaConductorTieneVehiculo();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, no puedo cambiar el vehículo");
        }
    }

    /**
     * Añade la funcionalidad de modificar al TAB Pais en su botón, Modificar Pais. Lee el texto previo y
     * se lo envía al Dao.
     */
    @FXML
    private void onBotonModificarPaisTabPais() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        if (DaoPais.modificarPais(paisSeleccionadoTemporalDesdeDobleClick, tf_pais_tab_paises.getText())) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaPaises();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando " +
                    "doble click sobre él");
            logger.info("No hay nada que modificar, en el tab país");

        }
    }

    /**
     * Añade la funcionalidad modificar dataos al tab de naturaleza peligrosa
     */
    @FXML
    private void onBotonModificarNaturalezaPeligrosaTabNaturalezaPeligrosa() {
        //le damos el login que seleccióno al hacer doble clic, y además el nuevo texto a incluir que será el nuevo rol
        ModeloNaturalezasPeligrosas modeloNaturalezasPeligrosas = new ModeloNaturalezasPeligrosas(
                Float.parseFloat(tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText()),
                tf_descripcion_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText());
        if (DaoNaturalezaPeligrosa.modificarCodigoYNaturaleza(
                Float.parseFloat(tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText()),
                tf_descripcion_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText())) {
            mostrarTablaNaturalezaPeligrosa();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar pulsando " +
                    "doble click sobre él");
            logger.info("No hay nada que modificar, en las naturalezas peligrosas");

        }
    }

    /**
     * Añade la funcionalidad de modificar datos al tab Login
     */
    @FXML
    private void onBotonModificarRolTabLogin() {
        //le damos el login que seleccióno al hacer doble clic, y además el nuevo texto a incluir que será el nuevo rol
        if (DaoLogin.modificarLogin(loginSeleccionadoTemporalDesdeDobleClick, tf_papel_tab_login.getText())) {


            mostrarTablaLogin();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, en el rol de login");

        }
    }

    /**
     * Modifica una entrada en Vehiculos
     */
    @FXML
    public void onBotonModificarTabVehiculos() {
        //le damos el login que seleccióno al hacer doble clic, y además el nuevo texto a incluir que será el nuevo rol
        if (DaoVehiculo.modificarVehiculo(vehiculoSeleccionadoTemporalDesdeDobleClick,
                vehiculoSeleccionadoTemporalDesdeDobleClick.getCod_vehiculo())) {
            mostrarTablaVehiculos();


        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, el el tab vehículos");

        }
    }


////////////////////////////////////////////////////////////////////////////
/////////////////// ZONA DE NUEVOS  ///////////////////
////////////////////////////////////////////////////////////////////////////

    /**
     * Añade la funcionalidad de modificar datos a la tabla PaisProvincias
     */
    @FXML
    private void onBotonModificarPaisYProvinciaTabPaisYProvincia() {
        //le damos el pais con provincia que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        ModeloPaisYProvincia modeloPaisYProvincia = new ModeloPaisYProvincia(
                paisYProvinciaSeleccionadoTemporalDesdeDobleClick.getPais(), tf_provincia_tab_paisesyprovincias.getText());
        if (DaoPaisYProvincia.modificarProvincia(paisYProvinciaSeleccionadoTemporalDesdeDobleClick, modeloPaisYProvincia)) {
            // éxito en la actualización, toca refrescar tabla


            mostrarTablaPaisesYProvincias();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, en provincias");

        }
    }

    /**
     * Añade la funcionalidad de modificar datos a la tabla PaisProvincias
     */
    @FXML
    private void onBotonModificarPaisYProvinciaYCodigoPostalTabPaisYProvinciaYCodigoPostal() {
        //le damos el pais con provincia que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        if (DaoPaisYProvinciaYLocalidadYCodigoPostal.modificarCodigoPostal(
                paisYProvinciaYLocalidadYCodigoPostalSeleccionadoTemporalDesdeDobleClick,
                Integer.valueOf(tf_codigo_postal_tab_codigo_postales.getText()))) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar," +
                    " elige un registro a modificar pulsando doble click sobre él");
            logger.info("No hay nada que modificar, en el código postal");
        }
    }

    /**
     * Añade la funcionalidad de modificar al TAB Pais en su botón, Modificar Pais. Lee el texto
     * previo y se lo envía al Dao modificar
     */
    @FXML
    private void onBotonModificarTipoDeCarnetTabTipoDeCarnet() {
        //le damos el carnet que selección al hacer doble clic, y además el nuevo texto a incluir
        if (DaoTipoDeCarnet.modificarCarnet(carnetSeleccionadoTemporalDesdeDobleClick, tf_carnet_tab_tipo_carnet.getText())) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaTipoDeCarnet();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, en el tipo de carnet");
        }
    }

    /**
     * Modifica un tipo de vehículo en función de los datos de los TextField
     */

    @FXML
    private void onBotonModificarTipoDeVehiculoTabTipoDeVehiculo() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        // ImageViewABlob im = new ImageViewABlob();
        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_tv_tipo_vehiculo);
        ModeloTipoDeVehiculo modeloTipoDeVehiculo = new ModeloTipoDeVehiculo(
                tf_codigo_vehiculo_tab_tipo_vehiculo.getText(),
                tf_estipulacion_tab_tipo_vehiculo.getText(),
                blobDeImagen,
                tf_instrucciones_tab_tipo_vehiculo.getText(),
                cxbx_atp_tab_tipo_vehiculo.isSelected(),
                cxbx_adr_tab_tipo_vehiculo.isSelected(),
                cmbx_sistema_carga_tab_tipo_vehiculo.getSelectionModel().getSelectedItem().getCod_sistema_carga(),
                // Integer.valueOf(tf_sistema_carga_tab_tipo_vehiculo.getText()),
                cxbx_rampa_tab_tipo_vehiculo.isSelected());
        if (DaoTipoDeVehiculo.modificarTipoDeVehiculo(tipoDeVehiculoTemporalDesdeDobleClick, modeloTipoDeVehiculo)) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaTipoDeVehiculo();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, elige un registro a modificar " +
                    "pulsando doble click sobre él");
            logger.info("No hay nada que modificar, en el tipo de vehículo");
        }
    }

    /**
     * Modificar una localidad cambiando su nombre
     */
    @FXML
    private void onBotonModificarTabLocalidad() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        if (DaoPaisYProvinciaYLocalidad.modificarLocalidad(paisYProvinciaYLocalidadSeleccionadoTemporalDesdeDobleClick,
                tf_localidad_tab_localidades.getText())) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaPaisesYProvinciasYLocalidades();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que modificar, " +
                    "elige un registro a modificar pulsando doble click sobre él");
            logger.info("No hay nada que modificar, elige un registro a modificar pulsando doble click sobre él");
        }
    }

    /**
     * Nuevo envío lanzado desde este botón
     */
    @FXML
    private void onBotonNuevoEnvio() {
        ModeloEnvio modeloNuevo = new ModeloEnvio(tf_tracker_tv_envio.getText(),
                Integer.parseInt(tf_carga_entrega_tv_envio.getText()),
                tf_conductor_tv_envio.getText(),
                tf_vehiculo_tv_envio.getText());
        if (DaoEnvio.nuevoEnvio(modeloNuevo)) {
            // éxito en la actualización, toca refrescar tabla
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado con éxito");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir a los envios");
        }
        mostrarTablaEnvios();
    }

//
//    /**
//     * Añade la funcionalidad Nuevo al TAB Tipo de vehículo en su botón, nuevo Tipo de Vehículo.
//     */
//
//    @FXML
//    private void onBotonNuevoTabTipoDeVehiculos() {
//        // ImageViewABlob im = new ImageViewABlob();
//        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_tv_tipo_vehiculo);
//
//        if (tf_codigo_vehiculo_tab_tipo_vehiculo.getText() != null &&
//                tf_codigo_vehiculo_tab_tipo_vehiculo.getText() != null) {
//            ModeloTipoDeVehiculo modeloNuevo = new ModeloTipoDeVehiculo(
//                    tf_codigo_vehiculo_tab_tipo_vehiculo.getText(),
//
//                    tf_estipulacion_tab_tipo_vehiculo.getText(),
//                    blobDeImagen,
//                    tf_instrucciones_tab_tipo_vehiculo.getText(),
//                    cxbx_atp_tab_tipo_vehiculo.isSelected(),
//                    cxbx_adr_tab_tipo_vehiculo.isSelected(),
//                    cmbx_sistema_carga_tab_tipo_vehiculo.getSelectionModel().getSelectedItem().getCod_sistema_carga(),
//                    cxbx_rampa_tab_tipo_vehiculo.isSelected());
//
//            if (DaoTipoDeVehiculo.nuevoTipoDeVehiculo(modeloNuevo)) {
//                // éxito en la actualización, toca refrescar tabla
//
//                mostrarTablaConductorTieneVehiculo();
//                Alertas notificacion = new Alertas();
//                notificacion.mostrarInformacion("Registro creado");
//                logger.info("Registro creado con éxito en la base de datos nuevo tipo de vehiculos");
//            } else {
//                Alertas alerta = new Alertas();
//                alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
//                logger.info("No hay nada que añadir, has dejado campos sin rellenar que llenar los campos de texto");
//            }
//        }
//
//    }

    /**
     * Nuevo Sistema de Carga para añadir Sistema de carga de vehiculos
     */
    @FXML
    private void onBotonNuevoSistemaCargaTabSistemaCargaVehiculo() {
        //le damos el Sistema de Carga, y además el nuevo texto a incluir
        ModeloSistemaCargaVehiculo modeloNuevo = new ModeloSistemaCargaVehiculo(
                tf_cod_sistema_carga_tv_sistema_carga.getText(), tf_descripcion_tv_sistema_carga.getText());
        if (DaoSistemaCargaVehiculo.nuevoModeloSistemaCargaVehiculo(modeloNuevo)) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaSistemaCargaVehiculos();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado con éxito en bbdd");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir ");
        }
        mostrarTablaSistemaCargaVehiculos();
    }

    /**
     * Nuevo Carga y entrega para añadir carga y entrega
     */
    @FXML
    private void onBotonNuevaCargaEntrega() {
//Creamos un objeto desde vacío
        ModeloCargaYEntrega modeloCargaYEntregaNuevo = new ModeloCargaYEntrega(
                Integer.parseInt(tf_codigo_carga_tab_carga_entrega.getText()),
                //hay que convertir las cadenas en timestamps,
                ConversorDataPickerTimeStamp.DeStringATimeStamp(
                        dp_dia_inicio_carga_entrega.getValue().toString() + " "
                                + tf_hora_inicio_carga_entrega.getText()),
                ConversorDataPickerTimeStamp.DeStringATimeStamp(
                        dp_dia_fin_carga_entrega.getValue().toString() + " "
                                + tf_hora_fin_carga_entrega.getText()),
                Float.valueOf(tf_dim_l_tab_carga_entrega.getText()),
                Float.valueOf(tf_dim_a_tab_carga_entrega.getText()),
                Float.valueOf(tf_dim_h_tab_carga_entrega.getText()),
                Float.valueOf(tf_peso_unidad_tab_carga_entrega.getText()),
                cxbx_manipulacion_carga_entrega.isSelected(),
                cbx_remontable_carga_entrega.isSelected(),
                combo_tipo_bulto_carga_entrega.getValue().getCod_tipo_bulto(),

                cxbx_dblconductor_carga_entrega.isSelected(),

                cxbx_grupaje_carga_entrega.isSelected(),
                cxbx_necesita_rampa_carga_entrega.isSelected(),
                combo_naturaleza_peligrosa_carga_entrega.getValue().getCodigo_naturaleza_peligrosa(),
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
                cxbx_ya_servida.isSelected(), // en esta pantalla no se indica si esta reservado o no
                Integer.valueOf(tf_cantidad_bultos_carga_entrega.getText()),
                ConversorDataPickerTimeStamp.DeStringATimeStamp(
                        dp_fecha_creaccion.getValue().toString()
                                + " "
                                + "00:00"), //el converter necesita que sea el formato año mes dia hora minuto.
                combo_empresa_carga_entrega.getValue().getCif()

        );
        //Llamamos al DAO y lo metemos

        if (DaoCargaYEntrega.nuevoModeloCargaYEntrega(modeloCargaYEntregaNuevo)
                && tf_codigo_carga_tab_carga_entrega.getText() != null) {
            // éxito en la actualización, toca refrescar tabla
            limpiarCheckBoxesEnTabView(tab_carga_entrega);
            limpiarComboBoxesEnTabView(tab_carga_entrega);
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado en la base de datos");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir");
        }
        mostrarTablaCargaYEntrega();
    }

    /**
     * Al pulsar nuevo sobre el boton abre una ventana para crear un nuevo conductor.
     * Es tal la cantidad de datos del Conductor que la vamos a sacar a un FXML distinto
     */
    @FXML
    private void onBotonNuevoTabConductor() {

        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/nuevoConductor.fxml"),bundle);

        Parent root;
        try {
            root = loader.load();
            ModificarConductorController controlador = loader.getController();
            //creo un nuevo conductor vacío para que la ventana aparezca vacía
            ModeloConductor modeloConductorNuevo = new ModeloConductor();
            modeloConductorNuevo.setCodigo_postal(0);
            controlador.cargarUnConductorConcreto(modeloConductorNuevo);
            Scene scene = new Scene(root);
            // nuevo escenario de tipo modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //Asignamos el owner de la nueva ventana
            stage.initOwner(this.tv_conductores.getScene()
                    .getWindow());
            // esto hace que IntelliJ sepa donde está la imagen en función de la raíz del proyecto
            // en otros ides, por ejemplo, code, no va a funcionar por cómo se hace referencia a la raíz de user.dir
            Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
            stage.getIcons().add(icono);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Crear un nuevo conductor");
            //Lanzamos
            stage.showAndWait();
            tv_conductores.refresh();

        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Error de entrada y salida");
            logger.error("Error de entrada y salida");
            alerta.mostrarError(e.getMessage());
        }
        mostrarTablaConductores();
    }

    /**
     * Añade la funcionalidad de modificar al TAB empresa en su botón, Modificar Empresa.
     * Lee el texto previo y se lo envía al Dao.
     */
    @FXML
    private void onBotonNuevaEmpresaTabEmpresa() {

        ModeloEmpresa modeloEmpresaModificado = getModeloEmpresa();
        if (DaoEmpresa.nuevaEmpresa(modeloEmpresaModificado)) {
            // éxito en la actualización, toca refrescar tabla
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado con éxito en la base de datos");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar los campos de texto antes");
        }
        mostrarTablaEmpresas();
    }

    /**
     * Mira los tf y crea un modelo de empresa
     *
     * @return modelo de empresa desde los tf
     */
    private ModeloEmpresa getModeloEmpresa() {
        ModeloEmpresa modeloEmpresaModificado = new ModeloEmpresa();
        modeloEmpresaModificado.setCif(tf_cif_tab_empresas.getText());
        modeloEmpresaModificado.setNombre(tf_nombre_tab_empresas.getText());
        modeloEmpresaModificado.setDomicilio(tf_domicilio_tab_empresas.getText());
        modeloEmpresaModificado.setPais(cmb_pais_empresas.getValue().getPais());
        modeloEmpresaModificado.setProvincia(cmb_provincias_empresas.getValue().getProvincia());
        modeloEmpresaModificado.setLocalidad(cmb_localidad_empresas.getValue().getLocalidad());
        modeloEmpresaModificado.setCP(cmb_cp_empresas.getValue().getCodigoPostal());
        return modeloEmpresaModificado;
    }

    /**
     * Añade la funcionalidad de modificar al TAB PaisYProvincia en su botón, nuevo Pais. Lee el texto previo y
     * se lo envía al Dao Modifica
     */
    @FXML
    private void onBotonNuevoTabConductorTieneVehiculo() {
        if (tf_codigo_conductor_conductor_tiene_vehiculo.getText() != null &&
                tf_codigo_vehiculo_conductor_tiene_vehiculo.getText() != null) {
            ModeloConductorTieneVehiculo modeloNuevo = new ModeloConductorTieneVehiculo(
                    tf_codigo_conductor_conductor_tiene_vehiculo.getText(),
                    tf_codigo_vehiculo_conductor_tiene_vehiculo.getText());
            if (DaoConductorTieneVehiculo.nuevoConductorTieneVehiculo(modeloNuevo)) {
                // éxito en la actualización, toca refrescar tabla
                mostrarTablaConductorTieneVehiculo();
                Alertas notificacion = new Alertas();
                notificacion.mostrarInformacion("Registro creado");
                logger.info("Registro creado con éxito en vehículos");
            } else {
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
                logger.info("No hay nada que añadir, tienes que llenar los campos de texto antes de escribir");
            }
        }
        mostrarTablaConductorTieneVehiculo();
    }

    /**
     * Crea una entrada en Naturaleza Peligrosa cuando se pulsa el botón de naturaleza peligrosa
     */
    @FXML
    private void onBotonNuevaNaturalezaPeligrosaTabNaturalezaPeligrosa() {
        if (tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText() != null &&
                tf_descripcion_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText() != null) {
            ModeloNaturalezasPeligrosas modeloNuevo = new ModeloNaturalezasPeligrosas(
                    Float.parseFloat(tf_codigo_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText()),
                    tf_descripcion_naturaleza_peligrosa_tab_naturaleza_peligrosa.getText());
            if (DaoNaturalezaPeligrosa.nuevaNaturalezaPeligrosa(modeloNuevo)) {
                // éxito en la actualización, toca refrescar tabla
                mostrarTablaNaturalezaPeligrosa();
                Alertas notificacion = new Alertas();
                notificacion.mostrarInformacion("Registro creado");
                logger.info("Registro creado en las naturalezas peligrosas");
            } else {
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
                logger.info("No hay nada que añadir, faltan por llenar los campos de texto");
            }
        }
        mostrarTablaNaturalezaPeligrosa();
    }

    /**
     * Añade la funcionalidad de modificar al TAB PaisYProvincia en su botón, nuevo Pais. Lee el texto previo y se lo envía al Dao
     */
    @FXML
    private void onBotonNuevoTabPaisesYProvincias() {
        if (tf_pais_tab_paisesyprovincias.getText() != null && tf_provincia_tab_paisesyprovincias.getText() != null) {
            ModeloPaisYProvincia modeloNuevo = new ModeloPaisYProvincia(
                    tf_pais_tab_paisesyprovincias.getText(), tf_provincia_tab_paisesyprovincias.getText());
            if (DaoPaisYProvincia.nuevoPaisYProvincia(modeloNuevo)) {
                // éxito en la actualización, toca refrescar tabla
                mostrarTablaPaisesYProvincias();
                Alertas notificacion = new Alertas();
                notificacion.mostrarInformacion("Registro creado");
                logger.info("Registro creado en los paises y provincias");
            } else {
                Alertas alerta = new Alertas();
                alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
                logger.info("No hay nada que añadir, faltan por rellenar los campos de texto");
            }
        }
        mostrarTablaPaisesYProvincias();
    }

    /**
     * Añade la funcionalidad de modificar al TAB Pais en su botón, Modificar Pais. Lee el texto previo y se lo envía al Dao modifica
     */
    @FXML
    private void onBotonNuevoCodigoPostalTabCodigoPostal() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        ModeloPaisYProvinciaYLocalidadYCodigoPostal modeloNuevo = new ModeloPaisYProvinciaYLocalidadYCodigoPostal(
                Integer.valueOf(tf_codigo_postal_tab_codigo_postales.getText()),
                tf_localidad_tab_codigo_postales.getText(),
                tf_provincia_tab_codigo_postales.getText(),
                tf_pais_tab_codigo_postales.getText()
        );
        if (DaoPaisYProvinciaYLocalidadYCodigoPostal.nuevoCodigoPostal(modeloNuevo)) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado entre los códigos postales");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar los campos de texto");
        }
        mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales();
    }

    /**
     * Añade la funcionalidad de nuevo al TAB modelo de documentación en su botón
     */
    @FXML
    private void onBotonNuevoDocumentoCapacitacion() {
        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_tv_documento_capacitacion);
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        ModeloDocumentoCapacitacion modeloDocumentoCapacitacion = null;
        try {
            modeloDocumentoCapacitacion = new ModeloDocumentoCapacitacion(
                    tf_codigo_capactiacion_tab_documento_capacitacion.getText(),
                    cmbx_peligrosidad_tab_documento_capacitacion.getSelectionModel().getSelectedItem().getCodigo_naturaleza_peligrosa(),
                    blobDeImagen);
        } catch (Exception e) {
            logger.error("Ni siquiera he podido crear el objeto modeloDocumentoCapacitacion", e);
        }


        if (DaoDocumentoCapacitacion.nuevoDocumentoCapacitacion(Objects.requireNonNull(modeloDocumentoCapacitacion))) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaDocumentosCapacitacion();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado de capacitación");
            tv_documento_capacitacion.refresh();
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("Faltan rellenar campos");
            tv_documento_capacitacion.refresh();
        }
        mostrarTablaDocumentosCapacitacion();
    }

    /**
     * Añade la funcionalidad de modificar al TAB Pais en su botón, Modificar Pais. Lee el texto previo y se lo envía al Dao.
     */
    @FXML
    private void onBotonNuevoPaisTabPais() {
        //le damos el pais que seleccióno al hacer doble clic, y además el nuevo texto a incluir
        ModeloPais modeloNuevo = new ModeloPais(tf_pais_tab_paises.getText());
        if (DaoPais.nuevoPais(modeloNuevo)) {
            // éxito en la actualización, toca refrescar tabla
            mostrarTablaPaises();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado en el tab país");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("Faltan campos por rellenar");
        }
        mostrarTablaPaises();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab Login
     */
    @FXML
    private void onBotonNuevoTabLocalidad() {
        //vamos a convertir los strings de los diferentes textfields en un objeto de tipo Login nuevo
        // si todos los campos están bien formateados construimos el objeto y lo llevamos a añadir
        // si no, se lanza una advertencia sobre lo que ha fallado
        ModeloPaisYProvinciaYLocalidad mloc = new ModeloPaisYProvinciaYLocalidad(
                tf_pais_tab_localidades.getText(),
                tf_provincia_tab_localidades.getText(),
                tf_localidad_tab_localidades.getText());
        if (DaoPaisYProvinciaYLocalidad.nuevoPaisYProvinciaYLocalidades(mloc)) {
            mostrarTablaPaisesYProvinciasYLocalidades();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Éxito en la creación del registro en  base de datos");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("Faltan campos por rellenar y no he podido crearlo");
        }
        mostrarTablaPaisesYProvinciasYLocalidades();
    }

    /**
     * Añade la funcionalidad de modificar datos al tab Login
     */
    @FXML
    private void onBotonNuevoTabLogin() {
        //vamos a convertir los strings de los diferentes textfields en un objeto de tipo Login nuevo
        // si todos los campos están bien formateados construimos el objeto y lo llevamos a añadir
        // si no, se lanza una advertencia sobre lo que ha fallado
        ModeloLogin loginDesdeTextfields = new ModeloLogin(
                tf_usuario_tab_login.getText(),
                tf_contrasenya_tab_login.getText(),
                tf_papel_tab_login.getText());
        if (DaoLogin.nuevoLogin(loginDesdeTextfields)) {
            mostrarTablaLogin();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado en tab login");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar los campos de texto antes.");
        }
        mostrarTablaLogin();
    }

    /**
     * Añade la funcionalidad de modificar al TAB Pais en su botón, Modificar Pais. Lee el texto previo y se lo envía al Dao modificar
     */
    @FXML
    private void onBotonNuevoTipoDeCarnetTabTipoDeCarnet() {
        ModeloDeCarnet nuevoTipoCarnet = new ModeloDeCarnet(tf_carnet_tab_tipo_carnet.getText());
        if (DaoTipoDeCarnet.nuevoCarnet(nuevoTipoCarnet)) {
            // éxito en la creación, toca refrescar tabla

            mostrarTablaTipoDeCarnet();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado en tab carnet");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar los campos de texto.");
        }
        mostrarTablaTipoDeCarnet();
    }

    /**
     * Añade la funcionalidad de nuevo al TAB Vehículos en su botón, Nuevo Vehículo. Lee el texto previo y se lo envía al Dao nuevo
     */
    @FXML
    private void onBotonNuevoVehiculoTabVehiculos() {
        // creamos un nuevo carnet a partir de los TextFields
        ModeloVehiculo nuevoVehiculo = getModeloVehiculo();
        if (DaoVehiculo.nuevoVehiculo(nuevoVehiculo)) {
            // éxito en la creación, toca refrescar tabla
            mostrarTablaVehiculos();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro nuevo en la base de datos Vehículos");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar los campos de texto del vehículo.");
        }
        mostrarTablaVehiculos();
    }

////////////////////////////////////////////////////////////////////
///////////////           ZONA DE SELECCIÓN DE IMAGENES
////////////////////////////////////////////////////////////////////

    /**
     * Crea un modelo de vehículo a partir de los campos tf
     *
     * @return modeloDeVehiculo desde tf de pantalla
     */
    private ModeloVehiculo getModeloVehiculo() {
        return new ModeloVehiculo(
                tf_codigo_vehiculo_tab_vehiculos.getText(),
                Float.parseFloat(tf_mma_tab_vehiculos.getText()),
                Float.parseFloat(tf_dim_a_tab_vehiculos.getText()),
                Float.parseFloat(tf_dim_l_tab_vehiculos.getText()),
                Float.parseFloat(tf_dim_h_tab_vehiculos.getText()),
                Float.parseFloat(tf_carga_util_tab_vehiculos.getText()),
                combo_codigo_tipo_vehiculo_tab_vehiculos.getSelectionModel().getSelectedItem().getCod_tipo_vehiculo());
    }

    /**
     * Crea un nuevo tipo de vehículo a partir de los datos de los campos de la pantalla
     */
    @FXML
    private void onBotonNuevoTipoDeVehiculoTabTipoDeVehiculo() {

        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_tv_tipo_vehiculo);
        ModeloTipoDeVehiculo modeloTipoDeVehiculo = new ModeloTipoDeVehiculo(
                tf_codigo_vehiculo_tab_tipo_vehiculo.getText(),
                tf_estipulacion_tab_tipo_vehiculo.getText(),
                blobDeImagen,
                tf_instrucciones_tab_tipo_vehiculo.getText(),
                cxbx_atp_tab_tipo_vehiculo.isSelected(),
                cxbx_adr_tab_tipo_vehiculo.isSelected(),
                cmbx_sistema_carga_tab_tipo_vehiculo.getSelectionModel().getSelectedItem().getCod_sistema_carga(),
                cxbx_rampa_tab_tipo_vehiculo.isSelected()
        );
        if (DaoTipoDeVehiculo.nuevoTipoDeVehiculo(modeloTipoDeVehiculo)) {
            mostrarTablaTipoDeVehiculo();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar los campos de texto del tipo de vehículos.");
        }

        mostrarTablaTipoDeVehiculo();
    }

    /**
     * Añade la funcionalidad de modificar al TAB Pais en su botón, Modificar Pais. Lee el texto previo y se lo envía al Dao modificar
     */
    @FXML
    private void onBotonNuevoTipoDeBultoTabTipoDeBultos() {
        //ImageViewABlob im = new ImageViewABlob();
        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_imagen_tipo_bulto_tab_tipo_bulto);
        ModeloTipoDeBulto nuevoTipoBulto = new ModeloTipoDeBulto(
                Integer.parseInt(tf_codigo_tipo_bulto_tab_tipo_bulto.getText()),
                tf_nombre_tipo_bulto_tab_tipo_bulto.getText(),
                blobDeImagen);
        if (DaoTipoDeBulto.nuevoTipoDeBulto(nuevoTipoBulto)) {
            // éxito en la creación, toca refrescar tabla
            mostrarTablaTiposDeBulto();
            Alertas notificacion = new Alertas();
            notificacion.mostrarInformacion("Registro creado");
            logger.info("Registro creado.");
        } else {
            Alertas alerta = new Alertas();
            alerta.mostrarInformacion("No hay nada que añadir, tienes que llenar los campos de texto");
            logger.info("No hay nada que añadir, tienes que llenar todos los campos de texto.");
        }
        mostrarTablaTiposDeBulto();
    }


////////////////////////////////////////////////////////////////////
///////////////            ZONA DE INICIALIZACIÓN  Y UTILIDADES
////////////////////////////////////////////////////////////////////

    /// /////////////////////////////////////////////////////
    /// //////////////// ZONA DE INFORMES  /////////////////
    /// ///////////////////////////////////////////////////

    @FXML
    private void lanzarVentaInformes() {
        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/botoneraInformes.fxml"),bundle);
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            // nuevo escenario de tipo modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //Asignamos el owner de la nueva ventana
            stage.initOwner(this.tv_empresa.getScene().getWindow());
            // esto hace que IntelliJ sepa donde está la imagen en función de la raíz del proyecto
            // en otros ides, no va a funcionar por cómo se hace referencia a la raíz de user.dir
            Image icono = new Image(String.valueOf(getClass().getResource("/es/israeldelamo/transportes/imagenes/icono.png")));
            stage.getIcons().add(icono);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Lanzador de informes");
            stage.showAndWait();
            //refresco cuando vuelva, no seá que se hayan cargado nuevos elementos

        } catch (IOException e) {
            Alertas alerta = new Alertas();
            alerta.mostrarError("Error al cargar la ventana de botonera de informes");
            alerta.mostrarError(e.getMessage());
            logger.error("Error al cargar la ventana de botonera de informes");
        }

    }

    /// /////////////////////////////////////////////////////
    /// //////////////// ZONA DE AYUDA  /////////////////
    /// ///////////////////////////////////////////////////

    @FXML
    private void lanzarVentanaAyuda() {

        //aprovechando un componente de la ventana cualquiera
        //recupero a su padre que es un stage
        Stage currentStage = (Stage) mi_ayuda.getParentPopup().getOwnerWindow();
        reproducirVideo("conductor", currentStage);
     /*
        FXMLLoader loader = new FXMLLoader(Iniciografico.class.getResource("fxml/botoneraManuales.fxml"),bundle);

        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            // nuevo escenario de tipo modal
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //Asignamos el owner de la nueva ventana
            stage.initOwner(this.tv_empresa.getScene().getWindow());
            // esto hace que IntelliJ sepa donde está la imagen en función de la raíz del proyecto
            // en otros ides, no va a funcionar por cómo se hace referencia a la raíz de user.dir

            Image icono = new Image(String.valueOf(getClass().getResource(
                    "/es/israeldelamo/transportes/imagenes/icono.png")));
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

    }

    /**
     * Le pasamos el ImageView que se va a modificar, y el botón que lo ha llamado
     */

    @FXML
    void seleccionarImagenTipoBulto() {
        SeleccionadorImagenDisco.SeleccionadorImagenDisco(
                img_imagen_tipo_bulto_tab_tipo_bulto, btn_carga_imagen_tipo_bulto);

    }

    /**
     * Le pasamos el ImageView que se va a modificar, y el botón que lo ha llamado
     */
    @FXML
    void seleccionarImagenDocumentoCapacitacion() {
        SeleccionadorImagenDisco.SeleccionadorImagenDisco(
                img_foto_tv_documento_capacitacion, btn_carga_imagen_documento_capacitacion);
    }

    /**
     * Le pasamos el ImageView que se va a modificar, y el botón que lo ha llamado
     */

    @FXML
    void seleccionarImagenTipoVehiculo() {
        SeleccionadorImagenDisco.SeleccionadorImagenDisco(
                img_foto_tv_tipo_vehiculo, btn_carga_imagen_documento_capacitacion);
    }

    /**
     * Sale de manera ordenada
     */
    @FXML
    void salir_programa() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de volver a la pantalla anterior?");
        logger.info("Pregunta para salir de la app");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.isPresent() && action.get() == ButtonType.OK) {


            Stage escenaActual = (Stage) tv_vehiculos.getScene().getWindow();
            //ahora tengo la referencia de esta escena cogida
            //desde cualquier elemento de la que es padre
            escenaActual.close();
            if (previousStage != null) {
                previousStage.show();
            }
        } else {
        }

    }

    /**
     * Este solo es un conjunto de lanzadores de mostrar tablas. SOlo sirve para tenerlas lanzadas desde un único lugar
     */
    private void inicializarTablas() {
        //     mostrar carga de las tablas en los diferentes tab
        //las inicializaciones se han pasado a cada vez que se cambia de tab

    }


    ///

    /**
     * Este solo es un conjunto de llamadas a añadir la funcionalidad doble clic. Sirve para tenerlas en un lugar fácil de controlar en el código
     */
    private void funcionalidesDobleClickParaTablas() {
        funcionDobleClickParaTableViewLogin();
        funcionDobleClickParaTableViewTipoDeCarnet();
        funcionDobleClickParaTableViewPaises();
        funcionDobleClickParaTableViewPaisesYProvincias();
        funcionDobleClickParaTableViewPaisesYProvinciasYLocalidades();
        funcionDobleClickParaTableViewPaisesYProvinciasYLocalidadesYCodigosPostales();
        funcionDobleClickTableViewVehiculos();
        funcionDobleClickParaTableViewNaturalezasPeligrosas();
        funcionDobleClickParaTableViewTipoDeBulto();
        funcionDobleClickParaTableViewNaturalezasPeligrosas();
        funcionDobleClickParaTableViewConductor();
        funcionDobleClickParaTableViewConductorTieneVehiculo();
        funcionDobleClickParaTableViewEmpresas();
        funcionDobleClickParaTableViewTipoDeVehiculo();
        funcionDobleClickParaTableViewModeloCapacitacion();
        funcionDobleClickParaTableViewSistemaCargaVehiculo();
        funcionDobleClickTableViewCargaYEntrega();
        funcionDobleClickParaTableViewEnvios();
    }

    /// ZONA DE DETECCIÓN DE CHEBOXES EXCLUYENTES
    ///
    ///
    ///
    @FXML
    /**
     * Cambia el estado entre ADR y ATP o viceversa
     */
    private void cbATPExcluirADR() {
        cxbx_atp_carga_entrega.setSelected(true);
        if (cxbx_adr_carga_entrega.isSelected()) {
            cxbx_adr_carga_entrega.setSelected(false);
        }
    }

    /**
     * Cambia el estado entre ATP y ADR o viceversa
     */
    @FXML
    private void cbADRExcluirATP() {
        cxbx_adr_carga_entrega.setSelected(true);
        if (cxbx_atp_carga_entrega.isSelected()) {
            cxbx_atp_carga_entrega.setSelected(false);
        }
    }

    @FXML
    /**
     * Cambia el estado entre ADR y ATP o viceversa
     */
    private void cbATPExcluirADRenTipoDeVehiculos() {
        cxbx_atp_tab_tipo_vehiculo.setSelected(true);
        if (cxbx_adr_tab_tipo_vehiculo.isSelected()) {
            cxbx_adr_tab_tipo_vehiculo.setSelected(false);
        }
    }

    /**
     * Cambia el estado entre ATP y ADR o viceversa
     */
    @FXML
    private void cbADRExcluirATPenTipoDeVehiculos() {
        cxbx_adr_tab_tipo_vehiculo.setSelected(true);
        if (cxbx_atp_tab_tipo_vehiculo.isSelected()) {
            cxbx_atp_tab_tipo_vehiculo.setSelected(false);
        }
    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada,
     * el de provincia del tab empresas
     */
    @FXML
    void cmb_pais_empresas_cambio() {
        empresaLocalizacionTemporal.setPais(cmb_pais_empresas.getSelectionModel().getSelectedItem()
                .getPais() == null ?
                "" : cmb_pais_empresas.getSelectionModel().getSelectedItem().getPais());
        rellenaComboBoxProvincias(cmb_provincias_empresas, empresaLocalizacionTemporal);
    }


////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
/// ////////////////// ZONA DE CAMBIOS DE COMBO BOXES
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////

    /**
     *
     */
    @FXML
    void comb_provincia_empresa_cambio() {
        empresaLocalizacionTemporal.setProvincia(cmb_provincias_empresas.getSelectionModel().getSelectedItem()
                .getProvincia() == null ?
                "" : cmb_provincias_empresas.getSelectionModel().getSelectedItem().getProvincia());
        rellenaComboBoxLocalidadesEmpresa();
    }

    /**
     *
     */
    @FXML
    void comb_localidad_empresa_cambio() {
        empresaLocalizacionTemporal.setLocalidad(cmb_localidad_empresas.getSelectionModel().getSelectedItem()

                .getLocalidad() == null ?
                "" : cmb_localidad_empresas.getSelectionModel().getSelectedItem().getLocalidad());
        rellenaComboBoxCPEmpresa();
    }

    @FXML
    void combo_codigo_tipo_vehiculo_tab_vehiculos_cambio() {
        // combo_codigo_tipo_vehiculo_tab_vehiculos.getItems().addAll()
        logger.info("cambio en el combo box de tab vehiculos");

    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada,
     * el de provincia del tab carga y entrega
     */
    @FXML
    void comb_pais_origen_cambio() {
        ambitoOrigenTemporal.setPais(combo_pais_origen_carga_entrega.getSelectionModel().getSelectedItem()
                .getPais() == null ?
                "" : combo_pais_origen_carga_entrega.getSelectionModel().getSelectedItem().getPais());
        rellenaComboBoxProvincias(combo_provincia_origen_carga_entrega, ambitoOrigenTemporal);
    }

    /**
     * Rellena las localidades del tab Conductores con la información obtenida desde pais y provincia temporal
     */
    @FXML
    void comboProvinciaConductoresCambio() {
        conductorLocalizacionTemporal.setProvincia(
                combo_provincia_conductores.getSelectionModel().getSelectedItem().getProvincia());
        rellenaComboBoxLocalidadesConductores();
    }




    /**
     * Rellena el combo box de CP del tab Conductor
     */

    @FXML
    void comboLocalidadConductoresCambio() {
        conductorLocalizacionTemporal.setLocalidad(
                combo_localidad_conductores.getSelectionModel().getSelectedItem().getLocalidad());
        rellenaComboBoxCPGenerico(combo_cp_conductores, conductorLocalizacionTemporal);
    }

    @FXML
    void comboTipoDeCarnetConductoresCambio() {

    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada, el de provincia
     */
    @FXML
    void comb_pais_destino_cambio() {
        ambitoDestinoTemporal.setPais(combo_pais_destino_carga_entrega.getSelectionModel().getSelectedItem()
                .getPais() == null ?
                "" : combo_pais_destino_carga_entrega.getSelectionModel().getSelectedItem().getPais());
        rellenaComboBoxProvincias(combo_provincia_destino_carga_entrega, ambitoDestinoTemporal);
    }

    /**
     * Detecta el cambio de provincia y la establece en el temporal, refresca la de localidades
     */
    @FXML
    void comb_provincia_origen_cambio() {
        ambitoOrigenTemporal.setProvincia(
                combo_provincia_origen_carga_entrega.getSelectionModel().getSelectedItem().getProvincia());
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
     * Rellena los comboboxes de paises para cualquier combobox de paises que se le pase
     */
    private void inicizializarComboBoxesPaises(ComboBox<ModeloPais> elComboARellenar) {
        ComboBoxHelper.initPaises(elComboARellenar);
    }


////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
/// ////////////////// ZONA DE INCIALIZACIÓN DE COMBO BOXES
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////

    /**
     * En la inicialización, rellena los combo boxes con los datos de ejemplo
     * se llama después de haber elegido el pais
     */
    private void rellenaComboBoxProvincias(ComboBox<ModeloPaisYProvincia> elComboBoxProvinciasARellenar,
                                           ModeloPaisYProvinciaYLocalidadYCodigoPostal modeloTemporalParaEstaTabla) {
        ComboBoxHelper.fillProvincias(elComboBoxProvinciasARellenar, modeloTemporalParaEstaTabla);
    }

    /**
     * Donde antes se llamaba a un rellenar para Destinos y el mismo, pero para Origenes, ahora se llama a uno solo con los parámetros necesarios
     *
     * @param comboLocalidadesARellenar el combo box que quieres que rellene con las localidades
     * @param ambitoTemporal            el ambito temporal con el pais y provincia para rellenar las diferentes localidades
     */
    private void rellenarComboBoxLocalidadesGenerico(ComboBox<ModeloPaisYProvinciaYLocalidad> comboLocalidadesARellenar,
                                                     ModeloPaisYProvinciaYLocalidadYCodigoPostal ambitoTemporal) {
        ComboBoxHelper.fillLocalidades(comboLocalidadesARellenar, ambitoTemporal);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxLocalidadesOrigenes() {
        rellenarComboBoxLocalidadesGenerico(combo_localidad_origen_carga_entrega, ambitoOrigenTemporal);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxLocalidadesDestinos() {
        rellenarComboBoxLocalidadesGenerico(combo_localidad_destino_carga_entrega, ambitoDestinoTemporal);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxLocalidadesConductores() {
        rellenarComboBoxLocalidadesGenerico(combo_localidad_conductores, conductorLocalizacionTemporal);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxLocalidadesEmpresa() {
        rellenarComboBoxLocalidadesGenerico(cmb_localidad_empresas, empresaLocalizacionTemporal);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxCPEmpresa() {
        rellenaComboBoxCPGenerico(cmb_cp_empresas, empresaLocalizacionTemporal);
    }

    /**
     * Rellena cuando están vacías.
     */
    private void rellenaComboBoxCPOrigen() {
        rellenaComboBoxCPGenerico(combo_cp_origen_carga_entrega, ambitoOrigenTemporal);
    }

    /**
     * Rellena los comboboxes de códigos postales     *
     *
     * @param comboBoxCPArellenar el combo box a rellnar
     * @param origen              la ciudad que indica el cp
     */
    private void rellenaComboBoxCPGenerico(ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> comboBoxCPArellenar, ModeloPaisYProvinciaYLocalidadYCodigoPostal origen) {
        ComboBoxHelper.fillCodigosPostales(comboBoxCPArellenar, origen);
    }

    /**
     * Rellena cuando están vacías.
     */

    private void rellenaComboBoxCPDestino() {
        rellenaComboBoxCPGenerico(combo_cp_destino_carga_entrega, ambitoDestinoTemporal);
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
     * Rellena los comboboxes de combo box una y otra vez, es un bloque refactorizado
     */
    private void relleneComboBoxTipoDeVehiculoTabVehiculos() {
        combo_codigo_tipo_vehiculo_tab_vehiculos.getItems().clear();
        ObservableList<ModeloTipoDeVehiculo> mtvehiculo = FXCollections.observableArrayList();
        mtvehiculo.addAll(DaoTipoDeVehiculo.cargarListadoVehiculos());

        combo_codigo_tipo_vehiculo_tab_vehiculos.setConverter(new TipoDeVehiculoStringConverter());
        combo_codigo_tipo_vehiculo_tab_vehiculos.getItems().addAll(mtvehiculo);


    }

    /**
     * Rellena el combo de tipo Carnet con los datos filtrados del modelo conductor
     *
     * @param modeloConductor el conductor que tiene que mostrar en la pantalla
     */
    private void rellenarComboBoxTipoCarnet(ModeloConductor modeloConductor) {
        ObservableList<ModeloDeCarnet> modelosDeCarnet;// = FXCollections.observableArrayList();
        modelosDeCarnet = DaoTipoDeCarnet.cargarTiposDeCarnets();
        // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
        // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
        combo_tipo_carnet_conductores.setConverter(new StringConverter<ModeloDeCarnet>() {
            @Override
            public String toString(ModeloDeCarnet object) {
                return object.getTipoDeCarnet();
            }

            @Override
            public ModeloDeCarnet fromString(String string) {
                return null;
            }
        });
        combo_tipo_carnet_conductores.setItems(modelosDeCarnet);
        combo_tipo_carnet_conductores.getSelectionModel().select(new ModeloDeCarnet(modeloConductor.getTipo_de_carnet()));
    }

    /**
     * Rellena el combo de tipo Carnet con los datos filtrados del modelo conductor sin especificar qué conductor es
     */
    private void rellenarComboBoxTipoCarnet() {
        ObservableList<ModeloDeCarnet> modelosDeCarnet;// = FXCollections.observableArrayList();
        modelosDeCarnet = DaoTipoDeCarnet.cargarTiposDeCarnets();
        // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
        // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
       /* combo_tipo_carnet_conductores.setConverter(new StringConverter<ModeloDeCarnet>() {
            @Override
            public String toString(ModeloDeCarnet object) {
                return object.getTipoDeCarnet();
            }
            @Override
            public ModeloDeCarnet fromString(String string) {
                return null;
            }
        });*/
        combo_tipo_carnet_conductores.setItems(modelosDeCarnet);
        //  combo_tipo_carnet_conductores.getSelectionModel().select(new ModeloDeCarnet(modeloConductor.getTipo_de_carnet()));
    }

    /**
     * private void rellenarCombo_empresa_carga_entrega
     */
    private void rellenarCombo_empresa_carga_entrega() {
        ComboBoxHelper.fillEmpresas(combo_empresa_carga_entrega);
    }

    /**
     * Rellenar combo box sistema de carga
     */
    private void rellenarComboBoxSistemaDeCarga() {
        ComboBoxHelper.fillSistemaCargaVehiculo(cmbx_sistema_carga_tab_tipo_vehiculo);
    }

    /**
     * Rellena los comboboxes de combo box una y otra vez, es un bloque refactorizado
     */
    private void relleneComboBoxTipoDeBulto() {
        ComboBoxHelper.configureAndFillTiposDeBulto(combo_tipo_bulto_carga_entrega);
    }

    /**
     * Algunos tabs necesitan una inicilización extra además de sus tablas y tc
     */
    private void inicizalizarTabs() {
        inicializarTabCargaYEntrega();
        inicializarTabEmpresas();
        inicializarTabConductores();
        inicializarTabTipoDeVehiculo();
        inicializarTabVehiculos();
    }

    private void inicializarTabVehiculos() {
        relleneComboBoxTipoDeVehiculoTabVehiculos();
    }

    private void inicializarTabTipoDeVehiculo() {
        tipoDeVehiculoTemporal = new ModeloTipoDeVehiculo();
        rellenarComboBoxSistemaDeCarga();

    }

    /**
     * Inicializa los combo boxes y otros elementos del tab Carga Y Entrega
     */
    private void inicializarTabCargaYEntrega() {
        // el tab de carga y entregas es tan complejo como el apartado de gestor de cargas
        // hay que sacar de aquí toda su gestión
        ambitoOrigenTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();
        ambitoDestinoTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();
        inicizializarComboBoxesPaises(combo_pais_destino_carga_entrega);
        inicizializarComboBoxesPaises(combo_pais_origen_carga_entrega);
        relleneComboBoxTipoDeBulto();
        relleneComboBoxNaturalezaPeligrosa();
        rellenarCombo_empresa_carga_entrega();
    }

    /**
     * Inicializa los combo boxes y otros elementos del tab Empresas
     */
    private void inicializarTabEmpresas() {
        // el tab de empresas es tan complejo como el apartado de gestor de cargas
        // hay que sacar de aquí toda su gestión
        empresaLocalizacionTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();
        inicizializarComboBoxesPaises(cmb_pais_empresas);
    }

    /**
     * Inicializa los combo boxes y otros elementos del tab Empresas
     */
    private void inicializarTabConductores() {
        // el tab de empresas es tan complejo como el apartado de gestor de cargas
        // hay que sacar de aquí toda su gestión
        conductorLocalizacionTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();
        conductorNacionalidadTemporal = new ModeloPaisYProvinciaYLocalidadYCodigoPostal();


    }

    /**
     * {@inheritDoc}
     * <p>
     * Inicio del controlador para Gestión Administrador
     */

    public void initialize(String elUsuario) {
        // Mantener la inicialización ligera para no bloquear la apertura de la ventana.
        // La carga de datos pesados de BBDD se realizará bajo demanda al cambiar a cada pestaña
        // mediante los manejadores cargarTab* ya existentes.
        cargarBundleIdiomas();
        // Evitamos pre-cargar tablas y combos aquí porque realizan consultas a BBDD costosas.
        // inicializarTablas();
        // inicizalizarTabs();
        funcionalidesDobleClickParaTablas();

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


    /// //////////////////////////////////////////////////////////////////////////////////////////////////////
    /// //////////////// ZONA DE CARGA DE LAS TABS POR DEMANDA //////////////
    /// ////////////////////////////////////////////////////////////////////////

    @FXML
    private void cargarTabVehiculosConductores() {
        mostrarTablaConductorTieneVehiculo();
    }

    @FXML
    private void cargarTabConductores() {
        mostrarTablaConductores();
    }

    @FXML
    private void cargarTabTipoCarnet() {
        mostrarTablaTipoDeCarnet();
    }

    @FXML
    private void cargarTabPaises() {
        mostrarTablaPaises();
    }

    @FXML
    private void cargarTabPaisesYProvincias() {
        mostrarTablaPaisesYProvincias();
    }

    @FXML
    private void cargarTabPaisesYProvinciasYLocalidades() {
        mostrarTablaPaisesYProvinciasYLocalidades();
    }

    @FXML
    private void cargarTabPaisesYProvinciasYLocalidadesYCodigosPostales() {
        mostrarTablaPaisesYProvinciasYLocalidadesYCodigosPostales();
    }

    @FXML
    private void cargarTabLogin() {
        mostrarTablaLogin();
    }

    @FXML
    private void cargarTabTipoDeCarnet() {
        mostrarTablaTipoDeCarnet();
    }

    @FXML
    private void cargarTabVehiculos() {
        mostrarTablaVehiculos();
    }

    @FXML
    private void cargarTabNaturalezaPeligrosa() {
        mostrarTablaNaturalezaPeligrosa();
    }

    @FXML
    private void cargarTabTiposDeBulto() {
        mostrarTablaTiposDeBulto();
    }

    @FXML
    private void cargarTabEmpresas() {
        mostrarTablaEmpresas();
    }

    @FXML
    private void cargarTabTipoDeVehiculo() {
        mostrarTablaTipoDeVehiculo();
    }

    @FXML
    private void cargarTabDocumentosCapacitacion() {
        mostrarTablaDocumentosCapacitacion();
        cmbx_peligrosidad_tab_documento_capacitacion.getItems().addAll(DaoNaturalezaPeligrosa.cargarListadoNaturalezasPeligrosas());
    }

    @FXML
    private void cargarTabSistemaCargaVehiculos() {
        mostrarTablaSistemaCargaVehiculos();
    }

    @FXML
    private void cargarTabCargaYEntrega() {
        mostrarTablaCargaYEntrega();
    }

    @FXML
    private void cargarTabEnvios() {
        mostrarTablaEnvios();
    }

    /**
     * Usado para pasar un parámetro desde la ventana que lo ha llamado
     *
     * @param parametro el dato que quiero pasar
     */
    public void pasoDeParametros(String parametro) {
        elAdministrador = parametro;
    }

    public static class Item {
        private final BooleanProperty selected;

        public Item(boolean selected) {
            this.selected = new SimpleBooleanProperty(selected);
        }

        public BooleanProperty selectedProperty() {
            return selected;
        }

        public boolean isSelected() {
            return selected.get();
        }

        public void setSelected(boolean selected) {
            this.selected.set(selected);
        }
    }
}
