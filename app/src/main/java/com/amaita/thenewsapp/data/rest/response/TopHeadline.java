package com.amaita.thenewsapp.data.rest.response;


import com.amaita.thenewsapp.data.database.Article;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopHeadline {

    @SerializedName("status")
    public String status;

    @SerializedName("totalResults")
    public int totalResults;

    @SerializedName("articles")
    public List<Article> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
