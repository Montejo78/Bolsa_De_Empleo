package BolsaDeEmpleo;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class TablaBase {

    public static void main(String[] args) throws Exception {

        // Ubicación del archivo de la base de datos
        String url = "jdbc:h2:file:./BolsaDeEmpleo";

        // Conexión con el driver de la base de datos
        ConnectionSource conexion =
                new JdbcConnectionSource(url);

        // Eliminar la tabla si ya existe
        TableUtils.dropTable(conexion, Aspirantes.class, true);

        // Creación de la base de datos y la tabla
        TableUtils.createTable(conexion, Aspirantes.class);
        System.out.println("Tabla creada satisfactoriamente!");

        // Cerrar la conexión
        conexion.close();
    }
}

