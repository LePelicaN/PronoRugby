package models;

import java.util.List;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import com.avaje.ebean.*;

@Entity
public class Joueur extends Model {

	@Id
	public Long id;

    @Constraints.Required
    public String nom;
    public String nom2345;

	public static Finder<Long,Joueur> find = new Finder(
			Long.class, Joueur.class
			);

	public static List<Joueur> all() {
		return find.all();
	}
}
