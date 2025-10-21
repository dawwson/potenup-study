package com.ohgiraffers.collection.mission.a_basic;

import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> taskList = new ArrayList<>();
    private int id = 0;

    public void addTask(String content) {
        // TODO: id 자동 증가
        id += 1;
        Task task = new Task(id, content);
        taskList.add(task);
    }

    public void removeTask(int id) {
        Task task = taskList.get(id);
        taskList.remove(task);
    }

    public void findTask(int id) {
        Task task = taskList.get(id);
        System.out.println(task.id + ". " + task.content);
    }

    public void printAllTasks() {
        for (Task task : taskList) {
            System.out.println(task.id + ". " + task.content);
        }
    }

}