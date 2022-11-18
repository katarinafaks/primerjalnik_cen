package si.fri.prpo.skupina5.entitete;


import javax.persistence.*;

@Entity(name = "izdelek")
@NamedQueries(value =
        {
                @NamedQuery(name = "Izdelek.getAll", query = "SELECT o FROM izdelek o"),
                @NamedQuery(name = "Izdelek.getAllfromId",query="SELECT o FROM izdelek o WHERE o.id = :id"),
                @NamedQuery(name= "Izdelek.getTrgovina",query = "SELECT o.trgovina FROM izdelek o")
        })
public class Izdelek {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String opis;

    private Integer cena;

    @ManyToOne
    @JoinColumn(name = "kategorija_id")
    private Kategorija kategorija;

    @ManyToOne
    @JoinColumn(name="trgovina_id")
    private Trgovina trgovina;

    public Trgovina getTrgovina() {
        return trgovina;
    }

    public void setTrgovina(Trgovina trgovina) {
        this.trgovina = trgovina;
    }
// getter in setter metode

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
