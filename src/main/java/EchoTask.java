import java.io.IOException;

import javax.xml.ws.http.HTTPException;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultBHttpServerConnection;
import org.apache.http.impl.client.HttpClients;
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
        JSONObject responseBody = new JSONObject();
        responseBody.put("bot_id", BotMain.getBotID());
        responseBody.put("text", super.be.getRawinput());

        HttpClient c = HttpClients.createDefault();
        HttpPost p = new HttpPost(BotMain.getAPIURL());

        p.setEntity(new StringEntity(responseBody.toString(), ContentType.create("application/json")));
        try {
            HttpResponse r = c.execute(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        doWork();
    }
}
