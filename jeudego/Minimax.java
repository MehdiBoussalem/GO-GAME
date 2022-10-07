package jeudego;

import java.util.Random;
import static java.lang.Math.*;

public class Minimax {
    private int move;
    private int move2;
    private boolean passer;
    private int nbmegaPion;

    public int getMove() {
        return move;
    }
    public int getMove2() {
        return move2;
    }
    public boolean getPasser(){
        return passer;
    }
    public void setPasser(boolean passer) {
        this.passer = passer;
    }
    public int getNbmegaPion() {return nbmegaPion;}
    public void setNbmegaPion(int nbmegaPion) {this.nbmegaPion = nbmegaPion;}

    /**
     * Constructeur de la classe.
     */
    public Minimax(){
        this.move=0;
        this.move2=0;
        this.passer=false;
        this.nbmegaPion=1;
    }

    /**
     * Methode permettant Ã  l'ordinateur de placer un pion ou de passer son tour.
     * @param pl plateau
     */
    public void bestMove(Plateau pl, int numJoueur){
        float bestscore=Float.NEGATIVE_INFINITY;  //Integer.MIN_VALUE
        for(int i=0;i<pl.getTab().length;i++) {
            for (int j = 0; j < pl.getTab().length; j++) {
                if(pl.getTab()[i][j].getPion()==null && pl.peutplacer(i,j,pl.getJoueur(numJoueur-1))){
                    //Pour 19x19=>depth=2; 13x13=>depth=3; 9x9=>depth=4.
                    float score=minimax(pl,getDepth(pl),Float.NEGATIVE_INFINITY,Float.POSITIVE_INFINITY,false,i,j,0);
                    if(score>bestscore){
                        bestscore=score;
                        move=i;
                        move2=j;
                    }
                }
            }
        }
        if (pl.getContenue() >= pl.getTab().length/2 && nbmegaPion==1) {
            pl.placerMegaPion(move, move2, pl.getJoueur(numJoueur-1));
            setNbmegaPion(0);
        } else {
            pl.placer(move, move2, pl.getJoueur(numJoueur-1));

        }
    }

    /**
     * Methode permettant de decider ou placer le pion.
     * @param pl plateau
     * @param depth int:profondeur de la recherche
     * @param alpha float
     * @param beta float
     * @param maximizePlayer boolean
     * @param x int:coordonnee
     * @param y int:coordonne
     * @param k int:valeur
     * @return minEval ou maxEval :float
     */
    public static float minimax(Plateau pl,int depth,float alpha,float beta,boolean maximizePlayer,int x,int y,int k){
        float eval;
        if(depth==0){
            return eval(x,y,pl) ;
        }
        if(maximizePlayer){
            float maxEval=Float.NEGATIVE_INFINITY;  //Integer.MAX_VALUE
            for(int i=0;i<pl.getTab().length;i++){
                for(int j=0;j<pl.getTab().length;j++){
                    eval=minimax(pl,depth-1,alpha,beta,false,x,y,k);
                    maxEval=max(maxEval,eval);
                    alpha=max(alpha,eval);
                    if(beta<=alpha){
                        break;
                    }
                }
            }
            return maxEval;
        }
        else {
            float minEval = Float.POSITIVE_INFINITY;  //Integer.POSITIVE_VALUE
            if (k == 0) {
                for (int i = 0; i < pl.getTab().length; i++) {
                    for (int j = 0; j < pl.getTab().length; j++) {
                        eval = minimax(pl, depth - 1, alpha, beta, false, x, y, k + 1);
                        minEval = min(minEval, eval);
                        beta = min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                return minEval;
            } else if (k == 1) {
                k=0;
                for (int i = 0; i < pl.getTab().length; i++) {
                    for (int j = 0; j < pl.getTab().length; j++) {
                        eval = minimax(pl, depth - 1, alpha, beta, true, x, y, k);
                        minEval = min(minEval, eval);
                        beta = min(beta, eval);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
                return minEval;
            }

        }
        return 0;
    }


    /**
     * Methode evaluant chaque case du plateau.
     * @param x abscisse
     * @param y ordonne
     * @param pl Plateau
     * @return une position
     */
    public static float eval(int x,int y,Plateau pl) {
        int dim = pl.getDimension();
        if (dim == 9) {
            float[][] eval=
                    {       {-20, -19,  -17,  -16,  -12,  -16,  -17,  -19,  -20},
                            {-19,  6,   0,    5,    10,   5,    5,    6,   -19},
                            {-17,  5,    30,   25,   30,   25,   30,   5,    -17},
                            {-16,  5,    25,   27,   31,    27,   25,   5,    -16},
                            {-12,  10,   30,   31,   35,   31,   30,   10,   -12},
                            {-16,  5,    25,   27,   31,    27,   25,   5,    -16},
                            {-17,  5,    30,   25,   30,   25,   30,   5,    -17},
                            {-19,  6,   5,    5,    10,   5,    0,    6,   -19},
                            {-20,  -19,  -17,  -16,  -12,  -16,  -17,  -19,  -20}};
            return eval[x][y];
        }
        else if (dim == 13) {
            float[][] eval=
                    {       {-20, -19,  -18,  -17,  -16,  -15,  -5,  -15,  -16,  -17,  -18,  -19,  -20},
                            {-19, -10,  -5,  0,  5,  7,  0,  7,  5,  0,  -5,  -10,  -19},
                            {-18, -5,  20,  8,  11,  13,  5,  13, 11,  8,  20,  -5,  -18},
                            {-17, 0 ,  8,  30,  20,  25,  10,  25,  20,  30,  8,  0,  -17},
                            {-16, 5 ,  11,  16,  22,  27,  15,  27,  22,  16,  11,  5,  -16},
                            {-15, 7 ,  13,  16,  24,  30,  20,  24,  30,  16,  13,  7,  -15},
                            {-5,  0 ,  5,  10,  15,  20,  30,  20,  15,  10,  5,  0,  -5},
                            {-15, 7 ,  13,  16,  24,  30,  20,  30,  24,  16,  13,  7,  -15},
                            {-16, 5 ,  11,  16,  22,  27,  15,  27,  22,  16,  11,  5,  -16},
                            {-17, 0 ,  8,  30,  20,  25,  10,  25,  20,  30,  8,  0,  -17},
                            {-18, -5,  20,  8,  11,  13,  5,  13, 11,  8,  20,  -5,  -18},
                            {-19, -10,  -5,  0,  5,  7,  0,  7,  5,  0,  -5,  -10,  -19},
                            {-20, -19,  -18,  -17,  -16,  -15,  -5,  -15,  -16,  -17,  -18,  -19,  -20}};
            return eval[x][y];
        }
        else if (dim == 19) {
            float[][] eval=
                    {       {-20, -19,  -18,  -17,  -16,  -15,  -14,  -13,  -12,  10,  -12,  -13,  -14,  -15,  -16,  -17,  -18,  -19,  -20},
                            {-19, -15,  -12,  -9,  -6,  -3,  -1,  0,  1,  12,  1,  0,  -1,  -3,  -6,  -9,  -12,  -15,  -19},
                            {-18, -14,  -11,  2,  -5,  -2,  1,  4,  7,  14,  7,  4,  1,  -2,  -5,  2,  -11,  -14,  -18},
                            {-17, -12,  -7,  30,  3,  8,  11,  16,  16,  30,  16,  16,  11,  8,  3,  30,  -7,  -12,  -17},
                            {-16, -12,  -8,  2,  0,  4,  8,  12,  16,  20,  16,  12,  8,  4,  0,  2,  -8,  -12,  -16},
                            {-15, -11,  -7,  -3,  1,  5,  9,  13,  17,  19,  17,  13,  9,  5,  1,  -3,  -7,  -11,  -15},
                            {-14, -8,  -2,  4,  10,  16,  22,  28,  32,  33,  32,  28,  22,  16,  10,  4,  -2,  -8,  -14},
                            {-13, -7,  -1,  5,  11,  17,  23,  29,  35,  36,  35,  29,  23,  17,  11,  5,  -1,  -7,  -13},
                            {-12, -6,  0,  6,  12,  18,  24,  30,  36,  38,  36,  30,  24,  18,  12,  6,  0,  -6,  -12},
                            {10, 12,  14,  30,  20,  19,  33,  36,  38,  40,  38,  36,  33,  19,  20, 30,  14,  12,  10},
                            {-12, -6,  0,  6,  12,  18,  24,  30,  36,  38,  36,  30,  24,  18,  12,  6,  0,  -6,  -12},
                            {-13, -7,  -1,  5,  11,  17,  23,  29,  35,  36,  35,  29,  23,  17,  11,  5,  -1,  -7,  -13},
                            {-14, -8,  -2,  4,  10,  16,  22,  28,  32,  33,  32,  28,  22,  16,  10,  4,  -2,  -8,  -14},
                            {-15, -11,  -7,  -3,  1,  5,  9,  13,  17,  10,  17,  13,  9,  5,  1,  -3,  -7,  -11,  -15},
                            {-16, -12,  -8,  2,  0,  4,  8,  12,  16,  20,  16,  12,  8,  4,  0,  2,  -8,  -12,  -16},
                            {-17, -12,  -7,  30,  3,  8,  11,  16,  16,  30,  16,  16,  11,  8,  3,  30,  -7,  -12,  -17},
                            {-18, -14,  -11,  2,  -5,  -2,  1,  4,  7,  14,  7,  4,  1,  -2,  -5,  2,  -11,  -14,  -18},
                            {-19, -15,  -12,  -9,  -6,  -3,  -1,  0,  1,  12,  1,  0,  -1,  -3,  -6,  -9,  -12,  -15,  -19},
                            {-20, -19,  -18,  -17,  -16,  -15,  -14,  -13,  -12,  10,  -12,  -13,  -14,  -15,  -16,  -17,  -18,  -19,  -20}};
            return eval[x][y];
        }
        else {
            Random r=new Random();
            return r.nextFloat(1+1)-1;
        }
    }

    /**
     * Methode permettant de definir la profondeur de recherche;
     * @param pl plateau
     * @return int la profondeur
     */
    public static int getDepth(Plateau pl){
        if(pl.getDimension()==9){
            return 4;
        }
        else if(pl.getDimension()==13){
            return 3;
        }
        else if(pl.getDimension()==19){
            return 2;
        }
        else if(pl.getDimension()<=5){
            return pl.getTab().length;
        }
        else if(pl.getDimension()>=6 & pl.getDimension()<9){
            return 3;
        }
        return 2;
    }
}