import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

abstract class BotRunable implements Runnable {
    BotEvent be;

    abstract void setEvent(BotEvent b);

    abstract void doWork();

    HttpResponse replyToServer(String rep) {
        HttpClient c = HttpClients.createDefault();
        HttpPost p = new HttpPost(BotMain.getAPIURL());

        p.setEntity(new StringEntity(rep, ContentType.create("application/json")));
        try {
            return c.execute(p);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}