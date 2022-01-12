package dtos;

import entities.UserInformation;
import java.util.Objects;

public class UserInformationDTO
{

    private int dto_id;
    private String dto_gender;
    private String dto_name;
    private int dto_age;
    private int dto_phone;
    private String dto_user;

    public UserInformationDTO(UserInformationDTO uif)
    {
        if (uif.dto_id != 0)
        {
            this.dto_id = uif.dto_id ;
        }
        this.dto_gender = uif.dto_gender;
        this.dto_name = uif.dto_name;
        this.dto_age = uif.dto_age;
        this.dto_phone = uif.dto_phone;
        this.dto_user = uif.dto_user;
    }


    public UserInformationDTO(UserInformation uif)
    {
        if (uif.getId() != 0)
        {
            this.dto_id = uif.getId();
        }
        this.dto_gender = uif.getGender();
        this.dto_name = uif.getName();
        this.dto_age = uif.getAge();
        this.dto_phone = uif.getPhone();
        this.dto_user = uif.getUser();
    }

    public int getDto_id()
    {
        return dto_id;
    }

    public void setDto_id(int dto_id)
    {
        this.dto_id = dto_id;
    }

    public String getDto_gender()
    {
        return dto_gender;
    }

    public void setDto_gender(String dto_gender)
    {
        this.dto_gender = dto_gender;
    }

    public String getDto_name()
    {
        return dto_name;
    }

    public void setDto_name(String dto_name)
    {
        this.dto_name = dto_name;
    }

    public int getDto_age()
    {
        return dto_age;
    }

    public void setDto_age(int dto_age)
    {
        this.dto_age = dto_age;
    }

    public int getDto_phone()
    {
        return dto_phone;
    }

    public void setDto_phone(int dto_phone)
    {
        this.dto_phone = dto_phone;
    }


    public String getDto_user()
    {
        return dto_user;
    }

    public void setDto_user(String dto_user)
    {
        this.dto_user = dto_user;
    }

    
    @Override
    public int hashCode()
    {
        return Objects.hash(dto_id, dto_gender, dto_name, dto_age, dto_phone, dto_user);
    }
}


