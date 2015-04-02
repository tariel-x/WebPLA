package models;

import play.db.ebean.Model;

import javax.persistence.Entity;

/**
 * Created by nikita on 30.03.15.
 */
@Entity
public class ConllRequest extends Model {
    public String inputtext;
}
