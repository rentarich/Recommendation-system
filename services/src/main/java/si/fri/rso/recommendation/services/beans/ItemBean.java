package si.fri.rso.recommendation.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.recommendation.models.models.Item;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@ApplicationScoped
public class ItemBean {
    private Logger log = Logger.getLogger(ItemBean.class.getName());
    private String idBean;

    @Inject
    private BorrowBean borrowBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        log.info("Init bean: " + ItemBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        log.info("Deinit bean: " + ItemBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public List getItems(){

        return em.createNamedQuery("Item.getAll").getResultList();
    }

    public List<Item> getAvailableItemsFilter(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        List<Item> items = JPAUtils.queryEntities(em, Item.class, queryParameters);

        List<Item> bor = borrowBean.getBorrowedItems();
        List<Integer> borrowedIds = bor.stream().map(borrowed -> borrowed.getId()).collect(Collectors.toList());
        items.removeIf(item -> borrowedIds.contains(item.getId()));

        return items;
    }

    public Item getItem(int id) {
        Item item = em.find(Item.class, id);
        return item;
    }

}

