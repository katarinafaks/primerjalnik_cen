package si.fri.prpo.skupina5.zrna;

import si.fri.prpo.skupina5.dtos.IzdelekDto;
import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Kategorija;
import si.fri.prpo.skupina5.entitete.Trgovina;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class UpravljanjeIzdelkovZrno {



    @Inject
    private KategorijaZrno kategorijaZrno;

    @Inject
    private TrgovinaZrno trgovinaZrno;


    @Inject
    private IzdelkiZrno izdelkiZrno;

    @PersistenceContext(unitName = "primerjalnik-cen-jpa")
    private EntityManager em;

    private Logger log = Logger.getLogger(KosaricaZrno.class.getName());

    private String idZrna;

    @PostConstruct
    private void init() {

        idZrna = UUID.randomUUID().toString();

        log.info("Inicializacija zrna " + UpravljanjeIzdelkovZrno.class.getSimpleName() + " z ID: " + idZrna);

        // Initialize sources
    }

    @PreDestroy
    private void destroy() {
        log.info("Deinicializacija zrna " + UpravljanjeIzdelkovZrno.class.getSimpleName() + " z ID: " + idZrna);

        // Deinitialize sources
    }

    public List<Integer> stIzdelkovInPovprecjeKategorije(int kategorijaId){

        Kategorija kategorija=kategorijaZrno.getKategorijaById(kategorijaId);
        List<Izdelek> seznamIzdelkov=kategorija.getSeznamIzdelkov();
        if(seznamIzdelkov.isEmpty()){
            log.info("Ta kategorija nima izdelkov" + kategorija.getIme());
            return null;
        }
        int steviloIzdelkov=0;
        int povprecje=0;
        for(Izdelek i : seznamIzdelkov){
            povprecje+=i.getCena();
            steviloIzdelkov++;
        }
        povprecje=povprecje/steviloIzdelkov;
        List<Integer>rezultat=new ArrayList<Integer>();
        rezultat.add(steviloIzdelkov);
        rezultat.add(povprecje);
        log.info("Vracamo stevilo izdelkov in povprecje kot seznam");
        return rezultat;
    }


    public Izdelek ustvariIzdelekVTrgovinoInKategorijo(IzdelekDto izdelekDto){

        List<Izdelek> seznamKategorije=kategorijaZrno.getIzdelkiFromKategorija(izdelekDto.getImeKategorije());
        if(seznamKategorije.isEmpty()){
            log.info("Ta kategorija ne obstaja");
            return null;
        }
        List<Izdelek> seznamTrgovine= trgovinaZrno.getTrgovinaSeznamFromIme(izdelekDto.getImeTrgovine());
        if(seznamTrgovine.isEmpty()){
            log.info("Ta trgovina neobstaja");
            return null;
        }
        Izdelek i =new Izdelek();
        i.setIme(izdelekDto.getImeIzdelka());
        i.setOpis(izdelekDto.getOpisIzdelka());
        i.setCena(izdelekDto.getCena());
        Kategorija k=kategorijaZrno.getKategorijaFromIme(izdelekDto.getImeKategorije());
        seznamKategorije.add(i);
        i.setKategorija(k);
        Trgovina t=trgovinaZrno.getTrgovinaFromIme(izdelekDto.getImeTrgovine());
        seznamTrgovine.add(i);
        i.setTrgovina(t);
        izdelkiZrno.dodajIzdelek(i);
        log.info("Dodan je izdelek " + i.getIme());
        return i;
    }

}
