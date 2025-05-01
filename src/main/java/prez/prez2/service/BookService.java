package prez.prez2.service;


import prez.prez2.entity.Book;
import prez.prez2.repository.BookRepository;
import prez.prez2.specification.BookSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> search(Optional<String> author, Optional<Integer> publicationYearFrom, Optional<Integer> publicationYearTo) {
        Specification<Book> spec = Specification.where(null);

        if (author.isPresent()) {
            spec = spec.and(BookSpecifications.hasAuthor(author.get()));
        }
        if (publicationYearFrom.isPresent()) {
            spec = spec.and(BookSpecifications.publishedAfterOrEqual(publicationYearFrom.get()));
        }
        if (publicationYearTo.isPresent()) {
            spec = spec.and(BookSpecifications.publishedBeforeOrEqual(publicationYearTo.get()));
        }

        return bookRepository.findAll(spec);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();
        book.setDeleted(true);
        bookRepository.save(book);
    }
}