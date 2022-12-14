package si.fri.prpo.skupina5.api.v1.viri;


import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
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
    @Path("/getAll")
    public Response vrniKategorije(){
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Kategorija> kategorije=kategorijaZrno.getKategorije(query);
        return Response.status(Response.Status.OK).entity(kategorije).build();
    }

    @GET
    @Path("/getKategorija/{id}")
    public Response vrniKategorijo(@PathParam("id") Integer id){
        Kategorija kategorija= kategorijaZrno.getKategorijaById(id);
        if(kategorija==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(kategorija).build();
    }

    @POST
    @Path("/dodajKategorijo")
    public Response dodajKategorijo(Kategorija kategorija){
        return Response.status(Response.Status.CREATED).entity(kategorijaZrno.dodajKategorijo(kategorija)).build();
    }

    @DELETE
    @Path("/odstraniKategorijo/{id}")
    public Response odstraniKategorijo(@PathParam("id") Integer id){
        return Response.status(Response.Status.OK).entity(kategorijaZrno.izbrisiKategorijo(id)).build();
    }

    @PUT
    @Path("/posodobiKategorijo/{id}")
    public Response posodobiKategorijo(@PathParam("id") Integer id, Kategorija kategorija){
        return Response.status(Response.Status.CREATED).entity(kategorijaZrno.posodobiKategorijo(id,kategorija)).build();
    }

    @GET
    @Path("/stIzdelkovInPovprecje/{KategorijaId}")
    public Response stIzdelkovInPovprecjeKategorije(@PathParam("KategorijaId") Integer KategorijaId){
        List<Integer> stIzdelkovInPovprecje = upravljanjeIzdelkovZrno.stIzdelkovInPovprecjeKategorije(KategorijaId);
        if(stIzdelkovInPovprecje==null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(stIzdelkovInPovprecje).build();

    }

}
