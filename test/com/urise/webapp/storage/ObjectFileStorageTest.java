package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectStreamSerializationStrategy;
public class ObjectFileStorageTest extends AbstractStorageTest {

    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}
