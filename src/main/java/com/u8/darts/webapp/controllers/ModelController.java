package com.u8.darts.webapp.controllers;

import com.u8.darts.webapp.brokers.ModelBroker;
import com.u8.darts.webapp.data.Model;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelBroker modelBroker;

    @Autowired
    public ModelController(ModelBroker modelBroker) {

        this.modelBroker = modelBroker;
    }

    @ApiOperation(value = "Creates a new model",
            notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Model created")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @JsonEndpoint(value = "", method = RequestMethod.POST)
    public ResponseEntity<Void> registerModel(HttpServletRequest request, @RequestBody Model model) {
        modelBroker.registerModel(request, model);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Log a new model (testing)",
            notes = "dummy endpoint")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Model logged")
    })
    @ResponseStatus(HttpStatus.ACCEPTED)
    @JsonEndpoint(value = "test", method = RequestMethod.POST)
    public ResponseEntity<Void> logModel(HttpServletRequest request, @RequestBody Model model) {
        modelBroker.logModel(request, model);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
