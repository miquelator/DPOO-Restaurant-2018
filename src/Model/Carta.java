package Model;

import java.io.Serializable;

public class Carta implements Serializable{
    private int idPlat;
    private String nomPlat;
    private float preu;
    private int quantitat;

    public Carta(int idPlat, String nomPlat, float preu, int quantitat){
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
        this.preu = preu;
        this.quantitat = quantitat;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }
}
