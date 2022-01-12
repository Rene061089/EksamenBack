package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import dtos.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@NamedQuery(name = "users.deleteAllRows", query = "DELETE from User")
@Table(name = "users")
public class User implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String userName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "user_pass")
    private String userPass;
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
            @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
    @ManyToMany
    private List<Role> roleList = new ArrayList<>();
    @JoinColumn
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private UserInformation userInformation;

    public User(UserDTO udto) {

        this.userName = udto.getDto_userName();

        this.userPass = BCrypt.hashpw(udto.getDto_userPass(), BCrypt.gensalt());

    }

    public List<String> getRolesAsStrings()
    {
        if (roleList.isEmpty())
        {
            return null;
        }
        List<String> rolesAsStrings = new ArrayList<>();
        roleList.forEach((role) ->
        {
            rolesAsStrings.add(role.getRoleName());
        });
        return rolesAsStrings;
    }

    public User()
    {
    }




    public boolean verifyPassword(String pw)
    {
        return (BCrypt.checkpw(pw, userPass));
    }

    public User(String userName, String userPass)
    {
        this.userName = userName;

        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public UserInformation getUserInformation()
    {
        return userInformation;
    }

    public void setUserInformation(UserInformation userInformation)
    {
        this.userInformation = userInformation;

    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPass()
    {
        return this.userPass;
    }

    public void setUserPass(String userPass)
    {
        this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
    }

    public List<Role> getRoleList()
    {
        return roleList;
    }

    public void setRoleList(List<Role> roleList)
    {
        this.roleList = roleList;
    }

    public void addRole(Role userRole)
    {
        roleList.add(userRole);
    }

}
