package si.fri.prpo.skupina5.dtos;

import si.fri.prpo.skupina5.entitete.Izdelek;
import si.fri.prpo.skupina5.entitete.Trgovina;

import java.util.List;

public class TrgovinaDto {

    private List<Trgovina> trgovine;  //seznam trgovin za katere racunamo ceno
    private List<Izdelek> izdelki; //seznam izdelkov v kosarici


    public List<Trgovina> getTrgovine() {
        return trgovine;
    }

    public void setTrgovine(List<Trgovina> trgovine) {
        this.trgovine = trgovine;
    }

    public List<Izdelek> getIzdelki() {
        return izdelki;
    }

    public void setIzdelki(List<Izdelek> izdelki) {
        this.izdelki = izdelki;
    }
}
