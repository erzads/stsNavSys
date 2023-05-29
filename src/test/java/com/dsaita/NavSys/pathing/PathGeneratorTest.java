package com.dsaita.NavSys.pathing;

import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

class PathGeneratorTest {

    /**
     *  0/0 ----- 10/10 ----- 20/10
     *      |           \---- 20/20
     *      |           |
     *      |           \---- 20/30
     *      |              /
     *      |            /
     *      |           |
     *      \---- 20/10 ----- 20/40
     */
    @Test
    void test() {
        ArrayList<ArrayList<MapRoomNode>> map = new ArrayList<>();
        MapRoomNode firstNode = new MapRoomNode(0, 0);
        firstNode.addEdge(new MapEdge(0,0,0,0));
        map.add(new ArrayList<>(Collections.singletonList(firstNode)));

        MapRoomNode firstNodeChild1 = new MapRoomNode(10, 10);
        firstNodeChild1.addParent(firstNode);
        firstNodeChild1.addEdge(new MapEdge(0,0,0,0));
        MapRoomNode firstNodeChild2 = new MapRoomNode(20, 10);
        firstNodeChild2.addParent(firstNode);
        firstNodeChild2.addEdge(new MapEdge(0,0,0,0));
        ArrayList<MapRoomNode> secondFloor = new ArrayList<>();
        secondFloor.add(firstNodeChild1);
        secondFloor.add(firstNodeChild2);
        map.add(secondFloor);

        MapRoomNode child1Child1 = new MapRoomNode(20, 10);
        child1Child1.addParent(firstNodeChild1);
        child1Child1.addEdge(new MapEdge(0,0,0,0));
        MapRoomNode child1Child2 = new MapRoomNode(20, 20);
        child1Child2.addParent(firstNodeChild1);
        child1Child2.addEdge(new MapEdge(0,0,0,0));
        MapRoomNode child1and2Child1 = new MapRoomNode(20, 30);
        child1and2Child1.addParent(firstNodeChild1); // Test node with multiple parents
        child1and2Child1.addParent(firstNodeChild2); // Test node with multiple parents
        child1and2Child1.addEdge(new MapEdge(0,0,0,0));
        MapRoomNode child2Child2 = new MapRoomNode(20, 40);
        child2Child2.addParent(firstNodeChild2);
        child2Child2.addEdge(new MapEdge(0,0,0,0));

        ArrayList<MapRoomNode> thirdFloor = new ArrayList<>();
        thirdFloor.add(child1Child1);
        thirdFloor.add(child1Child2);
        thirdFloor.add(child1and2Child1);
        thirdFloor.add(child2Child2);
        map.add(thirdFloor);

        AbstractDungeonWrapper abstractDungeonWrapper = Mockito.mock(AbstractDungeonWrapper.class);

        when(abstractDungeonWrapper.getCurrMapNode()).thenReturn(firstNode);
        when(abstractDungeonWrapper.getMap()).thenReturn(map);

        List<List<MapRoomNode>> possiblePaths = new PathGenerator(abstractDungeonWrapper).generateAllPossiblePaths();
        System.out.println(possiblePaths);
    }

}