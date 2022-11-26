package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class TrgovinaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;
    private Logger log = Logger.getLogger(TrgovinaZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("inicializacija zrna "+ TrgovinaZrno.class.getSimpleName());

        //inicializacija virov
    }

    @PreDestroy
    private void destroy(){
        log.info("deinicializacija zrna "+ TrgovinaZrno.class.getSimpleName());

        //zapiranje virov
    }

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

    //implementacija se ostalih CRUD operacij
    public Trgovina getTrgovinaById(int idTrgovine) {
        Trgovina t = em.find(Trgovina.class, idTrgovine);
        return t;
    }

    @Transactional
    public Trgovina dodajTrgovino(Trgovina t){
        if(t != null){
            em.persist(t);
        }
        return t;
    }

    @Transactional
    public Trgovina posodobiTrgovino(int idTrgovine, Trgovina tr){
        Trgovina t = getTrgovinaById(idTrgovine);
        if(t != null){ //preverimo da taka Trgovina sploh obstaja
            //nastavimo vrednosti
            tr.setId(t.getId());
            em.merge(tr);
        }

        return  tr;
    }

    @Transactional
    public boolean izbrisiTrgovina(int idTrgovine){
        Trgovina t = getTrgovinaById(idTrgovine);
        if(t != null) { //preverimo da taka Trgovina sploh obstaja
            em.remove(t);
            return true;
        }
        return false;
    }
}
