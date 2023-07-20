package com.urise.webapp.storage;

public class ObjectStreamPathStorage extends PathStorage {

    protected ObjectStreamPathStorage(String directory) {
        super(directory, new ObjectStreamStrategy());
    }
}
