package hellojpa.domain;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Album extends Item {

    private String artist;
    private String etc;

}
