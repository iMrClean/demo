package example.demo.simple.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ITEM")
public class Item {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

}
