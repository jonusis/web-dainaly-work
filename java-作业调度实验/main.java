package os;
import java.util.*;
import java.util.stream.Collectors;

public class main {
    public static void main(String args[]){
        ArrayList<JCB> arr = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        while(input.hasNextInt()){
            int ID = input.nextInt();
            int arriveTime = input.nextInt();
            int requestTime = input.nextInt();
            JCB work = new JCB(ID,arriveTime,requestTime);
            arr.add(work);
        }
        FCFS(arr);
        SJF(arr);
        HRRN(arr);
    }
    public static void FCFS(ArrayList<JCB> arr){
        List<JCB> temp = arr.stream().sorted(Comparator.comparing(JCB::getArriveTime)).collect(Collectors.toList());
        System.out.println("FCFS先来先服务算法");
        int now = temp.get(0).arriveTime;
        for(int i = 0;i < temp.size();i++){
            int arrt = temp.get(i).requestTime;
            temp.get(i).addTime(now,now + arrt);
            now += arrt;
            temp.get(i).print();
        }
    }
    public static void SJF(ArrayList<JCB> arr){
        List<JCB> temp = arr.stream().sorted(Comparator.comparing(JCB::getArriveTime)).collect(Collectors.toList());
        System.out.println("SJF短作业优先算法");
        int now = temp.get(0).arriveTime;
        while(temp.size() != 0){
            int shortTime = temp.get(0).requestTime;
            int index = 0;
            for(int i = 0;i < temp.size();i++) {
                int arrt = temp.get(i).arriveTime;
                int reqt = temp.get(i).requestTime;
                if (now >= arrt && shortTime > reqt) {
                    shortTime = reqt;
                    index = i;
                }
            }
            temp.get(index).addTime(now,now + temp.get(index).requestTime);
            now += temp.get(index).requestTime;
            temp.get(index).print();
            temp.remove(index);
        }
    }
    public static void HRRN(ArrayList<JCB> arr){
        List<JCB> temp = arr.stream().sorted(Comparator.comparing(JCB::getArriveTime)).collect(Collectors.toList());
        System.out.println("HRRN最高响应比优先算法");
        int now = temp.get(0).arriveTime;
        while(temp.size() != 0){
            double maxRatio = temp.get(0).ratioV(now);
            int index = 0;
            for(int i = 0;i < temp.size();i++) {
                int arrt = temp.get(i).arriveTime;
                double ratio = temp.get(i).ratioV(now);
                if (now >= arrt && ratio > maxRatio) {
                    maxRatio = ratio;
                    index = i;
                }
            }
            temp.get(index).addTime(now,now + temp.get(index).requestTime);
            now += temp.get(index).requestTime;
            temp.get(index).print();
            temp.remove(index);
        }
    }
}
class JCB{
    int ID;
    int requestTime;    // 运行所需时间
    int arriveTime; // 作业到达时间
    int startTime;  // 作业开始时间
    int endTime;    // 作业结束时间
    int zhouZhuanTime;  // 运行周转时间
    double ratio;    // 响应比
    JCB(int ID,int arriveTime,int requestTime){
        this.ID = ID;
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
    void print(){
        System.out.println("作业编号 " + ID +" 作业到达时间 " + arriveTime +" 运行所需时间 " + requestTime +
                " 作业开始时间 " + startTime + " 作业结束时间 " + endTime + " 运行周转时间 " + zhouZhuanTime);
    }
}


