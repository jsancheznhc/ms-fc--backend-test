package com.scmspain.validations;

import com.scmspain.Constants.SMCValidation;
import com.scmspain.controller.command.DiscardTweetCommand;
import com.scmspain.errors.SCMErrors;
import com.scmspain.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class DiscardTweetValidator implements Validator {

    private TweetService tweetService;

    @Autowired
    public DiscardTweetValidator(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return DiscardTweetCommand.class.isAssignableFrom(clazz);
        //return Tweet.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        DiscardTweetCommand discardTweetCommand = (DiscardTweetCommand) target;
        String tweetId = discardTweetCommand.getTweet();
        // validations
        CommonValidatorUtil.validateEmptyValue(SCMErrors.NULL_EMPTY_DISCARD_ID, discardTweetCommand.getTweet(), errors);
        // validate length of tweet
        if (!errors.hasErrors()) {
            CommonValidatorUtil.validateStringLongConversion(SCMErrors.DISCARD_ID_NUMERIC, discardTweetCommand.getTweet(), errors);
        }
    }
}
