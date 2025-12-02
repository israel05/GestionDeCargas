package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;

/**
 * Modelo que refleja la tabla llamada Tipo de Bulto
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloTipoDeBulto {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloTipoDeBulto.class);

    private int cod_tipo_bulto;
    private String nombre_bulto;

    private Blob imagen_bulto;

    /**
     * Constructor con los tres parámetros del objeto
     *
     * @param cod_tipo_bulto el código del tipo de bulto
     * @param nombre_bulto   el nombre del bulto
     * @param imagen_bulto   la imagen del bulto
     */
    public ModeloTipoDeBulto(int cod_tipo_bulto, String nombre_bulto, Blob imagen_bulto) {
        this.cod_tipo_bulto = cod_tipo_bulto;
        this.nombre_bulto = nombre_bulto;
        this.imagen_bulto = imagen_bulto;
    }

    /**
     * Constructor solo con el código.
     *
     * @param cod_tipo_bulto el código del tipo de bulto
     */
    public ModeloTipoDeBulto(int cod_tipo_bulto) {
        this.cod_tipo_bulto = cod_tipo_bulto;

    }

    /**
     * Devuelve la imagen del bulto.
     *
     * @return la imagen del bulto
     */
    public Blob getImagen_bulto() {
        return imagen_bulto;
    }

    /**
     * Establece la imagen del bulto.
     *
     * @param imagen_bulto la imagen del bulto
     */
    public void setImagen_bulto(Blob imagen_bulto) {
        this.imagen_bulto = imagen_bulto;
    }

    /**
     * Obtiene el código del tipo de bulto.
     *
     * @return el código del tipo de bulto
     */
    public int getCod_tipo_bulto() {
        return cod_tipo_bulto;
    }

    /**
     * Establece el código del tipo de bulto.
     *
     * @param cod_tipo_bulto el código del tipo de bulto
     */
    public void setCod_tipo_bulto(int cod_tipo_bulto) {
        this.cod_tipo_bulto = cod_tipo_bulto;
    }

    /**
     * Obtiene el nombre del bulto.
     *
     * @return el nombre del bulto
     */
    public String getNombre_bulto() {
        return nombre_bulto;
    }

    /**
     * Establece el nombre del bulto.
     *
     * @param nombre_bulto el nombre del bulto
     */
    public void setNombre_bulto(String nombre_bulto) {
        this.nombre_bulto = nombre_bulto;
    }

    @Override
    public String toString() {
        return "Bulto de tipo " + getNombre_bulto();

    }
}
