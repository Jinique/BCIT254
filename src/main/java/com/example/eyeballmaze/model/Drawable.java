package com.example.eyeballmaze.model;

import com.example.eyeballmaze.R;

import java.util.Dictionary;
import java.util.Hashtable;

public class Drawable {
    private Dictionary<String, Integer> map = new Hashtable<>();

    public Drawable(){
        map.put("  ", R.drawable.empty_block);

        map.put("CC", R.drawable.cyan_cross);
        map.put("DC", R.drawable.cyan_diamond);
        map.put("FC", R.drawable.cyan_flower);
        map.put("SC", R.drawable.cyan_star);

        map.put("CG", R.drawable.green_cross);
        map.put("DG", R.drawable.green_diamond);
        map.put("FG", R.drawable.green_flower);
        map.put("SG", R.drawable.green_star);

        map.put("CR", R.drawable.red_cross);
        map.put("DR", R.drawable.red_diamond);
        map.put("FR", R.drawable.red_flower);
        map.put("SR", R.drawable.red_star);

        map.put("CY", R.drawable.yellow_cross);
        map.put("DY", R.drawable.yellow_diamond);
        map.put("FY", R.drawable.yellow_flower);
        map.put("SY", R.drawable.yellow_star);

        map.put("U", R.drawable.eyeball_up);
        map.put("D", R.drawable.eyeball_down);
        map.put("L", R.drawable.eyeball_left);
        map.put("R", R.drawable.eyeball_right);

        map.put("G", R.drawable.goal);

    }

    public int getDrawable(String key){
        return map.get(key);
    }
}
