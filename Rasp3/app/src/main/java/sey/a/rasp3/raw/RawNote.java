package sey.a.rasp3.raw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RawNote {
    private int activity;
    private String value = "";
    private String text = "";
}
