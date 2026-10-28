package ru.nsu.kruzhaev;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Класс {@code Edge}.
 */
public class Vertex {
    private String name;
    private List<Vertex> parents;
    private List<Vertex> children;

    /**
     * Конструктор класса {@code Edge}.
     *
     * @param name Имя вершины.
     */
    public Vertex(String name) {
        if (name == null) {
            throw new NullPointerException("Нулевой указатель в имени переменной!");
        }

        this.name = name;
        parents = new ArrayList<>();
        children = new ArrayList<>();
    }

    /**
     * Метод, добавляющий родителя вершины в соответствующий список.
     *
     * @param vrtx Родитель текущей вершины.
     */
    public void addParent(Vertex vrtx) {
        parents.add(vrtx);
    }

    /**
     * Метод, удаляющий родителя вершины из соответствующего списка.
     *
     * @param vertex Родитель текущей вершины.
     */
    public void deleteParent(Vertex vertex) {
        parents.remove(vertex);
    }

    /**
     * Метод, добавляющий ребенка вершины в соответствующий список.
     *
     * @param vrtx Родитель текущей вершины.
     */
    public void addChild(Vertex vrtx) {
        children.add(vrtx);
    }

    /**
     * Метод, удаляющий ребенка вершины из соответствующего списка.
     *
     * @param vertex Ребенок текущей вершины.
     */
    public void deleteChild(Vertex vertex) {
        children.remove(vertex);
    }

    /**
     * Метод, список родителей вершины.
     *
     * @return список родителей вершины.
     */
    public List<Vertex> getParents() {
        return parents;
    }

    /**
     * Метод, список детей вершины.
     *
     * @return список детей вершины.
     */
    public List<Vertex> getChildren() {
        return children;
    }

    /**
     * Метод, имя вершины.
     *
     * @return имя вершины.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод очищающий вершину перед удалением.
     */
    public void clean() {
        for (Vertex v : children) {
            v.deleteParent(this);
        }
        for (Vertex v : parents) {
            v.deleteChild(this);
        }
    }

    /**
     * Метод переопределяющий метод {@code toString}.
     *
     * @return Имя вершины.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Переопределение метода {@code hashCode}.
     *
     * @return Хэш имени вершины.
     */
    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    /**
     * Переопределение метода {@code equals}.
     *
     * @return Результат сравнения объектов.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }

        Vertex vertex = (Vertex) o;

        return Objects.equals(name, vertex.name);
    }
}
