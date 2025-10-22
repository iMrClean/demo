package example.demo.multiple.db.repository.secondary;

import example.demo.multiple.db.IntegrationTest;
import example.demo.multiple.db.domain.secondary.SecondaryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

        registry.add("spring.datasource.secondary.url", oracle::getJdbcUrl);
        registry.add("spring.datasource.secondary.username", oracle::getUsername);
        registry.add("spring.datasource.secondary.password", oracle::getPassword);
    }

    @Autowired
    private SecondaryItemRepository secondaryItemRepository;

    @Test
    @DisplayName("Сохранение элемента")
    void shouldSaveItem() {
        var item = new SecondaryItem(1L, "Test Item");

        var savedItem = secondaryItemRepository.save(item);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение элемента по идентификатору")
    void shouldFindItemById() {
        var item = new SecondaryItem(1L, "Test Item");
        secondaryItemRepository.save(item);

        var foundItem = secondaryItemRepository.findById(item.getId());

        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldFindAllItems() {
        var item1 = new SecondaryItem(1L, "Test Item");
        var item2 = new SecondaryItem(2L, "Test Item");
        secondaryItemRepository.save(item1);
        secondaryItemRepository.save(item2);

        var allItems = secondaryItemRepository.findAll();

        assertThat(allItems).hasSize(2);
        assertThat(allItems).contains(item1, item2);
    }

    @Test
    @DisplayName("Удаление элемента по идентификатору")
    void shouldDeleteItemById() {
        var item = new SecondaryItem(1L, "Test Item");
        secondaryItemRepository.save(item);

        secondaryItemRepository.deleteById(item.getId());

        var deletedItem = secondaryItemRepository.findById(item.getId());
        assertThat(deletedItem).isEmpty();
    }

}
