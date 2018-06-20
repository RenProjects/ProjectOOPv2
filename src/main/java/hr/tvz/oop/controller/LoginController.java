package hr.tvz.oop.controller;

import hr.tvz.oop.dao.UserDao;
import hr.tvz.oop.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	private UserDao userDao;

	/**
	 * Metoda za poèetni login ekran. Ako u parametru dobije "error" šalje poruku o pogrešnom korisnièkom imenu/lozinki.
	 * 
	 * @param error
	 * @return ModelAndView sa porukom i/ili login.jsp
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView home( @RequestParam( value = "error", required = false ) String error) {
		ModelAndView model = new ModelAndView();
		
        if( error != null ) {
            model.addObject( "error", "Pogrešno korisnièko ime ili lozinka." );
        }

        model.setViewName( "login" );

        return model;
	}
	
	/**
	 * Inicijalizacija korisnika. Dohvaæa iz Spring Security-a korisnika i stavlja objekt tipa User u sesiju.
	 * 
	 * @param request
	 * @param response
	 * @return quiz.jsp
	 */
	@RequestMapping(value = "/user/initialize", method = RequestMethod.GET)
	public String initialize(HttpServletRequest request,
			HttpServletResponse response) {
		org.springframework.security.core.userdetails.User userSecurity = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();

		User user = userDao.getUserByUsername(userSecurity.getUsername());

		if (user != null) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute(User.USER_IN_SESSION, user);
		}

		return "/secure/quiz";
	}

	/**
	 * Odjava korinika, brisanje sesije.
	 * 
	 * @param httpSession
	 * @param response
	 * @return login.jsp
	 */
	@RequestMapping(value = { "/secure/logout" }, method = RequestMethod.GET)
	public String logout(HttpSession httpSession, HttpServletResponse response) {
		
		httpSession.invalidate();
		SecurityContextHolder.getContext().setAuthentication(null);

		return "login";
	}

}
