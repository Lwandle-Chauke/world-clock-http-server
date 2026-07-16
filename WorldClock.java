import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class WorldClock {
    public static void main(String[] args) throws IOException {
        int port = 55555;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("World Clock Server is running on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            String requestLine = in.readLine();
            if (requestLine == null) continue;

            String[] parts = requestLine.split(" ");
            String path = parts[1];

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();

            String html = generatePage(path);
            out.println(html);

            out.close();
            in.close();
            clientSocket.close();
        }
    }

    private static String generatePage(String path) {
        // Get current time in South Africa
        ZonedDateTime saTime = ZonedDateTime.now(ZoneId.of("Africa/Johannesburg"));
        String saTimeStr = saTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String saDateStr = saTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
        
        // Determine which city was clicked
        String selectedCity = path.equals("/") ? null : path.substring(1); // remove leading slash
        
        // Build HTML with pink-themed design
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n<head>\n");
        html.append("<title>World Clock");
        if (selectedCity != null) {
            html.append(" - ").append(getCityDisplayName(selectedCity));
        }
        html.append("</title>\n");
        html.append("<meta http-equiv=\"refresh\" content=\"1\">\n");
        html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        html.append("<style>\n");
        
        // Pink-themed CSS
        html.append("@import url('https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap');\n");
        html.append("* { margin: 0; padding: 0; box-sizing: border-box; }\n");
        html.append("body { \n");
        html.append("  background: linear-gradient(135deg, #fff5f7 0%, #ffe4e8 100%); \n");
        html.append("  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; \n");
        html.append("  color: #4a4a4a; \n");
        html.append("  line-height: 1.5; \n");
        html.append("  padding: 40px 20px; \n");
        html.append("  min-height: 100vh; \n");
        html.append("  display: flex; \n");
        html.append("  flex-direction: column; \n");
        html.append("  align-items: center; \n");
        html.append("}\n");
        
        html.append(".container { \n");
        html.append("  max-width: 1400px; \n");
        html.append("  width: 100%; \n");
        html.append("}\n");
        
        html.append("h1 { \n");
        html.append("  font-size: 3.5rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  letter-spacing: -0.02em; \n");
        html.append("  margin-bottom: 10px; \n");
        html.append("  background: linear-gradient(135deg, #ff758c 0%, #ff7eb3 100%); \n");
        html.append("  -webkit-background-clip: text; \n");
        html.append("  -webkit-text-fill-color: transparent; \n");
        html.append("  text-align: center; \n");
        html.append("}\n");
        
        html.append(".subtitle { \n");
        html.append("  font-size: 1.2rem; \n");
        html.append("  color: #b3808f; \n");
        html.append("  text-align: center; \n");
        html.append("  margin-bottom: 40px; \n");
        html.append("  font-weight: 400; \n");
        html.append("}\n");
        
        // Current location card
        html.append(".current-location { \n");
        html.append("  background: rgba(255, 255, 255, 0.9); \n");
        html.append("  backdrop-filter: blur(10px); \n");
        html.append("  border-radius: 30px; \n");
        html.append("  padding: 40px; \n");
        html.append("  margin-bottom: 50px; \n");
        html.append("  box-shadow: 0 20px 40px rgba(255, 120, 150, 0.2); \n");
        html.append("  border: 1px solid rgba(255, 200, 210, 0.5); \n");
        html.append("  display: flex; \n");
        html.append("  align-items: center; \n");
        html.append("  justify-content: space-between; \n");
        html.append("  flex-wrap: wrap; \n");
        html.append("  gap: 40px; \n");
        html.append("}\n");
        
        html.append(".location-info { \n");
        html.append("  flex: 1; \n");
        html.append("  min-width: 300px; \n");
        html.append("}\n");
        
        html.append(".location-name { \n");
        html.append("  font-size: 2.5rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  margin-bottom: 10px; \n");
        html.append("  color: #ff6b8b; \n");
        html.append("}\n");
        
        html.append(".location-date { \n");
        html.append("  font-size: 1.2rem; \n");
        html.append("  color: #b3808f; \n");
        html.append("  margin-bottom: 20px; \n");
        html.append("}\n");
        
        html.append(".digital-time { \n");
        html.append("  font-size: 3.5rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  font-family: 'Inter', monospace; \n");
        html.append("  letter-spacing: 3px; \n");
        html.append("  color: #ff6b8b; \n");
        html.append("  text-shadow: 2px 2px 4px rgba(255, 107, 139, 0.2); \n");
        html.append("}\n");
        
        // Clock face styling
        html.append(".clock-container { \n");
        html.append("  display: flex; \n");
        html.append("  justify-content: center; \n");
        html.append("  align-items: center; \n");
        html.append("  min-width: 200px; \n");
        html.append("}\n");
        
        html.append(".analog-clock { \n");
        html.append("  width: 180px; \n");
        html.append("  height: 180px; \n");
        html.append("  border-radius: 50%; \n");
        html.append("  background: white; \n");
        html.append("  border: 8px solid #ffb6c1; \n");
        html.append("  position: relative; \n");
        html.append("  box-shadow: 0 10px 20px rgba(255, 105, 180, 0.3); \n");
        html.append("}\n");
        
        // Clock numbers
        html.append(".clock-number { \n");
        html.append("  position: absolute; \n");
        html.append("  width: 100%; \n");
        html.append("  height: 100%; \n");
        html.append("  text-align: center; \n");
        html.append("  font-size: 14px; \n");
        html.append("  font-weight: 600; \n");
        html.append("  color: #ff6b8b; \n");
        html.append("  transform: rotate(var(--rotation)) translateY(-80px); \n");
        html.append("}\n");
        
        // Clock hands
        html.append(".hand { \n");
        html.append("  position: absolute; \n");
        html.append("  bottom: 50%; \n");
        html.append("  left: 50%; \n");
        html.append("  transform-origin: bottom center; \n");
        html.append("  border-radius: 4px; \n");
        html.append("  transition: transform 0.1s cubic-bezier(0.4, 2.5, 0.3, 1); \n");
        html.append("}\n");
        
        html.append(".hour-hand { \n");
        html.append("  width: 6px; \n");
        html.append("  height: 50px; \n");
        html.append("  margin-left: -3px; \n");
        html.append("  background: #ff6b8b; \n");
        html.append("  box-shadow: 0 2px 4px rgba(255, 107, 139, 0.3); \n");
        html.append("}\n");
        
        html.append(".minute-hand { \n");
        html.append("  width: 4px; \n");
        html.append("  height: 70px; \n");
        html.append("  margin-left: -2px; \n");
        html.append("  background: #ff8da1; \n");
        html.append("  box-shadow: 0 2px 4px rgba(255, 141, 161, 0.3); \n");
        html.append("}\n");
        
        html.append(".second-hand { \n");
        html.append("  width: 2px; \n");
        html.append("  height: 80px; \n");
        html.append("  margin-left: -1px; \n");
        html.append("  background: #ffb6c1; \n");
        html.append("  box-shadow: 0 2px 4px rgba(255, 182, 193, 0.3); \n");
        html.append("}\n");
        
        html.append(".center-dot { \n");
        html.append("  position: absolute; \n");
        html.append("  width: 12px; \n");
        html.append("  height: 12px; \n");
        html.append("  border-radius: 50%; \n");
        html.append("  background: #ff6b8b; \n");
        html.append("  top: 50%; \n");
        html.append("  left: 50%; \n");
        html.append("  transform: translate(-50%, -50%); \n");
        html.append("  z-index: 10; \n");
        html.append("  box-shadow: 0 2px 4px rgba(0,0,0,0.2); \n");
        html.append("}\n");
        
        // Selected city card (special styling)
        html.append(".selected-city-card { \n");
        html.append("  background: rgba(255, 255, 255, 0.95); \n");
        html.append("  border-radius: 30px; \n");
        html.append("  padding: 30px; \n");
        html.append("  margin-bottom: 40px; \n");
        html.append("  box-shadow: 0 20px 40px rgba(255, 105, 180, 0.3); \n");
        html.append("  border: 2px solid #ff6b8b; \n");
        html.append("  display: flex; \n");
        html.append("  align-items: center; \n");
        html.append("  justify-content: space-between; \n");
        html.append("  flex-wrap: wrap; \n");
        html.append("  gap: 30px; \n");
        html.append("}\n");
        
        html.append(".selected-city-info { \n");
        html.append("  flex: 1; \n");
        html.append("  min-width: 250px; \n");
        html.append("}\n");
        
        html.append(".selected-city-name { \n");
        html.append("  font-size: 2.2rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  margin-bottom: 10px; \n");
        html.append("  color: #ff6b8b; \n");
        html.append("}\n");
        
        html.append(".selected-city-time { \n");
        html.append("  font-size: 3rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  font-family: 'Inter', monospace; \n");
        html.append("  color: #4a4a4a; \n");
        html.append("  margin: 10px 0; \n");
        html.append("}\n");
        
        html.append(".selected-city-date { \n");
        html.append("  font-size: 1.1rem; \n");
        html.append("  color: #b3808f; \n");
        html.append("}\n");
        
        // Cities grid
        html.append(".cities-grid { \n");
        html.append("  display: grid; \n");
        html.append("  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); \n");
        html.append("  gap: 20px; \n");
        html.append("  margin: 30px 0; \n");
        html.append("}\n");
        
        html.append(".city-card { \n");
        html.append("  background: rgba(255, 255, 255, 0.8); \n");
        html.append("  backdrop-filter: blur(5px); \n");
        html.append("  border-radius: 20px; \n");
        html.append("  padding: 20px; \n");
        html.append("  box-shadow: 0 10px 20px rgba(255, 120, 150, 0.15); \n");
        html.append("  border: 1px solid rgba(255, 200, 210, 0.4); \n");
        html.append("  transition: transform 0.3s, box-shadow 0.3s; \n");
        html.append("  text-align: center; \n");
        html.append("  text-decoration: none; \n");
        html.append("  color: inherit; \n");
        html.append("  display: block; \n");
        html.append("}\n");
        
        html.append(".city-card:hover { \n");
        html.append("  transform: translateY(-4px); \n");
        html.append("  box-shadow: 0 15px 25px rgba(255, 105, 180, 0.25); \n");
        html.append("  background: rgba(255, 255, 255, 0.95); \n");
        html.append("  cursor: pointer; \n");
        html.append("}\n");
        
        html.append(".city-name { \n");
        html.append("  font-size: 1.3rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  margin-bottom: 10px; \n");
        html.append("  color: #ff6b8b; \n");
        html.append("}\n");
        
        html.append(".city-time-digital { \n");
        html.append("  font-size: 1.4rem; \n");
        html.append("  font-weight: 500; \n");
        html.append("  font-family: 'Inter', monospace; \n");
        html.append("  margin: 10px 0 5px; \n");
        html.append("  color: #4a4a4a; \n");
        html.append("}\n");
        
        html.append(".city-date { \n");
        html.append("  font-size: 0.85rem; \n");
        html.append("  color: #b3808f; \n");
        html.append("  margin-bottom: 10px; \n");
        html.append("}\n");
        
        // Small clock styling
        html.append(".small-clock { \n");
        html.append("  width: 70px; \n");
        html.append("  height: 70px; \n");
        html.append("  margin: 0 auto; \n");
        html.append("  border-radius: 50%; \n");
        html.append("  border: 3px solid #ffb6c1; \n");
        html.append("  position: relative; \n");
        html.append("  background: white; \n");
        html.append("  box-shadow: 0 5px 10px rgba(255, 105, 180, 0.2); \n");
        html.append("}\n");
        
        html.append(".small-hand { \n");
        html.append("  position: absolute; \n");
        html.append("  bottom: 50%; \n");
        html.append("  left: 50%; \n");
        html.append("  transform-origin: bottom center; \n");
        html.append("  border-radius: 2px; \n");
        html.append("  transition: transform 0.1s cubic-bezier(0.4, 2.5, 0.3, 1); \n");
        html.append("}\n");
        
        html.append(".small-hour { \n");
        html.append("  width: 3px; \n");
        html.append("  height: 18px; \n");
        html.append("  margin-left: -1.5px; \n");
        html.append("  background: #ff6b8b; \n");
        html.append("}\n");
        
        html.append(".small-minute { \n");
        html.append("  width: 2px; \n");
        html.append("  height: 25px; \n");
        html.append("  margin-left: -1px; \n");
        html.append("  background: #ff8da1; \n");
        html.append("}\n");
        
        html.append(".small-second { \n");
        html.append("  width: 1px; \n");
        html.append("  height: 28px; \n");
        html.append("  margin-left: -0.5px; \n");
        html.append("  background: #ffb6c1; \n");
        html.append("}\n");
        
        html.append(".small-center { \n");
        html.append("  position: absolute; \n");
        html.append("  width: 5px; \n");
        html.append("  height: 5px; \n");
        html.append("  border-radius: 50%; \n");
        html.append("  background: #ff6b8b; \n");
        html.append("  top: 50%; \n");
        html.append("  left: 50%; \n");
        html.append("  transform: translate(-50%, -50%); \n");
        html.append("  z-index: 10; \n");
        html.append("}\n");
        
        html.append(".section-title { \n");
        html.append("  font-size: 1.8rem; \n");
        html.append("  font-weight: 600; \n");
        html.append("  color: #ff6b8b; \n");
        html.append("  margin: 40px 0 20px; \n");
        html.append("  text-align: center; \n");
        html.append("}\n");
        
        html.append(".back-link { \n");
        html.append("  display: inline-block; \n");
        html.append("  margin-top: 30px; \n");
        html.append("  padding: 12px 30px; \n");
        html.append("  background: rgba(255, 255, 255, 0.8); \n");
        html.append("  color: #ff6b8b; \n");
        html.append("  text-decoration: none; \n");
        html.append("  border-radius: 40px; \n");
        html.append("  font-weight: 600; \n");
        html.append("  box-shadow: 0 4px 12px rgba(255, 105, 180, 0.2); \n");
        html.append("  border: 1px solid rgba(255, 182, 193, 0.5); \n");
        html.append("  transition: all 0.3s; \n");
        html.append("}\n");
        
        html.append(".back-link:hover { \n");
        html.append("  background: white; \n");
        html.append("  box-shadow: 0 8px 20px rgba(255, 105, 180, 0.3); \n");
        html.append("  transform: scale(1.05); \n");
        html.append("}\n");
        
        html.append("</style>\n");
        html.append("</head>\n<body>\n");
        html.append("<div class=\"container\">\n");
        
        // Header
        html.append("<h1>World Clock");
        if (selectedCity != null) {
            html.append(" - ").append(getCityDisplayName(selectedCity));
        }
        html.append("</h1>\n");
        
        // South Africa (current location) card with analog clock
        html.append("<div class=\"current-location\">\n");
        html.append("  <div class=\"location-info\">\n");
        html.append("    <div class=\"location-name\">South Africa</div>\n");
        html.append("    <div class=\"location-date\">").append(saDateStr).append("</div>\n");
        html.append("    <div class=\"digital-time\">").append(saTimeStr).append("</div>\n");
        html.append("  </div>\n");
        
        // Add analog clock for SA
        String[] timeParts = saTimeStr.split(":");
        int hours = Integer.parseInt(timeParts[0]);
        int minutes = Integer.parseInt(timeParts[1]);
        int seconds = Integer.parseInt(timeParts[2]);
        
        double hourAngle = (hours % 12) * 30 + minutes * 0.5 + seconds * 0.00833;
        double minuteAngle = minutes * 6 + seconds * 0.1;
        double secondAngle = seconds * 6;
        
        html.append("  <div class=\"clock-container\">\n");
        html.append("    <div class=\"analog-clock\">\n");
        
        // Add clock numbers
        for (int i = 1; i <= 12; i++) {
            html.append("      <div class=\"clock-number\" style=\"--rotation: ").append(i * 30).append("deg;\">").append(i).append("</div>\n");
        }
        
        html.append("      <div class=\"hand hour-hand\" style=\"transform: rotate(").append(hourAngle).append("deg);\"></div>\n");
        html.append("      <div class=\"hand minute-hand\" style=\"transform: rotate(").append(minuteAngle).append("deg);\"></div>\n");
        html.append("      <div class=\"hand second-hand\" style=\"transform: rotate(").append(secondAngle).append("deg);\"></div>\n");
        html.append("      <div class=\"center-dot\"></div>\n");
        html.append("    </div>\n");
        html.append("  </div>\n");
        html.append("</div>\n");
        
        // If a city is selected, show it prominently
        if (selectedCity != null) {
            String[] cityInfo = getCityInfo(selectedCity);
            if (cityInfo != null) {
                String cityDisplay = cityInfo[0];
                String zoneId = cityInfo[1];
                
                ZonedDateTime cityTime = ZonedDateTime.now(ZoneId.of(zoneId));
                String cityTimeStr = cityTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                String cityDateStr = cityTime.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"));
                
                String[] cParts = cityTimeStr.split(":");
                int cHours = Integer.parseInt(cParts[0]);
                int cMinutes = Integer.parseInt(cParts[1]);
                int cSeconds = Integer.parseInt(cParts[2]);
                
                double cHourAngle = (cHours % 12) * 30 + cMinutes * 0.5 + cSeconds * 0.00833;
                double cMinuteAngle = cMinutes * 6 + cSeconds * 0.1;
                double cSecondAngle = cSeconds * 6;
                
                html.append("<div class=\"selected-city-card\">\n");
                html.append("  <div class=\"selected-city-info\">\n");
                html.append("    <div class=\"selected-city-name\">").append(cityDisplay).append("</div>\n");
                html.append("    <div class=\"selected-city-date\">").append(cityDateStr).append("</div>\n");
                html.append("    <div class=\"selected-city-time\">").append(cityTimeStr).append("</div>\n");
                html.append("  </div>\n");
                html.append("  <div class=\"clock-container\">\n");
                html.append("    <div class=\"analog-clock\" style=\"width: 150px; height: 150px;\">\n");
                
                // Add clock numbers for selected city
                for (int i = 1; i <= 12; i++) {
                    html.append("      <div class=\"clock-number\" style=\"transform: rotate(").append(i * 30).append("deg) translateY(-65px);\">").append(i).append("</div>\n");
                }
                
                html.append("      <div class=\"hand hour-hand\" style=\"height: 40px; transform: rotate(").append(cHourAngle).append("deg);\"></div>\n");
                html.append("      <div class=\"hand minute-hand\" style=\"height: 60px; transform: rotate(").append(cMinuteAngle).append("deg);\"></div>\n");
                html.append("      <div class=\"hand second-hand\" style=\"height: 70px; transform: rotate(").append(cSecondAngle).append("deg);\"></div>\n");
                html.append("      <div class=\"center-dot\"></div>\n");
                html.append("    </div>\n");
                html.append("  </div>\n");
                html.append("</div>\n");
            }
        }
        
        // Other cities section
        html.append("<div class=\"section-title\">");
        if (selectedCity != null) {
            html.append("Other Cities");
        } else {
            html.append("Cities Around the World");
        }
        html.append("</div>\n");
        
        // Cities grid
        html.append("<div class=\"cities-grid\">\n");
        
        // Define all cities with their time zones and display names
        String[][] cities = {
            {"london", "London", "Europe/London"},
            {"tokyo", "Tokyo", "Asia/Tokyo"},
            {"nyc", "New York", "America/New_York"},
            {"paris", "Paris", "Europe/Paris"},
            {"dubai", "Dubai", "Asia/Dubai"},
            {"toronto", "Toronto", "America/Toronto"},
            {"auckland", "Auckland", "Pacific/Auckland"},
            {"nairobi", "Nairobi", "Africa/Nairobi"},
            {"kingston", "Kingston", "America/Jamaica"},
            {"saopaulo", "Sao Paulo", "America/Sao_Paulo"},
            {"rome", "Rome", "Europe/Rome"},
            {"berlin", "Berlin", "Europe/Berlin"},
            {"moscow", "Moscow", "Europe/Moscow"},
            {"mumbai", "Mumbai", "Asia/Kolkata"},
            {"sydney", "Sydney", "Australia/Sydney"},
            {"beijing", "Beijing", "Asia/Shanghai"},
            {"losangeles", "Los Angeles", "America/Los_Angeles"},
            {"chicago", "Chicago", "America/Chicago"}
        };
        
        for (String[] city : cities) {
            String cityPath = city[0];
            String cityDisplay = city[1];
            String zoneId = city[2];
            
            // Skip the selected city if one is selected
            if (selectedCity != null && selectedCity.equals(cityPath)) {
                continue;
            }
            
            ZonedDateTime cityTime = ZonedDateTime.now(ZoneId.of(zoneId));
            String cityTimeStr = cityTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String cityDateStr = cityTime.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
            
            html.append("<a href=\"/").append(cityPath).append("\" class=\"city-card\">\n");
            html.append("  <div class=\"city-name\">").append(cityDisplay).append("</div>\n");
            
            // Small analog clock for each city
            String[] cParts = cityTimeStr.split(":");
            int cHours = Integer.parseInt(cParts[0]);
            int cMinutes = Integer.parseInt(cParts[1]);
            int cSeconds = Integer.parseInt(cParts[2]);
            
            double cHourAngle = (cHours % 12) * 30 + cMinutes * 0.5 + cSeconds * 0.00833;
            double cMinuteAngle = cMinutes * 6 + cSeconds * 0.1;
            double cSecondAngle = cSeconds * 6;
            
            html.append("  <div class=\"small-clock\">\n");
            html.append("    <div class=\"small-hand small-hour\" style=\"transform: rotate(").append(cHourAngle).append("deg);\"></div>\n");
            html.append("    <div class=\"small-hand small-minute\" style=\"transform: rotate(").append(cMinuteAngle).append("deg);\"></div>\n");
            html.append("    <div class=\"small-hand small-second\" style=\"transform: rotate(").append(cSecondAngle).append("deg);\"></div>\n");
            html.append("    <div class=\"small-center\"></div>\n");
            html.append("  </div>\n");
            
            html.append("  <div class=\"city-time-digital\">").append(cityTimeStr).append("</div>\n");
            html.append("  <div class=\"city-date\">").append(cityDateStr).append("</div>\n");
            html.append("</a>\n");
        }
        
        html.append("</div>\n"); // close cities-grid
        
        // Back link (show if on a city page)
        if (selectedCity != null) {
            html.append("<div style=\"text-align: center;\">\n");
            html.append("<a href=\"/\" class=\"back-link\">Back to all cities</a>\n");
            html.append("</div>\n");
        }
        
        html.append("</div>\n"); // close container
        html.append("</body>\n</html>");
        
        return html.toString();
    }
    
    private static String getCityDisplayName(String cityPath) {
        switch(cityPath) {
            case "london": return "London";
            case "tokyo": return "Tokyo";
            case "nyc": return "New York";
            case "paris": return "Paris";
            case "dubai": return "Dubai";
            case "toronto": return "Toronto";
            case "auckland": return "Auckland";
            case "nairobi": return "Nairobi";
            case "kingston": return "Kingston";
            case "saopaulo": return "São Paulo";
            case "rome": return "Rome";
            case "berlin": return "Berlin";
            case "moscow": return "Moscow";
            case "mumbai": return "Mumbai";
            case "sydney": return "Sydney";
            case "beijing": return "Beijing";
            case "losangeles": return "Los Angeles";
            case "chicago": return "Chicago";
            default: return cityPath;
        }
    }
    
    private static String[] getCityInfo(String cityPath) {
        switch(cityPath) {
            case "london": return new String[]{"London", "Europe/London"};
            case "tokyo": return new String[]{"Tokyo", "Asia/Tokyo"};
            case "nyc": return new String[]{"New York", "America/New_York"};
            case "paris": return new String[]{"Paris", "Europe/Paris"};
            case "dubai": return new String[]{"Dubai", "Asia/Dubai"};
            case "toronto": return new String[]{"Toronto", "America/Toronto"};
            case "auckland": return new String[]{"Auckland", "Pacific/Auckland"};
            case "nairobi": return new String[]{"Nairobi", "Africa/Nairobi"};
            case "kingston": return new String[]{"Kingston", "America/Jamaica"};
            case "saopaulo": return new String[]{"Sao Paulo", "America/Sao_Paulo"};
            case "rome": return new String[]{"Rome", "Europe/Rome"};
            case "berlin": return new String[]{"Berlin", "Europe/Berlin"};
            case "moscow": return new String[]{"Moscow", "Europe/Moscow"};
            case "mumbai": return new String[]{"Mumbai", "Asia/Kolkata"};
            case "sydney": return new String[]{"Sydney", "Australia/Sydney"};
            case "beijing": return new String[]{"Beijing", "Asia/Shanghai"};
            case "losangeles": return new String[]{"Los Angeles", "America/Los_Angeles"};
            case "chicago": return new String[]{"Chicago", "America/Chicago"};
            default: return null;
        }
    }
}
