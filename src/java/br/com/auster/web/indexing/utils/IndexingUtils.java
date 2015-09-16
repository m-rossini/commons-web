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
package br.com.auster.web.indexing.utils;

/**
 * @author framos
 * @version $Id$
 */
public abstract class IndexingUtils {

    
    /** 
     * Returns the position of the first element to be displayed, according to the current page number
     * 
     * @param _pageId the current page number
     * @param _pageLen the number of elemtents per page
     * 
     * @return the first element to be displayed
     */
    public static int getStartingElement(int _pageId, int _pageLen) {
        return (_pageLen * (_pageId-1));
    }
    
    
    /**
     * Determines the number of pages into which the curent collection will be partitioned, according to the
     *  max. number of elements per page
     * 
     * @param _colLen the size of the collection
     * @param _pageLen the number of elements per page
     * 
     * @return the total number of pages 
     */
    public static int getNumberOfPages(int _colLen, int _pageLen) {
        int len = (_colLen / _pageLen) +
        ( ((_colLen % _pageLen) == 0) ? 0 : 1 );         
        if (len == 0) { len = 1; }
        return len;
    }
    
    /**
     * Returns the number of the next page which will be displayed
     * 
     * @param _currentPage the current displayed page
     * @param _moveTo the number of pages to move (forward or backward)
     * 
     * @return the number of the next page to be displayed
     */
    public static int getDisplayPageId(int _currentPage, int _moveTo) {
        int nextPage = _currentPage + _moveTo;
        if (nextPage <= 0) {
            return 1;
        }
        return nextPage;
    }
    
}
