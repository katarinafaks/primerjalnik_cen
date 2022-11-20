package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class KosaricaZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    public List<Kosarica> getKosarice() {

        List<Kosarica> kosarice=em.createNamedQuery("Kosarica.getAll").getResultList();

        return kosarice;
    }
}
