package com.engeto.eshop.settings;

import java.nio.file.FileSystems;

public class Settings {

    private static final String PATH_SEPARATOR =
            FileSystems.getDefault().getSeparator();
    private static final String RESOURCES_PATH = "src" + PATH_SEPARATOR
            + "main" + PATH_SEPARATOR + "resources" + PATH_SEPARATOR;
    private static final String FILE = RESOURCES_PATH + "products-data.csv";

    public static String getFile() {
        return FILE;
    }
}
