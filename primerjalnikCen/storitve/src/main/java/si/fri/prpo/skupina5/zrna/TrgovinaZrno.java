package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class TrgovinaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    public List<Trgovina> getTrgovine() {

        List<Trgovina> trgovine=em.createNamedQuery("Trgovina.getAll").getResultList();

        return trgovine;
    }

    public List<Trgovina> getAllTrgovineWithCriteria(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trgovina> q= cb.createQuery(Trgovina.class);
        Root<Trgovina> i = q.from(Trgovina.class);
        q.select(i);

        TypedQuery<Trgovina> query = em.createQuery(q);
        List<Trgovina> seznam = query.getResultList();

        return seznam;
    }
}
