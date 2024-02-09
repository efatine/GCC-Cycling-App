package com.example.cyclingclub;

public class ClubFeedback {

    //System for the feedback system for clubs
    private int clubRating;
    private String commentFeedback;

    private String participantUsername;

    public ClubFeedback(int clubRating, String commentFeedback, String participantUsername) {
        this.clubRating = clubRating;
        this.commentFeedback = commentFeedback;
        this.participantUsername = participantUsername;
    }

    public void setClubRating(int clubRating) {
        this.clubRating = clubRating;
    }

    public void setCommentFeedback(String commentFeedback) {
        this.commentFeedback = commentFeedback;
    }

    public void setParticipantUsername(String participantUsername) {
        this.participantUsername = participantUsername;
    }

    public int getClubRating() {
        return clubRating;
    }

    public String getCommentFeedback() {
        return commentFeedback;
    }

    public String getParticipantUsername() {
        return participantUsername;
    }
}
