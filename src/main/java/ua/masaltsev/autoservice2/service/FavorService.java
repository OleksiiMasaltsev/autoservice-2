package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Favor;

public interface FavorService {
    Favor save(Favor favor);

    Favor getById(Long id);

    void deleteById(Long id);
}
