package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int countResume;

    public void clear() {
        Arrays.fill(storage, 0, countResume - 1, null);
        countResume = 0;
    }

    private int getStorageIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume r) {
        if (getStorageIndex(r.getUuid()) != -1) {
            System.out.println("ERROR:Could not save resume uuid = \" + r.getUuid() + \"\nResume uuid = " + r.getUuid() + " already exists.");
        } else if (countResume == storage.length) {
            System.out.println("ERROR:Could not save resume uuid = \" + r.getUuid() + \"\nStorage is full.");
        } else {
            countResume++;
            storage[countResume - 1] = r;
        }
    }

    public Resume get(String uuid) {
        int storageIndex = getStorageIndex(uuid);
        if (storageIndex == -1) {
            System.out.println("ERROR:Could not get resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.");
            return null;
        } else {
            return storage[storageIndex];
        }
    }

    public void update(Resume resume) {
        if (getStorageIndex(resume.getUuid()) == -1) {
            System.out.println("ERROR:Could not update resume uuid = " + resume.getUuid() + "\nResume uuid = " + resume.getUuid() + " does not exist.");
        }
    }

    public void delete(String uuid) {
        int indexForDel = getStorageIndex(uuid);
        if (indexForDel == -1) {
            System.out.println("ERROR:Could not delete resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.");
        } else {
            for (int i = indexForDel + 1; i < countResume; i++) {
                storage[i - 1] = storage[i];
            }
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
