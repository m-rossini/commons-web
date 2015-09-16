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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.auster.common.io.CompressUtils;
import br.com.auster.common.io.FileSet;
import br.com.auster.web.utils.WebUtils;


/**
 *  This servlet implementation allows that files in the filesystem of the Web server be downloaded.
 *  <p>
 *  To do so, send a request with an attribute named {@link #REQUEST_FILE_ATTR}. Since there is no guarantees regarding the web server current directory,
 *  	the filename must be fully qualified. If such attribute is not specified, then an exception will be displayed in the Web browser.
 *  <p>
 *  This attribute can define a single file or a group, using a file mask. Either way, the file(s) will be zipped and sent as a <code>.zip</code> file.
 *  <p>
 *  Ex: This URL will download the<code> mydocs.txt</code> file, at <code>/home/myself</code> dir.
 *  <br>
 *  <code>
 *     http://myhost:1234/myapp/Download?{@value #REQUEST_FILE_ATTR}="/home/myself/mydocs.txt"
 *  </code>
 *  <br>
 *  Ex: This URL will download all <code>.doc</code> files, at <code>/home/myself</code> dir.
 *  <code>
 *     http://myhost:1234/myapp/Download?{@value #REQUEST_FILE_ATTR}="/home/myself/*.doc"
 *  </code>
 * @author framos
 * @version $Id$
 */
public class DownloadFileServlet extends HttpServlet {

	
	
	public static final String REQUEST_FILE_ATTR = "file";
	
	
    
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
        download((String)WebUtils.findRequestAttribute(_request, REQUEST_FILE_ATTR), _response);
    }
        
    
    protected void download(String _filename, HttpServletResponse _response) throws ServletException, IOException {
        if (_filename != null) {
            String basedir = ".";
            if(_filename.startsWith(File.separator)) {
                basedir = File.separator;
                _filename = _filename.substring(1);
            } else if(_filename.charAt(1)==':') {
                basedir = _filename.substring(0,3);
                _filename = _filename.substring(3);
            }
            File[] files = FileSet.getFiles(basedir, _filename);
            if ((files == null) || (files.length <= 0)) {
                throw new IOException("no files found for path " + _filename);
            }
            File ftemp = File.createTempFile("download-", ".zip");
            ftemp = CompressUtils.createZIPBundle(Arrays.asList(files), ftemp.getAbsolutePath());
            writeFile(new FileInputStream(ftemp), ftemp.getName(), _response);
        } else {
            throw new ServletException("no file name/mask was specified");
        }
    }
    
    protected void writeFile(InputStream _inS, String _filename, HttpServletResponse _response) throws IOException {
        _response.setContentType("application/zip; charset=utf-8");
        _response.setHeader("Content-Disposition", "attachment;filename=\"" + _filename + "\"");          
        OutputStream outS = _response.getOutputStream();
        int readBytes = 0;
        byte[] bufferIn = new byte[32000];
        // writing input bytes to output file
        while ((readBytes = _inS.read(bufferIn)) > 0) {
            outS.write(bufferIn, 0, readBytes);
        }
        outS.close();
    }
}
