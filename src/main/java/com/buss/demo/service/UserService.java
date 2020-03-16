package com.buss.demo.service;

import com.buss.demo.domain.User;
import com.buss.demo.persistence.UserRepository;
import io.micrometer.azuremonitor.AzureMonitorMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private static final String COUNTER_USER_CREATE_WITH_CONTACT = "user.create.contact";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AzureMonitorMeterRegistry meterRegistry;

    @Transactional(propagation = Propagation.REQUIRED)
    public User create(User user) {
        if (!user.getContacts().isEmpty()) {
            meterRegistry.counter(COUNTER_USER_CREATE_WITH_CONTACT);
            user.getContacts().forEach(contact -> contact.setUser(user));
        }
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User not found: " + id));
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        userRepository.delete(findById(id));
    }
}
