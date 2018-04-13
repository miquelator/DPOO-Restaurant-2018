package Model;

import java.util.Date;

public class Reserva {
    private int idTaula;
    private String nomReserva;
    private String password;
    private Date dataReserva;
    private int numComensals;
    private boolean conectat;

    public Reserva(int idTaula, String nomReserva, String password, Date dataReserva, int numComensals, boolean conectat){
        this.idTaula = idTaula;
        this.nomReserva = nomReserva;
        this.password = password;
        this.dataReserva = dataReserva;
        this.numComensals = numComensals;
        this.conectat = conectat;
    }

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
