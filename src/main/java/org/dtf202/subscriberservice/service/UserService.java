package org.dtf202.subscriberservice.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.entity.User;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Map<String, Object> getAllUsers(int pageNumber, int pageSize, String globalFilter) {
        Page<User> userPage = userRepository
            .findAllUsers
                (PageRequest.of(pageNumber, pageSize), globalFilter, globalFilter, globalFilter);
        return Map.of("data", userPage.stream().toList(), "count", userPage.getTotalElements());
    }

    public User getUserById(long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User not found"));
    }

    @Transactional
    public void changeUserStatus(long id) throws Exception {
        User user = getUserById(id);
        user.setIsActive(!user.getIsActive());
        userRepository.save(user);
    }

    @Transactional
    public void editUser(User editingUser) throws Exception {
        User user = getUserById(editingUser.getId());
        user.setFirstName(editingUser.getFirstName());
        user.setLastName(editingUser.getLastName());
        userRepository.save(user);
    }

}
