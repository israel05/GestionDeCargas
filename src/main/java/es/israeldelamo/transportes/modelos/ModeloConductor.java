package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;

/**
 * Modelo de Conductor que coincide con la fila básica de la tabla CONDUCTOR de la base de datos
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloConductor {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloConductor.class);
    /**
     *
     */
    public String telefono;
    /**
     * El dni del conductor, clave primaria de la tabla
     */
    private String dni;
    /**
     * El nombre del conductor
     */
    private String nombre;
    /**
     * EL Apellido del conductor
     */
    private String apellido;
    /**
     * Un nombre de conctacto para entrega y recogida
     */
    private String contacto_recogida;
    /**
     * Un correo electrónico de contacto
     */
    private String correo_electronico;
    /**
     * Una foto pequeña identificativa
     */
    private Blob foto_humano;
    /**
     * Si es internacional o no
     */
    private Boolean es_internacional;
    /**
     * Corresponde con un país existente
     */
    private String nacionalidad;
    /**
     * Una clave externa hacia el codigo postal de su residencia
     */
    private Integer codigo_postal;
    /**
     *
     */
    private String localidad;
    /**
     * Una clave externa hacia la provincia de us residencia
     */

    private String provincia;
    /**
     * Una clave externa hacia pais de su residencia
     */

    private String pais;
    /**
     * Algunos de ellos estarán es la lista negra, basta con que este campo no sea null
     */
    private String motivo_lista_negra;
    /**
     * Una clave externa hacia el tipo de carnet que tiene
     */

    private String tipo_de_carnet;

    /**
     * un constructor vacio
     */
    public ModeloConductor() {

    }

    /**
     * Constructor con todos los parámetros
     *
     * @param dni                a {@link java.lang.String} object
     * @param nombre             a {@link java.lang.String} object
     * @param apellido           a {@link java.lang.String} object
     * @param contacto_recogida  a {@link java.lang.String} object
     * @param correo_electronico a {@link java.lang.String} object
     * @param foto_humano        a Blob object
     * @param es_internacional   a {@link java.lang.Boolean} object
     * @param codigo_postal      a {@link java.lang.Integer} object
     * @param provincia          a {@link java.lang.String} object
     * @param localidad          a {@link java.lang.String} object
     * @param pais               De su dirección
     * @param tipo_de_carnet     a {@link java.lang.String} object
     * @param telefono           a {@link java.lang.String} object
     * @param motivo_lista_negra a {@link java.lang.String} object
     * @param nacionalidad       De origen
     */
    public ModeloConductor(String dni, String nombre, String apellido, String contacto_recogida, String correo_electronico, Blob foto_humano, Boolean es_internacional, Integer codigo_postal, String provincia, String localidad, String pais, String tipo_de_carnet, String telefono, String motivo_lista_negra, String nacionalidad) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contacto_recogida = contacto_recogida;
        this.correo_electronico = correo_electronico;
        this.foto_humano = foto_humano;
        this.es_internacional = es_internacional;
        this.codigo_postal = codigo_postal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
        this.tipo_de_carnet = tipo_de_carnet;
        this.motivo_lista_negra = motivo_lista_negra;
        this.telefono = telefono;
        this.nacionalidad = nacionalidad;


    }

    /**
     * <p>Getter for the field <code>motivo_lista_negra</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getMotivo_lista_negra() {
        return motivo_lista_negra;
    }

    /**
     * EL motivo por el cual está en la lista negra
     *
     * @param motivo_lista_negra a {@link java.lang.String} object
     */
    public void setMotivo_lista_negra(String motivo_lista_negra) {
        this.motivo_lista_negra = motivo_lista_negra;
    }

    /**
     * Un conductor vacio para los objetos de tipo ModeloConductor
     *
     * @return a {@link java.lang.String} object
     */
    public String getNacionalidad() {
        return nacionalidad;
    }

    /**
     * <p>Setter for the field <code>nacionalidad</code>.</p>
     *
     * @param nacionalidad a {@link java.lang.String} object
     */
    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
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
     * <p>Getter for the field <code>dni</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getDni() {
        return dni;
    }

    /**
     * <p>Setter for the field <code>dni</code>.</p>
     *
     * @param dni a {@link java.lang.String} object
     */
    public void setDni(String dni) {
        this.dni = dni;
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
     * <p>Getter for the field <code>apellido</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * <p>Setter for the field <code>apellido</code>.</p>
     *
     * @param apellido a {@link java.lang.String} object
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * <p>Getter for the field <code>contacto_recogida</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getContacto_recogida() {
        return contacto_recogida;
    }

    /**
     * <p>Setter for the field <code>contacto_recogida</code>.</p>
     *
     * @param contacto_recogida a {@link java.lang.String} object
     */
    public void setContacto_recogida(String contacto_recogida) {
        this.contacto_recogida = contacto_recogida;
    }

    /**
     * <p>Getter for the field <code>correo_electronico</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getCorreo_electronico() {
        return correo_electronico;
    }

    /**
     * <p>Setter for the field <code>correo_electronico</code>.</p>
     *
     * @param correo_electronico a {@link java.lang.String} object
     */
    public void setCorreo_electronico(String correo_electronico) {
        this.correo_electronico = correo_electronico;
    }

    /**
     * <p>Getter for the field <code>foto_humano</code>.</p>
     *
     * @return a Blob object
     */
    public Blob getFoto_humano() {
        return foto_humano;
    }

    /**
     * <p>Setter for the field <code>foto_humano</code>.</p>
     *
     * @param foto_humano a Blob object
     */
    public void setFoto_humano(Blob foto_humano) {
        this.foto_humano = foto_humano;
    }

    /**
     * <p>Getter for the field <code>es_internacional</code>.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getEs_internacional() {
        return es_internacional;
    }

    /**
     * <p>Setter for the field <code>es_internacional</code>.</p>
     *
     * @param es_internacional a {@link java.lang.Boolean} object
     */
    public void setEs_internacional(Boolean es_internacional) {
        this.es_internacional = es_internacional;
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
     * <p>Getter for the field <code>codigo_postal</code>.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getCodigo_postal() {
        return codigo_postal;
    }

    /**
     * <p>Setter for the field <code>codigo_postal</code>.</p>
     *
     * @param codigo_postal a {@link java.lang.Integer} object
     */
    public void setCodigo_postal(Integer codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    /**
     * <p>setPais_conductor.</p>
     *
     * @param pais_conductor a {@link java.lang.String} object
     */
    public void setPais_conductor(String pais_conductor) {
        this.pais = pais_conductor;
    }

    /**
     * <p>Getter for the field <code>tipo_de_carnet</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getTipo_de_carnet() {
        return tipo_de_carnet;
    }

    /**
     * <p>Setter for the field <code>tipo_de_carnet</code>.</p>
     *
     * @param tipo_de_carnet a {@link java.lang.String} object
     */
    public void setTipo_de_carnet(String tipo_de_carnet) {
        this.tipo_de_carnet = tipo_de_carnet;
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
     * {@inheritDoc}
     * <p>
     * Para a cadena para poder hacer tareas de depuración
     */
    @Override
    public String toString() {
        return "ModeloConductor{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", contacto_recogida='" + contacto_recogida + '\'' +
                ", correo_electronico='" + correo_electronico + '\'' +
                ", es_internacional=" + es_internacional +
                ", codigo_postal=" + codigo_postal +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                ", pais='" + pais + '\'' +
                ", tipo_de_carnet='" + tipo_de_carnet + '\'' +
                ", telefono='" + telefono + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", lista negra='" + motivo_lista_negra +
                '}';
    }
}
