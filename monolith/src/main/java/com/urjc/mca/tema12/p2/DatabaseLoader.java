package com.urjc.mca.tema12.p2;

import com.urjc.mca.tema12.p2.model.Customer;
import com.urjc.mca.tema12.p2.model.Product;
import com.urjc.mca.tema12.p2.repository.CustomerRepository;
import com.urjc.mca.tema12.p2.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.text.ParseException;

/**
 * Cargador de la BD y solución de apartados de la práctica.
 *
 * @author J. Manuel Colmenar
 */
@Controller
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void run(String... args) throws ParseException {

        System.out.println("\n-- Carga inicial de datos ------------------------------------------------\n");

        Customer customer = new Customer();
        customer.setCreditLimit(10);
        customer.setName("Pablo");

        customerRepository.save(customer);

        Product product = new Product();
        product.setStock(10);
        product.setName("airPods");
        product.setPrice(15);

        productRepository.save(product);

    }

}
