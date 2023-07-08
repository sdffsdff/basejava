package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doSave(String searchKey, Resume r) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
