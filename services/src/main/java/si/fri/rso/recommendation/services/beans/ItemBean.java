package si.fri.rso.recommendation.services.beans;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;
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
import java.util.stream.Collectors;


@ApplicationScoped
public class ItemBean {
    private com.kumuluz.ee.logs.Logger logger = LogManager.getLogger(ItemBean.class.getName());

    private String idBean;

    @Inject
    private BorrowBean borrowBean;

    @PostConstruct
    private void init(){
        idBean = UUID.randomUUID().toString();
        logger.info("Init bean: " + ItemBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PreDestroy
    private void destroy(){
        logger.info("Deinit bean: " + ItemBean.class.getSimpleName() + " idBean: " + idBean);
    }

    @PersistenceContext(unitName = "item-jpa")
    private EntityManager em;

    public List getItems(){

        return em.createNamedQuery("Item.getAll").getResultList();
    }

    @Gauge(name = "queue_length_gauge",unit = MetricUnits.NONE)
    private int getQueueLength(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        List<Item> items = JPAUtils.queryEntities(em, Item.class, queryParameters);

        List<Item> bor = borrowBean.getBorrowedItems();
        List<Integer> borrowedIds = bor.stream().map(borrowed -> borrowed.getId()).collect(Collectors.toList());
        items.removeIf(item -> borrowedIds.contains(item.getId()));

        return items.size();
    }

    public List<Item> getAvailableItemsFilter(UriInfo uriInfo) {
        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();
        getQueueLength(uriInfo);

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

