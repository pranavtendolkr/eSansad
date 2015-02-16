package com.persistent.esansad.helper;

import java.io.Serializable;

/**
 * Created by pranav_tendolkar on 2/7/2015.
 */


//{
//        "category": "Health And Family Welfare",
//        "submitted_by": "Cindy Kimball",
//        "description": "Lets dedicate some money towards Procurement of hospital equipments for Govt. hospitals. Money spent on this will greatly benefit the people of the constituency.",
//        "title": "Procurement of hospital equipments for Govt. hospitals",
//        "vote_count": {
//        "upvotes": 382,
//        "downvotes": 853
//        },
//        "cost": 2.523,
//        "status": "completed",
//        "constituency": "Andaman and Nicobar Islands",
//        "submission_date": "2014-10-24"
//        }

public class Proposal implements Serializable{
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubmitted_by() {
        return submitted_by;
    }

    public void setSubmitted_by(String submitted_by) {
        this.submitted_by = submitted_by;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getSubmission_date() {
        return submission_date;
    }

    public void setSubmission_date(String submission_date) {
        this.submission_date = submission_date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int  upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    private String category;
    private String submitted_by;
    private String description;
    private String title;
    private String status;
    private String constituency;
    private String submission_date;

    public String getSubmitted_by_email() {
        return submitted_by_email;
    }

    public void setSubmitted_by_email(String submitted_by_email) {
        this.submitted_by_email = submitted_by_email;
    }

    public String getProposal_id() {
        return proposal_id;
    }

    public void setProposal_id(String proposal_id) {
        this.proposal_id = proposal_id;
    }

    private String submitted_by_email;
    private String proposal_id;
    private double cost;
    private int upvotes,downvotes;



}
