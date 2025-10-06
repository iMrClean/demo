package example.demo.multiple.db.repository.secondary;

import example.demo.multiple.db.domain.secondary.SecondaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryItemRepository extends JpaRepository<SecondaryItem, Long> { }
