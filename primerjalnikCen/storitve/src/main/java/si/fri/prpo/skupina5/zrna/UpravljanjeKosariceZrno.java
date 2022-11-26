package si.fri.prpo.skupina5.zrna;


import si.fri.prpo.skupina5.dtos.KosaricaDto;
import si.fri.prpo.skupina5.dtos.TrgovinaDto;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kosarica;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeKosariceZrno {

    private Logger log = Logger.getLogger(UpravljanjeKosariceZrno.class.getName());

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    @Inject
    KosaricaZrno kosaricaZrno;

    @Inject
    TrgovinaZrno trgovinaZrno;

    @Inject
    IzdelkiZrno izdelkiZrno;

    public int IzracunajCenoKosariceByTrg(Kosarica k,int trgovinaId){
        List<Izdelek> izdelkiKosarice=k.getIzdelki();
        ListIterator<Izdelek> listIterator = izdelkiKosarice.listIterator();
        int cena=0;
        int i=0;
        while(listIterator.hasNext()){
            String ime=izdelkiKosarice.get(i).getIme();

            List<Izdelek> izdelkiTrgovine=pridobiIzdelkeTrgovine(trgovinaId);
            ListIterator<Izdelek> trg=izdelkiTrgovine.listIterator();
            int j=0;
            boolean izdelekObstaja=false;
            while(trg.hasNext()){
                if(izdelkiTrgovine.get(j).getIme().equalsIgnoreCase(ime)){
                    izdelekObstaja=true;
                    cena+=izdelkiTrgovine.get(j).getCena();
                }
                trg.next();
                j++;
            }
            if(!izdelekObstaja){
                log.info("Ta Trgovina nima na voljo tega izdelka" + trgovinaId + ime);
            }
            listIterator.next();
            i++;
            //em.find() najdi mi Izdelek z imenom 'ime' v tem seznamu
            //em.createNamedQuery("Trgovina.getSeznamFromId", Trgovina.class).setParameter("id",trgovinaId);

        }
        return cena;
    }


    public List<Izdelek> pridobiIzdelkeTrgovine(int trgovinaId){

       return em.createNamedQuery("Trgovina.getSeznamFromId").setParameter("id",trgovinaId).getResultList();
    }

    public boolean trgovinaImaIzdelek(int trgovinaId,Izdelek i){
        List<Izdelek> izdelkiTrgovine=pridobiIzdelkeTrgovine(trgovinaId);
        ListIterator<Izdelek> trg=izdelkiTrgovine.listIterator();
        int j=0;
        boolean izdelekObstaja=false;
        while(trg.hasNext()){
            if(izdelkiTrgovine.get(j).getIme().equalsIgnoreCase(i.getIme())){
                izdelekObstaja=true;
            }
            trg.next();
            j++;
        }
        return izdelekObstaja;
    }

    public Kosarica dodajIzdelekVKosarico(int kosaricaId,int izdelekId){
        Izdelek i=izdelkiZrno.getIzdelekById(izdelekId);
        if(i == null){
            log.info("Izdelek ne obstaja");
        }
        Kosarica k =kosaricaZrno.getKosaricaById(kosaricaId);
        if(k==null){
            log.info("Kosarica ne obstaja");
        }
        List<Izdelek>seznam=k.getIzdelki();
        seznam.add(i);
        k.setIzdelki(seznam);
        kosaricaZrno.posodobiKosarico(kosaricaId,k);
        i.setKosarica(k);
        izdelkiZrno.posodobiIzdelek(izdelekId,i);
        return k;
    }

    //vrne id izdelka, ki smo ga izbrisali iz Kosarice
    public Integer odstraniIzdelekizKosarice(int kosaricaId, int izdelekId){
        Kosarica k=kosaricaZrno.getKosaricaById(kosaricaId);
        List<Izdelek> novaKosarica=new ArrayList<Izdelek>();
        log.info("Stari seznam" + k.getIzdelki());

        for(Izdelek izdelek:k.getIzdelki()){
            log.info("Gledamo izdelek"+izdelek.getIme());
            if(izdelek.getId()!=izdelekId)
                novaKosarica.add(izdelek);
        }
        k.setIzdelki(novaKosarica);
        log.info("Novi seznam" + k.getIzdelki());
        kosaricaZrno.posodobiKosarico(kosaricaId,k);
        izdelkiZrno.izbrisiIzdelek(izdelekId);

        return izdelekId;
    }


    public boolean preveriKosarico(KosaricaDto kosaricaDto){

        List<Izdelek> zeljeniIzdelki=kosaricaDto.getIzdelki();
        if(zeljeniIzdelki.isEmpty()){
            log.info("Ni podanih izdelkov");
            return false;
        }
        for(Izdelek i: zeljeniIzdelki){
            //i.getIme();
            boolean ima=trgovinaImaIzdelek(kosaricaDto.getTrgovina().getId(),i);
            if(!ima){
                log.info("Trgovina" + kosaricaDto.getTrgovina().getIme()+"nima izdelka:" + i.getIme());
                return false;
            }
        }
        log.info("Trgovina"+kosaricaDto.getTrgovina().getIme()+"ima vse izdelke v kosarici");
        return true;
    }


    //vrne Kosarico ce imajo vse trgovine za katere se zanimajo te izdelke else vrne null
    public Kosarica dodajKosarica(TrgovinaDto trgDto){

        List<Izdelek> zeljeniIzdelki=trgDto.getIzdelki();
        if(zeljeniIzdelki.isEmpty()){
            log.info("Ni podanih izdelkov za dodajanje v Kosarico");
            return null;
        }

        List<Trgovina> trgovine=trgDto.getTrgovine();
        if(trgovine.isEmpty()){
            log.info("Ni podanih trgovin za katere nas zanimajo izdelki");
            return null;
        }

        ListIterator<Izdelek> listIterator = zeljeniIzdelki.listIterator();
        Kosarica k=new Kosarica();
        List<Izdelek> mozniIzdelki = new ArrayList<Izdelek>();
        int st=0;
        while(listIterator.hasNext()){
            Izdelek i=zeljeniIzdelki.get(st);
            ListIterator<Trgovina> trg=trgovine.listIterator();
            int stTrg=0;
            boolean notAllTrgovine=false;
            while(trg.hasNext()){
                int trgId=trgovine.get(stTrg).getId();
                if(!trgovinaImaIzdelek(trgId,i)){
                    log.info("trgovina" +trgovinaZrno.getTrgovinaById(trgId).getIme()+"nima "+ i.getIme());
                    notAllTrgovine=true;
                }
            }
            if(!notAllTrgovine){
                mozniIzdelki.add(i);
            }
        }
        if(!mozniIzdelki.isEmpty()){
            k.setIzdelki(mozniIzdelki);
            kosaricaZrno.dodajKosarico(k); //???nevem ce morm al ne
            return k;
        }

        log.info("Ne moremo ustvariti kosarice");
        return null;
    }


}
