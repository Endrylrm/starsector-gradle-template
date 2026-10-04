package org.example

import com.fs.starfarer.api.EveryFrameScript
import com.fs.starfarer.api.Global

class ExampleScript : EveryFrameScript
{
    private var done = false

    override fun advance(amount: Float)
    {
        if (done)
        {
            return
        }

        Global.getLogger(javaClass).info("My mod - Script has already advanced!")
        done = true
    }

    override fun isDone(): Boolean
    {
        return done
    }

    override fun runWhilePaused(): Boolean
    {
        return false
    }
}