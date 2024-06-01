package hellojpa.item_inh;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "B")
public class Book_inh extends Item_inh{

    private String author;
    private String isbn;
}
