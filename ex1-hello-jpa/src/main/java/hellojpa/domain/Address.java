package hellojpa.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Address {

    private String name;
    private String street;
    private String zipcode;

    public Address() {
    }

    public Address(String name, String street, String zipcode) {
        this.name = name;
        this.street = street;
        this.zipcode = zipcode;
    }
}
