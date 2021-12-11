package si.fri.rso.recommendation.api.v1.resources;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import si.fri.rso.recommendation.services.config.RestProperties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

@ApplicationScoped
@Path("/demo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    @Path("break")
    public Response makeUnhealthy() {

        // restProperties.setBroken(true);
        String baseUrl = "http://20.81.75.78:3333/v1/persons/1/recommend";

        log.info("gremo");
        try {
            HttpResponse<String> response = Unirest.get("http://20.81.75.78:3333/v1/persons/1/recommend")
                    .asString();
            log.info(response.getBody());
        }
        catch (Exception e){
            log.info(e.toString());
        }

        return Response.status(Response.Status.OK).build();
    }
}
