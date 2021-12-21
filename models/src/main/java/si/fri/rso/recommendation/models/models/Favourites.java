package si.fri.rso.recommendation.models.models;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

@Entity
@Table(name="favourites")
@NamedQueries(value =
        {
            @NamedQuery(name = "Favourites.getFavouritesForPerson", query = "SELECT f FROM Favourites f WHERE f.person = :person"),
            @NamedQuery(name = "Favourites.getFavourite", query = "SELECT f FROM Favourites f WHERE f.person = :person AND f.item= :item")
        })
public class Favourites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_item")
    private Item item;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_person")
    private Person person;

    public Integer getId() {
        return this.id;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
