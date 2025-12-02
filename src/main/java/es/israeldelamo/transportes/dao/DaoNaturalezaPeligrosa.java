package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloNaturalezasPeligrosas;
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
public class DaoNaturalezaPeligrosa {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoNaturalezaPeligrosa.class);


    /**
     * Procedimiento que carga los datos de la tabla NATURALEZAS_PELIGROSAS y los devuelve para usarlos en en un listado de naturalezas y su codigo
     *
     * @return listado de naturalezas  para cargar en un tableview
     */
    public static ObservableList<ModeloNaturalezasPeligrosas> cargarListadoNaturalezasPeligrosas() {
        ObservableList<ModeloNaturalezasPeligrosas> listadoDePaises = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();

            String consulta = "SELECT  cod_naturaleza_peligrosa, descripcion_natu_peligrosa FROM transportes.NATURALEZAS_PELIGROSAS;;";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                float codNaturaleza = rs.getFloat("cod_naturaleza_peligrosa");
                String descripcionNaturaleza = rs.getString("descripcion_natu_peligrosa");
                ModeloNaturalezasPeligrosas mp = new ModeloNaturalezasPeligrosas(codNaturaleza, descripcionNaturaleza);
                listadoDePaises.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Naturalezas peligrosas");
            alertaError.mostrarError(e.getMessage());
        }
        return listadoDePaises;
    }

    /**
     * Procedimiento que modifica los datos de una naturaleza peligrosa
     *
     * @param naturalezaAnterior Instancia del pais con datos nuevos
     * @param naturalezaNueva    Nuevo  naturaleza peligrosa
     * @return true/false
     */
    public static boolean modificarCodigoYNaturaleza(float naturalezaAnterior, String naturalezaNueva) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE NATURALEZAS_PELIGROSAS SET descripcion_natu_peligrosa =?  WHERE (cod_naturaleza_peligrosa = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, naturalezaNueva);
            pstmt.setFloat(2, naturalezaAnterior);
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Naturalezas");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Deduelve una naturaleza peligrosa a partir de su código
     *
     * @param codNaturalezaPeligrosa el código de la naturaleza peligrosa
     * @return a {@link ModeloNaturalezasPeligrosas} object
     */
    public static ModeloNaturalezasPeligrosas devuelvemeLaNaturalezaPeligrosaPCompletaPorCodigo(float codNaturalezaPeligrosa) {

        ConexionBD conexion;
        try {
            conexion = new ConexionBD();

            String consulta = "SELECT cod_naturaleza_peligrosa, descripcion_natu_peligrosa FROM NATURALEZAS_PELIGROSAS WHERE cod_naturaleza_peligrosa =? ";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setFloat(1, codNaturalezaPeligrosa);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                float codigoNaturalezaPeligrosa = rs.getFloat("cod_naturaleza_peligrosa");
                String descripcionNaturaleza = rs.getString("descripcion_natu_peligrosa");
                pstmt.close();
                conexion.CloseConexion();
                ModeloNaturalezasPeligrosas naturalezasPeligrosas = new ModeloNaturalezasPeligrosas(codigoNaturalezaPeligrosa, descripcionNaturaleza);
                return naturalezasPeligrosas;
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            //	Alertas alertaError = new Alertas();
            //alertaError.mostrarError("No he podido leer ese codigo, no te puedo devolver nada");
            //alertaError.mostrarError(e.getMessage());
            logger.error(e.getMessage());
        }
        return null;
    }


    /**
     * Crea una nueva entrada en la base de datos de Naturalezas Peligrosas
     *
     * @param nuevaNaturalezaPeligrosa a {@link ModeloNaturalezasPeligrosas} object
     * @return a boolean
     */
    public static boolean nuevaNaturalezaPeligrosa(ModeloNaturalezasPeligrosas nuevaNaturalezaPeligrosa) {
        // hay que verifica que no exista esa entrada. si no, dara error desde base de datos
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            //INSERT INTO `transportes`.`NATURALEZAS_PELIGROSAS` (`cod_naturaleza_peligrosa`, `descripcion_natu_peligrosa`) VALUES ('18', 'aaa');
            conexion = new ConexionBD();
            String consulta = "INSERT INTO NATURALEZAS_PELIGROSAS (cod_naturaleza_peligrosa, descripcion_natu_peligrosa) VALUES (?, ?);";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, String.valueOf(nuevaNaturalezaPeligrosa.getCodigo_naturaleza_peligrosa()));
            pstmt.setString(2, nuevaNaturalezaPeligrosa.getDescripcion_naturaleza_peligrosa());
            int filasAfectadas = pstmt.executeUpdate();// aquí veremos cuantas filas están afectadas en caso de no poder añadir cosas


            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Naturalezas peligrosas");
            logger.error(e.getMessage());
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una entrada de naturaleza peligrosa
     *
     * @param naturalezaPeligrosaAEliminar según su código
     * @return el resultado boolean de la eliminación
     */
    public static boolean eliminarNaturalezaPeligrosa(String naturalezaPeligrosaAEliminar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM LOGINS WHERE (user = ?) and (pass = ?) and (rol = ?);
            String consulta = "DELETE FROM NATURALEZAS_PELIGROSAS WHERE (cod_naturaleza_peligrosa = ?)";

            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, naturalezaPeligrosaAEliminar);
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * Filtra toda la lista de Naturalezas peligrosas por el código que quieras
     * y devuelve solo la descripción de esa naturaleza
     *
     * @param codigoQueBusco un Float con el código.
     * @return solo la descripción de esa naturaleza
     */
    public static String devuelvemeLaNaturalezaPeligrosaPorCodigo(Float codigoQueBusco) {
        ConexionBD conexion;
        String descripcionNaturaleza = "DESCRIPCIÓN NO ENCONTRADA";
        try {
            conexion = new ConexionBD();

            String consulta = "SELECT  descripcion_natu_peligrosa FROM NATURALEZAS_PELIGROSAS WHERE cod_naturaleza_peligrosa =? ";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setFloat(1, (codigoQueBusco));


            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                descripcionNaturaleza = rs.getString("descripcion_natu_peligrosa");
                pstmt.close();
                conexion.CloseConexion();
                logger.info("Encontrada la descripción como :{}", descripcionNaturaleza);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido leer ese codigo, no te puedo devolver nada");
            alertaError.mostrarError(e.getMessage());
            logger.error("No he podido recuperar la descripción de esa naturaleza");
        }
        return descripcionNaturaleza;
    }


}
