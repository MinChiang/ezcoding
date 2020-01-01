package ${componentPath};

import com.ezcoding.common.foundation.core.exception.ApplicationException;

/**
* @author ${author}
* @version 1.0.0
* @date ${date}
*/
public class ${componentName} extends AbstractApplicationException {

private static final String MODULE_NUM = "${moduleNum!"00"}";

public ${componentName}(String orginalCode) {
super(orginalCode);
}

public ${componentName}(String orginalCode, String message) {
super(orginalCode, message);
}

public ${componentName}(String orginalCode, Throwable cause) {
super(orginalCode, cause);
}

public ${componentName}(String orginalCode, String message, Throwable cause) {
super(orginalCode, message, cause);
}

@Override
public String moduleCode() {
return MODULE_NUM;
}
}