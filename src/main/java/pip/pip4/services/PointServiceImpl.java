package pip.pip4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pip.pip4.dtos.PointDTO;
import pip.pip4.entities.Point;
import pip.pip4.repositories.PointRepository;
import pip.pip4.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addPoint(String username, PointDTO pointDTO) {
        Point point = new Point(pointDTO.getX(), pointDTO.getY(), pointDTO.getR(), pointDTO.getResult());
        point.setUser(userRepository.findByUsername(username));
        pointRepository.save(point);
    }


    @Override
    public List<PointDTO> getPoints(String username) {
        long userId = userRepository.findByUsername(username).getId();
        List<PointDTO> pointDTOS = new ArrayList<>();
        List<Point> points = pointRepository.findAllByUserId(userId);
        for (Point point : points) {
            PointDTO pointDTO = new PointDTO(point.getX(), point.getY(), point.getR(), point.isResult());
            pointDTOS.add(pointDTO);
        }
        return pointDTOS;
    }

}

