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
        System.out.println("from MemeFaces Task");
        if (super.be.getArguments().get(0).equals("/face")) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("bot_id", BotMain.getBotID());
            responseBody.put("text", CoolFaces.getRandomFace());

            super.replyToServer(responseBody.toString());
        } else {
	    for(String s: super.be.getArguments()){
		System.out.println(s);
	    }
	}
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        doWork();
    }
}
