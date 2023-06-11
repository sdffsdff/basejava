package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String methodName, String uuid) {
        super("ERROR:Could not " + methodName + " resume uuid = " + uuid + "\nResume uuid = " + uuid + " does not exist.", uuid);
    }
}