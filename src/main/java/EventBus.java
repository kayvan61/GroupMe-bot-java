import java.util.ArrayList;

import lombok.Getter;

class EventBus {

    @Getter
    static EventBus ev = new EventBus();

    ArrayList<BotRunable> listeners;
    ArrayList<Thread> threadPool;

    private EventBus() {
        listeners = new ArrayList<BotRunable>();
        threadPool = new ArrayList<Thread>();
    };

    void dispatch(BotEvent event) {
        for (BotRunable br : listeners) {
            br.setEvent(event);
            threadPool.add(new Thread(br));
        }
        for (Thread tr : threadPool) {
            tr.start();
        }
        for (Thread tr : threadPool) {
            try {
                tr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        threadPool = new ArrayList<Thread>();
    }

    void subscribe(BotRunable lister) {
        listeners.add(lister);
    }
}