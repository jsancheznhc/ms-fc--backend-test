package com.scmspain.controller;

import com.scmspain.controller.command.DiscardTweetCommand;
import com.scmspain.controller.command.PublishTweetCommand;
import com.scmspain.entities.Tweet;
import com.scmspain.services.TweetService;
import com.scmspain.validations.DiscardTweetValidator;
import com.scmspain.validations.PublishTweetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class TweetController {
    private TweetService tweetService;

    @Autowired
    private PublishTweetValidator publishTweetValidator;
    @Autowired
    private DiscardTweetValidator discardTweetValidator;

    @InitBinder("publishTweetCommand")
    public void initPublishTweetCommandBinder(WebDataBinder binder) {
        binder.addValidators(publishTweetValidator);
    }

    @InitBinder("discardTweetCommand")
    public void initDiscardTweetCommandBinder(WebDataBinder binder) {
        binder.addValidators(discardTweetValidator);
    }

    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("/tweet")
    public List<Tweet> listAllTweets() {
        return this.tweetService.listAllTweets();
    }

    @GetMapping("/discarded")
    public List<Tweet> listAllDiscardedTweets() {
        return this.tweetService.listAllDiscardedTweets();
    }

    @PostMapping("/tweet")
    @ResponseStatus(CREATED)
    public void publishTweet(@Valid @RequestBody PublishTweetCommand publishTweetCommand) {
        this.tweetService.publishTweet(publishTweetCommand.getPublisher(), publishTweetCommand.getTweet());
    }

    @PostMapping("/discarded")
    @ResponseStatus(ACCEPTED)
    public void discardTweet(@Valid @RequestBody DiscardTweetCommand discardTweetCommand) {
        this.tweetService.discardTweet(discardTweetCommand.getTweetAsLong());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    public Object invalidArgumentException(IllegalArgumentException ex) {
        return new Object() {
            public String message = ex.getMessage();
            public String exceptionClass = ex.getClass().getSimpleName();
        };
    }

}
