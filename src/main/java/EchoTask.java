import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.entity.ContentType;
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
        responseBody.put("bot_id", BotMain.getBotID());
        responseBody.put("text", super.be.getRawinput());

        HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, 200, "OK");
        response.setEntity(new StringEntity(responseBody.toString(), ContentType.create("application/json")));
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
