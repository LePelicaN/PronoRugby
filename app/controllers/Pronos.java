package controllers;

import java.util.List;

import play.mvc.Controller.*;
import play.*;
import play.data.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import play.data.validation.ValidationError;
import views.html.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import play.*;
import play.data.validation.ValidationError;
import static play.data.Form.*;
import models.JourneeTop14;
import models.Joueur;
import models.PronoJournee;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.nouveauPronoForm;
import views.html.pronos.*;

public class Pronos extends Controller {
	
    public static Result GO_PRONOS_HOME = redirect(routes.Pronos.blank());

	public static Result blank() {
        return ok( general.render() );
	}


	public static Result nouveauChoixJoueurEtJournee() {
        Form<PronoJournee> pronoForm = form( PronoJournee.class );
        return ok( nouveauChoixJoueurEtJournee.render( pronoForm ) );
	}

	public static Result nouveauDetail(Long joueurId, Long journeeId) {
        Joueur joueur = Joueur.find.byId(joueurId);
        JourneeTop14 journee = JourneeTop14.find.byId(journeeId);
        PronoJournee pronoJournee = new PronoJournee(joueur, journee);

        Form<PronoJournee> pronoForm = form( PronoJournee.class ).fill(pronoJournee);
        return ok( nouveauDetail.render( pronoJournee, pronoForm, true ) );
	}

    public static Result editer(Long id) {
    	PronoJournee pronoJournee = PronoJournee.find.byId(id);
        Form<PronoJournee> pronoJourneeForm = form(PronoJournee.class).fill(pronoJournee);
        //return ok( nouveauDetail.render( pronoJournee, pronoJourneeForm, false ) );
        return ok( editer.render(pronoJournee, pronoJourneeForm, false) );
    }

    public static Result mettreAJour(Long id) {
        Form<PronoJournee> pronoJourneeForm = form(PronoJournee.class).bindFromRequest();
        if(pronoJourneeForm.hasErrors()) {
            return GO_PRONOS_HOME;
        }
        pronoJourneeForm.get().mettreAJour();
        pronoJourneeForm.get().mettreAJour2(PronoJournee.find.byId(id));
        pronoJourneeForm.get().update(id);
        return GO_PRONOS_HOME;
    }

	public static Result nouveauOuEditer(String nomJoueur, String numeroJournee) {
		Joueur joueur = Joueur.find
				              .where()
				              .eq( "nom", nomJoueur )
				              .findUnique();
		JourneeTop14 journee = JourneeTop14.find
				                           .where()
				                           .eq( "numero", numeroJournee )
				                           .findUnique();

		if (joueur != null && journee != null) {
			PronoJournee pronoJournee = PronoJournee.find
					                                .where()
					                                .eq( "idJoueurManual", joueur.id )
					                                .eq( "idJourneeManual", journee.id )
					                                .findUnique();
			if (pronoJournee != null) {
	        	flash("info", "Editer id n°" + pronoJournee.id);
				return editer(pronoJournee.id);
			} else {
	        	flash("info", "Nouveau detail");
				return nouveauDetail(joueur.id, journee.id);
			}
				
		}
		else {
        	flash("info", "Nouveau prono");
	        return Application.nouveauProno();
		}
    }

	public static Result nouveauOuEditer2() {
        DynamicForm requestData = form().bindFromRequest();
        String nomJoueur = requestData.get("nomJoueur");
        String numeroJournee = requestData.get("numeroJournee");
        if (nomJoueur == null || numeroJournee == null) {
        	flash("failure", "Impossible de comprende le nom du joueur (" + nomJoueur + ") ou le numero de la journee (" + numeroJournee + ")");
        }
        if (nomJoueur != null && numeroJournee != null) {
        	return nouveauOuEditer(nomJoueur, numeroJournee);
        }
        else {
			return GO_PRONOS_HOME;
        }
	}

    public static Result supprimer(Long id) {
    	PronoJournee.find.ref(id).delete();
        return GO_PRONOS_HOME;
    }


}
