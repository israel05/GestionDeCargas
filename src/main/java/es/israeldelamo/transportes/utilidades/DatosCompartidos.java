package es.israeldelamo.transportes.utilidades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>DatosCompartidos class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class DatosCompartidos {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DatosCompartidos.class);


    private final String informacion;

    /**
     * <p>Constructor for DatosCompartidos.</p>
     *
     * @param informacion a {@link java.lang.String} object
     */
    public DatosCompartidos(String informacion) {
        this.informacion = informacion;
    }

    /**
     * <p>Getter for the field <code>informacion</code>.</p>
     *
     * @return a {@link java.lang.String} object
     */
    public String getInformacion() {
        return informacion;
    }
}
