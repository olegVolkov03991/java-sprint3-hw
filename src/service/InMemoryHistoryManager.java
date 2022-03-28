package service;

import model.Node;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Node> historyList = new ArrayList<>();
    private final Map<Integer, Node> nodeMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    public Node<Task> linkLast(Task task) {
        Node<Task> lastNode = tail;
        Node<Task> newNode = new Node(tail, task, null);
        if (lastNode == null) {
            head = newNode;
            tail = newNode;
            newNode.prev = null;
            newNode.next = null;
            historyList.add(newNode);
        } else {
            lastNode.next = newNode;
            newNode.prev = lastNode;
            tail = newNode;
            historyList.add(newNode);
        }
        return newNode;
    }

    public List<Task> getTasks(){
        List<Task> tasks = new ArrayList<>();
        Node<Task> temp = head;
        while (temp != null){
            tasks.add(temp.data);
            temp = temp.next;
        } return tasks;
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
    public Node removeNode(Node node) {
        node = nodeMap.get(((Task) node.data).getId());
    if (node.prev == null && node.next == null) {
        historyList.remove(nodeMap.get(((Task) node.data).getId()));
        nodeMap.remove(((Task) node.data).getId());
        head = null;
    } else if (node.prev == null && node.next != null) {
        Node nextNode = node.next;
        nextNode.prev = null;
        historyList.remove(nodeMap.get(((Task) node.data).getId()));
        nodeMap.remove(((Task) node.data).getId());
    } else if (node.prev != null && node.next != null) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        prevNode.next = nextNode;
        nextNode.prev = prevNode;
        historyList.remove(nodeMap.get(((Task) node.data).getId()));
        nodeMap.remove(((Task) node.data).getId());
    } else if (node.prev != null && node.next == null) {
        Node prevNode = node.prev;
        node.prev = node.prev.prev;
        prevNode.next = null;
        tail = prevNode;
        historyList.remove(nodeMap.get(((Task) node.data).getId()));
        nodeMap.remove(((Task) node.data).getId());
    } return node;

}

    @Override
    public List<Task> getHistory() {
      return getTasks();
    }
}
