package ua.masaltsev.autoservice2.service;

import ua.masaltsev.autoservice2.model.Favor;

public interface FavorService {
    Favor save(Favor favor);
    Favor getById(Long id);
    Favor update(Favor favor);
    void delete(Long id);
}
