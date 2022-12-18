package si.fri.prpo.skupina5.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina5.dtos.IzdelekDto;
import si.fri.prpo.skupina5.dtos.KosaricaDto;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.entitete.Trgovina;
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
    @Operation(
            summary = "Pridobi košarice",description = "Vrne vse košarice"
    )
    @APIResponses({
            @APIResponse(description = "Seznam košaric", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class)))
    })
    @Path("/getAll")
    public Response vrniKosarice(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kosarica> kosarice=kosaricaZrno.getKosarice(query);
        return Response.status(Response.Status.OK).entity(kosarice).build();
    }


    @GET
    @Operation(
            summary = "Pridobi košarico",description = "Vrne košarico s podanim id-jem"
    )
    @APIResponses({
            @APIResponse(description = "Vrnjena košarica", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))),
            @APIResponse(description = "Kosarice s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/getKosarica/{id}")
    public Response vrniKosarico(@PathParam("id") Integer id){
        Kosarica kosarica = kosaricaZrno.getKosaricaById(id);
        if(kosarica==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @POST
    @Operation(
            summary = "Doda košarico",description = "Doda novo košarico s podanimi parametri"
    )
    @APIResponses({
            @APIResponse(description = "Košarica je dodana", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Kosarica.class)))
    })
    @Path("/dodajKosarico")
    public Response dodajKosarico(Kosarica kosarica){
        return Response.status(Response.Status.CREATED).entity(kosaricaZrno.dodajKosarico(kosarica)).build();
    }

    @DELETE
    @Operation(
            summary = "Odstrani košarico",description = "Odstrani košarico s podanim id"
    )
    @APIResponses({
            @APIResponse(description = "Košarica je odstranjena", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))),
            @APIResponse(description = "Košarice s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/odstraniKosarico/{id}")
    public Response odstraniKosarico(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(kosaricaZrno.izbrisiKosarico(id)).build();
    }

    @PUT
    @Operation(
            summary = "Posodobi košarico",description = "Posodbi košarico s podanimi id"
    )
    @APIResponses({
            @APIResponse(description = "Košarica je posodobljena", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))),
            @APIResponse(description = "Košarice s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/posodobiKosarico/{id}")
    public Response posodobiKosarico(@PathParam("id") Integer id, Kosarica kosarica){
        return Response.status(Response.Status.CREATED).entity(kosaricaZrno.posodobiKosarico(id,kosarica)).build();
    }


    @GET
    @Operation(
            summary = "Preveri košarico",description = "Preveri če ima trgovina vse izdelke, ki so v košarici"
    )
    @APIResponses({
            @APIResponse(description = "Preverjena košarica", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class)))
    })
    @Path("/preveriKosarico")
    public Response preveriKosarico(KosaricaDto kosaricaDto){
        return Response.status(Response.Status.OK).entity(upravljanjeKosariceZrno.preveriKosarico(kosaricaDto)).build();
    }

    @GET
    @Operation(
            summary = "Vrne ceno košarice",description = "Vrne ceno košarice v trgovini s podanim id"
    )
    @APIResponses({
            @APIResponse(description = "Cena košarice", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))),
            @APIResponse(description = "Trgovine s tem id-jem ne najde",responseCode = "404")
    })
    @Path("cenaKosarice/trgovina/{TrgovinaId}")
    public Response cenaKosarice(@PathParam("TrgovinaId") Integer TrgovinaId,Kosarica kosarica){
        int cena=upravljanjeKosariceZrno.IzracunajCenoKosariceByTrg(kosarica,TrgovinaId);
        return Response.status(Response.Status.OK).entity(cena).build();
    }

    @PUT
    @Operation(
            summary = "Doda izdelek v košarico",description = "Dodaj izdelek s podanim id v košarico"
    )
    @APIResponses({
            @APIResponse(description = "Izdelek je dodan v košarico", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))),
            @APIResponse(description = "Izdelka s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/dodajIzdelek/{id}/izdelki/{IzdelekId}")
    public Response dodajIzdelekVKosarico(@PathParam("IzdelekId") Integer IzdelekId,@PathParam("id") Integer id){
        Kosarica kosarica=upravljanjeKosariceZrno.dodajIzdelekVKosarico(id,IzdelekId);
        if(kosarica==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(kosarica).build();
    }

    @DELETE
    @Operation(
            summary = "Odstrani izdelek iz košarice",description = "Odstrani izdelek s podanim id iz košarice"
    )
    @APIResponses({
            @APIResponse(description = "Izdelek je odstranjen iz košarice", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Kosarica.class))),
            @APIResponse(description = "Izdelka s tem id-jem ne najde v košarici",responseCode = "404")
    })
    @Path("/odstraniIzdelek/{id}/izdelki/{IzdelekId}")
    public Response odstraniIzdelekIzKosarice(@PathParam("IzdelekId") Integer IzdelekId,@PathParam("id") Integer id){
        Kosarica kosarica=upravljanjeKosariceZrno.odstraniIzdelekizKosarice(id,IzdelekId);
        return Response.status(Response.Status.OK).entity(kosarica).build();
    }
}
