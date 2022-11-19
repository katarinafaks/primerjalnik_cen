package si.fri.prpo.skupina5.entitete;

import javax.persistence.*;
import java.util.List;

@Entity(name = "kosarica")
@NamedQueries(value =
        {
                @NamedQuery(name = "Kosarica.getAll", query = "SELECT o FROM kosarica o"),
                @NamedQuery(name = "Kosarica.getSeznamFromId",query = "SELECT o.izdelki FROM kosarica o WHERE o.id =:id")

        })
public class Kosarica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "kosarica", cascade = CascadeType.ALL)
    private List<Izdelek> izdelki;

    public List<Izdelek> getIzdelki() {return izdelki;}

    public void setIzdelki(List<Izdelek> izdelki) {this.izdelki = izdelki;}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}
}

