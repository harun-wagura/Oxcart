package DownloadServlet;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@SuppressWarnings("DuplicatedCode")
@WebServlet("/DownloadFileServlet")
public class DownloadFileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Read the file using FileInputStream
        String filePath = "/home/harun/Downloads/Q3.pdf";
        File downloadFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // Get the MIME type of the file
        ServletContext context = getServletContext();
        String mimeType = context.getMimeType(filePath);

        // If MimeType mapping is not found, read the file in binary
        if(mimeType == null){
            mimeType = "application/octet-stream";
        }

        // Update the response object by setting the mime type and file length
        response.setContentType(mimeType);
        int contentLength = (int)downloadFile.length();
        response.setContentLength(contentLength);

        // Force file download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey,headerValue);

        // Obtain and process the outputStream
        OutputStream outputStream = response.getOutputStream();
        // Create an array to read data byte by byte
        byte[] buffer = new byte[4096]; // Read in 4MB chunks
        int bytesRead = -1;

        while((bytesRead = inputStream.read(buffer))!=-1){
            outputStream.write(buffer,0,bytesRead);
        }

        // Close both input and output streams
        inputStream.close();
        outputStream.close();

    }
}
