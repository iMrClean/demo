package example.demo.multipledb;

import example.demo.multipledb.annotation.PrimaryTransactional;
import example.demo.multipledb.annotation.SecondaryTransactional;
import example.demo.multipledb.domain.primary.PrimaryItem;
import example.demo.multipledb.domain.secondary.SecondaryItem;
import example.demo.multipledb.repository.primary.PrimaryItemRepository;
import example.demo.multipledb.repository.secondary.SecondaryItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@SpringBootApplication
public class MultipleDbApplication implements CommandLineRunner {

    @Autowired
    private PrimaryItemRepository primaryItemRepository;

    @Autowired
    private SecondaryItemRepository secondaryItemRepository;

    @Autowired
    @Qualifier("primaryTransactionTemplate")
    private TransactionTemplate primaryTransactionTemplate;

    @Autowired
    @Qualifier("secondaryTransactionTemplate")
    private TransactionTemplate secondaryTransactionTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MultipleDbApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception { }

//    @Override
//    @PrimaryTransactional
//    public void run(String... args) {
//        log.info("run saving primary items");
//        long count = primaryItemRepository.count();
//        log.info("count: {}", count);
//        var primaryItem1 = primaryItemRepository.save(new PrimaryItem(1L, "Hello world"));
//        var primaryItem2 = primaryItemRepository.save(new PrimaryItem(2L, "Hello world"));
//        var primaryItem3 = primaryItemRepository.save(new PrimaryItem(3L, "Hello world"));
//        log.info("Primary Item 1: {}", primaryItem1);
//        log.info("Primary Item 2: {}", primaryItem2);
//        log.info("Primary Item 3: {}", primaryItem3);
//        throw new RuntimeException("Работает");
//    }
//
//    @Override
//    @SecondaryTransactional
//    public void run(String... args) {
//        log.info("run saving secondary items");
//        long count = secondaryItemRepository.count();
//        log.info("count: {}", count);
//        var secondaryItem1 = secondaryItemRepository.save(new SecondaryItem(1L, "Hello world"));
//        var secondaryItem2 = secondaryItemRepository.save(new SecondaryItem(2L, "Hello world"));
//        var secondaryItem3 = secondaryItemRepository.save(new SecondaryItem(3L, "Hello world"));
//        log.info("Secondary item 1: {}", secondaryItem1);
//        log.info("Secondary item 2: {}", secondaryItem2);
//        log.info("Secondary item 3: {}", secondaryItem3);
//        throw new RuntimeException("Не работает");
//    }
//
//    @Override
//    @Transactional("primaryTransactionManager")
//    public void run(String... args) {
//        log.info("run saving primary items");
//        long count = primaryItemRepository.count();
//        log.info("count: {}", count);
//        var primaryItem1 = primaryItemRepository.save(new PrimaryItem(1L, "Hello world"));
//        var primaryItem2 = primaryItemRepository.save(new PrimaryItem(2L, "Hello world"));
//        var primaryItem3 = primaryItemRepository.save(new PrimaryItem(3L, "Hello world"));
//        log.info("Primary Item 1: {}", primaryItem1);
//        log.info("Primary Item 2: {}", primaryItem2);
//        log.info("Primary Item 3: {}", primaryItem3);
//        throw new RuntimeException("Работает");
//    }
//
//    @Override
//    @Transactional("secondaryTransactionManager")
//    public void run(String... args) {
//        log.info("run saving secondary items");
//        long count = secondaryItemRepository.count();
//        log.info("count: {}", count);
//        var secondaryItem1 = secondaryItemRepository.save(new SecondaryItem(1L, "Hello world"));
//        var secondaryItem2 = secondaryItemRepository.save(new SecondaryItem(2L, "Hello world"));
//        var secondaryItem3 = secondaryItemRepository.save(new SecondaryItem(3L, "Hello world"));
//        log.info("Secondary item 1: {}", secondaryItem1);
//        log.info("Secondary item 2: {}", secondaryItem2);
//        log.info("Secondary item 3: {}", secondaryItem3);
//        throw new RuntimeException("Работает в отличие от @SecondaryTransactional");
//    }
//
//    @Override
//    public void run(String... args) {
//        primaryTransactionTemplate.execute(ts -> {
//            log.info("run saving primary items");
//            long count = primaryItemRepository.count();
//            log.info("count: {}", count);
//            var primaryItem1 = primaryItemRepository.save(new PrimaryItem(1L, "Hello world"));
//            var primaryItem2 = primaryItemRepository.save(new PrimaryItem(2L, "Hello world"));
//            var primaryItem3 = primaryItemRepository.save(new PrimaryItem(3L, "Hello world"));
//            log.info("Primary Item 1: {}", primaryItem1);
//            log.info("Primary Item 2: {}", primaryItem2);
//            log.info("Primary Item 3: {}", primaryItem3);
//            throw new RuntimeException("Работает");
//        });
//    }
//
//    @Override
//    public void run(String... args) {
//        secondaryTransactionTemplate.execute(ts -> {
//            log.info("run saving secondary items");
//            long count = secondaryItemRepository.count();
//            log.info("count: {}", count);
//            var secondaryItem1 = secondaryItemRepository.save(new SecondaryItem(1L, "Hello world"));
//            var secondaryItem2 = secondaryItemRepository.save(new SecondaryItem(2L, "Hello world"));
//            var secondaryItem3 = secondaryItemRepository.save(new SecondaryItem(3L, "Hello world"));
//            log.info("Secondary item 1: {}", secondaryItem1);
//            log.info("Secondary item 2: {}", secondaryItem2);
//            log.info("Secondary item 3: {}", secondaryItem3);
//            throw new RuntimeException("Работает");
//        });
//    }

}
