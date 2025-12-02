package es.israeldelamo.transportes.utilidades;

import es.israeldelamo.transportes.modelos.ModeloEmpresa;
import javafx.util.StringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpresaStringConverter extends StringConverter<ModeloEmpresa> {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(EmpresaStringConverter.class);


    /**
     * {@inheritDoc}
     * <p>
     * Esta clase está extendida de StringConverter. A veces se lee null entre los elementos del Empresa y tira la app
     * mediante esta condicional interna se devuelve vacío como cadena cuando el objeto elegido es ninguno
     */
    @Override
    public String toString(ModeloEmpresa object) {
        if (object != null) {
            // usaremos como plantilla 232323, CocaCola
            // asi es como se verán dentro del combo box, que para eso se ha creado esta clase
            return object.getCif() + "," + object.getNombre();
        } else {
            return "";
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModeloEmpresa fromString(String empresa) {
        //ahora hay que descomponer este string en dos partes, como hemos usado en toString
        // usaremos como plantilla 121212, CocaCola
        // hay que hacer usa separación para ese constructor
        String[] parts = empresa.split(",");
        String cp = parts[1].trim();
        String nombre = parts[0].trim();
        return new ModeloEmpresa(cp, nombre);
    }


}
