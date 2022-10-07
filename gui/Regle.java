package gui;
import javax.swing.*;
import java.awt.*;

public class Regle extends JPanel {
    private JLabel regle = new JLabel();
    private MyButton retour = new MyButton("image/retour.png");

    public Regle() {
        setLayout(null);
        String p1 = "Les pierres sont placées sur les intersections des lignes uniquement. C’est toujours le joueur possédant les pierres noires qui place la première pierre et démarre donc la partie. C’est ensuite au joueur possédant les pierres blanches de jouer et alterne tour à tour. Une fois qu’un pierre est placée par le joueur, elle ne peut pas être retirée par celui-ci, ni être déplacée.";
        String p2 = "Les libertés d’une pierre représentent les intersections vides adjacentes à cette dernière. Le pierre peut donc avoir entre zéro et quatre libertés. Une pierre qui n’a plus qu’une seule liberté est dite en «atari». Si la dernière libéré de la pierre est prise, cette dernière est retirée du jeu. De même un groupe de pierres ne possédant plus de liberté supprimé du plateau. Le jeu ne peut pas placer de pierre sur une intersection où il nepossédera pas de liberté.(Pour une capture de territoire: le territoire es soit vide, soit remplis des pierres adverses)";
        String p3 = "Les pierres de handicaps se placent sur les «hoshi», ces pierres se place avant le début d’une partie pour handicapé les pions blanc car c’est toujours noir qui pourra les poser. Généralement on  utilise se système quand le niveau des joueurs est différent pour faciliter le jeu du joueur débutant.";
        String p4 = "Le comptage de point on va le déterminer sans compter les pierres de chaque couleur sur le goban pour ça onpourra:Durant la partie, conserver les pierres que l'on a capturées, et donner à chaque fois que l'on passe une pierre àl'adversaire comme si elle avait été capturée,à la fin de la partie, si Noir a joué le dernier, imposer à Blanc de lui donner une pierre de plus,juste avant le décompte des points, placer les pierres adverses que l'on détient dans les territoires de l'autre.Ainsi, à la fin, dans une partie à égalité, chacun aura utilisé le même nombre de pierres, qui seront toutes sur legoban: il sera donc inutile de les compter.Dans une partie à n handicaps, le total des pierres noires sur legobansera égal au total des pierres blanches plus les n-1 points supplémentaires. Là encore, il sera donc inutile de compter les pierres.Dans les deux cas, le vainqueur sera celui qui possédera le plus d'intersections inoccupées, sans oublier, dans les parties à égalité, d'ajouter le«komi»au total de Blanc, et dans les parties à handicap, d'ajouter un demi-point au total du même Blanc.";
        String p5 = "La contrainte principale sera d’adapté les règles d’un jeu pour 2 joueurs, pour qu’il passe à un jeu pour 3 joueurs. De plus, l’ordinateur sera une contrainte car il lui faut un certain niveau de difficulté pour que le joueur ne trouve pas la partie trop simple. Le tutoriel devra être assez clair pour un nouveau joueur et donc il faut une certaine maîtrise de jeu.";
        String size = "5";
        String texte = "<html>" +
                "<h1 style='color:blue'> <font face='arial' size='7'>Regles du jeu de GO. </font></h1> <h2 style = 'color:red'> <font face='arial' size='6'> Placement des pierres </font> </h2>  <p align='justify' ><font  face='arial' size=" + size + ">" + p1 + "</font> </p> " +
                "<h2 style = 'color:red'> <font face='arial' size='6'> Liberté et prise de territoire  </font> </h2> <p align='justify'> <font  face='arial' size=" + size + ">" + p2 + " </font> </p>" +
                "<h2 style = 'color:red'> <font face='arial' size='6'>Modes de jeu</font> </h2> <p align='justify' ><font  face='arial' size=" + size + ">" + p3 + "</font> </p>" +
                "<h2 style = 'color:red'> <font face='arial' size='6'>Compte des points</font> </h2> <p align='justify'><font  face='arial' size=" + size + ">" + p4 + "</font> </p>" +
                "<h2 style = 'color:red'> <font face='arial' size='6'>Contraintes</font> </h2> <p align='justify'><font  face='arial' size=" + size + ">" + p5 + "</font> </p>" +
                "</html>";
        regle.setText(texte);
        regle.setBounds(50, 0, 1800, 700);
        add(regle);
        setBackground(Color.gray);
        retour.setBounds(1400, 800, 500, 100);
        add(retour);

    }

    MyButton getRetour() {
        return retour;
    }
}
