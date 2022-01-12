package dtos;

import entities.Role;
import entities.User;
import entities.UserInformation;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {


    private String dto_userName;
    private String  dto_userPass;
    private List<Role> dto_roleList = new ArrayList<>();
    private List<String> dto_roleListAsString = new ArrayList<>();
    private UserInformation dto_userInformation;


    public String getDto_userName() {
        return dto_userName;
    }

    public void setDto_userName(String dto_userName) {
        this.dto_userName = dto_userName;
    }

    public String getDto_userPass() {
        return dto_userPass;
    }

    public void setDto_userPass(String dto_userPass) {
        this.dto_userPass = dto_userPass;
    }

    public List<Role> getDto_roleList() {
        return dto_roleList;
    }

    public void setDto_roleList(List<Role> dto_roleList) {
        this.dto_roleList = dto_roleList;
    }

    public List<String> getDto_roleListAsString() {
        return dto_roleListAsString;
    }

    public void setDto_roleListAsString(List<String> dto_roleListAsString) {
        this.dto_roleListAsString = dto_roleListAsString;
    }

    public UserInformation getDto_userInformation() {
        return dto_userInformation;
    }

    public void setDto_userInformation(UserInformation dto_userInformation) {
        this.dto_userInformation = dto_userInformation;
    }
}
