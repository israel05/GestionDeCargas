package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloDeCarnet;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Este DAO debe verificar la integridad referencial de la tabla TIPO DE CARNET,
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoTipoDeCarnet {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoTipoDeCarnet.class);


    /**
     * Carga el listado de Modelos de Carnet desde la base de datos.
     *
     * @return a {@link javafx.collections.ObservableList} object
     */
    public static ObservableList<ModeloDeCarnet> cargarTiposDeCarnets() {
        ConexionBD conexion;
        ObservableList<ModeloDeCarnet> listadoDeDeCarnets = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT TipoDeCarnet FROM TIPO_DE_CARNET";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tipoDeCarnet = rs.getString("TipoDeCarnet");
                ModeloDeCarnet t = new ModeloDeCarnet(tipoDeCarnet);
                listadoDeDeCarnets.add(t);
            }
            rs.close();
            conexion.CloseConexion();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listadoDeDeCarnets;

    }

    /**
     * Metodo que modifica los datos de un tipo de carnet en la BD
     *
     * @param carnet Instancia del carnet con datos nuevos
     * @return true/false
     */
    public static boolean nuevoCarnet(ModeloDeCarnet carnet) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //INSERT INTO `transportes`.`TIPO_DE_CARNET` (`TipoDeCarnet`) VALUES ('ada');
            String consulta = "INSERT INTO  TIPO_DE_CARNET (TipoDeCarnet) VALUES (?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, carnet.getTipoDeCarnet());
            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de tipos de carnet");
            logger.error("No he podido cargar el listado de tipos de carnet al crear nuevo carnet");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * Metodo que modifica los datos de un tipo de carnet en la BD
     *
     * @param carnet      Instancia del carnet con datos nuevos
     * @param nuevoNombre Nuevo nombre del carnet a modificar
     * @return true/false
     */
    public static boolean modificarCarnet(ModeloDeCarnet carnet, String nuevoNombre) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            //UPDATE `transportes`.`TIPO_DE_CARNET` SET `TipoDeCarnet` = 'AM' WHERE (`TipoDeCarnet` = 'Am');
            String consulta = "UPDATE TIPO_DE_CARNET SET TipoDeCarnet = ? WHERE TipoDeCarnet = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, carnet.getTipoDeCarnet());
            int filasAfectadas = pstmt.executeUpdate();

            pstmt.close();
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de tipos de carnet");
            logger.error("No he podido cargar el listado de tipos de carnet");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * Borra el registro de tipos de carnet de la base de datos en función del objeto tipoDecArnet que le hemos pasado
     *
     * @param carnetABorrar el objeto a borrar de la bbdd
     * @return false si ha fallado, true para el resto
     */
    public static boolean borrarTipoDeCarnet(ModeloDeCarnet carnetABorrar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM TIPO_DE_CARNET WHERE (TipoDeCarnet = ?);
            String consulta = "DELETE FROM TIPO_DE_CARNET WHERE (TipoDeCarnet = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, carnetABorrar.getTipoDeCarnet());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de tipos de carnet");
            logger.error("No he podido borrar ese registro de tipos de carnet");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }
}
