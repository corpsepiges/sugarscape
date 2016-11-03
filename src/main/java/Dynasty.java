import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Dynasty {
    /**
     * 距离玄学参数
     */
    private double rangeMetaphysics;
    private int xLength;
    private int yLength;
    /**
     * 糖棋盘
     */
    private int[][] sugarSpace;
    /**
     * 人棋盘
     * true:表示该格有人
     * false:表示该格无人
     */
    private boolean[][] peopleSpace;

    /**
     * 糖中心：
     * 越靠近中心地带产糖量和比例越高
     */
    private List<int[]> sugerCenterList;

    private double[][] pointValue;

    private RandomUtil randomUtil;
    /**
     * 产生糖
     */
    public void makeSuger(){
        int initSuger=sumSuger();
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                if (!peopleSpace[i][j]) {
                    sugarSpace[i][j]=pointMakeSugar(i,j);
                }
            }
        }
    }
    public int[] makeMoreSuger(){
        int moreSuger=0;
        int moreSugerPlace=0;
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                if (!peopleSpace[i][j]) {
                    int test=pointMakeSugar(i,j);
                    moreSuger+=test;
                    if (sugarSpace[i][j]==0&&test!=0) {
                        moreSugerPlace+=1;
                    }
                    sugarSpace[i][j]+=test;
                }
            }
        }
        int[] ans={moreSuger,moreSugerPlace};
        return ans;
    }
    private int pointMakeSugar(int x,int y){
        double sum=pointValue[x][y];
        int ans=0;
        for (int i = 0; i < GameParameter.pointMaxSugar; i++) {
            double test=randomUtil.nextDouble();
            if (test<sum) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 糖盘每点的权重
     * @param x
     * @param y
     * @return
     */
    public double onePointValue(int x,int y){
        double sum=0.0;
        for (int i = 0; i < sugerCenterList.size(); i++) {
            int scX=sugerCenterList.get(i)[0];
            int scY=sugerCenterList.get(i)[1];
            double range=Math.pow(x-scX, 2)+Math.pow(y-scY, 2);
            double test=Math.pow(range+1.1, rangeMetaphysics);
            sum+=1/test;
        }
        return sum;
    }
    /**
     * 清空棋盘上的糖
     */
    public void cleanSuger(){
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                sugarSpace[i][j]=0;
            }
        }
    }
    /**
     * 统计棋盘上的糖
     * @return
     */
    public int sumSuger(){
        int sum=0;
        int have=0;
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                sum+=sugarSpace[i][j];
                if (sugarSpace[i][j]>0) {
                    have++;
                }
            }
        }
        return sum;
    }



    public Dynasty(int x,int y,List<int[]> sugerCenterList) {
        this.sugarSpace=new int[x][y];
        this.peopleSpace=new boolean[x][y];
        this.xLength=x;
        this.yLength=y;
        this.sugerCenterList=sugerCenterList;
        this.rangeMetaphysics=GameParameter.rangeMetaphysics;
        this.pointValue=new double[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                pointValue[i][j]=onePointValue(i, j);
            }
        }
        this.randomUtil=RandomUtil.getInstance();
    }

    public double getRangeMetaphysics() {
        return rangeMetaphysics;
    }

    public int getxLength() {
        return xLength;
    }

    public int getyLength() {
        return yLength;
    }

    public int[][] getSugarSpace() {
        return sugarSpace;
    }

    public boolean[][] getPeopleSpace() {
        return peopleSpace;
    }

    public List<int[]> getSugerCenterList() {
        return sugerCenterList;
    }

    public double[][] getPointValue() {
        return pointValue;
    }

    public RandomUtil getRandomUtil() {
        return randomUtil;
    }
}
