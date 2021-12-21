package si.fri.rso.recommendation.api.v1.resources;


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

    private Logger log = Logger.getLogger(DemoResource.class.getName());

    @Inject
    private RestProperties restProperties;

    @GET
    @Operation(description = "Simulates broken connection to DB.", summary = "Simulates unhealthy MS.",
            tags = "recommendation",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Simulation done."
                    )})
    @Path("break")
    public Response makeUnhealthy() {

        restProperties.setBroken(true);

        return Response.status(Response.Status.OK).build();
    }
}
