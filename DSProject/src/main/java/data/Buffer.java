/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import main.Tree;

/**
 *
 * @author Euaggelos
 */
public class Buffer {

    private List list;

    public Buffer() {
        list = new ArrayList();
    }

    public void addMsg(ChatDataUnit msg) {
        list.add(msg);

    }
    
    public void addMsgs(ArrayList<ChatDataUnit> lst){
        for(ChatDataUnit data : lst){
            addMsg(data);
        }
    }
    
    public List getMsgs(){
        return this.list;
    }

    public void cmpr() {
        Collections.sort(list, new Comparator<ChatDataUnit>() {

            public int compare(ChatDataUnit o1, ChatDataUnit o2) {
                Tree tree1 = o1.getTree();
                Tree tree2 = o2.getTree();

                int v = tree1.compareTo(tree2);

                return v;

            }

        }
        );
    }

}
