package com.urise.webapp.storage;

class ListStorageTest extends AbstractStorageTest {
    private static Storage storage = new ListStorage();

    ListStorageTest() {
        super(storage);
    }
}