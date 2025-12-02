package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloSistemaCargaVehiculo;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>SistemaCargaVehiculoStringConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class SistemaCargaVehiculoStringConverter extends StringConverter<ModeloSistemaCargaVehiculo> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(SistemaCargaVehiculoStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces se lee null entre los elementos del DaoPaisYProvincia y tira la app
     * mediante esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloSistemaCargaVehiculo object) {
        if (object != null) {
            // usaremos como plantilla Álava, España
            // asi es como se verán dentro del combo box, que para eso se ha creado esta clase
            return object.getCod_sistema_carga() + "," + object.getDescripcion();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloSistemaCargaVehiculo fromString(String modeloSistemaCarga) {
        //ahora hay que descomponer este string en dos partes, como hemos usado en toString
        // usaremos como plantilla Álava, España
        // hay que hacer usa separación para ese constructor
        String[] parts = modeloSistemaCarga.split(",");
        String cod = parts[1].trim();
        String descr = parts[0].trim();
        return new ModeloSistemaCargaVehiculo(cod, descr);
    }


}
