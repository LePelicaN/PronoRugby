package models;

import java.util.List;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.*;

@Entity
public class Commentaire extends Model {

	@Id
	public Long id;

    @Constraints.Required
    public String pseudo;
    public String texte;

	public static Finder<Long,Commentaire> find = new Finder(
			Long.class, Commentaire.class
			);

	public static List<Commentaire> last50() {
		return find.where().orderBy("id desc").findList().subList(0, 50);
	}

	public static List<Commentaire> all() {
		return find.where().orderBy("id desc").findList();
	}
}
