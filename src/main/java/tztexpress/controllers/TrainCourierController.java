package tztexpress.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tztexpress.core.GenericResultHandler;
import tztexpress.models.GenericResult;
import tztexpress.models.Traincourier;
import tztexpress.services.AuthenticationService;
import tztexpress.services.TrainCourierService;

import java.util.List;

@Controller
@RequestMapping("/api/courier")
public class TrainCourierController
{
    private TrainCourierService trainCourierService;

    @Autowired
    public TrainCourierController(TrainCourierService trainCourierService) {
        this.trainCourierService = trainCourierService;
    }

    @RequestMapping(value = "/couriers", method = RequestMethod.GET)
    public @ResponseBody
    GenericResult<List<Traincourier>> listTrainCouriers(@RequestHeader HttpHeaders headers)
    {
        if(AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication"))) {
            return GenericResultHandler.GenericResult(trainCourierService.listAll());
        } else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    GenericResult<Traincourier> getTrainCourier(@RequestHeader HttpHeaders headers, @PathVariable String id)
    {
        if (AuthenticationService.ValidateToken(headers.getValuesAsList("Authentication")))
        {
            return GenericResultHandler.GenericResult(trainCourierService.getById(Long.valueOf(id)));
        }
        else {
            return GenericResultHandler.GenericExceptionResult("Invalid authentication token");
        }
    }
}
