import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.*;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    static {
        try {

            SimpleFormatter simpleFormatter = new SimpleFormatter();
            FileHandler fileHandler = new FileHandler("output_logs/events.txt",true);
            fileHandler.setFormatter(simpleFormatter);
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }



    public static void main(String[] args) throws FileNotFoundException {
        PlanningService planningService = new PlanningService();

        List<Events> events = planningService.events();

        System.out.println(events);

        for (Events event : events) {



            ScheduledExecutorService pool = Executors.newScheduledThreadPool(4);
            pool.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    logger.log(Level.INFO,event.getMessage());

                }
            },event.getTime(),10,TimeUnit.SECONDS );
        }
    }
}