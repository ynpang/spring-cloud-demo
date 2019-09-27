package cc.mrbird.personserviceprovider.controller;

import cc.mrbird.personapi.domain.Person;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class PersonServiceProviderController {

    private final Map<Long,Person> persons = new ConcurrentHashMap<>();

    private final static Random random = new Random();

    @PostMapping(value = "/person/save")
    public boolean savePerson(@RequestBody Person person){
        return  persons.put(person.getId(),person) == null;
    }

    @GetMapping(value = "/person/find/all")
    @HystrixCommand( fallbackMethod = "fallbackForFindAllPersons",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "100")
    })
    public Collection<Person> findAllPersons() throws Exception {
        int value = random.nextInt(200);
        System.out.println("findAllPersons() costs" + value + "ms.");
        Thread.sleep(value);
        return persons.values();
    }

    public Collection<Person> fallbackForFindAllPersons(){
        System.err.println("fallbackForFindAllPersons() is invoked!");
        return Collections.emptyList();
    }
}
