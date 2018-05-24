import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.Map;

import org.apache.velocity.Template;

import java.util.ArrayList;
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
        
        //get method that adds a client & takes you to the Home page
        get("/clients/new", (request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("stylists", Stylist.allStyle());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

     

        //STYLIST
        post("/stylists", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object()>;
            String name = request.queryParams("name");
            String style = request.queryParams("style");
            Stylists newStylist = new Stylist(name, style);
            newStylist.save();
            model.put("template", "templates/style-success.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());
    
        get("/stylist", (request, response) ->{
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("stylists", Stylist.all());
            model.put("template", "templates/stylist.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        get("/stylist/:id", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Interger.parseInt(request.params(":id")));
            model.put("clients", Client.all());
            model.put("stylists", stylist);
            model.put("template", "templates/viewStylist.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
    
        //the post method that is responsible for deleting client
        post("/client/:id", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            client.deleteClient();
            model.put("stylist", client);
            model.put("template", "templates/viewClients.vtl");
            return new ModelAndView(model, layout);
        },new VelocityTemplateEngine());

        //the post method that is responsible for deleting Stylist
        post("/stylist/:id", (request, response)->{
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            stylist.delete();
            model.put("stylist", stylist);
            model.put("template", "templates/stylist.vtl"); 
            return new ModelAndView(model, layout); 
        },new VelocityTemplateEngine());

        //Client
        post("/client", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams(":id")));
            String clientName = request.queryParams("clientName");
            String clientStyle = request.queryParams("clientStyle");
            Client newClient = new Client(clientName, clientStyle, stylist.getId());
            newClient.save();
            model.put("stylist", stylist);
            model.put("template", "templates/client-success.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("clients", Client.all());
            model.put("template", "templates/AboutUs.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());
        
        get("/stylist/:id/viewClients", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            Stylist stylist = Stylist.find(Integer.parseInt(request.params(":id")));
            Client client = Client.find(Integer.parseInt(request.params(":id")));
            model.put("clients", stylist.getClients());
            model.put("clients", client);
            model.put("stylist", stylist);
            model.put("template", "template/viewClients.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/client/:client_id/update", (request, response)-> {
            Map<String, Object> model = new HashMap<String, Object>();
            Client c;lient = Client.find(Integer.parseInt(request.params(":client_id")));
            String clientName = request.queryParams("clientName");
            String clientStyle = request.queryParams("clientStyle");
            client.update(clientName, clientStyle);
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/client/:client_id/update", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            Client client = Client.find(Integer.parseInt(request.params(":client_id")));
            model.put("client", client);
            model.put("template", "templates/updateClient.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    


        })
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