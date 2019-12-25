package pip.pip4.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserDTO implements Serializable {

    @NotNull
    @Size(min=3, max=20)
    private String username;

    @NotNull
    @Size(min=4, max=60)
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
