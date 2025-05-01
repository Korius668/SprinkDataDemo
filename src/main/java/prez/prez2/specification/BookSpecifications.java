package prez.prez2.specification;

import prez.prez2.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    /**
     * Tworzy specyfikację, która filtruje książki, których autor (ignorując wielkość liter)
     * zawiera podany fragment tekstu.
     *
     * @param author Fragment tekstu do wyszukania w polu autora.
     * @return Specyfikacja filtrująca po autorze.
     */
    public static Specification<Book> hasAuthor(String author) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }
    /**
     * Tworzy specyfikację, która filtruje książki opublikowane po podanym roku (włącznie).
     *
     * @param publicationYear Rok publikacji, od którego książki mają być filtrowane.
     * @return Specyfikacja filtrująca po roku publikacji.
     */
    public static Specification<Book> publishedAfterOrEqual(int publicationYear) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publicationYear"), publicationYear);
    }

    public static Specification<Book> publishedBeforeOrEqual(int publicationYear) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("publicationYear"), publicationYear);
    }
}