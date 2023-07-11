package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import java.time.LocalDate;
import java.util.List;

class ResumeTestData {
    static final String UUID_1 = "uuid1";
    static final Resume R1 = new Resume(UUID_1, UUID_1);
    static final List<String> CONTACTS = List.of("+7(921) 855-0482 ", "skype:grigory.kislin", "gkislin@yandex.ru", "https://www.linkedin.com/in/gkislin",
            "https://github.com/gkislin", "https://stackoverflow.com/users/548473", "http://gkislin.ru/");
    static final String PERSONAL = "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры. ";
    static final String OBJECTIVE = "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям ";
    static final List<String> ACHIEVEMENTS = List.of("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет",
            "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников. ",
            "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk. ",
            "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера. ",
            "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга. ",
            "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django). ",
            "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
    static final List<String> QUALIFICATIONS = List.of("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ",
            "Version control: Subversion, Git, Mercury, ClearCase, Perforce ",
            "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB ",
            "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy ",
            "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts ",
            "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements). ",
            "Python: Django. ", "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ", "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka ",
            "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT. ",
            "Инструменты: Maven + plugin development, Gradle, настройка Ngnix ", "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer ",
            "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования ",
            "Родной русский, английский \"upper intermediate\"");

    static final Company EXPERIENCE_COMPANY_1 = new Company("Java Online Projects", "http://javaops.ru/",
            List.of(new Period("Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                    LocalDate.of(2013, 10, 1), null)));
    static final Company EXPERIENCE_COMPANY_2 = new Company("Wrike", "https://www.wrike.com/",
            List.of(new Period("Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                    LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1))));
    static final Company EXPERIENCE_COMPANY_3 = new Company("RIT Center", "",
            List.of(new Period("Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python",
                    LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1))));
    static final Company EXPERIENCE_COMPANY_4 = new Company("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/",
            List.of(new Period("Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.",
                    LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1))));
    static final List<String> EDUCATION_NAMES = List.of("Coursera", "Luxoft", "Siemens AG", "Alcatel");
    static final List<String> EDUCATION_URLS = List.of("https://www.coursera.org/course/progfun", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
            "http://www.siemens.ru/", "http://www.alcatel.ru/");
    static final List<Period> EDUCATION_PERIODS = List.of(new Period(null, "'Functional Programming Principles in Scala' by Martin Odersky",
                    LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1)),
            new Period(null, "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                    LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1)),
            new Period(null, "3 месяца обучения мобильным IN сетям (Берлин)",
                    LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1)),
            new Period(null, "6 месяцев обучения цифровым телефонным сетям (Москва)",
                    LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1)));
    static final Company EDUCATION_COMPANY_1 = new Company("Coursera", "https://www.coursera.org/course/progfun",
            List.of(new Period(null, "'Functional Programming Principles in Scala' by Martin Odersky",
                    LocalDate.of(2013, 3, 1), LocalDate.of(2013, 5, 1))));
    static final Company EDUCATION_COMPANY_2 = new Company("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
            List.of(new Period(null, "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'",
                    LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1))));
    static final Company EDUCATION_COMPANY_3 = new Company("Siemens AG", "http://www.siemens.ru/",
            List.of(new Period(null, "3 месяца обучения мобильным IN сетям (Берлин)",
                    LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1))));
    static final Company EDUCATION_COMPANY_4 = new Company("Alcatel", "http://www.alcatel.ru/",
            List.of(new Period(null, "6 месяцев обучения цифровым телефонным сетям (Москва)",
                    LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1))));
    static final List<Section> SECTIONS = List.of(new TextSection(PERSONAL), new TextSection(OBJECTIVE), new ListSection(ACHIEVEMENTS), new ListSection(QUALIFICATIONS),
            new CompanySection(List.of(EXPERIENCE_COMPANY_1, EXPERIENCE_COMPANY_2, EXPERIENCE_COMPANY_3, EXPERIENCE_COMPANY_4)),
            new CompanySection(List.of(EDUCATION_COMPANY_1, EDUCATION_COMPANY_2, EDUCATION_COMPANY_3, EDUCATION_COMPANY_4)));

    public static void main(String[] args) {
        for (ContactType type : ContactType.values()) {
            R1.setContact(type, CONTACTS.get(type.ordinal()));
        }
        for (SectionType type : SectionType.values()) {
            R1.setSection(type, SECTIONS.get(type.ordinal()));
        }
        System.out.println("Resume uuid = " + R1.getUuid() + " fullname = " + R1.getFullName());
        System.out.println();
        System.out.println("Контакты:");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + ": " + R1.getContact(type));
        }
        System.out.println();
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
            System.out.println(R1.getSection(type));
        }
    }
}
