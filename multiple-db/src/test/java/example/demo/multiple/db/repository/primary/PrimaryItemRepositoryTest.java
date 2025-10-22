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
    @DisplayName("Сохранение элемента")
    void shouldSaveItem() {
        var item = new PrimaryItem(1L, "Test Item");

        var savedItem = primaryItemRepository.save(item);

        assertThat(savedItem.getId()).isNotNull();
        assertThat(savedItem.getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение элемента по идентификатору")
    void shouldFindItemById() {
        var item = new PrimaryItem(1L, "Test Item");
        primaryItemRepository.save(item);

        var foundItem = primaryItemRepository.findById(item.getId());

        assertThat(foundItem).isPresent();
        assertThat(foundItem.get().getName()).isEqualTo("Test Item");
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldFindAllItems() {
        var item1 = new PrimaryItem(1L, "Test Item");
        var item2 = new PrimaryItem(2L, "Test Item");
        primaryItemRepository.save(item1);
        primaryItemRepository.save(item2);

        var allItems = primaryItemRepository.findAll();

        assertThat(allItems).hasSize(2);
        assertThat(allItems).contains(item1, item2);
    }

    @Test
    @DisplayName("Удаление элемента по идентификатору")
    void shouldDeleteItemById() {
        var item = new PrimaryItem(1L, "Test Item");
        primaryItemRepository.save(item);

        primaryItemRepository.deleteById(item.getId());

        var deletedItem = primaryItemRepository.findById(item.getId());
        assertThat(deletedItem).isEmpty();
    }

}
