package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Product;

public interface ProductService {
    Product save(Product product);
    Product getById(Long id);
    Product update(Product product);
    void delete(Long id);
}
