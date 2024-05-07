package com.quiz.QuizApp.models;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Data
@Builder

@Entity
@Table(name = "USERS")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer id;

    @Column(name = "email")
    public String email;

    @Column(name = "first_name")
    @NotEmpty(message = "Enter the first name of the user")
    private String first_name;

    @Column(name = "last_name")
    @NotEmpty(message = "Enter the last name of the user")
    private String last_name;

    @Column(name = "phone_no")
    private Integer phone_no;

    @Column(name = "passwrd")
    private String passwrd;

    @Enumerated(EnumType.ORDINAL)
    private Role role;

    
      public void setRole(Role role) {
        this.role = role;
      }
     
    public Role getRole() {
        // System.out.println(" imsde user jabva get role" , role);
        return role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(Integer phone_no) {
        this.phone_no = phone_no;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    @Override

    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(" inside user java getauthorities " + role.getAuthorities());

        return role.getAuthorities();
        // return List.of(new SimpleGrantedAuthority(role.name()));

    }

    @Override
    public String getPassword() {
        return passwrd;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    /*
     * @Override
     * public String toString(){
     * StringBuilder sb = new StringBuilder();
     * sb.append("{");
     * sb.append("\"id\":\"" + id + "\"");
     * sb.append("\"email\":\"" + email + "\"");
     * sb.append("\"first_name\":\"" + first_name + "\"");
     * sb.append("\"last_name\":\"" + last_name + "\"");
     * sb.append("\"role\":\"" + role + "\"");
     * sb.append("}");
     * return sb.toString();
     * }
     * 
     */

}
