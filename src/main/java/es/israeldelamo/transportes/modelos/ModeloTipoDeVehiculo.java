package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;

/**
 * Los vehiculos tienen un tipo de vehículo general, este es el modelo de la tabla TIPO_DE_VEHICULO que los trata.
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloTipoDeVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloTipoDeVehiculo.class);

    /**
     * Código del tipo de vehículo.
     */
    private String cod_tipo_vehiculo;


    /**
     * Estipulación del vehículo.
     */
    private String estipulacion;

    /**
     * Foto del vehículo.
     */
    private Blob foto;

    /**
     * Instrucciones relacionadas con el vehículo.
     */
    private String instrucciones;

    /**
     * Indica si el vehículo puede transportar materiales peligrosos de tipo Atp.
     */
    private Boolean puede_atp;

    /**
     * Indica si el vehículo puede transportar materiales peligrosos de tipo ADR.
     */
    private Boolean puede_adr;

    /**
     * Código del sistema de carga del vehículo.
     */
    private String cod_sistema_carga;

    /**
     * Indica si el vehículo tiene rampa de carga.
     */
    private Boolean tiene_rampa;

    /**
     * Constructor de la clase ModeloTipoDeVehiculo.
     *
     * @param cod_tipo_vehiculo Código del tipo de vehículo.
     * @param estipulacion      Estipulación del vehículo.
     * @param foto              Foto del vehículo.
     * @param instrucciones     Instrucciones relacionadas con el vehículo.
     * @param puede_adr         Indica si el vehículo puede transportar materiales peligrosos de tipo ADR.
     * @param puede_adr         Indica si el vehículo puede transportar materiales peligrosos de tipo ADR.
     * @param cod_sistema_carga Código del sistema de carga del vehículo.
     * @param tiene_rampa       Indica si el vehículo tiene rampa de carga.
     */
    public ModeloTipoDeVehiculo(String cod_tipo_vehiculo,

                                String estipulacion,
                                Blob foto,
                                String instrucciones,
                                Boolean puede_atp,
                                Boolean puede_adr,
                                String cod_sistema_carga,
                                Boolean tiene_rampa) {
        this.cod_tipo_vehiculo = cod_tipo_vehiculo;

        this.estipulacion = estipulacion;
        this.foto = foto;
        this.instrucciones = instrucciones;
        this.puede_adr = puede_adr;
        this.puede_atp = puede_atp;
        this.cod_sistema_carga = cod_sistema_carga;
        this.tiene_rampa = tiene_rampa;
    }

    /**
     * Constructor vacío de la clase ModeloTipoDeVehiculo.
     */
    public ModeloTipoDeVehiculo() {
    }

    /**
     * Constructor simple para poder usar el string converter
     *
     * @param cod
     * @param instrucciones
     */
    public ModeloTipoDeVehiculo(String cod, String instrucciones) {
        this.cod_tipo_vehiculo = cod;
        this.instrucciones = instrucciones;
    }

    /**
     * <p>Getter for the field <code>cod_tipo_vehiculo</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getCod_tipo_vehiculo() {
        return cod_tipo_vehiculo;
    }

    /**
     * <p>Setter for the field <code>cod_tipo_vehiculo</code>.</p>
     *
     * @param cod_tipo_vehiculo a {@link java.lang.String} object
     */
    public void setCod_tipo_vehiculo(String cod_tipo_vehiculo) {
        this.cod_tipo_vehiculo = cod_tipo_vehiculo;
    }


    /**
     * <p>Getter for the field <code>estipulacion</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getEstipulacion() {
        return estipulacion;
    }

    /**
     * <p>Setter for the field <code>estipulacion</code>.</p>
     *
     * @param estipulacion a {@link java.lang.String} object
     */
    public void setEstipulacion(String estipulacion) {
        this.estipulacion = estipulacion;
    }

    /**
     * <p>Getter for the field <code>foto</code>.</p>
     *
     * @return a Blob object
     */
    public Blob getFoto() {
        return foto;
    }

    /**
     * <p>Setter for the field <code>foto</code>.</p>
     *
     * @param foto a Blob object
     */
    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    /**
     * <p>Getter for the field <code>instrucciones</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getInstrucciones() {
        return instrucciones;
    }

    /**
     * <p>Setter for the field <code>instrucciones</code>.</p>
     *
     * @param instrucciones a {@link java.lang.String} object
     */
    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    /**
     * <p>Getter for the field <code>puede_atp</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getPuede_atp() {
        return puede_atp;
    }

    /**
     * <p>Setter for the field <code>puede_atp</code>.</p>
     *
     * @param puede_atp a {@link java.lang.Boolean} object
     */
    public void setPuede_atp(Boolean puede_atp) {
        this.puede_atp = puede_atp;
    }

    /**
     * <p>Getter for the field <code>puede_adr</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getPuede_adr() {
        return puede_adr;
    }

    /**
     * <p>Setter for the field <code>puede_adr</code>.</p>
     *
     * @param puede_adr a {@link java.lang.Boolean} object
     */
    public void setPuede_adr(Boolean puede_adr) {
        this.puede_adr = puede_adr;
    }

    /**
     * <p>Getter for the field <code>cod_sistema_carga</code>.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public String getCod_sistema_carga() {
        return cod_sistema_carga;
    }

    /**
     * <p>Setter for the field <code>cod_sistema_carga</code>.</p>
     *
     * @param cod_sistema_carga a {@link java.lang.Integer} object
     */
    public void setCod_sistema_carga(String cod_sistema_carga) {
        this.cod_sistema_carga = cod_sistema_carga;
    }

    /**
     * <p>Getter for the field <code>tiene_rampa</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getTiene_rampa() {
        return tiene_rampa;
    }

    /**
     * <p>Setter for the field <code>tiene_rampa</code>.</p>
     *
     * @param tiene_rampa a {@link java.lang.Boolean} object
     */
    public void setTiene_rampa(Boolean tiene_rampa) {
        this.tiene_rampa = tiene_rampa;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve el objeto en forma de cadena formateada
     */
    @Override
    public String toString() {
        return "ModeloTipoDeVehiculo{" +
                "cod_tipo_vehiculo='" + cod_tipo_vehiculo + '\'' +
                ", estipulacion='" + estipulacion + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                ", puede_atp=" + puede_atp +
                ", puede_adr=" + puede_adr +
                ", cod_sistema_carga=" + cod_sistema_carga +
                ", tiene_rampa=" + tiene_rampa +
                '}';
    }
}
