package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Modelo que refleja la tabla llamada Naturaleza Peligrosa
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloNaturalezasPeligrosas {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloNaturalezasPeligrosas.class);


    private float codigo_naturaleza_peligrosa;
    private String descripcion_naturaleza_peligrosa;

    /**
     * Constructor con sus dos parámetros
     *
     * @param codigo_naturaleza_peligrosa      a float
     * @param descripcion_naturaleza_peligrosa a {@link java.lang.String} object
     */
    public ModeloNaturalezasPeligrosas(float codigo_naturaleza_peligrosa, String descripcion_naturaleza_peligrosa) {
        this.codigo_naturaleza_peligrosa = codigo_naturaleza_peligrosa;
        this.descripcion_naturaleza_peligrosa = descripcion_naturaleza_peligrosa;
    }

    /**
     * Obtiene el código de la naturaleza peligrosa.
     *
     * @return el código de la naturaleza peligrosa
     */
    public float getCodigo_naturaleza_peligrosa() {
        return codigo_naturaleza_peligrosa;
    }

    /**
     * Establece el código de la naturaleza peligrosa.
     *
     * @param codigo_naturaleza_peligrosa el código de la naturaleza peligrosa
     */
    public void setCodigo_naturaleza_peligrosa(float codigo_naturaleza_peligrosa) {
        this.codigo_naturaleza_peligrosa = codigo_naturaleza_peligrosa;
    }

    /**
     * Obtiene la descripción de la naturaleza peligrosa.
     *
     * @return la descripción de la naturaleza peligrosa
     */
    public String getDescripcion_naturaleza_peligrosa() {
        return descripcion_naturaleza_peligrosa;
    }

    /**
     * Establece la descripción de la naturaleza peligrosa.
     *
     * @param descripcion_naturaleza_peligrosa la descripción de la naturaleza peligrosa
     */
    public void setDescripcion_naturaleza_peligrosa(String descripcion_naturaleza_peligrosa) {
        this.descripcion_naturaleza_peligrosa = descripcion_naturaleza_peligrosa;
    }


    /**
     * {@inheritDoc}
     * <p>
     * Genera un valor hash para este objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCodigo_naturaleza_peligrosa(), getDescripcion_naturaleza_peligrosa());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve la descripción de la naturaleza peligrosa.
     */
    @Override
    public String toString() {
        return getCodigo_naturaleza_peligrosa() + ", " + getDescripcion_naturaleza_peligrosa();
    }
}
