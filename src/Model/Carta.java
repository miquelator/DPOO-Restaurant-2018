package Model;

import java.io.Serializable;

public class Carta implements Serializable{
    private int idPlat;
    private String nomPlat;
    private String tipus;
    private float preu;
    private int quantitat;
    private int semanals;
    private int totals;

    /**
     * Constructor for current class
     * @param idPlat Value for idPlat variable.
     * @param nomPlat Value for nomPlat variable.
     * @param preu Value for preu variable.
     * @param quantitat Value for quantitat variable.
     * @param semanals Value for semanals value.
     * @param totals Value for totals value.
     */
    public Carta(int idPlat, String tipus, String nomPlat, float preu, int quantitat, int semanals, int totals){
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
        this.tipus = tipus;
        this.preu = preu;
        this.quantitat = quantitat;
        this.semanals = semanals;
        this.totals = totals;
    }

    //Getters i Setters

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

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
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

    public int getSemanals() {
        return semanals;
    }

    public void setSemanals(int semanals) {
        this.semanals = semanals;
    }

    public int getTotals() {
        return totals;
    }

    public void setTotals(int totals) {
        this.totals = totals;
    }

    @Override
    public String toString() {
        return "Carta{" +
                "idPlat=" + idPlat +
                ", nomPlat='" + nomPlat + '\'' +
                ", preu=" + preu +
                ", quantitat=" + quantitat +
                ", semanals=" + semanals +
                ", totals=" + totals +
                '}';
    }
}
