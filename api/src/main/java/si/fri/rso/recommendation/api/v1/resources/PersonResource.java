package si.fri.rso.recommendation.api.v1.resources;

import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.eclipse.microprofile.metrics.annotation.Metered;
import si.fri.rso.recommendation.models.models.Item;
import si.fri.rso.recommendation.services.beans.ManageBorrowBean;
import si.fri.rso.recommendation.services.config.RestProperties;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
@Path("persons")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private Client httpClient;
    private String baseUrl;

    @Context
    protected UriInfo uriInfo;

    private Logger logger=Logger.getLogger(PersonResource.class.getName());

    @Inject
    private ManageBorrowBean manageBorrowBean;

    @Inject
    private RestProperties restProperties;


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
    }

    @GET
    @Operation(description = "Get list of recommended and available items for borrow per person {personId}. Recommendation uses third API and calculates similarity between items borrowed in history and all available items.", summary = "Get persons recommended items",
            tags = "recommendation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Got recommended items for person {personId}.", content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = Item.class))),
                            headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
                    )})
    @Path("{id}/recommend")
    @Metered(name = "getRecommendation")
    public Response getRecommendation(@PathParam("id") int personId) throws UnirestException {

        // logger.info(restProperties.getBroken().toString());
        // logger.info(System.getenv().get("broken"));
        
        List<Item> items = manageBorrowBean.getRecommendation(personId,uriInfo);

        return Response.ok(items).header("X - total count", items.size()).build();
    }

}
