package example.demo.multipledb.repository.primary;

import example.demo.multipledb.domain.primary.PrimaryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryItemRepository extends JpaRepository<PrimaryItem, Long> { }
