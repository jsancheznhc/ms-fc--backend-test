package com.scmspain.validations;

import com.scmspain.Constants.SMCValidation;
import com.scmspain.errors.SCMErrors;
import org.springframework.validation.Errors;

public class CommonValidatorUtil {

    protected static void validateEmptyValue(SCMErrors field, String value, Errors errors) {
        if (value == null || "".equals(value)) {
            fillError(field,errors);
        } else if (value != null) {
            String v[] = value.split(SMCValidation.SPACES_PATTERN);
            switch (v.length) {
                case (0):
                    fillError(field,errors);
                default:
                    break;
            }
        }
    }

    protected static void validateStringLongConversion(SCMErrors field, String value, Errors errors) {
        try {
            Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            fillError(field,errors);
        }
    }

    private static void fillError(SCMErrors field, Errors errors) {
        errors.rejectValue(field.getField(), String.valueOf(field.getErrorCode()), field.getErrorDesc());
    }
}
