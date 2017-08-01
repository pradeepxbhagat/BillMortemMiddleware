package billmortem.middleware;

import billmortam.parser.BillIdentifier;
import billmortam.parser.ParserFactory;
import billmortam.pdf.Pdf;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/get_transactions")
public class TransactionService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransactions(String pdfStr){
        Pdf pdf = new Gson().fromJson(pdfStr,Pdf.class);
        return getTransacitonAsJson(pdf);
    }

    public String getTransacitonAsJson(Pdf pdf) {
        return new Gson().toJson(ParserFactory.getFactory().getParser(BillIdentifier.getIdentifier().identify(pdf)).parse(pdf, false));
    }
}
