package es.israeldelamo.transportes.modelos;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Modelo que refleja la tabla llamada Carnet
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloDeCarnet {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloDeCarnet.class);


    private String TipoDeCarnet;

    /**
     * Constructor que inicializa el objeto ModeloDeCarnet con un tipo de carnet.
     *
     * @param tipodDeCarnet el tipo de carnet a asignar
     */
    public ModeloDeCarnet(String tipodDeCarnet) {
        this.TipoDeCarnet = tipodDeCarnet;
    }

    /**
     * Obtiene el tipo de carnet.
     *
     * @return el tipo de carnet
     */
    public String getTipoDeCarnet() {
        return TipoDeCarnet;
    }

    /**
     * Establece el tipo de carnet.
     *
     * @param tipoDeCarnet el tipo de carnet a establecer
     */
    public void setTipoDeCarnet(String tipoDeCarnet) {
        TipoDeCarnet = tipoDeCarnet;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve una representación en cadena del objeto ModeloDeCarnet.
     */
    @Override
    public String toString() {
        return "Tipo de carnet = " + getTipoDeCarnet();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Devuelve el valor hash del tipo de carnet.
     */
    @Override
    public int hashCode() {
        return Objects.hash(TipoDeCarnet);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Compara si dos objetos ModeloDeCarnet son iguales.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ModeloDeCarnet other = (ModeloDeCarnet) obj;
        return Objects.equals(TipoDeCarnet, other.TipoDeCarnet);
    }


}
