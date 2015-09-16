/*
 * Copyright (c) 2004 TTI Tecnologia. All Rights Reserved.
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
 * Created on Sep 15, 2004
 *
 */
package br.com.auster.web.checklogon.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author Frederico A Ramos
 * @version $Id: CheckUserLogonTag.java,v 1.2 2004/09/25 10:54:25 framos Exp $
 */
public class CheckUserLogonTag extends BodyTagSupport {

    
    private String sessionKey;    
    
    
	public CheckUserLogonTag() {
		super();
	}
	
    
    
    public void setSessionKey(String _key) {
        sessionKey = _key;
    }
    
    public String getSessionKey() {
        return sessionKey;
    }


    
    public int doStartTag() throws JspException {
		
		HttpSession session = pageContext.getSession();
        if (session != null) {
    		if (session.getAttribute(getSessionKey()) != null) { 
    			return SKIP_BODY;
            }
		}
		return EVAL_BODY_INCLUDE;
	}

    
    public int doEndTag() throws JspException {
		
		HttpSession session = pageContext.getSession();
        if (session != null) {
    		if (session.getAttribute(getSessionKey()) != null) { 
    			return EVAL_PAGE;
            }
		}
		return SKIP_PAGE;
	}
    
}
