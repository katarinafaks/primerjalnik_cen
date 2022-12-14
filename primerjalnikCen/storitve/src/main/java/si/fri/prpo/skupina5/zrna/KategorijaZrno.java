package si.fri.prpo.skupina5.zrna;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class KategorijaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(KategorijaZrno.class.getName());

    private String idZrna;

    @PostConstruct
    private void init() {

        idZrna = UUID.randomUUID().toString();

        log.info("Inicializacija zrna " + KategorijaZrno.class.getSimpleName() + " z ID: " + idZrna);

        // Initialize sources
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + KategorijaZrno.class.getSimpleName() + " z ID: " + idZrna);

        // Deinitialize sources
    }





    public List<Kategorija> getKategorije() {

        List<Kategorija> kategorije=em.createNamedQuery("Kategorija.getAll").getResultList();

        return kategorije;
    }

    public List<Kategorija> getKategorije(QueryParameters query){
        return JPAUtils.queryEntities(em,Kategorija.class,query);
    }
    public Long getKategorijaCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Kategorija.class, query);
        return count;
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


    public List<Izdelek> getIzdelkiFromKategorija(String ime){
        List<Izdelek> seznam =em.createNamedQuery("Kategorija.getSeznamFromIme").setParameter("ime",ime).getResultList();

        return seznam;
    }
    public Kategorija getKategorijaFromIme(String ime){
        Kategorija a=null;
        try{
            Object t=em.createNamedQuery("Kategorija.getAllByIme").setParameter("ime",ime).getSingleResult();
            a=(Kategorija) t;
        }catch(NoResultException e){
            log.info("get single result je vrnilo napako");
        }

        return a;
    }


    public Kategorija getKategorijaById(int idKategorije) {
        Kategorija k = em.find(Kategorija.class, idKategorije);
        return k;
    }

    @Transactional
    public Kategorija dodajKategorijo(Kategorija i){
        if(i != null){
            em.persist(i);
        }
        return i;
    }

    @Transactional
    public Kategorija posodobiKategorijo(int idKategorije, Kategorija kat){
        Kategorija k = getKategorijaById(idKategorije);
        if(k != null){ //preverimo da taka kategorija sploh obstaja
            //nastavimo vrednosti
            kat.setId(k.getId());

            em.merge(kat);
        }

        return  kat;
    }

    @Transactional
    public boolean izbrisiKategorijo(int idKategorije){
        Kategorija k = getKategorijaById(idKategorije);
        if(k != null) { //preverimo da taka kategorija sploh obstaja
            em.remove(k);
            return true;
        }
        return false;
    }


}
