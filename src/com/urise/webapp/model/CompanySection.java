package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    private List<String> names;
    private List<String> urls;
    private List<Period> periods;

    public CompanySection(List<String> names, List<String> urls, List<Period> periods) {
        this.names = names;
        this.urls = urls;
        this.periods = periods;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(names, that.names) && Objects.equals(urls, that.urls) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(names, urls, periods);
    }

    @Override
    public String toString() {
        return "names=" + names + ", urls=" + urls + ", periods=" + periods;
    }
}
