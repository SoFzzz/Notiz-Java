package com.notiz.model;

import java.util.List;

public interface Exportable {
    List<Object> toExportRow();
    List<String> getExportHeaders();
    String getExportFileName();
}
