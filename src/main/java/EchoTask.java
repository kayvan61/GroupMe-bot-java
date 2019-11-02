import org.json.JSONObject;

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
        if (super.be.getArguments().get(0).equals("/echo")) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("bot_id", BotMain.getBotID());
            responseBody.put("text", super.be.getRawinput());

            super.getGroupMeImageURL("~/image.jpg");

            super.replyToServer(responseBody.toString());
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        doWork();
    }
}
