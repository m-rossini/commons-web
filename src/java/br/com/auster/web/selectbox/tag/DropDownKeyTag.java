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
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * @author framos
 * @version $Id$
 */
public class DropDownKeyTag extends BodyTagSupport {

    
    public String key;
    public String list;
    
    
    public final String getKey() {
        return key;
    }
    public final void setKey(String key) {
        this.key = key;
    }
    
    public final String getKeyList() {
        return list;
    }
    public final void setKeyList(String list) {
        this.list = list;
    }
    
    
    public int doEndTag() throws JspException {
        DropDownBoxTag parentTag = (DropDownBoxTag) this.getParent();
        try {
            JspWriter out = pageContext.getOut();
            if (getKey() != null) {
                printOption(out, parentTag.getDefaultKey(), getKey(), getBodyContent().getString());
            } else {
                printOptionList(out, parentTag.getDefaultKey());
            }
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
        return EVAL_PAGE;
    }
   
    private void printOptionList(JspWriter _out, String _defaultValue) throws IOException, JspException {
        
        try {
            Map optionList = (Map) pageContext.getAttribute(getKeyList());
            if (optionList != null) {
                Iterator iterator = optionList.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    printOption(_out, _defaultValue, key, (String) optionList.get(key));
                }
            }
        } catch (ClassCastException cce) {
            throw new JspException(cce);
        }
    }
    
    private void printOption(JspWriter _out, String _defaultValue, String _key, String _label) throws IOException {
        _out.print("<option value=\"" + _key + "\"");
        if ((_defaultValue != null) && (_defaultValue.equals(_key))) {
            _out.print(" selected ");
        }
        _out.print(">");
        _out.print(_label);
        _out.println("</option>");
    }
}
