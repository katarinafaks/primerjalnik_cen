package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
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
public class IzdelkiZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(IzdelkiZrno.class.getName());


    private String idZrna;

    @PostConstruct
    private void init(){

        idZrna = UUID.randomUUID().toString();
        log.info("inicializacija zrna "+ IzdelkiZrno.class.getSimpleName()+ " z ID: " + idZrna);

        //inicializacija virov
    }

    @PreDestroy
    private void destroy(){
        log.info("deinicializacija zrna "+ IzdelkiZrno.class.getSimpleName()+ " z ID: " + idZrna);

        //zapiranje virov
    }

    public List<Izdelek> getIzdelki() {

        // implementacija
        List<Izdelek> izdelki=em.createNamedQuery("Izdelek.getAll").getResultList();
        //List<Izdelek>izdelki=null;

        return izdelki;
    }
    public List<Trgovina> getTrgovina() {

        List<Trgovina> t=em.createNamedQuery("Izdelek.getTrgovina").getResultList();

        return t;
    }

    public List<Izdelek> getAllIzdelkiWithCriteria(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Izdelek> q= cb.createQuery(Izdelek.class);   //typesafe query..criteria query object(q) that
        //returns a 'Izdelek'

        //root..similar to from clause in jpql query? ..root entity..from which all navigation originates
        //create the query root by calling from method on the criteria query instance
        Root<Izdelek> i = q.from(Izdelek.class);
        //criteria queries can have multiple root--usually when querry navigates from several entitites

        //path navigation..select
        CriteriaQuery<Izdelek> a=q.select(i);

        TypedQuery<Izdelek> query = em.createQuery(a);
        List<Izdelek> seznam = query.getResultList();
        return seznam;
    }

    public Izdelek getIzdelekById(int idIzdelka) {
        Izdelek i = em.find(Izdelek.class, idIzdelka);
        return i;
    }

    @Transactional
    public Izdelek dodajIzdelek(Izdelek i){
        if(i != null){
            em.persist(i);
        }
        return i;
    }

    @Transactional
    public Izdelek posodobiIzdelek(int idIzdelka, Izdelek i){
        Izdelek iz = getIzdelekById(idIzdelka);
        if(iz != null){ //preverimo da tak izdelek sploh obstaja
            //nastavimo vrednosti
            i.setId(iz.getId());

            em.merge(i);
        }

        return  i;
    }

    @Transactional  //privzeto je REQUIRED ----> če transakcije še ni se ustvari drugače pa se uporabi obstoječa
    public boolean izbrisiIzdelek(int idIzdelka){
        Izdelek iz = getIzdelekById(idIzdelka);
        if(iz != null) { //preverimo da tak izdelek sploh obstaja
            em.remove(iz);
            return true;
        }
        return false;
    }
}