package facades;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.*;
import errorhandling.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    Owner owner;
    Owner owner1;

    List<Owner> ownerList;

    List<Boat> boatList;

    List<Harbour> harbourList;

    Boat boat;
    Boat boat1;

    Harbour harbour;
    Harbour harbour1;


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

        owner = new Owner("Rene", "Pæredalen", 5151);
        owner1 = new Owner("Camilla", "Pæredalen", 3636);

        boat = new Boat("Tesla", "Canada", "Elon");
        boat1 = new Boat("Viper", "Amarica", "Snake");

        harbour = new Harbour("Hasle", "Havnegade", 100);
        harbour1 = new Harbour("Rønne", "Havnegade", 200);


        harbour.setBoats(boatList);
        boat.setHarbour(harbour);

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
            em.persist(owner);
            em.persist(owner1);
            em.persist(boat);
            em.persist(boat1);
            em.persist(harbour);
            em.persist(harbour1);
            em.getTransaction().commit();


            List<Boat> boatList = new ArrayList<>();
            boatList.add(boat);
            owner.setBoatList(boatList);


            em.getTransaction().begin();
            em.persist(owner);
            em.getTransaction().commit();





    }

    @AfterEach
    void tearDown()
    {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("DELETE from user_roles").executeUpdate();
        em.createNativeQuery("DELETE from owner_boat").executeUpdate();
        em.createNamedQuery("users.deleteAllRows").executeUpdate();
        em.createNamedQuery("user_information.deleteAllRows").executeUpdate();
        em.createNamedQuery("role.deleteAllRows").executeUpdate();
        em.createNamedQuery("boat.deleteAllRows").executeUpdate();
        em.createNamedQuery("harbour.deleteAllRows").executeUpdate();
        em.createNamedQuery("owner.deleteAllRows").executeUpdate();
        em.getTransaction().commit();
    }


    // den virker ikke helt det er ikke sikkert Rene lander på array plads 1.
    @Test
    void allOwners()
    {
//        String expected = ("Rene");
//        String expected1 = ("Camilla");
        int expected = 2;

        ownerList = new ArrayList<>();
        ownerList.add(owner);
        ownerList.add(owner1);

        int actual = facade.allOwners().size();
//        String actual1 = facade.allOwners().get(0).getDto_name();
        assertEquals(expected, actual);
//        assertEquals(expected1, actual1);
    }

    @Test
    void allBoatsInAHarbour()
    {
        int h_id = harbour.getHarbour_id();
        String expected = (boat.getBoat_id() + boat.getName() );
//        String expected1 = (boat1.getName());

        String actual = facade.allBoatsInAHarbour(h_id).get(0).getDto_boatId() + facade.allBoatsInAHarbour(h_id).get(0).getDto_name();
//      String actual1 = facade.allBoatsInAHarbour(h_id).get(1).getDto_name();
        assertEquals(expected, actual);

    }

    @Test
    void getOwnersOfBoat()
    {
        int b_id = boat.getBoat_id();
        String expected = owner.getName();
        String actual = facade.allOwnersOfABoat(b_id).get(0).getDto_name();

        assertEquals(expected, actual);
    }

    @Test
    void allHarbours()
    {
        int expected = 2;

        harbourList = new ArrayList<>();
        harbourList.add(harbour);
        harbourList.add(harbour1);

        int actual = facade.allHarbours().size();
//        String actual1 = facade.allOwners().get(0).getDto_name();
        assertEquals(expected, actual);
//        assertEquals(expected1, actual1);
    }

    @Test
    void allBoats()
    {

        int expected = 2;

        boatList = new ArrayList<>();
        boatList.add(boat);
        boatList.add(boat1);

        int actual = facade.allBoats().size();
//        String actual1 = facade.allOwners().get(0).getDto_name();
        assertEquals(expected, actual);
//        assertEquals(expected1, actual1);

    }


    @Test
    void createBoat()
    {
        BoatDTO boatDTO = new BoatDTO("Yamaha", "Kina", "wrume");
        String expected = ("Båden er oprettet velkommen til.");
        String actual = facade.createBoat(boatDTO) ;
        assertEquals(expected, actual);

    }

    @Test
    void deleteBoat() throws NotFoundException
    {
        String expected = ("Båden med ID: " + boat.getBoat_id() + " er fjernet");
        String actual = facade.deleteBoat(boat.getBoat_id());
        assertEquals(expected, actual);
    }

    @Test
    void putBoatInHarbour() throws NotFoundException
    {

        int boat = boat1.getBoat_id();
        int harbour = harbour1.getHarbour_id();
        String expected = String.valueOf((boat));
        String actual = String.valueOf(facade.putBoatInHarbour(boat, harbour).getDto_boatId());

        assertEquals(expected, actual);

    }

    @Test
    void putOwnerToBoat()
    {

        int owner = owner1.getOwner_id();
        int boat = boat1.getBoat_id();
        String expected = (owner1.getName());
        String actual = String.valueOf(facade.putOwnerToBoat(owner,boat).getDto_name());

        assertEquals(expected, actual);
    }


   }