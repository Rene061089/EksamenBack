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


    public List<WashingAssistantDTO> allWashingAssistants()
    {
        EntityManager em = getEntityManager();
        TypedQuery<WashingAssistant> query = em.createQuery("SELECT wa FROM WashingAssistant wa", WashingAssistant.class);
        List<WashingAssistant> washingAssistantList = query.getResultList();
        List<WashingAssistantDTO> washingAssistantDTOList = new ArrayList<>();

        for (int i = 0; i < washingAssistantList.size(); i++)
        {
            WashingAssistantDTO washingAssistantDTO = new WashingAssistantDTO(washingAssistantList.get(i));
            washingAssistantDTOList.add(washingAssistantDTO);
        }
        return washingAssistantDTOList;
    }

    public List<BookingDTO> getAllBookings()
    {
        EntityManager em = getEntityManager();
        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b", Booking.class);
        List<Booking> bookingList = query.getResultList();
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for (int i = 0; i < bookingList.size(); i++)
        {
            BookingDTO bookingDTO = new BookingDTO(bookingList.get(i));
            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }

    public List<BookingDTO> getAllBookingsFromUser(String id)
    {
        EntityManager em = getEntityManager();
        TypedQuery<Booking> query = em.createQuery("SELECT b FROM Booking b join b.user u where u.userName =:user", Booking.class);
        query.setParameter("user", id);

        List<Booking> bookingList = query.getResultList();
        List<BookingDTO> bookingDTOList = new ArrayList<>();

        for (int i = 0; i < bookingList.size(); i++)
        {
            BookingDTO bookingDTO = new BookingDTO(bookingList.get(i));
            bookingDTOList.add(bookingDTO);
        }
        return bookingDTOList;
    }


    public String createNewWashingAssistants(WashingAssistantDTO washingAssistantDTO)
    {
        EntityManager em = getEntityManager();
        WashingAssistant washingAssistant = new WashingAssistant(washingAssistantDTO);

        try
        {
            em.getTransaction().begin();
            em.persist(washingAssistant);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            return "Fejl i oprettelsen af washing assistent";
        }
        return "Washing assistent er oprettet velkommen til.";
    }


    public String deleteBooking(int id) throws NotFoundException
    {
        EntityManager em = getEntityManager();
        if (em.find(Booking.class, id) == null)
        {
            throw new NotFoundException(404, "Der findes ikke nogen Booking med det id");
        } else
        {
            try
            {
                Booking booking = em.find(Booking.class, id);
                em.getTransaction().begin();
                em.remove(booking);
                em.getTransaction().commit();
                return "Booking med ID: " + booking.getBooking_id() + " er fjernet fra bookinger";
            } finally
            {
                em.close();
            }
        }
    }

    public String createNewBooking(BookingDTO bookingDTO, String id)
    {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, id);
        Booking booking = new Booking(bookingDTO);

        booking.setUser(user);


        try
        {
            em.getTransaction().begin();
            em.persist(booking);
            em.getTransaction().commit();
        } catch (Exception e)
        {
            return "Fejl i oprettelsen af booking";
        }
        return "You have booked a time for wash d. " + booking.getDate() + " at time: " + booking.getTime();
    }

    public BookingDTO putAssistantOnBooking(int b_id, int wa_id) throws NotFoundException
    {


        EntityManager em = getEntityManager();
        WashingAssistant washingAssistant = em.find(WashingAssistant.class, wa_id);
        Booking booking = em.find(Booking.class, b_id);

        if (em.find(Booking.class, b_id) != null && em.find(WashingAssistant.class, wa_id) != null)
        {
            booking.getWashingAssistantList().add(washingAssistant);

            em.getTransaction().begin();
            em.merge(booking);
            em.getTransaction().commit();
        } else
        {
          throw new NotFoundException(404, "Enten findes Booking eller Assistent ikke prøv igen");
        }
        return new BookingDTO(booking);

    }


}