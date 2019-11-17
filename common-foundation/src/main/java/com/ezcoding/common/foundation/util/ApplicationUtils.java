package com.ezcoding.common.foundation.util;

import com.ezcoding.common.foundation.core.application.ApplicationMetadata;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-08 16:56
 */
public class ApplicationUtils {

    private static ApplicationMetadata applicationMetadata;

    public static ApplicationMetadata getApplicationMetadata() {
        return applicationMetadata;
    }

    public static void setApplicationMetadata(ApplicationMetadata applicationMetadata) {
        ApplicationUtils.applicationMetadata = applicationMetadata;
    }

}
