package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataStreamSerializationStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            ConsumerWithException<List<String>> writer = e -> {
                writeWithException(e, dos::writeUTF);
            };
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            List<String> contactList = contacts.entrySet().stream().flatMap(e -> Stream.of(e.getKey().toString(), e.getValue())).collect(Collectors.toList());
            writer.accept(contactList);

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

    @FunctionalInterface
    private interface ConsumerWithException<T> {
        void accept(T t) throws IOException;
    }

    private static <T> void writeWithException(Collection<T> elements, ConsumerWithException<T> writer) throws IOException {
        for (T t : elements) {
            writer.accept(t);
        }
    }

    private <T> void writeListSection(Map.Entry<SectionType, Section> section, DataOutputStream dos, ConsumerWithException<List<String>> writer) throws IOException {
        List<String> strings = ((ListSection) section.getValue()).getStrings();
        int size = strings.size();
        dos.writeInt(size);
        writer.accept(strings);
    }

    private void writeCompanySection(Map.Entry<SectionType, Section> section, DataOutputStream dos, ConsumerWithException<List<String>> writer) throws IOException {
        List<Company> companies = ((CompanySection) section.getValue()).getCompanies();
        int size = companies.size();
        dos.writeInt(size);
        List<String> listCompanies = companies.stream().flatMap(e -> Stream.concat(
                Stream.of(e.getName().toString(), e.getWebsite(), String.valueOf(e.getPeriods().size())),
                e.getPeriods().stream().flatMap(p -> Stream.of((Optional.ofNullable(p.getTitle()).orElse("null")), p.getDescription(),
                        p.getStart().toString(), Optional.ofNullable(p.getEnd()).orElse(LocalDate.of(3000, 1, 1)).toString()))
        )).collect(Collectors.toList());
        writer.accept(listCompanies);
    }

    private void writeSection(Map.Entry<SectionType, Section> section, DataOutputStream dos, ConsumerWithException<List<String>> writer) throws IOException {
        dos.writeUTF(section.getKey().name());
        switch (section.getKey()) {
            case PERSONAL:
            case OBJECTIVE:
                writer.accept(List.of(section.getValue().toString()));
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