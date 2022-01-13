package dtos;

import entities.Booking;
import entities.User;

public class BookingDTO
{

    private int dto_booking_id;
    private double dto_duration;
    private String dto_date;
    private String dto_time;


    public BookingDTO(double dto_duration, String dto_date, String dto_time)
    {
        this.dto_duration = dto_duration;
        this.dto_date = dto_date;
        this.dto_time = dto_time;
    }

    public BookingDTO(Booking b)
    {
        this.dto_booking_id = b.getBooking_id();
        this.dto_duration = b.getDuration();
        this.dto_date= b.getDate();
        this.dto_time = b.getTime();
    }

    public int getDto_booking_id()
    {
        return dto_booking_id;
    }

    public void setDto_booking_id(int dto_booking_id)
    {
        this.dto_booking_id = dto_booking_id;
    }

    public double getDto_duration()
    {
        return dto_duration;
    }

    public void setDto_duration(double dto_duration)
    {
        this.dto_duration = dto_duration;
    }

    public String getDto_date()
    {
        return dto_date;
    }

    public void setDto_date(String dto_date)
    {
        this.dto_date = dto_date;
    }

    public String getDto_time()
    {
        return dto_time;
    }

    public void setDto_time(String dto_time)
    {
        this.dto_time = dto_time;
    }


}
