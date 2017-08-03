package billmortem.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class APITestHelper {

    public String doPostSynchronously(String urlStr, String payload) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStream os = conn.getOutputStream();
        os.write(payload.getBytes());
        os.flush();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        StringBuilder result = new StringBuilder();
        while ((output = br.readLine()) != null) {
            result.append(output);
        }

        conn.disconnect();

        return result.toString();
    }

    public String sendMultipart(String urlStr, String data) throws IOException {
        HttpURLConnection httpUrlConnection = null;
        String boundary = "*****";
        String attachmentName = "bitmap";
        String attachmentFileName = "bitmap.bmp";
        String crlf = "\r\n";
        String twoHyphens = "--";

        URL url = new URL(urlStr);
        httpUrlConnection = (HttpURLConnection) url.openConnection();
        httpUrlConnection.setUseCaches(false);
        httpUrlConnection.setDoOutput(true);

        httpUrlConnection.setRequestMethod("POST");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("Cache-Control", "no-cache");
        httpUrlConnection.setRequestProperty(
                "Content-Type", "multipart/form-data;boundary=" + boundary);

        DataOutputStream request = new DataOutputStream(
                httpUrlConnection.getOutputStream());

        request.writeBytes(data);
        /*request.writeBytes(twoHyphens + boundary + crlf);
        request.writeBytes("Content-Disposition: form-data; name=\"" +
                attachmentName + "\";filename=\"" +
                attachmentFileName + "\"" + crlf);
        request.writeBytes(crlf);

        request.writeBytes(crlf);*/
        request.writeBytes(crlf);

        request.flush();
        request.close();
        InputStream responseStream = new
                BufferedInputStream(httpUrlConnection.getInputStream());

        BufferedReader responseStreamReader =
                new BufferedReader(new InputStreamReader(responseStream));

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();

        String response = stringBuilder.toString();

        responseStream.close();

        httpUrlConnection.disconnect();
        return null;
    }
}
