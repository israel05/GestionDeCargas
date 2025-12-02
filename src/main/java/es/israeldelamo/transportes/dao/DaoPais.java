package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloPais;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase par gestionar las llamadas a la tabla PAISES y poder rellenar tablas, modificar elementos y f
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoPais {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoPais.class);

    /**
     * Metodo que carga los datos de la tabla PAISES y los devuelve para usarlos en un listado de paises
     *
     * @return listado de paises para cargar en un tableview
     */
    public static ObservableList<ModeloPais> cargarListadoPaises() {
        ConexionBD conexion;
        ObservableList<ModeloPais> listadoDePaises = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT pais FROM PAISES";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("pais");
                ModeloPais mp = new ModeloPais(pais);
                listadoDePaises.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDePaises;
    }

    /**
     * Metodo que modifica los datos de un pais en la BD
     *
     * @param pais        Instancia del pais con datos nuevos
     * @param nuevoNombre Nuevo nombre del pais a modificar
     * @return true/false
     */
    public static boolean modificarPais(ModeloPais pais, String nuevoNombre) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            // UPDATE `transportes`.`PAISES` SET `pais` = 'BulgariaK' WHERE (`pais` = 'Bulgaria');

            String consulta = "UPDATE PAISES SET pais = ? WHERE pais = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, pais.getPais());

            int filasAfectadas = pstmt.executeUpdate();


            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            logger.error("No he podido cargar el listado de paises al modificar");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * Metodo que CREA un nuevo un pais en la BD
     *
     * @param pais Instancia del pais con datos nuevos
     * @return true/false
     */
    public static boolean nuevoPais(ModeloPais pais) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            // INSERT INTO `transportes`.`PAISES` (`pais`) VALUES ('Afganistan');
            String consulta = "INSERT INTO PAISES (pais) VALUES (?) ";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, pais.getPais());
            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises");
            logger.error("No he podido cargar el listado de paises");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un pais en función del modelo Pais que le hayamos pasado
     *
     * @param paisAEliminar Pais a eliminar
     * @return a boolean
     */
    public static boolean eliminarPais(ModeloPais paisAEliminar) {

        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM `transportes`.`PAISES` WHERE (`pais` = 'asdasd');
            String consulta = "DELETE FROM PAISES WHERE (pais = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, paisAEliminar.getPais());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            logger.error("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

}
