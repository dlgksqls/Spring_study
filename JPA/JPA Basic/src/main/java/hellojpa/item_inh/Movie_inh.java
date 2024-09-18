package hellojpa.item_inh;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "M")
public class Movie_inh extends Item_inh{

    private String director;
    private String actor;
}
