package sdd.team1;
import sdd.team1.david.util.MyTask;
import sdd.team1.david.util.MyTaskExecutor;
public class BatchJob {
    private MyTaskExecutor te;


    private int h,m,s;

    public BatchJob(){
        te=null;


        this.h=0;
        this.m=0;
        this.s=0;
    }

    public BatchJob(int hour,int min, int second, MyTask task){
        this.h=hour;
        this.m=min;
        this.s=second;

        te = new MyTaskExecutor(task);
    }

    public void startJob(){
        while(true){
          try{
              te.startExecutionAt(this.h,this.m,this.s);
              te.stop();
//              System.out.println("stuff");
          }
          catch(Exception e){

          }
        }
    }
}
