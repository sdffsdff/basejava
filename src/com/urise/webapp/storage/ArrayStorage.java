package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final static int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int countResume;

    public void clear() {
        Arrays.fill(storage, 0, countResume - 1, null);
        countResume = 0;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume r) {
        if (countResume == storage.length) {
            System.out.println("ERROR:Could not save resume uuid = \" + r.getUuid() + \"\nStorage is full.");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("ERROR:Could not save resume uuid = \" + r.getUuid() + \"\nResume uuid = " + r.getUuid() + " already exists.");
        } else {
            countResume++;
            storage[countResume - 1] = r;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR:Could not get resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.");
            return null;
        } else {
            return storage[index];
        }
    }

    public void update(Resume resume) {
        if (getIndex(resume.getUuid()) == -1) {
            System.out.println("ERROR:Could not update resume uuid = " + resume.getUuid() + "\nResume uuid = " + resume.getUuid() + " does not exist.");
        }
        int index = getIndex(resume.getUuid());
        storage[index] = resume;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR:Could not delete resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.");
        } else {
            System.arraycopy(storage, index + 1, storage, index, storage.length - 1);
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
}
