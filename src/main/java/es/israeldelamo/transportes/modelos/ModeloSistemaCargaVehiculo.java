package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Modelo que representa el sistema de carga de un vehículo.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloSistemaCargaVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloSistemaCargaVehiculo.class);


    private String cod_sistema_carga;
    private String descripcion;

    /**
     * Constructor vacío del modelo de sistema de carga de vehículo.
     */
    public ModeloSistemaCargaVehiculo() {

    }

    /**
     * Constructor con todos los parámetros del modelo de sistema de carga de vehículo.
     *
     * @param cod_sistema_carga el código del sistema de carga
     * @param descripcion       la descripción del sistema de carga
     */
    public ModeloSistemaCargaVehiculo(String cod_sistema_carga, String descripcion) {
        this.cod_sistema_carga = cod_sistema_carga;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código del sistema de carga.
     *
     * @return el código del sistema de carga
     */
    public String getCod_sistema_carga() {
        return cod_sistema_carga;
    }

    /**
     * Establece el código del sistema de carga.
     *
     * @param cod_sistema_carga el código del sistema de carga
     */
    public void setCod_sistema_carga(String cod_sistema_carga) {
        this.cod_sistema_carga = cod_sistema_carga;
    }

    /**
     * Obtiene la descripción del sistema de carga.
     *
     * @return la descripción del sistema de carga
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del sistema de carga.
     *
     * @param descripcion la descripción del sistema de carga
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
