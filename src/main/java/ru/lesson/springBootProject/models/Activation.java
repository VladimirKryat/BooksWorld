package ru.lesson.springBootProject.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activation")
public class Activation {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @MapsId
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private User user;
    @Column(name="activation_code")
    private String activationCode;
}
