package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreOfficiel implements Score {
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

	ScoreOfficiel(Joueur joueur) {
		this.points = 0;
		this.joueur = joueur;
		points = computeScore();
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
					if (!match.resultat.equals(MatchC.Resultat.matchNull.toString())) {
						if (pronoMatch.gagnant.equals(match.gagnant)) {
							if (match.gagnant.equals(match.domicile)) {
								this.points += match.coteDomicile;								
							} else {
								this.points += match.coteExterieur;				
							}
						}
					}
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
			ScoreOfficiel score = new ScoreOfficiel(joueur);
			if (score.points != -1) {
				scores.add(score);
			}
		}
		Collections.sort(scores, new ComparePoints());
		return scores;
	}
}
