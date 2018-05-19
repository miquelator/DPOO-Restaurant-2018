// package where it belongs
package Model;

// import java class
import java.io.Serializable;

/**
 * Class that has the carta selection information of a dish
 */
public class CartaSelection implements Serializable{
    private String nomPlat;
    private float preu;
    private int unitatsDemanades;
    private float preuTotal;

    /**
     *
     * @param nomPlat String variable with the name of the dish
     * @param preu float value of the price of the dish
     * @param unitatsDemanades integer value with the demanded units
     * @param preuTotal float value with the total price of the dish
     */
    public CartaSelection(String nomPlat, float preu, int unitatsDemanades, float preuTotal){
        this.nomPlat = nomPlat;
        this.preu = preu;
        this.unitatsDemanades = unitatsDemanades;
        this.preuTotal = preuTotal;
    }

    // getters and setters

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

    /**
     * Method that overrides supper to string to create a custom string
     * @return String representation of the class
     */
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
