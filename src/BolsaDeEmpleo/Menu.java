package BolsaDeEmpleo;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) throws Exception {

        Logger.setGlobalLogLevel(Level.OFF);
        Scanner entrada = new Scanner(System.in);
        int opcion = 0;
        String cedula;
        String nombre;
        int edad;
        int experiencia;
        String profesion;
        String telefono;

        // Esta es la ubicacion del  archivo de la base de datos
        String url = "jdbc:h2:file:./BolsaDeEmpleo";
        ConnectionSource conexion =
                new JdbcConnectionSource(url);

        // aqui se obtiene el acceso a la lista de objetos =>Tabla (DAO)

        Dao<Aspirantes, String> listaAspirantes =
                DaoManager.createDao(conexion, Aspirantes.class);

        //Este es el menu de opciones que se presenta en la pantalla

        do {
            System.out.println("1. Agregar hojas de vida de aspirante.");
            System.out.println("2. Mostrar cedulas de los aspirantes.");
            System.out.println("3. Mostrar la información detallada del aspirante, a partir de la cedula.");
            System.out.println("4. Buscar por nombre del aspirante.");
            System.out.println("5. Ordenar la lista de aspirantes por los diferentes criterios: años de experiencia, edad y profesión.");
            System.out.println("6. Consultar el aspirante con mayor experiencia.");
            System.out.println("7. Consultar el aspirante más joven.");
            System.out.println("8. Contratar un aspirante (eliminarlo de la lista de aspirantes de la bolsa).");
            System.out.println("9. Eliminar aquellos aspirantes cuya experiencia sea menor a una cantidad de años especificada.");
            System.out.println("10. Presentar el promedio de edad de los aspirantes.");
            System.out.println("0. Salir");

            System.out.print("Digite el numero de la opcion: ");
            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {

                case 1:
                    System.out.println("1. Agregar hoja de vida de aspirante.");

                    System.out.println("Digite la siguiente informacion:");

                    System.out.print("Numero de Cedula aspirante: ");
                    cedula = entrada.nextLine();

                    System.out.println("Nombre aspirante: ");
                    nombre = entrada.nextLine();

                    System.out.println("Edad aspirante: ");
                    edad = entrada.nextInt();

                    System.out.println("Años de experiencia del aspirante: ");
                    experiencia = entrada.nextInt();
                    //salto de linea

                    entrada.nextLine();

                    System.out.println("Profesion del aspirante: ");
                    profesion = entrada.nextLine();

                    System.out.println("Telefono del aspirante: ");
                    telefono = entrada.nextLine();

                    //Creamos un objeto aspirante en la bd
                    Aspirantes aspirante = new Aspirantes(cedula, nombre, edad, experiencia, profesion, telefono);
                    //aqui se crea en objeto en la base de datos
                    listaAspirantes.create(aspirante);
                    System.out.println("Cedulas registradas:  ");
                    for (Aspirantes a : listaAspirantes) {
                        System.out.println("Nombre: " + a.getNombre() + " ---> " + "Cedula: " + a.getCedula());
                    }

                    break;
                case 2:
                    System.out.println("2. Mostrar cédulas de los aspirantes.");
                    System.out.println("Cédulas registradas: ");
                    List<Aspirantes> aspirantes = listaAspirantes.queryForAll();
                    for (Aspirantes a : aspirantes) {
                        System.out.println(a.getCedula());
                    }
                    break;


                case 3:
                    System.out.println("3. Mostrar la información detallada de un aspirante por medio de Cedula.");

                    System.out.println();
                    System.out.print("Ingrese el numero de cedula a buscar: ");
                    cedula = entrada.nextLine();

                    Aspirantes aspirante1 = listaAspirantes.queryForId(cedula);

                    if (aspirante1 == null) {

                        System.out.println("No existe un aspirante con esa cedula: " + cedula);

                    } else {
                        System.out.println("Cedula: " + aspirante1.getCedula());
                        System.out.println("Nombre: " + aspirante1.getNombre());
                        System.out.println("Edad: " + aspirante1.getEdad());
                        System.out.println("Profesion: " + aspirante1.getProfesion());
                        System.out.println("Telefono: " + aspirante1.getTelefono());
                        System.out.println("Años de experiencia: " + aspirante1.getAnosExperiencia());

                    }

                    break;


                case 4:

                    System.out.println("4. Buscar por nombre del aspirante.");
                    System.out.println();
                    System.out.println("Ingrese  el nombre completo del aspirante: ");
                    nombre = entrada.nextLine();


                    List<Aspirantes> aspirantesEncontrados = new ArrayList<>();
                    for (Aspirantes aspirante2 : listaAspirantes) {
                        if (aspirante2.getNombre().equalsIgnoreCase(nombre)){
                            aspirantesEncontrados.add(aspirante2);
                        }
                    }
                    // en el caso de no encontrar el nombre del aspirante

                    if (aspirantesEncontrados.isEmpty()) {
                        System.out.println("No existe un aspirante con ese nombre: " + nombre);
                    } else {
                        for (Aspirantes aspirante2 : aspirantesEncontrados) {
                            System.out.println("Cedula: " + aspirante2.getCedula());
                            System.out.println("Edad: " + aspirante2.getEdad());
                            System.out.println("Profesion: " + aspirante2.getProfesion());
                            System.out.println("Telefono: " + aspirante2.getTelefono());
                            System.out.println("Años de experiencia: " + aspirante2.getAnosExperiencia());
                            System.out.println();
                        }
                    }

                    break;


                case 5:
                    System.out.println("5. Lista de aspirantes por criterio ");


                    List<Aspirantes> AspirantesOrden = generateParameterOrderList(listaAspirantes);
                    int sizeListAspirantes = AspirantesOrden.size();

                    for (int i = 0; i < sizeListAspirantes; i++) {

                        System.out.println("*************************************************************************************************");
                        System.out.println("C.C                 " + AspirantesOrden.get(i).getCedula());
                        System.out.println("Nombre              " + AspirantesOrden.get(i).getNombre());
                        System.out.println("Edad                " + AspirantesOrden.get(i).getEdad());
                        System.out.println("Años de experiencia " + AspirantesOrden.get(i).getAnosExperiencia());
                        System.out.println("Profesión           " + AspirantesOrden.get(i).getProfesion());
                        System.out.println("Teléfono            " + AspirantesOrden.get(i).getTelefono());
                        System.out.println("*************************************************************************************************");
                    }


                    break;


                case 6:
                    System.out.println("6. Consultar el aspirante con mayor experiencia.");

                    // inicializamos una variable aspiranteMayorExperiencia en null, luego se mueve por la lista de
                    // aspirantes y compara los años de exp de cada aspirante con los años del exp del aspirante con
                    // mayor exp encontrado hasta el momento.

                    Aspirantes aspiranteMayorExperiencia = null;
                    for (Aspirantes aspirante4 : listaAspirantes) {
                        if (aspiranteMayorExperiencia == null || aspirante4.getAnosExperiencia() > aspiranteMayorExperiencia.getAnosExperiencia()) {
                            aspiranteMayorExperiencia = aspirante4;
                        }
                    }
                    if (aspiranteMayorExperiencia == null) {
                        System.out.println("No se encontraron aspirantes en la lista.");
                    } else {
                        System.out.println("El aspirante con mayor experiencia es: " + aspiranteMayorExperiencia.getNombre() + " con " + aspiranteMayorExperiencia.getAnosExperiencia() + " años de experiencia.");
                    }

                    break;


                case 7:

                    System.out.println("7. Consultar el aspirante más joven. ");

                    //inicia una variable aspiranteMasJoven en null.
                    // Luego, se mueve  a través de la lista de aspirantes y compara la edad de cada aspirante
                    // con la edad del aspirante más joven encontrado hasta el momento.

                    Aspirantes aspiranteMasJoven = null;
                    for (Aspirantes aspirante5 : listaAspirantes) {
                        if (aspiranteMasJoven == null || aspirante5.getEdad() < aspiranteMasJoven.getEdad()) {
                            aspiranteMasJoven = aspirante5;
                        }
                    }
                    if (aspiranteMasJoven == null) {
                        System.out.println("No se encontraron aspirantes en la lista.");
                    } else {
                        System.out.println("El aspirante más joven es: " + aspiranteMasJoven.getNombre() + " con " + aspiranteMasJoven.getEdad() + " años.");
                    }

                    break;


                case 8:
                    System.out.println("8. Contratar un aspirante (eliminarlo de la lista de aspirantes de la bolsa).");
                    System.out.println("Ingresa CEDULA el aspirante que contaras");
                    String cc = entrada.nextLine();
                    Aspirantes contratado = listaAspirantes.queryForId(cc);
                    listaAspirantes.delete(contratado);
                    System.out.println("El aspirante ha sido contratado.");
                    break;


                case 9:
                    System.out.println("9. Eliminar aquellos aspirantes cuya experiencia sea menor a una cantidad de años especificada.");
                    List<Aspirantes> aspirantesAEliminar = new ArrayList();
                    Iterator var4 = listaAspirantes.iterator();

                    int aniosExperienciaMinimos = 0;
                    System.out.println("Por favor ingresa la cantidad de experiencia que quieres eliminar");
                    aniosExperienciaMinimos = entrada.nextInt();

                    while (var4.hasNext()) {
                        Aspirantes aspirante_1 = (Aspirantes) var4.next();
                        if (aspirante_1.getAnosExperiencia() < aniosExperienciaMinimos) {
                            aspirantesAEliminar.add(aspirante_1);
                        }
                    }

                    int size = aspirantesAEliminar.size();
                    for (int i = 0; i < size; i++) {
                        listaAspirantes.delete(aspirantesAEliminar.get(i));
                    }
                    System.out.println("Se han eliminado " + aspirantesAEliminar.size() + " aspirantes con poca experiencia.");
                    break;


                case 10:
                    System.out.println("10. Presentar el promedio de edad de los aspirantes.");

                    int totalEdades = 0;
                    int numAspirantes = 0;

                    for (Aspirantes aspirante8 : listaAspirantes) {
                        totalEdades += aspirante8.getEdad();
                        numAspirantes++;
                    }
                    double promedioEdad = (double) totalEdades / numAspirantes;
                    System.out.println("El promedio de edad de los aspirantes es: " + promedioEdad);

                    break;


                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }

            System.out.println();

        } while (opcion != 0);


        // Cerrar la conexión
        conexion.close();


    }

    public static List<Aspirantes> ordenarLista(String field, Boolean order, Dao<Aspirantes, String> listaAspirantes) throws SQLException {
        QueryBuilder<Aspirantes, String> qb = listaAspirantes.queryBuilder();

        //genera la query que nos permita ordenar por el campo seleccionado y con el parametro
        //"forma" decir si se ordena de manera ASC = true  o DESC = false
        switch (field) {
            case "anosExperiencia":
                qb.orderBy("anosExperiencia", order);
                break;
            case "edad":
                qb.orderBy("edad", order);
                break;
            case "profesion":
                qb.orderBy("profesion", order);
                break;
            default:
                qb.orderBy("anosExperiencia", order).prepare();
        }

        List<Aspirantes> Aspirantes = qb.query();

        return Aspirantes;
    }

    public static List<Aspirantes> orderList(String field, Boolean ascending, Dao<Aspirantes, String> aspirantesDao) throws SQLException {
        QueryBuilder<Aspirantes, String> qb = aspirantesDao.queryBuilder();

        // Ordena por el campo seleccionado y con el parámetro "ascending" indica si se ordena de manera ASC o DESC
        switch (field) {
            case "anosExperiencia":
                qb.orderBy("anosExperiencia", ascending);
                break;
            case "edad":
                qb.orderBy("edad", ascending);
                break;
            case "profesion":
                qb.orderBy("profesion", ascending);
                break;
            default:
                qb.orderBy("anosExperiencia", ascending).prepare();
        }

        // Ejecuta la consulta
        List<Aspirantes> aspirantesList = qb.query();

        return aspirantesList;
    }

    public static List<Aspirantes> generateParameterOrderList(Dao<Aspirantes, String> aspirantesDao) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("----- ¿Cómo quieres ordenar la lista? -----");
        System.out.println("1. Por años de experiencia de manera ASCENDENTE");
        System.out.println("2. Por años de experiencia de manera DESCENDENTE");
        System.out.println("3. Por edad de manera ASCENDENTE");
        System.out.println("4. Por edad de manera DESCENDENTE");
        System.out.println("5. Por profesión de manera ASCENDENTE");
        System.out.println("6. Por profesión de manera DESCENDENTE");

        // Lee la opción seleccionada por el usuario
        int opcionSeleccionada = scanner.nextInt();

        // Define el campo y el orden según la opción seleccionada
        boolean ascending = true;
        String field = "anosExperiencia";

        switch (opcionSeleccionada) {
            case 1:
                field = "anosExperiencia";
                ascending = true;
                break;
            case 2:
                field = "anosExperiencia";
                ascending = false;
                break;
            case 3:
                field = "edad";
                ascending = true;
                break;
            case 4:
                field = "edad";
                ascending = false;
                break;
            case 5:
                field = "profesion";
                ascending = true;
                break;
            case 6:
                field = "profesion";
                ascending = false;
                break;
        }

        // Ejecuta el método de ordenamiento y devuelve el resultado
        List<Aspirantes> aspirantesList = orderList(field, ascending, aspirantesDao);
        return aspirantesList;
    }
}