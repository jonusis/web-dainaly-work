import java.util.*;
import java.util.stream.Collectors;

public class task2 {
    private static Queue<RR> ready = new LinkedList<>(); // 就绪队列;
    private static Queue<RR> run = new LinkedList<>(); // 运行队列;
    private static int index = -1; // -1表示无正在运行的进程
    private static int timePiece = 3; // 划分时间片为1
    public static void main(String args[]){
        ArrayList<RR> arr = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while(input.hasNextInt()){
            int ID = input.nextInt();
            int state = input.nextInt();
            int arriveTime = input.nextInt();
            int requestTime = input.nextInt();
            RR work = new RR(ID,state,arriveTime,requestTime);
            arr.add(work);
        }
        FIFO(arr);
        PieceTurn(arr);
    }
    public static void PieceTurn(ArrayList<RR> arr){
        List<RR> temp = arr.stream().sorted(Comparator.comparing(RR::getArriveTime)).collect(Collectors.toList());
        System.out.println("PieceTurn 时间片轮转算法");
        int now = temp.get(0).arriveTime;
        int reqAll = now;
        for(int i = 0;i < temp.size();i++){
            int reqt = temp.get(i).requestTime;
            reqAll += reqt;
        }
        while(now < reqAll){
            for(int i = 0;i < temp.size();i++) {
                int arrt = temp.get(i).arriveTime;
                int state = temp.get(i).state;
                if (arrt <= now && state == 0) {
                    ready.offer(temp.get(i));
                    temp.get(i).state = 1;
                }
            }
            if(index != -1){
                for(int i = 0;i < temp.size();i++) {
                    if (temp.get(i).ID == index && temp.get(i).requestTime > 0) {
                        ready.offer(temp.get(i));
                        break;
                    }
                }
                index = -1;
            }
            RR re = ready.poll();
            index = re.ID;
            if(re.requestTime > timePiece){
                re.print(now,timePiece);
                re.requestTime -= timePiece;
                now += timePiece;
            }else{
                re.print(now,re.requestTime);
                now += re.requestTime;
                re.requestTime = 0;
            }
        }
    }
    public static void FIFO(ArrayList<RR> arr){
        List<RR> temp = arr.stream().sorted(Comparator.comparing(RR::getArriveTime)).collect(Collectors.toList());
        System.out.println("FIF0先进先出算法");
        int now = temp.get(0).arriveTime;
        for(int i = 0;i < temp.size();i++){
            int reqt = temp.get(i).requestTime;
            temp.get(i).addTime(now,now + reqt);
            temp.get(i).printFIFO();
            now += reqt;
        }
        System.out.println();
    }
}
class RR{
    int ID;
    int state; // 当前执行的状态 0：未插入队列 1：已插入队列
    int requestTime;    // 运行所需时间
    int arriveTime; // 作业到达时间
    int startTime;  // 作业开始时间
    int endTime;    // 作业结束时间
    int zhouZhuanTime;  // 运行周转时间
    double ratio;    // 响应比
    RR(int ID,int state,int arriveTime,int requestTime){
        this.ID = ID;
        this.state = state;
        this.arriveTime = arriveTime;
        this.requestTime = requestTime;
    }
    int getArriveTime(){
        return this.arriveTime;
    }
    double ratioV(int now){
        this.ratio = (double) (now + requestTime - arriveTime) / requestTime;
        return ratio;
    }
    void addTime(int startTime,int endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.zhouZhuanTime = endTime - arriveTime;
    }
    void print(int now,int timePiece){
        int sum = now + timePiece;
        System.out.println("进程编号 " + ID +" 进程执行 " + now + "~" + sum + " 进程到达时间 " + arriveTime +" 进程剩余所需时间 " + requestTime );
    }
    void printFIFO(){
        System.out.println("作业编号 " + ID +" 作业到达时间 " + arriveTime +" 运行所需时间 " + requestTime +
                " 作业开始时间 " + startTime + " 作业结束时间 " + endTime + " 运行周转时间 " + zhouZhuanTime);
    }
}
