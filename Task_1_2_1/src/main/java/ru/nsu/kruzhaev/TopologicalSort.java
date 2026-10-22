package ru.nsu.kruzhaev;

import java.util.*;

int WHITE = 0;
int GREY = 1;
int BLACK = 2;

public class TopologicalSort {
    private Stack<Vertex> stack;
    private Graph graph;
    private Map<Vertex, Integer> colorVertices;

    public TopologicalSort(Graph graph) {
        this.graph = graph;
        stack = new Stack<>();
        colorVertices = new HashMap<>();



    }

    private Set<Vertex> sort() {
        Vertex start = null;

        for (Vertex vrtx : graph.getVertices()) {
            if (start == null && vrtx.getParents().isEmpty()) {
                start = vrtx;
            }
            colorVertices.put(vrtx, WHITE);
        }

        Vertex current = start;

        while (true) {
            colorVertices.replace(current, GREY);
            switch (colorVertices.get(current)) {
                case GREY: {
                    throw new RuntimeException("Обнаружен цикл!");
                }
                case WHITE: {
                    for (Vertex v : current.getChildren()) {
                        if (colorVertices.get(v) == WHITE) {
                            current = v;
                        }
                    }
                    break;
                }
                default: {
                    continue;
            }
            }
        }
    }
}
