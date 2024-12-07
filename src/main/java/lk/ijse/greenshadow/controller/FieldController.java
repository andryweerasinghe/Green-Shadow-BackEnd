package lk.ijse.greenshadow.controller;

import lk.ijse.greenshadow.dto.impl.FieldDtoImpl;
import lk.ijse.greenshadow.exception.FieldNotFoundException;
import lk.ijse.greenshadow.service.FieldService;
import lk.ijse.greenshadow.utill.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("api/v1/field")
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveFields(@RequestPart("fieldID") String fieldID, @RequestPart("fieldName") String fieldName, @RequestPart("fieldLocation") String fieldLocation, @RequestPart("fieldSize") String fieldSize,
            @RequestPart("image_01") MultipartFile image_01,
            @RequestPart("image_02") MultipartFile image_02) {
        String base67FieldImg01 = "";
        String base67FieldImg02 = "";

        try {
            byte[] image01Bytes = image_01.getBytes();
            base67FieldImg01 = AppUtil.imageToBase64(image01Bytes);
            byte[] image02Bytes = image_02.getBytes();
            base67FieldImg02 = AppUtil.imageToBase64(image02Bytes);

            fieldService.saveFields(fieldID,fieldName,fieldLocation,fieldSize,base67FieldImg01,base67FieldImg02);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FieldDtoImpl> getAllFields() {
        return fieldService.loadAllFields();
    }

    @GetMapping(value = "/{field_code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FieldDtoImpl searchFieldsByID(@PathVariable("field_code") String fieldCode) {
        return fieldService.getFieldsByID(fieldCode);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @DeleteMapping(value = "/{field_code}")
    public ResponseEntity<Void> deleteField(@PathVariable("field_code") String fieldCode) {
        try {
            fieldService.deleteField(fieldCode);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (FieldNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('SCIENTIST')")
    @PatchMapping(value = "/{field_code}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateFields(@RequestPart("fieldName") String fieldName, @RequestPart("fieldLocation") String fieldLocation, @RequestPart("fieldSize") String fieldSize,
                                           @RequestPart("image_01") MultipartFile image_01,
                                           @RequestPart("image_02") MultipartFile image_02,@PathVariable("field_code") String fieldID) {
        String base67FieldImg01 = "";
        String base67FieldImg02 = "";

        try {
            byte[] image01Bytes = image_01.getBytes();
            base67FieldImg01 = AppUtil.imageToBase64(image01Bytes);
            byte[] image02Bytes = image_02.getBytes();
            base67FieldImg02 = AppUtil.imageToBase64(image02Bytes);

            fieldService.updateFields(fieldID,fieldName,fieldLocation,fieldSize,base67FieldImg01,base67FieldImg02);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
