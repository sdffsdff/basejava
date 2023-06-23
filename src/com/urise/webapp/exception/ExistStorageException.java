package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("ERROR:Resume uuid = " + uuid + " already exists.", uuid);
    }
}
