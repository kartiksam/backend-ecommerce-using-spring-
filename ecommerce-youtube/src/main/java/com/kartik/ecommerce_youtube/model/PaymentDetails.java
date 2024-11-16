package com.kartik.ecommerce_youtube.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//jb embbedded no need entuity and primnary key
public class PaymentDetails {



    private String paymentMethod;
    private String paymentStatus;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;

    public PaymentDetails() {
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRazorpayPaymentLinkId() {
        return razorpayPaymentLinkId;
    }

    public void setRazorpayPaymentLinkId(String razorpayPaymentLinkId) {
        this.razorpayPaymentLinkId = razorpayPaymentLinkId;
    }

    public String getRazorpayPaymentLinkReferenceId() {
        return razorpayPaymentLinkReferenceId;
    }

    public void setRazorpayPaymentLinkReferenceId(String razorpayPaymentLinkReferenceId) {
        this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
    }

    public String getRazorpayPaymentLinkStatus() {
        return razorpayPaymentLinkStatus;
    }

    public void setRazorpayPaymentLinkStatus(String razorpayPaymentLinkStatus) {
        this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
    }

    public String getRazorpayPaymentd() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentd(String razorpayPaymentd) {
        this.razorpayPaymentId = razorpayPaymentd;
    }

    public PaymentDetails(String paymentMethod, String paymentStatus, String paymentId, String razorpayPaymentLinkId, String razorpayPaymentLinkReferenceId,
                          String razorpayPaymentLinkStatus, String razorpayPaymentId) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentId = paymentId;
        this.razorpayPaymentLinkId = razorpayPaymentLinkId;
        this.razorpayPaymentLinkReferenceId = razorpayPaymentLinkReferenceId;
        this.razorpayPaymentLinkStatus = razorpayPaymentLinkStatus;
        this.razorpayPaymentId = razorpayPaymentId;
    }
}
