package com.wrox.controllers.forms;

import java.util.Arrays;

/**
 * @author dengb
 */
public class WidgetDataSource {
    private Node[] genders;

    public Node[] getGenders() {
        return genders;
    }

    public void setGenders(Node[] genders) {
        this.genders = genders;
    }

    @Override
    public String toString() {
        return "WidgetDataSource{" +
                "genders=" + Arrays.toString(genders) +
                '}';
    }

    public static class Node {
        private String text;
        private String value;

        public Node() {
            super();
        }

        public Node(String text, String value) {
            this.text = text;
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "text='" + text + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
