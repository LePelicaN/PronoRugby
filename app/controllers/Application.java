package controllers;

import java.util.List;
import java.util.Map;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import play.data.validation.ValidationError;
import views.html.*;
import models.*;

public class Application extends Controller {
	
    public static Result GO_HOME = redirect(routes.Application.pronos());
	
	public static Result index() {
		return GO_HOME;
		}

	public static Result pronos() {
        DynamicForm requestData = form().bindFromRequest();
        String journeeCourante = requestData.get("journeeCourante");
        if (journeeCourante == null) {
        	journeeCourante = "10";
        }
		return ok( views.html.index.render( Joueur.all(), ScoreOfficiel.getAll(), ScoreVincent.getAll(), PronoJournee.journeeNumero(journeeCourante), JourneeTop14.all(), journeeCourante ) );
	}


	static Form<Joueur> joueurForm = form(Joueur.class);

	public static Result nouveauJoueur() {
        Form<Joueur> joueurForm = form( Joueur.class );
        return ok( nouveauJoueurForm.render( joueurForm ) );
	}

	public static Result sauvegardeJoueur() {
        Form<Joueur> joueurForm = form( Joueur.class ).bindFromRequest();
        if( joueurForm.hasErrors() ) {
        	flash("failure", "Le joueur n'a pas été créé.");
            return badRequest( nouveauJoueurForm.render( joueurForm ) );
        } else {
        	joueurForm.get().save();
        	flash("success", "Joueur " + joueurForm.get().nom + " a été créé.");
        	return GO_HOME;
        }
    }


	static Form<PronoJournee> pronoForm = form(PronoJournee.class);

	public static Result nouveauProno() {
        Form<PronoJournee> pronoForm = form( PronoJournee.class );
        return ok( nouveauPronoForm.render( pronoForm ) );
	}

	public static Result sauvegardeProno() {
		Form<PronoJournee> pronoForm = form( PronoJournee.class ).bindFromRequest();
		java.util.Map<java.lang.String,java.util.List<ValidationError>> err = pronoForm.errors(); 

		if( pronoForm.hasErrors() ) {
        	flash("failure", "Le prono n'a pas été créé.");
			return badRequest( nouveauPronoForm.render( pronoForm ) );
		} else {
			PronoJournee prono = pronoForm.get();
			prono.mettreAJour();
			pronoForm.get().save();
        	flash("success", "Prono " + pronoForm.get().journee + " a été créé (" + prono.pronoMatchs.get(0).gagnant + ").");
        	return GO_HOME;
        }
	}

	public static Result updateProno(Long id) {
		Form<PronoJournee> pronoForm = form( PronoJournee.class ).bindFromRequest();
		if( pronoForm.hasErrors() ) {
        	flash("failure", "Le prono n'a pas été mise à jour.");
			return badRequest( nouveauPronoForm.render( pronoForm ) );
		} else {
			PronoJournee prono = pronoForm.get();
			prono.mettreAJour();
			prono.mettreAJour2(PronoJournee.find.byId(id));
			pronoForm.get().update(id);
	        flash("success", "Le prono a été mise à jour.");
	        return GO_HOME;
        }
	}


	static Form<JourneeTop14> journeeForm = form( JourneeTop14.class );

	public static Result nouveauJournee() {
    	Form<JourneeTop14> journeeForm = form( JourneeTop14.class );
        return ok( nouveauJourneeForm.render( journeeForm ) );
	}

	public static Result sauvegardeJournee() {
    	Form<JourneeTop14> journeeForm = form( JourneeTop14.class ).bindFromRequest();
    	 Map<String, List<ValidationError>> erreurs = journeeForm.errors();
		if( journeeForm.hasErrors() ) {
			return badRequest( nouveauJourneeForm.render( journeeForm ) );
		} else {
			JourneeTop14 journee = journeeForm.get(); 
			journee.miseAJour();
			//journee.miseAJourResultat();
			journee.save();
        	flash("success", "Journée " + journeeForm.get().numero + " a été créé.");
        	return GO_HOME;
        }
	}
}