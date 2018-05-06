// package where it bellongs
package Model;

// import java packages
import java.util.ArrayList;

/**
 * This class contains all the data related with tables
 */
public class Taula extends ArrayList {

    // attributes of the class
    private int idTaula;
    private int numSeients;
    private boolean ocupada;

    /***
     * Constructor with parameters of the class
     * @param idTaula integer with the id of the table
     * @param numSeients intger with the number of seats of a table
     * @param ocupada boolean with the state of the table
     */
    public Taula(int idTaula, int numSeients, boolean ocupada) {
        this.idTaula = idTaula;
        this.numSeients = numSeients;
        this.ocupada = ocupada;
    }

    // getters and setters
    public int getIdTaula() {
        return idTaula;
    }
    public void setIdTaula(int idTaula) {
        this.idTaula = idTaula;
    }
    public int getNumSeients() {
        return numSeients;
    }
    public void setNumSeients(int numSeients) {
        this.numSeients = numSeients;
    }
    public boolean isOcupada() {
        return ocupada;
    }
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

}
