package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.DestinationRepository;
import com.greger.wigelltravels.entity.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService{

    private DestinationRepository destinationRepository;

    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination findById(int id) {
        Optional<Destination> d = destinationRepository.findById(id);
        Destination destination;
        if (d.isPresent()){
            destination = d.get();
        }
        else {
            throw new RuntimeException("Destination with id: " + id + " could not be found!");
        }
        return destination;
    }

    @Override
    public Destination save(Destination destination) {
        return destinationRepository.save(destination);
    }

    @Override
    public void deleteById(int id) {
        destinationRepository.deleteById(id);
    }
}
