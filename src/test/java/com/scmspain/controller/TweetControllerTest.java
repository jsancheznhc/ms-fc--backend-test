package com.scmspain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scmspain.configuration.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
public class TweetControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;
    private int tweetCreated = 0;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context).build();
    }

    @Test
    public void shouldReturn200WhenInsertingAValidTweet() throws Exception {
        mockMvc.perform(newTweet("Prospect", "Breaking the law"))
                .andExpect(status().is(201));
        tweetCreated++;
    }

    @Test
    public void validTweetWithLink() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net and milanuncios. Welcome!"))
                .andExpect(status().is(201));
        tweetCreated++;
    }

    @Test
    public void validTweetMultipleLinksNo255Characters() throws Exception {
        mockMvc.perform(newTweet("Schibsted Spain", "We are Schibsted Spain (look at our home page http://www.schibsted.es/), we own Vibbo, InfoJobs, fotocasa, coches.net http://www.schibsted.es/ and milanuncios. Welcome!"))
                .andExpect(status().is(201));
        tweetCreated++;
    }

    @Test
    public void invalidTweetNull() throws Exception {
        mockMvc.perform(newTweet(null, null)).andExpect(status().is(400));
    }

    @Test
    public void invalidTweetNullPublisher() throws Exception {
        mockMvc.perform(newTweet(null, "text and text")).andExpect(status().is(400));
    }

    @Test
    public void invalidTweetEmptyPublisher() throws Exception {
        mockMvc.perform(newTweet("     ", "text and text")).andExpect(status().is(400));
    }

    @Test
    public void invalidTweetEmptyTwoPublisher() throws Exception {
        mockMvc.perform(newTweet(" ", "text and text")).andExpect(status().is(400));
    }

    @Test
    public void invalidTweetEmptyThreePublisher() throws Exception {
        mockMvc.perform(newTweet("", "text and text")).andExpect(status().is(400));
    }

    @Test
    public void validDiscardRequest() throws Exception {
        shouldReturn200WhenInsertingAValidTweet();
        mockMvc.perform(newDiscardTweet("1")).andExpect(status().is(202));
    }

    @Test
    public void invalidDiscardRequest() throws Exception {
        shouldReturn200WhenInsertingAValidTweet();
        mockMvc.perform(newDiscardTweet("aaaa")).andExpect(status().is(400));
    }

    @Test
    public void invalidDiscardNullRequest() throws Exception {
        shouldReturn200WhenInsertingAValidTweet();
        mockMvc.perform(newDiscardTweet(null)).andExpect(status().is(400));
    }

    @Test
    public void shouldReturnAllPublishedTweets() throws Exception {
        shouldReturn200WhenInsertingAValidTweet();
        shouldReturn200WhenInsertingAValidTweet();
        shouldReturn200WhenInsertingAValidTweet();

        MvcResult getResult = mockMvc.perform(get("/tweet"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isBetween(tweetCreated , tweetCreated+1);
    }

    @Test
    public void shouldReturnAllDiscardedTweets() throws Exception {
        validDiscardRequest();

        MvcResult getResult = mockMvc.perform(get("/discarded"))
                .andExpect(status().is(200))
                .andReturn();

        String content = getResult.getResponse().getContentAsString();
        assertThat(new ObjectMapper().readValue(content, List.class).size()).isEqualTo(1);
    }

    private MockHttpServletRequestBuilder newTweet(String publisher, String tweet) {

        StringBuffer json = new StringBuffer();
        if (publisher != null && tweet != null) {
            json.append(format("{\"publisher\": \"%s\", \"tweet\": \"%s\"}", publisher, tweet));
        } else {
            json.append("{\"publisher\":").append(publisher).append(",");
            json.append("\"tweet\":").append(tweet).append("}");
        }

        return post("/tweet")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());
    }

    private MockHttpServletRequestBuilder newDiscardTweet(String tweet) {
        StringBuffer json = new StringBuffer();
        if (tweet != null) {
            json.append(format("{\"tweet\": \"%s\"}", tweet));
        } else {
            json.append("{\"tweet\":").append(tweet).append("}");
        }
        return post("/discarded")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json.toString());
    }

}
