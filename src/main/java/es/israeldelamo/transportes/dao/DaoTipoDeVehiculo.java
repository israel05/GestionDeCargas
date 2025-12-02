package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloTipoDeVehiculo;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Dao especial para Tipo De Vehículo donde se convierte el observableList a los Arrays para que se usen en Jasper
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoTipoDeVehiculo {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoTipoDeVehiculo.class);

    /**
     * Carga la lista de los vehiculos desde la tabla hasta un observableArrayList
     *
     * @return a {@link javafx.collections.ObservableList} object
     */
    public static ObservableList<ModeloTipoDeVehiculo> cargarListadoVehiculos() {
        ConexionBD conexion;
        ObservableList<ModeloTipoDeVehiculo> listadoDeTiposDeVehiculos = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM TIPO_DE_VEHICULO";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cod_tipo_vehiculo = rs.getString("cod_tipo_vehiculo");
                String estipulacion = rs.getString("estipulacion");
                String instrucciones = rs.getString("instrucciones");
                Blob foto = rs.getBlob("foto");
                Boolean tiene_rampa = rs.getBoolean("tiene_rampa");
                Boolean puede_adr = rs.getBoolean("puede_adr");
                Boolean puede_atp = rs.getBoolean("puede_atp");
                String cod_sistema_carga = rs.getString("SISTEMAEDCARGAVEHICULO_cod_sistem_carga");

                ModeloTipoDeVehiculo mp = new ModeloTipoDeVehiculo(
                        cod_tipo_vehiculo,

                        estipulacion,
                        foto,
                        instrucciones,
                        puede_atp,
                        puede_adr,
                        cod_sistema_carga,
                        tiene_rampa);

                listadoDeTiposDeVehiculos.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Tipo de vehiculos");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDeTiposDeVehiculos;
    }

    /**
     * Dado un codigo de tipo de vehiculo devuelve de la base de datos toda una tupla
     *
     * @param cod_tipo_vehiculo_a_recuperar el código de tipo de vehiculo a devolver
     * @return todos los datos recuperados de la base de datos sobre ese tipo de vehiculos
     */
    public static ModeloTipoDeVehiculo recuperaUnTipoDeVehiculo(String cod_tipo_vehiculo_a_recuperar) {
        ConexionBD conexion;
        ModeloTipoDeVehiculo tipoDeVehiculoRecuperado = new ModeloTipoDeVehiculo();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM TIPO_DE_VEHICULO WHERE cod_tipo_vehiculo =?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, cod_tipo_vehiculo_a_recuperar);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String cod_tipo_vehiculo = rs.getString("cod_tipo_vehiculo");
                String estipulacion = rs.getString("estipulacion");
                Blob foto = rs.getBlob("foto");
                String instrucciones = rs.getString("instrucciones");
                Boolean puede_atp = rs.getBoolean("puede_atp");
                Boolean puede_adr = rs.getBoolean("puede_adr");
                String cod_sistema_carga = rs.getString("SISTEMAEDCARGAVEHICULO_cod_sistem_carga");
                Boolean tiene_rampa = rs.getBoolean("tiene_rampa");


                return new ModeloTipoDeVehiculo(
                        cod_tipo_vehiculo,

                        estipulacion,
                        foto,
                        instrucciones,
                        puede_atp,
                        puede_adr,
                        cod_sistema_carga,
                        tiene_rampa);


            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar ese tipo de vehiculo en concreto desde Tipo de vehiculos");
            alertaError.mostrarError(e.getMessage());
            return new ModeloTipoDeVehiculo();
        }

        return tipoDeVehiculoRecuperado;
    }


    /**
     * Modifica una entrada en la base de datos partiendo del código de tipo de vehículo
     *
     * @param tipoDeVehiculoOriginal   contiene el código del objeto a modificar en la bbdd
     * @param tipoDeVehiculoModificado los datos nuevos a actualizar.
     * @return a boolean
     */
    public static boolean modificarTipoDeVehiculo(ModeloTipoDeVehiculo tipoDeVehiculoOriginal, ModeloTipoDeVehiculo tipoDeVehiculoModificado) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //UPDATE TIPO_DE_VEHICULO SET es_carga_lateral = ?, es_carga_superior = ?, es_LOLO_o_RORO = ?, SISTEMAEDCARGAVEHICULO_cod_sistem_carga = ?, estipulacion = ?, instrucciones = ?, foto = ?, tiene_rampo = ?, puede_adr = ?, puede_atp = ? WHERE (cod_tipo_vehiculo = ?);
            String consulta = "UPDATE TIPO_DE_VEHICULO" +
                    " SET " +
                    "SISTEMAEDCARGAVEHICULO_cod_sistem_carga = ?, " +
                    "estipulacion = ?, " +
                    "instrucciones = ?, " +
                    "foto = ?, " +
                    "tiene_rampa = ?, " +
                    "puede_adr = ?, " +
                    "puede_atp = ?" +
                    " WHERE (cod_tipo_vehiculo = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, tipoDeVehiculoModificado.getCod_sistema_carga());
            pstmt.setString(2, tipoDeVehiculoModificado.getEstipulacion());
            pstmt.setString(3, tipoDeVehiculoModificado.getInstrucciones());
            pstmt.setBlob(4, tipoDeVehiculoModificado.getFoto());
            pstmt.setBoolean(5, tipoDeVehiculoModificado.getTiene_rampa());
            pstmt.setBoolean(6, tipoDeVehiculoModificado.getPuede_adr());
            pstmt.setBoolean(7, tipoDeVehiculoModificado.getPuede_atp());
            pstmt.setString(8, tipoDeVehiculoOriginal.getCod_tipo_vehiculo());
            int filasAfectadas = pstmt.executeUpdate();

            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de tipos de vehiculos");
            logger.error("No he podido cargar el listado de tipos de vehiculos");
            alertaError.mostrarError(e.getMessage());
            return false;

        }
    }


    /**
     * Nuevo tipo de vehículo a ingresar en la base de datos
     *
     * @param modeloTipoDeVehiculo el nuevo tipo de vehículo
     * @return a boolean
     */
    public static boolean nuevoTipoDeVehiculo(ModeloTipoDeVehiculo modeloTipoDeVehiculo) {


        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();
            //INSERT INTO `transportes`.`TIPO_DE_VEHICULO` (`cod_tipo_vehiculo`, `es_carga_lateral`, `es_carga_superior`, `es_LOLO_o_RORO`, `SISTEMAEDCARGAVEHICULO_cod_sistem_carga`, `estipulacion`, `instrucciones`, `tiene_rampa`, `puede_adr`, `puede_atp`) VALUES ('2', '0', '0', '0', '1', '0', '0', '0', '0', '0');
            String consulta = "INSERT INTO  TIPO_DE_VEHICULO (" +
                    "`cod_tipo_vehiculo`," +

                    "`SISTEMAEDCARGAVEHICULO_cod_sistem_carga`," +
                    "`estipulacion`, " +
                    "`instrucciones`," +
                    "`foto` ," +
                    "`tiene_rampa`, " +
                    "`puede_adr`, " +
                    "`puede_atp`) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);

            pstmt.setString(1, modeloTipoDeVehiculo.getCod_tipo_vehiculo());
            pstmt.setString(2, modeloTipoDeVehiculo.getCod_sistema_carga());
            pstmt.setString(3, modeloTipoDeVehiculo.getEstipulacion());
            pstmt.setString(4, modeloTipoDeVehiculo.getInstrucciones());
            pstmt.setBlob(5, modeloTipoDeVehiculo.getFoto());
            pstmt.setBoolean(6, modeloTipoDeVehiculo.getTiene_rampa());
            pstmt.setBoolean(7, modeloTipoDeVehiculo.getPuede_adr());
            pstmt.setBoolean(8, modeloTipoDeVehiculo.getPuede_atp());

            int filasAfectadas = pstmt.executeUpdate();
            // if (pstmt != null)
            pstmt.close();
            // if (conexion != null)
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de tipo de vehiculos");
            logger.error("No he podido cargar el listado de tipo de vehiculos");
            alertaError.mostrarError(e.getMessage());
            return false;
        }

    }

    /**
     * Elimina un tipo de bulto de la base de datos
     *
     * @param modeloTipoDeVehiculoAEliminarCod_tipo_vehiculo Modelo a eliminar
     * @return a boolean
     */
    public static boolean eliminarTipoDeVehiculo(String modeloTipoDeVehiculoAEliminarCod_tipo_vehiculo) {
        try {
            ConexionBD conexion;
            PreparedStatement pstmt;
            conexion = new ConexionBD();

            String consulta = "DELETE FROM TIPO_DE_VEHICULO WHERE (cod_tipo_vehiculo = ?)";
            pstmt = conexion.getConexion()
                    .prepareStatement(consulta);
            pstmt.setString(1, modeloTipoDeVehiculoAEliminarCod_tipo_vehiculo);
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de tipos de vehiculos");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

}
