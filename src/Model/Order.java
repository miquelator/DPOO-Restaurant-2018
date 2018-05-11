package Model;


import java.sql.Date;

public class Order {
    private int idPlat;
    private int idTaula;
    private java.sql.Date hora;
    private boolean served;
    private int idComanda;

    public Order(int idPlat, int idTaula, Date hora, boolean served, int idComanda) {
        this.idPlat = idPlat;
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

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
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
}
