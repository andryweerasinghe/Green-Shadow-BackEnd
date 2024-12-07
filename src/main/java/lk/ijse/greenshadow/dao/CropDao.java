package lk.ijse.greenshadow.dao;

import lk.ijse.greenshadow.entity.impl.CropEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository   //marking as a srping data repository
public interface CropDao extends JpaRepository<CropEntity,String> {
}
