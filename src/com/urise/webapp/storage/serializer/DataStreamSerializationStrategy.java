package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializationStrategy implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                writeSection(entry, dos);
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

    private static <T> void writeWithException(Collection<T> elements, DataOutputStream dos, ConsumerWithException<T> writer) throws IOException {
        dos.writeInt(elements.size());
        for (T t : elements) {
            writer.accept(t);
        }
    }

    private void writeSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        dos.writeUTF(entry.getKey().name());
        switch (entry.getKey()) {
            case PERSONAL:
            case OBJECTIVE:
                writeWithException(List.of(entry.getValue().toString()), dos, text -> {
                    dos.writeUTF(text);
                });
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeWithException(((ListSection) entry.getValue()).getStrings(), dos, text -> {
                    dos.writeUTF(text);
                });
                break;
            case EXPERIENCE:
            case EDUCATION:
                List<Company> companies = ((CompanySection) entry.getValue()).getCompanies();
                writeWithException(companies, dos, company -> {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebsite());
                            writeWithException(company.getPeriods(), dos,
                                    period -> {
                                        dos.writeUTF(Optional.ofNullable(period.getTitle()).orElse("null"));
                                        dos.writeUTF(period.getDescription());
                                        dos.writeUTF(period.getStart().toString());
                                        dos.writeUTF(Optional.ofNullable(period.getEnd()).orElse(LocalDate.of(3000, 1, 1)).toString());
                                    });
                        }
                );
                break;
        }
    }

    private void readTextSection(DataInputStream dis, Resume resume, SectionType type) throws IOException {
        dis.readInt();
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
            int sizePeriods = dis.readInt();
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