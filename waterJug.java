import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class waterJug {

    static int jug1,jug2,req_vol=0;
    static ArrayList<Integer> current_state=new ArrayList<>();
    static ArrayList<ArrayList<Integer>> steps=new ArrayList<>();

    static boolean check() {
        int gcd=findGCD(jug1,jug2);
        return req_vol % gcd == 0;
    }

    static int findGCD(int a, int b) {
        if(a==0)
            return b;
        return findGCD(b%a,a);
    }

    static void fillJugFully(int index,int jug_cap)
    {
        current_state.set(index,jug_cap);
    }

    static void findSolution() {
        ArrayList<ArrayList<Integer>> temp=new ArrayList<>();
        try {
            if(req_vol<=jug1) {
                target(0, jug1);
                temp = (ArrayList<ArrayList<Integer>>) steps.clone();
                current_state.set(0, 0);
                current_state.set(1, 0);
                System.out.println(steps);
                steps.clear();
            }
            target(1, jug2);
            if (steps.size() > temp.size() && temp.size() != 0)
                steps = (ArrayList<ArrayList<Integer>>) temp.clone();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    static void target(int t_index,int jug)
    {
        while(isJugEmpty(t_index))
        {
            if(current_state.contains(req_vol))
                return;
            fillJugFully(t_index,jug);
            System.out.println(steps);
            steps.add((ArrayList<Integer>) current_state.clone());
            if(current_state.contains(req_vol))
                return;
            transfer(t_index);
        }
    }

    static void transfer(int from) {
        int pour_amt;//remaining capacity
        if(from==0) {//from smaller jug to bigger jug
            if(current_state.get(0)>jug2-current_state.get(1))//if amt to be poured is > than rem capacity
                pour_amt=jug2-current_state.get(1);
            else
                pour_amt=current_state.get(0);

            if(isJugEmpty(1))
            {
                current_state.set(1,pour_amt);
                current_state.set(0,0);
                steps.add((ArrayList<Integer>) current_state.clone());
            }
            else {
                current_state.set(1,pour_amt+current_state.get(1));
                current_state.set(0,current_state.get(0)-pour_amt);
                steps.add((ArrayList<Integer>) current_state.clone());

                if(current_state.get(1)==jug2)//making jug 2 empty if jug is full
                {
                    current_state.set(1, 0);
                    steps.add((ArrayList<Integer>) current_state.clone());
                    transfer(0);
                }
            }
        }
        else {//from 1
            if(current_state.get(1)!=jug2)//i.e not full
                pour_amt=current_state.get(1);
            else {
                pour_amt = jug1-current_state.get(0);
            }
            if(pour_amt<=jug1) {
                if (current_state.get(1) == jug2) {
                    current_state.set(1, jug2 - pour_amt);
                    current_state.set(0, jug1);
                    System.out.println(steps);
                    steps.add((ArrayList<Integer>) current_state.clone());
                    if (current_state.get(0) == jug1)//making jug 2 empty if jug is full
                    {
                        current_state.set(0, 0);
                        System.out.println(steps);
                        steps.add((ArrayList<Integer>) current_state.clone());
                        transfer(1);
                    }
                } else {
                    current_state.set(0, current_state.get(1));
                    current_state.set(1, 0);
                    System.out.println(steps);
                    steps.add((ArrayList<Integer>) current_state.clone());
                }
            }
        }

    }

    static boolean isJugEmpty(int index) {
        return current_state.get(index) == 0;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        System.out.println("Enter the volume of the 2 jugs:");
        System.out.print("Jug1 :");
        jug1= sc.nextInt();
        System.out.print("Jug2 :");
        jug2= sc.nextInt();

        if(jug1>jug2)
        {
            int temp=jug2;
            jug2=jug1;
            jug1=temp;
        }

        while(req_vol==0) {
            System.out.print("Enter the volume of water you want to measure: ");
            req_vol = sc.nextInt();
            if(req_vol>Math.max(jug1,jug2)) {
                System.out.println("Enter a valid volume you idiot");
                req_vol = 0;
            }
        }

        if(waterJug.check())
        {
            current_state.add(0);
            current_state.add(0);
            findSolution();
        }else System.out.println("There is no solution to the given set of volumes");

        System.out.println("Jug1\tJug2");
        for (ArrayList<Integer> element: steps) {
            System.out.println(" "+element.get(0)+"\t\t "+element.get(1));
        }

    }

}
