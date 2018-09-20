package com.scmspain.validations;

import com.scmspain.Constants.SMCValidation;
import com.scmspain.controller.command.PublishTweetCommand;
import com.scmspain.errors.SCMErrors;
import com.scmspain.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PublishTweetValidator implements Validator {

    private TweetService tweetService;

    @Autowired
    public PublishTweetValidator(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PublishTweetCommand.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PublishTweetCommand publishTweetCommand = (PublishTweetCommand) target;
        // validations
        CommonValidatorUtil.validateEmptyValue(SCMErrors.NULL_EMPTY_TWEET, publishTweetCommand.getTweet(), errors);
        CommonValidatorUtil.validateEmptyValue(SCMErrors.NULL_EMPTY_PUBLISHER, publishTweetCommand.getPublisher(), errors);
        // validate length of tweet
        if (!errors.hasErrors()) {
            validateTweet(publishTweetCommand.getTweet(), errors);
        }
    }

    private void validateTweet(String tweet, Errors errors) {
        String groups[] = tweet.split(SMCValidation.LINK_PATTERN);
        StringBuffer sb = new StringBuffer();
        for (String group : groups) {
            sb.append(group);
        }
        if ((tweet.length()> SMCValidation.MAX_TWEET_LENGTH_COLUMN) || (sb.length() > SMCValidation.MAX_TWEET_WITHOUT_LINKS)) {
            errors.rejectValue(SCMErrors.TWEET_TOO_LONG.getField(), String.valueOf(SCMErrors.TWEET_TOO_LONG.getErrorCode()), SCMErrors.TWEET_TOO_LONG.getErrorDesc());
        }

    }
}
