package com.urise.webapp.storage;

class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private static Storage storage = new SortedArrayStorage();

    SortedArrayStorageTest() {
        super(storage);
    }
}