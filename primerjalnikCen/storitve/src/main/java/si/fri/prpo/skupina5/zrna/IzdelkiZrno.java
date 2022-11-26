package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.entitete.Izdelek;
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
public class IzdelkiZrno {


    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

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
}