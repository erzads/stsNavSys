package com.dsaita.NavSys.pathing;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;

import java.util.ArrayList;

public class AbstractDungeonWrapper {

    public MapRoomNode getCurrMapNode(){
        return AbstractDungeon.getCurrMapNode();
    }

    public ArrayList<ArrayList<MapRoomNode>> getMap(){
        return AbstractDungeon.map;
    }
}
