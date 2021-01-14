package sey.a.rasp3.raw;

import java.util.Calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RawSchedule extends RawDefault {
    private String name;
    private Calendar start;
    private Calendar end;
}
