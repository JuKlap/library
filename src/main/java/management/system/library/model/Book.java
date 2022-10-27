package management.system.library.model;

import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@ApiModel(description = "Model of Book")
@Component
@EqualsAndHashCode
@NoArgsConstructor
@Data
public class Book {

    @ApiModelProperty(
            notes = "Book id", required = true, example = "1", hidden = false
    )
    @NotNull
    private Long id;

    @ApiModelProperty(
            notes = "Book's title")
    @NotNull
    private String title;

    @ApiModelProperty(
            notes = "Book's isbn")
    @NotNull
    private String isbn;

}