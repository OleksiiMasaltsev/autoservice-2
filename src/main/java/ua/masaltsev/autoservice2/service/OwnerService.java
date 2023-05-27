package ua.masaltsev.autoservice2.service;

import java.util.List;
import ua.masaltsev.autoservice2.model.Owner;

public interface OwnerService {
    Owner save(Owner owner);

    Owner getById(Long id);

    List<Owner> getAll();

    void delete(Long id);
}
