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
package br.com.auster.web.sorting.tag;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author framos
 * @version $Id$
 */
public class SortUrlBuilderTag extends TagSupport {

    
    
    
    private String orderKey;
    private String urlPattern;
    private String currentOrderKey;
    private String currentOrientation;
    
    private String backwardFlag;
    private String forwardFlag;
    
    private String target;
    
    
    
    public final void setUrl(String _url) {
        urlPattern = _url;
    }
    public final String getUrl() {
        return urlPattern;
    }
    

    public final String getCurrentOrderKey() {
        return currentOrderKey;
    }
    public final void setCurrentOrderKey(String currentOrderKey) {
        this.currentOrderKey = currentOrderKey;
    }
    public final String getCurrentOrientation() {
        return currentOrientation;
    }
    public final void setCurrentOrientation(String currentOrientation) {
        this.currentOrientation = currentOrientation;
    }
    public final String getOrderKey() {
        return orderKey;
    }
    public final void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
    public final String getBackwardFlag() {
        return backwardFlag;
    }
    public final void setBackwardFlag(String backwardFlag) {
        this.backwardFlag = backwardFlag;
    }
    public final String getForwardFlag() {
        return forwardFlag;
    }
    public final void setForwardFlag(String forwardFlag) {
        this.forwardFlag = forwardFlag;
    }
    public final String getTargetWindow() {
        return target;
    }
    public final void setTargetWindow(String target) {
        this.target = target;
    }

    
    
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            String orientation = getForwardFlag();
            if (getCurrentOrderKey().equals(getOrderKey())) {
                if (getCurrentOrientation().equals(orientation)) {
                    orientation = getBackwardFlag();
                }
            }
            out.print("<a href=\"" + MessageFormat.format(getUrl(), new String[] { getOrderKey(), orientation }) + "\"");
            if (target != null) {
                out.print(" target=\"" + target + "\"");
            }
            out.println(" >");
            return EVAL_BODY_INCLUDE;
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
    }

    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        try {
            out.print("</a>");
            return EVAL_PAGE;
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
    }
    
}
