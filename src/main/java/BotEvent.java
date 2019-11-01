import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.impl.DefaultBHttpServerConnection;

import lombok.Getter;

public class BotEvent {

    @Getter
    DefaultBHttpServerConnection currentConnection;

    @Getter
    String rawinput;

    @Getter
    String botAPIKey;

    @Getter
    ArrayList<String> arguments;

    public BotEvent(DefaultBHttpServerConnection cs, String rs, String bk) {
        currentConnection = cs;
        rawinput = rs;
        botAPIKey = bk;
        arguments = new ArrayList<String>(Arrays.asList(rs.split(" ")));
    }
}