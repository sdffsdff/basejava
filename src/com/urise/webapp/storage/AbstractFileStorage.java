package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        for (File file : getAllFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return getAllFiles().size();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume r) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void doSave(File file, Resume r) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()){
            throw new StorageException("IO error", file.getName());
        }
    }

    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File f : getAllFiles()) {
            resumes.add(doGet(f));
        }
        return resumes;
    }

    private List<File> getAllFiles() {
        File[] files = directory.listFiles();
        if (files == null){
            throw new StorageException("IO error", "");
        }
        return List.of(files);
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;
}