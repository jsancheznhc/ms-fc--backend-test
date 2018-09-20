package com.scmspain.Constants;

public interface SMCValidation {
    /* A link is any set of non-whitespace consecutive characters starting with http:// or https:// and finishing with a space or ftp:// or ftps:// */
    // String LINK_PATTTERN = "^*(https?://)?(([\\w!~*'().&=+$%-]+: )?[\\w!~*'().&=+$%-]+@)?(([0-9]{1,3}\\.){3}[0-9]{1,3}|([\\w!~*'()-]+\\.)*([\\w^-][\\w-]{0,61})?[\\w]\\.[a-z]{2,6})(:[0-9]{1,4})?((/*)|(/+[\\w!~*'().;?:@&=+$,%#-]+)+/*)";
    String LINK_PATTERN = "(ht|f)tp(s?)://[0-9a-zA-Z-@!~*'().&=+$%#-/?.]+";
    String SPACES_PATTERN = "(\\s)";
    int MAX_TWEET_LENGTH_COLUMN = 255;
    int MAX_TWEET_WITHOUT_LINKS = 140;
}
