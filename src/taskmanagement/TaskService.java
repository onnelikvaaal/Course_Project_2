package taskmanagement;

import taskmanagement.exception.TaskNotFoundException;
import taskmanagement.task.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskService {//сервисный класс

    private Map<Integer, Task> taskMap;
    private List<Task> removedTasks;

    public TaskService(Map<Integer, Task> taskMap, List<Task> removedTasks) {
        this.taskMap = taskMap;
        this.removedTasks = removedTasks;
    }

    public void add(Task task) {
        taskMap.put(task.getId(), task);
    }

    public Task remove(Integer id) throws TaskNotFoundException {
        Task task = taskMap.remove(id);
        if (task == null) {
            throw new TaskNotFoundException("No task with ID: " + id);
        }
        removedTasks.add(task);
        return task;
    }

    public List<Task> getAllByDate(LocalDate localDate) {
        List<Task> forTodayList = new ArrayList<>();
        for (Map.Entry<Integer, Task> mapEntry : taskMap.entrySet()) {
            if (mapEntry.getValue().appearsIn(localDate)) {
                forTodayList.add(mapEntry.getValue());
            }
        }
        return forTodayList;
    }






}
