package com.example.chatapp;

import java.net.URI;
public class User {

    public enum Gender{
        Male,
        Female,
        Others
    }

    private String mUserName;
    private String mDisplayName;
    private String mEmailAddress;
    private String mAuthUid;
    private Long mDateOfBirth;
    private Gender mGender;
    private boolean isPrivate;
    private URI mProfilePhoto;

    public User(){}

    public User(String authUid){
        this.mAuthUid = authUid;
    }

    public String getUserName() {
        return mUserName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public String getAuthUid() {
        return mAuthUid;
    }

    public Long getDateOfBirth() {
        return mDateOfBirth;
    }

    public Gender getGender() {
        return mGender;
    }

    public boolean isIsPrivate() {
        return isPrivate;
    }

    public URI getProfilePhoto() {
        return mProfilePhoto;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public void setDisplayName(String displayName) {
        this.mDisplayName = displayName;
    }

    public void setEmailAddress(String emailAddress) {
        this.mEmailAddress = emailAddress;
    }

    public void setAuthUid(String authUid) {
        this.mAuthUid = authUid;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.mDateOfBirth = dateOfBirth;
    }

    public void setGender(Gender gender) {
        this.mGender = gender;
    }

    public void setIsPrivate(boolean makePrivate) {
        this.isPrivate = makePrivate;
    }

    public void setProfilePhoto(URI profilePhoto) {
        this.mProfilePhoto = profilePhoto;
    }

}