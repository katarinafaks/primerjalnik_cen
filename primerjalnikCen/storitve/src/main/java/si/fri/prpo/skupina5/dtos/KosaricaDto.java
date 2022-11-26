package si.fri.prpo.skupina5.dtos;



import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Trgovina;

import java.util.List;

public class KosaricaDto {


    private Trgovina trgovina;  //seznam trgovin za katere racunamo ceno
    private List<Izdelek> izdelki; //seznam izdelkov v kosarici


    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }

    public Trgovina getTrgovina() {
        return trgovina;
    }

    public void setTrgovina(Trgovina trgovina) {
        this.trgovina = trgovina;
    }
}
