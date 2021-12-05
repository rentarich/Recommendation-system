package si.fri.rso.recommendation.api.v1.resources;

import com.mashape.unirest.http.exceptions.UnirestException;
import si.fri.rso.recommendation.models.Item;
import si.fri.rso.recommendation.services.beans.ManageBorrowBean;
import si.fri.rso.recommendation.services.config.RestProperties;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationResource {

    private Client httpClient;
    private String baseUrl;

    private Logger logger=Logger.getLogger(RecommendationResource.class.getName());

    @Inject
    private ManageBorrowBean manageBorrowBean;

    @Inject
    private RestProperties restProperties;


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    @GET
    @Path("{id}/recommend")
    public Response getRecommendation(@PathParam("id") int personId) throws UnirestException {

        // logger.info(restProperties.getBroken().toString());
        // logger.info(System.getenv().get("broken"));

        List<Item> items = manageBorrowBean.getRecommendation(personId);

        return Response.ok(items).header("X - total count", items.size()).build();
    }

}
