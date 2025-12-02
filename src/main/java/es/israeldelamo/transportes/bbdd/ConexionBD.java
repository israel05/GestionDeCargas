package es.israeldelamo.transportes.bbdd;

import es.israeldelamo.transportes.utilidades.Alertas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Clase de conexión a la bbdd
 *
 * @author israel
 * @version $Id: $Id
 */
public class ConexionBD {

    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class);

    private Connection conexion;

    /**
     * Es el constructor que se llama al crear un objeto de esta clase, lanzado la conexión
     */
    public ConexionBD() {

        try {
            String url = Propiedades.getValor("urlTransportes");
            String user = Propiedades.getValor("user");
            String password = Propiedades.getValor("password");

            Properties connConfig = new Properties();
            connConfig.setProperty("user", user);
            connConfig.setProperty("password", password);


            conexion = DriverManager.getConnection("jdbc:mariadb://" + url + "?serverTimezone=Europe/Madrid&characterEncoding=utf8mb4", connConfig);
            conexion.setAutoCommit(true);
            conexion.setAutoCommit(true);
            logger.info("Conexión establecida");
        } catch (SQLException e) {
            logger.error("Error al conectar a la base de datos", e);
            new Alertas().mostrarError("No se ha podido establecer conexión con la base de datos:\n"
                    + e.getMessage() +"\n Revise los datos de configuración inicial. \n " +
                    "Elimine el archivo de configuración incial configuration.properties para volver a empezar");
        }


    }

    /**
     * Esta clase devuelve la conexión creada
     *
     * @return una conexión a la BBDD
     */
    public Connection getConexion() {
        //logger.info("Conexión establecida");
        return conexion;
    }

    /**
     * Metodo de cerrar la conexion con la base de datos
     *
     * @return La conexión cerrada.
     * @throws java.sql.SQLException Se lanza en caso de errores de SQL al cerrar la conexión.
     */
    public Connection CloseConexion() throws SQLException {
        conexion.close();
        // logger.info("Conexión cerrada");
        return conexion;
    }

}
