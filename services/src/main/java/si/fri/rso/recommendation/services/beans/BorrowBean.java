package si.fri.rso.recommendation.services.beans;

import com.kumuluz.ee.logs.LogManager;
import si.fri.rso.recommendation.models.models.Borrow;
import si.fri.rso.recommendation.models.models.Item;
import si.fri.rso.recommendation.models.models.Person;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BorrowBean {
    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(BorrowBean.class.getName());

    private String idBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        logger.info("Init bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        logger.info("Deinit bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public List getBorrows(){

        return em.createNamedQuery("Borrow.getAll").getResultList();
    }

    public List<Item> getBorrowedItems() {
        TypedQuery<Borrow> query= em.createNamedQuery("Borrow.getReservedOrBorrowedItems",Borrow.class);
        List<Item> itemsBor = new ArrayList<>();
        for (Borrow borrow : query.getResultList()) {
            itemsBor.add(borrow.getItem());
        }
        return itemsBor;
    }

    public List<Borrow> getPersonsBorrows(Person person) {
        TypedQuery<Borrow> query= em.createNamedQuery("Borrow.getBorrowForPerson",Borrow.class);
        return query.setParameter("person",person).getResultList();
    }
}
