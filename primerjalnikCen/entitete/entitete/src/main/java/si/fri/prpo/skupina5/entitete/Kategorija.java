package si.fri.prpo.skupina5.entitete;


import javax.persistence.*;
import java.util.List;

@Entity(name = "kategorija")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kategorija.getAll", query = "SELECT o FROM kategorija o"),
                @NamedQuery(name = "Kategorija.getSeznamFromId",query = "SELECT o.seznamIzdelkov FROM kategorija o WHERE o.id =:id"),
                @NamedQuery(name = "Kategorija.getAllByIme", query = "SELECT k FROM kategorija k WHERE k.ime = :ime"),
                @NamedQuery(name = "Kategorija.getOpisFromIme", query = "SELECT k.opis FROM kategorija k WHERE k.ime = :ime"),
                @NamedQuery(name = "Kategorija.getSeznamFromIme", query = "SELECT o.seznamIzdelkov FROM kategorija o WHERE o.ime=:ime")
        })
public class Kategorija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String opis;

    @OneToMany(mappedBy="kategorija",cascade= CascadeType.ALL)
    private List<Izdelek> seznamIzdelkov;

    public List<Izdelek> getSeznamIzdelkov() {
        return seznamIzdelkov;
    }

    public void setSeznamIzdelkov(List<Izdelek> seznamIzdelkov) {
        this.seznamIzdelkov = seznamIzdelkov;
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
