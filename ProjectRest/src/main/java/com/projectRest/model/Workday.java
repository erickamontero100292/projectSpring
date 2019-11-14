package com.projectRest.model;


import com.projectRest.entity.EntityWorkday;

import java.util.Objects;

public class Workday {
    private Long id;

    private String name;

    private Long numberDailyHour;

    private Long numberWeekHour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getNumberWeekHour() {
        return numberWeekHour;
    }

    public void setNumberWeekHour(Long numberWeekHour) {
        this.numberWeekHour = numberWeekHour;
    }

    public Workday(Long id, String name, Long numberDailyHour) {
        super();
        this.id = id;
        this.name = name;
        this.numberDailyHour = numberDailyHour;
    }

    public Long getNumberDailyHour() {
        return numberDailyHour;
    }

    public void setNumberDailyHour(Long numberDailyHour) {
        this.numberDailyHour = numberDailyHour;
    }

    public Workday(Long id, String name, Long numberDailyHour, Long numberWeekHour) {
        super();
        this.id = id;
        this.name = name;
        this.numberDailyHour = numberDailyHour;
        this.numberWeekHour = numberWeekHour;
    }

    public Workday(String name, Long numberDailyHour, Long numberWeekHour) {
        this.name = name;
        this.numberDailyHour = numberDailyHour;
        this.numberWeekHour = numberWeekHour;
    }

    public Workday(EntityWorkday entityWorkday) {
        this.id = entityWorkday.getId();
        this.name = entityWorkday.getName();
        this.numberDailyHour = entityWorkday.getNumberDailyHour();
        this.numberWeekHour = entityWorkday.getNumberWeekHour();
    }

    public Workday(Workday workday) {
        this.name = (workday.getName().equalsIgnoreCase(this.name)) ? this.name : workday.getName();
        this.numberDailyHour = workday.getNumberDailyHour().equals(this.numberDailyHour) ? workday.getNumberDailyHour(): this.numberDailyHour ;
        this.numberWeekHour = workday.getNumberWeekHour().equals(this.numberWeekHour) ?workday.getNumberWeekHour()  :  this.numberWeekHour;
    }


    public Workday() {
        super();
    }

    public boolean emptyWorkday(){
        if (this.id == null)
            return true;
        return false;
    }

    public boolean isEmptyName(Workday workday) {
        if (workday == null || workday.getName() == null || workday.getName().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workday workday = (Workday) o;
        return name.equals(workday.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
