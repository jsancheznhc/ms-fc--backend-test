package com.scmspain.Constants;

public interface SMCQuerys {
    String ALL_TWEETS = "SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 ORDER BY id DESC";
    String ALL_TWEETS_NOT_DISCARDED = "SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 and discarded IS NULL ORDER BY creation DESC";
    String ALL_DISCARDED_TWEETS = "SELECT id FROM Tweet AS tweetId WHERE pre2015MigrationStatus<>99 and discarded IS NOT NULL ORDER BY discarded DESC";
}
