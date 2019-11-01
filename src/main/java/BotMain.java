import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

class BotMain {

    private final static int PORT_NUM = 25569;

    private static ArrayList<Thread> threadPool;

    private static boolean running = true;

    private static JSONObject configJson;

    public static void main(String[] args) {
        System.out.println("Starting bot");

        initEventBus();
        initConfig();

        threadPool = new ArrayList<Thread>();
        ServerSocket serverSock = null;
        try {
            serverSock = new ServerSocket(PORT_NUM);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Error creating listen socket on port: ");
            System.out.println(PORT_NUM);
        }

        System.out.print("Listening on port ");
        System.out.println(PORT_NUM);

        while (running) {
            try {
                Socket clientSocket = serverSock.accept();
                System.out.print("got a connection from: ");
                System.out.println(clientSocket.getInetAddress().toString());
                Thread t = new Thread(new ServingThread(clientSocket));
                t.start();
                threadPool.add(t);
                System.out.println("spun up a new thread to serve the request");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error when trying to accept connections");
                for (Thread t : threadPool) {
                    t.interrupt();
                }
                running = false;
            }
        }
    }

    static void initEventBus() {
        EventBus.ev.subscribe(new EchoTask());
    }

    static void initConfig() {
        try {
            JSONTokener tok = new JSONTokener(new BufferedReader(new FileReader("./config.json")));
            configJson = new JSONObject(tok);

        } catch (FileNotFoundException e) {
            File config = new File("config.json");
            try {
                config.createNewFile();
            } catch (IOException er) {
                System.out.println("Please open an issue on git and add this stack trace: ");
                e.printStackTrace();
                System.exit(-1);
            }
            System.out.println("config file not found!");
            System.out.print("created config file at: ");
            System.out.println(config.getAbsolutePath());
        }
    }

    static String getBotID() {
        String key = configJson.getString("bot_id");
        if (key.equals("DEFAULT")) {
            System.out.println("Please set the bot id key in the config.json file.");
            System.exit(-1);
        }
        return key;
    }

    static String getAPIURL() {
        return "https://api.groupme.com/v3/bots/post";
    }
}
