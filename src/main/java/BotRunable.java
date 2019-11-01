abstract class BotRunable implements Runnable {
    BotEvent be;

    abstract void setEvent(BotEvent b);

    abstract void doWork();
}