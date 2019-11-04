package com.projectRest.model;


import com.projectRest.entity.EntityWorkday;

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

    public Workday() {
        super();
    }


}
