package Model;



public class Order {
    private int idPlat;
    private String date;
    private boolean served;

    public Order(int id_plat, int id_taula, int id_reserva, int servit) {
        this.idPlat = idPlat;
        this.date = date;
        this.served = served;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }
}
