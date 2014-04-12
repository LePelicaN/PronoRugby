package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreVincent implements Score {
	public double points;
	public Joueur joueur;

	@Override
	public double getPoints() {
		return points;
	}

	@Override
	public Joueur getJoueur() {
		return joueur;
	}	

	ScoreVincent(Joueur joueur) {
		this.points = 0;
		this.joueur = joueur;
		points = computeScore();
	}
	
	private double retrieveCote(MatchC match, PronoMatch pronoMatch) {
		if (pronoMatch.gagnant.equals(match.domicile)) {
			return match.coteDomicile;
		} else {
			return match.coteExterieur;
		}
	}
	
	private enum Resultat {
		Victoire,
		Nul,
		Defaite
	}

	private double computePoint(Resultat resEquipeDonneeGagnante, String bonusDomicile) {
		double point = 0;
		switch (resEquipeDonneeGagnante)
		{
		case Victoire:
			point += 4;
			break;
		case Nul:
			point += 2;
			break;
		case Defaite:
			point += 0;
			break;
		}
		if (bonusDomicile.equals(MatchC.Bonus.offensif.toString()) ||
		    bonusDomicile.equals(MatchC.Bonus.defensifEtOffensif.toString())	) {
			point += 1;
		} else if (bonusDomicile.equals(MatchC.Bonus.defensif.toString()) ||
				   bonusDomicile.equals(MatchC.Bonus.defensifEtOffensif.toString())) {
			point += 1;			
		}
			
		return point;
	}

	private double retrievePointEquipeDonneeGagnante(MatchC match, PronoMatch pronoMatch) {
		Resultat resEquipeDonneeGagnante;
		if (match.resultat.equals(MatchC.Resultat.matchNull.toString())) {
			resEquipeDonneeGagnante = Resultat.Nul;
		} else if (pronoMatch.gagnant.equals(match.gagnant)) {
			resEquipeDonneeGagnante = Resultat.Victoire;
		} else {
			resEquipeDonneeGagnante = Resultat.Defaite;
		}

		String bonusEquipeDonneeGagnate;
		if (pronoMatch.gagnant.equals(match.domicile)) {
			bonusEquipeDonneeGagnate = match.bonusDomicile;
		} else {
			bonusEquipeDonneeGagnate = match.bonusDomicile;
		}

		return computePoint(resEquipeDonneeGagnante, bonusEquipeDonneeGagnate);
	}

	private double computeScore() {
		List<PronoJournee> pronosDuJoueur = retrievePronoJoueur();
		if (pronosDuJoueur == null) {
			return -1;
		}
		this.points = 0;
		for (PronoJournee pronoJournee : pronosDuJoueur) {
			for (PronoMatch pronoMatch : pronoJournee.pronoMatchs) {
				MatchC match = pronoMatch.matchPronostique;
				if (!match.resultat.equals(MatchC.Resultat.pasEncoreJoue.toString())) {
					double cote = retrieveCote(match, pronoMatch);
					double pointEquipeDonneeGagnante = retrievePointEquipeDonneeGagnante(match, pronoMatch);
					this.points += cote * pointEquipeDonneeGagnante;
				}
			}
		}
		this.points = Math.round(this.points * 100) / 100.;
		return this.points;
	}

	private List<PronoJournee> retrievePronoJoueur() {
		return PronoJournee.find
						   .where()
						   .eq( "joueur_id", this.joueur.id )
						   .findList();
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

	public static List<Score> getAll() {
		List<Joueur> joueurs = Joueur.all();
		List<Score> scores = new ArrayList<Score>();
		for (Joueur joueur : joueurs) {
			ScoreVincent score = new ScoreVincent(joueur);
			if (score.points != -1) {
				scores.add(score);
			}
		}
		Collections.sort(scores, new ComparePoints());
		return scores;
	}
}
