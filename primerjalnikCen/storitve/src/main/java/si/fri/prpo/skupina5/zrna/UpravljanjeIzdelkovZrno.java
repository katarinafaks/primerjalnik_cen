package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Izdelek;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Logger;

public class UpravljanjeIzdelkovZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(KosaricaZrno.class.getName());

    public List<Izdelek> getIzdelkiFromKategorija(String ime){
        List<Izdelek> seznam =em.createNamedQuery("Kategorija.getSeznamFromIme").setParameter("ime",ime).getResultList();

        return seznam;
    }
}
