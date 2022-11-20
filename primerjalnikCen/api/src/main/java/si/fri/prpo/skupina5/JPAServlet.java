package si.fri.prpo.skupina5;

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
    private CDIZrno cdiZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //resp.getWriter().println("Hello");
        //List<Trgovina> trgovine = izdelkiZrno.getTrgovina();
        List<Izdelek> izdelki = izdelkiZrno.getIzdelki();
        ListIterator<Izdelek> listIterator = izdelki.listIterator();
        int i=0;
        resp.getWriter().println("Izdelki:");
        while(listIterator.hasNext()) {
            Izdelek item = izdelki.get(i);
            resp.getWriter().print(item.getIme() + ", ");
            resp.getWriter().print(item.getOpis() + ", ");
            resp.getWriter().print(item.getCena() + ", ");
            resp.getWriter().print(item.getKategorija().getIme() + ", ");
            resp.getWriter().print(item.getTrgovina().getIme() + ", ");
            resp.getWriter().println(item.getKosarica().getIme());
            listIterator.next();
            i++;
        }
        List<Trgovina> trgovine = trgovinaZrno.getTrgovine();
        ListIterator<Trgovina> listIterator2 = trgovine.listIterator();
        i=0;
        resp.getWriter().println("Trgovine:");
        while(listIterator2.hasNext()){
            resp.getWriter().println(trgovine.get(i).getIme());
            //resp.getWriter().println(trgovine.get(i).getSeznamIzdelkov().get(i).getIme());
            i++;
            listIterator2.next();
        }
        List<Izdelek> izdelkiCriteria = cdiZrno.getAllIzdelkiWithCriteria();
        i=0;
        ListIterator<Izdelek> listIterator3 = izdelkiCriteria.listIterator();
        resp.getWriter().println("Z Criteria API:");
        while(listIterator3.hasNext()){
            resp.getWriter().print(izdelkiCriteria.get(i).getIme()+", ");
            resp.getWriter().print(izdelkiCriteria.get(i).getOpis() + ", ");
            resp.getWriter().print(izdelkiCriteria.get(i).getKategorija().getIme() + ", ");
            resp.getWriter().print(izdelkiCriteria.get(i).getTrgovina().getIme() + ", ");
            resp.getWriter().println(izdelkiCriteria.get(i).getKosarica().getIme());
            i++;
            listIterator3.next();
        }

        List<Kategorija> kategorije = kategorijaZrno.getKategorije();
        ListIterator<Kategorija> listIterator4 = kategorije.listIterator();
        i=0;
        resp.getWriter().println("Kategorije:");
        while(listIterator4.hasNext()){
            resp.getWriter().print(kategorije.get(i).getIme()+", ");
            resp.getWriter().println(kategorije.get(i).getOpis());
           // resp.getWriter().println(kategorije.get(i).getSeznamIzdelkov());
            i++;
            listIterator4.next();
        }
        List<Kosarica> kosarice = kosaricaZrno.getKosarice();
        ListIterator<Kosarica> listIterator5 = kosarice.listIterator();
        resp.getWriter().println("Kosarice:");
        i=0;
        while(listIterator5.hasNext()){
            resp.getWriter().print(kosarice.get(i).getIme() + ", ");
            //resp.getWriter().println(kosarice.get(i).getIzdelki());
            i++;
            listIterator5.next();
        }

    }
}
