// package where it belongs
package Model;

// import java class
import java.io.Serializable;

/**
 * Class that has the information of every dish ordered to show in the status window
 */
public class CartaStatus implements Serializable {
    private String nomPlat;
    private boolean servit;

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    private int idPlat;

    public boolean isServit() {
        return servit;
    }

    public void setServit(boolean servit) {
        this.servit = servit;
    }

    public String getNomPlat() {

        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }
}



