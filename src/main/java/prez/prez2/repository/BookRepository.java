package prez.prez2.repository;

import  prez.prez2.entity.Book;
import  prez.prez2.dto.BookProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    List<Book> findByAuthorContainingIgnoreCase(String author);

    @Query("SELECT b FROM Book b WHERE b.publicationYear >= :publicationYear")
    List<Book> findBooksPublishedAfter(@Param("publicationYear") int publicationYear);

    @Query("SELECT b.title as title, b.publicationYear as publicationYear FROM Book b")
    List<BookProjection> fetchTitleAndYearOnly();
}