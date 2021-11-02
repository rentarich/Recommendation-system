package si.fri.rso.recommendation.api.v1.resources;


import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
/*import com.kumuluz.ee.cors.annotations.CrossOrigin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;*/
import si.fri.rso.recommendation.models.Item;
import si.fri.rso.recommendation.services.beans.ItemBean;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("items")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    private Client httpClient;
    private String baseUrl;

    @Inject
    private ItemBean itemBean;


    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance().get("kumuluzee.server.base-url").orElse("N/A");
    }


    /*@Operation(description = "Vrni vse sezname", summary = "Vrni seznam vseh nakupovalnih seznamov.", tags = "seznami", responses = {
            @ApiResponse(responseCode = "200",
                    description = "Vrnjen seznam nakupovalnih seznamov.",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = NakupovalniSeznam.class))),
                    headers = {@Header(name = "X-Total-Count", schema = @Schema(type = "integer"))}
            )})*/
    @GET
    public Response getItems(){
        List items = itemBean.getItems();

        return Response.ok(items).header("X - total count", items.size()).build();
    }

}
