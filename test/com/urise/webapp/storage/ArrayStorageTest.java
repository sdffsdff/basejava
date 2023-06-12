package com.urise.webapp.storage;

class ArrayStorageTest extends AbstractArrayStorageTest {
    private static Storage storage = new ArrayStorage();

    ArrayStorageTest() {
        super(storage);
    }
}