package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Product;

public interface ProductService {
    Product save(Product product);

    Product getById(Long id);

    void delete(Long id);
}
