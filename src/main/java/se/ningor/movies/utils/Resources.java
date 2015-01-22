package se.ningor.movies.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide easy access to application global resources.
 * 
 * @author mats
 *
 */
public class Resources {
	
	public static final String BUNDLE_BASE_NAME = "se.ningor.movies.messages";
	
	@Produces
	@PersistenceContext(unitName="primary")
	private EntityManager em;
	
	@Produces
	public Logger produceLog(InjectionPoint injectionPoint) {
		return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
	}
	
	/**
	 * Retrieves a message from the declared resource-bundle with the current locale.
	 * 
	 * @param messageKey
	 * @return
	 */
	public static String getMessageFromBundle(String messageKey) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Locale locale = facesContext.getViewRoot().getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale);
		return bundle.getString(messageKey);
	}

}
