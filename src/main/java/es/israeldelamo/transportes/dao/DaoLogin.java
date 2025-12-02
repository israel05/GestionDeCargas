package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloLogin;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * DAO PLUGIN es el encargado de llamar a la BBDD de manera ordenada.
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoLogin {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoLogin.class);


    /**
     * Carga todos los Logins esn una colección
     *
     * @return a {@link javafx.collections.ObservableList} object
     */
    public static ObservableList<ModeloLogin> cargarListadoLogins() throws SQLException {
        ObservableList<ModeloLogin> listadoDeLogins = FXCollections.observableArrayList();
        ConexionBD conexion;
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT user,pass,rol FROM LOGINS";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String user = rs.getString("user");
                String pass = rs.getString("pass");
                String rol = rs.getString("rol");
                ModeloLogin l = new ModeloLogin(user, pass, rol);
                listadoDeLogins.add(l);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listadoDeLogins;
    }


    /**
     * Solo cambia el papel de un usuario ya registrado con su contraseña.
     *
     * @param login      El usuario a cambiar
     * @param nuevoPapel el nuevo rol excluyente a tener
     * @return a boolean
     */
    public static boolean modificarLogin(ModeloLogin login, String nuevoPapel) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE LOGINS SET rol = ? WHERE user = ? and pass = ? and rol = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, nuevoPapel); //nuevo rol
            pstmt.setString(2, login.getUser());
            pstmt.setString(3, login.getPass());
            pstmt.setString(4, login.getRol());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de logins");
            logger.error("No he podido cargar el listado de logins al modificarlos");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

    /**
     * Borra el login que coincida con el pasado como parámetro
     *
     * @param login El usuario a eliminar
     * @return a boolean
     */
    public static boolean borrarLogin(ModeloLogin login) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM LOGINS WHERE (user = ?) and (pass = ?) and (rol = ?);
            String consulta = "DELETE FROM LOGINS WHERE (user = ?) and (pass = ?) and (rol = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, login.getUser());
            pstmt.setString(2, login.getPass());
            pstmt.setString(3, login.getRol());
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


    /**
     * Añade un nuevo usuario con su contraseña en el sitio.
     *
     * @param login El usuario a cambiar
     * @return a boolean
     */
    public static boolean nuevoLogin(ModeloLogin login) {
        // hay que verifica que no exista esa entrada. si no, dara error desde base de datos
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO LOGINS (`user`, `pass`, `rol`) VALUES (?, ?, ?);";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, login.getUser());
            pstmt.setString(2, login.getPass());
            pstmt.setString(3, login.getRol());
            int filasAfectadas = pstmt.executeUpdate();// aquí veremos cuantas filas están afectadas en caso de no poder añadir cosas


            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de logins");
            logger.error("No he podido cargar el listado de logins");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }


    /**
     * Elimina una entrada en la tabla login
     *
     * @param ml la entrada de login a eliminar
     * @return a boolean
     */
    public static boolean eliminarLogin(ModeloLogin ml) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();

            String consulta = "DELETE FROM LOGINS WHERE (user = ?) AND (pass=?) AND (rol=?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, ml.getUser());
            pstmt.setString(2, ml.getPass());
            pstmt.setString(3, ml.getRol());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();
            System.out.println("Actualizada la tabla de logins");
            return filasAfectadas > 0;

        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro");
            alertaError.mostrarError(e.getMessage());
            return false;
        }


    }
}
