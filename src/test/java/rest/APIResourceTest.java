package rest;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


class APIResourceTest
{
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;


    User u1;
    User u2;

    Role userRole;
    Role adminRole;

    UserInformation uif1;
    UserInformation uif2;

    Owner owner;
    Owner owner1;


    List<Boat> boatList;

    Boat boat;
    Boat boat1;

    Harbour harbour;
    Harbour harbour1;

    WashingAssistant washingAssistant;
    WashingAssistant washingAssistant1;
    WashingAssistant washingAssistant2;

    Booking booking;
    Booking booking1;
    Booking booking2;

    static HttpServer startServer()
    {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass()
    {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    private static String securityToken;

    private static void login(String role, String password)
    {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        System.out.println("TOKEN ---> " + securityToken);
    }

    @AfterAll
    public static void closeTestServer()
    {

        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();

    }

    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();

        userRole = new Role("user");
        adminRole = new Role("admin");

        u1 = new User("Rene", "test");
        u2 = new User("Camilla", "test");


        uif1 = new UserInformation("Male", "Rene Andersen", 32, 669, u1.getUserName());
        uif2 = new UserInformation("Female", "Camilla jartoft", 30, 555, u2.getUserName());

        u1.setUserInformation(uif1);
        u2.setUserInformation(uif2);

        owner = new Owner("Rene", "Pæredalen", 5151);
        owner1 = new Owner("Camilla", "Pæredalen", 3636);

        boat = new Boat("Tesla", "Canada", "Elon");
        boat1 = new Boat("Viper", "Amarica", "Snake");

        List<Boat> boatList = new ArrayList<>();
        owner.getBoatList().add(boat);


        harbour = new Harbour("Hasle", "Havnegade", 100);

        harbour.setBoats(boatList);
        boat.setHarbour(harbour);
        boat1.setHarbour(harbour1);

        washingAssistant = new WashingAssistant("Kenneth", "Dansk", 2, 150);
       washingAssistant1 = new WashingAssistant("Aslan", "Finsk", 1, 100);
        washingAssistant2 = new WashingAssistant("Sigurd", "Svensk", 7, 175);

        booking = new Booking(0.5,"4/5/2022", "05:00");
        booking1 = new Booking(0.5,"3/6/2022", "06:00");
        booking2 = new Booking(0.5,"3/6/2022", "06:00");

        booking.setUser(u1);
        booking1.setUser(u1);
        booking2.setUser(u2);

        try
        {
            em.getTransaction().begin();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();
            em.createNativeQuery("DELETE from user_roles").executeUpdate();
            em.createNativeQuery("DELETE from owner_boat").executeUpdate();
            em.createNamedQuery("booking.deleteAllRows").executeUpdate();
            em.createNamedQuery("users.deleteAllRows").executeUpdate();
            em.createNamedQuery("user_information.deleteAllRows").executeUpdate();
            em.createNamedQuery("role.deleteAllRows").executeUpdate();
            em.createNamedQuery("boat.deleteAllRows").executeUpdate();
            em.createNamedQuery("harbour.deleteAllRows").executeUpdate();
            em.createNamedQuery("owner.deleteAllRows").executeUpdate();
            em.createNamedQuery("washing_assistant.deleteAllRows").executeUpdate();
            em.createNamedQuery("car.deleteAllRows").executeUpdate();
            u1.addRole(userRole);
            u2.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(u1);
            em.persist(u2);
            em.persist(owner);
            em.persist(owner1);
            em.persist(boat);
            em.persist(boat1);
            em.persist(harbour);
            em.persist(washingAssistant);
            em.persist(washingAssistant1);
            em.persist(washingAssistant2);
            em.persist(booking);
            em.persist(booking1);
            em.persist(booking2);
            em.getTransaction().commit();
        } finally
        {
            em.close();
        }

    }

    @AfterEach
    void tearDown()
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE from user_roles").executeUpdate();
        em.createNativeQuery("DELETE from owner_boat").executeUpdate();
        em.createNamedQuery("booking.deleteAllRows").executeUpdate();
        em.createNamedQuery("users.deleteAllRows").executeUpdate();
        em.createNamedQuery("user_information.deleteAllRows").executeUpdate();
        em.createNamedQuery("role.deleteAllRows").executeUpdate();
        em.createNamedQuery("boat.deleteAllRows").executeUpdate();
        em.createNamedQuery("harbour.deleteAllRows").executeUpdate();
        em.createNamedQuery("owner.deleteAllRows").executeUpdate();
        em.createNamedQuery("washing_assistant.deleteAllRows").executeUpdate();
        em.createNamedQuery("car.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void testServerIsUp()
    {
        System.out.println("Testing is server UP");
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    void getInfoForAll()
    {
//        login("user", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/info/" ).then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("msg", equalTo("Hello anonymous"));
    }


    @Test
    void createNewWashingAssistant()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .with().body(new WashingAssistant("Kell", "Japansk", 200, 50))
                .post("/info/newwashingassistant/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void getAllWashingAssistants()
    {
        login("Rene","test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .get("/info/allwashingassistants")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_wa_id", hasSize(3));
    }

    @Test
    void getAllUserBooking()
    {
        login("Rene","test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .get("/info/booking/" + u1.getUserName() )
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("booking", hasSize(2))
                .body("dto_booking_id", hasItem(booking.getBooking_id()))
                .body("dto_booking_id", hasItem(booking1.getBooking_id()))
//                .body("dto_duration", hasItem(booking.getDuration()))
//                .body("dto_duration", hasItem(booking1.getDuration()))
                .body("dto_date", hasItem(booking.getDate()))
                .body("dto_date", hasItem(booking1.getDate()))
                .body("dto_time", hasItem(booking.getTime()))
                .body("dto_time", hasItem(booking1.getTime()));
    }

    @Test
    void getAllBookings()
    {
        login("Rene","test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .get("/info/allbookings" )
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("allbookings", hasSize(3));

    }

    @Test
    void deleteBookings()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .delete("/info/booking/" + booking.getBooking_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

@Test
    void getAllOwners()
{
    login("Rene","test");
    given()
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-access-token", securityToken)
            .get("/info/allowners")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("dto_owner_id", hasSize(2));
}

    @Test
    void getBoatsInHarbour()
    {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .get("/info/boatinharbour/" + harbour.getHarbour_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_boatId", hasItem(boat.getBoat_id()))
                .body("dto_brand", hasItem(boat.getBrand()))
                .body("dto_make", hasItem(boat.getMake()))
                .body("dto_name", hasItem(boat.getName()));
    }

    @Test
    void getBoatOwners()
    {
        login("Camilla", "test");
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .get("/info/boatsowners/" + boat.getBoat_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("dto_owner_id", hasItem(owner.getOwner_id()))
                .body("dto_name", hasItem(owner.getName()))
                .body("dto_address", hasItem(owner.getAddress()))
                .body("dto_phone", hasItem(owner.getPhone()));
    }

    @Test
    void deleteBoat()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .delete("/info/deleteboat/" + boat.getBoat_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void updateBoatName()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .with().body(boat)
                .put("/info/updateboatname/" + boat.getBoat_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void putBoatToOwner()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .put("/info/putboattoowner/" + owner1.getOwner_id()
                + "/" + boat.getBoat_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void putBoatInHarbour()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .put("/info/putboatharbour/" + boat.getBoat_id()
                        + "/" + harbour.getHarbour_id())
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

    @Test
    void createNewBoat()
    {
        login("Camilla", "test");
        given()
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-access-token", securityToken)
                .with().body(new Boat("JAJA", "Japan", "UHI"))
                .post("/info/newboat/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode());

    }


}