import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlanningService {
   private static final Logger log = Logger.getLogger(PlanningService.class.getName());
   static {
       try {
           SimpleFormatter simpleFormatter = new SimpleFormatter();
           Handler fileHandler = new FileHandler("output_logs/logs",true);
           fileHandler.setFormatter(simpleFormatter);
           log.addHandler(fileHandler);
       } catch (IOException e) {
           log.severe(e.getMessage());
       }

   }

    FileReader fileReader = new FileReader("input_files/info.txt");
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    List<Events> evetList = new ArrayList<>();

    public PlanningService() throws FileNotFoundException {
    }


    public List<Events> events() {

        List<String> infos = new ArrayList<>();

        try {
            String line = bufferedReader.readLine();

            while (line!=null){
               infos.add(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {
            log.severe(e.getMessage());
        }

        Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})");

        for (String info : infos) {
            Matcher matcher = pattern.matcher(info);
            long seconds = 0;
            if(matcher.find()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                TemporalAccessor accessor = formatter.parse(matcher.group());
                LocalDateTime from = LocalDateTime.from(accessor);
                Duration between = Duration.between(LocalDateTime.now(), from);
                seconds = between.toSeconds();
            }
            Pattern event = Pattern.compile("(Event: .+)");
            Matcher matcherEvent = event.matcher(info);
            String name = null;
            if(matcherEvent.find()){
                name = matcherEvent.group();
            }

            evetList.add(new Events(seconds,name));

        }
     return evetList;
    }
}
