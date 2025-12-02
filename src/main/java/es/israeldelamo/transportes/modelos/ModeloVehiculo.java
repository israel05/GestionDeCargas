package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Modelo que refleja la tabla llamada Vehículo
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloVehiculo.class);


    /**
     * Código único del vehículo.
     */
    private String cod_vehiculo;
    /**
     * Masa Máxima Autorizada del vehículo.
     */
    private float MMA;
    /**
     * Dimensión de altura del vehículo.
     */
    private float dimension_A;
    /**
     * Dimensión de longitud del vehículo.
     */
    private float dimension_L;
    /**
     * Dimensión de altura del vehículo.
     */
    private float dimension_H;
    /**
     * Carga útil que el vehículo puede transportar.
     */
    private float carga_util;
    /**
     * Código que identifica el tipo de vehículo.
     */
    private String cod_tipo_vehiculo;

    /**
     * <p>Constructor for ModeloVehiculo.</p>
     *
     * @param cod_vehiculo      a {@link java.lang.String} object
     * @param mMA               a float
     * @param dimension_A       a float
     * @param dimension_L       a float
     * @param dimension_H       a float
     * @param carg_util         a float
     * @param cod_tipo_vehiculo a {@link java.lang.String} object
     */
    public ModeloVehiculo(String cod_vehiculo, float mMA, float dimension_A, float dimension_L, float dimension_H, float carg_util, String cod_tipo_vehiculo) {
        super();
        this.cod_vehiculo = cod_vehiculo;
        MMA = mMA;
        this.dimension_A = dimension_A;
        this.dimension_L = dimension_L;
        this.dimension_H = dimension_H;
        this.carga_util = carg_util;
        this.cod_tipo_vehiculo = cod_tipo_vehiculo;
    }

    /**
     * Constructor justo con el código o matrícula
     *
     * @param cod_vehiculo a {@link java.lang.String} object
     */
    public ModeloVehiculo(String cod_vehiculo) {
        this.cod_vehiculo = cod_vehiculo;
    }

    /**
     * Obtiene el código del tipo de vehículo.
     *
     * @return El código del tipo de vehículo.
     */
    public String getCod_tipo_vehiculo() {
        return cod_tipo_vehiculo;
    }

    /**
     * Establece el código del tipo de vehículo.
     *
     * @param cod_tipo_vehiculo El código del tipo de vehículo.
     */
    public void setCod_tipo_vehiculo(String cod_tipo_vehiculo) {
        this.cod_tipo_vehiculo = cod_tipo_vehiculo;
    }

    /**
     * Obtiene la carga útil del vehículo.
     *
     * @return La carga útil del vehículo.
     */
    public float getCarga_util() {
        return carga_util;
    }

    /**
     * Establece la carga útil del vehículo.
     *
     * @param carga_util La carga útil a establecer.
     */
    public void setCarga_util(float carga_util) {
        this.carga_util = carga_util;
    }

    /**
     * Obtiene el código del vehículo.
     *
     * @return El código del vehículo.
     */
    public String getCod_vehiculo() {
        return cod_vehiculo;
    }

    /**
     * <p>Setter for the field <code>cod_vehiculo</code>.</p>
     *
     * @param cod_vehiculo a {@link java.lang.String} object
     */
    public void setCod_vehiculo(String cod_vehiculo) {
        this.cod_vehiculo = cod_vehiculo;
    }

    /**
     * <p>getMMA.</p>
     *
     * @return a float
     */
    public float getMMA() {
        return MMA;
    }

    /**
     * <p>setMMA.</p>
     *
     * @param mMA Masa Máxima Autorizada del vehículo.
     */
    public void setMMA(float mMA) {
        MMA = mMA;
    }

    /**
     * <p>Getter for the field <code>dimension_A</code>.</p>
     *
     * @return a float
     */
    public float getDimension_A() {
        return dimension_A;
    }

    /**
     * <p>Setter for the field <code>dimension_A</code>.</p>
     *
     * @param dimension_A a float
     */
    public void setDimension_A(float dimension_A) {
        this.dimension_A = dimension_A;
    }

    /**
     * <p>Getter for the field <code>dimension_L</code>.</p>
     *
     * @return a float
     */
    public float getDimension_L() {
        return dimension_L;
    }

    /**
     * <p>Setter for the field <code>dimension_L</code>.</p>
     *
     * @param dimension_L a float
     */
    public void setDimension_L(float dimension_L) {
        this.dimension_L = dimension_L;
    }

    /**
     * <p>Getter for the field <code>dimension_H</code>.</p>
     *
     * @return a float
     */
    public float getDimension_H() {
        return dimension_H;
    }

    /**
     * <p>Setter for the field <code>dimension_H</code>.</p>
     *
     * @param dimension_H Dimensión de altura del vehículo.
     */
    public void setDimension_H(float dimension_H) {
        this.dimension_H = dimension_H;
    }
}
