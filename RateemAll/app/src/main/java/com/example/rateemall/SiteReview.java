package com.example.rateemall;

public class SiteReview {
    private String site;
    private String rate;

    public SiteReview(String site, String rate) {
        this.site = site;
        this.rate = rate;
    }

    public String getSite() {
        return site;
    }

    public String getRate() {
        return rate;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
