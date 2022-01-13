package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import errorhandling.NotFoundException;
import facades.UserFacade;
import utils.EMF_Creator;


@Path("info")
public class UserResource
{

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private final UserFacade userFacade = UserFacade.getUserFacade(EMF);
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll()
    {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
//    @RolesAllowed("admin")
    public String allUsers()
    {

        EntityManager em = EMF.createEntityManager();
        try
        {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally
        {
            em.close();
        }
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser()
    {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin()
    {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }


    @GET
    @Path("allinfo")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"user", "admin"})
    public Response getAllUsers()
    {

        List<UserInformationDTO> u = userFacade.alluserinformations();

        return Response.ok().entity(GSON.toJson(u)).build();
    }


    @GET
    @Path("allwashingassistants")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"admin", "user"})
    public Response getW_A()
    {
        List<WashingAssistantDTO> washingAssistantDTOList = userFacade.allWashingAssistants();
        return Response.ok().entity(GSON.toJson(washingAssistantDTOList)).build();
    }

    @GET
    @Path("alluserinfo")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"admin", "user"})
    public Response getAllUsersInfo()
    {

        List<UserInformationDTO> u = userFacade.allinformations();
        return Response.ok().entity(GSON.toJson(u)).build();
    }



    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"user"})
    public Response getUserInformationById(@PathParam("id") String id)
    {
        int id1 = userFacade.getUserId(id);
        UserInformationDTO uifd = userFacade.getUserInformation(id1);
        System.out.println(uifd);
        return Response.ok().entity(GSON.toJson(uifd)).build();
    }

    @GET
    @Path("booking/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("user")
    public Response getUserBookings(@PathParam("id") String id)
    {

        List<BookingDTO> bookingDTO = userFacade.getAllBookingsFromUser(id);
        return Response.ok().entity(GSON.toJson(bookingDTO)).build();
    }

    @GET
    @Path("allbookings")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response getAllBookings()
    {
        List<BookingDTO> bookingDTO = userFacade.getAllBookings();
        return Response.ok().entity(GSON.toJson(bookingDTO)).build();
    }



    @POST
    @Path("newwashingassistant")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response createNewWashingAssistant(String wa)
    {
        WashingAssistantDTO washingAssistantDTO = GSON.fromJson(wa, WashingAssistantDTO.class);
        String result = userFacade.createNewWashingAssistants(washingAssistantDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @POST
    @Path("newbooking/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("user")
    public Response createNewBooking(@PathParam("id") String u, String b)
    {
        BookingDTO bookingDTO = GSON.fromJson(b, BookingDTO.class);
        String result = userFacade.createNewBooking(bookingDTO, u);
        return Response.ok().entity(GSON.toJson(result)).build();
    }


    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"user"})
    public Response updateUserInformation(@PathParam("id") String id, String ui)
    {
        UserInformationDTO userInformationDTO = GSON.fromJson(ui, UserInformationDTO.class);
        int id1 = userFacade.getUserId(id);
        userInformationDTO.setDto_id(id1);
        UserInformationDTO result = userFacade.updateUserInfo(userInformationDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"user", "admin"})
    public Response createUser(String p) throws Exception
    {
        UserDTO userDTO = GSON.fromJson(p, UserDTO.class);
        UserInformationDTO userInformationDTO = GSON.fromJson(p, UserInformationDTO.class);
        String result = userFacade.addUser(userDTO, userInformationDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @DELETE
    @Path("{username}")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"user", "admin"})
    public Response deleteUser(@PathParam("username") String username)
    {
        String result = userFacade.deleteUser(username);
        return Response.ok().entity(GSON.toJson(result)).build();

    }


    @DELETE
    @Path("booking/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response deleteBooking(@PathParam("id") int id) throws NotFoundException
    {
        String result = userFacade.deleteBooking(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }



    @PUT
    @Path("putassistantonbooking/{id}/{id1}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"admin", "user"})
    public Response putAssistantOnBooking(@PathParam("id") int id, @PathParam("id1") int id1) throws NotFoundException
    {

        BookingDTO result = userFacade.putAssistantOnBooking(id, id1);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("updateassistant/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response updateAssistant(@PathParam("id") int id, String bn)
    {

        WashingAssistantDTO assistantDTO = GSON.fromJson(bn, WashingAssistantDTO.class);
        assistantDTO.setDto_wa_id(id);
        WashingAssistantDTO result = userFacade.updateAssistantDTO(assistantDTO);
        return Response.ok().entity(GSON.toJson(result)).build();

    }




}