package entities;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "booking")
@Entity
@NamedQuery(name = "booking.deleteAllRows", query = "DELETE from Booking ")
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private int booking_id;
    private double duration;
    private String date;
    private String time;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")
    private User user;

    public Booking()
    {
    }

    public Booking(int booking_id, double duration, String date, String time)
    {
        this.booking_id = booking_id;
        this.duration = duration;
        this.date = date;
        this.time = time;
    }

    public Booking(double duration, String date, String time)
    {
        this.duration = duration;
        this.date = date;
        this.time = time;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public int getBooking_id()
    {
        return booking_id;
    }

    public void setBooking_id(int booking_id)
    {
        this.booking_id = booking_id;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(double duration)
    {
        this.duration = duration;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return booking_id == booking.booking_id && Double.compare(booking.duration, duration) == 0 && date.equals(booking.date) && time.equals(booking.time) && user.equals(booking.user);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(booking_id, duration, date, time, user);
    }
}