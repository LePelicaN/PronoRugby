package models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Equipe {

	public enum EnumEquipe {
		ASM, 
		Bayonne,
		Brive, 
		Biarritz, 
		Castres, 
		Grenoble, 
		Montpellier, 
		Oyonnax,
		Racing_Metro_92, 
		Rugby_Club_Toulonnais, 
		Stade_Francais, 
		Stade_Toulousain,
		Union_Bordeaux_Begles,
		USAP
	}

	public EnumEquipe nom;

	public Equipe(EnumEquipe nomEquipe) {
		nom = nomEquipe;
	}

	public Equipe(String nomEquipe) {
		nom = EnumEquipe.valueOf(nomEquipe);
	}

    public static Map<String,String> listeEquipe() {
        LinkedHashMap<String,String> listeEquipe = new LinkedHashMap<String,String>();
    	for ( EnumEquipe equipe : EnumEquipe.values() ){
    		listeEquipe.put(equipe.toString(), equipe.toString());
    	}
        return listeEquipe;
    }
}
