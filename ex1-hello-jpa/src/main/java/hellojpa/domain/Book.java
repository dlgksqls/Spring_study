package hellojpa.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Book extends Item {
    private String author;
    private String isbn;
}
