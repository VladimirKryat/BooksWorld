package ru.lesson.springBootProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lesson.springBootProject.models.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
