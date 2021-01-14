package sey.a.rasp3.raw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sey.a.rasp3.shell.Clocks;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RawTime extends RawDefault {
    private String name;
    private Clocks start;
    private Clocks end;
}
