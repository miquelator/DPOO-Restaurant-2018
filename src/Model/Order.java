package Model;

/**
 * This class contains all the information of an order
 */
public class Order {
    private int idPlat;
    private String nomPlat;
    private int idTaula;
    private String hora;
    private boolean served;
    private int idComanda;

    /**
     * Constructor of the class
     * @param idPlat integer with id of the dish
     * @param nomPlat name of the dish
     * @param idTaula string with the integer with id from the table
     * @param hora string with the hour of the order
     * @param served boolean with the status of the order (served or not)
     * @param idComanda integer with id of the order
     */
    public Order(int idPlat, String nomPlat, int idTaula, String hora, boolean served, int idComanda) {
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
        this.idTaula = idTaula;
        this.hora = hora;
        this.served = served;
        this.idComanda = idComanda;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public int getIdTaula() {
        return idTaula;
    }

    public void setIdTaula(int idTaula) {
        this.idTaula = idTaula;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }
}
