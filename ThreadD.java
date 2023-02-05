public class ThreadD extends Thread {
    private int time1 = 1;
    private static boolean setting1 = false;

    public synchronized int getTime1() {
        while (setting1) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        return time1;
    }

    public synchronized void setTime1(int t) {
        this.time1 = t;
        setting1 = false;
        notifyAll();
    }

    public void run() {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    System.out.print(time1 + ", ");
                    setTime1(time1 + 1);
            }
        }
    }
class ThreadA extends Thread{
    ThreadD timer1;
    public ThreadA(ThreadD t){
        this.timer1 = t;
    }

    public void run(){
        synchronized(timer1){
            while(true){
                try {timer1.wait();} catch (InterruptedException e) {e.printStackTrace();}
                if((timer1.getTime1() % 3 == 0) && (timer1.getTime1() % 5 != 0)){
                    System.out.print("fizz, ");
                    timer1.setTime1(timer1.getTime1()+1);
                }
            }
        }
    }
}
class ThreadB extends Thread{
    ThreadD timer1;
    public ThreadB(ThreadD t){
        this.timer1 = t;
    }

    public void run(){
        synchronized(timer1){
            while(true){
                try {timer1.wait();} catch (InterruptedException e) {e.printStackTrace();}
                if(timer1.getTime1() % 5 == 0){
                    System.out.print("buzz, ");
                    timer1.setTime1(timer1.getTime1()+1);
                }
            }
        }
    }
}
class ThreadC extends Thread{
    ThreadD timer1;
    public ThreadC(ThreadD t){
        this.timer1 = t;
    }

    public void run(){
        synchronized(timer1){
            while(true){
                try {timer1.wait();} catch (InterruptedException e) {e.printStackTrace();}
                if((timer1.getTime1() % 3 == 0) && (timer1.getTime1() % 5 == 0)){
                    System.out.print("fizzbuzz, ");
                    timer1.setTime1(timer1.getTime1()+1);
                }
            }
        }
    }
}
class BuzzerTester {
    public static synchronized void main(String[] args) {
        ThreadD timer1 = new ThreadD();
        timer1.start();

        ThreadA t1 = new ThreadA(timer1);
        t1.start();

        ThreadB t2 = new ThreadB(timer1);
        t2.start();

        ThreadC t3 = new ThreadC(timer1);
        t3.start();


    }
}
