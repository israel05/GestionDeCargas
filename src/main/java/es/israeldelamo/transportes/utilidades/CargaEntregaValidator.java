package es.israeldelamo.transportes.utilidades;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import es.israeldelamo.transportes.modelos.ModeloPais;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvincia;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidad;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal;
import es.israeldelamo.transportes.modelos.ModeloTipoDeBulto;

/**
 * Validador de campos obligatorios para la creación/modificación de Carga y Entrega.
 */
public final class CargaEntregaValidator {

    private CargaEntregaValidator() {}

    /**
     * Valida los campos obligatorios de la pantalla de Carga y Entrega.
     * Muestra alertas descriptivas en caso de falta de datos.
     *
     * @return true si todos los campos obligatorios están presentes; false en caso contrario.
     */
    public static boolean validarCamposObligatorios(
            ComboBox<ModeloTipoDeBulto> comboTipoBulto,
            DatePicker dpDiaInicio,
            ComboBox<ModeloPais> comboPaisDestino,
            ComboBox<ModeloPaisYProvincia> comboProvinciaOrigen,
            ComboBox<ModeloPais> comboPaisEntrega,
            ComboBox<ModeloPaisYProvincia> comboProvinciaDestino,
            ComboBox<ModeloPaisYProvinciaYLocalidad> comboLocalidadDestino,
            ComboBox<ModeloPaisYProvinciaYLocalidadYCodigoPostal> comboCpDestino,
            TextField tfCantidadBultos,
            TextField tfPrecio,
            TextField tfDimA,
            TextField tfDimH,
            TextField tfDimL,
            TextField tfPesoUnidad
    ) {
        if (comboTipoBulto.getValue() == null) {
            new Alertas().mostrarInformacion("TIPO DE BULTO NO ESPECIFICADO. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (dpDiaInicio.getValue() == null) {
            new Alertas().mostrarInformacion("FRANJA DE RECOGIDA NO ESPECIFICADA. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (comboPaisDestino.getSelectionModel().getSelectedItem() == null) {
            new Alertas().mostrarInformacion("PAIS DESTINO NO SELECCIONADO. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (comboProvinciaOrigen.getSelectionModel().getSelectedItem() == null) {
            new Alertas().mostrarInformacion("PROVINCIA ORIGEN NO SELECCIONADA. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (comboPaisEntrega.getSelectionModel().getSelectedItem() == null) {
            new Alertas().mostrarInformacion("PAÍS DE ENTREGA NO SELECCIONADA. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (comboProvinciaDestino.getSelectionModel().getSelectedItem() == null) {
            new Alertas().mostrarInformacion("PROVINCIA DE DESTINO NO SELECCIONADA. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (comboLocalidadDestino.getSelectionModel().getSelectedItem() == null) {
            new Alertas().mostrarInformacion("LOCALIDA DE DESTINO NO SELECCIONADA. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (comboCpDestino.getSelectionModel().getSelectedItem() == null) {
            new Alertas().mostrarInformacion("CP DESTINO NO SELECCIONADO. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (tfCantidadBultos.getText().isEmpty()) {
            new Alertas().mostrarInformacion("HAY QUE INDICAR LA CANTIDAD DE BULTOS. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (tfPrecio.getText().isEmpty()) {
            new Alertas().mostrarInformacion("HAY QUE INDICAR EL PRECIO. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (tfDimA.getText().isEmpty()) {
            new Alertas().mostrarInformacion("HAY QUE INDICAR DIM A. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (tfDimH.getText().isEmpty()) {
            new Alertas().mostrarInformacion("HAY QUE INDICAR DIM H. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (tfDimL.getText().isEmpty()) {
            new Alertas().mostrarInformacion("HAY QUE INDICAR DIM L. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        if (tfPesoUnidad.getText().isEmpty()) {
            new Alertas().mostrarInformacion("HAY QUE INDICAR PESO POR UNIDAD. Todos los campos con asterisco son obligatorios, rellénelos por favor");
            return false;
        }
        return true;
    }
}
