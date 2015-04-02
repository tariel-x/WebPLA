package controllers;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import models.ConllWord;
import models.IWord;
import models.RPCSyntax;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
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
}
