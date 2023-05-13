package lv.nixx.poc.datastruct.tree;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Node {

    public Integer value;
    public Node right;
    public Node left;

    public Node(Integer value) {
        this.value = value;
    }




}
