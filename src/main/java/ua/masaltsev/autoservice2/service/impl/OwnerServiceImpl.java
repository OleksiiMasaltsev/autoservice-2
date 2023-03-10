package ua.masaltsev.autoservice2.service.impl;

import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Owner;
import ua.masaltsev.autoservice2.repository.OwnerRepository;
import ua.masaltsev.autoservice2.service.OwnerService;

@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerServiceImpl(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public Owner getById(Long id) {
        return ownerRepository.getReferenceById(id);
    }

    @Override
    public void delete(Long id) {
        ownerRepository.deleteById(id);
    }
}
