/**
 * Created by Administrator on 2016/11/3.
 */
public class PersonBaseInfo {
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

    public PersonBaseInfo(Person ancestor, String familyName, int degree, int initX, int initY, double initHealth) {
        this.ancestor = ancestor;
        this.familyName = familyName;
        this.degree = degree;
        this.name = familyName+degree;
        this.initX = initX;
        this.initY = initY;
        this.initHealth = initHealth;
    }

    public Person getAncestor() {
        return ancestor;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getDegree() {
        return degree;
    }

    public String getName() {
        return name;
    }

    public int getInitX() {
        return initX;
    }

    public int getInitY() {
        return initY;
    }

    public double getInitHealth() {
        return initHealth;
    }
}
