package ua.masaltsev.autoservice2.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import ua.masaltsev.autoservice2.model.Ordering;
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
        return ownerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't get an owner with id: " + id)
        );
    }

    @Override
    public List<Owner> getAll() {
        return ownerRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public List<Ordering> getOrderingsByOwnerId(Long id) {
        return ownerRepository.getOrderingsByOwnerId(id);
    }
}
