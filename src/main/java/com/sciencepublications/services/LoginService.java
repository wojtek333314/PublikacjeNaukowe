package com.sciencepublications.services;


import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/login")
public class LoginService {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("logininput") String login,
                          @FormParam("passwordinput") int password) {

        System.out.println("login/password" + login + " " + password);
        String output = "Jersey say : " ;
      //  Response.temporaryRedirect(new URI("/login")).build();
        return Response.status(200).entity(output).build();
    }
}
