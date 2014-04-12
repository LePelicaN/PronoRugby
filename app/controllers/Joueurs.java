package controllers;

import play.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.*;
import views.html.joueurs.*;

import models.JourneeTop14;

public class Joueurs extends Controller {
	
    public static Result GO_JOUEURS_HOME = redirect(routes.Joueurs.blank());
	
	public static Result blank() {
		return ok( views.html.joueurs.general.render( Joueur.all() ) );
	}
/*
    public static Result editer(Long id) {
        Form<JourneeTop14> journeeTop14Form = form(JourneeTop14.class).fill(
        	JourneeTop14.find.byId(id)
        );
        return ok( editer.render(id, journeeTop14Form) );
    }

    public static Result mettreAJour(Long id) {
        Form<JourneeTop14> journeeTop14Form = form(JourneeTop14.class).bindFromRequest();
        if(journeeTop14Form.hasErrors()) {
            return badRequest(editer.render(id, journeeTop14Form));
        }
        JourneeTop14 journeeTop14 = journeeTop14Form.get();
        journeeTop14.miseAJourResultat();
        journeeTop14.update(id);
        return GO_JOUEURS_HOME;
    }

    public static Result supprimer(Long id) {
    	JourneeTop14.find.ref(id).delete();
        return GO_JOUEURS_HOME;
    }*/

}
