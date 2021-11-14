package si.fri.rso.recommendation.services.beans;

import si.fri.rso.recommendation.models.Borrow;
import si.fri.rso.recommendation.models.Person;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class BorrowBean {
    private Logger log = Logger.getLogger(BorrowBean.class.getName());
    private String idBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        log.info("Init bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinit bean: " + BorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public List getBorrows(){

        return em.createNamedQuery("Borrow.getAll").getResultList();
    }

    public List<Borrow> getPersonsBorrows(Person person) {
        TypedQuery<Borrow> query= em.createNamedQuery("Borrow.getBorrowForPerson",Borrow.class);
        return query.setParameter("person",person).getResultList();
    }
}
