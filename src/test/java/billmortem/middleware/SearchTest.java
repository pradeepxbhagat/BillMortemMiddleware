package billmortem.middleware;

import billmortam.report.TransactionReport;
import billmortam.transaction.Transaction;
import billmortem.util.APITestHelper;
import billmortem.util.TimeComplexityTestCase;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pp00344204 on 28/07/17.
 */
public class SearchTest extends TimeComplexityTestCase {

    @Test
    public void searchStringTest() throws Exception {
        List<Transaction> contents = new ArrayList<Transaction>();
        contents.add(new Transaction("10/12/2017","desc1",100));
        contents.add(new Transaction("10/12/2017","desc3",200));
        contents.add(new Transaction("11/12/2017","desc4",300));

        TransactionReport report = new TransactionReport();
        report.setContents(contents);
        Search search = new Search();
        search.setReport(report);
        search.setSearchTerm("petrol");

        Assert.assertEquals(new Gson().toJson(search), search.toString());
    }

    @Test
    public void testSearchLogic() throws Exception {
        List<Transaction> contents = new ArrayList<Transaction>();
        contents.add(new Transaction("10/12/2017","desc1",100));
        contents.add(new Transaction("10/12/2017","desc3",200));
        contents.add(new Transaction("11/12/2017","desc4",300));

        TransactionReport report = new TransactionReport();
        report.setContents(contents);
        Search search = new Search();
        search.setReport(report);
        search.setSearchTerm("desc1");

        SearchService searchService = new SearchService();
        String result = searchService.doSearch(search.toString());

        System.out.println(result);

        Assert.assertTrue(result.length() > 0);
    }


    @Test
    public void testByHittingRestServiceUrl() throws Exception {
        List<Transaction> contents = new ArrayList<Transaction>();
        contents.add(new Transaction("10/12/2017","desc1",100));
        contents.add(new Transaction("10/12/2017","desc3",200));
        contents.add(new Transaction("11/12/2017","desc4",300));

        TransactionReport report = new TransactionReport();
        report.setContents(contents);
        Search search = new Search();
        search.setReport(report);
        search.setSearchTerm("desc1");

        SearchService searchService = new SearchService();

        APITestHelper helper = new APITestHelper();
        String result = helper.doPostSynchronously("http://localhost:8080/service/search", searchService.doSearch(search.toString()));
        Assert.assertTrue(result.length() > 0);
    }
}
