package example.demo.route.multiple.db.repository.secondary;

import example.demo.route.multiple.db.IntegrationTest;
import example.demo.route.multiple.db.config.DatabaseType;
import example.demo.route.multiple.db.config.DatabaseTypeRoutingDataSource;
import example.demo.route.multiple.db.domain.secondary.SecondaryItem;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class SecondaryItemRepositoryTest {

    @Autowired
    private SecondaryItemRepository secondaryItemRepository;

    @ParameterizedTest(name = "Сохранение элемента в базе {0} (надо настроить контейнер)")
    @EnumSource(DatabaseType.class)
    void shouldSaveItem(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = new SecondaryItem(1L, "Item " + db.name());
        var saved = secondaryItemRepository.save(item);

        assertThat(saved.getId()).isNotNull();
    }

    @ParameterizedTest(name = "Получение элемента по id в базе {0} (надо настроить контейнер)")
    @EnumSource(DatabaseType.class)
    void shouldFindItemById(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = secondaryItemRepository.save(new SecondaryItem(1L, "Item " + db.name()));
        var found = secondaryItemRepository.findById(item.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Item " + db.name());
    }

    @ParameterizedTest(name = "Получение всех элементов в базе {0} (надо настроить контейнер)")
    @EnumSource(DatabaseType.class)
    void shouldFindAllItems(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var i1 = secondaryItemRepository.save(new SecondaryItem(1L, "Item " + db.name()));
        var i2 = secondaryItemRepository.save(new SecondaryItem(2L, "Item " + db.name()));
        var all = secondaryItemRepository.findAll();

        assertThat(all).hasSize(2).contains(i1, i2);
    }

    @ParameterizedTest(name = "Удаление элемента в базе {0} (надо настроить контейнер)")
    @EnumSource(DatabaseType.class)
    void shouldDeleteItem(DatabaseType db) {
        DatabaseTypeRoutingDataSource.setDatabase(db);

        var item = secondaryItemRepository.save(new SecondaryItem(1L, "Item " + db.name()));
        secondaryItemRepository.deleteById(item.getId());

        assertThat(secondaryItemRepository.findById(item.getId())).isEmpty();
    }

}
