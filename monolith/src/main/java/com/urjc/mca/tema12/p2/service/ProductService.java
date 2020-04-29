package com.urjc.mca.tema12.p2.service;

import com.urjc.mca.tema12.p2.dto.ProductDto;
import com.urjc.mca.tema12.p2.mapper.ProductMapper;
import com.urjc.mca.tema12.p2.model.Product;
import com.urjc.mca.tema12.p2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDto save(ProductDto productDto) {
        Product product = convertBasicPostDTOToPostEntity(productDto);
        return convertProductToProductDto(productRepository.save(product));
    }

    public ProductDto getProduct(long id) {
        return convertProductToProductDto(productRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<ProductDto> getProducts() {
        return productRepository.findAll().stream().map(customer -> convertProductToProductDto(customer)).collect(Collectors.toList());
    }

    private ProductDto convertProductToProductDto(Product product) {
        return ProductMapper.MAPPER.toBasicProductDto(product);
    }

    private Product convertBasicPostDTOToPostEntity(ProductDto producDto) {
        return ProductMapper.MAPPER.toProduct(producDto);
    }

    public boolean haveStock(long id, int stock) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return product.getStock() >= stock;
    }

    public ProductDto updateStock(long id, Map<String, String> operation) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        processUpdateCredit(product, operation);
        return convertProductToProductDto(productRepository.save(product));
    }

    private void processUpdateCredit(Product product, Map<String, String> operation) throws IllegalArgumentException {
        if(operation.get("operation").equals("add")){
            product.setStock(product.getStock() + getStock(operation, "stock"));
        }
        else if(operation.get("operation").equals("subtract")){
            if(product.getStock() > getStock(operation, "stock"))
                product.setStock(product.getStock() - getStock(operation, "stock"));
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }

    private int getStock(Map<String, String> operation, String amount) {
        return Integer.parseInt(operation.get(amount));
    }
}
