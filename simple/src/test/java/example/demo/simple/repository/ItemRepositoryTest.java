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
        var item = new Item(1L, "Test Item");

        var savedItem = itemRepository.save(item);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение элемента по идентификатору")
    void shouldFindItemById() {
        var item = new Item(1L, "Test Item");
        itemRepository.save(item);

        var foundItem = itemRepository.findById(item.getId());

        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldFindAllItems() {
        var item1 = new Item(1L, "Test Item");
        var item2 = new Item(2L, "Test Item");
        itemRepository.save(item1);
        itemRepository.save(item2);

        var allItems = itemRepository.findAll();

        assertThat(allItems).hasSize(2);
        assertThat(allItems).contains(item1, item2);
    }

    @Test
    @DisplayName("Удаление элемента по идентификатору")
    void shouldDeleteItemById() {
        var item = new Item(1L, "Test Item");
        itemRepository.save(item);

        itemRepository.deleteById(item.getId());

        var deletedItem = itemRepository.findById(item.getId());
        assertThat(deletedItem).isEmpty();
    }

}
