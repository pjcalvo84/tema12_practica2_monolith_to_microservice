package com.urjc.mca.tema12.p2.feign;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import feign.Body;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "customer", url = "${monolith.host}")
public interface CustomerClient {


    @RequestMapping(method = RequestMethod.GET, value = "/customer/{id}/credit/{credit}")
    Boolean haveCredit(@RequestParam(value = "id") long id,
                                 @RequestParam(value = "credit") int credit);

    @RequestMapping(method = RequestMethod.PUT, value = "/customer/{id}")
    CustomerDto updateCredit(@PathVariable("id") long id, Map<String, String> operator);


}
