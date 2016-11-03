/**
 * Created by Administrator on 2016/11/3.
 */
public class GameParameter {
    public static double consumeMin=0.2;
    public static double consumeMax=1.0;
    public static double healthMin=10.0;
    public static double healthMax=10.0;
    public static int sightMin=1;
    public static int sightMax=6;
    /**
     * 距离玄学参数，数值越大产糖率越低
     */
    public static double rangeMetaphysics=0.2;
    /**
     * 消耗玄学参数，数值单步消耗越低
     */
    public static double consumeMetaphysics=8;
    /**
     * 单位地块糖产量最大值
     */
    public static int pointMaxSugar=4;

    /**
     * 产糖周期
     */
    public static int sugarProductCycle=10;

    /**
     * 生育年龄下限
     */
    public static int childbearingAgeMin=20;

    /**
     * 生育年龄上限
     */
    public static int childbearingAgeMax=50;

    /**
     * 生育概率,处于生育年龄期间，每次生育的概率
     */
    public static double childbearingRate=0.5;
}
