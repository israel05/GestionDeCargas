package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Este DAO debe verificar la integridad referencial de la tabla PROVINCIAS, impidiendo ningun añadido si el pais no
 * existia, su misión es informar al usuario de los errores posibles a la hora añadir esa información
 *
 * @author israel
 * @version $Id: $Id
 */
public final class DaoPaisYProvinciaYLocalidadYCodigoPostal {

    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoPaisYProvinciaYLocalidadYCodigoPostal.class);

    /**
     * Constructor privado para evitar instanciación
     */
    private DaoPaisYProvinciaYLocalidadYCodigoPostal() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }

    /**
     * Llama a la base de datos y en función de la localidad, devuelve una lista u otra
     *
     * @param laLocalidadFiltrante la localidad que tiene esos cp
     * @param paisFiltrante        pais filtrante
     * @param provinciaFiltrante   provincia filtrante
     * @return un listado de CP filtrados
     */
    public static ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostalesFiltrado(String paisFiltrante, String provinciaFiltrante, String laLocalidadFiltrante) {
        ConexionBD conexion;
        ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> listadoDePaisesYProvinciasYCodigosPostales = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT cp, localidad, provincia,pais  FROM CODIGOS_POSTALES WHERE (localidad=?) AND (provincia =?) AND (pais=?)   ";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, laLocalidadFiltrante);
            pstmt.setString(2, provinciaFiltrante);
            pstmt.setString(3, paisFiltrante);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("pais");
                String provincia = rs.getString("provincia");
                String localidad = rs.getString("localidad");
                Integer codigoPostal = rs.getInt("cp");
                ModeloPaisYProvinciaYLocalidadYCodigoPostal mp = new ModeloPaisYProvinciaYLocalidadYCodigoPostal(codigoPostal, localidad, provincia, pais);
                listadoDePaisesYProvinciasYCodigosPostales.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listadoDePaisesYProvinciasYCodigosPostales;
    }

    /**
     * Un constructor del Dao que devuelve toda la base de datos CP
     *
     * @return todas las entradas de CP
     */
    public static ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> cargarListadoPaisesYProvinciasYLocalidadesYCodigosPostales() {
        ConexionBD conexion;
        ObservableList<ModeloPaisYProvinciaYLocalidadYCodigoPostal> listadoDePaisesYProvinciasYCodigosPostales = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT cp, provincia,pais, localidad FROM CODIGOS_POSTALES";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);


            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String pais = rs.getString("pais");
                String provincia = rs.getString("provincia");
                String localidad = rs.getString("localidad");
                Integer codigoPostal = rs.getInt("cp");
                ModeloPaisYProvinciaYLocalidadYCodigoPostal mp = new ModeloPaisYProvinciaYLocalidadYCodigoPostal(codigoPostal, localidad, provincia, pais);
                listadoDePaisesYProvinciasYCodigosPostales.add(mp);
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return listadoDePaisesYProvinciasYCodigosPostales;
    }

    /**
     * Metodo que modifica los datos de un  codigos postales en la BD. AÑADIR RESTRICCIÓN PARA QUE NO ENTREN PAISES
     *
     * @param paisyprovinciaylocalidadycodigopostal a {@link es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal} object
     * @param nuevoCodigoPostal                     a {@link java.lang.Integer} object
     * @return true/false
     */
    public static boolean modificarCodigoPostal(ModeloPaisYProvinciaYLocalidadYCodigoPostal paisyprovinciaylocalidadycodigopostal, Integer nuevoCodigoPostal) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();
            //UPDATE CODIGOS_POSTALES SET cp = '1016' WHERE (cp = 1010) and (Provincias_provincia = ÁlavaXDXD) and (Provincias_Paises_pais = España);

            String consulta = "UPDATE CODIGOS_POSTALES SET cp = ? WHERE cp = ? and and localidad =? provincia = ? and pais = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setInt(1, nuevoCodigoPostal);
            pstmt.setInt(2, paisyprovinciaylocalidadycodigopostal.getCodigoPostal());
            pstmt.setString(3, paisyprovinciaylocalidadycodigopostal.getLocalidad());
            pstmt.setString(4, paisyprovinciaylocalidadycodigopostal.getProvincia());
            pstmt.setString(5, paisyprovinciaylocalidadycodigopostal.getPais());

            int filasAfectadas = pstmt.executeUpdate();

            pstmt.close();

            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de paises y provincias y y codigo postal y ccpp");
            logger.error("No he podido cargar el listado de paises y provincias y y codigo postal y ccpp");

            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }

    /**
     * Elimina una entrada de la base de datos de Codigo POstal
     *
     * @param aEliminar el objeto a eliminar
     * @return el resultado de la operación
     */
    public static boolean eliminarCodigoPostal(ModeloPaisYProvinciaYLocalidadYCodigoPostal aEliminar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            // DELETE FROM CODIGOS_POSTALES WHERE (localidad = ?) and (provincia = ?) and (pais = ?) and (cp = ?);

            String consulta = "DELETE FROM CODIGOS_POSTALES WHERE (localidad = ?) and (provincia = ?) and (pais = ?) and (cp = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(4, String.valueOf(aEliminar.getCodigoPostal()));
            pstmt.setString(3, aEliminar.getPais());
            pstmt.setString(1, aEliminar.getLocalidad());
            pstmt.setString(2, aEliminar.getProvincia());
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
     * Nuevo Código Postal una vez teniendo el país, la provincia y la localidad
     *
     * @param nuevoModelo a {@link es.israeldelamo.transportes.modelos.ModeloPaisYProvinciaYLocalidadYCodigoPostal} object
     * @return a boolean
     */
    public static boolean nuevoCodigoPostal(ModeloPaisYProvinciaYLocalidadYCodigoPostal nuevoModelo) {
        try {
            ConexionBD conexion;
            PreparedStatement pstmt;

            conexion = new ConexionBD();
            //INSERT INTO CODIGOS_POSTALES (cp, localidad, provincia, pais) VALUES (?,?,?,?);
            String consulta = "INSERT INTO CODIGOS_POSTALES (cp, localidad, provincia, pais) VALUES (?,?,?,?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, String.valueOf(nuevoModelo.getCodigoPostal()));
            pstmt.setString(2, nuevoModelo.getLocalidad());
            pstmt.setString(3, nuevoModelo.getProvincia());
            pstmt.setString(4, nuevoModelo.getPais());

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de CP");
            logger.error("No he podido cargar el listado de CP");
            alertaError.mostrarError(e.getMessage());
            return false;

        }
    }
}
