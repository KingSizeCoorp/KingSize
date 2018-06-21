package com.movinghead333.kingsize.ui.myfeed;

public class CardDetails {
    private Integer Id;
    private String type;
    private String title;
    private String description;
    private String positive_votes;
    private String negative_votes;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getPositive_votes() {
        return positive_votes;
    }

    public void setPositive_votes(String positive_votes) {
        this.positive_votes = positive_votes;
    }

    public String getNegative_votes() {
        return negative_votes;
    }

    public void setNegative_votes(String negative_votes) {
        this.negative_votes = negative_votes;
    }


}
