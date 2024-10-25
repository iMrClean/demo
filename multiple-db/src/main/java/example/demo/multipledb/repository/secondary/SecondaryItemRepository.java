package example.demo.multipledb.repository.secondary;

import example.demo.multipledb.domain.secondary.SecondaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryItemRepository extends JpaRepository<SecondaryItem, Long> { }
