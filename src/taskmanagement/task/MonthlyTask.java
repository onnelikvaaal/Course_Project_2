package taskmanagement.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MonthlyTask extends Task {

    public MonthlyTask(String title, Type type, LocalDateTime dateTime, String description) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        if (getDateTime().toLocalDate().isEqual(localDate)
                || getDateTime().toLocalDate().getDayOfMonth() == localDate.getDayOfMonth()) {
            return true;
        } else {
            return false;
        }
    }
}
