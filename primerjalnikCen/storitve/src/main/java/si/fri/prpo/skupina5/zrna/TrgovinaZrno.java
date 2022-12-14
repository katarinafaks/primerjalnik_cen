package si.fri.prpo.skupina5.zrna;


import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
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
public class TrgovinaZrno {

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;
    private Logger log = Logger.getLogger(TrgovinaZrno.class.getName());

    private String idZrna;

    @PostConstruct
    private void init(){
        idZrna = UUID.randomUUID().toString();
        log.info("inicializacija zrna "+ TrgovinaZrno.class.getSimpleName() + " z ID " + idZrna);

        //inicializacija virov
    }

    @PreDestroy
    private void destroy(){
        log.info("deinicializacija zrna "+ TrgovinaZrno.class.getSimpleName()+ " z ID " + idZrna);

        //zapiranje virov
    }

    public List<Trgovina> getTrgovine() {

        List<Trgovina> trgovine=em.createNamedQuery("Trgovina.getAll").getResultList();

        return trgovine;
    }
    public List<Trgovina> getTrgovine(QueryParameters query){
        return JPAUtils.queryEntities(em,Trgovina.class,query);
    }


    public Long getTrgovinaCount(QueryParameters query) {
        Long count = JPAUtils.queryEntitiesCount(em, Trgovina.class, query);
        return count;
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

    public List<Izdelek> getTrgovinaSeznamFromIme(String ime){
      return(em.createNamedQuery("Trgovina.getSeznamFromIme").setParameter("ime",ime).getResultList());
    }

    public Trgovina getTrgovinaFromIme(String ime){


        Trgovina a=null;
        try{
            Object t=em.createNamedQuery("Trgovina.getTrgovinaFromIme").setParameter("ime",ime).getSingleResult();
            a=(Trgovina) t;
        }catch(NoResultException e){
            log.info("get single result je vrnilo napako");
        }

        return a;

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
