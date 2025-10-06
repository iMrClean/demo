package example.demo.multiple.db.repository.primary;

import example.demo.multiple.db.IntegrationTest;
import example.demo.multiple.db.domain.primary.PrimaryItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class PrimaryItemRepositoryTest {

    @Autowired
    private PrimaryItemRepository primaryItemRepository;

    @Test
    @DisplayName("Сохранение элемента (надо настроить контейнер)")
    void shouldSaveItem() {
        // Arrange
        var item = new PrimaryItem(1L, "Test Item");

        // Act
        var savedItem = primaryItemRepository.save(item);

        // Assert
        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение элемента по идентификатору (надо настроить контейнер)")
    void shouldFindItemById() {
        // Arrange
        var item = new PrimaryItem(1L, "Test Item");
        primaryItemRepository.save(item);

        // Act
        var foundItem = primaryItemRepository.findById(item.getId());

        // Assert
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение всех элементов (надо настроить контейнер)")
    void shouldFindAllItems() {
        // Arrange
        var item1 = new PrimaryItem(1L, "Test Item");
        var item2 = new PrimaryItem(2L, "Test Item");
        primaryItemRepository.save(item1);
        primaryItemRepository.save(item2);

        // Act
        var allItems = primaryItemRepository.findAll();

        // Assert
        assertThat(allItems).hasSize(2);
        assertThat(allItems).contains(item1, item2);
    }

    @Test
    @DisplayName("Удаление элемента по идентификатору (надо настроить контейнер)")
    void shouldDeleteItemById() {
        // Arrange
        var item = new PrimaryItem(1L, "Test Item");
        primaryItemRepository.save(item);

        // Act
        primaryItemRepository.deleteById(item.getId());

        // Assert
        var deletedItem = primaryItemRepository.findById(item.getId());
        assertThat(deletedItem).isEmpty();
    }

}
