package com.example.dylan.finalprojectdylanalvin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gamma on 11/22/2015.
 */
public class YoutubeHelper {
    /**
     * An array of YouTube videos
     */
    public static List<YouTubeVideo> ITEMS = new ArrayList<>();

    /**
     * A map of YouTube videos, by ID.
     */
    public static Map<String, YouTubeVideo> ITEM_MAP = new HashMap<>();

    static {
        addItem(new YouTubeVideo("sGbxmsDFVnE", "Star Wars: The Force Awakens Trailer (Official)"));
        addItem(new YouTubeVideo("erLk59H86ww", "Star Wars: The Force Awakens Official Teaser"));
        addItem(new YouTubeVideo("ngElkyQ6Rhs", "Star Wars: The Force Awakens Official Teaser #2"));
        addItem(new YouTubeVideo("9owoYz5ikvI", "Star Wars: The Force Awakens TV Spot (Official)"));
        addItem(new YouTubeVideo("TpBcTMGsiOM", "Star Wars: The Force Awakens TV Spot 2 (Official)"));
        addItem(new YouTubeVideo("t562L6n4Lu4", "Star Wars The Force Awakens International Trailer #1"));
    }

    private static void addItem(final YouTubeVideo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A POJO representing a YouTube video
     */
    public static class YouTubeVideo {
        public String id;
        public String title;

        public YouTubeVideo(String id, String content) {
            this.id = id;
            this.title = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
