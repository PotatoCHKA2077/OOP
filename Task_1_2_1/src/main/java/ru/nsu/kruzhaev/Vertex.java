package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private String name;
    private List<Vertex> parents;
    private List<Vertex> children;

    public Vertex(String name) {
        this.name = name;
        parents = new ArrayList<>();
        children = new ArrayList<>();
    }

    public void addParent(Vertex vrtx) {
        parents.add(vrtx);
    }

    public void deleteParent(Vertex vertex) {
        parents.remove(vertex);
    }

    public void addChild(Vertex vrtx) {
        children.add(vrtx);
    }

    public void deleteChild(Vertex vertex) {
        children.remove(vertex);
    }

    public List<Vertex> getParents() {
        return parents;
    }

    public List<Vertex> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void clean() {
        for (Vertex v : parents) {
            v.deleteParent(this);
        }
        for (Vertex v : children) {
            v.deleteChild(this);
        }
    }
}
