package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase es un Modelo para la tabla de Paises y Provincia y localidad y cp.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloPaisYProvinciaYLocalidadYCodigoPostal {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloPaisYProvinciaYLocalidadYCodigoPostal.class);
    private Integer codigoPostal;
    private String localidad;
    private String provincia;
    private String pais;

    /**
     * Constructor vacio auxiliar
     */
    public ModeloPaisYProvinciaYLocalidadYCodigoPostal() {

    }

    /**
     * Constructor con todos los parámetros
     *
     * @param codigoPostal el código postal
     * @param localidad    la localidad
     * @param provincia    la provincia
     * @param pais         el país
     */
    public ModeloPaisYProvinciaYLocalidadYCodigoPostal(Integer codigoPostal, String localidad, String provincia, String pais) {
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
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
     * Obtiene el código postal.
     *
     * @return el código postal
     */
    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    /**
     * Establece el código postal.
     *
     * @param codigoPostal el código postal
     */
    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    /**
     * Obtiene el nombre de la localidad.
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
     * <p>
     * Devuelve una representación en forma de cadena de la información del modelo.
     */
    @Override
    public String toString() {
        // usaremos como plantilla Vitoria, Álava, España, 01005

        return localidad + ", " + provincia + ", " + pais + ", " + codigoPostal.toString();
    }
}
