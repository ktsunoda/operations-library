package io.berkeley.operations.health;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@SuppressWarnings("UnusedDeclaration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/check")
public class CheckService {

    @GET
    @Path("/ping")
    public String ping() {
        return "pong";
    }
}
