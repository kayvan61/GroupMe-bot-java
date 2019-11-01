import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServingThread implements Runnable{

    private Socket servingSocket;

    ServingThread(Socket soc) {
        servingSocket = soc;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(servingSocket.getInputStream()));
            String response = "";
            for (String line; (line = in.readLine()) != null; response += line);
            System.out.println(response);
            JSONObject respObj = new JSONObject(response);
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("error reading in the serving thread");
        }
    }
}
