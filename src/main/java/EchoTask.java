public class EchoTask extends BotRunable {

    @Override
    void setEvent(BotEvent b) {
        // TODO Auto-generated method stub
        super.be = b;
    }

    @Override
    void doWork() {
        // TODO Auto-generated method stub
        // access super.be;
        System.out.println("got an event in the Echo Task!");
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        doWork();
    }
}
