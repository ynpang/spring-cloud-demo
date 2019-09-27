package cc.mrbird.personapi.service;

import cc.mrbird.personapi.domain.Person;
import cc.mrbird.personapi.hystrix.PersonClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

@FeignClient(value = "person-service",fallback = PersonClientFallback.class)
public interface PersonService {

    @PostMapping(value = "/person/save")
    boolean save(@RequestBody Person person);

    @GetMapping(value = "/person/find/all")
    Collection<Person> findAll();

}
