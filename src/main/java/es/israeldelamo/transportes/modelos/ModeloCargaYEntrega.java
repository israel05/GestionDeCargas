package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

/**
 * Modelo de Carga y Entrega
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloCargaYEntrega {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloCargaYEntrega.class);

    /**
     * Código de carga.
     */
    private int cod_carga;

    /**
     * Franja horaria de recogida.
     */
    private Timestamp franja_horario_recogida;

    /**
     * Franja horaria de entrega.
     */
    private Timestamp franja_horario_entrega;


    /**
     * Fecha de creacción de la entrada en la base de datos.
     */
    private Timestamp fecha_creaccion;
    /**
     * Dimensión de longitud.
     */
    private Float dimension_l;
    /**
     * Dimensión de ancho.
     */
    private Float dimension_a;
    /**
     * Dimensión de altura.
     */
    private Float dimension_h;
    /**
     * Peso unitario.
     */
    private Float peso_unitario;
    /**
     * Indica si la carga requiere manipulación.
     */
    private Boolean manipulacion_carga_bool;
    /**
     * Indica si la carga es remontable.
     */
    private Boolean remontable;
    /**
     * Indica si se requiere doble conductor.
     */
    private Boolean doble_conductor;
    /**
     * Indica si la carga es completa o grupaje.
     */
    private Boolean carga_completa_o_grupaje;
    /**
     * Indica si se necesita rampa.
     */
    private Boolean necesita_rampa;
    /**
     * Código de naturaleza peligrosa.
     */
    private Float cod_naturaleza_peligrosa;
    /**
     * Localidad de origen.
     */
    private String localidad_origen;
    /**
     * Provincia de origen.
     */
    private String provincia_origen;
    /**
     * País de origen.
     */
    private String pais_origen;
    /**
     * Código postal de origen.
     */
    private Integer cp_origen;
    /**
     * Localidad de destino.
     */
    private String localidad_destino;
    /**
     * Provincia de destino.
     */
    private String provincia_destino;
    /**
     * País de destino.
     */
    private String pais_destino;
    /**
     * Código postal de destino.
     */
    private Integer cp_destino;
    /**
     * Instrucciones.
     */
    private String instrucciones;
    /**
     * Cantidad de reembolso.
     */
    private String reembolso_cantidad;
    /**
     * Indica si hay vuelta.
     */
    private Boolean con_vuelta;
    /**
     * Precio.
     */
    private String precio;
    /**
     * Seguro.
     */
    private String seguro;
    /**
     * Indica si es ADR.
     */
    private Boolean es_adr;
    /**
     * Indica si es ATP.
     */
    private Boolean es_atp;
    /**
     * Tipo de bulto.
     */
    private Integer tipo_bulto;
    /**
     * Indica si ya está reservado.
     */
    private Boolean ya_reservado;
    /**
     * Cantidad de bultos.
     */
    private Integer cantidad_bultos;
    /**
     * La empresa creadora
     */
    private String empresa;


    /**
     * Constructor que inicializa el modelo con todos los atributos.
     * *
     *
     * @param cod_carga                Código de la carga.
     * @param franja_horario_recogida  Franja horaria de recogida.
     * @param franja_horario_entrega   Franja horaria de entrega.
     * @param dimension_l              Longitud de la carga.
     * @param dimension_a              Ancho de la carga.
     * @param dimension_h              Altura de la carga.
     * @param peso_unitario            Peso unitario de la carga.
     * @param manipulacion_carga_bool  Indica si la carga requiere manipulación especial.
     * @param remontable               Indica si la carga es remontable.
     * @param tipo_bulto               Tipo de bulto de la carga.
     * @param doble_conductor          Indica si se requiere doble conductor.
     * @param carga_completa_o_grupaje Indica si la carga es completa o grupaje.
     * @param necesita_rampa           Indica si la carga necesita rampa.
     * @param cod_naturaleza_peligrosa Código de naturaleza peligrosa de la carga.
     * @param localidad_origen         Localidad de origen de la carga.
     * @param provincia_origen         Provincia de origen de la carga.
     * @param pais_origen              País de origen de la carga.
     * @param cp_origen                Código postal de origen de la carga.
     * @param localidad_destino        Localidad de destino de la carga.
     * @param provincia_destino        Provincia de destino de la carga.
     * @param pais_destino             País de destino de la carga.
     * @param cp_destino               Código postal de destino de la carga.
     * @param instrucciones            Instrucciones especiales para la carga.
     * @param reembolso_cantidad       Cantidad de reembolso si aplica.
     * @param con_vuelta               Indica si la carga requiere vuelta.
     * @param precio                   Precio de la carga.
     * @param seguro                   Seguro de la carga.
     * @param es_adr                   Indica si la carga es ADR.
     * @param es_atp                   Indica si la carga es ATP.
     * @param ya_reservado             Indica si la carga ya ha sido reservada.
     * @param cantidad_bultos          Indica la cantidad de bultos de la carga
     * @param fecha_creaccion          la fecha en la que se creo esta entrada
     * @param empresa                  la empresa que ha creado esta carga
     */
    public ModeloCargaYEntrega(int cod_carga,
                               Timestamp franja_horario_recogida,
                               Timestamp franja_horario_entrega,
                               Float dimension_l, Float dimension_a, Float dimension_h, Float peso_unitario,
                               Boolean manipulacion_carga_bool, Boolean remontable,
                               Integer tipo_bulto,
                               Boolean doble_conductor, Boolean carga_completa_o_grupaje,
                               Boolean necesita_rampa,
                               Float cod_naturaleza_peligrosa,
                               String localidad_origen, String provincia_origen,
                               String pais_origen,
                               Integer cp_origen,
                               String localidad_destino,
                               String provincia_destino, String pais_destino,
                               Integer cp_destino,
                               String instrucciones, String reembolso_cantidad,
                               Boolean con_vuelta,
                               String precio, String seguro,
                               Boolean es_adr, Boolean es_atp,
                               Boolean ya_reservado, Integer cantidad_bultos,
                               Timestamp fecha_creaccion, String empresa) {

        setCod_carga(cod_carga);
        this.franja_horario_recogida = franja_horario_recogida;
        this.franja_horario_entrega = franja_horario_entrega;
        this.dimension_l = dimension_l;
        this.dimension_a = dimension_a;
        this.dimension_h = dimension_h;
        this.peso_unitario = peso_unitario;
        this.manipulacion_carga_bool = manipulacion_carga_bool;
        this.remontable = remontable;

        this.tipo_bulto = tipo_bulto;
        this.doble_conductor = doble_conductor;

        this.carga_completa_o_grupaje = carga_completa_o_grupaje;
        this.necesita_rampa = necesita_rampa;
        this.cod_naturaleza_peligrosa = cod_naturaleza_peligrosa;
        this.localidad_origen = localidad_origen;
        this.provincia_origen = provincia_origen;
        this.pais_origen = pais_origen;
        this.cp_origen = cp_origen;
        this.localidad_destino = localidad_destino;
        this.provincia_destino = provincia_destino;
        this.pais_destino = pais_destino;
        this.cp_destino = cp_destino;
        this.instrucciones = instrucciones;
        this.reembolso_cantidad = reembolso_cantidad;
        this.con_vuelta = con_vuelta;
        this.precio = precio;
        this.seguro = seguro;
        this.es_adr = es_adr;
        this.es_atp = es_atp;
        this.ya_reservado = ya_reservado;
        this.cantidad_bultos = cantidad_bultos;
        this.fecha_creaccion = fecha_creaccion;
        this.empresa = empresa;
    }

    /**
     * Constructo solo con código
     *
     * @param cod_carga a {@link java.lang.Integer} object
     */
    public ModeloCargaYEntrega(int cod_carga) {
        this.cod_carga = cod_carga;
    }

    /**
     * Devuelve la hora de creacción de este bulto
     *
     * @return la hora
     */

    public Timestamp getFecha_creaccion() {
        return fecha_creaccion;
    }

    /**
     * Establece la fecha de creacción de esa carga
     */
    public void setFecha_creaccion(Timestamp fecha_creaccion) {
        this.fecha_creaccion = fecha_creaccion;
    }

    /**
     * Devuelve el nombre de la empresa que creo la carga
     *
     * @return empresa
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * Establece los datos de la empresa
     *
     * @param empresa
     */

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    /**
     * <p>Getter for the field {@code cantidad_bultos}.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getCantidad_bultos() {
        return cantidad_bultos;
    }

    /**
     * <p>Setter for the field {@code cantidad_bultos}.</p>
     *
     * @param cantidad_bultos a {@link java.lang.Integer} object
     */
    public void setCantidad_bultos(Integer cantidad_bultos) {
        this.cantidad_bultos = cantidad_bultos;
    }

    /**
     * <p>Getter for the field {@code ya_reservado}.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getYa_reservado() {
        return ya_reservado;
    }

    /**
     * <p>Setter for the field {@code ya_reservado}.</p>
     *
     * @param ya_reservado a {@link java.lang.Boolean} object
     */
    public void setYa_reservado(Boolean ya_reservado) {
        this.ya_reservado = ya_reservado;
    }

    /**
     * <p>Getter for the field {@code localidad_origen}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getLocalidad_origen() {
        return localidad_origen;
    }

    /**
     * <p>Setter for the field {@code localidad_origen}.</p>
     *
     * @param localidad_origen a {@link java.lang.String} object
     */
    public void setLocalidad_origen(String localidad_origen) {
        this.localidad_origen = localidad_origen;
    }

    /**
     * <p>Getter for the field {@code provincia_origen}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getProvincia_origen() {
        return provincia_origen;
    }

    /**
     * <p>Setter for the field {@code provincia_origen}.</p>
     *
     * @param provincia_origen a {@link java.lang.String} object
     */
    public void setProvincia_origen(String provincia_origen) {
        this.provincia_origen = provincia_origen;
    }

    /**
     * <p>Getter for the field {@code pais_origen}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getPais_origen() {
        return pais_origen;
    }

    /**
     * <p>Setter for the field {@code pais_origen}.</p>
     *
     * @param pais_origen a {@link java.lang.String} object
     */
    public void setPais_origen(String pais_origen) {
        this.pais_origen = pais_origen;
    }

    /**
     * <p>Getter for the field {@code cp_origen}.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getCp_origen() {
        return cp_origen;
    }

    /**
     * <p>Setter for the field {@code cp_origen}.</p>
     *
     * @param cp_origen a {@link java.lang.Integer} object
     */
    public void setCp_origen(Integer cp_origen) {
        this.cp_origen = cp_origen;
    }

    /**
     * <p>Getter for the field {@code localidad_destino}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getLocalidad_destino() {
        return localidad_destino;
    }

    /**
     * <p>Setter for the field {@code localidad_destino}.</p>
     *
     * @param localidad_destino a {@link java.lang.String} object
     */
    public void setLocalidad_destino(String localidad_destino) {
        this.localidad_destino = localidad_destino;
    }

    /**
     * <p>Getter for the field {@code provincia_destino}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getProvincia_destino() {
        return provincia_destino;
    }

    /**
     * <p>Setter for the field {@code provincia_destino}.</p>
     *
     * @param provincia_destino a {@link java.lang.String} object
     */
    public void setProvincia_destino(String provincia_destino) {
        this.provincia_destino = provincia_destino;
    }

    /**
     * <p>Getter for the field {@code pais_destino}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getPais_destino() {
        return pais_destino;
    }

    /**
     * <p>Setter for the field {@code pais_destino}.</p>
     *
     * @param pais_destino a {@link java.lang.String} object
     */
    public void setPais_destino(String pais_destino) {
        this.pais_destino = pais_destino;
    }

    /**
     * <p>Getter for the field {@code cp_destino}.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getCp_destino() {
        return cp_destino;
    }

    /**
     * <p>Setter for the field {@code cp_destino}.</p>
     *
     * @param cp_destino a {@link java.lang.Integer} object
     */
    public void setCp_destino(Integer cp_destino) {
        this.cp_destino = cp_destino;
    }

    /**
     * <p>Getter for the field {@code instrucciones}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getInstrucciones() {
        return instrucciones;
    }

    /**
     * <p>Setter for the field {@code instrucciones}.</p>
     *
     * @param instrucciones a {@link java.lang.String} object
     */
    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    /**
     * <p>Getter for the field {@code reembolso_cantidad}.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getReembolso_cantidad() {
        return reembolso_cantidad;
    }

    /**
     * <p>Setter for the field {@code reembolso_cantidad}.</p>
     *
     * @param reembolso_cantidad a {@link java.lang.String} object
     */
    public void setReembolso_cantidad(String reembolso_cantidad) {
        this.reembolso_cantidad = reembolso_cantidad;
    }

    /**
     * Obtiene si la carga requiere o no vuelta.
     *
     * @return Verdadero si la carga requiere vuelta, falso en caso contrario.
     */
    public Boolean getCon_vuelta() {
        return con_vuelta;
    }

    /**
     * Establece si la carga requiere o no vuelta.
     *
     * @param con_vuelta Verdadero si la carga requiere vuelta, falso en caso contrario.
     */
    public void setCon_vuelta(Boolean con_vuelta) {
        this.con_vuelta = con_vuelta;
    }

    /**
     * Obtiene el precio de la carga.
     *
     * @return El precio de la carga.
     */
    public String getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la carga.
     *
     * @param precio El precio de la carga.
     */
    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el seguro de la carga.
     *
     * @return El seguro de la carga.
     */
    public String getSeguro() {
        return seguro;
    }

    /**
     * Establece el seguro de la carga.
     *
     * @param seguro El seguro de la carga.
     */
    public void setSeguro(String seguro) {
        this.seguro = seguro;
    }

    /**
     * Obtiene si la carga es ADR (Acuerdo sobre el transporte internacional de mercancías peligrosas por carretera).
     *
     * @return Verdadero si la carga es ADR, falso en caso contrario.
     */
    public Boolean getEs_adr() {
        return es_adr;
    }

    /**
     * Establece si la carga es ADR (Acuerdo sobre el transporte internacional de mercancías peligrosas por carretera).
     *
     * @param es_adr Verdadero si la carga es ADR, falso en caso contrario.
     */
    public void setEs_adr(Boolean es_adr) {
        this.es_adr = es_adr;
    }

    /**
     * Obtiene si la carga es ATP (Acuerdo sobre Transporte Internacional de Mercancías Perecederas).
     *
     * @return Verdadero si la carga es ATP, falso en caso contrario.
     */
    public Boolean getEs_atp() {
        return es_atp;
    }

    /**
     * Establece si la carga es ATP (Acuerdo sobre Transporte Internacional de Mercancías Perecederas).
     *
     * @param es_atp Verdadero si la carga es ATP, falso en caso contrario.
     */
    public void setEs_atp(Boolean es_atp) {
        this.es_atp = es_atp;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve como string todas las partes del modelo
     */
    @Override
    public String toString() {
        return "ModeloCargaYEntrega{" +
                "cod_carga='" + getCod_carga() + '\'' +
                ", franja_horario_recogida=" + franja_horario_recogida +
                ", franja_horario_entrega=" + franja_horario_entrega +
                ", dimension_l=" + getDimension_l() +
                ", dimension_a=" + getDimension_a() +
                ", dimension_h=" + getDimension_h() +
                ", peso_unitario=" + getPeso_unitario() +
                ", manipulacion_carga_bool=" + getManipulacion_carga_bool() +
                ", remontable=" + getRemontable() +
                ", tipo_bulto=" + getTipo_bulto() +
                ", doble_conductor=" + getDoble_conductor() +
                ", carga_completa_o_grupaje=" + getCarga_completa_o_grupaje() +
                ", necesita_rampa=" + getNecesita_rampa() +
                ", cod_naturaleza_peligrosa=" + getCod_naturaleza_peligrosa() +
                ", localidad_origen='" + getLocalidad_origen() + '\'' +
                ", provincia_origen='" + getProvincia_origen() + '\'' +
                ", pais_origen='" + getPais_origen() + '\'' +
                ", cp_origen=" + getCp_origen() +
                ", localidad_destino='" + getLocalidad_destino() + '\'' +
                ", provincia_destino='" + getProvincia_destino() + '\'' +
                ", pais_destino='" + getPais_destino() + '\'' +
                ", cp_destino=" + getCp_destino() +
                ", instrucciones='" + instrucciones + '\'' +
                ", reembolso_cantidad='" + reembolso_cantidad + '\'' +
                ", con_vuelta=" + con_vuelta +
                ", precio='" + precio + '\'' +
                ", seguro='" + seguro + '\'' +
                ", es_adr=" + es_adr +
                ", es_atp=" + es_atp +
                ", ya_reservado=" + ya_reservado +
                ", cantidad_bultos=" + cantidad_bultos +
                "Fecha de creacción del bulto" + getFecha_creaccion() +
                "Empresa creadora: " + empresa +
                '}';
    }


    /**
     * Devuelve como string todas las partes del modelo
     *
     * @return a {@link java.lang.String} object
     */
    public String toStringParaAlertas() {
        return "SU CARGA ES LA SIGUIENTE \n" +
                "País de origen:            " + pais_origen + '\'' + "\n" +
                "Provincia de origen:       " + provincia_origen + '\'' + "\n" +
                "Localidad origen:          " + localidad_origen + '\'' + "\n" +
                "Código Postal de origen:   " + cp_origen + "\n" +
                "\n" +
                "Páis de destino:           " + pais_destino + '\'' + "\n" +
                "Provincia de destino:      " + provincia_destino + '\'' + "\n" +
                "Localidad de destino:      " + localidad_destino + '\'' + "\n" +
                "Código Posta de destino:   " + cp_destino + "\n" +
                "\n" +
                "Código de carga:           " + cod_carga + '\'' +
                "A recoger en:              " + franja_horario_recogida + "\n" +
                "A entregar en:             " + franja_horario_entrega + "\n" +
                "Dimensiones L, A, H:       " + dimension_l + ", " + dimension_a + ", " + dimension_h + "\n" +
                "Peso/Unidad:               " + peso_unitario + "\n" +
                "Con manipulación:          " + manipulacion_carga_bool + "\n" +
                "Carga remontable:          " + remontable + "\n" +
                "Tipo de bulto:             " + tipo_bulto + "\n" +
                "Doble conductor necesario: " + doble_conductor + "\n" +
                "Carga completa:            " + carga_completa_o_grupaje + "\n" +
                "Necesita rampa:            " + necesita_rampa + "\n" +
                "Naturaleza peligrosa:      " + cod_naturaleza_peligrosa + "\n" +
                "\n" +
                "Instrucciones adicionales: " + instrucciones + '\'' + "\n" +
                "Cantidad a reebolsar:      " + reembolso_cantidad + '\'' + "\n" +
                "Carga con vuelta:          " + con_vuelta + "\n" +
                "Precio:                    " + precio + '\'' + "\n" +
                "Seguro:                    " + seguro + '\'' + "\n" +
                "ADR:                       " + es_adr + "\n" +
                "ATP:                       " + es_atp + "\n" +
                //", ya_reservado=" + ya_reservado +
                "Cantidad de bultos" + cantidad_bultos +
                "Fecha de creacción del bulto" + getFecha_creaccion() +
                "Empresa creadora " + getEmpresa() +
                '}';
    }


    /**
     * <p>Getter for the field {@code franja_horario_recogida}.</p>
     *
     * @return a Timestamp object
     */
    public Timestamp getFranja_horario_recogida() {
        return franja_horario_recogida;
    }

    /**
     * <p>Setter for the field {@code franja_horario_recogida}.</p>
     *
     * @param franja_horario_recogida a Timestamp object
     */
    public void setFranja_horario_recogida(Timestamp franja_horario_recogida) {
        this.franja_horario_recogida = franja_horario_recogida;
    }

    /**
     * <p>Getter for the field {@code franja_horario_entrega}.</p>
     *
     * @return a Timestamp object
     */
    public Timestamp getFranja_horario_entrega() {
        return franja_horario_entrega;
    }

    /**
     * <p>Setter for the field {@code franja_horario_entrega}.</p>
     *
     * @param franja_horario_entrega a Timestamp object
     */
    public void setFranja_horario_entrega(Timestamp franja_horario_entrega) {
        this.franja_horario_entrega = franja_horario_entrega;
    }

    /**
     * <p>Getter for the field {@code dimension_l}.</p>
     *
     * @return a {@link java.lang.Float} object
     */
    public Float getDimension_l() {
        return dimension_l;
    }

    /**
     * <p>Setter for the field {@code dimension_l}.</p>
     *
     * @param dimension_l a {@link java.lang.Float} object
     */
    public void setDimension_l(Float dimension_l) {
        this.dimension_l = dimension_l;
    }

    /**
     * <p>Getter for the field {@code dimension_a}.</p>
     *
     * @return a {@link java.lang.Float} object
     */
    public Float getDimension_a() {
        return dimension_a;
    }

    /**
     * <p>Setter for the field {@code dimension_a}.</p>
     *
     * @param dimension_a a {@link java.lang.Float} object
     */
    public void setDimension_a(Float dimension_a) {
        this.dimension_a = dimension_a;
    }

    /**
     * <p>Getter for the field {@code dimension_h}.</p>
     *
     * @return a {@link java.lang.Float} object
     */
    public Float getDimension_h() {
        return dimension_h;
    }

    /**
     * <p>Setter for the field {@code dimension_h}.</p>
     *
     * @param dimension_h a {@link java.lang.Float} object
     */
    public void setDimension_h(Float dimension_h) {
        this.dimension_h = dimension_h;
    }

    /**
     * <p>Getter for the field {@code peso_unitario}.</p>
     *
     * @return a {@link java.lang.Float} object
     */
    public Float getPeso_unitario() {
        return peso_unitario;
    }

    /**
     * <p>Setter for the field {@code peso_unitario}.</p>
     *
     * @param peso_unitario a {@link java.lang.Float} object
     */
    public void setPeso_unitario(Float peso_unitario) {
        this.peso_unitario = peso_unitario;
    }

    /**
     * <p>Getter for the field {@code manipulacion_carga_bool}.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getManipulacion_carga_bool() {
        return manipulacion_carga_bool;
    }

    /**
     * <p>Setter for the field {@code manipulacion_carga_bool}.</p>
     *
     * @param manipulacion_carga_bool a {@link java.lang.Boolean} object
     */
    public void setManipulacion_carga_bool(Boolean manipulacion_carga_bool) {
        this.manipulacion_carga_bool = manipulacion_carga_bool;
    }

    /**
     * <p>Getter for the field {@code remontable}.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getRemontable() {
        return remontable;
    }

    /**
     * <p>Setter for the field {@code remontable}.</p>
     *
     * @param remontable a {@link java.lang.Boolean} object
     */
    public void setRemontable(Boolean remontable) {
        this.remontable = remontable;
    }


    /**
     * <p>Getter for the field {@code tipo_bulto}.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public Integer getTipo_bulto() {
        return tipo_bulto;
    }

    /**
     * <p>Setter for the field {@code tipo_bulto}.</p>
     *
     * @param tipo_bulto a {@link java.lang.Integer} object
     */
    public void setTipo_bulto(Integer tipo_bulto) {
        this.tipo_bulto = tipo_bulto;
    }

    /**
     * <p>Getter for the field {@code doble_conductor}.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getDoble_conductor() {
        return doble_conductor;
    }

    /**
     * <p>Setter for the field {@code doble_conductor}.</p>
     *
     * @param doble_conductor a {@link java.lang.Boolean} object
     */
    public void setDoble_conductor(Boolean doble_conductor) {
        this.doble_conductor = doble_conductor;
    }


    /**
     * <p>Getter for the field {@code carga_completa_o_grupaje}.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getCarga_completa_o_grupaje() {
        return carga_completa_o_grupaje;
    }

    /**
     * <p>Setter for the field {@code carga_completa_o_grupaje}.</p>
     *
     * @param carga_completa_o_grupaje a {@link java.lang.Boolean} object
     */
    public void setCarga_completa_o_grupaje(Boolean carga_completa_o_grupaje) {
        this.carga_completa_o_grupaje = carga_completa_o_grupaje;
    }

    /**
     * <p>Getter for the field {@code necesita_rampa}.</p>
     *
     * @return a {@link java.lang.Boolean} object
     */
    public Boolean getNecesita_rampa() {
        return necesita_rampa;
    }

    /**
     * <p>Setter for the field {@code necesita_rampa}.</p>
     *
     * @param necesita_rampa a {@link java.lang.Boolean} object
     */
    public void setNecesita_rampa(Boolean necesita_rampa) {
        this.necesita_rampa = necesita_rampa;
    }

    /**
     * <p>Getter for the field {@code cod_naturaleza_peligrosa}.</p>
     *
     * @return a {@link java.lang.Float} object
     */
    public Float getCod_naturaleza_peligrosa() {
        return cod_naturaleza_peligrosa;
    }

    /**
     * Establece el código de naturaleza peligrosa de la carga.
     *
     * @param cod_naturaleza_peligrosa El código que identifica la naturaleza peligrosa de la carga.
     */
    public void setCod_naturaleza_peligrosa(Float cod_naturaleza_peligrosa) {
        this.cod_naturaleza_peligrosa = cod_naturaleza_peligrosa;
    }

    /**
     * <p>Getter for the field {@code cod_carga}.</p>
     *
     * @return a {@link java.lang.Integer} object
     */
    public int getCod_carga() {
        return cod_carga;
    }

    /**
     * <p>Setter for the field {@code cod_carga}.</p>
     *
     * @param cod_carga a {@link java.lang.Integer} object
     */
    public void setCod_carga(int cod_carga) {
        this.cod_carga = cod_carga;
    }
}
