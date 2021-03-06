package grakn.doc.test.query;

import grakn.client.GraknClient;
import grakn.common.test.server.GraknProperties;
import grakn.common.test.server.GraknSetup;
import graql.lang.Graql;
import graql.lang.query.GraqlQuery;
import graql.lang.query.GraqlCompute;
import graql.lang.query.GraqlDefine;
import graql.lang.query.GraqlUndefine;
import graql.lang.query.GraqlGet;
import graql.lang.query.GraqlDelete;
import graql.lang.query.GraqlInsert;
import graql.lang.query.GraqlCompute.Argument;
import graql.lang.pattern.Pattern;
import org.junit.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import static graql.lang.Graql.*;
import static graql.lang.Graql.Token.Compute.Algorithm.*;
import static graql.lang.Graql.Token.Order.*;
import static graql.lang.query.GraqlCompute.Argument.*;

public class GraqlJavaTest {
    static GraknClient client;
    static GraknClient.Session session ;
    GraknClient.Transaction transaction;


    @BeforeClass
    public static void loadSocialNetwork() throws Exception {
        GraknSetup.bootup();
        String address = System.getProperty(GraknProperties.GRAKN_ADDRESS);

        client = new GraknClient(address);
        session = client.session("social_network");
        GraknClient.Transaction transaction = session.transaction().write();

        try {
            byte[] encoded = Files.readAllBytes(Paths.get("files/social-network/schema.gql"));
            String query = new String(encoded, StandardCharsets.UTF_8);
            transaction.execute((GraqlQuery) Graql.parse(query));

            encoded = Files.readAllBytes(Paths.get("files/phone-calls/schema.gql"));
            query = new String(encoded, StandardCharsets.UTF_8);
            transaction.execute((GraqlQuery) Graql.parse(query));

            transaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void openTransaction(){
        transaction = session.transaction().write();
    }

    @After
    public void closeTransaction(){
        transaction.close();
    }

    @AfterClass
    public static void closeSession() throws Exception {
        session.close();
        GraknSetup.shutdown();
    }

    // TEST METHODS PLACEHOLDER
}
