package com.amplify.user.models.service;

import com.amplify.common.enums.Messages;
import com.amplify.common.errors.NotFoundError;
import com.amplify.user.models.entities.User;
import com.amplify.user.models.opaque.user.UpdateUserRequest;
import com.amplify.user.models.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundError(Messages.USER_ID_NOT_FOUND));
    }

    public List<User> findByAll() {
        return userRepository.findAll();
    }

    public User update(UpdateUserRequest updateUserRequest) {
        return userRepository.save(updateUserRequest.buldUser());
    }
}
