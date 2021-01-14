package sey.a.rasp3.raw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RawDiscipline extends RawDefault {
    private String name;
    private String shortName;
    private String comment;
}
