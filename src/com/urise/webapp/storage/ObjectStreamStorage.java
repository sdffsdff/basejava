package com.urise.webapp.storage;

import java.io.File;
public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }
}
