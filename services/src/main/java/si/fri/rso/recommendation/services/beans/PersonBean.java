package si.fri.rso.recommendation.services.beans;

import com.kumuluz.ee.logs.LogManager;
import si.fri.rso.recommendation.models.models.Person;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PersonBean {

    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(PersonBean.class.getName());
    private String idBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        logger.info("Init bean: " + PersonBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        logger.info("Deinit bean: " + PersonBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public List getPersons(){
        return em.createNamedQuery("Person.getAll").getResultList();
    }

    public Person getPerson(int id) {
        Person person = em.find(Person.class, id);
        return person;
    }
}
