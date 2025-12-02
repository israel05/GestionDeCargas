package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase es un Modelo para la tabla de Paises y Provincia.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloPaisYProvincia {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloPaisYProvincia.class);


    /**
     * Constructor por defecto con los dos parámetros, el pais debe existir previamente en la base de datos
     */

    /**
     * Guarda el país
     */
    private String pais;
    /**
     * Guarda la provincia
     */
    private String provincia;

    /**
     * <p>Constructor for ModeloPaisYProvincia.</p>
     *
     * @param pais      a {@link java.lang.String} object
     * @param provincia a {@link java.lang.String} object
     */
    public ModeloPaisYProvincia(String pais, String provincia) {
        this.pais = pais;
        this.provincia = provincia;
    }

    /**
     * Obtiene el nombre del país.
     *
     * @return el nombre del país
     */
    public String getPais() {
        return pais;
    }

    /**
     * Establece el nombre del país.
     *
     * @param pais el nombre del país
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * Obtiene el nombre de la provincia.
     *
     * @return el nombre de la provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Establece el nombre de la provincia.
     *
     * @param provincia el nombre de la provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getProvincia() + ", " + getPais();
    }
}
