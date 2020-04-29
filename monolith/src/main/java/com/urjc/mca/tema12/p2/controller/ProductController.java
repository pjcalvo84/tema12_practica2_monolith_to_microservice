package com.urjc.mca.tema12.p2.controller;

import com.urjc.mca.tema12.p2.dto.CustomerDto;
import com.urjc.mca.tema12.p2.dto.ProductDto;
import com.urjc.mca.tema12.p2.model.Product;
import com.urjc.mca.tema12.p2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> listProduct() {
        List<ProductDto> myList = this.productService.getProducts();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        ProductDto product = this.productService.getProduct(id);
        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ProductDto> newProduct(@RequestBody ProductDto productDto) {
        ProductDto newProduct = this.productService.save(productDto);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }


    @GetMapping("/{id}/stock/{stock}")
    public ResponseEntity<Boolean> haveCredit(@PathVariable long id, @PathVariable int stock) {
        return new ResponseEntity<>(productService.haveStock(id, stock), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable long id, @RequestBody Map<String, String> operation) {
        try {
            ProductDto productDto = productService.updateStock(id, operation);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }
}
