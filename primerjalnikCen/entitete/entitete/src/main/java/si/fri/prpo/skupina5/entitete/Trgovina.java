package si.fri.prpo.skupina5.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "trgovina")
@NamedQueries(value =
        {
                @NamedQuery(name = "Trgovina.getAll", query = "SELECT o FROM trgovina o"),
                @NamedQuery(name = "Trgovina.getSeznamFromId",query = "SELECT o.seznamIzdelkov FROM trgovina o WHERE o.id =:id"),
                @NamedQuery(name = "Trgovina.getIme", query = "SELECT o.ime FROM trgovina o"),
                @NamedQuery(name = "Trgovina.getImeFromId",query = "SELECT o.ime FROM trgovina o WHERE o.id= :id"),
                @NamedQuery(name = "Trgovina.getSeznamFromIme",query = "SELECT o FROM trgovina o WHERE o.ime=:ime"),
                @NamedQuery(name = "Trgovina.getTrgovinaFromIme",query = "SELECT o FROM trgovina o WHERE o.ime= :ime")
        })
public class Trgovina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    @OneToMany(mappedBy="trgovina",cascade= CascadeType.ALL)
    private List<Izdelek> seznamIzdelkov;

    public List<Izdelek> getSeznamIzdelkov() {
        return seznamIzdelkov;
    }

    public void setSeznamIzdelkov(List<Izdelek> seznamIzdelkov) {
        this.seznamIzdelkov = seznamIzdelkov;
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
