package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;
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

    public List<Kategorija> getAllKategorijeWithCriteria(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Kategorija> q= cb.createQuery(Kategorija.class);
        Root<Kategorija> i = q.from(Kategorija.class);
        q.select(i);

        TypedQuery<Kategorija> query = em.createQuery(q);
        List<Kategorija> seznam = query.getResultList();

        return seznam;
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

    public List<Kosarica> getAllKosariceWithCriteria(){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Kosarica> q= cb.createQuery(Kosarica.class);
        Root<Kosarica> i = q.from(Kosarica.class);
        q.select(i);

        TypedQuery<Kosarica> query = em.createQuery(q);
        List<Kosarica> seznam = query.getResultList();

        return seznam;
    }
}
