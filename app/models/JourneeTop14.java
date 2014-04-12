package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.validation.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

@Entity
public class JourneeTop14 extends Model {

	@Id
	public Long id;
	@Required
	public int numero;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public List<MatchC> matchs;
    
    public JourneeTop14(int numero, MatchC... matchs) {
        this.numero = numero;
        this.matchs = new ArrayList<MatchC>();
        for(MatchC match: matchs) {
            this.matchs.add(match);
        }
    }

	public static Finder<Long,JourneeTop14> find = new Finder(
			Long.class, JourneeTop14.class
			);

	public static List<JourneeTop14> all() {
		return find.all();
	}

/*
	public static Finder<Long,JourneeTop14> find = new Finder(
			Long.class, JourneeTop14.class
			);*/

    public static Map<String,String> listeNumero() {
        LinkedHashMap<String,String> listeNumero = new LinkedHashMap<String,String>();
    	for ( Integer i = 1; i <= 26; ++i ){
    		listeNumero.put(i.toString(), i.toString());
    	}
        return listeNumero;
    }

	public void miseAJour() {
        for(MatchC match: matchs) {
            match.miseAJourResultat(this);
        }		
	}

	public void miseAJour2(JourneeTop14 oldJourneeTop14) {
		for (int i = 0; i < 7 ;++i) {
            matchs.get(i).update(oldJourneeTop14.matchs.get(i).id);
		}
	}
}
