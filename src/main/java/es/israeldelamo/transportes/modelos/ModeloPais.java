package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Modelo que refleja la tabla llamada País
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloPais {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloPais.class);
    private String pais;

    /**
     * Constructor de Modelo de Pais, Solo tiene una cadena en la tabla que es para el listado de paises
     *
     * @param pais a {@link java.lang.String} object
     */
    public ModeloPais(String pais) {
        this.pais = pais;
    }

    /**
     * Devuelve el país
     *
     * @return a {@link java.lang.String} object
     */
    public String getPais() {
        return pais;
    }

    /**
     * Asigna el país
     *
     * @param pais a {@link java.lang.String} object
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloPais that = (ModeloPais) o;
        return Objects.equals(getPais(), that.getPais());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getPais());
    }


}
