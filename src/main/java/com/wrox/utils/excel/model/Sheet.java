package com.wrox.utils.excel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dengbin on 2015/10/12.
 */
public class Sheet {

    private List<Map<String, Object>> contents;

    public Sheet() {
        contents = new ArrayList<>();
    }

    public List<Map<String, Object>> getContents() {
        return contents;
    }
}
