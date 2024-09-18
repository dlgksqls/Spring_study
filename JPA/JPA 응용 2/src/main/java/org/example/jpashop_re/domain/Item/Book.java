package org.example.jpashop_re.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue(value = "B") // 컬럼에 어떻게 들어갈 것인가?
public class Book extends Item{

    private String author;
    private String isbn;
}
