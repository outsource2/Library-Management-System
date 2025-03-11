package com.example.MaidsTest.Template.RestController.Table;

import com.example.MaidsTest.Base.Enum.ESuccess;
import com.example.MaidsTest.Template.API.Response.DTO.Mapper.CBookMapper;
import com.example.MaidsTest.Template.API.Response.DTO.Mapper.CPatronMapper;
import com.example.MaidsTest.Template.API.Response.Patron.CGetPatronResponse;
import com.example.MaidsTest.Template.Model.Table.Patron;
import com.example.MaidsTest.Template.Service.Table.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing patrons.
 * <p>
 * This class exposes REST API endpoints for handling patron-related operations in the library system.
 * It communicates with the {@link PatronService} to perform business logic and interacts with the database
 * to manage patron records. Responses are returned using {@link CGetPatronResponse}.
 * </p>
 */
@RestController
@RequestMapping("/api/patrons")  // Defines the base URL for all patron-related operations.
public class PatronRestController {

    /** Service for handling patron-related business logic. */
    @Autowired
    private PatronService patronService;

    /**
     * Endpoint for retrieving all patrons.
     * <p>
     * This method handles HTTP GET requests to {@code /api/patrons}.
     * It retrieves a list of all patrons in the library system.
     * </p>
     *
     * @return A {@link CGetPatronResponse} containing a list of patrons and success status.
     */
    @GetMapping
    public CGetPatronResponse getAllPatrons() {

        CGetPatronResponse apiResponse = new CGetPatronResponse();

        // Retrieve the list of all patrons using the service
        List<Patron> patrons = patronService.getAllPatrons();

        // Map the patron list to the response format
        apiResponse.setPatronList(CPatronMapper.parse(patrons));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status for success
        apiResponse.setSuccessMessage(ESuccess.GET_ALL_PATRON_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for retrieving a patron by their ID.
     * <p>
     * This method handles HTTP GET requests to {@code /api/patrons/{id}}.
     * It retrieves the details of a specific patron by their ID.
     * </p>
     *
     * @param id The ID of the patron to retrieve.
     * @return A {@link CGetPatronResponse} containing the patron's details and success status.
     */
    @GetMapping("/{id}")
    public CGetPatronResponse getPatronById(@PathVariable Long id) {

        CGetPatronResponse apiResponse = new CGetPatronResponse();

        // Retrieve the patron using the service
        Patron getPatron = patronService.getPatronById(id);

        // Map the patron data to the response format
        apiResponse.setPatron(CPatronMapper.parse(getPatron));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status for success
        apiResponse.setSuccessMessage(ESuccess.GET_PATRON_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for adding a new patron to the system.
     * <p>
     * This method handles HTTP POST requests to {@code /api/patrons}.
     * It allows the addition of a new patron to the library system by providing their information.
     * </p>
     *
     * @param patron The patron object containing the new patron's information.
     * @return A {@link CGetPatronResponse} containing the newly added patron and success status.
     */
    @PostMapping
    public CGetPatronResponse addPatron(@RequestBody Patron patron) {

        CGetPatronResponse apiResponse = new CGetPatronResponse();

        // Add the new patron using the service
        Patron addedPatron = patronService.addPatron(patron);

        // Map the newly added patron data to the response format
        apiResponse.setPatron(CPatronMapper.parse(addedPatron));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status for success
        apiResponse.setSuccessMessage(ESuccess.PATRON_ADDED_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for updating an existing patron's information.
     * <p>
     * This method handles HTTP PUT requests to {@code /api/patrons/{id}}.
     * It allows the update of an existing patron's information by providing their ID and the updated details.
     * </p>
     *
     * @param id     The ID of the patron to update.
     * @param patron The updated patron object.
     * @return A {@link CGetPatronResponse} containing the updated patron and success status.
     */
    @PutMapping("/{id}")
    public CGetPatronResponse updatePatron(@PathVariable Long id, @RequestBody Patron patron) {

        CGetPatronResponse apiResponse = new CGetPatronResponse();

        // Update the patron using the service
        Patron updatedPatron = patronService.updatePatron(id, patron);

        // Map the updated patron data to the response format
        apiResponse.setPatron(CPatronMapper.parse(updatedPatron));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status for success
        apiResponse.setSuccessMessage(ESuccess.PATRON_UPDATED_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for deleting a patron by their ID.
     * <p>
     * This method handles HTTP DELETE requests to {@code /api/patrons/{id}}.
     * It allows the removal of a patron from the library system by providing their ID.
     * </p>
     *
     * @param id The ID of the patron to delete.
     * @return A {@link CGetPatronResponse} with success status after deletion.
     */
    @DeleteMapping("/{id}")
    public CGetPatronResponse deletePatron(@PathVariable Long id) {

        CGetPatronResponse apiResponse = new CGetPatronResponse();

        // Delete the patron using the service
        patronService.deletePatron(id);

        // Return success status
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status for success
        apiResponse.setSuccessMessage(ESuccess.PATRON_DELETED_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }
}
