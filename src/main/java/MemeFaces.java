import org.json.JSONObject;

public class MemeFaces extends BotRunable {

    @Override
    void setEvent(BotEvent b) {
        // TODO Auto-generated method stub
        super.be = b;
    }

    @Override
    void doWork() {
        // TODO Auto-generated method stub
        // access super.be;
        if (super.be.getArguments().get(0).equals("/face")) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("bot_id", BotMain.getBotID());
	    System.out.println(CoolFaces.getRandomFace());
            responseBody.put("text", CoolFaces.getRandomFace());

            super.replyToServer(responseBody.toString());
        }
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        doWork();
    }
}
