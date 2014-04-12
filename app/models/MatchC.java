package models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import models.Equipe;
import models.Equipe.EnumEquipe;
import play.data.validation.Constraints.*;
import play.db.ebean.*;
import play.db.ebean.Model.Finder;

@Entity
public class MatchC extends Model {

	@Id
	public Long id;
	
	@Required
	public String domicile;
	@Required
	public String exterieur;
	@Required
	java.util.Date date;
	public double coteDomicile;
	public double coteExterieur;
	public String bonusExterieur;
	public String bonusDomicile;
	public String resultat;
	public String gagnant;
	public String perdant;
	public Boolean nul;

	public enum Resultat {
		pasEncoreJoue,
		victoireDomicile,
		victoireExterrieur,
		matchNull
	}

	public enum Bonus {
		aucun,
		defensif,
		offensif,
		defensifEtOffensif
	}

	public static Finder<Long,MatchC> find = new Finder(
			Long.class, MatchC.class
			);

    public static Map<String,String> listeResultat() {
        LinkedHashMap<String,String> listeResultat = new LinkedHashMap<String,String>();
    	for ( Resultat resultat : Resultat.values() ){
    		listeResultat.put(resultat.toString(), resultat.toString());
    	}
        return listeResultat;
    }

    public static Map<String,String> listeBonus() {
        LinkedHashMap<String,String> listeBonus = new LinkedHashMap<String,String>();
    	for ( Bonus bonus : Bonus.values() ){
    		listeBonus.put(bonus.toString(), bonus.toString());
    	}
        return listeBonus;
    }

	public void miseAJourResultat(JourneeTop14 journeeTop14) {
		//this.journeeTop14 = journeeTop14;
		if (this.resultat == null) {
			this.resultat = Resultat.pasEncoreJoue.name();
		} else {
			Resultat resultat = Resultat.valueOf(this.resultat);
			nul = false;
			switch (resultat)
			{
				case matchNull:
					nul = true;
					gagnant = "";
					perdant = "";
					break;
				case victoireDomicile:
					gagnant = domicile;
					perdant = exterieur;
					break;
				case victoireExterrieur:
					gagnant = exterieur;
					perdant = domicile;
					break;
			}
		}
		// TODO Auto-generated method stub
		
	}
}
