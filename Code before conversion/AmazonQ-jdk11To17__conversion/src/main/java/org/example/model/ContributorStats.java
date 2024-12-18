package org.example.model;


import lombok.Data;

import java.util.List;

@Data
public class ContributorStats {
    private Author author;
    private int total;
    private List<Week> weeks;

    @Data
    public static class Author {
        private String login;
        private int id;
        private String avatar_url;
        private String url;
    }

    @Data
    public static class Week {
        private String w;
        private int a;
        private int d;
        private int c;
    }
}