package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
class AbstractArrayStorageTest {
    private final Storage storage;

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume R1 = new Resume(UUID_1);
    private static final Resume R2 = new Resume(UUID_2);
    private static final Resume R3 = new Resume(UUID_3);
    private static final Resume R4 = new Resume(UUID_4);

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{});
    }

    private void assertGet(Resume resume) {
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test
    void get() {
        assertGet(R1);
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    private void updateNotExist(Resume resume) {
        assertThrows(NotExistStorageException.class, () -> storage.update(resume));
    }

    @Test
    void update() {
        Resume R1Updated = new Resume(UUID_1);
        storage.update(R1Updated);
        assertNotSame(R1, storage.get(UUID_1));
        assertGet(R1Updated);
        updateNotExist(R4);
    }

    private void saveExist(Resume resume) {
        assertThrows(ExistStorageException.class, () -> storage.save(resume));
    }

    private void saveOverflow() {
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
    void save() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
        storage.save(R4);
        assertGet(R4);
        saveExist(R4);
        saveOverflow();
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> storage.delete(R1.getUuid()));
        assertThrows(NotExistStorageException.class, () -> storage.delete(R1.getUuid()));
    }

    private void assertArrayEquals(Resume[] newStorage) {
        assertTrue(Arrays.equals(newStorage, storage.getAll()));
    }

    @Test
    void getAll() {
        Resume[] resumeArray = {R1, R2, R3};
        assertArrayEquals(resumeArray);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    public void size() {
        assertSize(3);
    }
}