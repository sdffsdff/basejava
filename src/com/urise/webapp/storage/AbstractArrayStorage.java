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

    public final Resume getResume(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return null;
        } else {
            return storage[index];
        }
    }

    @Override
    protected void setResume(int index, Resume resume) {
        storage[index] = resume;
    }

    protected void checkStorageLimit(String uuid) {
        if (countResume == STORAGE_LIMIT) {
            throw new StorageException("ERROR:Could not save resume uuid = " + uuid + "\nStorage is full.", uuid);
        }
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
}