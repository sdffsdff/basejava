package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

class MapResumeStorageTest extends AbstractStorageTest {
    private static Storage storage = new MapResumeStorage();

    MapResumeStorageTest() {
        super(storage);
    }

    @Test
    void getAll() {
        Resume[] resumeArray = {R1, R2, R3};
        Object[] resumeStorageArray = storage.getAllSorted().toArray();
        Arrays.sort(resumeStorageArray);
        assertArrayEquals(resumeArray, resumeStorageArray);
    }
}