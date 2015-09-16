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
 * Created on 16/05/2006
 */
package br.com.auster.web.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;



/**
 *  This servlet implementation allows that files be uploaded into the filesystem of the Web server.
 *  <p>
 *  To do so, build a JSP file which posts a <strong>multi-part</strong> request to this servlet. This page can also define the destination directory in the Web server, sending 
 *  	an attribute named {@value #REQUEST_FILEPATH_ATTR}. 
 *  <p>
 *   If such attribute is not specified then the Web server current directory is used as destination to the uploading files. And, if  no files are specified, nothing will happen. 
 *  <p>
 *  But, the servlet will force that the request be a multi-part one. If not, then an exception will be raised. 
 *  <p>
 *  The servlet supports uploading more then one file at once. They will all be written using the system user running the Web server; so this user <strong>must have</strong> write
 *  	permissions into such directory. Also, if the destination file path does not exist, the servlet will attempt to create it. So, again, the Web server user has to have to correct permissions
 *    to do so.
 *  
 * @author framos
 * @version $Id$
 */
public class UploadFileServlet extends HttpServlet {

    
	
	public static final String REQUEST_FILEPATH_ATTR = "filepath";
	

    /**
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        this.doGet(_request, _response);
    }
    
    /**
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest _request, HttpServletResponse _response) throws ServletException, IOException {
        upload(_request);
    }    
    
    
    protected void upload(HttpServletRequest  _request) throws IOException, ServletException {

        if (! ServletFileUpload.isMultipartContent(_request)) {
            throw new ServletException("cannot handle request if its not multiparted");
        }
        
        ServletFileUpload uploader = new ServletFileUpload(new DiskFileItemFactory());
        try {
            List files = uploader.parseRequest(_request);
            // setting the output dir.
            String filepath = System.getProperty("user.dir");
            for (Iterator it = files.iterator(); it.hasNext(); ) {
                FileItem fitem = (FileItem) it.next();
                if (fitem.isFormField() && fitem.getFieldName().equals(REQUEST_FILEPATH_ATTR)) {
                    filepath = fitem.getString();
                }
            }
            // writing out the uploaded files
            for (Iterator it = files.iterator(); it.hasNext(); ) {
                FileItem fitem = (FileItem) it.next();
                if (fitem.isFormField()) { continue; }
                File f = new File(filepath + System.getProperty("file.separator") + fitem.getName());
                f.getParentFile().mkdirs();
                fitem.write(f);
            }
            
        } catch (FileUploadException fue) {
            throw new ServletException("Error parsing request", fue);
        } catch (Exception e) {
            throw new ServletException("Error writing contents to file", e);
        }
    }
}
