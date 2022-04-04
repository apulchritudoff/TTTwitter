package com.tts.techtalenttwitter.Controller;

import java.util.List;

import javax.validation.Valid;

import com.tts.techtalenttwitter.Model.Tweet;
import com.tts.techtalenttwitter.Model.User;
import com.tts.techtalenttwitter.Service.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TweetController {

    @Autowired
    private TweetService tweetService;
    
    @GetMapping(value={"/", "/tweets"})
    public String getFeed(Model model) {
        List<Tweet> tweets = tweetService.findAll();
        model.addAttribute("tweetList", tweets);
        return "feed";
    }

    @GetMapping(value={"/tweets/{tag}"})
    public String getTweetsByTag(@PathVariable(value="tag") String tag, Model model) {
        List<Tweet> tweets = tweetService.findAllWithTag(tag);
        model.addAttribute("tweetList", tweets);
        return "feed";
    }

    @GetMapping(value = "/tweets/new")
    public String getTweetForm(Model model) {
        
        model.addAttribute("tweet", new Tweet());
        return "newTweet";
    }

    @PostMapping(value = "/tweets")
    public String submitTweetForm(@Valid Tweet tweet, BindingResult bindingResult, Model model) {
        User user = userService.getLoggedInUser();
        if (!bindingResult.hasErrors()) {
            tweet.setUser(user);
            tweetService.save(tweet);
            model.addAttribute("successMessage", "Tweet successfully created!");
            model.addAttribute("tweet", new Tweet());
        }
        return "newTweet";
    }
    
}
