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
 * Created on Mar 17, 2005
 */
package br.com.auster.web.selectbox.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author framos
 * @version $Id$
 */
public class DropDownBoxTag extends BodyTagSupport {


    
    private String style;
    private String name;
    private String defKey;
    
    
    
     
    public final String getDefaultKey() {
        return defKey;
    }
    public final void setDefaultKey(String _key) {
        this.defKey = _key;
    }
    public final String getName() {
        return name;
    }
    public final void setName(String name) {
        this.name = name;
    }
    public final String getStyle() {
        return style;
    }
    public final void setStyle(String _style) {
        this.style = _style;
    }
    
    
    
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("<select name=\"" + getName() + "\" ");
            if (getStyle() != null) {
                out.print(" class=\"" + getStyle() + "\" ");
            }
            out.print(">");
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }        
        return EVAL_BODY_INCLUDE;
    }
    

    public int doEndTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.print("</select>");
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }        
        return EVAL_PAGE;
    }
}
