// this class belongs to the model package
package Model;

// import java packages
import java.util.Date;

/***
 * This class handles all the information that's related with the reservation
 */
public class Reserva {

    // attributes if the class
    private int idTaula;
    private String nomReserva;
    private String password;
    private Date dataReserva;
    private int numComensals;
    private boolean conectat;

    /***
     * Constructor of the class
     * @param idTaula integer with the id of the table
     * @param nomReserva String with the name of the reserve
     * @param password String with the password
     * @param dataReserva Date with the reservation date
     * @param numComensals integer with the number of people
     * @param conectat boolean with the connection state
     */
    public Reserva(int idTaula, String nomReserva, String password, Date dataReserva, int numComensals, boolean conectat){
        this.idTaula = idTaula;
        this.nomReserva = nomReserva;
        this.password = password;
        this.dataReserva = dataReserva;
        this.numComensals = numComensals;
        this.conectat = conectat;
    }


    // getters and setters

    public int getIdTaula() {
        return idTaula;
    }
    public void setIdTaula(int idTaula) {
        this.idTaula = idTaula;
    }
    public String getPassword() {
        return password;
    }
    public boolean getConectat() {
        return conectat;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNomReserva() {
        return nomReserva;
    }
    public void setNomReserva(String nomReserva) {
        this.nomReserva = nomReserva;
    }
    public Date getDataReserva() {
        return dataReserva;
    }
    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }
    public int getNumComensals() {
        return numComensals;
    }
    public void setNumComensals(int numComensals) {
        this.numComensals = numComensals;
    }
}
