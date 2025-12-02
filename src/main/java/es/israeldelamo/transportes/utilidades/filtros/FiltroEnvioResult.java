package es.israeldelamo.transportes.utilidades.filtros;

/**
 * Resultado inmutable del proceso de construcción del filtro para crear envíos.
 * <p>
 * Esta clase actúa como un DTO (objeto de transferencia de datos) que agrupa:
 * </p>
 * <ul>
 *   <li>La sentencia SQL generada para consultar los vehículos/servicios que cumplen el filtro.</li>
 *   <li>Un resumen legible por humanos, dividido por secciones, pensado para mostrarse
 *       en el cuadro de confirmación antes de crear los envíos.</li>
 * </ul>
 * <p>
 * Todas sus propiedades son {@code final}, por lo que la instancia es segura para compartir entre hilos
 * y no cambia después de su construcción.
 * </p>
 */
public class FiltroEnvioResult {
    /** SQL final resultante del filtro. */
    private final String sql;

    // Secciones para el resumen mostrado al usuario
    /** Texto descriptivo de los orígenes seleccionados. */
    private final String origenes;
    /** Texto descriptivo de los destinos seleccionados. */
    private final String destinos;
    /** Texto para la disponibilidad desde (fecha/hora inicial). */
    private final String disponibilidadDesde;
    /** Texto para la disponibilidad hasta (fecha/hora final). */
    private final String disponibilidadHasta;
    /** Indica si el envío contempla ida y vuelta. */
    private final String idaYVuelta;
    /** Indica si se requiere vehículo con rampa. */
    private final String conRampa;
    /** Indica si se requiere doble conductor. */
    private final String dobleConductor;
    /** Indica si el envío es ADR (mercancías peligrosas). */
    private final String esAdr;
    /** Indica si el envío es ATP (transporte de perecederos). */
    private final String esAtp;
    /** Indica si el servicio es de carga completa. */
    private final String esCompleta;
    /** Indica si se ha seleccionado alguna naturaleza de la carga. */
    private final String tieneNaturaleza;

    /**
     * Crea un nuevo resultado de filtro con la sentencia SQL generada y las secciones
     * legibles por humanos para mostrar en la confirmación.
     *
     * @param sql                sentencia SQL final construida a partir de los criterios del filtro
     * @param origenes           resumen de los orígenes seleccionados
     * @param destinos           resumen de los destinos seleccionados
     * @param disponibilidadDesde texto que representa la disponibilidad desde (fecha/hora de inicio)
     * @param disponibilidadHasta texto que representa la disponibilidad hasta (fecha/hora de fin)
     * @param idaYVuelta         indicador/leyenda sobre si el envío es de ida y vuelta
     * @param conRampa           indicador/leyenda sobre la necesidad de rampa
     * @param dobleConductor     indicador/leyenda sobre la necesidad de doble conductor
     * @param esAdr              indicador/leyenda sobre si aplica normativa ADR
     * @param esAtp              indicador/leyenda sobre si aplica normativa ATP
     * @param esCompleta         indicador/leyenda sobre si se trata de carga completa
     * @param tieneNaturaleza    indicador/leyenda de si se ha seleccionado naturaleza de la carga
     */
    public FiltroEnvioResult(String sql,
                             String origenes,
                             String destinos,
                             String disponibilidadDesde,
                             String disponibilidadHasta,
                             String idaYVuelta,
                             String conRampa,
                             String dobleConductor,
                             String esAdr,
                             String esAtp,
                             String esCompleta,
                             String tieneNaturaleza) {
        this.sql = sql;
        this.origenes = origenes;
        this.destinos = destinos;
        this.disponibilidadDesde = disponibilidadDesde;
        this.disponibilidadHasta = disponibilidadHasta;
        this.idaYVuelta = idaYVuelta;
        this.conRampa = conRampa;
        this.dobleConductor = dobleConductor;
        this.esAdr = esAdr;
        this.esAtp = esAtp;
        this.esCompleta = esCompleta;
        this.tieneNaturaleza = tieneNaturaleza;
    }

    /**
     * Devuelve la sentencia SQL generada por el proceso de filtrado.
     *
     * @return SQL final a ejecutar en base de datos
     */
    public String getSql() {
        return sql;
    }

    /**
     * Devuelve el resumen de los orígenes seleccionados.
     *
     * @return texto con los orígenes
     */
    public String getOrigenes() {
        return origenes;
    }

    /**
     * Devuelve el resumen de los destinos seleccionados.
     *
     * @return texto con los destinos
     */
    public String getDestinos() {
        return destinos;
    }

    /**
     * Devuelve el texto de disponibilidad desde (fecha/hora inicial).
     *
     * @return cadena con la disponibilidad desde
     */
    public String getDisponibilidadDesde() {
        return disponibilidadDesde;
    }

    /**
     * Devuelve el texto de disponibilidad hasta (fecha/hora final).
     *
     * @return cadena con la disponibilidad hasta
     */
    public String getDisponibilidadHasta() {
        return disponibilidadHasta;
    }

    /**
     * Indica si el envío contempla ida y vuelta (en formato de texto para UI).
     *
     * @return texto que representa la opción de ida y vuelta
     */
    public String getIdaYVuelta() {
        return idaYVuelta;
    }

    /**
     * Indica si se requiere rampa (en formato de texto para UI).
     *
     * @return texto que representa la opción de rampa
     */
    public String getConRampa() {
        return conRampa;
    }

    /**
     * Indica si se requiere doble conductor (en formato de texto para UI).
     *
     * @return texto que representa la opción de doble conductor
     */
    public String getDobleConductor() {
        return dobleConductor;
    }

    /**
     * Indica si aplica normativa ADR (en formato de texto para UI).
     *
     * @return texto que representa la opción ADR
     */
    public String getEsAdr() {
        return esAdr;
    }

    /**
     * Indica si aplica normativa ATP (en formato de texto para UI).
     *
     * @return texto que representa la opción ATP
     */
    public String getEsAtp() {
        return esAtp;
    }

    /**
     * Indica si se trata de carga completa (en formato de texto para UI).
     *
     * @return texto que representa la opción de carga completa
     */
    public String getEsCompleta() {
        return esCompleta;
    }

    /**
     * Indica si se ha seleccionado naturaleza de la carga (en formato de texto para UI).
     *
     * @return texto que representa la selección de naturaleza
     */
    public String getTieneNaturaleza() {
        return tieneNaturaleza;
    }
}
