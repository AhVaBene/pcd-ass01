package pcd.ass01.simtraffic.concurrent.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.CyclicBarrier;

public class StartAndStopListener implements ActionListener {

    private CyclicBarrier barrier;
    private StartAndStopCounter counter;

    private  Counter counterLoop;

    private Thread startAndStopThread;

    private JTextField text;

    private boolean startFlag = false;
    public StartAndStopListener(CyclicBarrier barrier, Counter counterLoop, JTextField text){
        this.barrier = barrier;
        this.text = text;
        this.counterLoop = counterLoop;
        this.counter = new StartAndStopCounter();
        this.counter.stop();
        this.startAndStopThread = new Thread(()->{
            while(true) {
                try {
                    if(!counter.getIsStopped()){
                        barrier.await();
                        //Thread.sleep(1000);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.startAndStopThread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Objects.equals(e.getActionCommand(), "STOP")){
            counter.stop();
        } else if (Objects.equals(e.getActionCommand(), "START")) {
            if(!startFlag){
                var iters = 100;
                try {
                    iters = Integer.parseInt(text.getText());
                }catch (Exception ignored){}
                counterLoop.reset(iters);
                startFlag = true;
            }
            counter.start();
        }
    }
}
