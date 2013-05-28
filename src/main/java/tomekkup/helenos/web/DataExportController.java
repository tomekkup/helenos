package tomekkup.helenos.web;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import tomekkup.helenos.web.servlet.view.CsvView;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class DataExportController extends AbstractController {
    
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String keyspace = ServletRequestUtils.getRequiredStringParameter(request, "keyspace");
        String columnFamily = ServletRequestUtils.getRequiredStringParameter(request, "cf");
        String key = ServletRequestUtils.getRequiredStringParameter(request, "key");
        
        Map<String, Object> model = prepareModel(keyspace, columnFamily, key);
        return new ModelAndView(new CsvView(), model);
    }
    
    private Map<String, Object> prepareModel(String keyspace, String columnFamily, String key) {
        Map<String, Object> model = new HashMap<String, Object>();
        
        return model;
    }
    
}
