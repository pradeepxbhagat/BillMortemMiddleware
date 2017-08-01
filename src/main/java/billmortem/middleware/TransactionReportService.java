package billmortem.middleware;

import billmortam.report.TransactionReport;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by pp00344204 on 27/07/17.
 */
@Path("/report")
public class TransactionReportService {
    private Gson gson = new Gson();

    @Path("/get_distinct_report")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getDistinctReport(String payload){
        TransactionReport report = gson.fromJson(payload, TransactionReport.class);
        return gson.toJson(report.getDistinctKeyTotalReport());
    }

    @Path("/get_intelligent_report")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getIntelligentReport(String payload){
        Gson gson = new Gson();
        TransactionReport report = gson.fromJson(payload, TransactionReport.class);
        return gson.toJson(report.getArtificialDistinctKeyTotalReport());
    }
}
