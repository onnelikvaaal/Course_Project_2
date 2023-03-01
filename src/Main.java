import taskmanagement.TaskService;
import taskmanagement.exception.IncorrectArgumentException;
import taskmanagement.exception.TaskNotFoundException;
import taskmanagement.task.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Task task1 = new WeeklyTask("Strawberry", Type.PERSONAL, LocalDateTime.parse("2023-02-26T00:00:00"), "Buy strawberry on the Sunday market");
        Task task2 = new DailyTask("Cats feeding", Type.PERSONAL, LocalDateTime.parse("2023-02-26T00:00:00"), "Feed Pyatno, Pisklya & Hitler");
        Task task3 = new MonthlyTask("Coding", Type.WORK, LocalDateTime.parse("2023-03-12T00:00:00"), "Write some code with closed eyes");

        Map<Integer, Task> exampleMap = new HashMap<>();

        exampleMap.put(task1.getId(), task1);
        exampleMap.put(task2.getId(), task2);
        exampleMap.put(task3.getId(), task3);

        TaskService taskService = new TaskService(exampleMap, new ArrayList<>());

        Scanner sc = new Scanner(System.in);

        menu(taskService, sc);
    }

    public static void menu(TaskService taskService, Scanner sc) {
        System.out.println("Доступные операции:\n 0 - получить задачи на текущий день\n " +
                "1 - создать новую задачу\n 2 - удалить задачу по id\n 3 - выход");
        Integer operation = null;
        List<Integer> allowedOperations = Arrays.asList(0, 1, 2, 3);
        while (operation == null) {
            try {
                System.out.println("Выберите операцию:");
                operation = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Неправильно выбрана операция, введите 0, 1 или 2!");
                sc.nextLine();
            } finally {
                if (!allowedOperations.contains(operation)) {
                    operation = null;
                }
            }
        }

        switch (operation) {
            case 0:
                getTasks(taskService, sc);
                menu(taskService, sc);
                break;
            case 1:
                try {
                    createTask(taskService, sc);
                } catch (IncorrectArgumentException e) {
                    System.out.println("Не удалось создать задачу!");
                }
                menu(taskService, sc);
                break;
            case 2:
                try {
                    deleteTask(taskService, sc);
                } catch (TaskNotFoundException e) {
                    System.out.println("Нет задачи с таким ID!");
                }
                menu(taskService, sc);
                break;
            case 3:
                System.out.println("Exit");
                break;
        }
    }

    public static void getTasks(TaskService taskService, Scanner sc) {
        LocalDate localDate = null;
        while (localDate == null) {
            try {
                System.out.println("Введите дату в формате yyyy-mm-dd");
                String input = sc.nextLine();
                localDate = LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.print("Неправильно введена дата! ");
            }
        }
        List<Task> listForDate = taskService.getAllByDate(localDate);
        if (listForDate.isEmpty()) {
            System.out.println("На эту дату нет задач");
        }
        for (Task t : listForDate) {
            System.out.println(t);
        }
    }

    public static Task createTask(TaskService taskService, Scanner sc) throws IncorrectArgumentException {
        System.out.println("Введите заголовок задачи");
        String title = sc.nextLine();
        System.out.println("Введите описание задачи");
        String description = sc.nextLine();
        System.out.println("Введите тип задачи (WORK или PERSONAL)");
        Type type;
        try {
            type = Type.valueOf(sc.nextLine());
        } catch (IllegalArgumentException e) {
            throw new IncorrectArgumentException("Incorrect argument", "Type");
        }
        System.out.println("Введите дату и время в формате \"yyyy-MM-ddTHH:mm:ss\"");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(sc.nextLine());
        } catch (DateTimeParseException e) {
            throw new IncorrectArgumentException("Incorrect argument", "Date/time");
        }
        System.out.println("Выберите тип повторяемости задачи, если это необходимо: 1 - ежедневная, 2 - еженедельная, " +
                "3 - ежемесячная, 4 - ежегодная");
        int taskRepeatType;
        try {
            taskRepeatType = sc.nextInt();
        } catch (InputMismatchException e) {
            throw new IncorrectArgumentException("Incorrect argument", "Repeat type");
        }

        Task task;
        switch (taskRepeatType) {
            case 1:
                task = new DailyTask(title, type, dateTime, description);
                break;
            case 2:
                task = new WeeklyTask(title,type, dateTime, description);
                break;
            case 3:
                task = new MonthlyTask(title,type, dateTime, description);
                break;
            case 4:
                task = new YearlyTask(title, type, dateTime, description);
                break;
            default:
                task = new OneTimeTask(title, type, dateTime, description);
                break;
        }
        taskService.add(task);
        return task;
    }

    public static Task deleteTask(TaskService taskService, Scanner sc) throws TaskNotFoundException {
        Integer taskId = null;
        while (taskId == null) {
            try {
                System.out.println("Введите ID задачи:");
                taskId = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Неправильно введён ID!");
                sc.nextLine();
            }
        }
        return taskService.remove(taskId);
    }
}