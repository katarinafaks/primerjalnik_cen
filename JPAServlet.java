package si.fri.prpo.skupina5;

import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Trgovina;
import si.fri.prpo.skupina5.zrna.CDIZrno;
import si.fri.prpo.skupina5.zrna.IzdelkiZrno;

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
    private CDIZrno cdiZrno;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //resp.getWriter().println("Hello");
        List<Trgovina> trgovine = izdelkiZrno.getTrgovina();
        List<Izdelek> izdelki = izdelkiZrno.getIzdelki();
        ListIterator<Izdelek> listIterator = izdelki.listIterator();
        int i=0;
        while(listIterator.hasNext()) {
            Izdelek item = izdelki.get(i);
            resp.getWriter().print(item.getIme() + " ");
            resp.getWriter().print(item.getOpis() + " ");
            resp.getWriter().print(item.getCena() + " ");
            resp.getWriter().print(item.getKategorija().getIme() + " ");
            resp.getWriter().println(item.getTrgovina().getIme());
            listIterator.next();
            i++;
        }
        i=0;
        ListIterator<Trgovina> listIterator2 = trgovine.listIterator();
        while(listIterator2.hasNext()){
            Trgovina trg = trgovine.get(i);
            resp.getWriter().println(trg.getIme());
            i++;
            listIterator2.next();
        }
        List<Izdelek> izdelkiCriteria = cdiZrno.getAllIzdelkiWithCriteria();
        i=0;
        ListIterator<Izdelek> listIterator3 = izdelkiCriteria.listIterator();
        while(listIterator3.hasNext()){
            resp.getWriter().println(izdelkiCriteria.get(i).getIme());
            i++;
            listIterator3.next();
        }
    }
}
