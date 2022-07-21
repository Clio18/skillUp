package com.obolonyk.skillup.serialization;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Message implements Serializable {
    private Date date;
    private String message;
    private double amount;

    public Message(Date date, String message, double amount) {
        this.date = date;
        this.message = message;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Message{" +
                "date=" + date +
                ", message='" + message + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Double.compare(message1.amount, amount) == 0 && date.equals(message1.date) && message.equals(message1.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, message, amount);
    }
}
