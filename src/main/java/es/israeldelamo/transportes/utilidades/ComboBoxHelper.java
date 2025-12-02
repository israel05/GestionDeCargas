package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.Iniciografico;
import es.israeldelamo.transportes.dao.*;
import es.israeldelamo.transportes.modelos.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Utilidades para inicializar y rellenar ComboBox usados en múltiples pestañas del panel de administración.
 * Extraído desde GestionAdministradorController para reducir su tamaño y mejorar la reutilización.
 */
public final class ComboBoxHelper {

    private ComboBoxHelper() {}

    public static void initPaises(ComboBox<ModeloPais> combo) {
        combo.getItems().clear();
        ObservableList<ModeloPais> modelosDePaises = FXCollections.observableArrayList();
        modelosDePaises.addAll(DaoPais.cargarListadoPaises());
        combo.setConverter(new PaisStringConverter());
        combo.getItems().addAll(modelosDePaises);
    }

    public static void fillProvincias(ComboBox<ModeloPaisYProvincia> combo,
                                      ModeloPaisYProvinciaYLocalidadYCodigoPostal ambito) {
        combo.getItems().clear();
        ObservableList<ModeloPaisYProvincia> provincias = FXCollections.observableArrayList();
        provincias.addAll(DaoPaisYProvincia.cargarListadoProvincias(ambito.getPais()));
        combo.setConverter(new PaisYProvinciaStringConverter());
        combo.getItems().addAll(provincias);
    }

    public static void fillLocalidades(ComboBox<ModeloPaisYProvinciaYLocalidad> combo,
                                       ModeloPaisYProvinciaYLocalidadYCodigoPostal ambito) {
        combo.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidad> localidades = FXCollections.observableArrayList();
        localidades.addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades(
                ambito.getPais(), ambito.getProvincia()));
        combo.setConverter(new PaisYProvinciaLocalidadStringConverter());
        combo.getItems().addAll(localidades);
    }

    public static void fillCodigosPostales(ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> combo,
                                           ModeloPaisYProvinciaYLocalidadYCodigoPostal ambito) {
        combo.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> cps = FXCollections.observableArrayList();
        cps.addAll(DaoPaisYProvinciaYLocalidadYCodigoPostal.cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(
                ambito.getPais(), ambito.getProvincia(), ambito.getLocalidad()));
        combo.setConverter(new PaisYProvinciaYLocalidadYCodigoPostalStringConverter());
        combo.getItems().addAll(cps);
    }

    public static void fillEmpresas(ComboBox<ModeloEmpresa> combo) {
        combo.getItems().clear();
        ObservableList<ModeloEmpresa> empresas = FXCollections.observableArrayList();
        empresas.addAll(DaoEmpresa.cargarListadoEmpresas());
        combo.setConverter(new EmpresaStringConverter());
        combo.getItems().addAll(empresas);
    }

    public static void fillSistemaCargaVehiculo(ComboBox<ModeloSistemaCargaVehiculo> combo) {
        combo.getItems().clear();
        ObservableList<ModeloSistemaCargaVehiculo> sistemas = FXCollections.observableArrayList();
        sistemas.addAll(DaoSistemaCargaVehiculo.cargarListadoSistemaCargaVehiculo());
        combo.setConverter(new SistemaCargaVehiculoStringConverter());
        combo.getItems().addAll(sistemas);
    }

    public static void configureAndFillTiposDeBulto(ComboBox<ModeloTipoDeBulto> combo) {
        combo.getItems().clear();
        ObservableList<ModeloTipoDeBulto> tipos = FXCollections.observableArrayList();

        combo.setCellFactory(param -> new ListCell<ModeloTipoDeBulto>() {
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
                        new Alertas().mostrarError("No he podido cargar imagen alguna para ese registro desde Base De Datos");
                    } catch (NullPointerException e) {
                        new Alertas().mostrarError("No hay imagen definida para esa entrada  Base De Datos");
                    }

                    try {
                        Image imageDesdeBlob = new Image(new ByteArrayInputStream(Objects.requireNonNull(bytes)));
                        imageView = new ImageView(imageDesdeBlob);
                        imageView.setImage(imageDesdeBlob);
                    } catch (NullPointerException e) {
                        new Alertas().mostrarError("No hay imagen definida para esa entrada  Base De Datos, dejo una genérica");
                        Image imagenPorDefecto = new Image(Objects.requireNonNull(Iniciografico.class.getResourceAsStream("imagenes/icono.png")));
                        imageView.setImage(imagenPorDefecto);
                    }

                    imageView.setFitHeight(50);
                    imageView.setFitWidth(50);
                    setText(item.getCod_tipo_bulto() + " (" + item.getNombre_bulto() + ")");
                    setGraphic(imageView);
                }
            }
        });

        combo.setButtonCell(new ListCell<ModeloTipoDeBulto>() {
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

        tipos.addAll(DaoTipoDeBulto.cargarListadTiposDeBultos());
        combo.getItems().addAll(tipos);
    }
}
