package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void insertResume(Resume r, int index) {
        storage[countResume] = r;
        countResume++;
    }

    protected void removeResume(int index) {
        storage[index] = storage[countResume - 1];
        countResume--;
        storage[countResume] = null;
    }
}
