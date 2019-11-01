import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class BotMain {

    private final static int PORT_NUM = 25569;

    private static ArrayList<Thread> threadPool;

    private static boolean running = true;

    public static void main(String[] args) {
        System.out.println("Starting bot");
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

    void initEventBus() {
        EventBus.getEv().subscribe(new EchoTask());
    }

}