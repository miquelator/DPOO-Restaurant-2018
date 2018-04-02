package Model;

import java.util.ArrayList;

/**
 * Created by oriol on 1/4/2018.
 */
public class Taula extends ArrayList {
    private int idTaula;
    private int numSeients;
    private boolean ocupada;

    public Taula(int idTaula, int numSeients, boolean ocupada) {
        this.idTaula = idTaula;
        this.numSeients = numSeients;
        this.ocupada = ocupada;
    }

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
