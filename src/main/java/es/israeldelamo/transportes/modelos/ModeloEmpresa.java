package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Modelo que refleja la tabla llamada Empresas
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloEmpresa {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloEmpresa.class);


    /**
     * CP de la empresa
     */
    private Integer cp;
    /**
     * El Cif único de la empresa
     */
    private String cif;
    /**
     * El nombre de la empresa que
     */
    private String nombre;
    /**
     * Domicilio de la empresa
     */
    private String domicilio;
    /**
     * País de la empresa
     */
    private String pais;
    /**
     * La provincia de la empresa
     */
    private String provincia;
    /**
     * La localidad de la empresa
     */
    private String localidad;


    private String correo;
    private String telefono;
    private String persona_contacto;

    /**
     * Un constructor de empresa con solo dos parámetros
     *
     * @param cp
     * @param nombre
     */
    public ModeloEmpresa(String cp, String nombre) {
        setCif(cp);
        setNombre(nombre);
    }

    /**
     * Un constructor vacio
     */
    public ModeloEmpresa() {

    }

    /**
     * <p>Constructor for ModeloEmpresa.</p>
     *
     * @param cif              a {@link java.lang.String} object
     * @param nombre           a {@link java.lang.String} object
     * @param domicilio        a {@link java.lang.String} object
     * @param pais             a {@link java.lang.String} object
     * @param provincia        a {@link java.lang.String} object
     * @param localidad        a {@link java.lang.String} object
     * @param cp               a {@link java.lang.Integer} object
     * @param correo           a {@link java.lang.String} object
     * @param telefono         a {@link java.lang.String} object
     * @param persona_contacto a {@link java.lang.String} object
     */
    public ModeloEmpresa(String cif, String nombre, String domicilio, String pais, String provincia, String localidad, Integer cp, String correo, String telefono, String persona_contacto) {
        this.cp = cp;
        this.cif = cif;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.pais = pais;
        this.provincia = provincia;
        this.localidad = localidad;
        this.correo = correo;
        this.telefono = telefono;
        this.persona_contacto = persona_contacto;
    }

    /**
     * <p>Getter for the field <code>cp</code>.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getCp() {
        return cp;
    }

    /**
     * <p>Setter for the field <code>cp</code>.</p>
     *
     * @param cp a {@link java.lang.Integer} object
     */
    public void setCp(Integer cp) {
        this.cp = cp;
    }

    /**
     * <p>Getter for the field <code>correo</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * <p>Setter for the field <code>correo</code>.</p>
     *
     * @param correo a {@link java.lang.String} object
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * <p>Getter for the field <code>telefono</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * <p>Setter for the field <code>telefono</code>.</p>
     *
     * @param telefono a {@link java.lang.String} object
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * <p>Getter for the field <code>persona_contacto</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getPersona_contacto() {
        return persona_contacto;
    }

    /**
     * <p>Setter for the field <code>persona_contacto</code>.</p>
     *
     * @param persona_contacto a {@link java.lang.String} object
     */
    public void setPersona_contacto(String persona_contacto) {
        this.persona_contacto = persona_contacto;
    }

    /**
     * <p>Getter for the field <code>cif</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getCif() {
        return cif;
    }

    /**
     * <p>Setter for the field <code>cif</code>.</p>
     *
     * @param cif a {@link java.lang.String} object
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * <p>Getter for the field <code>nombre</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * <p>Setter for the field <code>nombre</code>.</p>
     *
     * @param nombre a {@link java.lang.String} object
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * <p>Getter for the field <code>domicilio</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getDomicilio() {
        return domicilio;
    }

    /**
     * <p>Setter for the field <code>domicilio</code>.</p>
     *
     * @param domicilio a {@link java.lang.String} object
     */
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    /**
     * <p>Getter for the field <code>pais</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getPais() {
        return pais;
    }

    /**
     * <p>Setter for the field <code>pais</code>.</p>
     *
     * @param pais a {@link java.lang.String} object
     */
    public void setPais(String pais) {
        this.pais = pais;
    }

    /**
     * <p>Getter for the field <code>provincia</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * <p>Setter for the field <code>provincia</code>.</p>
     *
     * @param provincia a {@link java.lang.String} object
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * <p>Getter for the field <code>localidad</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * <p>Setter for the field <code>localidad</code>.</p>
     *
     * @param localidad a {@link java.lang.String} object
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * <p>getCP.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getCP() {
        return cp;
    }

    /**
     * <p>setCP.</p>
     *
     * @param cp a {@link java.lang.Integer} object
     */
    public void setCP(Integer cp) {
        this.cp = cp;
    }
}
