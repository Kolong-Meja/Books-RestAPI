package com.book.demo.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.book.demo.enums.Role;
import com.book.demo.utils.VerifiedDataUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.lang.Nullable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Document(collection = "ClientCollection")
public class Client implements UserDetails {
    @Id
    @JsonProperty("id")
    @Schema(type = "string", format = "uuid", description = "ID of the client.")
    private String id;

    @NotNull
    @JsonProperty("fullname")
    @Schema(type = "string", description = "Name of the client.")
    @Field("fullname")
    private String fullname;

    @NotNull
    @Email
    @Indexed(unique = true)
    @JsonProperty("email")
    @Schema(
        type = "string", 
        format = "email", 
        description = "Email of the client.",
        pattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",
        nullable = false
    )
    @Field("email")
    private String email;

    @NotNull
    @JsonProperty("password")
    @Schema(
        type = "string", 
        description = "Password of the client.", 
        pattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
        nullable = false
    )
    @Field("password")
    private String password;

    @JsonProperty("role")
    @Schema(type = "string", description = "Role of the client.")
    @Field("role")
    private Role role;

    @Nullable
    @JsonProperty("createdOn")
    @Schema(type = "string", format = "date-time", description = "Created date time of the client data.")
    @Field("createdOn")
    private LocalDateTime createdOn;

    public Client() {}

    private Client(
        String fullname, 
        String email, 
        String password,
        Role role
    ) {
        this.id = UUID.randomUUID().toString();
        this.fullname = Objects.requireNonNull(fullname);
        this.email = Objects.requireNonNull(VerifiedDataUtil.getVerifiedEmail(email));
        this.password = Objects.requireNonNull(VerifiedDataUtil.getVerifiedPassword(password));
        this.role = Objects.requireNonNull(role);
        this.createdOn = LocalDateTime.now();
    }

    public static Client getInstance(
        String fullname, 
        String email, 
        String password,
        Role role
    ) {
        return new Client(fullname, email, password, role);
    }

    public void changeCurrentId(String newValue) {
        this.id = newValue;
    }

    public void changeFullName(String newValue) {
        this.fullname = newValue;
    }

    public void changeEmail(String newValue) {
        this.email = newValue;
    }

    public void changePassword(String newValue) {
        this.password = newValue;
    }

    public void changeRole(Role newValue) {
        this.role = newValue;
    }

    public void changeCreatedOn(LocalDateTime newDateTime) {
        this.createdOn = newDateTime;
    }

    public String getCurrentId() {
        return this.id;
    }

    public String getFullName() {
        return this.fullname;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }

    @Override
    public String toString() {
        return String.format("Cleint{id=%s, fullname=%s, email=%s, password=%s, role=%s, createdOn=%s}", id, fullname, email, password, role, createdOn);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;   
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
}
