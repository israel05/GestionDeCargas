package es.israeldelamo.transportes.bbdd;

import es.israeldelamo.transportes.utilidades.Alertas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Clase para leer las propiedades del sistema, urls, idioma instalado, directorios...
 *
 * @author israel
 * @version $Id: $Id
 */
public class Propiedades {
    // Usamos SLF4J para el logger
    private static final Logger logger = LoggerFactory.getLogger(Propiedades.class);
    private static final Properties props = new Properties();

    static {
        Path ruta = Paths.get("configuration.properties");
        try (InputStream input = Files.newInputStream(ruta)) {
            props.load(input);
        } catch (Exception e) {
            logger.error("No se pudo cargar configuration.properties externo", e);
            Alertas alerta = new Alertas();
            alerta.mostrarError("No encuentro el archivo de propiedades 'configuration.properties' junto al JAR.");
            alerta.mostrarError("Detalle: " + e.getMessage());
        }
    }


    /**
     * Obtiene el valor asociado a una clave desde el archivo de propiedades situado en la raíz del proyecto
     *
     * @param clave La clave cuyo valor se desea obtener.
     * @return El valor asociado a la clave.
     * @throws java.lang.RuntimeException Si el archivo de configuración no existe o la clave no tiene un valor asociado.
     */
    public static String getValor(String clave) {
        String valor = props.getProperty(clave);
        if (valor != null) {
            return valor;
        }
        throw new RuntimeException("No he logrado leer esa clave en concreto");
    }
}
