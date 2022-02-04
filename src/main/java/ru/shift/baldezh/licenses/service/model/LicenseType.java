package ru.shift.baldezh.licenses.service.model;

public enum LicenseType {
    SINGLE(1), CROP5(5), CROP(10);

    private final int count;

    LicenseType(int count) {
       this.count = count;
    }

    public int getCount() {
        return count;
    }
}
