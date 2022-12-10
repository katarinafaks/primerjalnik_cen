package si.fri.prpo.skupina5.interceptorji;


import si.fri.prpo.skupina5.anotacije.BeleziKlice;
import si.fri.prpo.skupina5.zrna.BelezenjeKlicovZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {


    //klice  CDI zrno BelezenjeKLicev ki hrani st vseh klicev metod
    @Inject
    BelezenjeKlicovZrno belezenjeKlicovZrno;

    private Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getSimpleName());

    private int steviloKlicev=0;

    @AroundInvoke
    public Object stejKlice(InvocationContext ctx) throws Exception{
        steviloKlicev++;

        log.info("Metoda: "+ ctx.getMethod().getName() + "v Razredu: "+
                ctx.getMethod().getDeclaringClass().getName() + "klici: " + steviloKlicev);
        try{
            Object x=ctx.proceed();
            return x;
        }catch(Exception e){
            log.throwing(ctx.getMethod().getDeclaringClass().getName(),ctx.getMethod().getName(),e);
            throw e;
        }

    }

}
