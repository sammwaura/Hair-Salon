
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";

        ProcessBuilder process = new ProcessBuilder();
        Integer port;

        // This tells our app that if Heroku sets a port for us, we need to use that port.
        // Otherwise, if they do not, continue using port 4567.

        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

            //get route for the index page
            get("/", (request, response) ->{
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("stylists",Stylist.all());
                model.put("template", "templates/index.vtl");
                return new ModelAndView(model, layout);
            },new VelocityTemplateEngine());


        //get method that takes you to the individual client
        get("/clients/:id", (request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            Client myClient = Client.find(Integer.parseInt(request.params(":id")));
            model.put("client", myClient);
            model.put("template", "templates/update-client.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

    

        //the get method that routes you to all the clients 
        get("/clients", (request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("clients", Client.all());
            model.put("template", "templates/clients.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        //the post method that is responsible for saving a new stylist in the DB
        post("/stylist", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("stylistName");
            int phone = Integer.parseInt(request.queryParams("phoneNumber"));
            Stylist myStylist = new Stylist(name, phone);
            myStylist.save();
            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        //the post method that is responsible for saving a new clients in the DB
        post("/client", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("clientName");
            int phone = Integer.parseInt(request.queryParams("phoneNumber"));
            Stylist stylistid = Stylist.find(Integer.parseInt(request.queryParams("stylistId")));
            Client myClient = new Client(name, phone, stylistid.getId());
            myClient.save();
            model.put("stylist", stylistid);
            model.put("template", "templates/success.vtl"); 
            return new ModelAndView(model, layout); 
        },new VelocityTemplateEngine());

        //the get method that routes you to a specific stylist
        get("/stylist/:id", (request, response)->{
        Map<String, Object> model = new HashMap<String, Object>(); 
        Stylist mystylist = Stylist.find(Integer.parseInt(request.params(":id")));
        model.put("stylist", mystylist);
        model.put("template", "templates/stylist.vtl");   
        return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        //the post method responsible for deleting a specific stylist
        post("/stylist/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist myStylist = Stylist.find(Integer.parseInt(request.params(":id")));
            myStylist.delete();
            model.put("stylist", myStylist);
            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());
        
        //the post method responsible for deleting a specific client
        post("/clients/:id/delete", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Client myClient = Client.find(Integer.parseInt(request.params(":id")));
            myClient.delete();
            model.put("Client", myClient);
            model.put("template", "templates/success.vtl");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());

          //the post method responsible for updating a specific stylist 
          post("/stylist/:id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            String name = request.queryParams("stylistName");
            int phone = Integer.parseInt(request.queryParams("stylistNumber"));
            stylist.update(name, phone);
            response.redirect("/");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());

          //the post method responsible for updating a specific client
          post("/clients/:id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            String name = request.queryParams("clientName");
            int phone = Integer.parseInt(request.queryParams("clientNumber"));
            client.update(name, phone);
            response.redirect("/clients");
            return new ModelAndView(model, layout);
          }, new VelocityTemplateEngine());
    }
}