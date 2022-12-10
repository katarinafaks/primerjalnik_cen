package si.fri.prpo.skupina5.api.v1.viri;




import si.fri.prpo.skupina5.entitete.Izdelek;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina5.zrna.IzdelkiZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;


@Path("izdelki")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class IzdelkiVir {



    @Inject
    private IzdelkiZrno izdelkiZrno;



    @GET
    public Response vrniIzdelke(){

        List<Izdelek> izdelki =izdelkiZrno.getIzdelki(); // pridobi izdelke
        return Response.status(Response.Status.OK).entity(izdelki).build();
    }


    @GET
    @Path("{id}")
    public Response vrniIzdelek(@PathParam("id") Integer id){
        Izdelek izdelek=izdelkiZrno.getIzdelekById(id);
        if(izdelek==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //return Response.ok(izdelek).build();
        return Response.status(Response.Status.OK).entity(izdelek).build();
    }

    @POST
    public Response dodajIzdelek(Izdelek izdelek) {

        return Response.status(Response.Status.CREATED).entity(izdelkiZrno.dodajIzdelek(izdelek)).build();
    }

    @DELETE
    @Path("{id}")
    public Response odstraniIzdelek(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(izdelkiZrno.izbrisiIzdelek(id)).build();
    }

    @PUT
    @Path("{id}")
    public Response posodobiIzdelek(@PathParam("id") Integer id, Izdelek izdelek){
        return Response.status(Response.Status.CREATED).entity(izdelkiZrno.posodobiIzdelek(id,izdelek)).build();
    }

}