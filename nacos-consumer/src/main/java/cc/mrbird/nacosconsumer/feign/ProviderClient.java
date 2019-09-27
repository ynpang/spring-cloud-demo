package cc.mrbird.nacosconsumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nacos-provider")
public interface ProviderClient {

    @GetMapping("/hi")
    public String hi(@RequestParam(value = "name",defaultValue = "forezp",required = false)String name);
}
