package com.skcc.cloudz.zcp.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

public class FileUtil {
    
    //public final static String PATH = "C:\\cloud\\temp\\upload\\";

    public static void fileDownload(HttpServletResponse response, File file) throws IOException {
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + java.net.URLEncoder.encode(file.getName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;
        
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException ex) {
            }
        }
        out.flush();
    }
    
    public static void fileWrite(File file, String content) {
        try {
            FileWriter fw = new FileWriter(file, true) ;
            fw.write(content);
            fw.flush();
            fw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean fileDelete(String fileName) {
        boolean ret = false;
        
        try {
            if(fileName != null) { 
                File file = new File(fileName);
                ret = file.delete();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return ret;
    }
}
