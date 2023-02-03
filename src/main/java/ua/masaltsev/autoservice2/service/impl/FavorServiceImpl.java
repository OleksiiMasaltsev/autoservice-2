package ua.masaltsev.autoservice2.service.impl;

import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Favor;
import ua.masaltsev.autoservice2.repository.FavorRepository;
import ua.masaltsev.autoservice2.service.FavorService;

@Service
public class FavorServiceImpl implements FavorService {
    private final FavorRepository favorRepository;

    public FavorServiceImpl(FavorRepository favorRepository) {
        this.favorRepository = favorRepository;
    }

    @Override
    public Favor save(Favor favor) {
        return favorRepository.save(favor);
    }

    @Override
    public Favor getById(Long id) {
        return favorRepository.getReferenceById(id);
    }

    @Override
    public void deleteById(Long id) {
        favorRepository.deleteById(id);
    }
}
