package si.fri.prpo.skupina5.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina5.dtos.IzdelekDto;
import si.fri.prpo.skupina5.dtos.KosaricaDto;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.zrna.KosaricaZrno;
import si.fri.prpo.skupina5.zrna.UpravljanjeKosariceZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("kosarica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KosaricaVir {


    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;


    @Context
    protected UriInfo uriInfo;


    @GET
    @Path("/getAll")
    public Response vrniKosarice(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kosarica> kosarice=kosaricaZrno.getKosarice(query);
        return Response.status(Response.Status.OK).entity(kosarice).build();
    }


    @GET
    @Path("/getKosarica/{id}")
    public Response vrniKosarico(@PathParam("id") Integer id){
        Kosarica kosarica = kosaricaZrno.getKosaricaById(id);
        if(kosarica==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @POST
    @Path("/dodajKosarico")
    public Response dodajKosarico(Kosarica kosarica){
        return Response.status(Response.Status.CREATED).entity(kosaricaZrno.dodajKosarico(kosarica)).build();
    }

    @DELETE
    @Path("/odstraniKosarico/{id}")
    public Response odstraniKosarico(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(kosaricaZrno.izbrisiKosarico(id)).build();
    }

    @PUT
    @Path("/posodobiKosarico/{id}")
    public Response posodobiKosarico(@PathParam("id") Integer id, Kosarica kosarica){
        return Response.status(Response.Status.CREATED).entity(kosaricaZrno.posodobiKosarico(id,kosarica)).build();
    }


    @GET
    @Path("/preveriKosarico")
    public Response preveriKosarico(KosaricaDto kosaricaDto){
        return Response.status(Response.Status.OK).entity(upravljanjeKosariceZrno.preveriKosarico(kosaricaDto)).build();
    }

    @GET
    @Path("cenaKosarice/trgovina/{TrgovinaId}")
    public Response cenaKosarice(@PathParam("TrgovinaId") Integer TrgovinaId,Kosarica kosarica){
        int cena=upravljanjeKosariceZrno.IzracunajCenoKosariceByTrg(kosarica,TrgovinaId);
        return Response.status(Response.Status.OK).entity(cena).build();
    }

    @PUT
    @Path("/dodajIzdelek/{id}/izdelki/{IzdelekId}")
    public Response dodajIzdelekVKosarico(@PathParam("IzdelekId") Integer IzdelekId,@PathParam("id") Integer id){
        Kosarica kosarica=upravljanjeKosariceZrno.dodajIzdelekVKosarico(id,IzdelekId);
        if(kosarica==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(kosarica).build();
    }

    @DELETE
    @Path("/odstraniIzdelek/{id}/izdelki/{IzdelekId}")
    public Response odstraniIzdelekIzKosarice(@PathParam("IzdelekId") Integer IzdelekId,@PathParam("id") Integer id){
        Kosarica kosarica=upravljanjeKosariceZrno.odstraniIzdelekizKosarice(id,IzdelekId);
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }
}
