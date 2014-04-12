package models;

import java.util.*;
import java.lang.*;

import javax.persistence.*;
import javax.validation.*;

import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

@Entity
public class PronoMatch extends Model {

	@Id
	public Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "match_id")
	public MatchC matchPronostique;

	public Long matchPronostiqueId;
	public boolean victoireADomicile;
	public String gagnant;
	public String perdant;
	public Boolean bonusOffensif;
	public Boolean bonusDefensif;
	public Boolean matchNul;

	public PronoMatch(MatchC match) {
		matchPronostique = match;
		matchPronostiqueId = match.id;
		victoireADomicile = true;
	}

	public static void create(PronoMatch prono) {
		prono.save();
	}

	public void mettreAJour(PronoJournee pronoJournee) {
		//this.pronoJournee = pronoJournee;
		matchPronostique = MatchC.find.byId(matchPronostiqueId);
    	if (victoireADomicile) {
    		gagnant = matchPronostique.domicile;
    		perdant = matchPronostique.exterieur;
    	} else {
    		 perdant = matchPronostique.domicile;
    		 gagnant = matchPronostique.exterieur;
    	}
	}
}
