package com.test.model;

import com.test.enums.Status;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int phone;

    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @Column(name = "verification", unique = true)
    private String verification;

    private long verificationTime;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "resetPasswordCode", unique = true)
    private String resetPasswordCode;

    private long resetPasswordTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public long getVerificationTime() {
        return verificationTime;
    }

    public void setVerificationTime(long verificationTime) {
        this.verificationTime = verificationTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }

    public long getResetPasswordTime() {
        return resetPasswordTime;
    }

    public void setResetPasswordTime(long resetPasswordTime) {
        this.resetPasswordTime = resetPasswordTime;
    }

    @Override
    public String toString() {
        return "AbstractModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", verification='" + verification + '\'' +
                ", verificationTime=" + verificationTime +
                ", status=" + status +
                ", resetPasswordCode='" + resetPasswordCode + '\'' +
                ", resetPasswordTime=" + resetPasswordTime +
                '}';
    }
}
