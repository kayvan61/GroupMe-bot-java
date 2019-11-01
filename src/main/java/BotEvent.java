import java.io.PrintWriter;
import java.net.Socket;

import org.apache.http.impl.DefaultBHttpServerConnection;

import lombok.Getter;

public class BotEvent {

    @Getter
    DefaultBHttpServerConnection currentConnection;

    @Getter
    String rawinput;

    public BotEvent(DefaultBHttpServerConnection cs, String rs) {
        currentConnection = cs;
        rawinput = rs;
    }
}