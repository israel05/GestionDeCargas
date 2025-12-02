package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;

/**
 * Modelo que representa un documento de capacitación.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloDocumentoCapacitacion {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloDocumentoCapacitacion.class);


    private String cod_capacitacion;
    private Float peligrosidad;
    private Blob foto;

    /**
     * Constructor con sus tres parámetros.
     *
     * @param cod_capacitacion el código de la capacitación
     * @param peligrosidad     la peligrosidad que debe existir previamente en Naturalezas_Peligrosas
     * @param foto             la foto del documento
     */
    public ModeloDocumentoCapacitacion(String cod_capacitacion, Float peligrosidad, Blob foto) {
        this.cod_capacitacion = cod_capacitacion;
        this.peligrosidad = peligrosidad;
        this.foto = foto;
    }

    /**
     * Constructor vacío
     */
    public ModeloDocumentoCapacitacion() {

    }

    /**
     * Obtiene el código de la capacitación.
     *
     * @return el código de la capacitación
     */
    public String getCod_capacitacion() {
        return cod_capacitacion;
    }

    /**
     * Establece el código de la capacitación.
     *
     * @param cod_capacitacion el código de la capacitación
     */
    public void setCod_capacitacion(String cod_capacitacion) {
        this.cod_capacitacion = cod_capacitacion;
    }

    /**
     * Obtiene la peligrosidad.
     *
     * @return la peligrosidad
     */
    public Float getPeligrosidad() {
        return peligrosidad;
    }

    /**
     * Establece la peligrosidad.
     *
     * @param peligrosidad la peligrosidad
     */
    public void setPeligrosidad(Float peligrosidad) {
        this.peligrosidad = peligrosidad;
    }

    /**
     * Obtiene la foto del documento.
     *
     * @return la foto del documento
     */
    public Blob getFoto() {
        return foto;
    }

    /**
     * Establece la foto del documento.
     *
     * @param foto la foto del documento
     */
    public void setFoto(Blob foto) {
        this.foto = foto;
    }
}
