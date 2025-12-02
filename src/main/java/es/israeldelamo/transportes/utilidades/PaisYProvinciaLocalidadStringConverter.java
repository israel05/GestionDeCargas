package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidad;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>PaisYProvinciaLocalidadStringConverter class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class PaisYProvinciaLocalidadStringConverter extends StringConverter<ModeloPaisYProvinciaYLocalidad> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(PaisYProvinciaLocalidadStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces se lee null entre los elementos del DaoPaisYProvincia y tira la app
     * mediante esta condicional interna se devuelve vacio como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloPaisYProvinciaYLocalidad object) {
        // usaremos como plantilla Vitoria, Álava, España
        if (object != null) {
            return object.getLocalidad() + ", " + object.getProvincia() + ", " + object.getPais();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     * <p>
     * Convierte una cadena en un objeto compuesto por los valores de esa cadena
     */
    @Override
    public ModeloPaisYProvinciaYLocalidad fromString(String paisYProvinciaYLocalidad) {
        //ahora hay que descomponer este string en dos partes, como hemos usado en toString
        //usaremos como plantilla "provincia (pais)"
        // hay que hacer usa spareación para ese constructor
        // la plantilla de la cadena es "Vitoria, Álava, España"

        String[] partes = paisYProvinciaYLocalidad.split(",");
        String localidad = partes[0].trim();
        String provincia = partes[1].trim();
        String pais = partes[2].trim();

        return new ModeloPaisYProvinciaYLocalidad(pais, provincia, localidad);
    }

}
