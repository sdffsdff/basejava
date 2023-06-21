package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
public abstract class AbstractStorage implements Storage {

    public final Resume get(String uuid) {
        Resume resume = getResume(uuid);
        if (resume == null) {
            throw new NotExistStorageException("get", uuid);
        }
        return resume;
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException("update", resume.getUuid());
        }
        setResume(index, resume);
    }

    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        checkStorageLimit(r.getUuid());
        if (index > -1) {
            throw new ExistStorageException("save", r.getUuid());
        } else {
            insertResume(r, index);
        }
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException("delete", uuid);
        } else {
            removeResume(index);
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract Resume getResume(String uuid);

    protected abstract void setResume(int index, Resume resume);

    protected abstract void checkStorageLimit(String uuid);

    protected abstract void insertResume(Resume r, int index);

    protected abstract void removeResume(int index);
}
