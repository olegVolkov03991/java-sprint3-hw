package main.java.service;

import main.java.model.Node;
import main.java.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    public Node<Task> linkLast(Task task) {
        Node<Task> lastNode = tail;
        Node<Task> newNode = new Node(tail, task, null);
        if (lastNode == null) {
            head = newNode;
            tail = newNode;
            newNode.setPrev(null);
            newNode.setNext(null);
        } else {
            lastNode.setNext(newNode);
            newNode.setPrev(lastNode);
            tail = newNode;
        }
        return newNode;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        while (head != null) {
            tasks.add(head.getData());
            head = head.getNext();
        }
        return tasks;
    }

    @Override
    public void add(Task task) {
        Node node;
        if (nodeMap.isEmpty()) {
            node = linkLast(task);
            nodeMap.put(task.getId(), node);
        } else if (!nodeMap.containsKey((task.getId()))) {
            nodeMap.put(task.getId(), linkLast(task));
        } else {
            removeNode(nodeMap.get(task.getId()));
            nodeMap.put(task.getId(), linkLast(task));
        }
    }

    @Override
    public Node removeNode(Node<Task> node) {
        if (node.getPrev() == null && node.getNext() == null) {
            nodeMap.remove((node.getData()).getId());
            head = null;
        } else if (node.getPrev() == null && node.getNext() != null) {
            Node nextNode = node.getNext();
            nextNode.setPrev(null);
            nodeMap.remove((node.getData()).getId());
        } else if (node.getPrev() != null && node.getNext() != null) {
            Node prevNode = node.getPrev();
            Node nextNode = node.getNext();
            prevNode.setNext(nextNode);
            nextNode.setPrev(prevNode);
            nodeMap.remove((node.getData()).getId());
        } else if (node.getPrev() != null && node.getNext() == null) {
            Node prevNode = node.getPrev();
            node.setPrev(node.getPrev().getPrev());
            prevNode.setNext(null);
            tail = prevNode;
            nodeMap.remove((node.getData()).getId());
        }
        return node;
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }
}
