package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Izdelek;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class CDIZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    public List<Izdelek> getAllIzdelkiWithCriteria(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Izdelek> q= cb.createQuery(Izdelek.class);
        Root<Izdelek> i = q.from(Izdelek.class);
        q.select(i);

        TypedQuery<Izdelek> query = em.createQuery(q);
        List<Izdelek> seznam = query.getResultList();
        return seznam;
    }
}
