package org.example.jpashop_re.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue(value = "M") // 컬럼에 어떻게 들어갈 것인가?
public class Movie extends Item{

    private String director;
    private String actor;
}
