package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataStreamSerializationStrategy implements SerializationStrategy {

    private void writeTextSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        dos.writeUTF(entry.getValue().toString());
    }

    private void writeListSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        List<String> strings = ((ListSection) entry.getValue()).getStrings();
        int size = strings.size();
        dos.writeInt(size);
        for (int i = 0; i < size; i++) {
            dos.writeUTF(strings.get(i));
        }
    }

    private void writeCompanySection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        List<Company> companies = ((CompanySection) entry.getValue()).getCompanies();
        int size = companies.size();
        dos.writeInt(size);
        for (int i = 0; i < size; i++) {
            Company company = companies.get(i);
            dos.writeUTF(company.getName());
            dos.writeUTF(company.getWebsite());
            List<Period> periods = company.getPeriods();
            dos.writeInt(periods.size());
            for (int j = 0; j < periods.size(); j++) {
                Period period = periods.get(j);
                dos.writeUTF(Optional.ofNullable(period.getTitle()).orElse("null"));
                dos.writeUTF(period.getDescription());
                dos.writeUTF(period.getStart().toString());
                dos.writeUTF(Optional.ofNullable(period.getEnd()).orElse(LocalDate.of(3000, 1, 1)).toString());
            }
        }
    }

    private void writeSection(Map.Entry<SectionType, Section> entry, DataOutputStream dos) throws IOException {
        dos.writeUTF(entry.getKey().toString());
        switch (entry.getKey().toString()) {
            case "PERSONAL":
            case "OBJECTIVE":
                writeTextSection(entry, dos);
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                writeListSection(entry, dos);
                break;
            case "EXPERIENCE":
            case "EDUCATION":
                writeCompanySection(entry, dos);
                break;
        }
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                writeSection(entry, dos);
            }
        }
    }

    private void readTextSection(DataInputStream dis, Resume resume, String type) throws IOException {
        resume.setSection(SectionType.valueOf(type), new TextSection(dis.readUTF()));
    }

    private void readListSection(DataInputStream dis, Resume resume, String type) throws IOException {
        List<String> strings = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            strings.add(dis.readUTF());
        }
        resume.setSection(SectionType.valueOf(type), new ListSection(strings));
    }

    private void readCompanySection(DataInputStream dis, Resume resume, String type) throws IOException {
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
        resume.setSection(SectionType.valueOf(type), new CompanySection(companies));
    }

    private void readSection(DataInputStream dis, Resume resume) throws IOException {
        String type = dis.readUTF();
        switch (type) {
            case "PERSONAL":
            case "OBJECTIVE":
                readTextSection(dis, resume, type);
                break;
            case "ACHIEVEMENT":
            case "QUALIFICATIONS":
                readListSection(dis, resume, type);
                break;
            case "EXPERIENCE":
            case "EDUCATION":
                readCompanySection(dis, resume, type);
                break;
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
}