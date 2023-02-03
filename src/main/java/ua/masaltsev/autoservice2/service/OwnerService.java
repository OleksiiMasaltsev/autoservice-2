package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Owner;

public interface OwnerService {
    Owner save(Owner owner);

    Owner getById(Long id);

    void delete(Long id);
}
