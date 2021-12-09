package ru.lesson.springBootProject.models;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "userdata", schema = "public")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotEmpty(message = "Username cannot be empty")
    @Column (name = "username", length = 50, unique = true, nullable = false)
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, max=100, message = "Size password must be from 4 to 100")
    @Column (name = "password", length = 100, nullable = false)
    private String password;



    @Column (name ="state")
    @Enumerated(EnumType.STRING)
    private State state;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    @Column (name="email")
    private String email;

    @ManyToMany (fetch = FetchType.LAZY, targetEntity = Book.class, mappedBy = "likes")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Book> likes = new HashSet<>();


    //подобно OneToMany  за тем исключением, что нам не нужно описывает Role как Entity/Embeddable
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    //указываем таблицу для сохранения ролей пользователей
    @CollectionTable(
            name="user_role",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "user_id"),
            uniqueConstraints =  @UniqueConstraint(columnNames = { "user_id", "roles" }, name = "rolesForUserUnique")
    )
    //указываем, хранить значение в String, а не Ordinal(порядковый номер в enum)
    @Enumerated(value = EnumType.STRING)
    private Set<Role> roles;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(
            name = "user_subscription_author",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    @Fetch(FetchMode.SUBSELECT)
    private Set<Author> subscriptions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", targetEntity = Comment.class)
    private Set<Comment> comments = new HashSet<>();

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", state=" + state +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && state == user.state && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, state, email);
    }
}
