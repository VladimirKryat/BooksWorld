package ru.lesson.springBootProject.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
@Table(name = "userdata", schema = "public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long userId;

    @NotEmpty(message = "Username cannot be empty")
    @Column (name = "username", length = 50, unique = true, nullable = false)
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 4, max=20, message = "Size password must be from 4 to 20")
    @Column (name = "password", length = 100, nullable = false)
    private String password;
    @NotEmpty(message = "Password confirmation cannot be empty")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Transient
    private String passwordConfirm;



    @Column (name ="state")
    @Enumerated(EnumType.STRING)
    private State state;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email is not correct")
    @Column (name="email")
    private String email;


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


}
