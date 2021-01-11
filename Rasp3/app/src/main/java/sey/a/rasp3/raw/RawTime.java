package sey.a.rasp3.raw;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sey.a.rasp3.shell.Clocks;

@Getter
@Setter
@AllArgsConstructor
public class RawTime {
    private String name;
    private Clocks start;
    private Clocks end;
}
