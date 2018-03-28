//@noobDocSection("S01", "FuzzBizz")
public class FuzzBizz {

    public void FizzBuzz(int a) {
        if (a % 3 != 0 && a % 5 != 0) { //@noobDoc("Si la valeur d'entrée est divisible ni par 3 ni par 5 on affiche cette valeur", "R04", "FuzzBizzRules")
            System.out.println(a);
            return;
        }
        String ret = "";

        if (a % 3 == 0) { //@noobDoc("Si la valeur d'entrée est divisible par 3 on affiche Fuzz", "R01", "FuzzBizzRules")
            ret += "Fuzz";
        }


        //@noobDoc("Si la valeur d'entrée est divisible par 5, on affiche Bizz", "R02", "FuzzBizzRules")
        if (a % 5 == 0) {
            ret += "Bizz";
        }

        //@noobDoc("Si la valeur d'entrée est divisible par 3 et par 5, on affiche FuzzBizz", "R03", "FuzzBizzRules")
        System.out.println(ret);
    }

}