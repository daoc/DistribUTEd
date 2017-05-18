/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed;

import java.util.concurrent.Callable;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public abstract class Distributable implements Runnable, Callable<Distributable> {
    private final Task task;
    
    public Distributable() {
        this.task = null;
    }    
    
    public Distributable(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }
    
    public abstract void configureForCall();
    
}
