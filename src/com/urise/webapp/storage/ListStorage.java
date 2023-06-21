package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.ArrayList;
public class ListStorage extends AbstractStorage {

    protected ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).toString().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected Resume getResume(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            return null;
        } else {
            return storage.get(index);
        }
    }

    @Override
    protected void setResume(int index, Resume resume) {
        storage.set(index, resume);
    }

    protected void checkStorageLimit(String uuid) {
    }

    @Override
    protected void insertResume(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[]{});
    }

    @Override
    public int size() {
        return storage.size();
    }
}
