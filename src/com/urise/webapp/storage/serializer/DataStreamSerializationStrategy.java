package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataStreamSerializationStrategy implements SerializationStrategy {

    @FunctionalInterface
    private interface ConsumerWithException<V> {
        void accept(V v) throws IOException;
    }

    private static <E> void writeWithException(Collection<E> elements, ConsumerWithException writer) throws IOException {
        for (E e : elements) {
            writer.accept(e.toString());
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            ConsumerWithException<String> writer = dos::writeUTF;
            List<Object> contactList = contacts.entrySet().stream().flatMap(e -> Stream.of(e.getKey(), e.getValue())).collect(Collectors.toList());
            writeWithException(contactList, writer);

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
                writeSection(section, dos, writer);
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                readSection(dis, resume);
            }
            return resume;
        }
    }

    private void writeListSection(Map.Entry<SectionType, Section> section, DataOutputStream dos, ConsumerWithException writer) throws IOException {
        List<String> strings = ((ListSection) section.getValue()).getStrings();
        int size = strings.size();
        dos.writeInt(size);
        writeWithException(strings, writer);
    }

    private void writeCompanySection(Map.Entry<SectionType, Section> section, DataOutputStream dos, ConsumerWithException writer) throws IOException {
        List<Company> companies = ((CompanySection) section.getValue()).getCompanies();
        int size = companies.size();
        dos.writeInt(size);
        List<Object> list = companies.stream().flatMap(e -> Stream.concat(
                Stream.of(e.getName(), e.getWebsite(), String.valueOf(e.getPeriods().size())),
                e.getPeriods().stream().flatMap(p -> Stream.of((Optional.ofNullable(p.getTitle()).orElse("null")), p.getDescription(),
                        p.getStart(), Optional.ofNullable(p.getEnd()).orElse(LocalDate.of(3000, 1, 1)).toString()))
        )).collect(Collectors.toList());
        writeWithException(list, writer);
    }

    private void writeSection(Map.Entry<SectionType, Section> section, DataOutputStream dos, ConsumerWithException writer) throws IOException {
        dos.writeUTF(section.getKey().name());
        switch (section.getKey()) {
            case PERSONAL:
            case OBJECTIVE:
                writeWithException(List.of(section.getValue()), writer);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeListSection(section, dos, writer);
                break;
            case EXPERIENCE:
            case EDUCATION:
                writeCompanySection(section, dos, writer);
                break;
        }
    }

    private void readTextSection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        resume.setSection(type, new TextSection(dis.readUTF()));
    }

    private void readListSection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        List<String> strings = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            strings.add(dis.readUTF());
        }
        resume.setSection(type, new ListSection(strings));
    }

    private void readCompanySection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        List<Company> companies = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            Company c = new Company();
            c.setName(dis.readUTF());
            c.setWebsite(dis.readUTF());
            List<Period> periods = new ArrayList<>();
            int sizePeriods = Integer.parseInt(dis.readUTF());
            for (int j = 0; j < sizePeriods; j++) {
                String title = dis.readUTF();
                if (title.equals("null")) {
                    title = null;
                }
                Period p = new Period(title, dis.readUTF(), LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()));
                if (p.getEnd().getYear() > LocalDate.now().getYear()) {
                    p.setEnd(null);
                }
                periods.add(p);
            }
            c.setPeriods(periods);
            companies.add(c);
        }
        resume.setSection(type, new CompanySection(companies));
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        SectionType type = SectionType.valueOf(dis.readUTF());
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                readTextSection(dis, resume, type);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                readListSection(dis, resume, type);
                break;
            case EXPERIENCE:
            case EDUCATION:
                readCompanySection(dis, resume, type);
                break;
        }
    }
}