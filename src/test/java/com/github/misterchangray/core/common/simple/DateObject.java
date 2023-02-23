package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@MagicClass
public class DateObject {
    @MagicField(order = 1)
    private Date a;
    @MagicField(order = 2)
    private Instant b;
    @MagicField(order = 3)
    private    LocalTime c;
    @MagicField(order = 4)
    private LocalDate d;
    @MagicField(order = 5)
    private LocalDateTime e;

    public Date getA() {
        return a;
    }

    public void setA(Date a) {
        this.a = a;
    }

    public Instant getB() {
        return b;
    }

    public void setB(Instant b) {
        this.b = b;
    }

    public LocalTime getC() {
        return c;
    }

    public void setC(LocalTime c) {
        this.c = c;
    }

    public LocalDate getD() {
        return d;
    }

    public void setD(LocalDate d) {
        this.d = d;
    }

    public LocalDateTime getE() {
        return e;
    }

    public void setE(LocalDateTime e) {
        this.e = e;
    }
}
