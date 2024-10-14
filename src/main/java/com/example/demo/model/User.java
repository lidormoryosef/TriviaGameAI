package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 60)
    private String firstName;

    @NotBlank
    @Size(max = 60)
    private String lastName;

    @NotBlank
    @Size(max = 60)
    private String username;

    @NotBlank
    @Size(max = 60)
    private String password;

   public static final class UserBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String username;
        private String password;

        private UserBuilder() {
        }

        public static UserBuilder aUser() {
            return new UserBuilder();
        }

        public UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }
    }


}
