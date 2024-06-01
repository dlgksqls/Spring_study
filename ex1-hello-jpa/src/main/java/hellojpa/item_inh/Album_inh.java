package hellojpa.item_inh;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue(value = "A") // DTYPE에 들어가는 값이 Default 값은 클래스 이름,, 하지만 설정 가능
public class Album_inh extends Item_inh{
    private String artist;
}
