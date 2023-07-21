package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.SerializationStrategy;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private SerializationStrategy serializationStrategy;

    protected FileStorage(File directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(serializationStrategy, "serializationStrategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializationStrategy = serializationStrategy;
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
            serializationStrategy.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName(), e);
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
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, r);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return serializationStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()){
            throw new StorageException("File delete error", file.getName());
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
            throw new StorageException("Directory read error", "");
        }
        return List.of(files);
    }
}