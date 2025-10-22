package example.demo.route.multiple.db.repository.secondary;

import example.demo.route.multiple.db.IntegrationTest;
import example.demo.route.multiple.db.config.DatabaseType;
import example.demo.route.multiple.db.config.DatabaseTypeRoutingDataSource;
import example.demo.route.multiple.db.domain.secondary.SecondaryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class SecondaryItemRepositoryTest {

    @Autowired
    private SecondaryItemRepository secondaryItemRepository;

    @DisplayName("Сохранение элемента")
    @ParameterizedTest(name = "Сохранение элемента {0}")
    @EnumSource(DatabaseType.class)
    void shouldSaveItem(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = new SecondaryItem(1L, "Item " + db.name());
        var saved = secondaryItemRepository.save(item);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Item " + db.name());
    }

    @DisplayName("Получение элемента по идентификатору")
    @ParameterizedTest(name = "Получение элемента по идентификатору {0}")
    @EnumSource(DatabaseType.class)
    void shouldFindItemById(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = new SecondaryItem(1L, "Item " + db.name());
        secondaryItemRepository.save(item);

        var foundItem = secondaryItemRepository.findById(item.getId());

        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Item " + db.name());
    }

    @DisplayName("Получение всех элементов")
    @ParameterizedTest(name = "Получение всех элементов {0}")
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

    @DisplayName("Удаление элемента по идентификатору")
    @ParameterizedTest(name = "Удаление элемента по идентификатору {0}")
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
