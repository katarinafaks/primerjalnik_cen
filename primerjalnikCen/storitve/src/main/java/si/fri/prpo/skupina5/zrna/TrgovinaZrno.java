package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class TrgovinaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    public List<Trgovina> getTrgovine() {

        List<Trgovina> trgovine=em.createNamedQuery("Trgovina.getAll").getResultList();

        return trgovine;
    }
}
