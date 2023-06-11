package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String methodName, String uuid) {
        super("ERROR:Could not " + methodName + " resume uuid = " + uuid + "\nResume uuid = " + uuid + " already exists.", uuid);
    }
}
