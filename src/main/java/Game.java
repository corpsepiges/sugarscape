import java.util.*;

/**
 * Created by Administrator on 2016/11/3.
 */
public class Game {
    RandomUtil randomUtil;
    public static void main(String[] args) {

        int x=50;
        int y=50;
        int sugerCenterNum=2;
        int peopleNum=200;
        int round=10000;
        new Game().startGame(x, y, sugerCenterNum, peopleNum,round);
    }
    private void startGame(int x, int y, int sugerCenterNum, int peopleNum, int round){
        randomUtil=RandomUtil.getInstance();
        List<int[]> sugerCenterList=makeSugerCenter(x,y,sugerCenterNum);
        Dynasty dynasty=new Dynasty(x, y, sugerCenterList);
        int[][] sugarSpace=dynasty.getSugarSpace();
        boolean[][] peopleSpace=dynasty.getPeopleSpace();
        List<Person> personList=new ArrayList<Person>();
        //初始化随机坐标值
        int[] nums=new int[x*y];
        for (int i = 0; i < nums.length; i++) {
            nums[i]=i;
        }
        //初始化糖人
        for (int i = 0; i < peopleNum; i++) {
            double consume=randomUtil.makaRandomDouble(GameParameter.consumeMin,GameParameter.consumeMax)/GameParameter.consumeMetaphysics;
            double initHealth=randomUtil.makaRandomDouble(GameParameter.healthMin,GameParameter.healthMax);
            int test=randomUtil.nextInt(x*y-i);
            int initX=nums[test]/y;
            int initY=nums[test]%y;
            int sightLength=randomUtil.makeRandomInt(GameParameter.sightMin,GameParameter.sightMax);
            Person p=new Person(""+(i+1)+"a",consume,sightLength,initX,initY,initHealth,sugarSpace,peopleSpace);
            nums[test]=nums[x*y-i-1];
            personList.add(p);
        }
        dynasty.makeSuger();
        System.out.println();
        System.out.println("init!!!!");
        Set<Person> allPerson=new HashSet<Person>();
        List<Person> newPersonList=new ArrayList<Person>();
        for (int i = 0; i < round; i++) {
            Collections.shuffle(personList);
            for (Person p : personList) {
                allPerson.add(p);
                if (p.isLive()) {
                    p.nextDo();
                    newPersonList.add(p);
                    Person ancestor = p.getBaseInfo().getAncestor();
                    if (ancestor == null) {
                        ancestor = p;
                    }
                    Person newPerson = p.makeChild(ancestor);
                    if (newPerson != null) {
                        newPersonList.add(newPerson);
                    }
                }
            }
            if ((i+1)%GameParameter.sugarProductCycle==0) {
                dynasty.makeMoreSuger();
            }
            if ((i+1)%10==0){
                System.out.println(newPersonList.size());
            }
            personList.clear();
            personList.addAll(newPersonList);
            newPersonList.clear();
            if ((i+1)%10==0){
                System.out.println(personList.size()+"\t"+count(personList).size());
            }
        }
        System.out.println(count(personList));
        countDeathType(allPerson);
    }

    /**
     * 制作糖心
     * @param x
     * @param y
     * @param sugarCenterNum
     * @return
     */
    public List<int[]> makeSugerCenter(int x,int y,int sugarCenterNum){
        List<int[]> list=new ArrayList<int[]>();
        for (int i = 0; i < sugarCenterNum; i++) {
            int[] sugerCenter=new int[2];
            sugerCenter[0]=randomUtil.nextInt(x);
            sugerCenter[1]=randomUtil.nextInt(y);
            System.out.print("("+sugerCenter[0]+","+sugerCenter[1]+")\t");
            list.add(sugerCenter);
        }
        System.out.println();
        return list;
    }

    private Map<String,Integer> count(List<Person> personList){
        Map<String,Integer> map=new HashMap<String, Integer>();
        for (Person p:personList) {
            Person ancestor=p.getBaseInfo().getAncestor();
            if (ancestor==null){
                ancestor=p;
            }
            String familyName=ancestor.getBaseInfo().getFamilyName();
            if (map.get(familyName)==null){
                map.put(familyName,1);
            }else{
                map.put(familyName,map.get(familyName)+1);
            }
        }
        return map;
    }
    private int[] countDeathType(Set<Person> set){
        int[] nums=new int[3];
        for (Person p:set) {
            nums[p.getDeathType()]++;
        }
        System.out.println(nums[0]+"\t"+nums[1]+"\t"+nums[2]);
        return nums;
    }
}
