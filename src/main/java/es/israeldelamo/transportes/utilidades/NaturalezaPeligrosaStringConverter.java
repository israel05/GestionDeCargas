package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloNaturalezasPeligrosas;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>NaturalezaPeligrosaStringConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class NaturalezaPeligrosaStringConverter extends StringConverter<ModeloNaturalezasPeligrosas> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(NaturalezaPeligrosaStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces lee null entre los elementos del DaoPais y tira la app
     * medinate esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloNaturalezasPeligrosas object) {
        if (object != null) {
            return object.getCodigo_naturaleza_peligrosa() + ", " + object.getDescripcion_naturaleza_peligrosa();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloNaturalezasPeligrosas fromString(String cadenaCompuestaDeNaturalezas) {

        String[] parts = cadenaCompuestaDeNaturalezas.split(", ");
        String codigo = parts[0].trim();
        String descripcion = parts[1].trim();
        return new ModeloNaturalezasPeligrosas(Float.parseFloat(codigo), descripcion);

    }


}
