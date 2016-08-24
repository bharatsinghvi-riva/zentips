package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Subscriptions {

    private static final HashMap<String, Set<String>> subscriptions = new HashMap<>();
    private static final HashMap<String, Set<String>> subscribers = new HashMap<>();

    public Set<String> getSubscribersForTag(String tag) {
        return subscribers.containsKey(tag) ? subscribers.get(tag) : new HashSet<>();
    }

    public Set<String> getSubscriptionsForUser(String userId) {
        return subscriptions.containsKey(userId) ? subscriptions.get(userId) : new HashSet<>();
    }

    public void addSubscription(String tag, String userId) {
        if (!subscriptions.containsKey(userId)) subscriptions.put(userId, new HashSet<>());
        subscriptions.get(userId).add(tag);
        if (!subscribers.containsKey(tag)) subscribers.put(tag, new HashSet<>());
        subscribers.get(tag).add(userId);
    }

    public void removeSubscription(String tag, String userId) {
        if (subscribers.containsKey(tag)) subscribers.get(tag).remove(userId);
        if (subscriptions.containsKey(userId)) subscriptions.get(userId).remove(tag);
    }
}
