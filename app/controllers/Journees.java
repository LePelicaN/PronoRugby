package controllers;

import play.*;
import play.data.*;
import static play.data.Form.*;
import models.*;
import play.data.Form;
import play.data.validation.ValidationError;
import play.mvc.*;
import views.html.journees.*;

import models.JourneeTop14;


// 
// http://sports.orange.fr/rugby/top-14/2014/resultats/26e-journee.html
// (..)/(..)/(..)\s*(..)h(..)\s*([a-zA-Z_]*)\s[a-zA-Z_]*\s*([a-zA-Z_]*)\s[a-zA-Z_]*.*
// '\6', '\7', '20\3-\2-\1 \4:\5:00'

public class Journees extends Controller {
	
    public static Result GO_JOURNEES_HOME = redirect(routes.Journees.blank());
	
	public static Result blank() {
		return ok( views.html.journees.general.render( JourneeTop14.all() ) );
	}

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
        journeeTop14.miseAJour();
        journeeTop14.miseAJour2(JourneeTop14.find.byId(id));
        journeeTop14.update(id);
        return GO_JOURNEES_HOME;
    }

    public static Result supprimer(Long id) {
    	JourneeTop14.find.ref(id).delete();
        return GO_JOURNEES_HOME;
    }

}
