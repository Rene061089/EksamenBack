package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "user_information")
@Entity
@NamedQuery(name = "user_information.deleteAllRows", query = "DELETE from UserInformation")
public class UserInformation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    private String gender;
    private String name;
    private int age;
    private int phone;
    private String user;



    public UserInformation()
    {
    }

    public UserInformation(String gender, String name, int age, int phone, String user)
    {
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.user = user;
    }




    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public int getPhone()
    {
        return phone;
    }

    public void setPhone(int phone)
    {
        this.phone = phone;
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }


    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "gender = " + gender + ", " +
                "name = " + name + ", " +
                "age = " + age + ", " +
                "phone = " + phone + ", " +
                "user = " + user + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInformation that = (UserInformation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}