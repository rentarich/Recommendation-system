package si.fri.rso.recommendation.models.models;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="borrow")
@NamedQueries(value =
        {
                @NamedQuery(name = "Borrow.getAll", query = "SELECT b FROM Borrow b"),
                @NamedQuery(name = "Borrow.getBorrowForPerson", query = "SELECT b FROM Borrow b WHERE b.person = :person"),
                @NamedQuery(name = "Borrow.getReservedOrBorrowedItems", query = "SELECT b FROM Borrow b WHERE b.reserved = true OR b.returned=false")
        })
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "from_date")
    private Date from_date;

    @Column(name = "to_date")
    private Date to_date;

    @Column(name= "reserved")
    private boolean reserved;

    @Column(name = "returned")
    private boolean returned;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "id_person")
    private Person person;

    @JsonbTransient
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_item")
    private Item item;

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Item getItem(){
        return item;
    }

    public void setItem(Item item){
        this.item = item;
    }

    public Integer getId() {
        return id;
    }
}
