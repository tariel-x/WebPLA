package controllers;

import java.util.*;

import models.*;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.plaindex;
import play.data.Form;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result plaindex() {
        return ok(plaindex.render());
    }


    public static Result processConll() {
        JsonNode json = request().body().asJson();
        String inputtext;
        if(json == null) {
            return badRequest("Expecting Json data");
        } else {
            inputtext = json.findPath("inputtext").textValue();
            if(inputtext == null) {
                return badRequest("Missing parameter [inputtext]");
            }
        }

        String rpcserver = play.Play.application().configuration().getString("dependrpc.server");
        Integer rpcport = play.Play.application().configuration().getInt("dependrpc.port");

        RPCSyntax rpcclient = new RPCSyntax(rpcserver, rpcport);
        List<String> conllLines = rpcclient.tagSentence(inputtext, "russian");
        String conll = StringUtils.join(conllLines, "\n");
        IWord word = new ConllWord();
        word.fromConll(conll);
        return ok(toJson(word.toPlayModel()));
    }

    public static Result createPla() {

        String inputtext;
        Form<Request> userForm = Form.form(Request.class);
        Request userreq = userForm.bindFromRequest().get();
        inputtext = userreq.inputtext;

        String rpcserver = play.Play.application().configuration().getString("dependrpc.server");
        Integer rpcport = play.Play.application().configuration().getInt("dependrpc.port");

        RPCSyntax rpcclient = new RPCSyntax(rpcserver, rpcport);
        List<String> conllLines = rpcclient.tagSentence(inputtext, "russian");
        String conll = StringUtils.join(conllLines, "\n");

        String plarpcserver = play.Play.application().configuration().getString("plarpc.server");
        Integer plarpcport = play.Play.application().configuration().getInt("plarpc.port");

        PLARPC placlient = new PLARPC(plarpcserver, plarpcport);
        String out = placlient.toPla(conll);
        PLASentence retsent = new PLASentence();
        retsent.text = out;
        return ok(toJson(retsent));
    }
}
