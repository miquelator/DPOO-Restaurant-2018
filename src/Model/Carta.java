package Model;

public class Carta {
    private int idPlat;
    private String nomPlat;
    private float preu;
    private int quantitat;
    private int semanals;
    private int totals;

    public Carta(int idPlat, String nomPlat, float preu, int quantitat, int semanals, int totals){
        this.idPlat = idPlat;
        this.nomPlat = nomPlat;
        this.preu = preu;
        this.quantitat = quantitat;
        this.semanals = semanals;
        this.totals = totals;
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
}
