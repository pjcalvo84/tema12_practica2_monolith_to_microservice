package com.urjc.mca.tema12.p2.feign;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "product", url = "${monolith.host}")
public interface ProductClient {


    @RequestMapping(method = RequestMethod.GET, value = "/product/{id}/stock/{stock}")
    Boolean haveStock(@PathVariable(value = "id") long id,
                      @PathVariable(value = "stock") int stock);

    @RequestMapping(method = RequestMethod.PUT, value = "/product/{id}")
    void updateStock(@PathVariable("id") long id, Map<String, String> operator);

}
