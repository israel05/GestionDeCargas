package es.israeldelamo.transportes.modelos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Modelo que refleja la tabla llamada Login
 *
 * @author israel
 * @version $Id: $Id
 */
public class ModeloLogin {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ModeloLogin.class);
    private String user;
    private String pass;
    private String rol;

    /**
     * Constructor del objeto Login a partir de los tres campos que tiene en su base da datos
     *
     * @param user a {@link java.lang.String} object
     * @param pass a {@link java.lang.String} object
     * @param rol  a {@link java.lang.String} object
     */
    public ModeloLogin(String user, String pass, String rol) {
        this.user = user;
        this.pass = pass;
        this.rol = rol;
    }

    /**
     * Recupera el usuario de la tabla Login
     *
     * @return a {@link java.lang.String} object
     */
    public String getUser() {
        return user;
    }

    /**
     * Asigna un user para meterlo en la tabla LOGIN
     *
     * @param user a {@link java.lang.String} object
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Lee la entrada Pass del objeto Login
     *
     * @return a {@link java.lang.String} object
     */
    public String getPass() {
        return pass;
    }

    /**
     * Asigna un pass al objeto Login
     *
     * @param pass a {@link java.lang.String} object
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * Devuelve el atributo rol del objeto Login
     *
     * @return a {@link java.lang.String} object
     */
    public String getRol() {
        return rol;
    }

    /**
     * Asigna un rol al objeto para meterlo en el objeto Login
     *
     * @param rol a {@link java.lang.String} object
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getUser() + " " + getPass() + " " + getRol();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModeloLogin that = (ModeloLogin) o;
        return Objects.equals(getUser(), that.getUser()) && Objects.equals(getPass(), that.getPass()) && Objects.equals(getRol(), that.getRol());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getPass(), getRol());
    }
}
