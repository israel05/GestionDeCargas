package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloVehiculo;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Dao especial para Vehiculos donde se convierte el observableList a los Arrays para que se usen en Jasper
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoVehiculo.class);


    /**
     * Metodo que carga los datos de la tabla VEHICULOS y los devuelve para usarlos en un listado de vehiculos
     *
     * @return listado de vehiculos para cargar en un tableview
     */
    public static ObservableList<ModeloVehiculo> cargarListadoVehiculos() {
        ObservableList<ModeloVehiculo> listadoDeVehiculos = FXCollections.observableArrayList();
        try {
            ConexionBD conexion = new ConexionBD();
            String consulta = "SELECT * FROM VEHICULOS";
            PreparedStatement pstmt = conexion.getConexion()
                    .prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cod_vehiculo = rs.getString("cod_vehiculo");
                float mma = rs.getFloat("MMA");
                float din_a = rs.getFloat("dimension_A");
                float din_l = rs.getFloat("dimension_L");
                float din_h = rs.getFloat("dimension_H");
                float carga_util = rs.getFloat("carga_util");
                String cod_tipo_vehiculo = rs.getString("cod_tipo_vehiculo");
                ModeloVehiculo mp = new ModeloVehiculo(cod_vehiculo, mma, din_a, din_l, din_h, carga_util, cod_tipo_vehiculo);
                listadoDeVehiculos.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de vehiculos");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDeVehiculos;
    }


    /**
     * Metodo que modifica los datos de un nuevo vehiculo para la bbdd
     *
     * @param vehiculoNuevo Instancia del vehículo con datos nuevos
     * @return true/false si tiene éxito
     */
    public static boolean nuevoVehiculo(ModeloVehiculo vehiculoNuevo) {


        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();
            //INSERT INTO `transportes`.`VEHICULOS` (`cod_vehiculo`, `MMA`, `dimension_A`, `dimension_L`, `dimension_H`, `carga_util`) VALUES ('2', '4', '8', '16', '32', '64');

            String consulta = "INSERT INTO  VEHICULOS (`cod_vehiculo`, `MMA`, `dimension_A`, `dimension_L`, `dimension_H`, `carga_util`, `cod_tipo_vehiculo` ) " +
                    "VALUES (?,?,?,?,?,?,?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);

            pstmt.setString(1, vehiculoNuevo.getCod_vehiculo());
            pstmt.setFloat(2, vehiculoNuevo.getMMA());
            pstmt.setFloat(3, vehiculoNuevo.getDimension_A());
            pstmt.setFloat(4, vehiculoNuevo.getDimension_L());
            pstmt.setFloat(5, vehiculoNuevo.getDimension_H());
            pstmt.setFloat(6, vehiculoNuevo.getCarga_util());
            pstmt.setString(7, vehiculoNuevo.getCod_tipo_vehiculo());

            int filasAfectadas = pstmt.executeUpdate();
            // if (pstmt != null)
            pstmt.close();
            // if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de vehículos");
            logger.error("No he podido cargar el listado de vehículos al crear uno nuevo");
            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }

    /**
     * Modifica el Vehiculo con daots nuevos
     *
     * @param datosNuevosVehiculo   los nuevos datos del vehículo
     * @param codVehiculoAModificar el código de vehículo que vas a modificar.
     * @return a boolean
     */
    public static boolean modificarVehiculo(ModeloVehiculo datosNuevosVehiculo, String codVehiculoAModificar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {

            conexion = new ConexionBD();
            //UPDATE                 `VEHICULOS` SET `MMA` = '5', `dimension_A` = '6', `dimension_L` = '7', `dimension_H` = '8', `carga_util` = '9' WHERE (`cod_vehiculo` = '1');
            String consulta = "UPDATE VEHICULOS SET `MMA` = ? , `dimension_A` = ? , `dimension_L` = ?, `dimension_H` = ? , `carga_util` ,  `cod_tipo_vehiculo`= ? WHERE (`cod_vehiculo` = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setFloat(1, datosNuevosVehiculo.getMMA());
            pstmt.setFloat(2, datosNuevosVehiculo.getDimension_A());
            pstmt.setFloat(3, datosNuevosVehiculo.getDimension_L());
            pstmt.setFloat(4, datosNuevosVehiculo.getDimension_H());
            pstmt.setFloat(5, datosNuevosVehiculo.getCarga_util());
            pstmt.setString(6, datosNuevosVehiculo.getCod_tipo_vehiculo());

            pstmt.setString(7, codVehiculoAModificar);
            int filasAfectadas = pstmt.executeUpdate();

            pstmt.close();
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de vehiculos");
            logger.error("No he podido cargar el listado de vehiculos");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un vehículo seleccionado en función de su cod_vehiculo
     *
     * @param modeloABorrar el vehiculo con código a borrar
     * @return a boolean
     */
    public static boolean elminarVehiculo(ModeloVehiculo modeloABorrar) {

        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();

            String consulta = "DELETE FROM VEHICULOS WHERE (cod_vehiculo = ?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);
            pstmt.setString(1, modeloABorrar.getCod_vehiculo());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de tipos de vehiculos");
            logger.error("No he podido borrar ese registro de tipos de vehiculos");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


}




