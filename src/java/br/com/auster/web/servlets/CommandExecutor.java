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
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;


/**
 * Executes a command and sends all results from both standard output and error streams to the specified <code>OutputStream</code>.
 * <p>
 * Each one of these two streams are preceded with a title named {@value #STDOUT_TITLE} or {@value #STDERR_TITLE}.
 * 
 * @author framos
 * @version $Id$
 */
public class CommandExecutor {

    
    public static final String NL = System.getProperty("line.separator");
    
    public static final String STDERR_TITLE = "ERROR OUTPUT";
    public static final String STDOUT_TITLE = "STANDARD OUTPUT";
    
    
    
    public void executeCommand(String _command, OutputStream _out) throws ServletException, IOException {
        if (_command == null) {
            return;
        }
        Process p = Runtime.getRuntime().exec(_command);
        try {
            p.waitFor();
        } catch (InterruptedException ie) {
            throw new ServletException(ie);
        }
        printStream(_out, p.getErrorStream(), STDERR_TITLE);
        printStream(_out, p.getInputStream(), STDOUT_TITLE);
    }
    
    protected void printStream(OutputStream _out, InputStream _in, String _title) throws IOException {
        // write title
        _out.write(NL.getBytes());
        _out.write(_title.getBytes());        
        _out.write(NL.getBytes());
        // write input stream content
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = _in.read(buffer)) > 0) {
            _out.write(buffer, 0, readBytes);
        }
    }    
    
}
