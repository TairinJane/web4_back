package pip.pip4.services;

import pip.pip4.dtos.PointDTO;

import java.util.List;

public interface PointService {
    void addPoint(String header, PointDTO pointDTO);

    List<PointDTO> getPoints(String header);
}
