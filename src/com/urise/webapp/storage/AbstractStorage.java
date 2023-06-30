package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }

    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        doSave(searchKey, r);
    }

    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    public final List<Resume> getAllSorted() {
        List<Resume> storageList = getAll();
        storageList.sort(RESUME_COMPARATOR);
        return storageList;
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume r);

    protected abstract void doSave(Object searchKey, Resume r);

    protected abstract void doDelete(Object searchKey);

    protected abstract List<Resume> getAll();
}
