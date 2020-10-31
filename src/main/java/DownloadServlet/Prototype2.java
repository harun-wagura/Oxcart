package DownloadServlet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class Prototype2 extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the file path
        String filePath = "/home/harun/Downloads/Q3.pdf";
        File downloadFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // Get the mimeType of the file
        ServletContext  context = getServletContext();
        String mimeType = context.getMimeType(filePath);

        // Fetch file in binary there's no MimeType mapping
        if(mimeType==null) mimeType = "application/octet-stream";
        // Log the mimeType in the console
        System.out.println(mimeType);


        //Set the response mimeType/contentType and file length
        response.setContentType("Mime Tyoe: " + mimeType);
        int fileLength = (int) downloadFile.length();
        response.setContentLength(fileLength);

        // Set the response header value// Force download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey,headerValue);

        // read the input stream in buffered chunks to the output stream
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[4096]; // Buffers of 4MB's
        int c = -1;

        while((c = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, c);
        }

        // Close the input and output streams
        inputStream.close();
        outputStream.close();

    }
}
