package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class KategorijaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;


    public List<Kategorija> getKategorije() {

        List<Kategorija> kategorije=em.createNamedQuery("Kategorija.getAll").getResultList();

        return kategorije;
    }


}
