package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class IzdelkiZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Izdelek> getIzdelki() {

        // implementacija
        List<Izdelek> izdelki=em.createNamedQuery("Izdelek.getAll").getResultList();
        //List<Izdelek>izdelki=null;

        return izdelki;
    }
    public List<Trgovina> getTrgovina() {

        List<Trgovina> t=em.createNamedQuery("Izdelek.getTrgovina").getResultList();

        return t;
    }
}