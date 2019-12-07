package network;

import models.*;
import utils.JsonParser;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;


public class TcpClient
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String host;
    private int port;

    public TcpClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException
    {
        socket = new Socket(host, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        this.makeRequest("connect?" + socket.getInetAddress().getHostAddress());
    }

    public void disconnect()
    {
        this.makeRequest("disconnect");

        try
        {
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean hasAdmin()
    {
        this.makeRequest("has-admin");
        return this.getResponse().equals("200");
    }

    public User authoriseUser(String login, String password)
    {
        this.makeRequest("authorise-user?" + login + "&" + password);
        String response = this.getResponse();
        return response.equals("400") ? null : JsonParser.objectFromJson(response, User.class);
    }

    public boolean addUser(User user)
    {
        this.makeRequest("add-user?" + JsonParser.jsonFromObject(user));
        return this.getResponse().equals("200");
    }

    public boolean editUser(User user)
    {
        this.makeRequest("edit-user?" + JsonParser.jsonFromObject(user));
        return this.getResponse().equals("200");
    }

    public void deleteUser(User user)
    {
        this.makeRequest("delete-user?" + JsonParser.jsonFromObject(user));
    }

    public List<User> getUsers()
    {
        this.makeRequest("get-users");
        return Arrays.asList(JsonParser.objectFromJson(this.getResponse(), User[].class));
    }

    public boolean addProduct(Product product)
    {
        this.makeRequest("add-product?" + JsonParser.jsonFromObject(product));
        return this.getResponse().equals("200");
    }

    public boolean editProduct(Product product)
    {
        this.makeRequest("edit-product?" + JsonParser.jsonFromObject(product));
        return this.getResponse().equals("200");
    }

    public void deleteProduct(Product product)
    {
        this.makeRequest("delete-product?" + JsonParser.jsonFromObject(product));
    }

    public List<Product> getProducts()
    {
        this.makeRequest("get-products");
        return Arrays.asList(JsonParser.objectFromJson(this.getResponse(), Product[].class));
    }

    public boolean addBox(Box box)
    {
        this.makeRequest("add-box?" + JsonParser.jsonFromObject(box));
        return this.getResponse().equals("200");
    }

    public boolean editBox(Box box)
    {
        this.makeRequest("edit-box?" + JsonParser.jsonFromObject(box));
        return this.getResponse().equals("200");
    }

    public void deleteBox(Box box)
    {
        this.makeRequest("delete-box?" + JsonParser.jsonFromObject(box));
    }

    public List<Box> getBoxes()
    {
        this.makeRequest("get-boxes");
        return Arrays.asList(JsonParser.objectFromJson(this.getResponse(), Box[].class));
    }

    public boolean addOrder(Order order)
    {
        this.makeRequest("add-order?" + JsonParser.jsonFromObject(order));
        return this.getResponse().equals("200");
    }

    public boolean editOrder(Order order)
    {
        this.makeRequest("edit-order?" + JsonParser.jsonFromObject(order));
        return this.getResponse().equals("200");
    }

    public void deleteOrder(Order order)
    {
        this.makeRequest("delete-order?" + JsonParser.jsonFromObject(order));
    }

    public List<Order> getOrders()
    {
        this.makeRequest("get-orders");
        return Arrays.asList(JsonParser.objectFromJson(this.getResponse(), Order[].class));
    }

    public boolean addAddress(Address address)
    {
        this.makeRequest("add-address?" + JsonParser.jsonFromObject(address));
        return this.getResponse().equals("200");
    }

    public boolean editAddress(Address address)
    {
        this.makeRequest("edit-address?" + JsonParser.jsonFromObject(address));
        return this.getResponse().equals("200");
    }

    public void deleteAddress(Address address)
    {
        this.makeRequest("delete-address?" + JsonParser.jsonFromObject(address));
    }

    public List<Address> getAddresses()
    {
        this.makeRequest("get-addresses");
        return Arrays.asList(JsonParser.objectFromJson(this.getResponse(), Address[].class));
    }

    public boolean editWarehouseMap(WarehouseMap map)
    {
        this.makeRequest("edit-warehouse-map?" + JsonParser.jsonFromObject(map));
        return this.getResponse().equals("200");
    }

    public WarehouseMap getWarehouseMap()
    {
        this.makeRequest("get-warehouse-map");
        return JsonParser.objectFromJson(this.getResponse(), WarehouseMap.class);
    }

    public Place getPlace(String position)
    {
        this.makeRequest("get-place?" + position);
        String response = this.getResponse();

        if (!response.equals("400"))
        {
            return JsonParser.objectFromJson(response, Place.class);
        }

        return null;
    }

    public boolean addPlace(Place place)
    {
        this.makeRequest("add-place?" + JsonParser.jsonFromObject(place));
        return this.getResponse().equals("200");
    }

    public boolean editPlace(Place place)
    {
        this.makeRequest("edit-place?" + JsonParser.jsonFromObject(place));
        return this.getResponse().equals("200");
    }

    public void deletePlace(Place place)
    {
        this.makeRequest("delete-place?" + JsonParser.jsonFromObject(place));
    }

    public List<Place> getPlaces()
    {
        this.makeRequest("get-places");
        return Arrays.asList(JsonParser.objectFromJson(this.getResponse(), Place[].class));
    }


    private String getResponse()
    {
        String response = "";

        try
        {
            response = in.readUTF();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return response;
    }

    private void makeRequest(String query)
    {
        try
        {
            out.writeUTF(query);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
