package com.medsec.entity;

import com.medsec.util.UserRole;

import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
public class User implements Principal {
	private String password;
	private String id;
	private String surname;
	private String firstname;
	private String middlename;
	private LocalDate dob;
	private String email;
	private String street;
	private String suburb;
	private String state;
	private String token;
	private Instant token_expire_date;
	private Instant token_valid_from;
    private UserRole role;

    public User() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getToken_expire_date() {
        return token_expire_date;
    }

    public void setToken_expire_date(Instant token_expire_date) {
        this.token_expire_date = token_expire_date;
    }

    public Instant getToken_valid_from() {
        return token_valid_from;
    }

    public void setToken_valid_from(Instant token_valid_from) {
        this.token_valid_from = token_valid_from;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public User password(final String password) {
        setPassword(password);
        return this;
    }

    public User id(final String id) {
        setId(id);
        return this;
    }

    public User surname(final String surname) {
        setSurname(surname);
        return this;
    }

    public User firstname(final String firstname) {
        setFirstname(firstname);
        return this;
    }

    public User middlename(final String middlename) {
        setMiddlename(middlename);
        return this;
    }

    public User dob(final LocalDate dob) {
        setDob(dob);
        return this;
    }

    public User email(final String email) {
        setEmail(email);
        return this;
    }

    public User street(final String street) {
        setStreet(street);
        return this;
    }

    public User suburb(final String suburb) {
        setSuburb(suburb);
        return this;
    }

    public User state(final String state) {
        setState(state);
        return this;
    }

    public User token(final String token) {
        setToken(token);
        return this;
    }

    public User token_expire_date(final Instant token_expire_date) {
        setToken_expire_date(token_expire_date);
        return this;
    }

    public User token_valid_from(final Instant token_valid_from) {
        setToken_valid_from(token_valid_from);
        return this;
    }

    public User role(final UserRole role) {
        setRole(role);
        return this;
    }

    @Override
    public String getName() {
        return id;
    }
}
