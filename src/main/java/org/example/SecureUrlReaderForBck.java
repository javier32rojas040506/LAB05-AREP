package org.example;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;

public class SecureUrlReaderForBck {
    public static String readSecureUrl(String url_base, String fileName) throws Exception {
        // Create a file and a password representation
        File trustStoreFile = new File(fileName);
        char[] trustStorePassword = "pacho1906".toCharArray();
        // Load the trust store, the default type is "pkcs12", the alternative is "jks"
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
        // Get the singleton instance of the TrustManagerFactory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // Itit the TrustManagerFactory using the truststore object
        tmf.init(trustStore);
        //Set the default global SSLContext so all the connections will use it
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        SSLContext.setDefault(sslContext);
        // We can now read this URL
        return readURL(url_base);
    }
    public static String readURL(String url) throws Exception {
        URL site = new URL(url);
        String result = null;
        System.out.println("===============================================================================");
        System.out.println(site);
        System.out.println("===============================================================================");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(site.openStream()))) {
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                result = inputLine;
            }
        } catch (IOException x) {
            System.err.println(x);
            x.printStackTrace();
        }
        return result;
    }
}
