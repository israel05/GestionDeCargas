package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * <p>ModeloConductoTieneDocuCapac class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloConductoTieneDocuCapac {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloConductoTieneDocuCapac.class);


    private String dni_conductor;
    private String cod_capacitacion;
    private float doc_capac_cod_naturaleza;

    /**
     * Constructor para ModeloConductoTieneDocuCapac.
     *
     * @param dni_conductor            El DNI del conductor.
     * @param cod_capacitacion         El código de capacitación.
     * @param doc_capac_cod_naturaleza El código de naturaleza de la documentación de capacitación.
     */
    public ModeloConductoTieneDocuCapac(String dni_conductor, String cod_capacitacion, float doc_capac_cod_naturaleza) {
        this.dni_conductor = dni_conductor;
        this.cod_capacitacion = cod_capacitacion;
        this.doc_capac_cod_naturaleza = doc_capac_cod_naturaleza;
    }

    /**
     * Obtiene el DNI del conductor.
     *
     * @return El DNI del conductor.
     */
    public String getDni_conductor() {
        return dni_conductor;
    }

    /**
     * Establece el DNI del conductor.
     *
     * @param dni_conductor El DNI del conductor a establecer.
     */
    public void setDni_conductor(String dni_conductor) {
        this.dni_conductor = dni_conductor;
    }

    /**
     * Obtiene el código de capacitación.
     *
     * @return El código de capacitación.
     */
    public String getCod_capacitacion() {
        return cod_capacitacion;
    }

    /**
     * Establece el código de capacitación.
     *
     * @param cod_capacitacion El código de capacitación a establecer.
     */
    public void setCod_capacitacion(String cod_capacitacion) {
        this.cod_capacitacion = cod_capacitacion;
    }

    /**
     * Obtiene el código de naturaleza de la documentación de capacitación.
     *
     * @return El código de naturaleza de la documentación de capacitación.
     */
    public float getDoc_capac_cod_naturaleza() {
        return doc_capac_cod_naturaleza;
    }

    /**
     * Establece el código de naturaleza de la documentación de capacitación.
     *
     * @param doc_capac_cod_naturaleza El código de naturaleza a establecer.
     */
    public void setDoc_capac_cod_naturaleza(float doc_capac_cod_naturaleza) {
        this.doc_capac_cod_naturaleza = doc_capac_cod_naturaleza;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloConductoTieneDocuCapac that = (ModeloConductoTieneDocuCapac) o;
        return Float.compare(getDoc_capac_cod_naturaleza(), that.getDoc_capac_cod_naturaleza()) == 0 && Objects.equals(getDni_conductor(), that.getDni_conductor()) && Objects.equals(getCod_capacitacion(), that.getCod_capacitacion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getDni_conductor(), getCod_capacitacion(), getDoc_capac_cod_naturaleza());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ModeloConductoTieneDocuCapac{" +
                "dni_conductor='" + dni_conductor + '\'' +
                ", cod_capacitacion='" + cod_capacitacion + '\'' +
                ", doc_capac_cod_naturaleza=" + doc_capac_cod_naturaleza +
                '}';
    }
}
