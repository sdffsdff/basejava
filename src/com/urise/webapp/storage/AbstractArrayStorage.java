package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResume = 0;

    public void clear() {
        Arrays.fill(storage, 0, countResume - 1, null);
        countResume = 0;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("ERROR:Could not update resume uuid = " + resume.getUuid() + "\nResume uuid = " + resume.getUuid() + " does not exist.");
        }
        storage[index] = resume;
    }

    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (countResume == STORAGE_LIMIT) {
            System.out.println("ERROR:Could not save resume uuid = " + r.getUuid() + "\nStorage is full.");
        } else if (index > -1) {
            System.out.println("ERROR:Could not save resume uuid = " + r.getUuid() + "\nResume uuid = " + r.getUuid() + " already exists.");
        } else {
            insertResume(r, index);
            countResume++;
        }
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR:Could not delete resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.");
        } else {
            removeResume(index);
            countResume--;
            storage[countResume] = null;
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

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void removeResume(int index);
}