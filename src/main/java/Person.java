import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Person {
    /**
     * 人物无关乎运行的信息
     */
    private PersonBaseInfo baseInfo;
    /**
     * 地图长
     */
    private int xLength;
    /**
     * 地图宽
     */
    private int yLength;

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
    private int[][] sugarSpace;

    /**
     * 人棋盘
     */
    private boolean[][] peopleSpace;

    /**
     * 年龄
     */
    private int age;

    /**
     * 死亡方式
     * 1：饿死
     * 2：老死
     */
    private int deathType;
    /**
     * 儿子列表
     */
    private List<Person> sonList;
    
    private RandomUtil randomUtil;

    /**
     * 糖人下一步行动
     * 目前策略是先寻找视野内最大的糖块，如果有相同大的则取近的
     * 然后朝目标移动的过程中，逐步选取次大的点
     */
    public void nextDo(){
        if (live) {
            int max=0;
            int count=0;
            int targetX=currentX+(randomUtil.nextInt(2)==0?1:-1);
            int targetY=currentY+(randomUtil.nextInt(2)==0?1:-1);
            int targetLength=Integer.MAX_VALUE;
            /**
             * 寻找视野内最大的目标
             */
            for (int i = Math.max(0, currentX-sightLength); i <= Math.min(xLength-1, currentX+sightLength); i++) {
                for (int j = Math.max(0, currentY-sightLength); j <= Math.min(xLength-1, currentY+sightLength); j++) {
                    if (sugarSpace[i][j]>max) {
                        max=sugarSpace[i][j];
                        targetX=i;
                        targetY=j;
                        count=1;
                        targetLength=Math.abs(i-currentX)+Math.abs(j-currentY);
                    }else if (sugarSpace[i][j]==max) {
                        int length=Math.abs(i-currentX)+Math.abs(j-currentY);
                        if (length<targetLength) {
                            targetX=i;
                            targetY=j;
                            count=1;
                            targetLength=Math.abs(i-currentX)+Math.abs(j-currentY);
                        }else if(length==targetLength){
                            count+=1;
                            if (randomUtil.nextInt(count)==0) {
                                targetX=i;
                                targetY=j;
                            }
                        }
                    }
                }
            }
            /**
             * 朝着目标前进
             */
            toTarget(targetX,targetY);
        }
    }
    public void toTarget(int targetX,int targetY){
        if (targetX==currentX) {
            if ((targetY>currentY&&!peopleSpace[currentX][currentY+1])||(targetY<currentY&&!peopleSpace[currentX][currentY-1])) {
                move(currentX,currentY+(targetY>currentY?1:-1));
            }else{
                moveRandom();
            }
        }else if (targetY==currentY) {
            if ((targetX>currentX&&!peopleSpace[currentX+1][currentY])||(targetX<currentX&&!peopleSpace[currentX-1][currentY])) {
                move(currentX+(targetX>currentX?1:-1),currentY);
            }else{
                moveRandom();
            }
        }else{
            int xf=targetX>currentX?1:-1;
            int yf=targetY>currentY?1:-1;
            int max=0;
            int count=0;
            int t=randomUtil.nextInt(2);
            int newTargetX=currentX+(t==0?xf:0);
            int newTargetY=currentY+(t==0?0:yf);
            int targetLength=Integer.MAX_VALUE;
            for (int i = currentX; i <= targetX; i+=xf) {
                for (int j = currentY; j <= targetY; j+=yf) {
                    if (i==currentX&&j==currentY) {
                        continue;
                    }
                    if (i==targetX&&j==targetY) {
                        break;
                    }
                    if (sugarSpace[i][j]>max) {
                        max=sugarSpace[i][j];
                        newTargetX=i;
                        newTargetY=j;
                        count=1;
                        targetLength=Math.abs(i-currentX)+Math.abs(j-currentY);
                    }else if (sugarSpace[i][j]==max) {
                        int length=Math.abs(i-currentX)+Math.abs(j-currentY);
                        if (length<targetLength) {
                            newTargetX=i;
                            newTargetY=j;
                            count=1;
                            targetLength=Math.abs(i-currentX)+Math.abs(j-currentY);
                        }else if(length==targetLength){
                            count+=1;
                            if (randomUtil.nextInt(count)==0) {
                                newTargetX=i;
                                newTargetY=j;
                            }
                        }
                    }
                }
            }
            toTarget(newTargetX,newTargetY);
        }
    }
    public void move(int x,int y){
        age++;
        //修改棋盘
        peopleSpace[currentX][currentY]=false;
        peopleSpace[x][y]=true;
        //修改人物血量
        currentHealth+=sugarSpace[x][y];
        currentHealth-=consume;
        //修改糖棋盘
        sugarSpace[x][y]=0;
        //修改人物坐标
        currentX=x;
        currentY=y;
        //判断是否死亡
        if (currentHealth<0) {
            live=false;
            deathType=1;
            peopleSpace[x][y]=false;
        }else if (randomUtil.naturalDeath(age)){
            live=false;
            deathType=2;
            peopleSpace[x][y]=false;
            //分遗产
            if (sonList.size()>0){
                double avgHealth=0.9*currentHealth/(sonList.size()+1);
                for (Person son:sonList) {
                    son.setCurrentHealth(son.getCurrentHealth()+avgHealth);
                }
            }
        }
    }

    /**
     * 随机移动
     */
    public void moveRandom(){
        List<Integer> list=new ArrayList<Integer>();
        for (int i = 0; i < 4; i++) {
            int xf=i/2;
            int yf=i%2;
            int nx=currentX+1-xf*2;
            int ny=currentY+1-yf*2;
            if (nx>=0&&nx<xLength&&ny>=0&&ny<yLength&&!peopleSpace[nx][ny]) {
                list.add(i);
            }
        }
        if (list.size()==0) {
            move(currentX, currentY);
        }else{
            int t=randomUtil.nextInt(list.size());
            int xf=list.get(t)/2;
            int yf=list.get(t)%2;
            int nx=currentX+1-xf*2;
            int ny=currentY+1-yf*2;
            move(nx, ny);
        }
    }

    /**
     * 生小孩，需要同时符合以下几点：
     * 1.年龄在条件之内
     * 2.不在地图边缘
     * 3.上下左右四格没有人
     * 4.体力大于20
     * 5.符合概率
     *
     * @return
     */
    public Person makeChild(Person ancestor){
        if (age>=GameParameter.childbearingAgeMin&&age<=GameParameter.childbearingAgeMax){
            if(currentX>0&&currentX<xLength-1&&currentY>0&&currentY<yLength-1){
                if(!peopleSpace[currentX-1][currentY]&&!peopleSpace[currentX+1][currentY]&&!peopleSpace[currentX][currentY-1]&&!peopleSpace[currentX][currentY+1]){
                    if(currentHealth>50){
                        if (randomUtil.nextDouble()<GameParameter.childbearingRate){
//                            System.out.println("生了！！！！");
                            int sonDegree=baseInfo.getDegree()+1;
                            double sonConsume=randomUtil.makaRandomDouble(GameParameter.consumeMin,GameParameter.consumeMax,consume);
                            int sonSightLength=randomUtil.makeRandomInt(GameParameter.sightMin,GameParameter.sightMax,sightLength);
                            int direction=randomUtil.nextInt(4);
                            int sonX=currentX-1+2*(direction/2);
                            int sonY=currentX-1+2*(direction%2);
                            double sonHealth=currentHealth/2;
                            currentHealth/=2;
                            Person son=new Person(ancestor,sonDegree,sonConsume,sonSightLength,sonX,sonY,sonHealth/2);
                            return son;
                        }else{
//                            System.out.println("可惜！！！！");
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * 制作祖先
     */
    public Person(String familyName,double consume, int sightLength, int x, int y, double health,int[][] sugarSpace, boolean[][] peopleSpace) {
        this.baseInfo = new PersonBaseInfo(null,familyName,0,x,y,health) ;
        this.xLength = sugarSpace.length;
        this.yLength = sugarSpace[0].length;
        this.consume = consume;
        this.sightLength = sightLength;
        this.currentX = x;
        this.currentY = y;
        this.currentHealth = health;
        this.live = true;
        this.sugarSpace = sugarSpace;
        this.peopleSpace = peopleSpace;
        this.age=0;
        this.deathType=0;
        this.peopleSpace[x][y]=true;
        this.sonList=new ArrayList<Person>();
        this.randomUtil=RandomUtil.getInstance();
    }

    /**
     * 制作非祖先
     */
    public Person(Person ancestor,int sonDegree,double sonConsume, int sonSightLength, int x, int y, double health) {
        PersonBaseInfo ancestorBaseInfo=ancestor.getBaseInfo();
        this.baseInfo = new PersonBaseInfo(ancestor,ancestorBaseInfo.getFamilyName(),sonDegree,x,y,health) ;
        this.xLength = ancestor.getxLength();
        this.yLength = ancestor.getyLength();
        this.consume = sonConsume;
        this.sightLength = sonSightLength;
        this.currentX = x;
        this.currentY = y;
        this.currentHealth = health;
        this.live = true;
        this.sugarSpace = ancestor.getSugarSpace();
        this.peopleSpace = ancestor.getPeopleSpace();
        this.age=0;
        this.deathType=0;
        this.peopleSpace[x][y]=true;
        this.sonList=new ArrayList<Person>();
        this.randomUtil=RandomUtil.getInstance();
    }
    public PersonBaseInfo getBaseInfo() {
        return baseInfo;
    }

    public boolean isLive() {
        return live;
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

    public double getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(double currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getDeathType() {
        return deathType;
    }
}
