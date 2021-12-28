package si.fri.rso.recommendation.api.v1.health;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Liveness
@ApplicationScoped
public class ThirdAPIHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        String url="https://text-similarity-calculator.p.rapidapi.com/stringcalculator.php?ftext=test1&stext=test2";

        try {
            HttpResponse<String> response = (HttpResponse<String>) Unirest.get(url).asString();

            return HealthCheckResponse.up(ThirdAPIHealthCheck.class.getSimpleName());
        }
        catch (Exception e){
            return HealthCheckResponse.down(ThirdAPIHealthCheck.class.getSimpleName());
        }
    }
}
