package management.system.library.model;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@ApiModel(description = "Model of Author")
@Component
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Author {

    @ApiModelProperty(
            notes = "Author id", required = true, example = "1", hidden = false
    )
    @NotNull
    private Long id;

    @ApiModelProperty(
            notes = "Author's name")
    @NotNull
    private String firstName;

    @ApiModelProperty(
            notes = "Author's surname")
    @NotNull
    private String surname;

}
