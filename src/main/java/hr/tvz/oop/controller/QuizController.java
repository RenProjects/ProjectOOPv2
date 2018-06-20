package hr.tvz.oop.controller;

import hr.tvz.oop.dao.ImageDao;
import hr.tvz.oop.dao.ResultsDao;
import hr.tvz.oop.dao.UserDao;
import hr.tvz.oop.model.Image;
import hr.tvz.oop.model.User;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuizController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private ImageDao imageDao;

	@Autowired
	private ResultsDao resultsDao;

	/**
	 * Metoda za dohvat quiz.jsp
	 * 
	 * @param httpSession
	 * @param response
	 * @return quiz.jsp
	 */
	@RequestMapping(value = "/secure/quiz", method = RequestMethod.GET)
	public String images(HttpSession httpSession, HttpServletResponse response) {
		return "secure/quiz";
	}

	/**
	 * Dohvat slika za kviz.
	 * 
	 * @return listu sa dvije slike
	 */
	@RequestMapping(value = "/getImages", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<Image> getTime() {

		List<Image> images = imageDao.getRandomImages();

		for (Image image : images) {
			String randomChar = RandomStringUtils.randomAlphabetic(1)
					.toUpperCase();
			String tempChar;

			for (Image image2 : images) {

				if (image2.getCharacter() != null) {
					if (image2.getCharacter().equals(randomChar)) {
						tempChar = image2.getCharacter();
						do {
							randomChar = RandomStringUtils.randomAlphabetic(1)
									.toUpperCase();
						} while (randomChar.equals(tempChar));
					}
				}
			}

			image.setCharacter(randomChar);
		}

		return images;
	}

	/**
	 * Dohvat konfiguracije korisnika - broj iteracija slika.
	 * 
	 * @param httpSession
	 * @param response
	 * @return broj iteracija slika
	 */
	@RequestMapping(value = "/config", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getUserConfig(HttpSession httpSession,
			HttpServletResponse response) {
		User user = (User) httpSession.getAttribute(User.USER_IN_SESSION);
		if (user != null) {
			int initializeQuizId = resultsDao.initializeQuiz(user.getId());
			httpSession.setAttribute(User.USER_QUIZ_ID, initializeQuizId);

			int userIterations = userDao.getUserIterations(user.getId());
			return Integer.toString(userIterations);
		}
		return null;
	}

	/**
	 * Pohrana odgovora korisnika.
	 * 
	 * @param imageId
	 * @param isCorrect
	 * @param httpSession
	 */
	@RequestMapping(value = "/anwser", method = RequestMethod.POST)
	public void setAnwser(@RequestParam(value="imageId") int imageId, @RequestParam(value="isCorrect") boolean isCorrect, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute(User.USER_IN_SESSION);
		Integer quizId = (Integer) httpSession.getAttribute(User.USER_QUIZ_ID);
		
		if (user != null && quizId != null) {
			resultsDao.saveResult(imageId, quizId, isCorrect);
		}
	}
	
	/**
	 * Završetak kviza.
	 * 
	 * @param httpSession
	 * @param response
	 * @return end.jsp
	 */
	@RequestMapping(value = "/secure/end", method = RequestMethod.GET)
	public String end(HttpSession httpSession, HttpServletResponse response) {
		User user = (User) httpSession.getAttribute(User.USER_IN_SESSION);
		Integer quizId = (Integer) httpSession.getAttribute(User.USER_QUIZ_ID);
		
		if (user != null && quizId != null) {
			resultsDao.finishQuiz(user.getId(), quizId);
		}
		
		return "/secure/end";
	}

}
