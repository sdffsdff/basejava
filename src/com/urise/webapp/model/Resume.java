package com.urise.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new HashMap<>();
    private final Map<SectionType, Section> sections = new HashMap<>();

    public Resume(String fullName) {
        this.uuid = UUID.randomUUID().toString();
        this.fullName = fullName;
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public Resume() {
        this(UUID.randomUUID().toString());
        this.fullName = "";
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public Section getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void setSection(SectionType sectionType, Section section) {
        sections.put(sectionType, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        return uuid.equals(resume.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.uuid);
    }
}
