package Model;

public class ConfigJson {
    private int port_server;
    private String db_name;
    private String db_user;
    private String db_pass;
    private String db_ip;
    private int db_port;


    //Getters i Setters

    int getPort_server() {
        return port_server;
    }

    String getDb_name() {
        return db_name;
    }

    String getDb_user() {
        return db_user;
    }

    String getDb_pass() { return db_pass; }

    String getDb_ip() {
        return db_ip;
    }

    int getDb_port() {
        return db_port;
    }
}
