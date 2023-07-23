package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.JsonStreamSerializationStrategy;
public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new JsonStreamSerializationStrategy()));
    }
}