package facades;

import dtos.*;
import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class UserFacade
{

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade()
    {
    }


    public static UserFacade getUserFacade(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException
    {
        EntityManager em = emf.createEntityManager();
        User user;
        try
        {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password))
            {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally
        {
            em.close();
        }
        return user;
    }


    public List<UserInformationDTO> alluserinformations()
    {
        EntityManager em = getEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> users = query.getResultList();

        List<UserInformationDTO> userInformationDTOList = new ArrayList();
        for (User user : users)
        {
            if (user.getRoleList().get(0).getRoleName().equals("user") && user.getUserInformation() != null)
            {
                UserInformationDTO userInformationDTO = new UserInformationDTO(user.getUserInformation());
                userInformationDTOList.add(userInformationDTO);
            }
        }
        return userInformationDTOList;
    }


    public UserInformationDTO getUserInformation(int id)
    {

        EntityManager em = emf.createEntityManager();
        try
        {
            return new UserInformationDTO(em.find(UserInformation.class, id));
        } finally
        {
            em.close();
        }
    }


    public int getUserId(String id)
    {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Integer> query = em.createQuery("SELECT u.userInformation.id FROM User u JOIN u.userInformation i where i.user =:user ", Integer.class);
        query.setParameter("user", id);
        int username = query.getSingleResult();

        return username;
    }


    public UserInformationDTO updateUserInfo(UserInformationDTO uif)
    {
        EntityManager em = emf.createEntityManager();
        UserInformation userinfo = em.find(UserInformation.class, uif.getDto_id());

        if (uif.getDto_age() == 0)
        {
            userinfo.setAge(userinfo.getAge());
        } else
        {
            userinfo.setAge(uif.getDto_age());
        }

        if (uif.getDto_gender().equals(""))
        {
            userinfo.setGender(userinfo.getGender());
        } else
        {
            userinfo.setGender(uif.getDto_gender());
        }


        if (uif.getDto_name().equals(""))
        {
            userinfo.setName(userinfo.getName());
        } else
        {
            userinfo.setName(uif.getDto_name());
        }

        if (uif.getDto_phone() == 0)
        {
            userinfo.setPhone(userinfo.getPhone());
        } else
        {
            userinfo.setPhone(uif.getDto_phone());
        }

        em.getTransaction().begin();
        em.merge(userinfo);
        em.getTransaction().commit();
        return new UserInformationDTO(uif);
    }


    public String addUser(UserDTO udto, UserInformationDTO idto) throws Exception
    {
        EntityManager em = emf.createEntityManager();
        if (em.find(User.class, udto.getDto_userName()) != null)
        {
            return "403, brugeren eksistere allerede";
        } else
        {
            User user = new User(udto);
            UserInformation userInformation = new UserInformation(idto.getDto_gender(), idto.getDto_name(), idto.getDto_age(), idto.getDto_phone(), user.getUserName());
            user.setUserInformation(userInformation);
            Role userRole = new Role("user");
            user.addRole(userRole);
            try
            {
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            } catch (Exception e)
            {
                return ("Fejl i oprettelse af bruger.. Kontakt support & tjek evt. informationerne er korrekt indtastet");
            }
        }

        return "Du kan nu logge ind med dit brugernavn " + udto.getDto_userName() + " + dit password";
    }

    public String deleteUser(String username)
    {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, username);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
        return "Brugeren med id " + user.getUserInformation().getId() + " Er fjernet";
    }

    public List<UserInformationDTO> allinformations()
    {
        EntityManager em = getEntityManager();
        TypedQuery<UserInformation> query = em.createQuery("SELECT u FROM UserInformation u", UserInformation.class);
        List<UserInformation> users = query.getResultList();
        List<UserInformationDTO> userInformationDTOS = new ArrayList<>();
        for (int i = 0; i < users.size(); i++)
        {
            UserInformationDTO userDTO = new UserInformationDTO(users.get(i));
            userInformationDTOS.add(userDTO);
        }
        return userInformationDTOS;
    }

    public List<OwnerDTO> allOwners()
    {
        EntityManager em = getEntityManager();
        TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
        List<Owner> ownerList = query.getResultList();
        List<OwnerDTO> ownerDTOList = new ArrayList<>();

        for (int i = 0; i < ownerList.size(); i++)
        {
            OwnerDTO ownerDTO = new OwnerDTO(ownerList.get(i));
            ownerDTOList.add(ownerDTO);
        }

        return ownerDTOList;
    }

    public List<HarbourDTO> allHarbours()
    {
        EntityManager em = getEntityManager();
        TypedQuery<Harbour> query = em.createQuery("SELECT h FROM Harbour h", Harbour.class);
        List<Harbour> harbourList = query.getResultList();
        List<HarbourDTO> harbourDTOList = new ArrayList<>();

        for (int i = 0; i < harbourList.size(); i++)
        {
            HarbourDTO harbourDTO = new HarbourDTO(harbourList.get(i));
            harbourDTOList.add(harbourDTO);
        }

        return harbourDTOList;
    }

    public List<BoatDTO> allBoats()
    {
        EntityManager em = getEntityManager();
        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b", Boat.class);
        List<Boat> boatList = query.getResultList();
        List<BoatDTO> boatDTOList = new ArrayList<>();

        for (int i = 0; i < boatList.size(); i++)
        {
            BoatDTO boatDTO = new BoatDTO(boatList.get(i));
            boatDTOList.add(boatDTO);
        }
        return boatDTOList;
    }

    public List<BoatDTO> allBoatsInAHarbour(int id)
    {
        EntityManager em = getEntityManager();
//        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b JOIN b.harbour h WHERE h.harbour_id =: id", Boat.class);
        TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b where b.harbour.harbour_id =:id", Boat.class);
        query.setParameter("id", id);
        List<Boat> boatList = query.getResultList();
        List<BoatDTO> boatDTOList = new ArrayList<>();

        for (int i = 0; i < boatList.size(); i++)
        {
            BoatDTO boatDTO = new BoatDTO(boatList.get(i));
            boatDTOList.add(boatDTO);
        }
        return boatDTOList;
    }

    public List<OwnerDTO> allOwnersOfABoat(int id)
    {
        EntityManager em = getEntityManager();
        TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o JOIN o.boatList b WHERE b.boat_id =:id ", Owner.class);
        query.setParameter("id", id);
        List<Owner> ownerList = query.getResultList();
        List<OwnerDTO> ownerDTOList = new ArrayList<>();

        for (int i = 0; i < ownerList.size(); i++)
        {
            OwnerDTO ownerDTO = new OwnerDTO(ownerList.get(i));
            ownerDTOList.add(ownerDTO);
        }
        return ownerDTOList;
    }

    public String createBoat(BoatDTO boatDTO)
    {
        EntityManager em = getEntityManager();
        Boat boat = new Boat(boatDTO);

        try
        {
            em.getTransaction().begin();
            em.persist(boat);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            return "Fejl i oprettelsen af båd";
        }
        return "Båden er oprettet velkommen til.";
    }


    public String deleteBoat(int id) throws NotFoundException
    {
        EntityManager em = getEntityManager();
        if (em.find(Boat.class, id) == null)
        {
            throw new NotFoundException(404, "Båden med " + id + " findes ikke");
        } else
        {
            try
            {
                Boat boat = em.find(Boat.class, id);
                em.getTransaction().begin();
                em.remove(boat);
                em.getTransaction().commit();
                return "Båden med ID: " + boat.getBoat_id() + " er fjernet";
            }finally
            {
                em.close();
            }

        }
    }


    public BoatDTO putBoatInHarbour(int b_id, int h_id) throws NotFoundException
    {
        EntityManager em = getEntityManager();
        if (em.find(Boat.class, b_id) == null)
        {
            throw new NotFoundException(404, "Båden med id " + b_id + " findes ikke");
        } else if (em.find(Harbour.class, h_id) == null)
        {
            throw new NotFoundException(404, "Havnen med id " + h_id + " findes ikke");
        } else
        {
            Boat boat = em.find(Boat.class, b_id);
            Harbour harbour = em.find(Harbour.class, h_id);

            boat.setHarbour(harbour);
            try
            {
                em.getTransaction().begin();
                em.merge(boat);
                em.getTransaction().commit();
            } finally
            {
                em.close();
            }
        return new BoatDTO(boat);
        }
    }

    public OwnerDTO putOwnerToBoat(int o_id, int b_id)
    {

        EntityManager em = getEntityManager();
        Owner owner = em.find(Owner.class, o_id);
        Boat boat = em.find(Boat.class, b_id);

        owner.getBoatList().add(boat);

        em.getTransaction().begin();
        em.persist(owner);
        em.getTransaction().commit();


        return new OwnerDTO(owner);

    }

    public BoatDTO updateBoat(BoatDTO boatDTO)
    {
        EntityManager em = getEntityManager();
        Boat boat = em.find(Boat.class, boatDTO.getDto_boatId());

        boat.setName(boatDTO.getDto_name());

        em.getTransaction().begin();
        em.merge(boat);
        em.getTransaction().commit();

        return new BoatDTO(boat);
    }


//    public BoatOwnerDTO UpdateBoatOwner(Owner owner, Boat boat)
//    {
//        EntityManager em = getEntityManager();
//        boat = em.find(Boat.class, boat.getBoat_id());
//        owner = em.find(Owner.class, owner.getOwner_id());
//        BoatOwner boatOwner = em.find(BoatOwner.class, boat.getBoat_id());
//
//        boatOwner.setOwner(owner.getOwner_id());
//
//
//        em.getTransaction().begin();
//        em.merge(boatOwner);
//        em.getTransaction().commit();
//
//        return new BoatOwnerDTO(boatOwner);
//    }

//    public String deleteOwnerFromBoat(int o_id)
//    {
//        EntityManager em = getEntityManager();
//        BoatOwner boatOwner = em.find(BoatOwner.class, o_id);
//
//        em.getTransaction().begin();
//        em.remove(boatOwner);
//        em.getTransaction().commit();
//        return "Båden med ID: " + boatOwner.getOwner().getOwner_id() + "er fjernet";
//    }

}