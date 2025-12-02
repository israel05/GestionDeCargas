package es.israeldelamo.transportes.controladores;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.dao.*;
import es.israeldelamo.transportes.modelos.*;
import es.israeldelamo.transportes.utilidades.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Clase controladora del FXML modificar Conductor
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModificarConductorController implements Initializable {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModificarConductorController.class);

    @FXML
    private TextField tf_contacto_modal_conductor;
    @FXML
    private TextField tf_correo_modal_conductor;
    @FXML
    private TextField tf_dni_modal_conductor;
    @FXML
    private TextField tf_apellidos_modal_conductor;
    @FXML
    private TextField tf_nombre_modal_conductor;
    @FXML
    private TextField tf_telefono_modal_conductor;
    @FXML
    private TextField tf_negra_modal_conductor;


    @FXML
    private Button btn_cancelar_modal_conductor;
    @FXML
    private Button btn_modificar_modal_conductor;
    @FXML
    private CheckBox ckbx_es_inter_modal_conductor;
    @FXML
    private Button btn_carga_imagen_conductor;


    @FXML
    private ComboBox<ModeloPais> cmb_pais_modal_conductor;

    @FXML
    private ComboBox<ModeloPaisYProvincia> cmbbx_provincia_modal;

    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidad> cmb_localidad_modal_conductor;
    @FXML
    private ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> cmb_cp_modal_conductor;

    @FXML
    private ComboBox<ModeloPais> cmb_nacionalidad_modal_conductor;


    @FXML
    private ComboBox<ModeloDeCarnet> cmb_tipo_carnet_modal_conductor;
    @FXML
    private ImageView img_foto_modal_conductor;


    /**
     * Un conductor base sobre el que vamos a trabajar
     */

    private ModeloConductor modeloConductorCambiado;

    @FXML
    void salirDeLaVentanaModal() {
        Stage stage = (Stage) btn_cancelar_modal_conductor.getScene().getWindow();
        stage.close();
    }


    /**
     * Swap de valores boolean para es_internacional.
     */
    @FXML
    void cambioEnEsInternacional() {
        //Como se ha pulsado clic vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setEs_internacional(!modeloConductorCambiado.getEs_internacional());
    }


    /**
     * Ya que se pueden producir cambios en modificar o nuevo, este procedimiento creara un objeto nuevo con lo modificado
     */
    @FXML
    void cambioEntf_telefono_modal_conductor() {
        //Como se ha pulsado ENTER vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setTelefono(tf_telefono_modal_conductor.getText());
    }

    /**
     * Ya que se pueden producir cambios en modificar o nuevo, este procedimiento creara un objeto nuevo con lo modificado
     */
    @FXML
    void cambioEntf_correo_modal_conductor() {
        //Como se ha pulsado ENTER vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setCorreo_electronico(tf_correo_modal_conductor.getText());
    }

    /**
     * Ya que se pueden producir cambios en modificar o nuevo, este procedimiento creara un objeto nuevo con lo modificado
     */
    @FXML
    void cambioEntf_contacto_modal_conductor() {
        //Como se ha pulsado ENTER vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setContacto_recogida(tf_contacto_modal_conductor.getText());
    }

    /**
     * Ya que se pueden producir cambios en modificar o nuevo, este procedimiento creara un objeto nuevo con lo modificado
     */
    @FXML
    void cambioEntf_dni_modal_conductor() {
        //Como se ha pulsado ENTER vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setDni(tf_dni_modal_conductor.getText());
    }

    /**
     * Ya que se pueden producir cambios en modificar o nuevo, este procedimiento creara un objeto nuevo con lo modificado
     */
    @FXML
    void onKeyPressCampoNombre() {
        //Como se ha pulsado ENTER vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setNombre(tf_nombre_modal_conductor.getText());
    }

    /**
     * Ya que se pueden producir cambios en modificar o nuevo, este procedimiento creara un objeto nuevo con lo modificado
     */
    @FXML
    void cambioEntf_apellidos_modal_conductor() {
        //Como se ha pulsado ENTER vamos a dar cambiar el atributo del modeloConductorCambiado
        modeloConductorCambiado.setApellido(tf_apellidos_modal_conductor.getText());
    }

    /**
     * Un cambio en el motivo de la lista negra
     */

    @FXML
    void cambioEntf_negra_modal_conductor() {
        modeloConductorCambiado.setMotivo_lista_negra(tf_negra_modal_conductor.getText());
    }

    /**
     * Selecciona la nacionalidad del conductor
     */

    @FXML
    void cmb_nacionalidad_modal_cambio() {
        modeloConductorCambiado.setNacionalidad(cmb_nacionalidad_modal_conductor.getValue().getPais());
    }


    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada, el de provincia
     */
    @FXML
    void cmb_pais_modal_cambio() {
        modeloConductorCambiado.setPais(cmb_pais_modal_conductor.getValue().getPais());
        rellenaComboBoxProvincias(modeloConductorCambiado);
        logger.info("El nuevo pais del modelo conductor cambiado es :{}", cmb_pais_modal_conductor.getValue().getPais());
        //rellenaComboBoxProvincias(modeloConductorCambiado);

    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada, el de localidad, provincia y el de cp
     */
    @FXML
    void cmbbx_provincia_cambio() {
        modeloConductorCambiado.setProvincia(cmbbx_provincia_modal.getValue().getProvincia());
        logger.info("La nueva provincia del conductor es {}", cmbbx_provincia_modal.getValue().getProvincia());
        //con la nueva provincia, refresca las localidades
        rellenaComboBoxLocalidades(modeloConductorCambiado);

    }

    /**
     * Detecta el cambio en el combo box y así refresca el listado de los otros comboboxes en cascada, el de cp
     */
    @FXML
    void cmb_localidad_modal_cambio() {
        modeloConductorCambiado.setLocalidad(cmb_localidad_modal_conductor.getValue().getLocalidad());
        logger.info("La nueva localidad del conductor es {}", cmb_localidad_modal_conductor.getValue().getLocalidad());
        //con la nueva localidad, refresca los cp
        rellenaComboBoxCodigoPostales(modeloConductorCambiado);
    }

    /**
     * Detecta el cambio en el combo box y actualiza en consecuencia el modeloConductorCambiado
     */

    @FXML
    void cmb_codigo_postal_modal_cambio() {
        //  modeloConductorCambiado.setCodigo_postal(cmb_cp_modal_conductor.getValue().getCodigoPostal());

        //          cmb_cp_modal_conductor.getValue().getLocalidad());
        //No hay que rellenar nada más
    }


    /**
     * Rellena el combo box en función del modeloConducto pasado
     *
     * @param modeloConductor el que determina el pais que debe aparece
     */
    private void rellenaComboBoxNacionalidad(ModeloConductor modeloConductor) {
        cmb_nacionalidad_modal_conductor.getItems().clear();
        ObservableList<ModeloPais> modelosDePais = FXCollections.observableArrayList();
        modelosDePais.addAll(DaoPais.cargarListadoPaises());
        // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
        // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
        cmb_nacionalidad_modal_conductor.setConverter(new PaisStringConverter());
        cmb_nacionalidad_modal_conductor.getItems().addAll(modelosDePais); // Corregido para asignar correctamente los modelos de país
        // este selecciona un item de tipo MODELO PAIS, mientras que modeloConductor.getPais_conductor devuelve un string
        cmb_nacionalidad_modal_conductor.getSelectionModel().select(
                new PaisStringConverter().
                        fromString(modeloConductor.getPais()));

    }

    /**
     * Rellena el combo box en función del modeloConducto pasado
     *
     * @param modeloConductor el que determina el pais que debe aparece
     */
    private void rellenaComboBoxPais(ModeloConductor modeloConductor) {
        cmb_pais_modal_conductor.getItems().clear();
        ObservableList<ModeloPais> modelosDePais = FXCollections.observableArrayList();
        modelosDePais.addAll(DaoPais.cargarListadoPaises());
        // Hace falta un setConverter para que se llame a StringConverter. Es la manera de sacar el string del objeto
        // ModeloCarnet. Además, añade un fromString que no es necesario implementar.
        cmb_pais_modal_conductor.setConverter(new PaisStringConverter());
        cmb_pais_modal_conductor.getItems().addAll(modelosDePais);
        cmb_pais_modal_conductor.getSelectionModel().select(
                new PaisStringConverter().
                        fromString(modeloConductor.getPais()));
    }

    /**
     * Aquí es donde hay que leer según el pais de arriba
     *
     * @param modeloConductor el que determina el pais que debe aparece
     */
    private void rellenaComboBoxProvincias(ModeloConductor modeloConductor) {
        //vacío la anterior
        cmbbx_provincia_modal.getItems().clear();
        ObservableList<ModeloPaisYProvincia> modelosDePaisYProvincia = FXCollections.observableArrayList();
        modelosDePaisYProvincia.addAll(DaoPaisYProvincia.cargarListadoProvincias(modeloConductor.getPais()));

        //este converter es el mismo que el de la clase PaisYProvinciaStringConverter, esta aquí por los errores que daba el equal
        //todo quitar este converter explícito y dejarlo en su clase
        cmbbx_provincia_modal.setConverter(new StringConverter<ModeloPaisYProvincia>() {
            @Override
            public String toString(ModeloPaisYProvincia object) {
                return object != null ? object.getProvincia() + ", " + object.getPais() : "";
            }

            @Override
            public ModeloPaisYProvincia fromString(String paisYProvincia) {
                return null;
            }
        });
        cmbbx_provincia_modal.getItems().addAll(modelosDePaisYProvincia);
        //una vez relleno, hay que poner el que estaba seleccionado
        cmbbx_provincia_modal.getSelectionModel().select(
                new PaisYProvinciaStringConverter().
                        fromString(modeloConductor.getProvincia() + ", " + modeloConductor.getPais()));

    }

    /**
     * Aquí es donde hay que leer según el pais de arriba
     *
     * @param modeloConductor el que determina el pais que debe aparece
     */
    private void rellenaComboBoxLocalidades(ModeloConductor modeloConductor) {
        //vacío la anterior
        cmb_localidad_modal_conductor.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidad> modelosDePaisYProvinciaYLocalidad = FXCollections.observableArrayList();
        modelosDePaisYProvinciaYLocalidad.addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades(modeloConductor.getPais(), modeloConductor.getProvincia()));

        //todo quitar este converter explícito y dejarlo en su clase
        cmb_localidad_modal_conductor.setConverter(new StringConverter<ModeloPaisYProvinciaYLocalidad>() {
            @Override
            public String toString(ModeloPaisYProvinciaYLocalidad object) {
                return object != null ? object.getLocalidad() + ", " + object.getProvincia() + ", " + object.getPais() : "";
            }

            @Override
            public ModeloPaisYProvinciaYLocalidad fromString(String paisYProvinciaYLocalidad) {
                return null;
            }
        });
        cmb_localidad_modal_conductor.getItems().addAll(modelosDePaisYProvinciaYLocalidad);

        //una vez relleno, hay que poner el que estaba seleccionado
        cmb_localidad_modal_conductor.getSelectionModel().select(
                new PaisYProvinciaLocalidadStringConverter().
                        fromString(modeloConductor.getLocalidad()
                                + ", " + modeloConductor.getProvincia()
                                + ", " + modeloConductor.getPais()));

    }

    /**
     * Rellena el combo box de codigo postal en función de un modeloConductor
     *
     * @param modeloConductor el modelo Conductor que se usara para filtrar
     */
    private void rellenaComboBoxCodigoPostales(ModeloConductor modeloConductor) {

//vacío la anterior
        cmb_cp_modal_conductor.getItems().clear();
        //Comienzo con la creación del nuevo
        ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> modeloPaisYProvinciaYLocalidadYCodigoPostalObservableList = FXCollections.observableArrayList();
        modeloPaisYProvinciaYLocalidadYCodigoPostalObservableList.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.
                cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                        modeloConductor.getPais(),
                        modeloConductor.getProvincia(),
                        modeloConductor.getLocalidad()
                ));

        //todo quitar este converter explícito y dejarlo en su clase
        cmb_cp_modal_conductor.setConverter(new StringConverter<ModeloPaisYProvinciaYLocalidadYCodigoPostal>() {
            @Override
            public String toString(ModeloPaisYProvinciaYLocalidadYCodigoPostal object) {
                return object != null ? object.getCodigoPostal().toString() + ", " + object.getLocalidad() + ", " + object.getProvincia() + ", " + object.getPais() : "";
            }

            @Override
            public ModeloPaisYProvinciaYLocalidadYCodigoPostal fromString(String paisYProvinciaYLocalidadYCp) {
                return null;
            }
        });
        cmb_cp_modal_conductor.getItems().addAll(modeloPaisYProvinciaYLocalidadYCodigoPostalObservableList);
        //una vez relleno, hay que poner el que estaba seleccionado
        //"España, Álava, Vitoria, 01111"
        cmb_cp_modal_conductor.getSelectionModel().select(
                new PaisYProvinciaYLocalidadYCodigoPostalStringConverter().
                        fromString(modeloConductor.getPais()
                                + ", " + modeloConductor.getProvincia()
                                + ", " + modeloConductor.getPais()
                                + ", " + modeloConductor.getCodigo_postal().toString()));


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
        cmb_tipo_carnet_modal_conductor.setConverter(new StringConverter<ModeloDeCarnet>() {
            @Override
            public String toString(ModeloDeCarnet object) {
                return object.getTipoDeCarnet();
            }

            @Override
            public ModeloDeCarnet fromString(String string) {
                return null;
            }
        });
        cmb_tipo_carnet_modal_conductor.setItems(modelosDeCarnet);
        cmb_tipo_carnet_modal_conductor.getSelectionModel().select(new ModeloDeCarnet(modeloConductor.getTipo_de_carnet()));
    }


    /**
     * Función para cargar la imagen en función de modeloConductor
     *
     * @param modeloConductor este modeloConductor puede o no llevar una imagen asociada dentro de su bbDD
     */
    private void cargarImagenCarnet(ModeloConductor modeloConductor) {
        // ZONA DE LA IMAGEN
        //Conversion entre blob e imágenes
        Blob blob = modeloConductor.getFoto_humano();
        //Si la imagen no existe, que muestre una imagen vacía, si no, que muestre la que existe
        // Convertir Blob a byte array
        byte[] bytes = null;
        try {
            int blobLength = (int) blob.length();
            bytes = blob.getBytes(1, blobLength);
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarInformacion("No he podido cargar imagen alguna para ese registro desde Base De Datos");
            logger.error("No he podido cargar imagen alguna para ese registro desde Base De Datos");
        } catch (NullPointerException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarInformacion("No hay imagen definida para esa entrada Base De Datos");
            logger.error("No hay imagen definida para esa entrada Base De Datos");
        }
        // Crear una imagen desde el byte array
        try {
            Image imageDesdeBlob = new Image(new ByteArrayInputStream(Objects.requireNonNull(bytes)));
            img_foto_modal_conductor.setImage(imageDesdeBlob);
        } catch (NullPointerException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
            Image imagenPorDefecto = new Image(Objects.requireNonNull(Iniciografico.class
                    .getResourceAsStream("imagenes/icono.png")));
            img_foto_modal_conductor.setImage(imagenPorDefecto);
        }
/*
           img_foto_modal_conductor.setOnMouseClicked(event -> {
                   if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        //no recuerdo que es lo que había que hacer LOL
                   }
           });
*/
    }


    /**
     * Modificar un modelo conductor basado en la lista de la base de datos de conductores
     */
    @FXML
    private void modificar_modal_conductor() {

        if (DaoConductor.actualizarConductor(modeloConductorCambiado)) {
            Alertas infoExito = new Alertas();
            infoExito.mostrarInformacion("Éxito en la actualización");
            salirDeLaVentanaModal();
        } else {
            Alertas alertaError = new Alertas();
            alertaError.mostrarAlertaCabecera("No he podido modificar ese conductor. Verifique que no existan campos vacíos");
        }
    }

    /**
     * Crea un nuevo conductor al pulsar el botón, esta tiene que leer todos los campos y crear un nuevo objeto y pasar al DAO
     */

    @FXML
    private void nuevo_modal_conductor() {

        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_modal_conductor);

        ModeloConductor nuevoConductor = new ModeloConductor(
                tf_dni_modal_conductor.getText(),
                tf_nombre_modal_conductor.getText(),
                tf_apellidos_modal_conductor.getText(),
                tf_contacto_modal_conductor.getText(),
                tf_correo_modal_conductor.getText(),
                blobDeImagen,
                ckbx_es_inter_modal_conductor.isSelected(),
                cmb_cp_modal_conductor.getValue() != null ? cmb_cp_modal_conductor.getValue().getCodigoPostal() : 0,
                cmbbx_provincia_modal.getValue().getProvincia(),
                cmb_localidad_modal_conductor.getValue().getLocalidad(),
                cmb_pais_modal_conductor.getValue().getPais(),
                cmb_tipo_carnet_modal_conductor.getValue().getTipoDeCarnet(),
                tf_telefono_modal_conductor.getText(),
                tf_negra_modal_conductor.getText(),
                cmb_nacionalidad_modal_conductor.getValue().getPais());


        //Con el objeto nuevo, lo metemos en el DAO
        //Notificamos la creación del nuevo conductor y cerramos
        if (DaoConductor.nuevoConductor(nuevoConductor)) {

            Alertas infoExito = new Alertas();
            infoExito.mostrarInformacion("Éxito en la actualización");
            salirDeLaVentanaModal();
        } else {
            //     System.out.println("Exito en el cambio");
            Alertas alertaFracaso = new Alertas();
            alertaFracaso.mostrarAlertaCabecera("No he podido instar ese Conductor");
            logger.error("No he podido instar ese Conductor");
            // salirDeLaVentanaModal(); que no salga, que cierre y lo piense
        }

    }

    /**
     * Como lo has cambiado anoto el cambio en el modeloConductorCambiado.
     * CUando vayas a Modifcar, llevará la nueva foto.
     */
    @FXML
    private void seleccionarImagenModificarConductor() {


        SeleccionadorImagenDisco.SeleccionadorImagenDisco(img_foto_modal_conductor, btn_carga_imagen_conductor);
        Blob blobDeImagen = ImageViewABlob.conversorImageViewABlob(img_foto_modal_conductor);
        modeloConductorCambiado.setFoto_humano(blobDeImagen);

    }

    @FXML
    private void seleccionarImagenNuevoConductor() {
        SeleccionadorImagenDisco.SeleccionadorImagenDisco(img_foto_modal_conductor, btn_carga_imagen_conductor);
    }

    /**
     * Este método es el que se llama para modificar un conductor concreto
     *
     * @param modeloConductor el conductor que vamos a modificar
     */
    void cargarUnConductorConcreto(ModeloConductor modeloConductor) {
        //Vamos a crear una copia del original para poder cambiarlo,
        //Así al pulsar modificar compararemos los dos, el modeloConductor con el modeloConductoCambiado
        //si es igual no lo tocamos, si es diferente actualizamos la base de datos
        modeloConductorCambiado = modeloConductor;


// RELLENA EL COMBO BOX DEL NACIONALIDADES
        rellenaComboBoxNacionalidad(modeloConductor);

        // RELLENA EL COMBO BOX DEL PAIS
        rellenaComboBoxPais(modeloConductor);
        // RELLENA EL COMBO BOX DE PROVINCIA
        rellenaComboBoxProvincias(modeloConductor);
        //RELLENA EL COMBO BOX DE LOCALIDADES
        rellenaComboBoxLocalidades(modeloConductor);
        // RELLENA EL COMBO BOX DE CODIGO POSTAL
        rellenaComboBoxCodigoPostales(modeloConductor);
        // RELLENA EL COMBO BOX
        rellenarComboBoxTipoCarnet(modeloConductor);
        // RELLENA EL LA IMAGEN
        cargarImagenCarnet(modeloConductor);


        //RELLENA EL CAMPO DE CAPACITACIONES


        // RELLENA LOS TEXTFIELDS
        tf_dni_modal_conductor.setText(modeloConductor.getDni());
        tf_correo_modal_conductor.setText(modeloConductor.getCorreo_electronico());
        tf_nombre_modal_conductor.setText(modeloConductor.getNombre());
        tf_contacto_modal_conductor.setText(modeloConductor.getContacto_recogida());
        tf_apellidos_modal_conductor.setText(modeloConductor.getApellido());
        tf_telefono_modal_conductor.setText(modeloConductor.getTelefono());
        tf_negra_modal_conductor.setText(modeloConductor.getMotivo_lista_negra());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
