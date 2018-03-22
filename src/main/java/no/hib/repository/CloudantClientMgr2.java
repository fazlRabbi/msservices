package no.hib.repository;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by adrianrutle on 03.02.2018.
 */
import java.util.Map.Entry;
import java.util.Set;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.CouchDbException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CloudantClientMgr2 {

    private static CloudantClient cloudant = null;
    private static Database db = null;

    private static String databaseName = "sample_nosql_db";

    private static String user = "admin";
    private static String password = "pass";

    private static void initClient() {
        if (cloudant == null) {
            synchronized (CloudantClientMgr2.class) {
                if (cloudant != null) {
                    return;
                }
                cloudant = createClient();

            }
        }
    }

    private static CloudantClient createClient() {

        try {
            System.out.println("Connecting to Cloudant : " + user);
            /*
            CloudantClient client = ClientBuilder.account(user)
                    .username(user)
                    .password(password)
                    .build(); */
            CloudantClient client;			
				client = ClientBuilder.url(new URL("http://localhost:9080")) // "https://b6768e5f-ff3f-4b05-8894-f2445cc2d875-bluemix:9a63dfff816095013e2278d65df3d1aa0171ad5a6f03e5c5de111ed5ece9dc4b@b6768e5f-ff3f-4b05-8894-f2445cc2d875-bluemix.cloudant.com"
				        .username(user)
				        .password(password)
				        .build();
			
            return client;
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect to repository", e);
        }
    }

    public static Database getDB() {
        if (cloudant == null) {
            initClient();
        }

        if (db == null) {
            try {
                db = cloudant.database(databaseName, true);
                System.out.println("Database created successfully! ");
            } catch (Exception e) {
                throw new RuntimeException("DB Not found", e);
            }
        }
        return db;
    }

    private CloudantClientMgr2() {
    }
}