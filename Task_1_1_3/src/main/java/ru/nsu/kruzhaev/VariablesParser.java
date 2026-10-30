package ru.nsu.kruzhaev;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс для парсинга переменных.
 */
public class VariablesParser {
    private Map<String, Integer> variables;

    /**
     * Конструктор класса {@code VariableParser}.
     *
     * @param variables Строка с переменными.
     */
    public VariablesParser(String variables) {
        this.variables = new HashMap<>();

        String[] vars = variables.trim().split(";");
        for (String each : vars) {
            String var = each.trim();
            String[] splitedVar = var.split("=");
            checkVar(splitedVar);

            String name = splitedVar[0].trim();
            int value = Integer.parseInt(splitedVar[1].trim());
            this.variables.put(name, value);
        }
    }

    /**
     * Проверка переменной.
     *
     * @param var Список, состоящий из имени переменной и её значения.
     */
    private void checkVar(String[] var) {
        if (var.length <= 1) {
            throw new IllegalArgumentException("Illegal format of variable \"" + var[0]
                    + "\": format \"key = value\" expected");
        }

        var[0] = var[0].trim();
        var[1] = var[1].trim();

        for (int i = 0; i < var[1].length(); i++) {
            char x = var[1].charAt(i);
            if (!Character.isDigit(x)) {
                throw new NumberFormatException(
                        "Unexpected value at \"" + var[0] + "\" variable: Int expected"
                );
            }
        }
    }

    /**
     * Метод, возвращающий словарь с переменными и их значениями.
     *
     * @return Словарь с переменными и их значениями.
     */
    public Map<String, Integer> getVariables() {
        return variables;
    }
}
