package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
class MapStorageTest extends AbstractStorageTest {
    private static Storage storage = new MapStorage();

    MapStorageTest() {
        super(storage);
    }

    @Test
    void getAll() {
        Resume[] resumeArray = {R1, R2, R3};
        Resume[] resumeStorageArray = storage.getAll();
        Arrays.sort(resumeStorageArray);
        assertArrayEquals(resumeArray, resumeStorageArray);
    }
}