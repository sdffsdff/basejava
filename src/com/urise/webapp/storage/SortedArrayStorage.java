package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResume, searchKey);
    }

    protected void insertResume(Resume r, int index) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, (size() - index));
        storage[index] = r;
        countResume++;
    }

    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, (size() - index));
        countResume--;
        storage[countResume] = null;
    }
}