package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.DataStreamSerializationStrategy;
public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamSerializationStrategy()));
    }
}
