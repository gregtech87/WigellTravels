package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.DestinationRepository;
import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService{

    private final Logger logger = LogManager.getLogger("myLogger");
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
        Destination destination = new Destination();
        if (d.isPresent()){
            destination = d.get();
        }
        return destination;
    }

    @Override
    @Transactional
    public Destination save(Destination destination) {
//        destination = checkIfExistsInDatabaseIfNotSave(destination, false);
        Destination savedDestination = destinationRepository.save(destination);
        logger.info("Destination saved: " + savedDestination);
        return savedDestination;
    }

    @Override
    public Destination updateDestination(int id, Destination destination) {
        Destination destinationFromDb = findById(id);

        destinationFromDb.setHotellName(destination.getHotellName());
        destinationFromDb.setPricePerWeek(destination.getPricePerWeek());
        destinationFromDb.setCity(destination.getCity());
        destinationFromDb.setCountry(destination.getCountry());
        destination.setId(id);
        logger.info("Destination edited \nFrom: " + destination + "\nTo: " + destinationFromDb);
        return save(destinationFromDb);
    }

    @Override
    @Transactional
    public String deleteById(int id) {
        String message = "";
        List<Trip> tripList = destinationRepository.findAllByDestination(findById(id).getId());
        System.out.println("RESA BASERAT PÅÅ RESA: " + tripList);
        System.out.println(tripList.size());
        if (tripList.size() == 0){
            message = "Destination with id: " + id + " has been deleted!";
            logger.info("Destination was deleted: " + findById(id));
            destinationRepository.deleteById(id);
            return message;
        }else {
            message = "Destination with id: " + id + " can not be deleted! Destination is used in " + tripList.size() + " trips!";
            for (Trip t : tripList){
                String tripId = "\n" + "Trip ID: " + String.valueOf(t.getTripId());
                message = message.concat(tripId);
            }
            logger.info(message);
        }
        return message;
    }

    @Override
    @Transactional
    public Destination checkIfExistsInDatabaseIfNotSave(Destination destination, boolean autoSave) {
//        final Destination baseLineDestination = new Destination();
//        baseLineDestination.setId(destination.getId());
//        baseLineDestination.setCity(destination.getCity());
//        baseLineDestination.setCountry(destination.getCountry());
//        baseLineDestination.setPricePerWeek(destination.getPricePerWeek());
//        baseLineDestination.setHotellName(destination.getHotellName());

        String city = destination.getCity();
        String country = destination.getCountry();
        String hotellName = destination.getHotellName();

        if (destination.getId() > 0){
          return updateDestination(destination.getId(), destination);
        }
        Destination destinationFromDatabase = destinationRepository.findDestinationByHotellNameAndCityAndCountry(hotellName, city, country);
        if (destinationFromDatabase != null){
            return destinationFromDatabase;
        }
        if(autoSave){
            destinationFromDatabase = save(destination);
            return destinationFromDatabase;
        }
      return destination;
    }
}
