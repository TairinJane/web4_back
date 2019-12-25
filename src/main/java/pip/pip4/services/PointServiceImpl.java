package pip.pip4.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pip.pip4.authentication.JwtUtil;
import pip.pip4.dtos.PointDTO;
import pip.pip4.entities.Point;
import pip.pip4.repositories.PointRepository;
import pip.pip4.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository, JwtUtil jwtUtil, UserRepository userRepository) {
        this.pointRepository = pointRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void addPoint(String header, PointDTO pointDTO) {
        String token = jwtUtil.getTokenFromHeader(header);
        if (token != null) {
            String username = jwtUtil.getUsernameFromToken(token);
            Point point = new Point(pointDTO.getX(), pointDTO.getY(), pointDTO.getR(), pointDTO.getResult());
            point.setUser(userRepository.findByUsername(username));
            pointRepository.save(point);
        }
    }

    @Override
    public List<PointDTO> getPoints(String header) {
        String token = jwtUtil.getTokenFromHeader(header);
        List<PointDTO> pointDTOS = new ArrayList<>();
        if (token != null) {
            long userId = Long.parseLong(jwtUtil.getUserIdFromToken(token));
            List<Point> points = pointRepository.findAllByUserId(userId);
            for (Point point: points) {
                PointDTO pointDTO = new PointDTO(point.getX(), point.getY(), point.getR(), point.isResult());
                pointDTOS.add(pointDTO);
            }
        }
        return pointDTOS;
    }
}
