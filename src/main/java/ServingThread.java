import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;

public class ServingThread implements Runnable {

    private Socket servingSocket;
    HashMap<String, String> reqMap;

    ServingThread(Socket soc) {
        servingSocket = soc;
        reqMap = new HashMap<String, String>();
    }

    public void run() {
        String jsonString = "";

        try {
            DefaultBHttpServerConnection conn = new DefaultBHttpServerConnection(8 * 1024);
            conn.bind(servingSocket);
            HttpRequest request = conn.receiveRequestHeader();
            if (request instanceof HttpEntityEnclosingRequest) {
                conn.receiveRequestEntity((HttpEntityEnclosingRequest) request);
                HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
                if (entity != null) {
                    // Do something useful with the entity and, when done, ensure all
                    // content has been consumed, so that the underlying connection
                    // could be re-used

                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = entity.getContent().read(buffer)) != -1) {
                        result.write(buffer, 0, length);
                    }
                    // StandardCharsets.UTF_8.name() > JDK 7

                    jsonString = result.toString("UTF-8");
                    EntityUtils.consume(entity);
                }
            }
            HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "OK");
            response.setEntity(new StringEntity("Got it"));
            conn.sendResponseHeader(response);
            conn.sendResponseEntity(response);
            conn.flush();

            JSONObject jobj = new JSONObject(jsonString);
            System.out.println(jobj.getString("text"));

            // Put this string on the Event bus
            // Let people sub to the Event bus and dispatch from there
            BotEvent event = new BotEvent(conn, jobj.getString("text"), BotMain.getBotID());
            EventBus.ev.dispatch(event);

            System.out.println("finished serving a request");
            conn.close();
            servingSocket.close();
        } catch (IOException | HttpException e) {
            e.printStackTrace();
            System.out.println("error reading in the serving thread");
        }
    }
}
