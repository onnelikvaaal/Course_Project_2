package taskmanagement.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class YearlyTask extends Task {

    public YearlyTask(String title, Type type, LocalDateTime dateTime, String description) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        if (getDateTime().toLocalDate().isEqual(localDate)
                || getDateTime().toLocalDate().getDayOfYear() == localDate.getDayOfYear()) {
            return true;
        } else {
            return false;
        }
    }
}
