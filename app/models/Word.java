package models;

import play.db.ebean.Model;

import java.util.List;

/**
 * Created by nikita on 30.03.15.
 */
public class Word extends Model {
    public String name;
    public List<Word> children;
}
