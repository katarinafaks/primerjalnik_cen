package si.fri.prpo.skupina5.servlet;

import si.fri.prpo.skupina5.dtos.IzdelekDto;
import si.fri.prpo.skupina5.dtos.KosaricaDto;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.entitete.Trgovina;
import si.fri.prpo.skupina5.zrna.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

@WebServlet("/servlet")
public class JPAServlet extends HttpServlet {

    @Inject
    private IzdelkiZrno izdelkiZrno;

    @Inject
    private KategorijaZrno kategorijaZrno;

    @Inject
    private TrgovinaZrno trgovinaZrno;

    @Inject
    private KosaricaZrno kosaricaZrno;

    @Inject
    private UpravljanjeKosariceZrno upravljanjeKosariceZrno;
    @Inject
    private UpravljanjeIzdelkovZrno upravljanjeIzdelkovZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        Trgovina t=trgovinaZrno.getTrgovinaById(1);
        List<Izdelek> izdelki=t.getSeznamIzdelkov();


        resp.getWriter().println("Trgovina pridobljena po imenu:");
        Trgovina trgovina=trgovinaZrno.getTrgovinaFromIme("Lidl");
        resp.getWriter().println(trgovina.getIme());

        Kosarica kos=kosaricaZrno.getKosaricaById(1);
        resp.getWriter().println("Elementi kosarice " + kos.getIme());
        for(Izdelek i:kos.getIzdelki()){
            resp.getWriter().println(i.getIme() + " cena: " + i.getCena());
        }
        KosaricaDto kosDto=new KosaricaDto();
        kosDto.setIzdelki(izdelki);
        kosDto.setTrgovina(t);
        resp.getWriter().println("Preverjamo kosarico, ce sploh lahko izracunamo ceno");
        boolean x=upravljanjeKosariceZrno.preveriKosarico(kosDto);
        if(x){
            resp.getWriter().println("Skupna cena kosarice " + kos.getIme());
            int cena=upravljanjeKosariceZrno.IzracunajCenoKosariceByTrg(kos,t.getId());
            resp.getWriter().println(cena);
        }else{
            resp.getWriter().println("Ni vrei");
        }
        Kosarica prej=kosaricaZrno.getKosaricaById(1);
        resp.getWriter().println("V Prva kosarica:");
        List<Izdelek> ss1=prej.getIzdelki();
        for(Izdelek ii:ss1){
            resp.getWriter().println(ii.getIme());
        }

        Izdelek i=izdelkiZrno.getIzdelekById(1);
        Kosarica nova=upravljanjeKosariceZrno.dodajIzdelekVKosarico(1,i.getId());
        resp.getWriter().println("V Prvi Kosarico sem dodala izdelek z id=1");
        List<Izdelek> ss=nova.getIzdelki();
        for(Izdelek ii:ss){
            resp.getWriter().println(ii.getIme());
        }

        Kategorija k =kategorijaZrno.getKategorijaFromIme("Pisala");
        resp.getWriter().println("Izracunano st elementov in povprecje kategorije " + k.getIme());
        for(Izdelek izdelek:k.getSeznamIzdelkov()){
            resp.getWriter().println(izdelek.getIme() + ", cena: " + izdelek.getCena());
        }
        List<Integer> stIzdInAverage=upravljanjeIzdelkovZrno.stIzdelkovInPovprecjeKategorije(k.getId());
        resp.getWriter().print("Stevilo elementov : " + stIzdInAverage.get(0) + ", ");
        resp.getWriter().println(" Povprecje elementov kategorije:" + stIzdInAverage.get(1));

        //resp.getWriter().println(k.getOpis());
        //resp.getWriter().println(k.getIme());
        //resp.getWriter().println(k.getId());


        IzdelekDto izdelekDto=new IzdelekDto();
        izdelekDto.setImeIzdelka("Sir");
        izdelekDto.setOpisIzdelka("Hrana iz krave");
        izdelekDto.setCena(7);
        izdelekDto.setImeKategorije("Mlecni izdelki");
        izdelekDto.setImeTrgovine("Merkator");
        Izdelek izdto=upravljanjeIzdelkovZrno.ustvariIzdelekVTrgovinoInKategorijo(izdelekDto);
        List<Izdelek> xx=trgovinaZrno.getTrgovinaSeznamFromIme("Merkator");
        //List<Izdelek> xx=izdto.getTrgovina().getSeznamIzdelkov();

        Kosarica kosarica=upravljanjeKosariceZrno.dodajIzdelekVKosarico(1,izdto.getId());
        resp.getWriter().println("Elementi kosarice 1: " + kosarica.getIme());
        for(Izdelek el:kosarica.getIzdelki()){
            resp.getWriter().println(el.getIme());
        }
        resp.getWriter().println("Dodan nov izdelek v trgovino Merkator:");
        for(Izdelek el :xx){
            resp.getWriter().println(el.getIme());
        }

        Kosarica kosarica1=upravljanjeKosariceZrno.odstraniIzdelekizKosarice(1,izdto.getId());
        resp.getWriter().println("Elementi kosarice 1 po odstranitvi: " + izdto.getIme());
        for(Izdelek el:kosarica1.getIzdelki()){
            resp.getWriter().println(el.getIme());
        }

    }
}
