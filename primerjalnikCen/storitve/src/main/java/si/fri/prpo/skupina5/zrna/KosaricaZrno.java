package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Kosarica;

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
public class KosaricaZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;



    private Logger log = Logger.getLogger(KosaricaZrno.class.getName());

    @PostConstruct
    private void init(){
        log.info("inicializacija zrna "+ KosaricaZrno.class.getSimpleName());

        //inicializacija virov
    }

    @PreDestroy
    private void destroy(){
        log.info("deinicializacija zrna "+ KosaricaZrno.class.getSimpleName());

        //zapiranje virov
    }
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

    //implementacija se ostalih CRUD operacij
    public Kosarica getKosaricaById(int idKosarice) {
        Kosarica i = em.find(Kosarica.class, idKosarice);
        return i;
    }

    @Transactional
    public Kosarica dodajKosarico(Kosarica k){
        if(k != null){
            em.persist(k);
        }
        return k;
    }

    @Transactional
    public Kosarica posodobiKosarico(int idKosarice, Kosarica kos){
        Kosarica k = getKosaricaById(idKosarice);
        if(k != null){ //preverimo da taka Kosarica sploh obstaja
            //nastavimo vrednosti
            kos.setId(k.getId());
            em.merge(kos);
        }

        return  kos;
    }

    @Transactional
    public boolean izbrisiKosarico(int idKosarice){
        Kosarica k = getKosaricaById(idKosarice);
        if(k != null) { //preverimo da taka Kosarica sploh obstaja
            em.remove(k);
            return true;
        }
        return false;
    }
}
