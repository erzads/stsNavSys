package com.dsaita.NavSys.pathing;

import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.EmptyRoom;
import com.megacrit.cardcrawl.rooms.TreasureRoomBoss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathGenerator {

    private final AbstractDungeonWrapper abstractDungeonWrapper;

    public PathGenerator(AbstractDungeonWrapper abstractDungeonWrapper) {
        this.abstractDungeonWrapper = abstractDungeonWrapper;
    }

    public List<List<MapRoomNode>> generateAllPossiblePaths() {
        List<List<MapRoomNode>> paths = new ArrayList<>();
        MapRoomNode currMapNode = this.abstractDungeonWrapper.getCurrMapNode();
        if (currMapNode != null
                && !(currMapNode.getRoom() instanceof NeowRoom)
                && !(currMapNode.getRoom() instanceof EmptyRoom)
                && !(currMapNode.getRoom() instanceof TreasureRoomBoss)) {
            paths.add(Collections.singletonList(currMapNode));
            addAllPathsFrom(currMapNode, paths);
            if (paths.size() == 1 && paths.get(0).size() == 1) {
                //Currently at the last rest site.
                paths = new ArrayList<>();
            } else {
                // Remove current node from the listing
                paths.forEach(path -> {
                    path.remove(currMapNode);
                });
            }
        } else {
            ArrayList<MapRoomNode> startingNodes = this.abstractDungeonWrapper.getMap().get(0);
            for (MapRoomNode startingNode : startingNodes) {
                paths.add(Collections.singletonList(startingNode));
                addAllPathsFrom(startingNode, paths);
            }
        }
        paths.removeIf(path -> path.size() == 1);
        return paths;
    }

    private void addAllPathsFrom(MapRoomNode node, List<List<MapRoomNode>> paths) {
        List<MapRoomNode> nextNodes = getNextNodes(node);
        List<List<MapRoomNode>> pathsToRemove = new ArrayList<>();
        for (MapRoomNode nextNode : nextNodes) {
            for (List<MapRoomNode> path : new ArrayList<>(paths)) {
                if (nextNode.getParents().contains(path.get(path.size() - 1))) {
                    pathsToRemove.add(path);
                    ArrayList<MapRoomNode> pathCopy = new ArrayList<>(path);
                    pathCopy.add(nextNode);
                    paths.add(pathCopy);
                    addAllPathsFrom(nextNode, paths);
                }
            }
        }

        paths.removeAll(pathsToRemove);
    }

    private List<MapRoomNode> getNextNodes(MapRoomNode startingNode) {
        List<MapRoomNode> nextNodes = new ArrayList<>();
        if (startingNode.hasEdges()) {
            boolean floorHadResults = false;
            for (int i = 0; i < this.abstractDungeonWrapper.getMap().size(); i++) {
                if (floorHadResults) {
                    return nextNodes;
                }
                for (int j = 0; j < this.abstractDungeonWrapper.getMap().get(i).size(); j++) {
                    MapRoomNode node = this.abstractDungeonWrapper.getMap().get(i).get(j);
                    if (node.getParents().contains(startingNode)) {
                        nextNodes.add(node);
                        floorHadResults = true;
                    }
                }
            }
        }
        return nextNodes;
    }
}
