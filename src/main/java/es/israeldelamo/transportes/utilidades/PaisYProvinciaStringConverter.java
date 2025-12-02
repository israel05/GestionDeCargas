package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloPaisYProvincia;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>PaisYProvinciaStringConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class PaisYProvinciaStringConverter extends StringConverter<ModeloPaisYProvincia> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(PaisYProvinciaStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces se lee null entre los elementos del DaoPaisYProvincia y tira la app
     * mediante esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloPaisYProvincia object) {
        if (object != null) {
            // usaremos como plantilla Álava, España
            // asi es como se verán dentro del combo box, que para eso se ha creado esta clase
            return object.getProvincia() + "," + object.getPais();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloPaisYProvincia fromString(String paisYProvincia) {
        //ahora hay que descomponer este string en dos partes, como hemos usado en toString
        // usaremos como plantilla Álava, España
        // hay que hacer usa separación para ese constructor
        String[] parts = paisYProvincia.split(",");
        String pais = parts[1].trim();
        String provincia = parts[0].trim();
        return new ModeloPaisYProvincia(pais, provincia);
    }


}
