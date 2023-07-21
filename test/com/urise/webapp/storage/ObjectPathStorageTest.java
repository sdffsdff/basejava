package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamStrategy;
public class ObjectPathStorageTest extends AbstractStorageTest {

    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new ObjectStreamStrategy()));
    }
}
