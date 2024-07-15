package ru.gorchanyuk.securitywithjwt.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.gorchanyuk.securitywithjwt.util.RoleUser;

import java.io.Serializable;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                //Уникальный идентификатор

    @Column(name = "password")
    private String password;        //Пароль

    @Column(name = "last_name")
    private String lastName;        //Фамилия

    @Column(name = "first_name")
    private String firstName;       //Имя

    @Column(name = "username")
    private String username;        //Учетная запись

    @Column(name = "roles")
    @Enumerated(EnumType.ORDINAL)
    private Set<RoleUser> roles;   //Роли пользователя
}
