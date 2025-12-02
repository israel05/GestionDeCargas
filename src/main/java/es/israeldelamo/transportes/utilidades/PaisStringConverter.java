package es.israeldelamo.transportes.utilidades;


import es.israeldelamo.transportes.modelos.ModeloPais;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>PaisStringConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class PaisStringConverter extends StringConverter<ModeloPais> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(PaisStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase esta extendida de StringConverter. A veces lee null entre los elementos del DaoPais y tira la app
     * medinate esta condicional interna se devuelve vacio como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloPais object) {
        if (object != null) {
            return object.getPais();
        } else {
            return "";

        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloPais fromString(String string) {
        return new ModeloPais(string);
    }
}
