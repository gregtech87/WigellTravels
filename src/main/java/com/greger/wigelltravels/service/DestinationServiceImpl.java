package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.DestinationRepository;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final Logger logger = LogManager.getLogger("MyLogger");
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
        if (d.isPresent()) {
            destination = d.get();
        }
        return destination;
    }

    @Override
    @Transactional
    public Destination save(Destination destination) {
        Destination savedDestination = destinationRepository.save(destination);
        logger.info("Destination saved: " + savedDestination);
        return savedDestination;
    }

    @Override
    public Destination updateDestination(int id, Destination destination, boolean randomize) {
        if (randomize) {
            List<Destination> destinationList = findAll();
            int index = new Random().nextInt(destinationList.size() - 1);
            System.out.println("**************** INDEX: "+index);
            Destination randomDestination =  destinationList.get(index);
            System.out.println("**************** random dest: "+index);
            return findById(randomDestination.getId());
        } else {
            Destination destinationFromDb = findById(id);
            destinationFromDb.setHotellName(destination.getHotellName());
            destinationFromDb.setPricePerWeek(destination.getPricePerWeek());
            destinationFromDb.setCity(destination.getCity());
            destinationFromDb.setCountry(destination.getCountry());
            destination.setId(id);
            logger.info("Destination edited \nFrom: " + destination + "\nTo: " + destinationFromDb);
            return save(destinationFromDb);
        }
    }

    @Override
    @Transactional
    public String deleteById(int id) {
        String message = "";
        List<Trip> tripList = destinationRepository.findAllByDestination(findById(id).getId());
        System.out.println("RESA BASERAT PÅÅ RESA: " + tripList);
        System.out.println(tripList.size());

        //Wigell consernens feature: tar admin bort en destination så tilldelas ALLA som valt den destinationen en slumpmässig destination till sin resa.
            for (Trip t : tripList) {
                Destination newDestination = new Destination();
                while (newDestination.getId() <= 0){
                    newDestination = updateDestination(0, t.getDestination(), true);
                    System.out.println("****************************Slumpad: "+newDestination);
                }
                System.out.println(newDestination);
                t.setDestination(newDestination);

                System.out.println(t);
            }
        System.out.println(tripList);
        destinationRepository.deleteById(id);
        //Original kod som skickar tillbaka ett felmeddelande om att destinationen används i x antal resor samt listar dess ID.
//        if (tripList.size() == 0){
//            message = "Destination with id: " + id + " has been deleted!";
//            logger.info("Destination was deleted: " + findById(id));
//            destinationRepository.deleteById(id);
//            return message;
//        }else {
//            message = "Destination with id: " + id + " can not be deleted! Destination is used in " + tripList.size() + " trips!";
//            for (Trip t : tripList){
//                String tripId = "\n" + "Trip ID: " + String.valueOf(t.getTripId());
//                message = message.concat(tripId);
//            }
//            logger.info(message);
//        }
        message = "Destination with id: " + id + " has been deleted! Destination was used in " + tripList.size() + " trips!";
        for (Trip t : tripList) {
            String tripId = "\n" + "Trip ID: " + t.getTripId();
            message = message.concat(tripId);
        }
        message = message.concat("\nThese trips has now been given a random destination!");
        message = message.concat("\nQuote: It´s not a bug. it´s a feature!! *CEO Wigell Travels");
        if (tripList.size() == 0) {
            message = "Destination removed!";
        }
        return message;
    }

    @Override
    @Transactional
    public Destination checkIfExistsInDatabaseIfNotSave(Destination destination, boolean autoSave) {

        String city = destination.getCity();
        String country = destination.getCountry();
        String hotellName = destination.getHotellName();

        if (destination.getId() > 0) {
            return updateDestination(destination.getId(), destination, false);
        }
        Destination destinationFromDatabase = destinationRepository.findDestinationByHotellNameAndCityAndCountry(hotellName, city, country);
        if (destinationFromDatabase != null) {
            return destinationFromDatabase;
        }
        if (autoSave) {
            return save(destination);
        }
        return destination;
    }
}
