package com.quiz.quizeegame;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {
    private String userId;
    private String upiAddress;
    private String requestedBy;
    private String statusBy;
    private String coinBy;
    private String statusReject;

    public WithdrawRequest(){

    }

    public WithdrawRequest(String userId, String upiAddress, String requestedBy, String statusBy, String coinBy, String statusReject) {
        this.userId = userId;
        this.upiAddress = upiAddress;
        this.requestedBy = requestedBy;
        this.statusBy = statusBy;
        this.coinBy = coinBy;
        this.statusReject = statusReject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpiAddress() {
        return upiAddress;
    }

    public void setUpiAddress(String upiAddress) {
        this.upiAddress = upiAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getStatusBy() {
        return statusBy;
    }

    public void setStatusBy(String statusBy) {
        this.statusBy = statusBy;
    }

    public String getCoinBy() {
        return coinBy;
    }

    public void setCoinBy(String coinBy) {
        this.coinBy = coinBy;
    }

    public String getStatusReject() {
        return statusReject;
    }

    public void setStatusReject(String statusReject) {
        this.statusReject = statusReject;
    }

    @ServerTimestamp
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "WithdrawRequest{" +
                "userId='" + userId + '\'' +
                ", upiAddress='" + upiAddress + '\'' +
                ", requestedBy='" + requestedBy + '\'' +
                ", statusBy='" + statusBy + '\'' +
                ", coinBy='" + coinBy + '\'' +
                ", statusReject='" + statusReject + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}


//    @Override
//    public String toString() {
//        return "WithdrawRequest{" +
//                "userId='" + userId + '\'' +
//                ", upiAddress='" + upiAddress + '\'' +
//                ", requestedBy='" + requestedBy + '\'' +
//                ", statusBy='" + statusBy + '\'' +
//                ", coinBy='" + coinBy + '\'' +
//                ", createdAt=" + createdAt +
//                '}';
//    }