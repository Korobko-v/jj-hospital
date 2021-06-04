package ru.levelp.hospital.model;

import lombok.Getter;

@Getter
public enum Day {
    MONDAY(1,"Понедельник"),
    TUESDAY(2,"Вторник"),
    WEDNESDAY(3,"Среда"),
    THURSDAY(4,"Четверг"),
    FRIDAY(5,"Пятница"),
    SATURDAY(6,"Суббота"),
    SUNDAY(7,"Воскресенье");

    private final String day;
    private final Integer order;

    Day(Integer order, String day) {
        this.order = order;
        this.day = day;
    }
}
