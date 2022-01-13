package facades;

import dtos.BookingDTO;
import dtos.WashingAssistantDTO;
import entities.*;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


class UserFacadeTest
{

    private static EntityManagerFactory emf;
    private static UserFacade facade;

    User u1;
    User u2;

    Role userRole = new Role("user");

    UserInformation uif1;
    UserInformation uif2;


    WashingAssistant washingAssistant;
    WashingAssistant washingAssistant1;

    Booking booking;
    Booking booking1;


    @BeforeAll
    public static void setUpClass()
    {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);


    }

    @BeforeEach
    void setUp()
    {
        EntityManager em = emf.createEntityManager();
        u1 = new User("Rene", "test");
        u2 = new User("Nicklas", "test");

        uif1 = new UserInformation("Male", "Rene Andersen", 32, 669, u1.getUserName());
        uif2 = new UserInformation("Male", "Nicklas Hansen", 30, 555, u2.getUserName());

        u1.setUserInformation(uif1);
        u2.setUserInformation(uif2);


        washingAssistant = new WashingAssistant("Kenneth", "Dansk", 2, 150);
        washingAssistant1 = new WashingAssistant("Aslan", "Finsk", 1, 100);

        booking = new Booking(0.5, "4/5/2022", "05:00");
        booking1 = new Booking(0.5, "3/6/2022", "06:00");

        booking.setUser(u1);
        booking1.setUser(u1);

        em.getTransaction().begin();
        em.createNativeQuery("DELETE from user_roles").executeUpdate();
        em.createNamedQuery("users.deleteAllRows").executeUpdate();
        em.createNamedQuery("user_information.deleteAllRows").executeUpdate();
        em.createNamedQuery("role.deleteAllRows").executeUpdate();
        u1.addRole(userRole);
        u2.addRole(userRole);
        em.persist(userRole);
        em.persist(u1);
        em.persist(u2);
        em.persist(washingAssistant);
        em.persist(washingAssistant1);
        em.persist(booking);
        em.persist(booking1);
        em.getTransaction().commit();


    }

    @AfterEach
    void tearDown()
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE from user_roles").executeUpdate();
        em.createNativeQuery("DELETE from booking_washing_assistant").executeUpdate();
        em.createNamedQuery("booking.deleteAllRows").executeUpdate();
        em.createNamedQuery("users.deleteAllRows").executeUpdate();
        em.createNamedQuery("user_information.deleteAllRows").executeUpdate();
        em.createNamedQuery("role.deleteAllRows").executeUpdate();
        em.createNamedQuery("washing_assistant.deleteAllRows").executeUpdate();
        em.createNamedQuery("car.deleteAllRows").executeUpdate();

        em.getTransaction().commit();
    }


    @Test
    void allWashingAssistants()
    {
        int expected = 2;
        int actual = facade.allWashingAssistants().size();

        assertEquals(expected, actual);

    }

    @Test
    void allBookingsFromOnUser()
    {
        int expected = 2;
        int actual = facade.getAllBookingsFromUser(u1.getUserName()).size();

        assertEquals(expected, actual);

    }

    @Test
    void createNewWashingAssistant()
    {
        WashingAssistantDTO washingAssistantDTO = new WashingAssistantDTO("Kurt Larsen", "Bornholms", 25, 200);
        String expected = ("Washing assistent er oprettet velkommen til.");
        String actual = facade.createNewWashingAssistants(washingAssistantDTO);
        assertEquals(expected, actual);

    }

    @Test
    void createNewBooking()
    {
        BookingDTO bookingDTO = new BookingDTO(1.5, "2/2/2022", "08:00");
        String expected = ("You have booked a time for wash d. " + bookingDTO.getDto_date() + " at time: " + bookingDTO.getDto_time());
        String actual = facade.createNewBooking(bookingDTO, u1.getUserName());
        assertEquals(expected, actual);

    }

    @Test
    void allBookings()
    {
        int expected = 2;
        int actual = facade.getAllBookings().size();

        assertEquals(expected, actual);

    }

    @Test
    void deleteBooking() throws NotFoundException
    {
        String expected = ("Booking med ID: " + booking.getBooking_id() + " er fjernet fra bookinger");
        String actual = facade.deleteBooking(booking.getBooking_id());
        assertEquals(expected, actual);
    }


    @Test
    void putAssistantOnBooking() throws NotFoundException
    {

        int booking = booking1.getBooking_id();
        int assistant = washingAssistant.getWa_id();
        String expected = (booking1.getDate());
        String actual = String.valueOf(facade.putAssistantOnBooking(booking, assistant).getDto_date());

        assertEquals(expected, actual);
    }





}
