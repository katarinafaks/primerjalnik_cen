package si.fri.prpo.skupina5.zrna;


import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

@ApplicationScoped
public class BelezenjeKlicovZrno {

    private int stKlicov=0;

    private Logger log =Logger.getLogger(BelezenjeKlicovZrno.class.getSimpleName());

    public void klic(){
        stKlicov++;
        log.info("stevilo klicov je bilo povecano na " + stKlicov);
    }

}
