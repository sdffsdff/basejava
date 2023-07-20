package com.urise.webapp.storage;

import java.io.File;
public class ObjectStreamStorage extends FileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory, new ObjectStreamStrategy());
    }
}
