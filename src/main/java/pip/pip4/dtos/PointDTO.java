package pip.pip4.dtos;

import javax.validation.constraints.*;
import java.io.Serializable;

public class PointDTO implements Serializable {
    @NotNull
    @Max(3)
    @Min(-5)
    private Double x;

    @NotNull
    @DecimalMax(value = "5", inclusive = false)
    @DecimalMin(value = "-3", inclusive = false)
    private Double y;

    @NotNull
    @Max(3)
    @Min(1)
    private Integer r;

    @NotNull
    private Boolean result;

    public PointDTO(@NotNull @Max(3) @Min(-5) Double x, @NotNull @DecimalMax(value = "5", inclusive = false) @DecimalMin(value = "-3", inclusive = false) Double y, @NotNull @Max(3) @Min(1) Integer r, @NotNull Boolean result) {
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
