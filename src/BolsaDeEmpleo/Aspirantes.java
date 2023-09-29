package BolsaDeEmpleo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "aspirantes")
public class Aspirantes {

    @DatabaseField(id = true, columnName = "cedula")
    private String cedula;

    @DatabaseField(columnName = "nombre")
    private String nombre;

    @DatabaseField(columnName = "edad")
    private int edad;

    @DatabaseField(columnName = "anosExperiencia")
    private int anosExperiencia;

    @DatabaseField(columnName = "profesion")
    private String profesion;

    @DatabaseField(columnName = "telefono")
    private String telefono;

    //Constructor vacio para OMRlite
    public Aspirantes() {
    }

    //Constructor que inicializa la informacion
    public Aspirantes(String cedula, String nombre, int edad, int anosExperiencia, String profesion, String telefono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.anosExperiencia = anosExperiencia;
        this.profesion = profesion;
        this.telefono = telefono;
    }

    // get nos da el numero de la cedula
    public String getCedula() {
        return cedula;
    }

    // set permite cambiar el numero de cedula
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }
}
