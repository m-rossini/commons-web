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
 * Created on 15/05/2006
 */
package br.com.auster.web.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.auster.web.utils.WebUtils;


/**
 *  This servlet implementation allows that shell commands be executed, and its output be displayed in the Web browser.
 *  <p>
 *  To do so, send a request with an attribute named {@link #REQUEST_CMD_ATTR}. Since there is no guarantees regarding the web server current directory,
 *  	all files and commands should be fully qualified. If this attribute is not specified, then nothing will happen an the resulting page will be an empty one.
 *  <p>
 *  Ex:<br>
 *  <code>
 *     http://myhost:1234/myapp/cmdEx?{@value #REQUEST_CMD_ATTR}="pwd"
 *  </code>
 *
 * @author framos
 * @version $Id$
 */
public class CommandExecutorServlet extends HttpServlet {

	
	public static final String REQUEST_CMD_ATTR = "cmd";
	
    
    protected CommandExecutor executor = new CommandExecutor();
    
    
    
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
        this.printHeader(_response.getOutputStream());
        executor.executeCommand((String)WebUtils.findRequestAttribute(_request, REQUEST_CMD_ATTR), _response.getOutputStream());
        this.printFooter(_response.getOutputStream());
    }
    
    
    protected void printHeader(OutputStream _out) throws IOException {
        _out.write("<HTML><BODY>".getBytes());
    }

    protected void printFooter(OutputStream _out) throws IOException {
        _out.write("</BODY></HTML>".getBytes());
    }

}
