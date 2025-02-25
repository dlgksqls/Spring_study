package hello.springtx.propagation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
public class Log {

    @Id @GeneratedValue
    private Long id;
    private String message;

    public Log(String message) {
        this.message = message;
    }

    public Log() {
    }
}
