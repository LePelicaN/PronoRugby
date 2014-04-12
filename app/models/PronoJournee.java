package models;

import java.util.*;
import java.lang.*;

import javax.persistence.*;
import javax.validation.*;

import play.data.Form;
import play.data.Form.Field;
import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

@Entity
public class PronoJournee extends Model {

	@Id
	public Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "journee_id")
	public JourneeTop14 journee;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "joueur_id")
	public Joueur joueur;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "prono_match_id")
	public List<PronoMatch> pronoMatchs;

	public Long idJourneeManual;
	public Long idJoueurManual;
    
    public PronoJournee(Joueur joueur, JourneeTop14 journee) {
        this.journee = journee;
        this.idJourneeManual = journee.id;
        this.joueur = joueur;
        this.idJoueurManual = joueur.id;
        this.pronoMatchs = new ArrayList<PronoMatch>();
        for(MatchC match: journee.matchs) {
            this.pronoMatchs.add(new PronoMatch(match));
        }
    }

	public static Finder<Long,PronoJournee> find = new Finder(
			Long.class, PronoJournee.class
			);

	public static List<PronoJournee> all() {
		return find.all();
	}

	public static void create(PronoJournee prono) {
		prono.save();
	}

	public void mettreAJour() {
		journee = JourneeTop14.find.byId(idJourneeManual);
		joueur = Joueur.find.byId(idJoueurManual);
		for (PronoMatch pronoMatch : pronoMatchs) {
			pronoMatch.mettreAJour(this);
		}
	}
	
	public void mettreAJour2(PronoJournee oldPronoJournee) {
		for (int i = 0; i < 7; ++i) {
			pronoMatchs.get(i).update(oldPronoJournee.pronoMatchs.get(i).id);
		}
		
	}

	public static List<PronoJournee> journeeNumero(String journeeCourante) {
		JourneeTop14 journeeTop14 = JourneeTop14.find
												.where()
												.eq( "numero", journeeCourante )
												.findUnique();
		if (journeeTop14 != null) {
			return PronoJournee.find
					.where()
					.eq( "journee_id", journeeTop14.id ).findList();
		}
		return null;
	}
}
