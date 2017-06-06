package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.models.*;
import tztexpress.repositories.TraincourierRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TraincourierService {
    private TraincourierRepository traincourierRepository;
    private ModelProviderService modelProviderService;

    @Autowired
    public TraincourierService(TraincourierRepository traincourierRepository, ModelProviderService modelProviderService) {
        this.traincourierRepository = traincourierRepository;
        this.modelProviderService = modelProviderService;
    }

    public List<TraincourierModel> listAll() {
        List<TraincourierModel> returnValue = new ArrayList<>();
        List<Traincourier> traincouriers = new ArrayList<>();
        traincourierRepository.findAll().forEach(traincouriers::add);

        for(Traincourier courier : traincouriers) {
            returnValue.add(this.modelProviderService.TraincourierToModel(courier));
        }
        return returnValue;
    }

    public TraincourierModel getModelById(long id) {
        Traincourier courier = this.getById(id);
        return this.modelProviderService.TraincourierToModel(courier);
    }

    public Traincourier getById(long id) {
        return this.traincourierRepository.findOne(id);
    }

    public AvailableTrainCourierModel getTrainCourierForRoute(String weekday, String originTrainStation, String destinationTrainStation) {
        AvailableTrainCourierModel returnValue = new AvailableTrainCourierModel();

        List<TraincourierRoute> availableCourierRoutes = traincourierRepository.availableCourierRoutes(weekday, originTrainStation, destinationTrainStation);

        if(availableCourierRoutes.size() < 1) {
            returnValue.isavailable = false;
            return returnValue;
        } else {
            // Initially, just get the first courierId, might want to refactor this to a round robin
            returnValue.isavailable = true;
            returnValue.traincourierid = availableCourierRoutes.get(0).getTraincourierId();
            return returnValue;
        }
    }
}
