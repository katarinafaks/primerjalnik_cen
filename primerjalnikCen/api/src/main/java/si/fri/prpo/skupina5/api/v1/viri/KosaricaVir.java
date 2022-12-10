package si.fri.prpo.skupina5.api.v1.viri;


import si.fri.prpo.skupina5.dtos.IzdelekDto;
import si.fri.prpo.skupina5.dtos.KosaricaDto;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.zrna.KosaricaZrno;
import si.fri.prpo.skupina5.zrna.UpravljanjeKosariceZrno;

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
    private KosaricaZrno kosaricaZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;

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


    @GET
    public Response preveriKosarico(KosaricaDto kosaricaDto){
        return Response.status(Response.Status.OK).entity(upravljanjeKosariceZrno.preveriKosarico(kosaricaDto)).build();
    }

    @GET
    @Path("/trgovina/{TrgovinaId}")
    public Response cenaKosarice(@PathParam("TrgovinaId") Integer TrgovinaId,Kosarica kosarica){
        int cena=upravljanjeKosariceZrno.IzracunajCenoKosariceByTrg(kosarica,TrgovinaId);
        return Response.status(Response.Status.OK).entity(cena).build();
    }

    @POST //AL PA @PUT IDK
    @Path("{id}/izdelek/{IzdelekId}")
    public Response dodajIzdelekVKosarico(@PathParam("IzdelekId") Integer IzdelekId,@PathParam("id") Integer id){
        Kosarica kosarica=upravljanjeKosariceZrno.dodajIzdelekVKosarico(id,IzdelekId);
        if(kosarica==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(kosarica).build();
    }

    @POST //AL PA @PUT IDK
    @Path("{id}/izdelek/{IzdelekId}")
    public Response odstraniIzdelekIzKosarice(@PathParam("IzdelekId") Integer IzdelekId,@PathParam("id") Integer id){
        Kosarica kosarica=upravljanjeKosariceZrno.odstraniIzdelekizKosarice(id,IzdelekId);
        return Response.status(Response.Status.CREATED).entity(kosarica).build();
    }
}
