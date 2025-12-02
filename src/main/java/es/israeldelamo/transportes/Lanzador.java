package es.israeldelamo.transportes;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Punto de entrada en la aplicación
 *
 * @author israel
 * @version $Id: $Id
 */
public class Lanzador {
    /**
     * Logger para esta clase
     */
   private static final Logger logger = LoggerFactory.getLogger(Lanzador.class);


    /**
     * Punto de entrada de la aplicación, es necesario para mejorar la entrada sin javafx
     * Solo llama a InicioGráfico
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     */
    public static void main(String[] args) {
       logger.info("Iniciando aplicación");
        Iniciografico.main(args);
    }
}
