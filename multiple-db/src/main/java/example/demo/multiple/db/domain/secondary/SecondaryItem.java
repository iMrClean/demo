package example.demo.multiple.db.domain.secondary;

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
@Table(name = "SECONDARY_ITEM")
public class SecondaryItem {

    @Id
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

}
