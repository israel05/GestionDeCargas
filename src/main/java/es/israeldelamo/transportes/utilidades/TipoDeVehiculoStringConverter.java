package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloTipoDeVehiculo;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>TipoDeVehiculoStringConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class TipoDeVehiculoStringConverter extends StringConverter<ModeloTipoDeVehiculo> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(TipoDeVehiculoStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces se lee null entre los elementos del DaoPaisYProvincia y tira la app
     * mediante esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloTipoDeVehiculo object) {
        if (object != null) {
            // usaremos como plantilla Álava, España
            // asi es como se verán dentro del combo box, que para eso se ha creado esta clase
            return object.getCod_sistema_carga() + "," + object.getInstrucciones();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloTipoDeVehiculo fromString(String modeloTipoVehiculo) {
        //ahora hay que descomponer este string en dos partes, como hemos usado en toString
        // hay que hacer usa separación para ese constructor
        String[] parts = modeloTipoVehiculo.split(",");
        String cod = parts[1].trim();
        String instrucciones = parts[0].trim();
        return new ModeloTipoDeVehiculo(cod, instrucciones);
    }


}
