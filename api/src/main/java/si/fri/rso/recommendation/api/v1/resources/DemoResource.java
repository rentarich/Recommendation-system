package si.fri.rso.recommendation.api.v1.resources;


import com.kumuluz.ee.logs.LogManager;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import si.fri.rso.recommendation.models.models.Item;
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

    private com.kumuluz.ee.logs.Logger logger= LogManager.getLogger(DemoResource.class.getName());


    @Inject
    private RestProperties restProperties;

    @GET
    @Operation(description = "Simulate unhealthy service.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service broken.")})
    @Path("break")
    public Response makeUnhealthy() {

        restProperties.setBroken(true);
        logger.info("Making service unhealthy.");
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Operation(description = "Make service back healthy.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Service healthy.")})
    @Path("heal")
    public Response makeHealthy() {

        restProperties.setBroken(false);
        logger.info("Making service healthy.");

        return Response.status(Response.Status.OK).build();
    }
}
