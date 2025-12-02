package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


/**
 * Modelo que refleja la tabla llamada Conductor Tiene Vehículo
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloConductorTieneVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloConductorTieneVehiculo.class);


    private String conductor_dni;
    private String vehiculos_cod_vehiculo;

    /**
     * Constructor que inicializa el objeto ModeloConductorTieneVehiculo con el DNI del conductor y el código del vehículo.
     *
     * @param conductor_dni          el DNI del conductor
     * @param vehiculos_cod_vehiculo el código del vehículo
     */
    public ModeloConductorTieneVehiculo(String conductor_dni, String vehiculos_cod_vehiculo) {
        this.conductor_dni = conductor_dni;
        this.vehiculos_cod_vehiculo = vehiculos_cod_vehiculo;
    }

    /**
     * Obtiene el DNI del conductor.
     *
     * @return el DNI del conductor
     */
    public String getConductor_dni() {
        return conductor_dni;
    }

    /**
     * Establece el DNI del conductor.
     *
     * @param conductor_dni el DNI del conductor
     */
    public void setConductor_dni(String conductor_dni) {
        this.conductor_dni = conductor_dni;
    }

    /**
     * Obtiene el código del vehículo.
     *
     * @return el código del vehículo
     */
    public String getVehiculos_cod_vehiculo() {
        return vehiculos_cod_vehiculo;
    }

    /**
     * Establece el código del vehículo.
     *
     * @param vehiculos_cod_vehiculo el código del vehículo
     */
    public void setVehiculos_cod_vehiculo(String vehiculos_cod_vehiculo) {
        this.vehiculos_cod_vehiculo = vehiculos_cod_vehiculo;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve una representación en cadena del objeto ModeloConductorTieneVehiculo.
     */
    @Override
    public String toString() {
        return "ModeloConductorTieneVehiculo{" +
                "conductor_dni='" + conductor_dni + '\'' +
                ", vehiculos_cod_vehiculo='" + vehiculos_cod_vehiculo + '\'' +
                '}';
    }

    /**
     * {@inheritDoc}
     * <p>
     * Compara si dos objetos ModeloConductorTieneVehiculo son iguales.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloConductorTieneVehiculo that = (ModeloConductorTieneVehiculo) o;
        return Objects.equals(getConductor_dni(), that.getConductor_dni()) && Objects.equals(getVehiculos_cod_vehiculo(), that.getVehiculos_cod_vehiculo());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve el valor hash del objeto ModeloConductorTieneVehiculo.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getConductor_dni(), getVehiculos_cod_vehiculo());
    }
}
