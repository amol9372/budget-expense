package org.budget.tracker.expenseapp.service;

import org.budget.tracker.expenseapp.rest.request.CreateUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void createUser(CreateUserRequest request);

}
