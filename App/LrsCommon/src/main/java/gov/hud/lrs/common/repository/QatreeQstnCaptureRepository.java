package gov.hud.lrs.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import gov.hud.lrs.common.entity.QatreeQstnCapture;

@Repository
public interface QatreeQstnCaptureRepository extends CrudRepository<QatreeQstnCapture, String> {
}
