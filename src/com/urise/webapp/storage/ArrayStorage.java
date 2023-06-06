package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, countResume - 1, null);
        countResume = 0;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < countResume; i++) {
            if (storage[i].toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume r) {
        if (countResume == STORAGE_LIMIT) {
            System.out.println("ERROR:Could not save resume uuid = \" + r.getUuid() + \"\nStorage is full.");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("ERROR:Could not save resume uuid = \" + r.getUuid() + \"\nResume uuid = " + r.getUuid() + " already exists.");
        } else {
            storage[countResume] = r;
            countResume++;
        }
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("ERROR:Could not update resume uuid = " + resume.getUuid() + "\nResume uuid = " + resume.getUuid() + " does not exist.");
        }
        storage[index] = resume;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("ERROR:Could not delete resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.");
        } else {
            storage[index] = storage[countResume - 1];
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
}
