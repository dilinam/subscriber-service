package org.dtf202.subscriberservice.service;

import lombok.RequiredArgsConstructor;
import org.dtf202.subscriberservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;



}
