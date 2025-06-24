package echo;

import smartbox.components.Casino;

public class CasinoHandler extends RequestHandler {
    private Casino casino = new Casino();

    @Override
    protected String response(String msg) throws Exception {
        return casino.execute(msg);
    }
}

