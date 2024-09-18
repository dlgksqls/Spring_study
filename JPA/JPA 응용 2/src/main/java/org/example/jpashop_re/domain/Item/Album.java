package org.example.jpashop_re.domain.Item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@DiscriminatorValue(value = "A") // 컬럼에 어떻게 들어갈 것인가?
public class Album extends Item{

    private String artist;
    private String etc;
}
