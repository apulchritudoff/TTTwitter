package com.tts.techtalenttwitter.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tts.techtalenttwitter.Model.Tweet;
import com.tts.techtalenttwitter.Model.User;
import com.tts.techtalenttwitter.Service.TweetService;
import com.tts.techtalenttwitter.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired 
    private TweetService tweetService;

    @GetMapping("/users/{username}")
    public String getUser(@PathVariable(value="username") String username, Model model) {
        // We are going to collect information about username,
        // and display it on the page by passing it to the model

        User user = userService.findByUsername(username);
        List<Tweet> tweets = tweetService.findAllByUserOrderByCreatedAtDesc(user);
        
        List<User> following = loggedInUser.getFollowing();
        boolean isFollowing = false;
        for (User user1 : following) {
            if (user1.getUsername().equals(username)) {
                isFollowing = true;
            }
        }

        return "user";
    }

    

    @GetMapping("/users/")
    public String getUsers(Model model) {
        // We are going to collect information about all users,
        // and display it on the page by passing it to the model

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        setTweetCounts(users, model);
        return "users";
    }

    private void setTweetCounts(List<User> users, Model model) {
       Map<String, Integer> tweetCounts = new HashMap<>();
        for (User user : users) {
            int tweetCount = tweetService.findAllByUser(user).size();
            tweetCounts.put(user.getUsername(), tweetCount);
        }
    }

    private void setFollowingStatus(List<User> users, List<User> following, Model model) {
        Map<String, Boolean> followingStatus = new HashMap<>();
        for (User user : users) {
            boolean isFollowing = false;
            for (User user1 : following) {
                if (user1.getUsername().equals(user.getUsername())) {
                    isFollowing = true;
                }
            }
            followingStatus.put(user.getUsername(), isFollowing);
        }
        model.addAttribute("followingStatus", followingStatus);
    }

}
