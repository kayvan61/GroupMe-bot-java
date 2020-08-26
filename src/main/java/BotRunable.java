import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

abstract class BotRunable implements Runnable {
    BotEvent be;

    abstract void setEvent(BotEvent b);

    abstract void doWork();

    HttpResponse replyToServer(String rep) {
        HttpClient c = HttpClients.createDefault();
        HttpPost p = new HttpPost(BotMain.getAPIURL());
        p.setHeader("Content-Type", "application/json; charset=UTF-8");

        p.setEntity(new StringEntity(rep, "UTF-8"));
        try {
            return c.execute(p);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    String getGroupMeImageURL(String path) {
        String resString = "";
        File pic = new File(path);

        HttpClient c = HttpClients.createDefault();
        HttpPost p = new HttpPost(BotMain.getAPIURL());
        p.setHeader("Content-Type", "application/json; charset=UTF-8");
        p.setHeader("X-Access-Token", BotMain.getAPIToken());

        p.setEntity(new FileEntity(pic));
        try {
            HttpResponse rep = c.execute(p);

            HttpEntity entity = rep.getEntity();
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

                resString = result.toString("UTF-8");
                EntityUtils.consume(entity);
            }
            System.out.println(resString);
            return resString;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}