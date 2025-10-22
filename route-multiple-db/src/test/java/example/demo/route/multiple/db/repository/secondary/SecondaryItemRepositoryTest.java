package example.demo.route.multiple.db.repository.secondary;

import example.demo.route.multiple.db.IntegrationTest;
import example.demo.route.multiple.db.config.DatabaseType;
import example.demo.route.multiple.db.config.DatabaseTypeRoutingDataSource;
import example.demo.route.multiple.db.domain.secondary.SecondaryItem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@IntegrationTest
class SecondaryItemRepositoryTest {

    @Container
    static OracleContainer oracle = new OracleContainer("gvenzl/oracle-free:latest");

    @DynamicPropertySource
    static void registerProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.primary.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.primary.username", oracle::getUsername);
        registry.add("spring.datasource.primary.password", oracle::getPassword);

        registry.add("spring.datasource.secondary-a.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.secondary-a.username", oracle::getUsername);
        registry.add("spring.datasource.secondary-a.password", oracle::getPassword);

        registry.add("spring.datasource.secondary-b.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.secondary-b.username", oracle::getUsername);
        registry.add("spring.datasource.secondary-b.password", oracle::getPassword);
    }

    @Autowired
    private SecondaryItemRepository secondaryItemRepository;

    @ParameterizedTest(name = "Сохранение элемента в базе {0}")
    @EnumSource(DatabaseType.class)
    void shouldSaveItem(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = new SecondaryItem(1L, "Item " + db.name());
        var saved = secondaryItemRepository.save(item);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Item " + db.name());
    }

    @ParameterizedTest(name = "Получение элемента по id в базе {0}")
    @EnumSource(DatabaseType.class)
    void shouldFindItemById(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = new SecondaryItem(1L, "Item " + db.name());
        secondaryItemRepository.save(item);

        var foundItem = secondaryItemRepository.findById(item.getId());

        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Item " + db.name());
    }

    @ParameterizedTest(name = "Получение всех элементов в базе {0}")
    @EnumSource(DatabaseType.class)
    void shouldFindAllItems(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item1 = new SecondaryItem(1L, "Item " + db.name());
        var item2 = new SecondaryItem(2L, "Item " + db.name());
        secondaryItemRepository.save(item1);
        secondaryItemRepository.save(item2);

        var allItems = secondaryItemRepository.findAll();

        assertThat(allItems).hasSize(2);
        assertThat(allItems).contains(item1, item2);
    }

    @ParameterizedTest(name = "Удаление элемента в базе {0}")
    @EnumSource(DatabaseType.class)
    void shouldDeleteItem(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = new SecondaryItem(1L, "Item " + db.name());
        secondaryItemRepository.save(item);

        secondaryItemRepository.deleteById(item.getId());

        var deletedItem = secondaryItemRepository.findById(item.getId());
        assertThat(deletedItem).isEmpty();
    }

}
