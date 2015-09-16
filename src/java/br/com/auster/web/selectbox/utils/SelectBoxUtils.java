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
package br.com.auster.web.selectbox.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.auster.common.util.I18n;

/**
 * @author framos
 * @version $Id$
 */
public abstract class SelectBoxUtils {

    
    
    private static Logger log = Logger.getLogger(SelectBoxUtils.class);
    private static I18n i18n = I18n.getInstance(SelectBoxUtils.class);

    
    
    /**
     * Filters the incoming list for all elements which <code>_field</code>'s value matches the specified condition. The matching elements
     *  will be added to a separate list and returned. If the incoming list, the condition or the field name are <code>null</code> then the
     *  incoming list, unmodified, will be returned.
     * <P>
     * Any exceptions during the verification of the values for the element's fields will be logged as WARN messages and the filtering process
     *  will continue. Note that in those cases the element <strong>WILL NOT</strong> be added to the result list.  
     * 
     * @param _sourceList the incoming list of elements
     * @param _condition the conidition value
     * @param _field the field to match the condition
     * 
     * @return the list of elements with <code>_field</code> value matching the condition 
     */
    public static List filterList(List _sourceList, String _condition, String _field) {
        return filterList(_sourceList, _condition, _field, false);
        
    }
    
    /**
     * Filters the incoming list for all elements which <code>_field</code>'s value matches the specified condition. The elements that do not
     *  match this condition will be remoted from the list. If the incoming list, the condition or the field name are <code>null</code> then the
     *  incoming list, unmodified, will be returned.
     * <P>
     * Any exceptions during the verification of the values for the element's fields will be logged as WARN messages and the filtering process
     *  will continue. Note that in those cases the element <strong>WILL NOT</strong> be removed from the list.  
     * 
     * @param _sourceList the incoming list of elements
     * @param _condition the conidition value
     * @param _field the field to match the condition
     * 
     * @return the incoming list with non-matching elements removed 
     */
    public static List filterList(List _sourceList, String _condition, String _field, boolean _modifySource) {
        
        if ((_sourceList == null) || (_condition == null) || (_field == null)) {
            return _sourceList;
        }
        List finalList = null;
        if (! _modifySource) {
            finalList = new ArrayList(_sourceList.size()/2 + 1);
        }
        ListIterator iterator = _sourceList.listIterator();
        while (iterator.hasNext()) {
            Object currentElement = iterator.next();
            String content = getFieldAsString(currentElement, _field);
            if (content == null) {
                continue;
            } else if ((content.indexOf(_condition) >= 0) && (!_modifySource)) {
                finalList.add(currentElement);
            } else if ((content.indexOf(_condition) < 0) && (_modifySource)) {
                iterator.remove();
            }
        }
        if (_modifySource) {
            return _sourceList;
        }
        return finalList;
    }
    
    private static String getFieldAsString(Object _element, String _field) {
        String methodName = "get" + _field.substring(0,1).toUpperCase();
        if (_field.length() > 1) {
            methodName += _field.substring(1);
        }
        try {
            Method method = _element.getClass().getMethod(methodName, null);
            Object result = method.invoke(_element, null);
            if (result != null) {
                return result.toString();
            }
        } catch (NoSuchMethodException nsme) {
            log.warn(i18n.getString("selectbox.utils.exceptionCaught", nsme.getMessage()));
        } catch (IllegalArgumentException iae) {
            log.warn(i18n.getString("selectbox.utils.exceptionCaught", iae.getMessage()));
        } catch (IllegalAccessException iae) {
            log.warn(i18n.getString("selectbox.utils.exceptionCaught", iae.getMessage()));
        } catch (InvocationTargetException ite) {
            log.warn(i18n.getString("selectbox.utils.exceptionCaught", ite.getMessage()));
        }
        return null;

    }
    
    
    public static Map filterMap(Map _sourceList, String _condition, String _field) {
        
        if ((_sourceList == null) || (_condition == null) || (_field == null)) {
            return _sourceList;
        }
        Map finalList = new HashMap(_sourceList.size()/2 + 1);
        Iterator iterator = _sourceList.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry currentElement = (Map.Entry) iterator.next();
            String key = (String) currentElement.getKey();
            if (key.indexOf(_condition) >= 0) {
                finalList.put(key, currentElement.getValue());
            }
        }
        return finalList;
    }
    
}
