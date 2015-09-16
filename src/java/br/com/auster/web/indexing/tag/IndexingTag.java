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
package br.com.auster.web.indexing.tag;

import java.io.IOException;
import java.text.MessageFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import br.com.auster.common.util.I18n;

/**
 * @author framos
 * @version $Id$
 */
public class IndexingTag extends TagSupport {

    
    private I18n i18n = I18n.getInstance(IndexingTag.class);
    
    private String pageId;
    private String totalPages;
    private String pagesIdToDisplay = "5";
    
    private String style = null;
    
    private String[] urlPatterns = new String[5];
    
    
    public static final int INDEXING_FIRST_URL = 0;
    public static final int INDEXING_PREVIOUS_URL = 1;
    public static final int INDEXING_PAGENUMBER_URL = 2;
    public static final int INDEXING_NEXT_URL = 3;
    public static final int INDEXING_LAST_URL = 4;
    
    
    public final void setFirstUrl(String _url) {
        urlPatterns[INDEXING_FIRST_URL] = _url;
    }
    public final String getFirstUrl() {
        return urlPatterns[INDEXING_FIRST_URL];
    }
    
    public final void setPreviousUrl(String _url) {
        urlPatterns[INDEXING_PREVIOUS_URL] = _url;
    }
    public final String getPreviousUrl() {
        return urlPatterns[INDEXING_PREVIOUS_URL];
    }

    public final void setPageIndexUrl(String _url) {
        urlPatterns[INDEXING_PAGENUMBER_URL] = _url;
    }
    public final String getPageIndexUrl() {
        return urlPatterns[INDEXING_PAGENUMBER_URL];
    }

    public final void setNextUrl(String _url) {
        urlPatterns[INDEXING_NEXT_URL] = _url;
    }
    public final String getNextUrl() {
        return urlPatterns[INDEXING_NEXT_URL];
    }

    public final void setLastUrl(String _url) {
        urlPatterns[INDEXING_LAST_URL] = _url;
    }
    public final String getLastUrl() {
        return urlPatterns[INDEXING_LAST_URL];
    }
    
    
    public final void setPageId(String _pageId) {
        this.pageId = _pageId;
    }
    public final String getPageId() {
        return pageId;
    }
    
    public final String getStyle() {
        return style;
    }
    public final void setStyle(String style) {
        this.style = style;
    }
    
    public final String getTotalPages() {
        return totalPages;
    }
    public final void setTotalPages(String totalPages) {
        this.totalPages= totalPages;
    }
    
    public final String getNumberOfPagesIdToDisplay() {
        return pagesIdToDisplay;
    }
    public final void setNumberOfPagesIdToDisplay(String pagesIdToDisplay) {
        this.pagesIdToDisplay = pagesIdToDisplay;
    }
    


    public int doEndTag() throws JspException {
        
        JspWriter out = pageContext.getOut();
        try {
            int nbrToDisplay = Integer.parseInt(getNumberOfPagesIdToDisplay());
            int totPages = Integer.parseInt(getTotalPages());
            int page = Integer.parseInt(getPageId());
            
            if (getStyle() != null) {
                out.println("<font class=\"" + getStyle() + "\">");
            }
            out.print("[ " + printFirstUrl(page));
            out.print(printPreviousUrl(page) + "&nbsp;&nbsp;&nbsp;");
            
            int firstIdx = page - nbrToDisplay/2;
            
            int lastIdx = page + nbrToDisplay/2;
            int addPrevious = Math.max(lastIdx - totPages, 0);            
            int addNext = Math.max(1 - firstIdx, 0);
            
            for (int i=Math.max(1, firstIdx-addPrevious); i < page; i++) {
                out.print(" " + printPageUrl(i, page));
            }
            out.print(" " + printPageUrl(page, page));
            for (int i=page+1; i <= Math.min(totPages, lastIdx+addNext); i++) {
                out.print(" " + printPageUrl(i, page));
            }

            out.print("&nbsp;&nbsp;&nbsp;" + printNextUrl(page, totPages));
            out.println(printLastUrl(page, totPages) + " ]");
            
            out.print("<br>");
            out.print(i18n.getString("indexing.page"));
            out.print(" " + page + " ");
            out.print(i18n.getString("indexing.of"));
            out.print(" " + totPages);
            if (getStyle() != null) {
                out.println("</font>");
            }            
            return EVAL_PAGE;
        } catch (IOException ioe) {
            throw new JspException(ioe);
        }
    }
    
    private String printFirstUrl(int _pageId) {
        if (_pageId <= 1) {
            return i18n.getString("indexing.first");
        } else {
            String url = MessageFormat.format(getFirstUrl(), new Object[] { String.valueOf(1-_pageId) } );
            return "<a href=\"" + url + "\">" + i18n.getString("indexing.first") + "</a>";
        }
    }
    
    private String printPreviousUrl(int _pageId) {
        if (_pageId > 1) {
            String url = MessageFormat.format(getPreviousUrl(), new Object[] { String.valueOf(-1) } );
            return " / <a href=\"" + url + "\">" + i18n.getString("indexing.previous") + "</a>";
        } else {
            return "";
        }
    }
    
    private String printPageUrl(int _id, int _pageId) {
        int move = _id - _pageId;
        if (move != 0) {
            String url = MessageFormat.format(getPageIndexUrl(), new Object[] { String.valueOf(move) } );
            return "<a href=\"" + url + "\">" + String.valueOf(_id) + "</a>";
        } else {
            return String.valueOf(_id);
        }
    }
    
    private String printNextUrl(int _pageId, int _totPages) {
        if (_pageId >= _totPages) {
            return "";
        } else {
            String url = MessageFormat.format(getNextUrl(), new Object[] { String.valueOf(1) } );
            return "<a href=\"" + url + "\">" + i18n.getString("indexing.next") + "</a> / ";
        }
    }
    
    private String printLastUrl(int _pageId, int _totalPages) {
        if (_pageId >= Integer.parseInt(getTotalPages())) {
            return i18n.getString("indexing.last");
        } else {
            String url = MessageFormat.format(getLastUrl(), new Object[] { String.valueOf(_totalPages - _pageId) } );
            return "<a href=\"" + url + "\">" + i18n.getString("indexing.last") + "</a>";
        }
    }    
    
}
