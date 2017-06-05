package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.models.AvailableTrainCourierModel;
import tztexpress.models.Traincourier;
import tztexpress.models.TraincourierRoute;
import tztexpress.repositories.TrainCourierRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainCourierService {
    private TrainCourierRepository trainCourierRepository;

    @Autowired
    public TrainCourierService(TrainCourierRepository trainCourierRepository) {
        this.trainCourierRepository = trainCourierRepository;
    }

    public List<Traincourier> listAll() {
        List<Traincourier> traincouriers = new ArrayList<>();
        trainCourierRepository.findAll().forEach(traincouriers::add);
        return traincouriers;
    }

    public Traincourier getById(long id) {
        return this.trainCourierRepository.findOne(id);
    }

    public AvailableTrainCourierModel getTrainCourierForRoute(String weekday, String originTrainStation, String destinationTrainStation) {
        AvailableTrainCourierModel returnValue = new AvailableTrainCourierModel();

        List<TraincourierRoute> availableCourierRoutes = trainCourierRepository.availableCourierRoutes(weekday, originTrainStation, destinationTrainStation);

        if(availableCourierRoutes.size() < 1) {
            returnValue.isAvailable = false;
            return returnValue;
        } else {
            // Initially, just get the first courierId, might want to refactor this to a round robin
            returnValue.isAvailable = true;
            returnValue.traincourierId = availableCourierRoutes.get(0).getTraincourierId();
            return returnValue;
        }
    }
}
