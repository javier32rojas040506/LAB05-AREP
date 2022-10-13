package org.example;
import static spark.Spark.*;
/**
 * Hello world!
 *
 */

public class HelloWorld {
    public static void main(String[] args) {
        port(getPort());
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure(getKeyStoreFile(), getPassword(), null, null);
        get("/hellolocal", (req, res) -> "Hello World from HelloWorld");
        get("/helloremote", (req, res) -> {
            return SecureUrlReaderForBck.readSecureUrl(getDNS(), getKeyTrustFile());
        });

    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost)
    }

    static String getKeyStoreFile() {
        if (System.getenv("KS") != null) {
            return System.getenv("KS");
        }
        return "keystore/ecikeystore.p12"; //returns default KEY STORAGE
    }

    static String getKeyTrustFile() {
        if (System.getenv("KT") != null) {
            return System.getenv("KT");
        }
        return "keystore/ecikeystore2.p12"; //returns default KEY STORAGE TO TRUST
    }

    static String getDNS() {
        if (System.getenv("DNS") != null) {
            return System.getenv("DNS");
        }
        return "https://localhost:5050/hellolocal"; //returns default end point or DNS
    }

    static String getPassword(){
        if (System.getenv("PASSWORD") != null) {
            return System.getenv("PASSWORD");
        }
        return "pacho1906"; //returns default password
    }
}
