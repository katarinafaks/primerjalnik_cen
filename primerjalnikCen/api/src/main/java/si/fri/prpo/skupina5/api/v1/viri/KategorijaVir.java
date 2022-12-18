package si.fri.prpo.skupina5.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.zrna.KategorijaZrno;
import si.fri.prpo.skupina5.zrna.UpravljanjeIzdelkovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("kategorija")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class KategorijaVir {


    @Inject
    private KategorijaZrno kategorijaZrno;

    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    @Context
    protected UriInfo uriInfo;


    @GET
    @Operation(
            summary = "Pridobi kategorije",description = "Vrne seznam vseh kategorij"
    )
    @APIResponses({
            @APIResponse(description = "Seznam kategorij", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kategorija.class)))
    })
    @Path("/getAll")
    public Response vrniKategorije(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kategorija> kategorije=kategorijaZrno.getKategorije(query);
        return Response.status(Response.Status.OK).entity(kategorije).build();
    }

    @GET
    @Operation(
            summary = "Pridobi kategorijo",description = "Vrne kategorijo s podanim id-jem"
    )
    @APIResponses({
            @APIResponse(description = "Vrnjena kategorija", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kategorija.class))),
            @APIResponse(description = "Kategorije s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/getKategorija/{id}")
    public Response vrniKategorijo(@PathParam("id") Integer id){
        Kategorija kategorija= kategorijaZrno.getKategorijaById(id);
        if(kategorija==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(kategorija).build();
    }

    @POST
    @Operation(
            summary = "Doda kategorijo",description = "Doda novo kategorijo s podanimi parametri"
    )
    @APIResponses({
            @APIResponse(description = "Kategorija je dodana", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = Kategorija.class)))
    })
    @Path("/dodajKategorijo")
    public Response dodajKategorijo(Kategorija kategorija){
        return Response.status(Response.Status.CREATED).entity(kategorijaZrno.dodajKategorijo(kategorija)).build();
    }

    @DELETE
    @Operation(
            summary = "Odstrani kategorijo",description = "Odstrani kategorijo s podanim id"
    )
    @APIResponses({
            @APIResponse(description = "Kategorija je odstranjena", responseCode = "204",
                    content = @Content(schema = @Schema(implementation = Kategorija.class))),
            @APIResponse(description = "Kategorije s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/odstraniKategorijo/{id}")
    public Response odstraniKategorijo(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(kategorijaZrno.izbrisiKategorijo(id)).build();
    }

    @PUT
    @Operation(
            summary = "Posodobi kategorijo",description = "Posodbi kategorijo s podanimi id"
    )
    @APIResponses({
            @APIResponse(description = "Kategorija je posodobljena", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kategorija.class))),
            @APIResponse(description = "Kategorije s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/posodobiKategorijo/{id}")
    public Response posodobiKategorijo(@PathParam("id") Integer id, Kategorija kategorija){
        return Response.status(Response.Status.CREATED).entity(kategorijaZrno.posodobiKategorijo(id,kategorija)).build();
    }

    @GET
    @Operation(
            summary = "Pridobi število izdelkov in povprečje kategorije",description = "Pridobi število izdelkov in povprečje kategorije s podanim id"
    )
    @APIResponses({
            @APIResponse(description = "Število izdelkov in povprečje kategorije", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Kategorija.class))),
            @APIResponse(description = "Kategorije s tem id-jem ne najde",responseCode = "404")
    })
    @Path("/stIzdelkovInPovprecje/{KategorijaId}")
    public Response stIzdelkovInPovprecjeKategorije(@PathParam("KategorijaId") Integer KategorijaId){
        List<Integer> stIzdelkovInPovprecje = upravljanjeIzdelkovZrno.stIzdelkovInPovprecjeKategorije(KategorijaId);
        if(stIzdelkovInPovprecje==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(stIzdelkovInPovprecje).build();

    }

}
