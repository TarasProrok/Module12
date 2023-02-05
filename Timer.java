 public class Timer extends Thread {
        private int time = 0;
        private static boolean setting = false;

        public synchronized int getTime() {
            while (setting) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            return time;
        }

        public synchronized void setTime(int t) {
            this.time = t;
            setting = false;
            notifyAll();
        }

        public void run() {
            while (true) {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(time + " ");
                setTime(time + 1);
            }
        }
    }
    class MessageTimer5 extends Thread{
        Timer timer;
        public MessageTimer5(Timer t){
            this.timer = t;
        }

        public void run(){
            synchronized(timer){
                while(true){
                    try {timer.wait();} catch (InterruptedException e) {e.printStackTrace();}
                    if(timer.getTime() % 5 == 0){
                        System.out.print("\nМинуло 5 секунд\n");
                    }
                }
            }
        }
    }

    class TimerTester {
        public static synchronized void main(String[] args) {
            Timer timer = new Timer();
            timer.start();

            MessageTimer5 t1 = new MessageTimer5(timer);
            t1.start();
        }
    }
