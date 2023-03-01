package taskmanagement.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WeeklyTask extends Task {

    public WeeklyTask(String title, Type type, LocalDateTime dateTime, String description) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        if (getDateTime().toLocalDate().isEqual(localDate)
                || getDateTime().toLocalDate().getDayOfWeek().equals(localDate.getDayOfWeek())) {
            return true;
        } else {
            return false;
        }
    }
}
