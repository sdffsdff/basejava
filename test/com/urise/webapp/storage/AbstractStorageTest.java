package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AbstractStorageTest {

    private final Storage storage;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume R1 = ResumeTestData.createResume(UUID_1, UUID_1);
    protected static final Resume R2 = ResumeTestData.createResume(UUID_2, UUID_2);
    protected static final Resume R3 = ResumeTestData.createResume(UUID_3, UUID_3);
    protected static final Resume R4 = ResumeTestData.createResume(UUID_4, UUID_4);

    @BeforeEach
    void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[]{}, storage.getAllSorted().toArray());
    }

    private void assertGet(Resume resume) {
        assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test
    void get() {
        assertGet(R1);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(R4));
    }

    @Test
    void update() {
        Resume R1Updated = ResumeTestData.createResume(UUID_1, UUID_1);
        storage.update(R1Updated);
        assertNotSame(R1, storage.get(UUID_1));
        assertGet(R1Updated);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(R1));
    }

    @Test
    void save() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
        storage.save(R4);
        assertGet(R4);
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(R4.getUuid()));
    }

    @Test
    void delete() {
        assertDoesNotThrow(() -> storage.delete(R1.getUuid()));
    }

    @Test
    void getAll() {
        Resume[] resumeArray = {R1, R2, R3};
        assertArrayEquals(resumeArray, storage.getAllSorted().toArray());
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    @Test
    void size() {
        assertSize(3);
    }
}