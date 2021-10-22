package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.lesson.springBootProject.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
//    @Query(nativeQuery = true, value = "SELECT * FROM userdata LEFT JOIN user_role USING(user_id) where username=?")
    Optional<User> findByUsername(String username);
//    @Query(nativeQuery = true, value = "SELECT * FROM userdata LEFT JOIN user_role USING(user_id)")
//    List<User> findAllWithRoles();
}
