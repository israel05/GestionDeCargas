package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase es un Modelo para la tabla de Paises y Provincia y localidad.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloPaisYProvinciaYLocalidad {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloPaisYProvinciaYLocalidad.class);


    private String localidad;

    private String provincia;
    private String pais;

    /**
     * Constructor del objeto a partir de sus tres campos
     *
     * @param pais      el nombre del país
     * @param provincia el nombre de la provincia
     * @param localidad el nombre de la localidad
     */
    public ModeloPaisYProvinciaYLocalidad(String pais, String provincia, String localidad) {
        this.pais = pais;
        this.provincia = provincia;
        this.localidad = localidad;
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
     * Obtiene el nombre de la localidad
     *
     * @return el nombre de la localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Establece el nombre de la localidad.
     *
     * @param localidad el nombre de la localidad
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getLocalidad() + ", " + getProvincia() + ", " + getPais();
    }
}
