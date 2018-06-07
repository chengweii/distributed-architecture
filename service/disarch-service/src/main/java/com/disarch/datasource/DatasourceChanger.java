package com.disarch.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

public class DatasourceChanger {
    private String datasourceKey;
    private String writeMethodPrefix;

    public DatasourceChanger(String datasourceKey, String writeMethodPrefix) {
        this.datasourceKey = datasourceKey;
        this.writeMethodPrefix = writeMethodPrefix;
    }

    public void change(JoinPoint jp) {
        if (jp != null) {
            MethodSignature methodSignature = (MethodSignature) jp.getSignature();
            String methodName = methodSignature.getName();
            String[] writeMethodPrefixArray = StringUtils.delimitedListToStringArray(writeMethodPrefix, ",");
            for (String prefix : writeMethodPrefixArray) {
                if (methodName.startsWith(prefix)) {
                    DatasourceHolder.setSlave(this.datasourceKey);
                    return;
                }
            }
        }

        DatasourceHolder.setMaster(this.datasourceKey);
    }
}