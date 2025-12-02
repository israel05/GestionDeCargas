package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloSistemaCargaVehiculo;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>DaoSistemaCargaVehiculo class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoSistemaCargaVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoSistemaCargaVehiculo.class);

    /**
     * Metodo que carga los datos de la tabla Sistemas de carga del vehículo y los devuelve para usarlos en un listado de sistemas de carga
     *
     * @return listado de sistemas de carga para cargar en un tableview
     */
    public static ObservableList<ModeloSistemaCargaVehiculo> cargarListadoSistemaCargaVehiculo() {
        ConexionBD conexion;
        ObservableList<ModeloSistemaCargaVehiculo> listadoSistemasCarga = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM SISTEMA_DE_CARGA_DE_VEHICULO";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cod_sistema_carga = rs.getString("cod_sistema_carga");
                String descripcion = rs.getString("descripcion");
                ModeloSistemaCargaVehiculo mp = new ModeloSistemaCargaVehiculo(cod_sistema_carga, descripcion);
                listadoSistemasCarga.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de sistemas de carga");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoSistemasCarga;
    }

    /**
     * Devuelve un solo objeto Sistema de carga en función del codigo pasasdo
     *
     * @param elCodDelSistemaABuscar el codigo de sistema de carga a buscar
     * @return a {@link es.israeldelamo.transportes.modelos.ModeloSistemaCargaVehiculo} object
     */
    public static ModeloSistemaCargaVehiculo cargarUnSistemaDeCargaConcreto(String elCodDelSistemaABuscar) {
        ConexionBD conexionBD;
        ModeloSistemaCargaVehiculo md = new ModeloSistemaCargaVehiculo();
        try {
            conexionBD = new ConexionBD();
            String consulta = "SELECT * FROM SISTEMA_DE_CARGA_DE_VEHICULO WHERE cod_sistema_carga =?";
            PreparedStatement pstmt = conexionBD.getConexion().prepareStatement(consulta);
            pstmt.setString(1, elCodDelSistemaABuscar);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cod_sistema_carga = rs.getString("cod_sistema_carga");
                String descripcion = rs.getString("descripcion");
                md = new ModeloSistemaCargaVehiculo(cod_sistema_carga, descripcion);
            }
            rs.close();
            conexionBD.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de sistemas de carga");
            alertaError.mostrarError(e.getMessage());
        }
        return md;
    }


    /**
     * Metodo que modifica los datos de un ModeloSistemaCargaVehiculo en la BD
     *
     * @param antiguoModeloSistemaCargaVehiculo Instancia del ModeloSistemaCargaVehiculo con datos nuevos
     * @param nuevadescirpcion                  Nuevo descripcion del ModeloSistemaCargaVehiculo a modificar en String
     * @return true/false
     */
    public static boolean modificarModeloSistemaCargaVehiculo(ModeloSistemaCargaVehiculo antiguoModeloSistemaCargaVehiculo, String nuevadescirpcion) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();

            String consulta = "UPDATE SISTEMA_DE_CARGA_DE_VEHICULO SET descripcion = ? WHERE cod_sistema_carga = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, nuevadescirpcion);
            pstmt.setString(2, antiguoModeloSistemaCargaVehiculo.getCod_sistema_carga());

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de ModeloSistemaCargaVehiculo");
            logger.error("No he podido cargar el listado de ModeloSistemaCargaVehiculo al modificar");
            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }


    /**
     * Metodo que CREA un nuevo un sistema de carga de vehículo en la BD
     *
     * @param sistemaCargaVehiculoNuevo Instancia del nuevo sistema de carga de vehiclo con datos nuevos
     * @return true/false
     */
    public static boolean nuevoModeloSistemaCargaVehiculo(ModeloSistemaCargaVehiculo sistemaCargaVehiculoNuevo) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            //INSERT INTO SISTEMA_DE_CARGA_DE_VEHICULO (cod_sistema_carga, descripcion) VALUES (?.?);
            String consulta = "INSERT INTO SISTEMA_DE_CARGA_DE_VEHICULO (cod_sistema_carga, descripcion) VALUES (?,?) ";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, sistemaCargaVehiculoNuevo.getCod_sistema_carga());
            pstmt.setString(2, sistemaCargaVehiculoNuevo.getDescripcion());

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de ModeloSistemaCargaVehiculo");
            logger.error("No he podido cargar el listado de ModeloSistemaCargaVehiculo");
            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }

    /**
     * Elimina un modelo de sistema de carga de la base de datos
     *
     * @param codigoModeloSistemaCargaVehiculoAEliminar codigo del modelo a eliminar.
     * @return a boolean
     */
    public static boolean eliminarModeloSistemaCargaVehiculo(String codigoModeloSistemaCargaVehiculoAEliminar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM `transportes`.`SISTEMA_DE_CARGA_DE_VEHICULO` WHERE (`cod_sistema_carga` = '3');

            String consulta = "DELETE FROM SISTEMA_DE_CARGA_DE_VEHICULO WHERE (cod_sistema_carga = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, codigoModeloSistemaCargaVehiculoAEliminar);
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
