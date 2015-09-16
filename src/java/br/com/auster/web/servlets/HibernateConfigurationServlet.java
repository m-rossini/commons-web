/*
 * Copyright (c) 2004-2007 Auster Solutions. All Rights Reserved.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Created on 09/04/2007
 */
package br.com.auster.web.servlets;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.auster.common.util.I18n;

/**
 * @author framos
 * @version $Id$
 *
 */
public class HibernateConfigurationServlet extends HttpServlet {

	
    private static Logger log = Logger.getLogger(HibernateConfigurationServlet.class);
    private static I18n i18n = I18n.getInstance(HibernateConfigurationServlet.class);
	
	
	public static final String RESOURCE_PATH = "resource";
	public static final String NAMING_PATH = "jndi-path";
	public static final String TOKEN_DELIM = "/.";
	
	protected String resourcePath;
	protected String warResourcePath;
	protected String jndiPath;
	
	

	/**
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig _config) throws ServletException {
		super.init(_config);
		// reading init. parameters from configuration element
		this.resourcePath = _config.getInitParameter(RESOURCE_PATH);
		this.jndiPath = _config.getInitParameter(NAMING_PATH);
		// making sure mandatory init. parameters are set
		if ((this.jndiPath == null) || (this.resourcePath == null)) {			
			throw new ServletException(i18n.getString("hibernate.initParamsNotSet", RESOURCE_PATH, NAMING_PATH));
		}
		log.info(i18n.getString("hibernate.initParamIs", RESOURCE_PATH, this.resourcePath));
		log.info(i18n.getString("hibernate.initParamIs", NAMING_PATH, this.jndiPath));
		try {
			Configuration cfg = new Configuration();
			URL resourceUrl = this.getServletContext().getResource(this.resourcePath);
			if (resourceUrl == null) {
				throw new ServletException(i18n.getString("hibernate.resourceNotFound", this.resourcePath));
			}
			cfg.configure(resourceUrl);
			SessionFactory sf = cfg.buildSessionFactory();
			log.debug(i18n.getString("hibernate.sessionFactory.done"));
			// adding session factory to naming env.
			log.debug(i18n.getString("hibernate.jndi.preparing", this.jndiPath));
			StringTokenizer tokeninzer = new StringTokenizer(this.jndiPath, TOKEN_DELIM);
			Context ctx = new InitialContext();
			String token = null;
			while (tokeninzer.hasMoreTokens()) {
				token = tokeninzer.nextToken();
				if (!tokeninzer.hasMoreTokens()) {
					// when found last token, exit!
					log.debug(i18n.getString("hibernate.jndi.lastToken", token));
					break;
				}
				// adding/loading current part of the jndi path
				try {
					ctx = ctx.createSubcontext(token);
				} catch (NameAlreadyBoundException nabe) {
					log.warn(i18n.getString("hibernate.jndi.alreadyExists", token, ctx.getNameInNamespace()));
					ctx = (Context) ctx.lookup(token);
				}
				log.debug(i18n.getString("hibernate.jndi.tokenHandled", token));
			}
			ctx.rebind(token, sf);
			log.info(i18n.getString("hibernate.sessionFactory.bound", token, ctx.getNameInNamespace()));
		} catch (NamingException ne) {
			throw new ServletException(ne);
		} catch (MalformedURLException mfurle) {
			throw new ServletException(mfurle);
		}
	}
}
