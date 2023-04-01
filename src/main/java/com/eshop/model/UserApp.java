package com.eshop.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class UserApp {
    @Id
    @SequenceGenerator(sequenceName = "user_sequence", name = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String name;
    private String lastName;
    private String userName;
    private LocalDate dob;
    private String password;
    private LocalDateTime joinedDate;
    @PrimaryKeyJoinColumn
    @Column(nullable = false)
    private String email;
    @Lob
    private String image;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    private boolean enabled;
    private boolean accountLocked;
    private int failedAttempt;
    private Date lockTime;
    private String location;


    public UserApp() {
        this.userName = email != null ? email.substring(0, email.indexOf("@")) : "";
        this.joinedDate = LocalDateTime.now();
        this.enabled = true;
        this.accountLocked = false;
        this.failedAttempt = 0;
    }

    public UserApp(Long id,
                   String name, String lastName,
                   LocalDate dob, String password,
                   String email, String image, String location) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dob = dob;
        this.password = password;
        this.joinedDate = LocalDateTime.now();
        this.email = email;
        this.image = image;
        this.enabled = true;
        this.accountLocked = false;
        this.failedAttempt = 0;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
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

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
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

    public LocalDateTime getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim().toLowerCase();
    }

    public String getImage() {
        return image;
    }

    public void setImgUrl(String image) {
        this.image = image;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public int getFailedAttempt() {
        return failedAttempt;
    }

    public void setFailedAttempt(int failedAttempt) {
        this.failedAttempt = failedAttempt;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName.trim().toLowerCase();
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location.trim().toLowerCase();
    }
}
