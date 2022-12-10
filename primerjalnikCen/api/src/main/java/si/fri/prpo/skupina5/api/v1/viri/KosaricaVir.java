package si.fri.prpo.skupina5.api.v1.viri;


import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.zrna.KosaricaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("kosarica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KosaricaVir {


    @Inject
    KosaricaZrno kosaricaZrno;

    @Path("{id}")
    public Response vrniKosarico(@PathParam("id") Integer id){
        Kosarica kosarica = kosaricaZrno.getKosaricaById(id);
        if(kosarica==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @POST
    public Response dodajKosarico(Kosarica kosarica){
        return Response.status(Response.Status.CREATED).entity(kosaricaZrno.dodajKosarico(kosarica)).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniKosarico(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(kosaricaZrno.izbrisiKosarico(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiKosarico(@PathParam("id") Integer id, Kosarica kosarica){
        return Response.status(Response.Status.CREATED).entity(kosaricaZrno.posodobiKosarico(id,kosarica)).build();
    }
}
