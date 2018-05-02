package Model;

import java.io.Serializable;

public class CartaSelection implements Serializable{
    private String nomPlat;
    private float preu;
    private int unitatsDemanades;
    private float preuTotal;

    public CartaSelection(String nomPlat, float preu, int unitatsDemanades, float preuTotal){
        this.nomPlat = nomPlat;
        this.preu = preu;
        this.unitatsDemanades = unitatsDemanades;
        this.preuTotal = preuTotal;
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

    public int getUnitatsDemanades() {
        return unitatsDemanades;
    }

    public void setUnitatsDemanades(int unitatsDemanades) {
        this.unitatsDemanades = unitatsDemanades;
    }

    public float getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(float preuTotal) {
        this.preuTotal = preuTotal;
    }

    @Override
    public String toString() {
        return "CartaSelection{" +
                "nomPlat='" + nomPlat + '\'' +
                ", preu=" + preu +
                ", unitatsDemanades=" + unitatsDemanades +
                ", preuTotal=" + preuTotal +
                '}';
    }
}
