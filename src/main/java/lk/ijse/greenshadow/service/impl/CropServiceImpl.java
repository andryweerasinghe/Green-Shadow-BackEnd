package lk.ijse.greenshadow.service.impl;

import lk.ijse.greenshadow.dao.CropDao;
import lk.ijse.greenshadow.dao.FieldDao;
import lk.ijse.greenshadow.dto.impl.CropDtoImpl;
import lk.ijse.greenshadow.entity.impl.CropEntity;
import lk.ijse.greenshadow.entity.impl.FieldEntity;
import lk.ijse.greenshadow.exception.CropNotFoundException;
import lk.ijse.greenshadow.exception.DataPersistException;
import lk.ijse.greenshadow.exception.FieldNotFoundException;
import lk.ijse.greenshadow.service.CropService;
import lk.ijse.greenshadow.utill.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CropServiceImpl implements CropService {
    @Autowired
    private CropDao cropDao;
    @Autowired
    private FieldDao fieldDao;
    @Autowired
    private Mapping mapping;

    @Override
    public void saveCrops(String cropCode, String commonName, String scientificName, String category, String base67FieldImg, String season, String field_code) {
        CropDtoImpl dto = getCropDto(cropCode,commonName,scientificName,category,base67FieldImg,season,field_code);

        CropEntity save = cropDao.save(mapping.toCropEntity(dto));
        if (save == null) {
            throw new DataPersistException("Failed to save crop");
        }
    }

    @Override
    public List<CropDtoImpl> loadAllCrops() {
        return mapping.toAllCrops(cropDao.findAll());
    }

    @Override
    public void deleteCrop(String cropCode) {
        Optional<CropEntity> cropFound = cropDao.findById(cropCode);
        if (cropFound.isEmpty()) {
            throw new CropNotFoundException("Crop not found");
        } else {
            cropDao.deleteById(cropCode);
        }
    }

    @Override
    public void updateCrops(String cropCode, String commonName, String scientificName, String category, String base67FieldImg, String season, String fieldCode) {
        CropDtoImpl dto = getCropDto(cropCode, commonName, scientificName, category, base67FieldImg, season, fieldCode);
        Optional<CropEntity> crop = cropDao.findById(cropCode);
        if (crop.isPresent()) {
            crop.get().setCommon_name(dto.getCommon_name());
            crop.get().setScientific_name(dto.getScientific_name());
            crop.get().setCategory(dto.getCategory());
            crop.get().setImg(dto.getImg());
            crop.get().setSeason(dto.getSeason());

            Optional<FieldEntity> field = fieldDao.findById(fieldCode);
            if (field.isPresent()) {
                crop.get().setField(field.get());
            } else {
                throw new FieldNotFoundException("Field not found");
            }
            crop.get().setCrop_code(cropCode);
        }
    }

    @Override
    public CropDtoImpl getCropByID(String cropCode) {
        if (cropDao.existsById(cropCode)) {
            CropEntity crop = cropDao.getReferenceById(cropCode);
            return mapping.toCropDto(crop);
        } else {
            throw new CropNotFoundException("Crop not found");
        }
    }

    private CropDtoImpl getCropDto(String cropCode, String commonName, String scientificName, String category, String base67FieldImg, String season, String field_code) {
        CropDtoImpl cropDto = new CropDtoImpl();
        cropDto.setCrop_code(cropCode);
        cropDto.setCommon_name(commonName);
        cropDto.setScientific_name(scientificName);
        cropDto.setCategory(category);
        cropDto.setImg(base67FieldImg);
        cropDto.setSeason(season);
        cropDto.setField_code(field_code);
        return cropDto;
    }
}
