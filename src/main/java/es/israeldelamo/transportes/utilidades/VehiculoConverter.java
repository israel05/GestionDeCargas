package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloVehiculo;
import javafx.util.StringConverter;

/**
 * <p>VehiculoConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class VehiculoConverter extends StringConverter<ModeloVehiculo> {


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase esta extendida de StringConverter. A veces lee null entre los elementos del DaoPais y tira la app
     * medinate esta condicional interna se devuelve vacio como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloVehiculo object) {
        if (object != null) {
            return object.getCod_vehiculo();
        } else {
            return "";

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloVehiculo fromString(String string) {

        return new ModeloVehiculo(string);
    }


}
