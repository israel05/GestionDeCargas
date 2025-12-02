package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Clase que representa un modelo de envío unico, pero con toda la información relevante del mismo.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloEnvioUnico {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloEnvioUnico.class);


    /**
     * El código del tracker de ese envio único
     */
    private String cod_tracking;
    /**
     * El código de la carga de ese envio único
     */
    private int cod_carga_entrega;
    /**
     * El código del conductor de ese envio único
     */
    private String cod_conductor;
    /**
     * El código del vehículo de ese envio único
     */
    private String cod_vehiculo;

    /**
     * Constructor de la clase ModeloEnvio.
     *
     * @param cod_tracking      el código de seguimiento
     * @param cod_carga_entrega el código de carga de entrega
     * @param cod_conductor     el código del conductor
     * @param cod_vehiculo      el código del vehículo
     */
    public ModeloEnvioUnico(String cod_tracking, int cod_carga_entrega, String cod_conductor, String cod_vehiculo) {
        this.cod_tracking = cod_tracking;
        this.cod_carga_entrega = cod_carga_entrega;
        this.cod_conductor = cod_conductor;
        this.cod_vehiculo = cod_vehiculo;
    }

    /**
     * Obtiene el código de seguimiento.
     *
     * @return el código de seguimiento
     */
    public String getCod_tracking() {
        return cod_tracking;
    }

    /**
     * Establece el código de seguimiento.
     *
     * @param cod_tracking el código de seguimiento
     */
    public void setCod_tracking(String cod_tracking) {
        this.cod_tracking = cod_tracking;
    }

    /**
     * Obtiene el código de carga de entrega.
     *
     * @return el código de carga de entrega
     */
    public int getCod_carga_entrega() {
        return cod_carga_entrega;
    }

    /**
     * Establece el código de carga de entrega.
     *
     * @param cod_carga_entrega el código de carga de entrega
     */
    public void setCod_carga_entrega(int cod_carga_entrega) {
        this.cod_carga_entrega = cod_carga_entrega;
    }

    /**
     * Obtiene el código del conductor.
     *
     * @return el código del conductor
     */
    public String getCod_conductor() {
        return cod_conductor;
    }

    /**
     * Establece el código del conductor.
     *
     * @param cod_conductor el código del conductor
     */
    public void setCod_conductor(String cod_conductor) {
        this.cod_conductor = cod_conductor;
    }

    /**
     * Obtiene el código del vehículo.
     *
     * @return el código del vehículo
     */
    public String getCod_vehiculo() {
        return cod_vehiculo;
    }

    /**
     * Establece el código del vehículo.
     *
     * @param cod_vehiculo el código del vehículo
     */
    public void setCod_vehiculo(String cod_vehiculo) {
        this.cod_vehiculo = cod_vehiculo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getCod_tracking(), getCod_carga_entrega(), getCod_conductor(), getCod_vehiculo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ModeloEnvioUnico{" +
                "cod_tracking='" + cod_tracking + '\'' +
                ", cod_carga_entrega='" + cod_carga_entrega + '\'' +
                ", cod_conductor='" + cod_conductor + '\'' +
                ", cod_vehiculo='" + cod_vehiculo + '\'' +
                '}';
    }
}
