import org.apache.http.impl.DefaultBHttpServerConnection;

import lombok.Getter;

public class BotEvent {

    @Getter
    DefaultBHttpServerConnection currentConnection;

    @Getter
    String rawinput;

    @Getter
    String botAPIKey;

    public BotEvent(DefaultBHttpServerConnection cs, String rs, String bk) {
        currentConnection = cs;
        rawinput = rs;
        botAPIKey = bk;
    }
}