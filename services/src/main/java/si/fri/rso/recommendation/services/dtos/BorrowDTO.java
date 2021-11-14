package si.fri.rso.recommendation.services.dtos;

public class BorrowDTO {
    private String from_date;
    private String to_date;
    private int idPerson;
    private int idItem;

    public int getIdItem() {
        return idItem;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public String getFrom_date() {
        return from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }
}
