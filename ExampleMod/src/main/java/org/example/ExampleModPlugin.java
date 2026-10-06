package org.example;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;

public class ExampleModPlugin extends BaseModPlugin
{
    @Override
    public void onApplicationLoad()
    {
        Global.getLogger(ExampleModPlugin.class).info("Example mod - Plugin loaded...");
    }

    @Override
    public void onGameLoad(boolean newGame)
    {
        Global.getLogger(ExampleModPlugin.class).info("Example mod - Game loaded...");
        Global.getSector().addScript(new ExampleScript());
    }
}
