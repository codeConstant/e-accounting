package com.amplify.user.models.opaque.user;

import com.amplify.user.models.entities.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserRequest extends CreateUserCuRequest {


    private Long id;

    @Override
    public User buldUser(){
        var user = super.buldUser();
        user.setId(id);
        return user;
    }
}
