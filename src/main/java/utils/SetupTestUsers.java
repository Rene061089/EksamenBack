package utils;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class SetupTestUsers
{

    public static void main(String[] args)
    {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
        // CHANGE the three passwords below, before you uncomment and execute the code below
        // Also, either delete this file, when users are created or rename and add to .gitignore
        // Whatever you do DO NOT COMMIT and PUSH with the real passwords

        //CREATE USER/ADMIN
        User user = new User("Rene", "test");
        User user1 = new User("Nicklas", "test");
        User user2 = new User("Nick", "test");
        User admin = new User("sysAdmin", "test");
        User both = new User("system", "test");

        //CREATE USERINFORMATION
        UserInformation uif = new UserInformation("Male", "René Andersen", 32, 555999, user.getUserName());
        UserInformation uif1 = new UserInformation("Male", "Nick", 32, 555999,  user1.getUserName());
        UserInformation uif2 = new UserInformation("Male", "Nick", 32, 555999,  user2.getUserName());

        user.setUserInformation(uif);
        user1.setUserInformation(uif1);
        user2.setUserInformation(uif2);

        //CREATE BOAT OWNER
        Owner owner = new Owner("René", "Pæretræsdalen 37", 51913777);
        Owner owner1 = new Owner("Camilla", "Pæretræsdalen 37", 545454);

        //CREATE BOAT
        Boat boat = new Boat("Yamaha", "Japan", "ELLIOT");
        Boat boat1 = new Boat("Yamaha", "Japan", "CAMMALOT");

        //CREATE HARBOUR
        Harbour harbour = new Harbour("Hasle", "Strandvejen 1", 400);
        Harbour harbour1 = new Harbour("Rønne", "Kystvejen 2", 800);

        boat.setHarbour(harbour);
        boat1.setHarbour(harbour);
        boat1.setHarbour(harbour1);

        em.getTransaction().begin();
        em.persist(owner);
        em.persist(owner1);
        em.persist(boat);
        em.persist((boat1));
        em.persist(harbour);
        em.persist(harbour1);
        em.getTransaction().commit();


//        List<Owner> ownerList = new ArrayList<>();
//        ownerList.add(owner);

        List<Boat> boatList = new ArrayList<>();
        boatList.add(boat);

        owner.setBoatList(boatList);
//        boat.setOwnerList(ownerList);

//        BoatOwner boatOwner = new BoatOwner(owner, boat);
//        BoatOwner boatOwner1 = new BoatOwner(owner1, boat);
//        BoatOwner boatOwner2 = new BoatOwner(owner, boat1);

        em.getTransaction().begin();
        em.persist(owner);
//        em.persist(boatOwner1);
//        em.persist(boatOwner2);
        em.getTransaction().commit();




        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        user1.addRole(userRole);
        user2.addRole(adminRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(user1);
        em.persist(user2);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");

    }
}
