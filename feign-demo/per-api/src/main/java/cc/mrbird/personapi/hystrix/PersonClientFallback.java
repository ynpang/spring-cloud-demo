package cc.mrbird.personapi.hystrix;

import cc.mrbird.personapi.domain.Person;
import cc.mrbird.personapi.service.PersonService;

import java.util.Collection;
import java.util.Collections;

public class PersonClientFallback implements PersonService {
    @Override
    public boolean save(Person person) {
        return false;
    }

    @Override
    public Collection<Person> findAll() {
        return Collections.emptyList();
    }
}
