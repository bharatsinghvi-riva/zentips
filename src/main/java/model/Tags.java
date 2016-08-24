package model;

import java.util.HashSet;

public class Tags {

    private static final HashSet<String> tags = new HashSet<>();

    public HashSet<String> getTags() {
        return tags;
    }

    public boolean addTag(String tag) {
        return tags.add(tag);
    }
}
