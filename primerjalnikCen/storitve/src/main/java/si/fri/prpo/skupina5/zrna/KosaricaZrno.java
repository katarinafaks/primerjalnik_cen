package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class KosaricaZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    public List<Kosarica> getKosarice() {

        List<Kosarica> kosarice=em.createNamedQuery("Kosarica.getAll").getResultList();

        return kosarice;
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
