package com.urise.webapp.storage;

import com.urise.webapp.strategy.ObjectStreamStrategy;
public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new ObjectStreamStrategy()));
    }
}
