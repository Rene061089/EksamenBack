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
    public Response getAllUsers()
    {

        List<UserInformationDTO> u = userFacade.alluserinformations();

        return Response.ok().entity(GSON.toJson(u)).build();
    }

    @GET
    @Path("allowners")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"user", "admin"})
    public Response getAllOwners()
    {
        List<OwnerDTO> o = userFacade.allOwners();
        return Response.ok().entity(GSON.toJson(o)).build();
    }

    @GET
    @Path("allharbours")
    @Produces({MediaType.APPLICATION_JSON})

    public Response getAllHarbours()
    {
       List<HarbourDTO> harbourDTOS = userFacade.allHarbours();
       return Response.ok().entity(GSON.toJson(harbourDTOS)).build();
    }

    @GET
    @Path("allboats")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoats()
    {
        List<BoatDTO> boatDTOList = userFacade.allBoats();
        return Response.ok().entity(GSON.toJson(boatDTOList)).build();
    }

    @GET
    @Path("allwashingassistants")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getW_A()
    {
        List<WashingAssistantDTO> washingAssistantDTOList = userFacade.allWashingAssistants();
        return Response.ok().entity(GSON.toJson(washingAssistantDTOList)).build();
    }

    @GET
    @Path("alluserinfo")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllUsersInfo()
    {

        List<UserInformationDTO> u = userFacade.allinformations();
        return Response.ok().entity(GSON.toJson(u)).build();
    }

    @GET
    @Path("boatinharbour/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoatsInHarbour(@PathParam("id") int id)
    {
        List<BoatDTO> boatDTOList = userFacade.allBoatsInAHarbour(id);
        return Response.ok().entity(GSON.toJson(boatDTOList)).build();
    }

    @GET
    @Path("boatsowners/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getBoatsOwners(@PathParam("id") int id)
    {
        List<OwnerDTO> ownerDTOList = userFacade.allOwnersOfABoat(id);
        return Response.ok().entity(GSON.toJson(ownerDTOList)).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
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
    public Response getUserBookings(@PathParam("id") String id)
    {

        List<BookingDTO> bookingDTO = userFacade.getAllBookingsFromUser(id);
        return Response.ok().entity(GSON.toJson(bookingDTO)).build();
    }

    @POST
    @Path("newboat")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public Response createNewBoat(String b)
    {
        BoatDTO boatDTO = GSON.fromJson(b, BoatDTO.class);
        String result = userFacade.createBoat(boatDTO);
        return Response.ok().entity(GSON.toJson(result)).build();
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


    @PUT
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
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
    @Path("deleteboat/{id}")
    @Produces({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response deleteTheBoat(@PathParam("id") int id) throws NotFoundException
    {
        String result = userFacade.deleteBoat(id);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("putboatharbour/{id}/{id1}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed({"admin", "user"})
    public Response putBoatInHarbour(@PathParam("id") int id, @PathParam("id1") int id1) throws NotFoundException
    {

        BoatDTO result = userFacade.putBoatInHarbour(id, id1);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("putboattoowner/{id}/{id1}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response putOwnerToBoat(@PathParam("id") int id, @PathParam("id1") int id1)
    {
        OwnerDTO result = userFacade.putOwnerToBoat(id, id1);
        return Response.ok().entity(GSON.toJson(result)).build();
    }

    @PUT
    @Path("updateboatname/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
    public Response updateBoatName(@PathParam("id") int id, String bn)
    {

        BoatDTO boatDTO = GSON.fromJson(bn, BoatDTO.class);
        boatDTO.setDto_boatId(id);
        BoatDTO result = userFacade.updateBoat(boatDTO);
        return Response.ok().entity(GSON.toJson(result)).build();

    }

}