package com.disarch.datasource;

import org.apache.commons.lang3.RandomUtils;

public class DatasourceHolder {
    private static final ThreadLocal<String> datasourceKey = new ThreadLocal();

    public DatasourceHolder() {
    }

    public static void setDatasourceKey(String key) {
        datasourceKey.set(key);
    }

    public static void setMaster(String datasource) {
        datasourceKey.set(datasource + "-master");
    }

    public static void setSlave(String datasource) {
        datasourceKey.set(datasource + "-slave" + (RandomUtils.nextInt(0, 1) + 1));
    }

    public static String getDatasourceKey() {
        return (String) datasourceKey.get();
    }
}
