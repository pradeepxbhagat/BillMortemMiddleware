package billmortem.middleware;

import billmortam.report.TransactionReport;
import billmortam.transaction.TransactionSearch;
import com.google.gson.Gson;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by pp00344204 on 27/07/17.
 */
@Path("/search")
public class SearchService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String doSearch(String payload) {
        return getTransactionReportAsString(TransactionSearch.getSearchEngine(getSearchModel(payload).getReport()).searchTransaction(getSearchModel(payload).searchTerm));
    }

    private String getTransactionReportAsString(TransactionReport report) {
        return new Gson().toJson(report);
    }

    private Search getSearchModel(String searchModel) {
        Gson gson = new Gson();
        return gson.fromJson(searchModel, Search.class);
    }

    private class Search{
        private String searchTerm;
        private TransactionReport report;

        public String getSearchTerm() {
            return searchTerm;
        }

        public void setSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
        }

        public TransactionReport getReport() {
            return report;
        }

        public void setReport(TransactionReport report) {
            this.report = report;
        }
    }
}
