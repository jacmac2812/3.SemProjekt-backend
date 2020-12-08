/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.UserDTO;
import facades.UserFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

/**
 *
 * @author jacobsimonsen
 */
@Path("users")
public class UserResource {
     private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
     
      private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
     
      @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(String user){
        UserDTO uDTO = GSON.fromJson(user, UserDTO.class);
        UserDTO uAdded = FACADE.createUser(uDTO.getName(), uDTO.getPassword(), uDTO.getEmail(), uDTO.getPhoneNumber());
        return GSON.toJson(uAdded);
    }
}
