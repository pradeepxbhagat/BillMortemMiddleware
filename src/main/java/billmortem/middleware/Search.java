package billmortem.middleware;

import billmortam.report.TransactionReport;
import com.google.gson.Gson;

public class Search {
    private String searchTerm;
    private TransactionReport report;

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public void setReport(TransactionReport report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
