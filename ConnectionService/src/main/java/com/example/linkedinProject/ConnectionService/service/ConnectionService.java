package com.example.linkedinProject.ConnectionService.service;

import com.example.linkedinProject.ConnectionService.entity.Person;
import com.example.linkedinProject.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {

    private final PersonRepository personRepository;

    public List<Person> getFirstDegreeConnectionsOfUser(Long userId){
        log.info("Getting first degree connections of user with userId : {} ",userId);
        return personRepository.getFirstDegreeConnections(userId);
    }
}
