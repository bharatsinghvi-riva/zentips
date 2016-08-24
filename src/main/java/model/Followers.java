package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Followers {

    private static final HashMap<String, HashSet<String>> followers = new HashMap<>();

    public void addFollower(String follow, String follower) {
        if (!followers.containsKey(follow)) followers.put(follow, new HashSet<>());
        followers.get(follow).add(follower);
    }

    public boolean isFollower(String follow, String follower) {
        return followers.containsKey(follow) && followers.get(follow).contains(follower);
    }

    public void removeFollower(String follow, String follower) {
        if (followers.containsKey(follow)) {
            followers.get(follow).remove(follower);
        }
    }

    public List<String> getFollowers(String userId) {
        return new ArrayList<>(followers.get(userId));
    }
}
