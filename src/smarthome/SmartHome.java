package smarthome;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

abstract class SmartDevice{
    
    private boolean isOn;
    
    private final String location;

    public SmartDevice(String location) {
        this.isOn = false;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
    
    public boolean isTurnedOn(){
        return isOn;
    }
    
    public void turnOn(){
        isOn = true;
        System.out.println(location+ this.getClass().getName()+ " turned on");
    }
    
    public void turnOff(){
        isOn = false;
        System.out.println(location+ this.getClass().getName()+ " turned off");
    }
}

class GeneralElectricDevice extends SmartDevice{

    public GeneralElectricDevice(String location) {
        super(location);
    }
}

class Light extends SmartDevice{

    private int brightness = 5;

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        if(super.isTurnedOn()){
            this.brightness = brightness;
            System.out.println("Brightness set to");
        }
        else{
            System.out.println("Light not switched on");
        }
    }
    
    public Light(String location){
        super(location);
    }
}
/*
class RGBLight extends Light{

    enum Color{
        White,
        Red,
        Blue,
        Green
    }
    
    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if(super.isTurnedOn()){
            this.color = color;
            System.out.println("RGB light color set to");
        }
        else{
            System.out.println("RGB light not switched on");
        }
    }
    
    public RGBLight(String location) {
        super(location);
    }
}
*/
class Fan extends SmartDevice{

    private int speed;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if(super.isTurnedOn()){
            if(speed>=1 && speed<=5){
            this.speed = speed;
            System.out.println("Speed set to: ");
            }
            else{
                System.out.println("Speed can't be set to: ");
            }
        }
        else{
            System.out.println("Fan is not switched on");
        }
    }
    
    public Fan(String location){
        super(location);
    }
}

class CommandObject{
    String[] value = new String[]{"-1", "-1", "-1"};
    String location;
    String activationCode;

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
    
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
    }
}

interface Command{
    public void execute(CommandObject co);
}

class GeneralElectricDeviceOnCommand implements Command{

    GeneralElectricDevice ged;

    public GeneralElectricDeviceOnCommand(GeneralElectricDevice ged) {
        this.ged = ged;
    }
    
    @Override
    public void execute(CommandObject co) {
        ged.turnOn();
    }
}

class GeneralElectricDeviceOffCommand implements Command{

    GeneralElectricDevice ged;

    public GeneralElectricDeviceOffCommand(GeneralElectricDevice ged) {
        this.ged = ged;
    }
    
    @Override
    public void execute(CommandObject co) {
        ged.turnOff();
    }
}

class GeneralElectricDeviceChangeCommand implements Command{

    GeneralElectricDevice ged;

    public GeneralElectricDeviceChangeCommand(GeneralElectricDevice ged) {
        this.ged = ged;
    }
    
    @Override
    public void execute(CommandObject co) {
        ged.turnOff();
    }
}

class LightOnCommand implements Command{

    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute(CommandObject co) {
        light.turnOn();
    }
}

class LightOffCommand implements Command{

    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute(CommandObject co) {
        light.turnOff();
    }
}

class LightChangeCommand implements Command{

    Light light;

    public LightChangeCommand(Light light) {
        this.light = light;
    }
    
    @Override
    public void execute(CommandObject co) {
        light.setBrightness(Integer.parseInt(co.value[0]));
    }
}
/*
class RGBLightOnCommand implements Command{

    RGBLight rgbLight;

    public RGBLightOnCommand(RGBLight rgbLight) {
        this.rgbLight = rgbLight;
    }
    
    @Override
    public void execute(CommandObject co) {
        rgbLight.setColor(RGBLight.Color.valueOf(co.value[1]));
    }
}

class RGBLightOffCommand implements Command{

    RGBLight rgbLight;

    public RGBLightOffCommand(RGBLight rgbLight) {
        this.rgbLight = rgbLight;
    }
    
    @Override
    public void execute(CommandObject co) {
        rgbLight.setColor(RGBLight.Color.valueOf(co.value[1]));
    }
}

class RGBLightChangeCommand implements Command{

    RGBLight rgbLight;

    public RGBLightChangeCommand(RGBLight rgbLight) {
        this.rgbLight = rgbLight;
    }
    
    @Override
    public void execute(CommandObject co) {
        rgbLight.setColor(RGBLight.Color.valueOf(co.value[1]));
    }
}
*/
class FanOnCommand implements Command{

    Fan fan;

    public FanOnCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute(CommandObject co) {
        fan.turnOn();
    }
}

class FanOffCommand implements Command{

    Fan fan;

    public FanOffCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute(CommandObject co) {
        fan.turnOff();
    }
}

class FanChangeCommand implements Command{

    Fan fan;

    public FanChangeCommand(Fan fan) {
        this.fan = fan;
    }
    
    @Override
    public void execute(CommandObject co) {
        fan.setSpeed(Integer.parseInt(co.value[0]));
    }
}

interface SmartInterface{
    void executeCommand(String activationCode, String s, Command cmd, CommandObject co);
    void addDevice(String deviceName, String location, SmartDevice sd);
    void print_connected_device(String location);
}

class GoogleHome implements SmartInterface{

    private final String ActivationCode;
    private final String location;
    
    public GoogleHome(String activationCode, String location) {
        this.ActivationCode = activationCode;
        this.location = location;
    }

    //HashSet<String> googleHomeDevices = new HashSet<>();
    Map<String, SmartDevice> googleHomeDevices = new HashMap<>();
    
    @Override
    public void executeCommand(String activationCode, String s, Command cmd, CommandObject co) {
        if(!this.ActivationCode.equals(activationCode)){
            System.out.println("Activation code incorrect!");
            return;
        }
        if(!googleHomeDevices.containsKey(s))
        {
            System.out.println("Device not added!");
            return;
        }
        cmd.execute(co);
    }
    
    @Override
    public void addDevice(String deviceName, String location, SmartDevice sd){
        if(!this.location.equals(location)){
            System.out.println("Device can not be added!");
            return;            
        }
        googleHomeDevices.put(location+deviceName, sd);
    }
       
    @Override
    public void print_connected_device(String location) {
        Iterator it = googleHomeDevices.entrySet().iterator();
        int i=0;
        while (it.hasNext()) {
            Map.Entry<String, SmartDevice> pair = (Map.Entry)it.next();
            if(pair.getKey().substring(0, location.length()).equals(location)){
                System.out.println(i+" "+ pair.getKey().substring(location.length())+pair.getValue().isTurnedOn());
            }
            i++;
        }
    }
}

class Alexa implements SmartInterface{

    private final String ActivationCode;
    private final String location;
    
    public Alexa(String activationCode, String location) {
        this.ActivationCode = activationCode;
        this.location = location;
    }

    Map<String, SmartDevice> alexaDevices = new HashMap<>();
    
    @Override
    public void executeCommand(String activationCode, String s, Command cmd, CommandObject co) {
        if(!this.ActivationCode.equals(activationCode)){
            System.out.println("Activation code incorrect!");
            return;
        }
        if(!alexaDevices.containsKey(s))
        {
            System.out.println("Device not added!");
            return;
        }
        cmd.execute(co);
    }
    
    @Override
    public void addDevice(String deviceName, String location, SmartDevice sd){
        if(!this.location.equals(location)){
            System.out.println("Device can not be added!");
            return;            
        }
        alexaDevices.put(location+deviceName, sd);
    }

    @Override
    public void print_connected_device(String location) {
        Iterator it = alexaDevices.entrySet().iterator();
        int i=0;
        while (it.hasNext()) {
            Map.Entry<String, SmartDevice> pair = (Map.Entry)it.next();
            if(pair.getKey().substring(0, location.length()).equals(location)){
                System.out.println(i+" "+ pair.getKey().substring(location.length())+pair.getValue().isTurnedOn());
            }
            i++;
        }
    }
}

public class SmartHome {
    static Map<String, SmartInterface> interfaceMap = new HashMap<>();
    static Map<String, SmartInterface> activationMap = new HashMap<>();
    
    static Map<String, SmartDevice> deviceMap = new HashMap<>();
    static Map<String, String> deviceList = new HashMap<>();
    
    public static void add_interface_device(String interfaceName, String location, String activationCode){
        switch (interfaceName) {
            case "Google Home":
                interfaceMap.put(interfaceName, new GoogleHome(activationCode, location));
                activationMap.put(activationCode, interfaceMap.get(interfaceName));
                break;
            case "Alexa":
                interfaceMap.put(interfaceName, new Alexa(activationCode, location));
                activationMap.put(activationCode, interfaceMap.get(interfaceName));
                break;
            default:
                break;
        }
    }
    
    public static void add_smarthome_device(String deviceName, String location, String interfaceName){
        if(deviceMap.get(location+deviceName)!=null){
            interfaceMap.get(interfaceName).addDevice(deviceName, location, deviceMap.get(location+deviceName));
        }
        else{
            switch (deviceName){
                case "Light":
                    deviceMap.put(location+deviceName, new Light(location));
                    deviceList.put(deviceName, "Light");
                    interfaceMap.get(interfaceName).addDevice(deviceName, location, deviceMap.get(location+deviceName));
                    break;
                /*
                case "RGB Light":
                    deviceMap.put(location+deviceName, new RGBLight(location));
                    deviceList.put(deviceName, "RGBLight");
                    interfaceMap.get(interfaceName).addDevice(deviceMap.get(location+deviceName));
                    break;
                */
                case "Fan":
                    deviceMap.put(location+deviceName, new Fan(location));
                    deviceList.put(deviceName, "Fan");
                    interfaceMap.get(interfaceName).addDevice(deviceName, location, deviceMap.get(location+deviceName));
                    break;
                default:
                    deviceMap.put(location+deviceName, new GeneralElectricDevice(location));
                    deviceList.put(deviceName, "GeneralElectricDevice");
                    interfaceMap.get(interfaceName).addDevice(deviceName, location, deviceMap.get(location+deviceName));
                    break;
            }
        }
    }
    
    public static void give_command(String activationCode, String deviceName, String location, String input){
           
        try{
            switch (input){
                case "ON":
                {
                    if(deviceName.equals("Fan")){
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new FanOnCommand((Fan)(deviceMap.get(location+deviceName))) , null);
                    }
                    else if(deviceName.equals("Light")){
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new LightOnCommand((Light)(deviceMap.get(location+deviceName))) , null);
                    }
                    else{
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new GeneralElectricDeviceOnCommand((GeneralElectricDevice)(deviceMap.get(location+deviceName))) , null);
                    }
                    break;                    
                }
                case "OFF":
                {
                    if(deviceName.equals("Fan")){
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new FanOffCommand((Fan)(deviceMap.get(location+deviceName))) , null);
                    }
                    else if(deviceName.equals("Light")){
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new LightOffCommand((Light)(deviceMap.get(location+deviceName))) , null);
                    }
                    else{
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new GeneralElectricDeviceOffCommand((GeneralElectricDevice)(deviceMap.get(location+deviceName))) , null);
                    }
                    break; 
                }
                default:
                {
                    if(deviceName.equals("Fan")){
                        CommandObject co = new CommandObject();
                        co.value[0] = input;
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new FanChangeCommand((Fan)(deviceMap.get(location+deviceName))) , co);
                    }
                    else if(deviceName.equals("Light")){
                        CommandObject co = new CommandObject();
                        co.value[0] = input;
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new LightChangeCommand((Light)(deviceMap.get(location+deviceName))) , co);
                    }
                    else{
                        CommandObject co = new CommandObject();
                        co.value[0] = input;
                        activationMap.get(activationCode).executeCommand(activationCode, location+deviceName, new GeneralElectricDeviceChangeCommand((GeneralElectricDevice)(deviceMap.get(location+deviceName))) , co);
                    }
                    break; 
                }
                    
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void print_connected_device(String interfaceName, String location){
        interfaceMap.get(interfaceName).print_connected_device(location);
    }
    
    public static void main(String[] args) {

        add_interface_device("Google Home", "Living Room", "OK Google");
        add_interface_device("Alexa", "Drawing Room", "Alexa");
        add_smarthome_device("Light", "Drawing Room", "Alexa");
        add_smarthome_device("Fan", "Living Room", "Google Home");
        add_smarthome_device("Smart Charger", "Drawing Room", "Alexa");

        give_command("Alexa", "Light", "Drawing Room", "ON");
        give_command("OK Google", "Fan", "Living Room", "ON");
        give_command("OK Google", "Fan", "Living Room", "5");
        give_command("OK Google", "Fan", "Living Room", "7");
        give_command("Alexa", "Light", "Drawing Room", "8");
        give_command("Alexa", "Smart Charger", "Drawing Room", "ON");
        give_command("Alexa", "Smart Charger", "Drawing Room", "OFF");
        give_command("OK Google", "Fan", "Living Room", "OFF");
        give_command("OK Google", "Fan", "Living Room", "3");

        print_connected_device("Alexa", "Drawing Room");
    }
}
