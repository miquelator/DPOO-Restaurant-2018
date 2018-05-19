// package where it belongs
package Model;

/**
 * This class has the information of config.json file
 */
public class ConfigJson {

    // attributes
    private int port_server;
    private String db_name;
    private String db_user;
    private String db_pass;
    private String db_ip;
    private int db_port;


    //Getters and Setters

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
