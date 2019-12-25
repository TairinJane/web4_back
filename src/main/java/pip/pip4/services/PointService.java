package pip.pip4.services;

import pip.pip4.dtos.PointDTO;
import pip.pip4.entities.Point;

import java.util.List;

public interface PointService {
    public void addPoint(String header, PointDTO pointDTO);
    public List<PointDTO> getPoints(String header);
}
