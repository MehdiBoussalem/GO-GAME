import gui.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
public class Main {
    private static final Logger LOGGER =  LogManager.getLogger( Main.class );
    public static void main(String []args ){
      LOGGER.info("Progamme lancé");
         new Fenetre();

    }
}
