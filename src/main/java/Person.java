/**
 * Created by Administrator on 2016/11/3.
 */
public class Person {
    /**
     * 祖宗
     */
    private Person ancestor;
    /**
     * 姓
     */
    private String familyName;
    /**
     * 代
     */
    private int degree;
    /**
     * 全名
     */
    private String name;
    /**
     * 地图长
     */
    private int xLength;
    /**
     * 地图宽
     */
    private int yLength;
    /**
     * 初始x
     */
    private int initX;
    /**
     * 初始y
     */
    private int initY;
    /**
     * 初始体力
     */
    private double initHealth;
    /**
     * 体力消耗
     */
    private double consume;
    /**
     * 视野
     */
    private int sightLength;
    /**
     * 当前x
     */
    private int currentX;
    /**
     * 当前y
     */
    private int currentY;

    /**
     * 当前体力
     */
    private double currentHealth;

    /**
     * 存活状态
     */
    private boolean live;

    /**
     * 糖棋盘
     */
    private int[][] sugerSpace;

    /**
     * 人棋盘
     */
    private boolean[][] peopleSpace;
}
