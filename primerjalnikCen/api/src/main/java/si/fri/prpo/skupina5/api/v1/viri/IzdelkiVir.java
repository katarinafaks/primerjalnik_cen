package si.fri.prpo.skupina5.api.v1.viri;

import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina5.dtos.IzdelekDto;
import si.fri.prpo.skupina5.entitete.Izdelek;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.zrna.IzdelkiZrno;
import si.fri.prpo.skupina5.zrna.UpravljanjeIzdelkovZrno;

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

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;


    @Context
    protected UriInfo uriInfo;


    @GET
    @Operation(
            summary = "Pridobi izdelke",description = "Vrne seznam vseh izdelkov"
    )
    @APIResponses({
            @APIResponse(description = "Seznam izdelkov", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Izdelek.class)))
    })
    @Path("/getAll")
    public Response vrniIzdelke(){

        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Izdelek> izdelki= izdelkiZrno.getIzdelki(query);
        //List<Izdelek> izdelki =izdelkiZrno.getIzdelki(); // pridobi izdelke
        long izdelkiCount= izdelkiZrno.getIzdelkiCount(query);
        return Response.status(Response.Status.OK).entity(izdelki).header("X-Total-Count",izdelkiCount).build();
    }


    @GET
    @Operation(
            summary = "Pridobi izdelek",description = "Vrne izdelek s podanim id-jem"
    )
    @APIResponses({
            @APIResponse(description = "Vrnjen izdelek", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Izdelek.class))),
            @APIResponse(description = "Izdelka s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/getIzdelek/{id}")
    public Response vrniIzdelek(@PathParam("id") Integer id){
        Izdelek izdelek=izdelkiZrno.getIzdelekById(id);
        if(izdelek==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        //return Response.ok(izdelek).build();
        return Response.status(Response.Status.OK).entity(izdelek).build();
    }

    @POST
    @Operation(
            summary = "Doda izdelek",description = "Doda nov izdelek s podanimi parametri"
    )
    @APIResponses({
            @APIResponse(description = "Izdelek je dodan", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Izdelek.class)))
    })
    @Path("/dodajIzdelek")
    public Response dodajIzdelek(Izdelek izdelek) {

        return Response.status(Response.Status.CREATED).entity(izdelkiZrno.dodajIzdelek(izdelek)).build();
    }

    @DELETE
    @Operation(
            summary = "Odstrani izdelek",description = "Odstrani izdelek s podanim id"
    )
    @APIResponses({
            @APIResponse(description = "Izdelek je odstranjen", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Izdelek.class))),
            @APIResponse(description = "Izdelka s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/odstraniIzdelek/{id}")
    public Response odstraniIzdelek(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(izdelkiZrno.izbrisiIzdelek(id)).build();
    }

    @PUT
    @Operation(
            summary = "Posodobi izdelek",description = "Posodbi izdelek s podanimi id"
    )
    @APIResponses({
            @APIResponse(description = "Izdelek je posodobljen", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Izdelek.class))),
            @APIResponse(description = "Izdelka s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/posodobiIzdelek/{id}")
    public Response posodobiIzdelek(@PathParam("id") Integer id, Izdelek izdelek){
        return Response.status(Response.Status.CREATED).entity(izdelkiZrno.posodobiIzdelek(id,izdelek)).build();
    }

    @Path("/ustvariIzdelekVTrgovinoInKategorijo")
    @Operation(
            summary = "Ustvari izdelek v trgovino in kategorijo",description = "Ustvari nov izdelek s podanimi parametri in ga da v trgovino in kategorijo"
    )
    @APIResponses({
            @APIResponse(description = "Ustvarjen izdelek je dodan v trgovino in kategorijo", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Izdelek.class)))
    })
    @POST
    public Response ustvariIzdelekVTrgovinoInKategorijo(IzdelekDto izdelekDto){
        Izdelek izdelek= upravljanjeIzdelkovZrno.ustvariIzdelekVTrgovinoInKategorijo(izdelekDto);
        if(izdelek==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.CREATED).entity(izdelek).build();
    }
}
