package sey.a.rasp3.raw;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RawLessonDate {
    private String condition;
    private String lessonTime;
    private String startTime;
    private String endTime;
    private String lessonType;
    private String lessonDiscipline;
    private String teachers;
    private String auditorium;
}
