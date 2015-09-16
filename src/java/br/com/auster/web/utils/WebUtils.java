/*
 * Copyright (c) 2004 Auster Solutions. All Rights Reserved.
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
 * Created on Mar 14, 2005
 */
package br.com.auster.web.utils;

import javax.portlet.PortletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author framos
 * @version $Id$
 */
public abstract class WebUtils {


    /**
     * Finds and, if found, returns the value of the specified attribute. The attribute can be set as either 
     *      attribute or parameter of the current request object. If the parameter is not found, then <code>null</code> is returned.
     * <p>
     * This method works only with Servlet/JSP requests. For Portlets, use {@link #findRequestAttribute(PorletRequest, String)}.      
     *        
     * @param _request the current request
     * @param _key the paremeter key
     * 
     * @return the parameter value
     */
    public static Object findRequestAttribute(ServletRequest _request, String _key) {
        Object obj = _request.getAttribute(_key);
        if (obj == null) {
            obj = _request.getParameter(_key);
        }
        return obj;
    }

    /**
     * Needed due to compatibility with previous versions.
     * <BR>
     * Works just as the {@link #findRequestAttribute(ServletRequest, String)} version.
     */
    public static Object findRequestAttribute(HttpServletRequest _request, String _key) {
        Object obj = _request.getAttribute(_key);
        if (obj == null) {
            obj = _request.getParameter(_key);
        }
        return obj;
    }
    
    /**
     * Finds and, if found returns, the value of the specified attribute. The attribute can be set as either 
     *      attribute or parameter of the current request object. If the parameter is not found, then <code>null</code> is returned.
     * <p>
     * This method works only with Portlets requests. For Servlet/JSP, use {@link #findRequestAttribute(ServletRequest, String)}.      
     *         
     * @param _request the current request
     * @param _key the paremeter key
     * 
     * @return the parameter value
     */
    public static Object findRequestAttribute(PortletRequest _request, String _key) {
        Object obj = _request.getAttribute(_key);
        if (obj == null) {
            obj = _request.getParameter(_key);
        }
        return obj;
    }    
    
    /**
     * Removes the attribute from the current request. Also, if the key was defined as a parameter, it will be
     *      nulled since there is no API to remove paremeters from a request.
     * <p>
     * This method works only with Servlet/JSP requests. For Portlets, use {@link #removeRequestAttribute(PorletRequest, String)}.      
     * 
     * @param _request the current request
     * @param _key the paremeter key
     * 
     * @return if the key was found, and consequently removed
     */
    public static boolean removeRequestAttribute(ServletRequest _request, String _key) {
        if (_request.getAttribute(_key) != null) {
            _request.removeAttribute(_key);
        } else if (_request.getParameter(_key) != null) {
            _request.setAttribute(_key, null);
        } else {
            return false;
        }
        return true;
    }
    
    /**
     * Needed due to compatibility with previous versions.
     * <BR>
     * Works just as the {@link #removeRequestAttribute(ServletRequest, String)} version.
     */
    public static boolean removeRequestAttribute(HttpServletRequest _request, String _key) {
        if (_request.getAttribute(_key) != null) {
            _request.removeAttribute(_key);
        } else if (_request.getParameter(_key) != null) {
            _request.setAttribute(_key, null);
        } else {
            return false;
        }
        return true;
    }
    
    
    /**
     * Removes the attribute from the current request. Also, if the key was defined as a parameter, it will be
     *      nulled since there is no API to remove paremeters from a request.
     * <p>
     * This method works only with Portlets requests. For Servlet/JSP, use {@link #removeRequestAttribute(ServletRequest, String)}.      
     * 
     * @param _request the current request
     * @param _key the paremeter key
     * 
     * @return if the key was found, and consequently removed
     */
    public static boolean removeRequestAttribute(PortletRequest _request, String _key) {
        if (_request.getAttribute(_key) != null) {
            _request.removeAttribute(_key);
        } else if (_request.getParameter(_key) != null) {
            _request.setAttribute(_key, null);
        } else {
            return false;
        }
        return true;
    }
    
}
