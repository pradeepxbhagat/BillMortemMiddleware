package billmortem.middleware;

import billmortam.parser.BillIdentifier;
import billmortam.parser.ParserFactory;
import billmortam.pdf.Pdf;
import billmortam.pdf.PdfBoxReader;
import billmortam.pdf.PdfReaderException;
import billmortam.util.Util;
import com.google.gson.Gson;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;

@Path("/transaction")
public class TransactionService {

    @Path("/get_transactions")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getTransactions(String pdfStr) {
        Pdf pdf = new Gson().fromJson(pdfStr, Pdf.class);
        return getTransactionAsJson(pdf);
    }

    @POST
    @Path("/get_transactions1")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String getTransactions1(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail,@FormDataParam("password") String password) {
        if (uploadedInputStream == null) {
            return new ServiceError(1301,"Not able to process").toString();
        }
        if(!isPdfFileRequest(fileDetail)){
            return new Gson().toJson(new ServiceError(1302,"Invalid pdf file"));
        }
        try {
            Pdf pdf = getPdf(saveTemporaryFile(getFileStream(uploadedInputStream)), password);
            removeTemporaryFile(pdf.getFilePath());
            return getTransactionAsJson(pdf);
        } catch (IOException e) {
            return new ServiceError(1301,e.getMessage()).toString();
        } catch (PdfReaderException e) {
            Gson gson = new Gson();
            switch (e.getExceptionType()){
                case PdfReaderException.ExceptionType.PASSWORD_PROTECTED : return gson.toJson(new ServiceError(1303,"Password required"));
                case PdfReaderException.ExceptionType.INVALID_PDF_FILE : return gson.toJson(new ServiceError(1302,"Invalid pdf file"));
                case PdfReaderException.ExceptionType.INVALID_PASSWORD : return gson.toJson(new ServiceError(1304,"Password invalid"));
            }
            return e.getMessage();
        }
    }

    private boolean isPdfFileRequest(FormDataContentDisposition fileDetail) {
        return Util.checkPdfUrl(fileDetail.getFileName());
    }

    private Pdf getPdf(String fileName, String password) throws PdfReaderException {
        return password != null ? PdfBoxReader.getReader().read(fileName, password): PdfBoxReader.getReader().read(fileName);
    }

    private String saveTemporaryFile(ByteArrayOutputStream result) throws IOException {
        String fileName = System.currentTimeMillis() + ".pdf";
        OutputStream os = new FileOutputStream(fileName);
        result.writeTo(os);
        return fileName;
    }

    private ByteArrayOutputStream getFileStream(@FormDataParam("file") InputStream uploadedInputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = uploadedInputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result;
    }

    public String getTransactionAsJson(Pdf pdf) {
        return new Gson().toJson(ParserFactory.getFactory().getParser(BillIdentifier.getIdentifier().identify(pdf)).parse(pdf, false));
    }

    protected boolean removeTemporaryFile(String filePath){
        if(filePath == null){
            return false;
        }
        return new File(filePath).delete();
    }
}
