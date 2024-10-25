package example.demo.simple.repository;

import example.demo.simple.IntegrationTest;
import example.demo.simple.domain.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("Сохранение элемента")
    void shouldSaveItem() {
        // Arrange
        var item = new Item(1L, "Test Item");

        // Act
        var savedItem = itemRepository.save(item);

        // Assert
        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение элемента по идентификатору")
    void shouldFindItemById() {
        // Arrange
        var item = new Item(1L, "Test Item");
        itemRepository.save(item);

        // Act
        var foundItem = itemRepository.findById(item.getId());

        // Assert
        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldFindAllItems() {
        // Arrange
        var item1 = new Item(1L, "Test Item 1");
        var item2 = new Item(2L, "Test Item 1");
        itemRepository.save(item1);
        itemRepository.save(item2);

        // Act
        var allItems = itemRepository.findAll();

        // Assert
        assertThat(allItems).hasSize(2);
        assertThat(allItems).contains(item1, item2);
    }

    @Test
    @DisplayName("Удаление элемента по идентификатору")
    void shouldDeleteItemById() {
        // Arrange
        var item = new Item(1L, "Test Item");
        itemRepository.save(item);

        // Act
        itemRepository.deleteById(item.getId());

        // Assert
        var deletedItem = itemRepository.findById(item.getId());
        assertThat(deletedItem).isEmpty();
    }

}
