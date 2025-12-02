package es.israeldelamo.transportes.dao;

import es.israeldelamo.transportes.bbdd.ConexionBD;
import es.israeldelamo.transportes.modelos.ModeloEmpresa;
import es.israeldelamo.transportes.utilidades.Alertas;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase par gestionar las llamadas a la tabla EMPRESA y poder rellenar tablas, modificar elementos y f
 *
 * @author israel
 * @version $Id: $Id
 */
public class DaoEmpresa {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(DaoEmpresa.class);


    /**
     * Contiene en forma de String Array la lista de los cif leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] cifArray;

    /**
     * Contiene en forma de String Array la lista de los nombres leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] nombreArray;
    /**
     * Contiene en forma de String Array la lista de los domicilios leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] domicilioArray;
    /**
     * Contiene en forma de String Array la lista de los roles leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] paisArray;
    /**
     * Contiene en forma de String Array la lista de los paises leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] provinciaArray;
    /**
     * Contiene en forma de String Array la lista de las provincias leídas por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] localidadArray;
    /**
     * Contiene en forma de String Array la lista de los cp leídos por la base de datos. Es necesario para JasperReportsDataSource
     */
    public String[] cpArray;


    /**
     * Procedimiento que carga una sola empresa de la tabla EMPRESA y los devuelve esa empresa nada más
     *
     * @return listado de una sola empresa
     */
    public static ModeloEmpresa cargarUnaSolaEmpresas(String cifABuscar) {


        ConexionBD conexion;
        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM EMPRESAS WHERE cif =? ";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, cifABuscar);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String cif = rs.getString("cif");
                String nombre = rs.getString("nombre");
                String domicilio = rs.getString("domicilio");
                String pais = rs.getString("pais");
                String provincia = rs.getString("provincia");
                String localidad = rs.getString("localidad");
                Integer cp = rs.getInt("cp");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String persona_contacto = rs.getString("persona_contacto");


                pstmt.close();
                conexion.CloseConexion();

                ModeloEmpresa mp = new ModeloEmpresa(cif, nombre, domicilio, pais, provincia, localidad, cp, correo, telefono, persona_contacto);

                return mp;
            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido leer ese codigo, no te puedo devolver nada");
            alertaError.mostrarError(e.getMessage());
            logger.error(e.getMessage());
        }
        return null;
    }


    /**
     * Procedimiento que carga los datos de la tabla EMPRESA y los devuelve para usarlos en un listado de empresas
     *
     * @return listado de paises para cargar en un tableview
     */
    public static ObservableList<ModeloEmpresa> cargarListadoEmpresas() {
        ConexionBD conexion;
        ObservableList<ModeloEmpresa> listadoDeEmpresas = FXCollections.observableArrayList();

        try {
            conexion = new ConexionBD();

            String consulta = "SELECT * FROM EMPRESAS";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String cif = rs.getString("cif");
                String nombre = rs.getString("nombre");
                String domicilio = rs.getString("domicilio");
                String pais = rs.getString("pais");
                String provincia = rs.getString("provincia");
                String localidad = rs.getString("localidad");
                Integer cp = rs.getInt("cp");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                String persona_contacto = rs.getString("persona_contacto");


                ModeloEmpresa mp = new ModeloEmpresa(cif, nombre, domicilio, pais, provincia, localidad, cp, correo, telefono, persona_contacto);
                listadoDeEmpresas.add(mp);

            }
            rs.close();
            conexion.CloseConexion();
        } catch (
                SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de Empresas");
            alertaError.mostrarError(e.getMessage());
            logger.error("No he podido cargar el listado de empresas");
        }
        return listadoDeEmpresas;
    }

    /**
     * Procedimiento que modifica los datos de una Empresa en la BD
     *
     * @param empresaACambiar      Instancia del pais con datos nuevos
     * @param nuevosDatosDeEMpresa Nuevos datos para la empresa
     * @return true/false
     */
    public static boolean modificarEmpresa(ModeloEmpresa empresaACambiar, ModeloEmpresa nuevosDatosDeEMpresa) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();

            String consulta = "UPDATE EMPRESAS SET nombre = ?, domicilio =?, pais =?, provincia =?, localidad =?, cp = ? , correo=?, telefono=?, persona_contacto=? WHERE cif = ?";
            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, nuevosDatosDeEMpresa.getNombre());
            pstmt.setString(2, nuevosDatosDeEMpresa.getDomicilio());
            pstmt.setString(3, nuevosDatosDeEMpresa.getPais());
            pstmt.setString(4, nuevosDatosDeEMpresa.getProvincia());
            pstmt.setString(5, nuevosDatosDeEMpresa.getLocalidad());
            pstmt.setInt(6, nuevosDatosDeEMpresa.getCP());
            pstmt.setString(7, nuevosDatosDeEMpresa.getCorreo());
            pstmt.setString(8, nuevosDatosDeEMpresa.getTelefono());
            pstmt.setString(9, nuevosDatosDeEMpresa.getPersona_contacto());


            pstmt.setString(10, empresaACambiar.getCif());
            int filasAfectadas = pstmt.executeUpdate();

            pstmt.close();

            conexion.CloseConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de empresas");
            logger.error("No he podido modificar el listado de empresas");
            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }


    /**
     * Procedimiento que CREA un nuevo una EMPRESA en la BD
     *
     * @param empresaNueva Instancia del pais con datos nuevos
     * @return true/false
     */
    public static boolean nuevaEmpresa(ModeloEmpresa empresaNueva) {
        ConexionBD conexion;
        PreparedStatement pstmt;

        try {
            conexion = new ConexionBD();

            String consulta = "INSERT INTO EMPRESAS (cif, nombre, domicilio, pais, provincia, localidad, cp, correo, telefono, persona_contacto) VALUES (?,?,?,?,?,?,?,?,?,?)";

            pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, empresaNueva.getCif());
            pstmt.setString(2, empresaNueva.getNombre());
            pstmt.setString(3, empresaNueva.getDomicilio());
            pstmt.setString(4, empresaNueva.getPais());
            pstmt.setString(5, empresaNueva.getProvincia());
            pstmt.setString(6, empresaNueva.getLocalidad());
            pstmt.setInt(7, empresaNueva.getCp());
            pstmt.setString(8, empresaNueva.getCorreo());
            pstmt.setString(9, empresaNueva.getTelefono());
            pstmt.setString(10, empresaNueva.getPersona_contacto());


            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido cargar el listado de empresas");
            logger.error("No he podido Crear una nueva empresa de empresas");
            alertaError.mostrarError(e.getMessage());
            return false;

        }

    }


    /**
     * Elimina de la base de datos una empresa dada
     *
     * @param empresaAEliminar a {@link es.israeldelamo.transportes.modelos.ModeloEmpresa} object
     * @return a boolean
     */
    public static boolean eliminarEmpresa(ModeloEmpresa empresaAEliminar) {
        ConexionBD conexion;
        PreparedStatement pstmt;
        try {
            conexion = new ConexionBD();
            //DELETE FROM LOGINS WHERE (user = ?) and (pass = ?) and (rol = ?);
            String consulta = "DELETE FROM EMPRESAS WHERE (cif = ?) and (nombre = ?)";
            pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, empresaAEliminar.getCif());
            pstmt.setString(2, empresaAEliminar.getNombre());

            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.CloseConexion();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            Alertas alertaError = new Alertas();
            alertaError.mostrarError("No he podido borrar ese registro de empresa");
            logger.error("No he podido borrar ese registro de empresa");
            alertaError.mostrarError(e.getMessage());
            return false;
        }
    }

/*
	/**
	 * Lee toda la base de datos igual que CargarListadoEmpresas, pero en vez de un observableList, escribe unos arrays
	 * Da mil vueltas desde un array a un list para que no salte por los aires al añadir los elementos del pstmt
	 * @deprecated  NO USAR, era para Jasper 6.2 con adaptadores java, ahora tiramos contra JDBC desde JRXML

	public void cargarListadoEmpresasAArrays() {
		ConexionBD conexion;

		List<String> listaDeStringsCif = new ArrayList<>();
		List<String> listaDeStringsNombre = new ArrayList<>();
		List<String> listaDeStringsDimicilio = new ArrayList<>();
		List<String> listaDeStringsPais = new ArrayList<>();
		List<String> listaDeStringsProvincia = new ArrayList<>();
		List<String> listaDeStringsLocalidad = new ArrayList<>();
		List<String> listaDeStringsCp = new ArrayList<>();

		try{
			conexion = new ConexionBD();
			String consulta = "SELECT cif, nombre, domicilio, pais, provincia, localidad, cp FROM EMPRESAS";
			PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				String cif = String.valueOf(rs.getInt("cif"));
				String nombre = rs.getString("nombre");
				String domicilio = rs.getString("domicilio");
				String pais = rs.getString("pais");
				String provincia = rs.getString("provincia");
				String localidad = rs.getString("localidad");
				String cp = String.valueOf(rs.getInt("cp"));

				listaDeStringsCif.add(cif);
				listaDeStringsNombre.add(nombre);
				listaDeStringsDimicilio.add(domicilio);
				listaDeStringsPais.add(pais);
				listaDeStringsProvincia.add(provincia);
				listaDeStringsLocalidad.add(localidad);
				listaDeStringsCp.add(String.valueOf(cp));

			}

			/* SINTAXIS INTERCAMBIABLES, como lambda o como doble dos puntos

			cifArray = listaDeStringsCif.stream().toArray(cif-> new String[cif] );
			cifArray = listaDeStringsCif.stream().toArray(String[] ::new);
			//

			cifArray = listaDeStringsCif.stream().toArray(String[] ::new);

			nombreArray = listaDeStringsNombre.stream().toArray(String[] ::new);

			domicilioArray = listaDeStringsDimicilio.stream().toArray(String[] ::new);

			paisArray = listaDeStringsPais.stream().toArray(String[] ::new);

			provinciaArray = listaDeStringsProvincia.stream().toArray(String[] ::new);

			localidadArray = listaDeStringsLocalidad.stream().toArray(String[] ::new);

			cpArray = listaDeStringsCp.stream().toArray(String[] ::new);



			rs.close();
			conexion.CloseConexion();
		}catch (
				SQLException e) {
		//	Alertas alertas = new Alertas();
		//	alertas.mostrarError("Imposible pasar los datos De Empresa Para Informes");
		//	alertas.mostrarError(e.getMessage());
			e.printStackTrace();
		}
	}
	*/
/*
	public static void main(String[] args) {
		ModeloEmpresa modelin = DaoEmpresa.cargarUnaSolaEmpresas("23");
        assert modelin != null;
        System.out.println(modelin.getNombre());
	}*/
}

