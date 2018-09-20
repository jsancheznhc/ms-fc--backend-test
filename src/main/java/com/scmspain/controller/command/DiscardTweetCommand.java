package com.scmspain.controller.command;

public class DiscardTweetCommand {
    private String tweet;

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweetId) {
        this.tweet = tweetId;
    }

    public Long getTweetAsLong() {
        return Long.valueOf(tweet);
    }
}
