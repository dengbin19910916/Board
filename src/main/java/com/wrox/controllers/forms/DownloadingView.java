package com.wrox.controllers.forms;

import org.springframework.web.servlet.View;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by dengb on 2015/9/10.
 */
public class DownloadingView implements View {

    private final String filename;
    private final String contentTtype;
    private final byte[] contents;

    public DownloadingView(String filename, String contentTtype, byte[] contents) {
        this.filename = filename;
        this.contentTtype = contentTtype;
        this.contents = contents;
    }

    @Override
    public String getContentType() {
        return this.contentTtype;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition", "attachment; filename=" + this.filename);
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(this.contents);
    }
}
