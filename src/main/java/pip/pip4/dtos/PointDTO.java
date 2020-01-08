package pip.pip4.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PointDTO implements Serializable {
    @NotNull
    @Max(6)
    @Min(-6)
    private Double x;

    @NotNull
    @Max(6)
    @Min(-6)
    private Double y;

    @NotNull
    @Max(3)
    @Min(1)
    private Integer r;

    @NotNull
    private Boolean result;

    public PointDTO(Double x, Double y, Integer r, Boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Integer getR() {
        return r;
    }

    public Boolean getResult() {
        return result;
    }
}
