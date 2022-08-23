package com.eshop.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(
    uniqueConstraints={
            @UniqueConstraint(name="user_email_unique", columnNames = "email")
    }
)
public class User {
    @Id
    @SequenceGenerator(sequenceName = "user_sequence", name = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String name;
    private String lastName;
    private Short age;
    private LocalDate dob;
    private String password;
    @PrimaryKeyJoinColumn
    @Column(nullable = false)
    private String email;
    private String imgUrl;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public User(){}

    public User(Long id, String name, String lastName, Short age, LocalDate dob, String password, String email, String imgUrl) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.dob = dob;
        this.password = password;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addRole(Role role){
        roles.add(role);
    }
    public void removeRole(Role role){
        getRoles().remove(role);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim().toLowerCase();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim().toLowerCase();
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short age) {
        this.age = age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl.trim();
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}