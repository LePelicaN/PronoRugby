package models;

import java.util.Comparator;

public interface Score {
	public double getPoints();
	public Joueur getJoueur();


	public static class ComparePoints implements Comparator<Score> {
		 
	    @Override
	    public int compare(Score o1, Score o2) {
	        return (o1.getPoints() > o2.getPoints() ? -1 : (o1.getPoints() == o2.getPoints() ? 0 : 1));
	    }
	} 
}
