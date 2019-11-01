import java.io.PrintWriter;
import java.net.Socket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class BotContext {
    Socket      currentConnection;
    PrintWriter outputStream;
}
