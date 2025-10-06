package example.demo.route.multiple.db.repository.secondary;

import example.demo.route.multiple.db.domain.secondary.SecondaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecondaryItemRepository extends JpaRepository<SecondaryItem, Long> { }
