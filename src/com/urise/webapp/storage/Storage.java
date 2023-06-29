package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.List;

public interface Storage {

    void clear();

    Resume get(String uuid);

    void update(Resume r);

    void save(Resume r);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}