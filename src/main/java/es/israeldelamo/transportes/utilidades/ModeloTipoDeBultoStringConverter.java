package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloTipoDeBulto;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModeloTipoDeBultoStringConverter extends StringConverter<ModeloTipoDeBulto> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloTipoDeBultoStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces lee null entre los elementos del DaoPais y tira la app
     * medinate esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloTipoDeBulto object) {
        if (object != null) {
            return object.getCod_tipo_bulto() + ", " + object.getNombre_bulto();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloTipoDeBulto fromString(String cadenaCompuestaDeTipoDeBulto) {

        String[] parts = cadenaCompuestaDeTipoDeBulto.split(", ");
        String codigo = parts[0].trim();
        String nombre = parts[1].trim();
        return new ModeloTipoDeBulto(Integer.parseInt(codigo), nombre, null);//va sin foto

    }


}
