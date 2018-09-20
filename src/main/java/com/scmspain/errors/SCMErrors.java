package com.scmspain.errors;

public enum SCMErrors {

    TWEET_TOO_LONG(100,"Tweet","Tweet is too long, cannot greater than 255 characters or 140 characters without count links (http, https, ftp and ftps) if contains links ending with space."),
    NULL_EMPTY_TWEET(102,"Tweet","Tweet cannot be null or empty value."),
    NULL_EMPTY_PUBLISHER(103,"Publisher","Publisher cannot be null or empty."),
    DISCARD_ID_NUMERIC(104,"tweet","Tweet id should be numeric characters"),
    NULL_EMPTY_DISCARD_ID(105,"tweet","Tweet Id cannot be null or empty.");

    private String errorDesc;
    private String field;
    private int errorCode;

    SCMErrors(int errorCode, String field, String errorDesc)
    {
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.field = field;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getField() {
        return field;
    }
}
