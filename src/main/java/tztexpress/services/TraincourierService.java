package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.models.*;
import tztexpress.repositories.TraincourierRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A service to get a list, or a single traincourier, or an available courier for a specific route.
 */
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

    /**
     * Gets a single traincourier
     * @param id the traincourierId
     * @return the traincourier
     */
    public Traincourier getById(long id) {
        return this.traincourierRepository.findOne(id);
    }

    /**
     * Returns a courier that is available on the given date with the given route-parameters
     * @param weekday the current weekday
     * @param originTrainStation the first trainstation of the route
     * @param destinationTrainStation the last trainstation of the route
     * @return a model with a boolean if the courier is available, and the courierId if it is.
     */
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
