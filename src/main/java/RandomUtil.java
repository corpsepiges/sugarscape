import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 随机化工具类
 * Created by Administrator on 2016/11/3.
 */
public class RandomUtil {
    private static double deathFactor=0.05;
     static Map<Integer,Double> deathRate;
    private static Random r;
    private static RandomUtil instance=null;
    public static RandomUtil getInstance(){
        if(instance==null){
            synchronized(RandomUtil.class){
                if(instance==null){
                    instance=new RandomUtil();
                    r=new Random();
                    deathRate=new HashMap<Integer, Double>();
                    for (int i = 1; i <= 100; i++) {
                        double rate=Math.pow(1.0-1.0*i/100,deathFactor);
                        deathRate.put(i,rate);
                    }
                }
            }
        }
        return instance;
    }
    private RandomUtil(){}

    /**
     * 制作指定范围和锚点随机double
     * @param min
     * @param max
     * @param self
     * @return
     */
    public double makaRandomDouble(double min,double max,double self){
        double newAvg=(self+(max+min)/2)/2;
        double left=r.nextDouble();
        double right=r.nextDouble();
        return left*(newAvg-min)+right*(max-newAvg)+min;
    }

    /**
     * 制作指定范围随机double
     * @param min
     * @param max
     * @return
     */
    public double makaRandomDouble(double min,double max){
        return makaRandomDouble(min,max,(min+max)/2);
    }

    /**
     * 制作指定范围和锚点随机int
     * @param min
     * @param max
     * @param self
     * @return
     */
    public int makeRandomInt(int min,int max,int self){
        int newAvg=(2*self+max+min)/4;
        int left=r.nextInt(newAvg-min+1);
        int right=r.nextInt(max-newAvg+1);
        return left+right+min;
    }

    /**
     * 制作制定范围的int随机数
     * @param min
     * @param max
     * @return
     */
    public int makeRandomInt(int min,int max){
        return min+r.nextInt(max-min+1);
    }

    /**
     * 判断是否自然死亡
     * @param age
     * @return
     */
    public boolean naturalDeath(int age){
        double value=r.nextDouble();
        return value>=deathRate.get(age);
    }

    /**
     * 制作0-i的随机int
     * @param i
     * @return
     */
    public int nextInt(int i){
        return r.nextInt(i);
    }

    /**
     * 制作0-1的随机double
     * @return
     */
    public double nextDouble(){
        return r.nextDouble();
    }
}
