package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import models.ConllWord;
import models.IWord;
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
        IWord word = new ConllWord();
//        word.fromConll("1\tА\tа\tCONJ\tCONJ\t_\t0\tROOT\n" +
//                "2\tгосударство\tгосударство\tS\tS\tNOM|SG|N|INAN\t3\tSUBJ\n" +
//                "3\tсможет\tмочь\tV\tV\tNPST|SG|REAL|3P|PERF\t1\tROOT\n" +
//                "4\tизменить\tизменять\tVINF\tVINF\tINF|PERF\t3\tOBJ\n" +
//                "5\tорганизационно-правовую\tорганизационно-правовой\tA\tA\tACC|SG|F\t6\tAMOD\n" +
//                "6\tформу\tформа\tS\tS\tACC|SG|F|INAN\t4\tOBJ\n" +
//                "7\tучреждения\tучреждение\tS\tS\tGEN|SG|N|INAN\t6\tROOT\n" +
//                "8\tобратно?-\tобратно\tADV\tADV\t_\t4\tPOBJ");
        word.fromConll(inputtext);
        return ok(toJson(word.toPlayModel()));
    }
}
