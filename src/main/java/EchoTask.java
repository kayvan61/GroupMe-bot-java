import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.message.BasicHttpResponse;
import org.json.JSONObject;

public class EchoTask extends BotRunable {

    @Override
    void setEvent(BotEvent b) {
        // TODO Auto-generated method stub
        super.be = b;
    }

    @Override
    void doWork() {
        // TODO Auto-generated method stub
        // access super.be;
        DefaultBHttpServerConnection conn = super.be.getCurrentConnection();

        JSONObject responseBody = new JSONObject();
        responseBody.put(key, value)

        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "OK");
        response.setEntity(new StringEntity("Got it"));
        conn.sendResponseHeader(response);
        conn.sendResponseEntity(response);
        conn.flush();

    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        doWork();
    }
}
