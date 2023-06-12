package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.exception.UUIDEmptyException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
class AbstractArrayStorageTest {
    private Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private Resume r1;
    private Resume r2;
    private Resume r3;

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        r1 = new Resume(UUID_1);
        r2 = new Resume(UUID_2);
        r3 = new Resume(UUID_3);
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void get() {
        assertThrows(RuntimeException.class, () -> storage.get(null));
        assertSame(r1, storage.get(UUID_1));
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void update() {
        assertThrows(UUIDEmptyException.class, () -> storage.update(new Resume(null)));
        Resume r1Updated = new Resume(UUID_1);
        storage.update(r1Updated);
        assertNotSame(r1, storage.get(UUID_1));
        assertSame(r1Updated, storage.get(UUID_1));
    }

    @Test
    void save() {
        assertThrows(UUIDEmptyException.class, () -> storage.delete(null));
        String uuid_4 = "uuid4";
        Resume r4 = new Resume(uuid_4);
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid_4));
        storage.save(r4);
        assertSame(r4, storage.get(uuid_4));
        assertThrows(ExistStorageException.class, () -> storage.save(r4));
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assertions.fail("Storage out of space prematurely.");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void delete() {
        assertThrows(UUIDEmptyException.class, () -> storage.delete(null));
        assertDoesNotThrow(() -> storage.delete(r1.getUuid()));
        assertThrows(NotExistStorageException.class, () -> storage.delete(r1.getUuid()));
    }

    @Test
    void getAll() {
        Resume[] resumeArray = {r1, r2, r3};
        assertTrue(Arrays.equals(resumeArray, storage.getAll()));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}