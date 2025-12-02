package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase está extendida de StringConverter. A veces se lee null entre los elementos del DaoPaisYProvinciaYCodigoPostal y tira la app
 * mediante esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
 *
 * @author israel
 * @version $Id: $Id
 */
public class PaisYProvinciaYLocalidadYCodigoPostalStringConverter extends StringConverter<ModeloPaisYProvinciaYLocalidadYCodigoPostal> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(PaisYProvinciaYLocalidadYCodigoPostalStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Método toString para obtener una cadena con la info del objeto imprescindible para combo box
     * mediante esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloPaisYProvinciaYLocalidadYCodigoPostal object) {
        // la plantilla de la cadena es "Vitoria, (Álava) España"
        if (object != null) {
            return object.getCodigoPostal().toString() + ", " + object.getLocalidad() + ", " + object.getProvincia() + ", " + object.getPais();
        } else {
            return "";
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Método constructor de un PaisYProvinciaY Código Postal a partir de una cadena. Imprescindible para combo box
     */
    @Override
    public ModeloPaisYProvinciaYLocalidadYCodigoPostal fromString(String paisYProvinciaYLocalidadYCodigoPostal) {
        //ahora hay que descomponer este string en dos partes, como hemos usado en toString
        // usaremos como plantilla 01005, Vitoria, Álava, España
        //  hay que hacer usa separación para ese constructor
        String[] partes = paisYProvinciaYLocalidadYCodigoPostal.split(",");
        String cp = partes[3].trim();
        int integerCP = Integer.parseInt(cp);

        String localidad = partes[1].trim();
        String provincia = partes[2].trim();
        String pais = partes[0].trim();


        return new ModeloPaisYProvinciaYLocalidadYCodigoPostal(integerCP, localidad, provincia, pais);
    }


}
