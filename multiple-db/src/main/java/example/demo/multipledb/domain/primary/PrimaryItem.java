package example.demo.multipledb.domain.primary;

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
@Table(name = "PRIMARY_ITEM", schema = "PRIMARY")
public class PrimaryItem {

    @Id
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

}
