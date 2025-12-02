package es.israeldelamo.transportes.utilidades.filtros;

import es.israeldelamo.transportes.modelos.ModeloNaturalezasPeligrosas;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal;

import java.time.LocalDate;
import java.util.List;

/**
 * Construye la sentencia SQL y un resumen legible a partir de los filtros
 * seleccionados en la interfaz de usuario, sin depender de clases de JavaFX.
 *
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Transformar los valores seleccionados (orígenes, destinos, fechas, flags, etc.)
 *       en una cláusula SQL (fragmento de <code>WHERE</code>) lista para ser añadida a una
 *       consulta.</li>
 *   <li>Generar un resumen textual (multilínea) de los filtros aplicados para
 *       mostrarlo en la UI o incluirlo en informes.</li>
 * </ul>
 *
 * <p>Notas de uso:</p>
 * <ul>
 *   <li>Los parámetros que admiten estado tri‑valor usan {@link TriState} para distinguir entre
 *       TRUE, FALSE e INDETERMINATE (no filtrar por ese criterio).</li>
 *   <li>Las listas de orígenes/destinos pueden ser <code>null</code>; en ese caso no se añade
 *       resumen para dichos apartados.</li>
 *   <li>Las marcas ADR y ATP son excluyentes en la UI, pero el builder acepta cualquier
 *       combinación booleana y genera la condición correspondiente.</li>
 *   <li>El SQL generado es un fragmento de condiciones acumuladas que parte de
 *       <code>TRUE = TRUE</code> para simplificar la concatenación con <code>AND</code>.</li>
 * </ul>
 *
 * <p>Hilos/estado:</p>
 * <ul>
 *   <li>La clase es <b>sin estado</b> (stateless) y el método es <b>thread‑safe</b> siempre
 *       que los parámetros suministrados lo sean.</li>
 * </ul>
 */
public class FiltroEnvioBuilder {

    /**
     * Construye el resultado del filtrado a partir de los criterios seleccionados.
     *
     * <p>Genera dos salidas coherentes entre sí:</p>
     * <ul>
     *   <li>Un fragmento SQL (cláusula WHERE acumulativa) en <code>getSql()</code>.</li>
     *   <li>Un resumen textual legible de cada filtro en sus respectivos campos.</li>
     * </ul>
     *
     * @param origenes Lista de lugares de origen seleccionados (país, provincia, localidad, CP). Puede ser {@code null}.
     * @param destinos Lista de lugares de destino seleccionados. Puede ser {@code null}.
     * @param diaFin Día de disponibilidad máxima (fin de intervalo). Puede ser {@code null} si no se aplica.
     * @param horaFin Hora de disponibilidad máxima (HH:mm). Ignorado si {@code diaFin} es {@code null} o está en blanco.
     * @param diaInicio Día de disponibilidad mínima (inicio de intervalo). Puede ser {@code null} si no se aplica.
     * @param horaInicio Hora de disponibilidad mínima (HH:mm). Ignorado si {@code diaInicio} es {@code null} o está en blanco.
     * @param idaYVueltaChecked Si se requiere ida y vuelta ({@code true}) o solo ida ({@code false}).
     * @param vehiculoConRampa Requisito de rampa del vehículo. {@link TriState#TRUE} = debe tener; {@link TriState#FALSE} = no debe; {@link TriState#INDETERMINATE} = indiferente.
     * @param dobleConductor Requisito de doble conductor. Mismo esquema tri‑estado que el anterior.
     * @param esAdrChecked Si la carga es ADR.
     * @param esAtpChecked Si la carga es ATP.
     * @param cargaCompleta Indica si la carga es completa o grupaje, en modo tri‑estado.
     * @param naturalezaSeleccionada Naturaleza peligrosa seleccionada; si es {@code null} no se filtra por este campo.
     * @return {@link FiltroEnvioResult} con el SQL generado y los resúmenes de cada sección para su presentación.
     */
    public static FiltroEnvioResult build(List<ModeloPaisYProvinciaYLocalidadYCodigoPostal> origenes,
                                          List<ModeloPaisYProvinciaYLocalidadYCodigoPostal> destinos,
                                          LocalDate diaFin,
                                          String horaFin,
                                          LocalDate diaInicio,
                                          String horaInicio,
                                          boolean idaYVueltaChecked,
                                          TriState vehiculoConRampa,
                                          TriState dobleConductor,
                                          boolean esAdrChecked,
                                          boolean esAtpChecked,
                                          TriState cargaCompleta,
                                          ModeloNaturalezasPeligrosas naturalezaSeleccionada) {

        StringBuilder sbSQL = new StringBuilder("TRUE = TRUE ");
        StringBuilder sOrigenes = new StringBuilder();
        StringBuilder sDestinos = new StringBuilder();
        StringBuilder sDispDesde = new StringBuilder();
        StringBuilder sDispHasta = new StringBuilder();
        StringBuilder sIdaVuelta = new StringBuilder();
        StringBuilder sRampa = new StringBuilder();
        StringBuilder sDoble = new StringBuilder();
        StringBuilder sAdr = new StringBuilder();
        StringBuilder sAtp = new StringBuilder();
        StringBuilder sCompleta = new StringBuilder();
        StringBuilder sNaturaleza = new StringBuilder();

        // Orígenes
        if (origenes != null) {
            for (ModeloPaisYProvinciaYLocalidadYCodigoPostal mp : origenes) {
                sOrigenes.append(mp.getPais()).append(" ")
                        .append(mp.getProvincia()).append(" ")
                        .append(mp.getLocalidad()).append(" ")
                        .append(mp.getCodigoPostal()).append("\n");
            }
        }

        // Destinos
        if (destinos != null) {
            for (ModeloPaisYProvinciaYLocalidadYCodigoPostal mp : destinos) {
                sDestinos.append(mp.getPais()).append(" ")
                        .append(mp.getProvincia()).append(" ")
                        .append(mp.getLocalidad()).append(" ")
                        .append(mp.getCodigoPostal()).append("\n");
            }
        }

        // Disponibilidades
        if (horaFin != null && !horaFin.isBlank() && diaFin != null) {
            sDispDesde.append(diaFin).append(" ").append(horaFin).append(":00\n");
        }
        if (horaInicio != null && !horaInicio.isBlank() && diaInicio != null) {
            sDispHasta.append(diaInicio).append(" ").append(horaInicio).append(":00\n");
        }

        // Ida y vuelta
        if (idaYVueltaChecked) {
            sbSQL.append(" AND con_vuelta = TRUE ");
            sIdaVuelta.append("Ida y vuelta\n");
        } else {
            sbSQL.append(" AND con_vuelta = 0 ");
            sIdaVuelta.append("Solo ida\n");
        }

        // Rampa
        switch (vehiculoConRampa) {
            case TRUE:
                sbSQL.append(" AND necesita_rampa = TRUE ");
                sRampa.append("El vehículo SÍ tiene rampa\n");
                break;
            case FALSE:
                sbSQL.append(" AND necesita_rampa = 0 ");
                sRampa.append("El vehículo NO tiene rampa\n");
                break;
            case INDETERMINATE:
                sRampa.append("El vehículo Puede o No tener rampa\n");
                break;
        }

        // Doble conductor
        switch (dobleConductor) {
            case TRUE:
                sbSQL.append(" AND doble_conductor = TRUE ");
                sDoble.append("Usaremos DOBLE conductor\n");
                break;
            case FALSE:
                sbSQL.append(" AND doble_conductor = 0 ");
                sDoble.append("Usaremos SIMPLE conductor\n");
                break;
            case INDETERMINATE:
                sDoble.append("No importa el número de conductores\n");
                break;
        }

        // ADR / ATP (excluyentes en la UI)
        if (esAdrChecked) {
            sbSQL.append(" AND es_adr = TRUE ");
            sAdr.append("Será una carga ADR\n");
        } else {
            sbSQL.append(" AND es_adr = 0 ");
        }
        if (esAtpChecked) {
            sbSQL.append(" AND es_atp = TRUE ");
            sAtp.append("Será una carga ATP\n");
        } else {
            sbSQL.append(" AND es_atp = 0 ");
        }

        // Carga completa
        switch (cargaCompleta) {
            case TRUE:
                sbSQL.append(" AND carga_completa_o_grupaje = TRUE ");
                sCompleta.append("Será una carga COMPLETA\n");
                break;
            case FALSE:
                sbSQL.append(" AND carga_completa_o_grupaje = 0 ");
                sCompleta.append("No será una carga completa\n");
                break;
            case INDETERMINATE:
                sCompleta.append("No importa si la carga es completa o no\n");
                break;
        }

        // Naturaleza peligrosa
        if (naturalezaSeleccionada != null) {
            sbSQL.append(" AND cod_naturaleza_peligrosa = ")
                    .append(naturalezaSeleccionada.getCodigo_naturaleza_peligrosa());
            sNaturaleza.append("CUYA NATURALEZA ES: ")
                    .append(naturalezaSeleccionada.getDescripcion_naturaleza_peligrosa()).append("\n");
        }

        return new FiltroEnvioResult(
                sbSQL.toString(),
                sOrigenes.toString(),
                sDestinos.toString(),
                sDispDesde.toString(),
                sDispHasta.toString(),
                sIdaVuelta.toString(),
                sRampa.toString(),
                sDoble.toString(),
                sAdr.toString(),
                sAtp.toString(),
                sCompleta.toString(),
                sNaturaleza.toString()
        );
    }
}
