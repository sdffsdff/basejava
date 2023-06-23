package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume = 0;

    public void clear() {
        if (countResume > 0) {
            Arrays.fill(storage, 0, countResume - 1, null);
            countResume = 0;
        }
    }

    protected boolean isExist(Object searchKey) {
        if ((int) searchKey < 0) {
            return false;
        } else {
            return true;
        }
    }

    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    protected void doUpdate(Object searchKey, Resume r) {
        storage[(int) searchKey] = r;
    }

    protected void doSave(Object searchKey, Resume r) {
        if (countResume == STORAGE_LIMIT) {
            throw new StorageException("ERROR:Could not save resume uuid = " + r.getUuid() + "\nStorage is full.", r.getUuid());
        }
        insertResume(r, (int) searchKey);
        countResume++;
    }

    protected void doDelete(Object searchKey) {
        removeResume((int) searchKey);
        countResume--;
        storage[countResume] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResume);
    }

    public int size() {
        return countResume;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void removeResume(int index);
}