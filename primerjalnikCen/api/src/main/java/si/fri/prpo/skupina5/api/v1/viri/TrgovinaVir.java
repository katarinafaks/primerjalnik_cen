package si.fri.prpo.skupina5.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
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
    @Operation(
            summary = "Pridobi trgovine",description = "Vrne seznam vseh trgovin"
    )
    @APIResponses({
            @APIResponse(description = "Seznam trgovin", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Trgovina.class)))
    })
    @Path("/getAll")
    public Response vrniTrgovine(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Trgovina> trgovine =trgovinaZrno.getTrgovine(query);
        return Response.status(Response.Status.OK).entity(trgovine).build();
    }



    @GET
    @Operation(
            summary = "Pridobi trgovino",description = "Vrne trgovino s podanim id-jem"
    )
    @APIResponses({
            @APIResponse(description = "Vrnjena trgovina", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))),
            @APIResponse(description = "Trgovine s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/getTrgovina/{id}")
    public Response vrniTrgovino(@PathParam("id") Integer id){
        Trgovina trgovina=trgovinaZrno.getTrgovinaById(id);
        if(trgovina==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(trgovina).build();
    }

    @POST
    @Operation(
            summary = "Doda trgovino",description = "Doda novo trgovino s podanimi parametri"
    )
    @APIResponses({
            @APIResponse(description = "Trgovina je dodana", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Trgovina.class)))
    })
    @Path("/dodajTrgovino")
    public Response dodajTrgovino(Trgovina trgovina){
        return Response.status(Response.Status.CREATED).entity(trgovinaZrno.dodajTrgovino(trgovina)).build();
    }

    @DELETE
    @Operation(
            summary = "Odstrani trgovino",description = "Odstrani trgovino s podanim id"
    )
    @APIResponses({
            @APIResponse(description = "Trgovina je odstranjena", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))),
            @APIResponse(description = "Trgovine s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/odstraniTrgovino/{id}")
    public Response odstraniTrgovino(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(trgovinaZrno.izbrisiTrgovina(id)).build();
    }

    @PUT
    @Operation(
            summary = "Posodobi trgovino",description = "Posodbi trgovino s podanimi id"
    )
    @APIResponses({
            @APIResponse(description = "Trgovina je posodobljena", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))),
            @APIResponse(description = "Trgovine s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/posodobiTrgovino/{id}")
    public Response posodobiTrgovino(@PathParam("id") Integer id, Trgovina trgovina){
        return Response.status(Response.Status.CREATED).entity(trgovinaZrno.posodobiTrgovino(id,trgovina)).build();
    }



    @POST
    @Operation(
            summary = "Pridobi izdelke trgovine",description = "Pridobi izdelke trgovine s podanimi id"
    )
    @APIResponses({
            @APIResponse(description = "Seznam izdelkov trgovine", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Trgovina.class))),
            @APIResponse(description = "Trgovine s tem id-jem ne najde",responseCode = "404")
    })
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
