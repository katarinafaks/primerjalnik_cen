package si.fri.prpo.skupina5.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.entitete.Trgovina;
import si.fri.prpo.skupina5.zrna.IzdelkiZrno;
import si.fri.prpo.skupina5.zrna.TrgovinaZrno;
import si.fri.prpo.skupina5.zrna.UpravljanjeKosariceZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("trgovina")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class TrgovinaVir {


    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;


    @Context
    protected UriInfo uriInfo;

    @GET
    @Path("/getAll")
    public Response vrniTrgovine(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Trgovina> trgovine =trgovinaZrno.getTrgovine(query);
        return Response.status(Response.Status.OK).entity(trgovine).build();
    }



    @GET
    @Path("/getTrgovina/{id}")
    public Response vrniTrgovino(@PathParam("id") Integer id){
        Trgovina trgovina=trgovinaZrno.getTrgovinaById(id);
        if(trgovina==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @POST
    @Path("/dodajTrgovino")
    public Response dodajTrgovino(Trgovina trgovina){
        return Response.status(Response.Status.CREATED).entity(trgovinaZrno.dodajTrgovino(trgovina)).build();
    }

    @DELETE
    @Path("/odstraniTrgovino/{id}")
    public Response odstraniTrgovino(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(trgovinaZrno.izbrisiTrgovina(id)).build();
    }

    @PUT
    @Path("/posodobiTrgovino/{id}")
    public Response posodobiTrgovino(@PathParam("id") Integer id, Trgovina trgovina){
        return Response.status(Response.Status.CREATED).entity(trgovinaZrno.posodobiTrgovino(id,trgovina)).build();
    }



    @POST
    @Path("/izdelkiTrgovine/{TrgovinaId}")
    public Response pridobiIzdelkeTrgovine(@PathParam("TrgovinaId") Integer trgovinaId){
        List<Izdelek> izdelki = upravljanjeKosariceZrno.pridobiIzdelkeTrgovine(trgovinaId);
        return Response.status(Response.Status.OK).entity(izdelki).build();
    }



    /*
    @GET
    @Path("{idX}")
    public Response trgovinaImaIzdelek(@PathParam("idX") Integer id, Izdelek i){

        return Response.status(Response.Status.OK).entity(upravljanjeKosariceZrno.trgovinaImaIzdelek(id,i)).build();
    }

     */


}
