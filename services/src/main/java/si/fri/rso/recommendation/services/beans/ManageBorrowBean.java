package si.fri.rso.recommendation.services.beans;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
@Timed(name = "ManageBorrowBean")
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

    public List<Item> getRecommendation(int personId) throws UnirestException {
        Person person=personBean.getPerson(personId);
        List<Borrow> borrows=borrowBean.getPersonsBorrows(person);

        List<String> categories=new ArrayList<>();

        borrows.forEach(b -> categories.add(itemBean.getItem(b.getItem().getId()).getCategory()));


        return sortCatalog(categories);
    }


    @Counted(name = "sortCatalog")
    private List<Item> sortCatalog(List<String> categories) throws UnirestException {
        String mostCommonCategory=mostCommon(categories).replaceAll("\\s+","%20");

        List<Item> items=itemBean.getItems();
        List<Float> matches=new ArrayList<>();

        for(int i=0;i<items.size();i++) {
            Item item=items.get(i);
            String itemCategory=item.getCategory().replaceAll("\\s+","%20");
            String rapidAPIkey = System.getenv("rapid-api-key");
            String url="https://text-similarity-calculator.p.rapidapi.com/stringcalculator.php?ftext="+mostCommonCategory+"&stext="+itemCategory;
            HttpResponse<String> response = (HttpResponse<String>) Unirest.get(url)
                    .header("x-rapidapi-host", "text-similarity-calculator.p.rapidapi.com")
                    .header("x-rapidapi-key", rapidAPIkey)
                    .asString();

            String responseBody=response.getBody();
            Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
            Matcher matcher = pattern.matcher(responseBody);
            float match=0;
            if (matcher.find())
            {
                match=Float.parseFloat(matcher.group(0));
            }
            matches.add(match);
        }

        for(int i=0; i < matches.size(); i++){
            for(int j=1; j < (matches.size()-i); j++){
                if(matches.get(j-1) < matches.get(j)){
                    Item tempItem=items.get(j-1);
                    items.set(j-1,items.get(j));
                    items.set(j,tempItem);

                    float tempFloat=matches.get(j-1);
                    matches.set(j-1,matches.get(j));
                    matches.set(j,tempFloat);
                }
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
