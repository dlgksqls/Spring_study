package hellojpa.parentChild;

import hellojpa.domain.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Child {

    @Id @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    private Address homeAddress;

    @ManyToOne()
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;
}
