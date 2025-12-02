package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.dao.DaoPais;
import es.israeldelamo.transportes.dao.DaoPaisYProvincia;
import es.israeldelamo.transportes.dao.DaoPaisYProvinciaYLocalidad;
import es.israeldelamo.transportes.modelos.ModeloPais;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvincia;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidad;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class RellenaComboLocalizaciones {

    /**
     * Rellena el combo box de paises una vez que se le pasa el país para el DAO y el combo box a rellenar
     *
     * @param combo_pais_origen_carga_entrega el combo box de paises a rellenar
     * @param pais                            el pais seleccionado
     */
    public static void rellenarComboPaises(ComboBox<ModeloPais> combo_pais_origen_carga_entrega, String pais) {
        combo_pais_origen_carga_entrega.getItems().clear();
        ObservableList<ModeloPais> modelosDePaisOrigen = FXCollections.observableArrayList();
        modelosDePaisOrigen.addAll(DaoPais.cargarListadoPaises());
        combo_pais_origen_carga_entrega.setConverter(new PaisStringConverter());
        combo_pais_origen_carga_entrega.getItems().addAll(modelosDePaisOrigen);
        combo_pais_origen_carga_entrega.getSelectionModel().select(new PaisStringConverter().fromString(pais));
    }


    /**
     * Rellena el combo box de provincias que le pasemos en función de la carga y entrega temporal
     *
     * @param comboProvincia    el combo box de provincias a rellenar
     * @param provinciaAFiltrar la provincia que queremos seleccionar
     * @param paisAFiltrar      el pais a filtrar
     */
    public static void rellenarComboProvincias(ComboBox<ModeloPaisYProvincia> comboProvincia, String provinciaAFiltrar, String paisAFiltrar) {
        comboProvincia.getItems().clear();
        ObservableList<ModeloPaisYProvincia> mppOrigen = FXCollections.observableArrayList();
        mppOrigen.addAll(DaoPaisYProvincia.cargarListadoProvincias(paisAFiltrar));
        comboProvincia.setConverter(new PaisYProvinciaStringConverter());
        comboProvincia.getItems().addAll(mppOrigen);
        comboProvincia.getSelectionModel().select(new PaisYProvinciaStringConverter()
                .fromString(provinciaAFiltrar + ", " + paisAFiltrar));
    }

    /**
     * Rellena los comboboxes de localidades
     *
     * @param combobox  el combo box a rellenar
     * @param localidad la localidad
     * @param provincia la provincia
     * @param pais      el país
     */
    public static void rellenarComboLocalidades(ComboBox<ModeloPaisYProvinciaYLocalidad> combobox, String localidad, String provincia, String pais) {
        combobox.getItems().clear();
        ObservableList<ModeloPaisYProvinciaYLocalidad> mlocalidadOrigen = FXCollections.observableArrayList();
        mlocalidadOrigen.addAll(DaoPaisYProvinciaYLocalidad.cargarListadoLocalidades(pais, provincia));
        combobox.setConverter(new PaisYProvinciaLocalidadStringConverter());
        combobox.getItems().addAll(mlocalidadOrigen);
        // la plantilla de la cadena es "Vitoria, Álava, España"
        combobox.getSelectionModel().select(new PaisYProvinciaLocalidadStringConverter()
                .fromString(localidad + ", " + provincia + ", " + pais));
    }

}
