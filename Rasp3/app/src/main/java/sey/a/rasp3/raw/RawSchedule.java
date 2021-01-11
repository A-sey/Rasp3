package sey.a.rasp3.raw;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RawSchedule {
    private String name;
    private Calendar start;
    private Calendar end;
}
