package eric.clapton.infrastructure.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseUtils {
    protected HttpServletResponseUtils() {

    }

    public static final void forServingFile(HttpServletResponse response, String contentType,
            String fileName) {
        forServingFile(response, contentType, fileName, true);
    }

    public static void forServingFile(HttpServletResponse response, String contentType,
            String fileName, boolean noCache) {
        // no cache
        if (noCache) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "No-cache");
            response.setDateHeader("Expires", 0);
        }

        response.setContentType(contentType);
        String fileNameEncoded;
        try {
            fileNameEncoded = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileNameEncoded = fileName;
        }

        response.setHeader("Content-disposition", "attachment;filename=" + fileNameEncoded);
    }
}
