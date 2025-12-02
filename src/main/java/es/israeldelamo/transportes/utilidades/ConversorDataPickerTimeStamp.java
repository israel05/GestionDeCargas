package es.israeldelamo.transportes.utilidades;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>ConversorDataPickerTimeStamp class.</p>
 *
 * @author israel
 * @version $Id: $Id
 */
public class ConversorDataPickerTimeStamp {
    /**
     * Logger para esta clase
     */
    private static final Logger logger = LoggerFactory.getLogger(ConversorDataPickerTimeStamp.class);

    /**
     * <p>DeStringATimeStamp.</p>
     *
     * @param cadenaYYYYMMddHHmm a {@link java.lang.String} object
     * @return a Timestamp object
     */
    public static Timestamp DeStringATimeStamp(String cadenaYYYYMMddHHmm) {
        try {
            // Crear un objeto SimpleDateFormat para parsear la cadena
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            // Parsear la cadena a un objeto Date
            Date date = dateFormat.parse(cadenaYYYYMMddHHmm);

            // Obtener el tiempo en milisegundos desde el 1 de enero de 1970
            long timeInMillis = date.getTime();

            // Crear un objeto Timestamp a partir del tiempo en milisegundos
            Timestamp timestamp = new Timestamp(timeInMillis);
            System.out.println("Timestamp creado: " + timestamp);
            return timestamp;

        } catch (
                ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}
