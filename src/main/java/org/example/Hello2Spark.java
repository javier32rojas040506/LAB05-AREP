package org.example;

import static spark.Spark.*;

public class Hello2Spark {
    public static void main(String[] args) throws Exception {
        port(getPort());
        //API: secure(keystoreFilePath, keystorePassword, truststoreFilePath, truststorePassword);
        secure("keystore/ecikeystore2.p12", "pacho1906", null, null);
        get("/hellolocal", (req, res) -> "Hello World from 2Spark");
        get("/helloremote", (req, res) -> {
            return SecureUrlReaderForBck.readSecureUrl("https://localhost:5000/hellolocal","keystore/ecikeystore.p12");
        });

    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5050; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
