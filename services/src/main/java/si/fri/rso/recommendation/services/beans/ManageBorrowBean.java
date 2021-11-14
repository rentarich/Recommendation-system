package si.fri.rso.recommendation.services.beans;

import si.fri.rso.recommendation.models.Borrow;
import si.fri.rso.recommendation.models.Item;
import si.fri.rso.recommendation.models.Person;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Logger;

@ApplicationScoped
public class ManageBorrowBean {

    @Inject
    private BorrowBean borrowBean;
    @Inject
    private ItemBean itemBean;
    @Inject
    private PersonBean personBean;

    private Logger log = Logger.getLogger(ManageBorrowBean.class.getName());
    private String idBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        log.info("Init bean: " + ManageBorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinit bean: " + ManageBorrowBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public List<Item> getRecommendation(int personId){
        Person person=personBean.getPerson(personId);
        List<Borrow> borrows=borrowBean.getPersonsBorrows(person);

        List<String> categories=new ArrayList<>();

        borrows.forEach(b -> categories.add(itemBean.getItem(b.getItem().getId()).getCategory()));


        return sortCatalog(categories);
    }


    private List<Item> sortCatalog(List<String> categories){
        String mostCommonCategory=mostCommon(categories);

        List<Item> items=itemBean.getItems();

        int ixSorted=0;
        for(int i=0;i<items.size();i++){
            Item item=items.get(i);
            if(item.getCategory().equals(mostCommonCategory)){
                Item tempItem=items.get(ixSorted);
                items.set(ixSorted,item);
                items.set(i,tempItem);

                ixSorted++;
            }
        }
        return items;
    }

    private <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;

        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }
}
