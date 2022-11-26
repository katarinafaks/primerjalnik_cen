package si.fri.prpo.skupina5.dtos;

public class IzdelekDto {
    private String imeIzdelka;
    private String opisIzdelka;
    private Integer cena;
    private String imeTrgovine;
    private String imeKategorije;

    public String getImeIzdelka() {
        return imeIzdelka;
    }

    public void setImeIzdelka(String imeIzdelka) {
        this.imeIzdelka = imeIzdelka;
    }

    public String getOpisIzdelka() {
        return opisIzdelka;
    }

    public void setOpisIzdelka(String opisIzdelka) {
        this.opisIzdelka = opisIzdelka;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public String getImeTrgovine() {
        return imeTrgovine;
    }

    public void setImeTrgovine(String imeTrgovine) {
        this.imeTrgovine = imeTrgovine;
    }

    public String getImeKategorije() {
        return imeKategorije;
    }

    public void setImeKategorije(String imeKategorije) {
        this.imeKategorije = imeKategorije;
    }
}
