package si.fri.prpo.skupina5.api.v1.viri;


import si.fri.prpo.skupina5.entitete.Trgovina;
import si.fri.prpo.skupina5.zrna.IzdelkiZrno;
import si.fri.prpo.skupina5.zrna.TrgovinaZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("trgovina")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TrgovinaVir {


    @Inject
    private TrgovinaZrno trgovinaZrno;

    @GET
    @Path("{id}")
    public Response vrniTrgovino(@PathParam("id") Integer id){
        Trgovina trgovina=trgovinaZrno.getTrgovinaById(id);
        if(trgovina==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @POST
    public Response dodajTrgovino(Trgovina trgovina){
        return Response.status(Response.Status.CREATED).entity(trgovinaZrno.dodajTrgovino(trgovina)).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniTrgovino(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(trgovinaZrno.izbrisiTrgovina(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiTrgovino(@PathParam("id") Integer id, Trgovina trgovina){
        return Response.status(Response.Status.CREATED).entity(trgovinaZrno.posodobiTrgovino(id,trgovina)).build();
    }

}
