package tomekkup.helenos.web.servlet.view;

import java.io.BufferedWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class CsvView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setHeader("Content-Disposition","attachment; filename=\"" + (String)model.get("key") + "_" + (String)model.get("key") + ".csv\"");
        BufferedWriter writer = new BufferedWriter(response.getWriter());
        
        //myDbData = (Whatever) modelMap.get("modelKey");
        //some kind of loop {writer.write(myDbData csv row); writer.newLine(); }
        writer.flush();
        writer.close();
    }
}
