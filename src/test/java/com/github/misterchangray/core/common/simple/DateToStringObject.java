package com.github.misterchangray.core.common.simple;

import com.github.misterchangray.core.annotation.MagicClass;
import com.github.misterchangray.core.annotation.MagicField;
import com.github.misterchangray.core.enums.TimestampFormatter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


/**
 * 将date格式化为字符串
 */
@MagicClass
public class DateToStringObject {
    @MagicField(order = 1, size = 8,timestampFormat = TimestampFormatter.TO_TIMESTAMP_STRING, formatPattern = "yyyyMMdd")
    private Date a;
    @MagicField(order = 2, size = 17,timestampFormat = TimestampFormatter.TO_TIMESTAMP_STRING, formatPattern = "yyyyMMdd-HH:mm:ss")
    private Instant b;
    @MagicField(order = 3, size = 8,timestampFormat = TimestampFormatter.TO_TIMESTAMP_STRING, formatPattern = "HH:mm:ss")
    private    LocalTime c;
    @MagicField(order = 4,size = 14, timestampFormat = TimestampFormatter.TO_TIMESTAMP_STRING, formatPattern = "yyyyMMdd HH mm")
    private LocalDate d;
    @MagicField(order = 5,size = 17, timestampFormat = TimestampFormatter.TO_TIMESTAMP_STRING, formatPattern = "yyyyMMdd@HH@mm@ss")
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
