package example.demo.multiple.db.repository.primary;

import example.demo.multiple.db.domain.primary.PrimaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryItemRepository extends JpaRepository<PrimaryItem, Long> { }
