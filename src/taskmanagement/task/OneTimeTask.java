package taskmanagement.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OneTimeTask extends Task {

    public OneTimeTask(String title, Type type, LocalDateTime dateTime, String description) {
        super(title, type, dateTime, description);
    }

    @Override
    public boolean appearsIn(LocalDate localDate) {
        if (getDateTime().toLocalDate().isEqual(localDate)) {
            return true;
        } else {
            return false;
        }
    }
}
