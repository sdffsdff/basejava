package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class AbstractArrayStorageTest extends AbstractStorageTest {

    private final Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
        this.storage = storage;
    }

    @Test
    void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(""));
            }
        } catch (Exception e) {
            Assertions.fail("Storage out of space prematurely.");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("")));
    }
}