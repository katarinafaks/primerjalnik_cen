package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class KategorijaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    public List<Kategorija> getKategorije() {

        List<Kategorija> kategorije=em.createNamedQuery("Kategorija.getAll").getResultList();

        return kategorije;
    }
    public List<Kategorija> getAllKategorijeWithCriteria(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Kategorija> q= cb.createQuery(Kategorija.class);
        Root<Kategorija> i = q.from(Kategorija.class);
        q.select(i);

        TypedQuery<Kategorija> query = em.createQuery(q);
        List<Kategorija> seznam = query.getResultList();

        return seznam;
    }

}
