package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CategoryDTO;
import dto.ChuckDTO;
import dto.CombinedDTO;
import dto.DadDTO;
import dto.OnSaleDTO;
import dto.ProductDTO;
import dto.SearchDTO;
import dto.SwabiDTO;
import entities.Role;
import entities.User;
import java.io.IOException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import fetchers.JokeFetcher;
import fetchers.ProductFetcher;
import javax.ws.rs.PathParam;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * @author lam@cphbusiness.dk
 */
@Path("info")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    @Context
    private UriInfo context;

    private static EntityManagerFactory emf;

    @Context
    SecurityContext securityContext;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }

    @GET //fjern
    @Produces(MediaType.APPLICATION_JSON)
    @Path("jokes")
    @RolesAllowed("user")
    public String getJokes() throws IOException {
        JokeFetcher jf = new JokeFetcher();

        CombinedDTO cDTO = jf.getJokes();

        return GSON.toJson(cDTO);
    }

    @GET //fjern
    @Produces(MediaType.APPLICATION_JSON)
    @Path("swabi")
    public String getSwabi() throws IOException {
        JokeFetcher jf = new JokeFetcher();

        SwabiDTO sDTO = jf.getSwabi();

        return GSON.toJson(sDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("product/{search}")
    public String getProduct(@PathParam("search") String search) throws IOException {
        ProductFetcher pf = new ProductFetcher();

        SearchDTO sDTO = pf.getProduct(search);

        return GSON.toJson(sDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products/{category}")
    public String getProducts(@PathParam("category") String category) throws IOException {
        ProductFetcher pf = new ProductFetcher();

        CategoryDTO cDTO = pf.getProducts(category);

        return GSON.toJson(cDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products/onsale")
    public String getProductsOnSale() throws IOException {
        ProductFetcher pf = new ProductFetcher();

        OnSaleDTO osDTO = pf.getProductsOnSale();

        return GSON.toJson(osDTO);
    }

    @GET // fjern til sidst efter lavet opret user metode
    @Produces(MediaType.APPLICATION_JSON)
    @Path("populate")
    public String populate() throws IOException {

        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        User user = new User("user", "hello");
        User admin = new User("admin", "with");
        User both = new User("user_admin", "you");

        em.getTransaction().begin();
        Role userRole = new Role("user");
        Role adminRole = new Role("admin");
        user.addRole(userRole);
        admin.addRole(adminRole);
        both.addRole(userRole);
        both.addRole(adminRole);
        em.persist(userRole);
        em.persist(adminRole);
        em.persist(user);
        em.persist(admin);
        em.persist(both);
        em.getTransaction().commit();

        return GSON.toJson("hej");
    }

}
